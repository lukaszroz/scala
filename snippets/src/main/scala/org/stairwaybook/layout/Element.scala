package org.stairwaybook.layout

abstract class Element {
  def contents: Array[String]

  def height: Int = contents.length

  def width: Int = if (height == 0) 0 else contents(0).length

  def above(that: Element): Element = {
    Element(this.widen(that.width).contents ++ that.widen(this.width).contents)
  }

  def beside(that: Element): Element = {
    Element(
      for (
        (line1, line2) <- this.heighten(that.height).contents zip that.heighten(this.height).contents
      ) yield line1 + line2
    )
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = Element(' ', (w - width) / 2, height)
      val right = Element(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = Element(' ', width, (h - height) / 2)
      val bot = Element(' ', width, h - height - top.height)
      top above this above bot
    }

  override def toString = contents mkString "\n"
}

object Element {

  private class ArrayElement(val contents: Array[String]) extends Element

  private class LineElement(s: String) extends Element {
    def contents = Array(s)

    override def width = s.length

    override def height = 1
  }

  private class UniformElement(ch: Char, override val width: Int, override val height: Int) extends Element {
    private val line = ch.toString * width

    def contents = Array.fill(height)(line)
  }

  def apply(s: String): Element = {
    new LineElement(s)
  }

  def apply(a: Array[String]): Element = {
    new ArrayElement(a)
  }

  def apply(ch: Char, width: Int, height: Int): Element = {
    new UniformElement(ch, width, height)
  }
}