package org.actors

import actors.Actor
import actors.Actor._
import compat.Platform._

object ActorTest extends App {

  case object Ping

  case object Pong

  case object Stop

  case object Reset

  class PingActor(i: Int) extends Actor {
    def act {
      react {
        case (Ping, sender: Actor) =>
          sender ! (Pong, i)
          act()
        case Stop => exit();
      }
    }
  }

  def ping(i: Int) = new PingActor(i).start()

  class PongActor(id: Int, max: Int, main: Actor) extends Actor {
    var pongs = 0
    var lastActor = 0

    def act {
      react {
        case (Pong, i: Int) =>
          pongs += 1
          lastActor = i
//          println("[%d] Pongs received: %d, last actor id: %d".format(id, pongs, lastActor))
          if (pongs == max) {
            main ! (Stop, pongs)
          }
          act()
        case Stop =>
        case Reset =>
          pongs = 0
          act()
      }
    }
  }

  def pong(id: Int, max: Int = n) = new PongActor(id, max, self).start()
//  {
//    val main = self
//    actor {
//      def loop(pongs: Int, lastActor: Int) {
//        react {
//          case (Pong, i: Int) =>
//            //            println("[%d] Pongs received: %d, last actor id: %d".format(id, pongs, lastActor))
//            if (pongs + 1 == max) {
//              main ! (Stop, pongs + 1)
//            }
//            loop(pongs + 1, i)
//          case Stop =>
//          case Reset => loop(0, lastActor)
//        }
//      }
//      loop(0, 0)
//    }
//  }

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
    pings foreach (_ ! Stop)
    //    println("----- Stop sent -----")
  }

  def stopPongs = {
    //    println("----- Stopping pongs... -----")
    pongs foreach (_ ! Stop)
    //    println("----- Stop sent -----")
  }

  def pongsWait = for (i <- 1 to pongN) receive {
    case (Stop, pongs: Int) => total += pongs
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
  println("[Max throughput: %.2f pings/sec]".format(maxThroughput))
}