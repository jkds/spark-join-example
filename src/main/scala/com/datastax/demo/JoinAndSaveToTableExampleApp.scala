package com.datastax.demo

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.SparkContext._
import com.datastax.spark.connector._

/**
 * Simple Application that runs Spark locally connecting to a remote C* host
 * obtaining data from two tables and joining them on a specified predicate key
 */
object JoinAndSaveToTableExampleApp extends App with ArgParser {

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
  val org = sc.cassandraTable("datastax","orgchart").select("personnel_id","org_name")

  val dirKeyedByPersonnelId : RDD[(String,CassandraRow)] = directory.keyBy(row => row.getString("personnel_id"))
  val orgKeyedByPersonnelId : RDD[(String,CassandraRow)] = org.keyBy(row => row.getString("personnel_id"))

  //Join the tables based on their personnel id and map the results to a formatted string.
  val joined = dirKeyedByPersonnelId.join(orgKeyedByPersonnelId).sortByKey().map { joinedRow =>
    //Note the output doesn't require for org_row it was only there for joining...
    val (personnel_id, (per_row,org_row)) = joinedRow
    (per_row.getInt("personnel_id"),
      per_row.getString("first_name"),
      per_row.getString("last_name"),
      org_row.getString("org_name"))
  }
  //Save to a pre-defined Cassandra table
  joined.saveToCassandra("datastax", "org_directory", SomeColumns("personnel_id","fname","lname","role"))
  //Shut down the context
  sc.stop()

}
