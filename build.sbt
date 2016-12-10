name := """studentsystem"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.4.0-rc1",
  "org.mongodb.morphia" % "morphia" % "1.2.1",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "io.jsonwebtoken" % "jjwt" % "0.7.0",
  "org.jsoup" % "jsoup" % "1.10.1",
  "net.sourceforge.htmlunit" % "htmlunit" % "2.23",
  javaJdbc,
  cache,
  javaWs,
  filters
)