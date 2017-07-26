package com.jianqing.Spark.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianqing_sun on 7/11/17.
 */
public class Util {

    public static final String FROM_PATTERN = "^From:\\s+(.*)";

    public static final String TO_PATTERN = "^To:\\s+(.*)";

    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+)$";

    public static final String DATE_PATTERN = "^Date:\\s+?(.*)";

    public static final String SUBJECT_PATTERN = "^Subject:\\s+?(.*)";


    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z '('Z')'";
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
    public static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd");
    public static final int INVALID_EMAIL = -1;
    public static final int DIRECT_EMAIL = 1;
    public static final int BROADCAST_EMAIL = 2;

    public static String getFrom(String input) {
        String s = doMatch(input, FROM_PATTERN);
        if (StringUtils.isNotEmpty(s)) {
            String email = doMatch(s, EMAIL_PATTERN);
            return email;
        }
        return null;
    }

    public static Set<String> getTo(String input) {
        String toEmails = doMatch(input, TO_PATTERN);
        if (StringUtils.isNotEmpty(toEmails)) {
            String[] splits = toEmails.split(",");
            Set<String> emailSet = new HashSet<>();
            for (String s : splits) {
                String email = doMatch(s, EMAIL_PATTERN);
                if (StringUtils.isNotEmpty(email)) {
                    emailSet.add(email);
                }
            }
            return emailSet;
        } else {
            return null;
        }
    }

    public static String getSubject(String input) {
        String s = doMatch(input, SUBJECT_PATTERN);
        if (StringUtils.isNotEmpty(s)) {
            return s;
        }
        return null;
    }

    public static Date getDate(String input) {
        String s = doMatch(input, DATE_PATTERN);
        if (s == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        try {
            Date d = sdf.parse(s);
            return d;
        } catch (Exception e) {
            System.out.println("Got Exception in parsing " + s);
        }
        return null;
    }

    private static String doMatch(String input, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(input.trim());
        String res = null;
        while (matcher.find()) {
            res = matcher.group(1).trim();
        }

        return res;
    }

    public static void writeoutResult(List<String> res, Configuration conf, String outputPath, String subOutputPath) throws IOException {
        if (res != null && !res.isEmpty()) {
            FileSystem fileSystem = FileSystem.get(conf);

            Path path = new Path(outputPath);

            if(StringUtils.isNotEmpty(subOutputPath)){
                if(!fileSystem.exists(path)){
                    fileSystem.mkdirs(path);
                }
                path = new Path(outputPath + "/" + subOutputPath);
            }

            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fileSystem.create(path)));
            for (String output : res) {
                br.write(output);
                br.write('\n');
            }
            br.close();
            fileSystem.close();
        }
    }

}
