package org.whitesneakers.buggy;

import java.util.ArrayList;


public class GroupItem {

    private String title;
    ArrayList<PlanDetailChildItems> arrayList = new ArrayList<>();

    public GroupItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<PlanDetailChildItems> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PlanDetailChildItems> arrayList) {
        this.arrayList = arrayList;
    }
}