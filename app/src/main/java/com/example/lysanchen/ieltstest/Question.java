package com.example.lysanchen.ieltstest;

import java.io.Serializable;

/**
 * Created by Lysan Chen on 19/11/2018.
 */

public class Question implements Serializable {

    private int id;
    private String questionText;
    private String answerText;
    private String chosenAnswer;
    private int sequence;
    private String answerOptions;

    public Question() {
    }

    public Question(int id, String questionText, String answerText, int sequence, String answerOptions) {
        this.id = id;
        this.questionText = questionText;
        this.answerText = answerText;
        this.sequence = sequence;
        this.answerOptions = answerOptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(String answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }
}
