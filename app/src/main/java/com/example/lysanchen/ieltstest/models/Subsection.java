package com.example.lysanchen.ieltstest.models;

import com.example.lysanchen.ieltstest.Question;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lysan Chen on 19/11/2018.
 */

public class Subsection implements Serializable{

    private int ID;
    @PropertyName("totalQuestion")
    private int totalQuestion;
    private String typeofQuestion;
    private String image;
    private String sectionText;
    private String imageurl;
    private String imageStr;
    private HashMap<String, Question> question;

    public Subsection() {

    }

    public Subsection(int ID, int totalQuestion, String typeofQuestion, String image, String sectionText) {
        this.ID = ID;
        this.totalQuestion = totalQuestion;
        this.typeofQuestion = typeofQuestion;
        this.image = image;
        this.sectionText = sectionText;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestions) {
        this.totalQuestion = totalQuestion;
    }

    public String getTypeofQuestion() {
        return typeofQuestion;
    }

    public void setTypeofQuestion(String typeofQuestion) {
        this.typeofQuestion = typeofQuestion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    public String getImageStr() {
        return imageStr;
    }

    public void setImageStr(String imageStr) {
        this.imageStr = imageStr;
    }

    public HashMap<String,Question> getQuestion() {
        return question;
    }


    public Subsection(int ID, int totalQuestion, String typeofQuestion, String image, String sectionText, String imageurl) {
        this.ID = ID;
        this.totalQuestion = totalQuestion;
        this.typeofQuestion = typeofQuestion;
        this.image = image;
        this.sectionText = sectionText;
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setQuestion(HashMap<String,Question> question) {
        this.question = question;
    }
}
