package com.jianqing.Spark.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jianqing_sun on 7/16/17.
 */
public class Email implements Serializable {

    private String from;
    private String to;
    private String subject;
    private Integer emailType;
    private Date date;

    public static Builder builder() {
        return new Builder();
    }

    private Email(String from, String to, String subject, Integer emailType, Date date) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.emailType = emailType;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getEmailType() {
        return emailType;
    }

    public Date getDate() {
        return date;
    }

    public static class Builder {
        private String from;
        private String to;
        private String subject;
        private Integer emailType;
        private Date date;

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder setEmailType(Integer emailType) {
            this.emailType = emailType;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Email build() {
            return new Email(from, to, subject, emailType, date);
        }

    }

}
