package org.lroza

import actors._
import Actor._

case class Ping(time: Long)

case class Pong(time: Long)

object PingServer extends Actor {
  def pong(time: Long, from: Actor) = actor {
    from ! Pong(time)
  }

  def act() {
    loop {
      react {
        case Ping(time) => sender ! Pong(time)
        case "STOP" => exit()
      }
    }
  }

  start()
}