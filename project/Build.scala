import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-form-kludge"
  val appVersion      = "1.0-SNAPSHOT"

   val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.webjars" % "webjars-play" % "2.1.0",
    
    // Downgrade to JQuery 1.8.3 so that integration tests with HtmlUnit work.
    "org.webjars" % "bootstrap" % "2.3.2" exclude("org.webjars", "jquery"),
    "org.webjars" % "jquery" % "1.8.3",
      
    "org.webjars" % "bootswatch" % "2.3.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
