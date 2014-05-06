// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")
//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3-M1")

//addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.0-M2a")
