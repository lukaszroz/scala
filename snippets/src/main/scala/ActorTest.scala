import actors.Actor
import actors.Actor._
import compat.Platform._

object ActorTest extends App {

  case object Ping

  case object Pong

  case object Stop

  case object Reset

  def ping(i: Int) = actor(
    loop {
      react {
        case (Ping, sender: Actor) => sender ! (Pong, i)
        case Stop =>
          if (i == n)
            println("[%d] stopped".format(i))
          exit()
      }
    }
  )

  def pong(id: Int, max: Int = n) = {
    val main = self
    actor {
      var pongs = 0
      var lastActor = 0
      loop {
        react {
          case (Pong, i: Int) =>
            pongs += 1
            lastActor = i
            //            println("[%d] Pongs received: %d, last actor id: %d".format(id, pongs, lastActor))
            if (pongs == max) {
              main ! (Stop, pongs)
            }
          case Stop => exit()
          case Reset => pongs = 0
        }
      }
    }
  }

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

  prompt

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