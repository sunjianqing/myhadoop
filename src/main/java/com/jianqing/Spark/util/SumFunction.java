package com.jianqing.Spark.util;

import org.apache.spark.api.java.function.Function2;

/**
 * Created by jianqing_sun on 7/11/17.
 */
public class SumFunction implements Function2<Integer, Integer, Integer> {

    @Override
    public Integer call(Integer v1, Integer v2) throws Exception {
        return v1 + v2;
    }
}
