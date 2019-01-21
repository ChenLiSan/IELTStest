
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("sessionID")
    @Expose
    private Integer sessionID;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("validPeriod")
    @Expose
    private String validPeriod;

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }


}
