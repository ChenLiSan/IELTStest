package com.example.lysanchen.ieltstest.models;

/**
 * Created by Lysan Chen on 17/1/2019.
 */

public class Attempt {
    String attemptid;
    String grade;
    int score;
    String log;
    String paper;
    String record;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Attempt() {
    }

    public Attempt(String attemptid, String grade, int score, String log, String paper) {
        this.attemptid = attemptid;
        this.grade = grade;
        this.score = score;
        this.log = log;
        this.paper = paper;
    }

    public String getAttemptid() {
        return attemptid;
    }

    public void setAttemptid(String attemptid) {
        this.attemptid = attemptid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }
}
