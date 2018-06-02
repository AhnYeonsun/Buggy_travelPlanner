package com.example.ahn.signinwithgoogle;

public class Plan {
    public String title;
    public String address;
    public double mapX;
    public double mapY;
    public String message;
    public String Day;

    Plan(String t, String a, double x, double y, String m){
        this.title = t;
        this.address = a;
        this.mapX = x;
        this.mapY = y;
        this.message = m;
    }
}
