name := "fotofm"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.27"

play.Project.playScalaSettings

sources in (Compile, doc) := Seq.empty

publishArtifact in (Compile, packageDoc) := false
