import sbt.Keys._
import play.sbt.PlaySettings

lazy val scala213 = "2.13.12"
lazy val scala3 = "3.3.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayService, PlayLayoutPlugin, Common)
  .settings(
    name := "rest-api-nuit-l'info",
    scalaVersion := scala213,
    crossScalaVersions := Seq(scala213, scala3),
    libraryDependencies ++= Seq(
      guice,
      "org.joda" % "joda-convert" % "2.2.3",
      "net.logstash.logback" % "logstash-logback-encoder" % "7.3",
      "io.lemonlabs" %% "scala-uri" % "4.0.3",
      "net.codingwell" %% "scala-guice" % "6.0.0",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-Werror"
    )
  )

