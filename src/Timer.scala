import scala.compat.Platform.currentTime


class Timer() {
  val executionStart: Long = currentTime

  val total = currentTime - executionStart
  Console.println("[total " + total + "ms]")
}