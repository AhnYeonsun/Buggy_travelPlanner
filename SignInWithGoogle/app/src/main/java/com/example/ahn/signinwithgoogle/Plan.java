package com.example.ahn.signinwithgoogle;

public class Plan {
    String title;
    String address;
    double mapX;
    double mapY;
    String message;

    Plan(String t, String a, double x, double y, String m){
        this.title = t;
        this.address = a;
        this.mapX = x;
        this.mapY = y;
        this.message = m;
    }
}
