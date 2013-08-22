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
    //"com.dadrox" % "scuttle_2.10" % "0.3"
    "com.codahale.metrics" % "metrics-core" % "3.0.1"

    //"com.yammer.metrics" % "metrics-core" % "2.2.0",
    //"nl.grons" %% "metrics-scala" % "2.2.0",
    //"com.github.sdb" %% "play2-metrics" % "0.1.0"
  )


  /*
  <dependencies>
    <dependency>
        <groupId>com.codahale.metrics</groupId>
        <artifactId>metrics-core</artifactId>
        <version>3.0.1</version>
    </dependency>
</dependencies>
   */

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
