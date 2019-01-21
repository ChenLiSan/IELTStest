package com.example.lysanchen.ieltstest.models;

/**
 * Created by Lysan Chen on 17/1/2019.
 */

public class Session {
    String date;
    String sessionid;
    String startTime;
    String validTime;

    public Session() {
    }

    public Session(String date, String sessionid, String startTime, String validTime) {
        this.date = date;
        this.sessionid = sessionid;
        this.startTime = startTime;
        this.validTime = validTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
}
