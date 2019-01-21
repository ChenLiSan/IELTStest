
package com.example.lysanchen.ieltstest.restmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("subsection")
    @Expose
    private Subsection subsection;
    @SerializedName("questionID")
    @Expose
    private Integer questionID;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("answerText")
    @Expose
    private String answerText;
    @SerializedName("sequence")
    @Expose
    private Integer sequence;
    @SerializedName("answerOptions")
    @Expose
    private String answerOptions;

    public Subsection getSubsection() {
        return subsection;
    }

    public void setSubsection(Subsection subsection) {
        this.subsection = subsection;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(String answerOptions) {
        this.answerOptions = answerOptions;
    }

}
