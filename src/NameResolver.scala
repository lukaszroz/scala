import actors.Actor

object NameResolver extends Actor {

  import java.net.{InetAddress, UnknownHostException}

  def act() {
    react {
      case (name: String, actor: Actor) =>
        println("Acting...")
        actor ! getIp(name)
        println("Done.")
        act()
      case "EXIT" =>
        println("Name resolver exiting.")
      // quit
      case msg =>
        println("Unhandled message: " + msg)
        act()
    }
  }

  def getIp(name: String): Option[InetAddress] = {
    try {
      val inetAddress = InetAddress.getByName(name)
      println(inetAddress)
      Some(inetAddress)
    } catch {
      case _: UnknownHostException => None
    }
  }
}