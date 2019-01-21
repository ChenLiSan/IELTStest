
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Paper {

    @SerializedName("paperID")
    @Expose
    private Integer paperID;
    @SerializedName("section")
    @Expose
    private String section;

    public Integer getPaperID() {
        return paperID;
    }

    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }



}
