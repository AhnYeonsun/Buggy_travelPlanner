package org.whitesneakers.buggy;


public class Checklist_item {
    public String list = "";
    public int check = 0;

    public Checklist_item(){}
    public Checklist_item(String list, int check){
        this.list = list;
        this.check = check;
    }

    //    //get set TEXT
    public void setText(String list){
        this.list = list;
    }
    //    public String getText(){
//        return this.list;
//    }
//
//    //get set isCHECKED
    public void setIsChecked(int check){
        this.check = check;
    }
    public int getC(){
        return this.check;
    }
}
