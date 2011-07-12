object Lists extends App {
  def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
    case Nil => ys
    case x :: xt => x :: concat(xt, ys)
  }

  def reverse[T](xs: List[T]): List[T] = reverse(xs, Nil)

  def reverse[T](xs: List[T], acc: List[T]): List[T] = xs match {
    case Nil => acc
    case x :: xt => reverse(xt, x :: acc)
  }

  def reverse2[T](xs: List[T]): List[T] = (List[T]() /: xs)(_.::(_))

  def testConcatAndReverse() {
    println(concat(List(), List(1, 2, 3, 4, 5)))
    println(concat(List(1, 2, 3), List(4, 5)))
    println(concat(List(1, 2, 3, 4), List(5)))
    println(concat(List(1, 2, 3, 4, 5), List()))
    println(concat(List("a", "b"), List("c")))
    println()
    println(reverse(List(1, 2, 3, 4, 5)))
    println(reverse2(List(1, 2, 3, 4, 5)))
  }

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xt, y :: yt) => if (less(x, y)) x :: merge(xt, ys) else y :: merge(xs, yt)
    }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs.splitAt(n)
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

  def testMsort() {
    val mixedInts = List(4, 1, 9, 0, 5, 8, 3, 6, 2, 7)
    println(msort((x: Int, y: Int) => x < y)(mixedInts))
    println(msort((x: Int, y: Int) => x > y)(mixedInts))
    println(mixedInts.sortWith(_ < _))
  }

  testConcatAndReverse()
  println()
  testMsort()
}