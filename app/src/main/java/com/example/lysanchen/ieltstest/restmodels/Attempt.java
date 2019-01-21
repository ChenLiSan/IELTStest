
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Attempt {

    @SerializedName("candidate")
    @Expose
    private Candidate candidate;
    @SerializedName("paper")
    @Expose
    private Paper paper;
    @SerializedName("session")
    @Expose
    private Session session;
    @SerializedName("attemptID")
    @Expose
    private Integer attemptID;
    @SerializedName("record")
    @Expose
    private String record;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("grade")
    @Expose
    private String grade;

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Integer getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(Integer attemptID) {
        this.attemptID = attemptID;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }



}
