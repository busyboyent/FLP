name := "assignment08"

version := "0.1"

scalaVersion := "2.12.10"

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0"

scalacOptions ++= List(
  "-deprecation",
  "-feature",
  "-Ypartial-unification",
  "-Yrangepos",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:params",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates",
  "-Ywarn-adapted-args"
)

lazy val Acceptance = config("acceptance").extend(Test)

lazy val solution = project.in(file(".")).settings(
  inConfig(Acceptance)(Defaults.testSettings),
  scalaSource in Acceptance := baseDirectory.value / "src"
)

compile in Compile := (Def.taskDyn {
  val compilation = (compile in Compile).value
  Def.task {
    (scalafix in Compile).toTask("").value
    compilation
  }
}).value

compile in Acceptance := (Def.taskDyn {
  val compilation = (compile in Acceptance).value
  Def.task {
    (scalafix in Compile).toTask("").value
    compilation
  }
}).value
