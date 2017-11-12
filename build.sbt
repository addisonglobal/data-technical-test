name := "data_assessment"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation")

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += "junit" % "junit" % "4.10" % "test"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.0"
)

