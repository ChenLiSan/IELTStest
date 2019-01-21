package com.example.lysanchen.ieltstest.models;

import java.util.List;

/**
 * Created by Lysan Chen on 16/1/2019.
 */

public class Candidate {

    String name;
    String email;
    String password;
    String id;
    String candidateid;
    List<Attempt> attempt;

    public List<Attempt> getAttempt() {
        return attempt;
    }

    public void setAttempt(List<Attempt> attempt) {
        this.attempt = attempt;
    }

    public Candidate() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Candidate(String name, String email, String password, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateid() {
        return candidateid;
    }

    public void setCandidateid(String candidateid) {
        this.candidateid = candidateid;
    }
}
