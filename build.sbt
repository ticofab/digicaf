name := "digicaf"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.5",

  "de.heikoseeberger" %% "akka-http-play-json" % "1.15.0"
)