	 

name := "JUGL 2013 challenge"

version := "1.0"

scalaVersion := "2.10.0"


resolvers ++= Seq(
  "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
   "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= Seq ( "io.spray" % "spray-can" % "1.0-M7",
							 "io.spray" % "spray-routing" % "1.0-M7", 
							 "io.spray" %% "spray-json" % "1.2.3",
							 "com.typesafe.akka" %   "akka-actor" % "2.0.3" ,
							 "org.scalatest" %% "scalatest" % "1.9.1" % "test")



scalaVersion := "2.9.2"

scalacOptions += "-Ydependent-method-types"

packageOptions in (Compile, packageBin) +=
    Package.ManifestAttributes( java.util.jar.Attributes.Name.CLASS_PATH -> "akka-actor-2.0.3.jar config-0.3.1.jar mimepull-1.8.jar parboiled-core-1.1.4.jar parboiled-scala_2.9.2-1.1.4.jar scala-library.jar shapeless_2.9.2-1.2.3.jar spray-can-1.0-M7.jar spray-http-1.0-M7.jar spray-httpx-1.0-M7.jar spray-io-1.0-M7.jar spray-routing-1.0-M7.jar spray-util-1.0-M7.jar spray-json_2.9.2-1.2.3.jar" )
