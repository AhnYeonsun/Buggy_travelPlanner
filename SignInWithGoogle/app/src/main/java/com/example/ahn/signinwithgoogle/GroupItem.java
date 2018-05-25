package com.example.ahn.signinwithgoogle;

import java.util.ArrayList;

/**
 * Created by SOSO on 2018-05-20.
 */

public class GroupItem {

    String title;
    ArrayList<ChildItems> arrayList = new ArrayList<>();

    public GroupItem(String title) {
        this.title = title;
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
