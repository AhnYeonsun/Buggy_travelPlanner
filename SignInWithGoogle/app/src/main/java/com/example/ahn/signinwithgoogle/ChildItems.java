package com.example.ahn.signinwithgoogle;

/**
 * Created by SOSO on 2018-05-20.
 */
public class ChildItems {
    String planName = "";
    public double planMapX = 0;
    public double planMapY = 0;
    public String planMessage = "";
    public String planDay = "";

    public ChildItems(String name, String message){
        this.planName = name;
        this.planMessage = message;
    }

    public ChildItems(String name, double x, double y, String m, String d) {
        this.planName = name;
        this.planMapX = x;
        this.planMapY = y;
        this.planMessage = m;
        this.planDay = d;
    }

    public String getValue() {
        return planName;

    }

    public void setValue(String value) {
        this.planName = value;
    }
}
