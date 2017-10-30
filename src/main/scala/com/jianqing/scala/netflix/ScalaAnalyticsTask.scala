package com.jianqing.scala.netflix

import com.jianqing.netflix.TaskInterface
import org.apache.avro.Schema
import org.apache.spark.sql.SparkSession

/**
  * Created by jianqingsun on 10/29/17.
  */
class ScalaAnalyticsTask extends TaskInterface{
  override def init(): Unit = {}

  override def run(): Int = {

  }

  override def stop(): Int = {
    0
  }

  override def clean(): Unit = {

  }
}
