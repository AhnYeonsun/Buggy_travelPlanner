package com.example.ahn.ttubucke;


public class Vertex {
    private int vertexID;
    public double vertexX;
    public double vertexY;

    Vertex(int id, double x, double y){
        this.vertexID = id;
        this.vertexX = x;
        this.vertexY = y;
    }

    public double getX(){
        return vertexX;
    }
    public double getY(){
        return vertexY;
    }
}
