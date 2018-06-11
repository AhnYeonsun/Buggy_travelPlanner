package org.whitesneakers.buggy;

/**
 * Created by ahn on 2018-05-31.
 */

public class GetDaysForTravel {

    public static String[] p;
    public static String title;

    public GetDaysForTravel(){

    }
    public GetDaysForTravel(String[] daysOfNewPlan){
        this.p = daysOfNewPlan;
    }
    public GetDaysForTravel(String planTitle){
        this.title = planTitle;
    }
    public String[] getPD(){
        return p;
    }
    public String getTitle(){
        return title;
    }
}
