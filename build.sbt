scalaVersion := "2.12.6"

name := "statistics"
organization := "uk.co.kenfos"
version := "1.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.20.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.19"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.13.3"
libraryDependencies += "org.codehaus.groovy" % "groovy" % "2.5.0"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.7"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.19"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.12" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.7" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "4.1.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

assemblyJarName in assembly := "statistics.jar"

assemblyMergeStrategy in assembly := {
  case file if file.toLowerCase.endsWith("manifest.mf")         => MergeStrategy.discard
  case file if file.toLowerCase.endsWith("versions.properties") => MergeStrategy.discard
  case file                                                     => (assemblyMergeStrategy in assembly).value(file)
}
