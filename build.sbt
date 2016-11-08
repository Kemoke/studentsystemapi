name := """studentsystem"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.feth" % "play-authenticate_2.11" % "0.8.1-SNAPSHOT",
  "org.mongodb" % "mongo-java-driver" % "3.4.0-rc1",
  "org.mongodb.morphia" % "morphia" % "1.2.1",
  javaJdbc,
  cache,
  javaWs
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"