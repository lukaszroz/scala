package org.lroza

import collection.mutable.ArrayBuffer
import actors.threadpool.helpers.Utils


object ForTest extends App{
  case class Node(outEdges: Seq[Int])

  val outEdges: Seq[Int] = for (i <- 0 to 10000000 - 1) yield i

  val number = for (i <- 1 to 10000000) yield 0

  val UNVISITED = 0
  val currentNode = Node(outEdges)

  def DFS(i: Int, arrayBuffer: IndexedSeq[Int], i1: Int): Int = {
    i1
  }
  
  var lastid = 0;

  def forComprehension {
    for (target <- currentNode.outEdges
    if (number(target) == UNVISITED))
      lastid = DFS(target, number, lastid + 1)
  }

  def forEach {
    currentNode.outEdges.foreach(target =>
      if (number(target) == UNVISITED)
        lastid = DFS(target, number, lastid + 1)
    )
  }

  forComprehension
  forComprehension
  forComprehension
  val start = Utils.nanoTime
  forComprehension
  val dur = Utils.nanoTime - start
  printf("Duration: %d ms\n", dur/1000000)

  forEach
  forEach
  forEach
  val start2 = Utils.nanoTime
  forEach
  val dur2 = Utils.nanoTime - start
  printf("Duration: %d ms", dur/1000000)

}