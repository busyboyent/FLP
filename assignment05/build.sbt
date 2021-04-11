import sbt.Keys.compile
import sbt.addCompilerPlugin

name := "assignment"

version := "0.1"

scalaVersion in ThisBuild := "2.12.10"

addCompilerPlugin(scalafixSemanticdb)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"

compile in Compile := (Def.taskDyn {
  val compilation = (compile in Compile).value
  Def.task {
    (scalafix in Compile).toTask("").value
    compilation
  }
}).value
