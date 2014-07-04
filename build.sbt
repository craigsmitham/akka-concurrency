name := "Akka Book"

organization := "zzz.akka"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

 libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.4",
    "org.scalatest" %% "scalatest" % "2.2.0" % "test", 
    "com.typesafe.akka" %% "akka-testkit" % "2.3.4",
    "com.typesafe.akka" %% "akka-actor" % "2.3.4"
  )

sublimeProjectName := "AkkaBook"