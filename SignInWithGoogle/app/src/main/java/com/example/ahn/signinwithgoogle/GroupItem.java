package com.example.ahn.signinwithgoogle;

import android.util.Log;

import java.util.ArrayList;


public class GroupItem {

    private String title;
    ArrayList<ChildItems> arrayList = new ArrayList<>();

    public GroupItem(String title) {
        this.title = title;
        Log.d("GGGTTT",title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ChildItems> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ChildItems> arrayList) {
        this.arrayList = arrayList;
    }
}