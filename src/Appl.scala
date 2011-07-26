import actors.Actor._
import scala.compat.Platform.currentTime

object Appl extends App {
  val sillyActor2 = actor {
    def emoteLater() {
      val mainActor = self
      actor {
        val t = currentTime
        Thread.sleep(1000)
        println(currentTime - t)
        mainActor ! "Emote"
      }
    }
    var emoted = 0
    emoteLater()
    loop {
      react {
        case "Emote" =>
          println("I'm acting!")
          emoted += 1
          if (emoted < 5)
            emoteLater()
          else
            System.exit(0)
        case msg =>
          println("Received: " + msg)
      }
    }
  }
  sillyActor2 ! "Hello"
  sillyActor2 ! "Hello"
  sillyActor2 ! "Hello"
  sillyActor2 ! "Hello"
  List(1, 1, 1, 2, 2).span()
}

