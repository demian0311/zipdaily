import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "zipdaily"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    //jdbc,
    //anorm,
    "com.dadrox" % "scuttle_2.10" % "0.3"
  )
  /*
  <dependency>
  <groupId>com.dadrox</groupId>
  <artifactId>scuttle_2.10</artifactId>
  <version>0.3</version>
</dependency>
   */


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
