import sbt._
import Keys._
import com.github.siasia.WebPlugin._

object MyProject extends Build {
  lazy val root = Project(
    id = "root",
    base = file("."),
    aggregate = Seq(snippets, server, client),
    settings = parentSettings
  )

  lazy val server = Project(
    id = "server",
    base = file("server"),
    settings = defaultSettings ++ webSettings ++ Seq(
      libraryDependencies ++= Deps.webPluginDeps :+ Deps.jerkson
    )
      //++ Seq(jettyScanDirs := file("C:\\github\\scala\\server\\target\\webapp\\WEB-INF\\web.xml") :: Nil)
  )

  lazy val client = Project(
    id = "client",
    base = file("client"),
    settings = baseSettings ++ Seq(
      libraryDependencies ++= Seq(Deps.jettyClient, Deps.jerkson)
    ) ++ Seq(scalacOptions ++= Seq("-deprecation"))
  )

  lazy val snippets = Project(
    id = "snippets",
    base = file("snippets"),
    settings = defaultSettings ++ Seq(
      libraryDependencies <+= Deps.swing,
      libraryDependencies += Deps.akkaActor,
      libraryDependencies += Deps.jerkson
    )
  )

  // Settings
  lazy val buildSettings = Seq(
    version := "1.0",
    scalaVersion := "2.9.1"
  )

  override lazy val settings = super.settings ++ buildSettings

  lazy val baseSettings = Defaults.defaultSettings ++ Seq(crossPaths := false)

  lazy val defaultSettings = baseSettings ++ Seq(
    resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "JBoss Public Repository Group" at "https://repository.jboss.org/nexus/content/groups/public/",
    resolvers += "repo.codahale.com" at "http://repo.codahale.com"
  )

  lazy val parentSettings = baseSettings ++ Seq(
    publishArtifact in Compile := false
  )

  // Dependencies
  object Deps {

    // Versions

    object V {
      val akka = "1.1.3"
      val jetty = "7.5.3.v20111011"
    }

    val swing = scalaVersion("org.scala-lang" % "scala-swing" % _)
    val akkaActor = "se.scalablesolutions.akka" % "akka-actor" % V.akka
    val jettyClient = "org.eclipse.jetty" % "jetty-client" % V.jetty
    
    // web plugin
    val webPluginDeps = {
      Seq(
        "org.eclipse.jetty" % "jetty-webapp" % V.jetty % "container", // The last part is "jetty" not "test".
        "org.eclipse.jetty" % "jetty-server" % V.jetty % "container",
        //for jsp
        "org.eclipse.jetty" % "jetty-jsp-2.1" % V.jetty % "container",
        "org.mortbay.jetty" % "jsp-2.1-glassfish" % "2.1.v20100127" % "container",
        "javax.servlet" % "servlet-api" % "2.5" % "provided->default"
      )
    }
    // jerkson
    val jerkson = "com.codahale" % "jerkson_2.9.1" % "0.5.0"
  }

}
