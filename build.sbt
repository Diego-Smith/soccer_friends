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
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)


//LessKeys.compress in Assets := false

play.Project.playScalaSettings

//play.Keys.lessEntryPoints <<= baseDirectory { base =>
//   (base / "app" / "assets" / "stylesheets" * "asset.less")
//}
//   (base / "app" / "assets" / "stylesheets" * "mixins.less") +++
//   (base / "app" / "assets" / "stylesheets" * "carousel.less")
