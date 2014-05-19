import sbt._
import sbt.Keys._
import play.Project._

name := "soccer_friends"

version := "1.0-SNAPSHOT"



//lazy val root = (project in file(".")).addPlugins(SbtWeb)
scalacOptions ++= Seq("-feature")

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
  cache,
  "com.typesafe.play" %% "play-slick" % "0.6.0.1",
  "org.webjars" %% "webjars-play" % "2.2.0",
  // Downgrade to JQuery 1.8.3 so that integration tests with HtmlUnit work.
  "org.webjars" % "bootstrap" % "3.0.0" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "1.8.3",
  "org.scalacheck" %% "scalacheck" % "1.10.1" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "ws.securesocial" %% "securesocial" % "2.1.3",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.5",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.1.0"
)


//LessKeys.compress in Assets := false

play.Project.playScalaSettings

scalaVersion := "2.10.4"

autoScalaLibrary := false

play.Keys.lessEntryPoints <<= baseDirectory { base =>
   (base / "app" / "assets" / "stylesheets" * "layout.less")
}
//   (base / "app" / "assets" / "stylesheets" * "mixins.less") +++
//   (base / "app" / "assets" / "stylesheets" * "carousel.less")
