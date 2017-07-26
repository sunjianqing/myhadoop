package com.jianqing.Spark.util;

import com.jianqing.Spark.base.Email;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by jianqing_sun on 7/15/17.
 * This class is used to parse email content line by line and generate a tuple which could be used for every assignment.
 */
public class EmailMapFunc implements FlatMapFunction<String, Email> {

    @Override
    public Iterator<Email> call(String contents) throws Exception {
        Set<Email> res = new HashSet();
        String[] lines = contents.split("\n");

        Set<String> toEmails = new HashSet<>();
        String fromEmail = "";
        String subject = "";
        Date date = null;
        int emailType = Util.INVALID_EMAIL;

        for (int i = 0 ; i < lines.length; i++ ) {
            String line = lines[i];
            if (StringUtils.isNotEmpty(fromEmail) && StringUtils.isNotEmpty(subject) && !toEmails.isEmpty() && date != null) {
                break;
            }

            String parseStr = Util.getFrom(line);
            if (StringUtils.isNotEmpty(parseStr)) {
                fromEmail = parseStr;
                continue;
            }

            Set<String> toSet = Util.getTo(line);
            boolean parsedEmail = false;
            // To emails might have multiple lines
            while(toSet != null && !toSet.isEmpty()){
                parsedEmail = true;
                toEmails.addAll(toSet);
                toSet = Util.getTo("To: " + lines[++i].trim());
            }
            if(parsedEmail){
                i--;
                continue;
            }


            String subjectStr = Util.getSubject(line);
            if (StringUtils.isNotEmpty(subjectStr)) {
                subject = subjectStr;
                continue;
            }

            Date d = Util.getDate(line);
            if (d != null) {
                date = d;
                continue;
            }
        }

        if (toEmails.size() > 1) {
            emailType = Util.BROADCAST_EMAIL;
        }
        if (toEmails.size() == 1) {
            emailType = Util.DIRECT_EMAIL;
        }

        for (String toEmail : toEmails) {
            Email e = Email.builder()
                    .setFrom(fromEmail.trim())
                    .setTo(toEmail.trim())
                    .setSubject(subject.trim())
                    .setDate(date)
                    .setEmailType(emailType)
                    .build();
            res.add(e);
        }
        return res.iterator();
    }
}
