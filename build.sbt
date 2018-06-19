name := "ovo-exercise"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  // https://mvnrepository.com/artifact/com.typesafe.play/play-json
  "com.typesafe.play" %% "play-json" % "2.6.9",
  // https://mvnrepository.com/artifact/org.scalatest/scalatest
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)