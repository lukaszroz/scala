package org.actors

import compat.Platform._
import util.Random
import concurrent.ops._
import org.stairwaybook.expr.Var
import java.lang.{Thread, Object}

object NoActorTest extends App {

  case class Ping(p: PongNoActor)

  case class Pong(i: Int)

  class Reset

  class PingNoActor(i: Int) {
    def !(p: Ping) {
      p.p ! new Pong(i)
    }
  }

  def ping(i: Int) = new PingNoActor(i)

  class PongNoActor(id: Int, max: Int) {
    var pongs = 0
    var lastActor = 0
    var lastResult = 0.0
    val pongsLock: Object = new Object

    def doWork(): Double = {
      (for (i <- 1 to 100) yield math.sqrt(Random.nextDouble())) sum
    }

    def !(p: Pong) = {
      pongsLock.synchronized(pongs += 1)
      lastActor = p.i
      lastResult = doWork()
      //      println("[%d] Pongs received: %d, last actor id: %d".format(id, pongs, lastActor))
    }

    def !(r: Reset) {
      pongsLock.synchronized(pongs = 0)
    }
  }

  def pong(id: Int, max: Int = n) = new PongNoActor(id, max)

  def runTest {
    //    println("----- Sending pings... -----")
    pings.foreach {
      (ping) =>
        pongs.foreach((pong) => ping ! (new Ping(pong)))
    }
    //    println("----- Pings sent -----")
  }

  val r = new Reset

  def pongsReset = pongs.foreach(_ ! r)

  def prompt = {
    var line = ""
    while (line != "OK")
      line = readLine("Type in OK when ready...")
  }

  //  prompt

  var total = 0

  val n = 4
  val pongN = 4
  val multiply = 10000

  val pongs = for (i <- 1 to pongN) yield pong(i, n * multiply)
  val pings = for (i <- 1 to n) yield ping(i)

  //  prompt

  var line = ""
  var maxThroughput = 0.
  while (line != "OK") {
    val start: Long = currentTime

    val t = new Thread(new Runnable {
      def run() {
        for (i <- 1 to multiply / 2)
          runTest
      }
    })

    t.start()
    for (i <- 1 to multiply / 2)
      runTest
    t.join()

    val time = currentTime - start

    total = pongs map (_.pongs) sum
    val throughput = total / (time / 1000.)
    println("[Total pongs received: %d, time: %d, throughput: %.2f pings/sec]".format(total, time, throughput))

    pongsReset
    total = 0
    maxThroughput = throughput max maxThroughput

    line = readLine("Type in OK when ready...")
  }

  println("[Max throughput: %.2f pings/sec]".format(maxThroughput))
}