
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Candidate {

    @SerializedName("candidateID")
    @Expose
    private Integer candidateID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("studentID")
    @Expose
    private String studentID;
    @SerializedName("gmailID")
    @Expose
    private String gmailID;
    @SerializedName("eligibility")
    @Expose
    private String eligibility;
    @SerializedName("token")
    @Expose
    private String token;

    public Integer getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(Integer candidateID) {
        this.candidateID = candidateID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getGmailID() {
        return gmailID;
    }

    public void setGmailID(String gmailID) {
        this.gmailID = gmailID;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
