import sbt.Keys.compile
import sbt.addCompilerPlugin

name := "assignment09"

version := "0.1"

scalaVersion in ThisBuild := "2.12.10"


addCompilerPlugin(scalafixSemanticdb)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.3"

compile in Compile := (Def.taskDyn {
  val compilation = (compile in Compile).value
  Def.task {
    (scalafix in Compile).toTask("").value
    compilation
  }
}).value
