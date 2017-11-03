package com.jianqing.netflix;

import com.jianqing.scala.netflix.ScalaAnalyticsTask;

/**
 * Created by jianqing_sun on 11/2/17.
 */
public class Main {
    public static void main(String[] args) {
        TaskInterface webScrapingTask = new WebScrapingTask();
        webScrapingTask.init();
        webScrapingTask.run();

//        ExportTask exportTask = new ExportTask();
//        exportTask.init();
//        exportTask.run();
//
//        ScalaAnalyticsTask scalaAnalyticsTask = new ScalaAnalyticsTask();
//        scalaAnalyticsTask.init();
//        scalaAnalyticsTask.run();
    }
}
