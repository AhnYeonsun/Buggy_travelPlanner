package com.example.ahn.signinwithgoogle;

public class Checklist_item {
    private String text = "";
    private  int isChecked = 0;

    public void setText(String text){
        this.text = text;
    }
    public String getText(){return this.text;}

    public void setIsChecked(int isChecked){ this.isChecked = isChecked;}
    public int getIsChecked(){return this.isChecked;}
}

