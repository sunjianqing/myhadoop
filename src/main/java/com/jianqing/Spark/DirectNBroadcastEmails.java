package com.jianqing.Spark;

import com.jianqing.Spark.base.Email;
import com.jianqing.Spark.util.EmailMapFunc;
import com.jianqing.Spark.util.SumFunction;
import com.jianqing.Spark.util.Util;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jianqing_sun on 7/11/17.
 * <p>
 * Assignment 2
 * <p>
 * The code is trying to read all txt files and parse emails.
 * Then create a RDD like <Email, Type>
 * Then it runs a SQL-Like Java Spark code
 * select email, sum(*) as total from table where type = "#type#" group by email order by total desc limit 5
 */
public class DirectNBroadcastEmails implements Serializable {
    private static final String DIRECT = "direct";
    private static final String BROADCAST = "broadcast";

    public static void main(String[] args) throws IOException {
        DirectNBroadcastEmails wc = new DirectNBroadcastEmails();
        wc.run(args[0], args[1]);
    }

    public void run(String dataPath, String outputPath) throws IOException {
        SparkConf conf = new SparkConf().setAppName("Email Direct and Broadcast App");

        //conf.setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.hadoopConfiguration().set("mapreduce.input.fileinputformat.input.dir.recursive", "true");
        JavaRDD<String> input = sc.wholeTextFiles(dataPath).values();

        JavaRDD<Email> emailRDD = input.flatMap(new EmailMapFunc());

        List<Tuple2<String, Integer>> broadcastRes = emailRDD.filter(new BroadCastFunction())
                .mapToPair(new EmailFromPairFunction())
                .reduceByKey(new SumFunction())
                .top(1, new EmailComparator());
        List<Tuple2<String, Integer>> directRes = emailRDD.filter(new DirectFunction())
                .mapToPair(new EmailToPairFunction())
                .reduceByKey(new SumFunction())
                .top(1, new EmailComparator());

        // Output result
        Util.writeoutResult(buildOutput(broadcastRes), sc.hadoopConfiguration(), outputPath, BROADCAST);
        Util.writeoutResult(buildOutput(directRes), sc.hadoopConfiguration(), outputPath, DIRECT);
    }

    private List<String> buildOutput(List<Tuple2<String, Integer>> list) {
        List<String> res = new ArrayList<>();
        for (Tuple2 tuple2 : list) {
            res.add(tuple2._1() + "," + tuple2._2());
        }
        return res;
    }
}

class BroadCastFunction implements Function<Email, Boolean> {

    @Override
    public Boolean call(Email email) throws Exception {
        return email.getEmailType() == Util.BROADCAST_EMAIL;
    }
}

class DirectFunction implements Function<Email, Boolean> {
    @Override
    public Boolean call(Email email) throws Exception {
        return email.getEmailType() == Util.DIRECT_EMAIL;
    }
}

class EmailFromPairFunction implements PairFunction<Email, String, Integer> {

    @Override
    public Tuple2<String, Integer> call(Email email) throws Exception {
        return new Tuple2<>(email.getFrom(), Integer.valueOf(1));
    }
}

class EmailToPairFunction implements PairFunction<Email, String, Integer> {

    @Override
    public Tuple2<String, Integer> call(Email email) throws Exception {
        return new Tuple2<>(email.getTo(), Integer.valueOf(1));
    }
}

class EmailComparator implements Comparator<Tuple2<String, Integer>>, Serializable {

    @Override
    public int compare(Tuple2<String, Integer> o1, Tuple2<String, Integer> o2) {
        return o1._2 - o2._2;
    }
}


