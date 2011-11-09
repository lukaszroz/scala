import annotation.tailrec
import java.io.{PrintWriter, File}

object HelloWorld extends App {

  def withPrintWriter(file: File)(op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  val file = new File("date.txt")
  withPrintWriter(file) {
    writer => writer.println(new java.util.Date)
  }

  @tailrec
  def noweWhile(predykat: => Boolean)(zawartosc: => Unit) {
    if (predykat) {
      zawartosc
      noweWhile(predykat)(zawartosc)
    }
  }

  var i = 0;
  noweWhile(i < 10) {
    println(i)
    i += 1
  }
}