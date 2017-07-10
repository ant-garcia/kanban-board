name := """kanban"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava).enablePlugins(SbtWeb)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	javaJpa,
	cache,
	javaWs,
	"mysql" % "mysql-connector-java" % "5.1.36",
  	"org.hibernate" % "hibernate-core" % "5.1.0.Final",
  	"org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
)

fork in run := true