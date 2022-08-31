import sbt.io.Path.rebase

javacOptions ++= Seq("-encoding", "UTF-8")

enablePlugins(GatlingPlugin)
enablePlugins(AssemblyPlugin)

name := "load_generator"
version := "1.0"

scalaVersion := "2.13.6"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-11", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

val gatlingVersion = "3.6.1"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "it"
libraryDependencies += "io.gatling" % "gatling-test-framework" % gatlingVersion % "it"
libraryDependencies += "org.scala-sbt" %% "compiler-bridge" % "1.5.8" % "it"

watchSources += baseDirectory.value / "src" / "it"

assembly / fullClasspath := (GatlingIt / fullClasspath).value
assembly / mainClass := Some("io.gatling.app.Gatling")

assembly / target := baseDirectory.value / "dist"

/*
  Assembly task will compile load_generator.jar to dist folder
  All unnecessary  resources will be excluded from the jar
*/
assembly / assemblyMergeStrategy := {
  case path if path.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case path if path.endsWith("module-info.class") => MergeStrategy.discard
  case PathList("logback.xml") => MergeStrategy.discard
  case PathList("logback.dummy") => MergeStrategy.discard
  case path if path.endsWith(".sh") => MergeStrategy.discard
  case path if path.endsWith(".bat") => MergeStrategy.discard
  case path => {
    val currentStrategy = (assembly / assemblyMergeStrategy).value
    currentStrategy(path)
  }
}

/*
  This task will copy files from resources folder to target dist folder before compiling
*/
lazy val copyResourcesTask = taskKey[Unit]("Copy resources")

copyResourcesTask := {
  println("Start copying")
  val to = baseDirectory.value / "dist"
  val res = baseDirectory.value / "src" / "it" / "resources"
  val confFiles: Seq[File] = (res ** ("application.conf" || "*.xml" || "*.sh" || "*.bat")).get()
  val pairs = confFiles pair rebase(res, to)

  IO.copy(pairs, CopyOptions.apply(overwrite = true, preserveLastModified = true, preserveExecutable = false))
  println("Done copying")
}

Compile / compile := {
  copyResourcesTask.value
  (Compile / compile).value
}