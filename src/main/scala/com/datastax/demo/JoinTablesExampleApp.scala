package com.datastax.demo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.SparkContext._
import com.datastax.spark.connector._

import scala.util.Try


/**
 * Simple Application that runs Spark locally connecting to a remote C* host
 * obtaining data from two tables and joining them on a specified predicate key
 */
object JoinTablesExampleApp extends App with ArgParser {

  //Create Spark config limiting both the number of cores and the memory use otherwise
  //Spark will consume everything
  val conf = new SparkConf()
    .setMaster(parsedArgs.getOrElse("sparkmaster","local[2]"))
    .setAppName("join-example")
    .set("spark.executor.memory","512m")
    .set("spark.default.parallelism","2")
    .set("spark.cassandra.connection.host", parsedArgs.getOrElse("cassandrahost","127.0.0.1"))

  val sc = new SparkContext(conf)

  val directory = sc.cassandraTable("datastax", "directory").select("personnel_id","first_name","last_name")
  val org = sc.cassandraTable("datastax","orgchart").select("personnel_id","org_name").filter(_.getString("org_name") == "Executive Team")

  val dirKeyedByPersonnelId : RDD[(String,CassandraRow)] = directory.keyBy(row => row.getString("personnel_id"))
  val orgKeyedByPersonnelId : RDD[(String,CassandraRow)] = org.keyBy(row => row.getString("personnel_id"))

  //Join the tables based on their personnel id and map the results to a formatted string.
  val joined = dirKeyedByPersonnelId.join(orgKeyedByPersonnelId).sortByKey().map { joined =>
    //Note the output doesn't require for org_row it was only there for joining...
    val (personnel_id, (personnel_row,org_row)) = joined
    s"Executive -> Personnel ID = ${personnel_row.getString("personnel_id")}, Name = ${personnel_row.getString("first_name")} ${personnel_row.getString("last_name")}"
  }.toArray.mkString("\n")
  //Print to stdout
  println(joined)
  //Shutdown the context
  sc.stop()

}
