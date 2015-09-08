name := "tags"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.9.2",
  "com.twitter" %% "scrooge-core" % "4.0.0",
  "com.twitter" %% "finagle-thrift" % "6.28.0",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)