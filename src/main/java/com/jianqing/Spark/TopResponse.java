package com.jianqing.Spark;

import com.jianqing.Spark.base.Email;
import com.jianqing.Spark.util.EmailMapFunc;
import com.jianqing.Spark.util.LongComparator;
import com.jianqing.Spark.util.Util;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import scala.Tuple3;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by jianqing_sun on 7/12/17.
 *
 * Assignment 3
 * The code is trying to read all txt files and parse emails.
 * Then it runs a SQL-Like self join with Java Spark code.
 *
 * Select tbl1.from, tbl1.to, ( tbl2.date - tbl1.date ) as response_time from table as tbl1
 * join table as tbl2 on tbl1.from = tbl2.to and tbl1.to = tbl2.from
 * where tbl1.date < tbl2.date and tbl2.subject.contains(tbl1.subject)
 * order by response_time asc limit 5
 */
public class TopResponse implements Serializable {

    public static void main(String[] args) throws IOException {
        TopResponse topResponse = new TopResponse();
        topResponse.run(args[0], args[1]);
    }

    public void run(String dataPath, String outputPath) throws IOException {
        SparkConf conf = new SparkConf().setAppName("Email Top Response App");

//        conf.setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.hadoopConfiguration().set("mapreduce.input.fileinputformat.input.dir.recursive", "true");
        JavaRDD<String> input = sc.wholeTextFiles(dataPath).values();

        //<<FromEmail, ToEmail>, <Subject, Date>>
        JavaPairRDD<Tuple2<String, String>, Tuple2<String, Date>> fromToSubjectDateRDD = input.flatMap(new EmailMapFunc())
                .mapToPair(new FromToSbjDatePairFunction());

        //<<ToEmail, FromEmail>, <Subject, Date>>
        JavaPairRDD<Tuple2<String, String>, Tuple2<String, Date>> toFromSubjectDateRDD = fromToSubjectDateRDD.mapToPair(new ToFromSbjDatePairFunction());

        JavaPairRDD<Tuple2<String, String>, Tuple2<Tuple2<String, Date>, Tuple2<String, Date>>> joinRDD = fromToSubjectDateRDD.join(toFromSubjectDateRDD);

        JavaRDD<Tuple3<String, String, Long>> tuple3JavaRDD = joinRDD.flatMap(new SubjectMatchFlatMapFunction());

        //<From, To, ResponseTime>
        List<Tuple3<String, String, Long>> topRes = tuple3JavaRDD.top(5, new LongComparator());

        Util.writeoutResult(buildOutput(topRes), sc.hadoopConfiguration(), outputPath, null);

    }

    private List<String> buildOutput(List<Tuple3<String, String, Long>> topRes) {
        List<String> res = new ArrayList<>();
        for (Tuple3<String, String, Long> tuple3 : topRes) {
            res.add(tuple3._1() + "," + tuple3._2() + "," + tuple3._3() / 1000);
        }
        return res;
    }
}


class FromToSbjDatePairFunction implements PairFunction<Email, Tuple2<String, String>, Tuple2<String, Date>> {

    @Override
    public Tuple2<Tuple2<String, String>, Tuple2<String, Date>> call(Email email) throws Exception {
        return new Tuple2<>(new Tuple2<>(email.getFrom(), email.getTo()), new Tuple2<>(email.getSubject(), email.getDate()));
    }
}


class ToFromSbjDatePairFunction implements PairFunction<Tuple2<Tuple2<String, String>, Tuple2<String, Date>>, Tuple2<String, String>, Tuple2<String, Date>> {

    @Override
    public Tuple2<Tuple2<String, String>, Tuple2<String, Date>> call(Tuple2<Tuple2<String, String>, Tuple2<String, Date>> v) throws Exception {
        return new Tuple2<>(new Tuple2<>(v._1()._2(), v._1()._1()), new Tuple2<>(v._2()._1(), v._2()._2()));
    }
}

class SubjectMatchFlatMapFunction implements FlatMapFunction<Tuple2<Tuple2<String, String>, Tuple2<Tuple2<String, Date>, Tuple2<String, Date>>>, Tuple3<String, String, Long>> {

    @Override
    public Iterator<Tuple3<String, String, Long>> call(Tuple2<Tuple2<String, String>, Tuple2<Tuple2<String, Date>, Tuple2<String, Date>>> v) throws Exception {
        Set<Tuple3<String, String, Long>> fromToDateSet = new HashSet<>();

        Date fromDate = v._2()._1()._2();
        Date toDate = v._2()._2()._2();

        String fromSubject = v._2()._1()._1().toLowerCase();
        String toSubject = v._2()._2()._1().toLowerCase();

        if (toDate.getTime() > fromDate.getTime() && toSubject.contains(fromSubject)) {
            fromToDateSet.add(new Tuple3<>(v._1()._1(), v._1()._2(), toDate.getTime() - fromDate.getTime()));
        }

        return fromToDateSet.iterator();
    }
}


