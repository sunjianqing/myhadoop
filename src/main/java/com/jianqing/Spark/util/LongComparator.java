package com.jianqing.Spark.util;


import scala.Tuple3;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by jianqing_sun on 7/11/17.
 */
public class LongComparator implements Comparator<Tuple3<String, String, Long>>, Serializable {

    @Override
    public int compare(Tuple3<String, String, Long> o1, Tuple3<String, String, Long> o2) {
        return Long.compare(o2._3(), o1._3());
    }
}
