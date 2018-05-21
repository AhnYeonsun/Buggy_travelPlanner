package com.example.ahn.ttubucke;

import android.util.Log;

import java.io.InputStream;

import static java.lang.Math.*;

/**
 * Created by ahn on 2018-05-19.
 */

public class Graph {

    private int n;           //노드들의 수
    private double maps[][];    //노드들간의 가중치 저장할 변수

    public Graph(int n) {
        this.n = n;
        maps = new double[n][n];
    }

    public void input(Object[] Vertices) {
        double x1, y1, x2, y2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x1 = ((Vertex) Vertices[i]).vertexX;
                y1 = ((Vertex) Vertices[i]).vertexY;
                x2 = ((Vertex) Vertices[j]).vertexX;
                y2 = ((Vertex) Vertices[j]).vertexY;
                maps[i][j] = sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));
                Log.d("i to j distance : ", String.valueOf(maps[i][j]) + "\n");
            }
        }
    } //*******

    public void initialize(int v) { //시작 노드 번호
        double distance[] = new double[n];          //최단 거리를 저장할 변수
        boolean[] check = new boolean[n];     //해당 노드를 방문했는지 체크할 변수
        double D;                                //총거리
        int order[] = new int[n];

        //distance값 최대로 초기화.
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            check[i] = false;
            order[i] = -1;
        }

        //시작노드값 초기화.
        distance[v] = 0;
        check[v] = true;
        D = 0.0;
        order[0] = v;

        dijkstra(v, distance, check, D, order);
    }

    public void dijkstra(int lastV, double[] distance, boolean[] check, double D, int[] order) {

        for (int i = 0; i < n; i++){
            //마지막 V와 연결된 node들 사이의 거리 저장
            if(!check[i] && maps[lastV][i] != 0){
                distance[i] = maps[lastV][i];
            }
        }
        for (int i = 0; i < n; i++){
            if(!check[i] && distance[i] != Integer.MAX_VALUE){

            }
        }
    }
}

//        //연결노드 distance갱신
//        for (int i = 0; i < n; i++) {
//            if (!check[i] && maps[v][i] != 0) {
//                distance[i] = maps[v][i];
//            }
//        }
//        for (int a = 0; a < n - 1; a++) {
//            double min = Integer.MAX_VALUE;
//            int min_index = -1;
//
//            //최소값 찾기
//            for (int i = 0; i < n; i++) {
//                if (!check[i] && distance[i] != Integer.MAX_VALUE) {
//                    if (distance[i] < min) {
//                        min = distance[i];
//                        min_index = i;
//                    }
//                }
//            }
//
//            check[min_index] = true;
//
//        }
//
//        //결과값 출력
//        for (int i = 0; i < n; i++) {
//            System.out.print(distance[i] + " ");
//        }
//        System.out.println("");