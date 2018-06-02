package com.example.ahn.signinwithgoogle;

public class Plan {
    public String key;
    public String title="";
    public String address="";
    public double mapX=0.0;
    public double mapY=0.0;
    public String message="";
    public String Day="";

    public Plan(){}
    public Plan(String title, String address, double mapX, double mapY, String message){
        this.title = title;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.message = message;
    }
    public Plan(String title, String address, double mapX, double mapY, String message, String Day){
        this.title = title;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.message = message;
        this.Day = Day;
    }
}
