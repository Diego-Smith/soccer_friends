
name := "soccer_friends"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
  cache,
  "com.typesafe.play" %% "play-slick" % "0.6.0.1" 
)     

play.Project.playScalaSettings

play.Keys.lessEntryPoints <<= baseDirectory { base =>
   (base / "app" / "assets" / "stylesheets" * "bootstrap.less") +++
   (base / "app" / "assets" / "stylesheets" * "responsive.less") 
}