package com.example.slat_2;

import java.util.ArrayList;

class Item {

    private ArrayList<String> titlestr;
    private ArrayList<String> descriptionstr;
    private String subjectstr;

    public Item(ArrayList<String> titlestr, ArrayList<String> descriptionstr, String subjectstr) {
        this.titlestr = titlestr;
        this.descriptionstr = descriptionstr;
        this.subjectstr = subjectstr;
    }
    public Item(){

    }

    public ArrayList<String> getTitlestr() {return titlestr;}

    public ArrayList<String> getDescriptionstr() {return descriptionstr;}

    public String getSubjectstr() {return subjectstr;}

}
