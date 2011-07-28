package org.lroza

import actors.Actor._
import actors.threadpool.helpers.Utils.nanoTime

object LoadTest extends App {
  val n = 400000

  val main = self
  val pongReceiver = actor{
    loop{
      react{
//        case Pong(time) => main ! (currentTime - time)
        case Pong(time) => main ! ((nanoTime - time) / 1000000)
        case "STOP" => exit()
      }
    }

  }

  def run = {
    val start = nanoTime
    for (i <- 1 to n)
      PingServer ! Ping(nanoTime)

    val pongs = for (i <- 1 to n) yield receive {
      case Pong(time) => (nanoTime - time) / 1000000
      case roundtrip: Long => roundtrip
    }
    val duration = (nanoTime - start) / 1000000

    val size = pongs.size

    println("%d pongs received, duration = %d ms, throughput = %.2f pings/sec".format(size, duration, size / (duration / 1000.)))
    println("round-trip min/avg/max = %d/%d/%d ms".format(pongs.min, pongs.sum / size, pongs.max))
    (size, duration, pongs.min, pongs.sum, pongs.max)
  }

  def prompt = {
    var line = ""
    while (line != "OK")
      line = readLine("Type in OK when ready...")
  }

  run
//  prompt

  val total = for (i <- 1 to 10) yield run

  val (size, duration, min, sum, max) = (total.head /: total.tail)((a, b) => (a._1 + b._1, a._2 + b._2, a._3 min b._3, a._4 + b._4, a._5 max b._5))

  println("Total:")
  println("%d pongs received, duration = %d ms, throughput = %.2f pings/sec".format(size, duration, size / (duration / 1000.)))
  println("round-trip min/avg/max = %d/%d/%d ms".format(min, sum / size, max))

  PingServer ! "STOP"
  pongReceiver ! "STOP"
}