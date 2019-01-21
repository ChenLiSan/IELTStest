package com.example.lysanchen.ieltstest.models;

import java.io.Serializable;

/**
 * Created by Lysan Chen on 19/11/2018.
 */

public class Paper implements Serializable {

    private int ID;
    private Section section1;
    private Section section2;
    private Section section3;
    private Section section4;

    public Paper() {
    }

    public Paper(int ID, Section section1, Section section2, Section section3, Section section4) {
        this.ID = ID;
        this.section1 = section1;
        this.section2 = section2;
        this.section3 = section3;
        this.section4 = section4;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Section getSection1() {
        return section1;
    }

    public void setSection1(Section section1) {
        this.section1 = section1;
    }

    public Section getSection2() {
        return section2;
    }

    public void setSection2(Section section2) {
        this.section2 = section2;
    }

    public Section getSection3() {
        return section3;
    }

    public void setSection3(Section section3) {
        this.section3 = section3;
    }

    public Section getSection4() {
        return section4;
    }

    public void setSection4(Section section4) {
        this.section4 = section4;
    }
}
