package com.example.lysanchen.ieltstest.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lysan Chen on 19/11/2018.
 */

public class Section implements Serializable {

    private int ID;
    private int category;
    private String audio;

    private HashMap<String,Subsection> subsection;


    public Section() {

    }

    public Section(int ID, int category, String audio) {
        this.ID = ID;
        this.category = category;
        this.audio = audio;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }


    public HashMap<String, Subsection> getSubsection() {
        return subsection;
    }

    public void setSubsection(HashMap<String, Subsection> subsection) {
        this.subsection = subsection;
    }
}
