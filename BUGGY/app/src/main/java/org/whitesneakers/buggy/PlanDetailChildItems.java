package org.whitesneakers.buggy;

import java.io.Serializable;

public class PlanDetailChildItems implements Serializable {
    String planName = "";
    public double planMapX = 0;
    public double planMapY = 0;
    public String planMessage = "";
    public String planDay = "";
    public Plan plan;
    public String address = "";

    public PlanDetailChildItems(String name, String message){
        this.planName = name;
        this.planMessage = message;
    }

    public PlanDetailChildItems(Plan plan){
        this.plan = plan;
    }

    public PlanDetailChildItems(String name, double x, double y, String m, String d, String a) {
        this.planName = name;
        this.planMapX = x;
        this.planMapY = y;
        this.planMessage = m;
        this.planDay = d;
        this.address = a;
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
    public String getAddress(){return this.address;}
    public String getDay(){return this.planDay;}
}
