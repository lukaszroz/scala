import scala.Console

object SearchFrom extends App {

  def searchFrom(i: Int): Int =
    if (i >= arr.length) -1
    else arr(i) match {
      case s: String if s.startsWith("-") => searchFrom(i + 1)
      case s: String if s.endsWith(".scala") => i
      case _ => searchFrom(i + 1)
    }

  //    if (arr(i).startsWith("-"))
  //      searchFrom(i + 1)
  //    else if (arr(i).endsWith(".scala")) i
  //    else searchFrom(i + 1)

  val arr = Array("-.scala", "scala", "a.scala")

  Console println arr(searchFrom(0))
}