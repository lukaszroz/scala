package org.actors

import akka.actor._
import Actor._
import compat.Platform._

object AkkaActorTest extends App {

  case object Ping

  case object Pong

  case object Stop

  case object Reset

  class PingActor(i: Int) extends Actor {
    def receive = {
      case (Ping, sender: ActorRef) =>
        sender ! (Pong, i)
    }
  }

  def ping(i: Int) = actorOf(new PingActor(i)).start()

  class PongActor(id: Int, max: Int, main: ActorRef) extends Actor {
    var pongs, lastActor = 0

    def receive = {
      case (Pong, i: Int) =>
        pongs += 1
        lastActor = i
        if (pongs == max) {
          main ! (Stop, pongs)
        }
      case Reset => pongs = 0
    }
  }

  class MainActor extends Actor {
    protected def receive = {
      case (Stop, pongs: Int) => total += pongs;
    }
  }

  val main = actorOf[MainActor].start()

  def pong(id: Int, max: Int = n) = actorOf(new PongActor(id, max, main)).start()

  def run = {
    //    println("----- Sending pings... -----")
    pings.foreach {
      (ping) =>
        pongs.foreach((pong) => ping ! (Ping, pong))
    }
    //    println("----- Pings sent -----")
  }

  def stopPings = {
    //    println("----- Stopping pings... -----")
    pings foreach (_ ! PoisonPill)
    //    println("----- Stop sent -----")
  }

  def stopPongs = {
    //    println("----- Stopping pongs... -----")
    pongs foreach (_ ! PoisonPill)
    //    println("----- Stop sent -----")
  }

  def pongsWait = {
    while(total < pongN * n * multiply) {
      Thread.sleep(100);
    }
  }

  def pongsReset = pongs.foreach(_ ! Reset)

  def prompt = {
    var line = ""
    while (line != "OK")
      line = readLine("Type in OK when ready...")
  }

  //  prompt

  var total = 0

  val n = 4
  val pongN = 4
  val multiply = 100000

  val pongs = for (i <- 1 to pongN) yield pong(i, n * multiply)
  val pings = for (i <- 1 to n) yield ping(i)

  //  prompt

  var line = ""
  var maxThroughput = 0.
  while (line != "OK") {
    val start: Long = currentTime

    for (i <- 1 to multiply)
      run

    pongsWait

    val time = currentTime - start
    val throughput = total / (time / 1000.)
    println("[Total pongs received: %d, time: %d, throughput: %.2f pings/sec]".format(total, time, throughput))

    pongsReset
    total = 0
    maxThroughput = throughput max maxThroughput

    line = readLine("Type in OK when ready...")
  }

  stopPings
  stopPongs
  main ! PoisonPill
  println("[Max throughput: %.2f pings/sec]".format(maxThroughput))
}