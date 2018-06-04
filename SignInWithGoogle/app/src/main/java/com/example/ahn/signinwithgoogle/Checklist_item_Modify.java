package com.example.ahn.signinwithgoogle;

public class Checklist_item_Modify {
    private String text = "";
    private int ischk = 0;

    public void setText(String text){
        this.text = text;
    }
    public String getText(){return this.text;}

    public void setCheck(int ischk){this.ischk = ischk;}
    public int getCheck(){return this.ischk;}
}
