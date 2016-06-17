name := "Reporter"

version := "1.0"

lazy val `reporter` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6"

libraryDependencies ++= Seq("org.apache.poi" % "poi" % "3.13",
                            "org.apache.poi" % "poi-ooxml" % "3.13")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  