name := "logging.context"

version := "0.1"

scalaVersion := Versions.scalaV

libraryDependencies ++= Dependencies.akka ++ Dependencies.logging ++ Dependencies.kamon

// Bring the sbt-aspectj settings into this build
aspectjSettings

// Here we are effectively adding the `-javaagent` JVM startup
// option with the location of the AspectJ Weaver provided by
// the sbt-aspectj plugin.
javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj

// We need to ensure that the JVM is forked for the
// AspectJ Weaver to kick in properly and do it's magic.
fork in run := true
