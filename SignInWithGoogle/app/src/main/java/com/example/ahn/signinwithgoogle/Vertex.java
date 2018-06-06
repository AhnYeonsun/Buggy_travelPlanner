package com.example.ahn.signinwithgoogle;

import java.io.Serializable;

public class Vertex implements Serializable {
    public int vertexID;
    public double vertexX;
    public double vertexY;

    Vertex(int id, double x, double y) {
        this.vertexID = id;
        this.vertexX = x;
        this.vertexY = y;
    }

    public double getID() {
        return vertexID;
    }

    public double getX() {
        return vertexX;
    }

    public double getY() {
        return vertexY;
    }
}