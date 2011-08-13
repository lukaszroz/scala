import sbt._
import Keys._

object MyBuild extends Build {
  lazy val root = Project(
    id = "root",
    base = file("."),
    aggregate = Seq(snippets, server, client),
    settings = parentSettings
  )

  lazy val server = Project(
    id = "server",
    base = file("server"),
    settings = baseSettings
  )

  lazy val client = Project(
    id = "client",
    base = file("client"),
    settings = baseSettings
  )

  lazy val snippets = Project(
    id = "snippets",
    base = file("snippets"),
    settings = defaultSettings ++ Seq(
      libraryDependencies <+= scalaVersion(Dependency.swing % _),
      libraryDependencies += Dependency.akkaActor
    )
  )

  // Settings
  lazy val buildSettings = Seq(
    version := "1.0",
    scalaVersion := "2.9.0-1"
  )

  override lazy val settings = super.settings ++ buildSettings

  lazy val baseSettings = Defaults.defaultSettings ++ Seq(crossPaths := false)

  lazy val defaultSettings = baseSettings ++ Seq(
      resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  )

  lazy val parentSettings = baseSettings ++ Seq(
    publishArtifact in Compile := false
  )

  // Dependencies
  object Dependency {

    // Versions

    object V {
      val akka = "1.1.3"
    }

    val swing = "org.scala-lang" % "scala-swing"
    val akkaActor = "se.scalablesolutions.akka" % "akka-actor" % V.akka
  }
}
