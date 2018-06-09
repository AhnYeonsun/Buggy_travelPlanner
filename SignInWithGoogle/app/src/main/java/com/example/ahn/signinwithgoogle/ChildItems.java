package com.example.ahn.signinwithgoogle;

import java.io.Serializable;

public class ChildItems implements Serializable {
    String planName = "";
    public double planMapX = 0;
    public double planMapY = 0;
    public String planMessage = "";
    public String planDay = "";
    public Plan plan;
    public String address = "";

    public ChildItems(String name, String message){
        this.planName = name;
        this.planMessage = message;
    }

    public ChildItems(Plan plan){
        this.plan = plan;
    }

    public ChildItems(String name, double x, double y, String m, String d) {
        this.planName = name;
        this.planMapX = x;
        this.planMapY = y;
        this.planMessage = m;
        this.planDay = d;
    }

    public String getValue() { return planName; }
    public void setValue(String value) {
        this.planName = value;
    }

    public void setPlanMapX(Double mapX){this.planMapX = mapX;}
    public void setPlanMapY(Double mapY){this.planMapY = mapY;}
    public void setDetailMessage(String message){this.planMessage = message;}
    public void setDay(String day){this.planDay = day;}

    public Double getPlanMapX(){return this.planMapX;}
    public Double getPlanMapY(){return this.planMapY;}
    public String getDetailMessage(){return this.planMessage;}
    public String getDay(){return this.planDay;}
}
