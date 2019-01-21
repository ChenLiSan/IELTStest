
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    @SerializedName("sectionID")
    @Expose
    private Integer sectionID;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("setter")
    @Expose
    private Object setter;

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Object getSetter() {
        return setter;
    }

    public void setSetter(Object setter) {
        this.setter = setter;
    }

}
