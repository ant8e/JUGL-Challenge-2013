import sbt._
import sbt.Keys._
import xerial.sbt.Pack._

object Build extends sbt.Build {

  lazy val root = Project(
    id = "myprog",
    base = file("."),
    settings = Defaults.defaultSettings ++ packSettings ++
      Seq(
        // Map from program name -> Main class (full path)
        packMain := Map("juglchallenge" -> "net.antoinecomte.jugl.challenge2013.Main")
        // Add custom settings here
      )
  )
}