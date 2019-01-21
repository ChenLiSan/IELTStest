
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subsection {

    @SerializedName("section")
    @Expose
    private Section section;
    @SerializedName("subsectionID")
    @Expose
    private Integer subsectionID;
    @SerializedName("totalQuestions")
    @Expose
    private Integer totalQuestions;
    @SerializedName("typeOfQuestion")
    @Expose
    private String typeOfQuestion;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("sectionText")
    @Expose
    private String sectionText;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Integer getSubsectionID() {
        return subsectionID;
    }

    public void setSubsectionID(Integer subsectionID) {
        this.subsectionID = subsectionID;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    public Object getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
