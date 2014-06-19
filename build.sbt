name := "Akka Book"

organization := "zzz.akka"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

 libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.2.3",
    "org.scalatest" %% "scalatest" % "2.2.0" % "test", 
    "com.typesafe.akka" %% "akka-testkit" % "2.2.3",
    "com.typesafe.akka" %% "akka-actor" % "2.2.3"
  )