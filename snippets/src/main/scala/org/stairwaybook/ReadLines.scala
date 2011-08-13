package org.stairwaybook

import scala.io.Source

object ReadLines {

  def width(s: String) = s.length.toString.length

  def main(args: Array[String]) {
    if (args.length > 0) {
      val fileLines: List[String] = Source.fromFile(args(0)).getLines().toList
      val maxLength = width(fileLines.reduceLeft((a, b) => if (a.length > b.length) a else b))

      for (line <- fileLines)
        println((" " * (maxLength - width(line))) + line.length + " | " + line)
    }
    else
      Console.err.println("Please enter filename")
  }
}