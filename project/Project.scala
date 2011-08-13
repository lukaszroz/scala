import sbt._

object MyBuild extends Build {
  lazy val buildSettings = Seq(
    version      := "1.0",
    scalaVersion := "2.9.0-1"
  )

  lazy val root = Project(
    id = "root",
    base = file("."),
    aggregate = Seq(snippets)
  )

  lazy val server = Project(
    id = "server",
    base = file("server")
  )

  lazy val client = Project(
    id = "client",
    base = file("client")
  )

  lazy val snippets = Project(
    id = "snippets",
    base = file("snippets")
  )
}
