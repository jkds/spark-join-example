name := "spark-join-example"

version := "0.1"

scalaVersion := "2.10.4"

val SparkVersion = "1.1.0"
val SparkCassandraVersion = "1.1.0"

libraryDependencies ++= Seq(
  ("org.apache.spark" %%  "spark-core"  % SparkVersion % "provided").
    exclude("org.eclipse.jetty.orbit", "javax.transaction").
    exclude("org.eclipse.jetty.orbit", "javax.mail").
    exclude("org.eclipse.jetty.orbit", "javax.activation").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("commons-collections", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog")
)

libraryDependencies ++= Seq(
  "com.datastax.spark"  %%  "spark-cassandra-connector"               % SparkCassandraVersion
)

