import scala.Console

object SearchFrom extends App {

  object Begins {
    def apply(s: String, s1: String) = s + s1

    def unapply(s: String): Option[(String, String)] = {
      return Some(s splitAt 1)
    }
  }

  val pattern = """([^-]{0,1}.*\.scala)""".r

  def searchFrom(i: Int): Int =
    if (i >= arr.length) -1
    else arr(i) match {
      //      case pattern(_) => searchFrom(i + 1)
      case pattern(_) => i
      case _ => searchFrom(i + 1)
    }

  //    if (arr(i).startsWith("-"))
  //      searchFrom(i + 1)
  //    else if (arr(i).endsWith(".scala")) i
  //    else searchFrom(i + 1)

  val arr = Array("-.scala", ".scala", "", "-", "a.scala")

  Console println arr(searchFrom(0))
}