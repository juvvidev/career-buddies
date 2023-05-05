package com.juvvi.careerbuddies;

import com.google.firebase.Timestamp;

public class Question {
    String question;
    String tag;
    Timestamp timestamp;
    String userid;

    public Question() {
    }

    public Question(String question, String tag, Timestamp timestamp, String userid) {
        this.question = question;
        this.tag = tag;
        this.timestamp = timestamp;
        this.userid = userid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", tag='" + tag + '\'' +
                ", timestamp=" + timestamp +
                ", userid='" + userid + '\'' +
                '}';
    }
}
