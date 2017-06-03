name := "datateam-challenge"

version := "1.0"

scalaVersion := "2.11.1"


//FIXME:Please do a publish local to resolve this dependency
//libraryDependencies += "net.debasishg" %% "redisreact" % "0.9"
libraryDependencies += "net.debasishg" %% "redisclient" % "3.3"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "com.typesafe.akka" % "akka-testkit_2.11" % "2.3.9"

libraryDependencies ++= Seq(
  // -- Spray --
    "io.spray" %% "spray-routing" % "1.3.1"
  , "io.spray" %% "spray-can" % "1.3.1"
  , "io.spray" %% "spray-httpx" % "1.3.1"
)
