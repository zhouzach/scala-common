name := "scala-common"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.9.9",
  "com.github.nscala-time" %% "nscala-time" % "2.16.0",
  "org.apache.hive" % "hive-jdbc" % "1.1.0",
  "org.apache.hadoop" % "hadoop-common" % "2.7.1",
  "com.alibaba" % "druid" % "1.0.28",
  "org.scalaj" %% "scalaj-http" % "2.3.0",

  // https://mvnrepository.com/artifact/com.typesafe.akka/akka-http
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  // https://mvnrepository.com/artifact/com.typesafe.akka/akka-stream
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",

  // https://mvnrepository.com/artifact/org.mule.mchange/c3p0
  "com.mchange" % "c3p0" % "0.9.5.2",
  "commons-dbutils" % "commons-dbutils" % "1.6",
  "mysql" % "mysql-connector-java" % "5.1.44",

  "com.squareup.okhttp3" % "okhttp" % "3.13.1",
  "com.squareup.moshi" % "moshi" % "1.8.0",
  "com.alibaba" % "fastjson" % "1.2.56",

  "org.projectlombok" % "lombok" % "1.18.6" % "provided"


)

mainClass in Compile := Some("org.zach.concurrency.HelloActor")
  