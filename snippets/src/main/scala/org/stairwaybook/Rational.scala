package org.stairwaybook

class Rational(n: Int, d: Int) {
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val nn = n / g
  val dd = d / g

  def this(n: Int) = this (n, 1)

  def +(that: Rational) = new Rational(that.dd * nn + dd * that.nn, dd * that.dd)

  def +(that: Int) = new Rational(nn + dd * that, dd)

  def -(that: Rational) = new Rational(that.dd * nn - dd * that.nn, dd * that.dd)

  def -(that: Int) = new Rational(nn - dd * that, dd)

  def *(that: Rational) = new Rational(nn * that.nn, dd * that.dd)

  def *(that: Int) = new Rational(nn * that, dd)

  def /(that: Rational) = new Rational(nn * that.dd, dd * that.nn)

  def /(that: Int) = new Rational(nn, dd * that)

  def <(that: Rational) = that.dd * nn < dd * that.nn

  def max(that: Rational) = if (this < that) that else this

  override def toString = nn + "/" + dd

  private def gcd(a: Int, b: Int): Int = {
    if (b == 0) a else gcd(b, a % b)
  }
}

//new Rational(3,4) < new Rational(5,6)