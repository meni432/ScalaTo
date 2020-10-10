import sbt.Keys.libraryDependencies

name := "ScalaToJavaClassesConverter"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")


version := "0.1"

scalaVersion := "2.13.3"

lazy val root = (project in file(".")).
    settings(
        name := "lambda-demo",
        version := "1.0",
        scalaVersion := "2.11.4",
        retrieveManaged := true,
        libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
        libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "1.2.1",
        libraryDependencies += "io.spray" %%  "spray-json" % "1.3.5",
        libraryDependencies += "com.lihaoyi" %% "fastparse" % "2.2.2"
    )
