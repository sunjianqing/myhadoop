package com.jianqing.Spark;

import com.jianqing.Spark.base.Email;
import com.jianqing.Spark.util.EmailMapFunc;
import com.jianqing.Spark.util.SumFunction;
import com.jianqing.Spark.util.Util;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jianqing_sun on 7/11/17.
 *
 * Assignment 1
 * The code is trying to read all txt files and parse emails.
 * Then code is running a SQL-Like logic
 * select To_Email, Date, Sum(*) from Table Group By To_Email, Date
 */
public class EmailDateCnt implements Serializable {

    public static void main(String[] args) {
        EmailDateCnt wc = new EmailDateCnt();
        wc.run(args[0], args[1]);
    }

    public void run(String dataPath, String outputPath) {
        SparkConf conf = new SparkConf().setAppName("Email Date Cnt App");

        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.hadoopConfiguration().set("mapreduce.input.fileinputformat.input.dir.recursive", "true");
        JavaRDD<String> input = sc.wholeTextFiles(dataPath).values();

        //<From, To, Subject, Date>
        JavaRDD<Email> emailRDD = input.flatMap(new EmailMapFunc());

        //<Email> -> <<To, Date>, 1>
        JavaPairRDD<Tuple2<String, String>, Integer> toEmailDateCntPairRDD = emailRDD.mapToPair(new ToEmailDatePairFunction());

        JavaPairRDD<Tuple2<String, String>, Integer> toEmailDateCntPairTotalRDD = toEmailDateCntPairRDD.reduceByKey(new SumFunction());

        // <To, Date, Cnt>
        JavaRDD<String> res = toEmailDateCntPairTotalRDD.map(new EmailDateCntOutputFunction());

        res.saveAsTextFile(outputPath);
    }

}

class ToEmailDatePairFunction implements PairFunction<Email, Tuple2<String, String>, Integer> {

    @Override
    public Tuple2<Tuple2<String, String>, Integer> call(Email email) throws Exception {
        Date d = email.getDate();
        String dateStr =  Util.YEAR_FORMAT.format(d) + "-" + Util.MONTH_FORMAT.format(d) + "-" + Util.DAY_FORMAT.format(d);
        return new Tuple2<>(new Tuple2<>(email.getTo(), dateStr), Integer.valueOf(1));
    }
}

class EmailDateCntOutputFunction implements Function<Tuple2<Tuple2<String, String>, Integer>, String> {

    @Override
    public String call(Tuple2<Tuple2<String, String>, Integer> v) throws Exception {
        return v._1()._1() + "," + v._1()._2() + "," + v._2();
    }
}
