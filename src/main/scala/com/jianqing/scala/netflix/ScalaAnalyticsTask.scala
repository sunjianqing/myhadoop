package com.jianqing.scala.netflix

import com.jianqing.netflix.TaskInterface
import org.apache.parquet.hadoop.metadata.CompressionCodecName
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jianqing_sun on 11/2/17.
  */
class ScalaAnalyticsTask extends TaskInterface{
  override def init(): Unit = ???

  override def run(): Int = {
    val sc = new SparkContext(new SparkConf().setAppName("Analytics Task").setMaster("local[*]"))
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    sqlContext.setConf("spark.sql.parquet.compression.codec", CompressionCodecName.UNCOMPRESSED.name())
    val df = sqlContext.read.parquet("/tmp/movieexport")
    df.registerTempTable("movie")

    sqlContext.sql("select * from movie").collect.foreach(println)

    0
  }

  override def stop(): Int = ???

  override def clean(): Unit = ???
}


object Mainn{
  def main(args: Array[String]): Unit = {
    val task = new ScalaAnalyticsTask()
    task.run()
  }
}
