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
                Log.d(i+" to "+j+" distance : ", String.valueOf(maps[i][j]) + "\n");
            }
        }
    }

    private double finalDistance;
    private int[] finalOrder;

    public void run(int v) { //시작 노드 번호
        boolean[] check = new boolean[n];     //해당 노드를 방문했는지 체크할 변수
        double currentD;                                //총거리
        finalDistance = 0.0;
        int order[] = new int[n];
        finalOrder = new int[n];
        int count = 0;

        //distance값 최대로 초기화.
        for (int i = 0; i < n; i++) {
            check[i] = false;
            finalOrder[i] = -1;
            order[i] = -1;
        }

        //시작노드값 초기화.
        check[v] = true; //어차피 함수 시작 시 초기화 해주니까 필요 없을까?
        currentD = 0.0;
        //minD = Integer.MAX_VALUE;
        order[0] = v; //얘도 함수 시작 시 초기화 해주니까 버릴까?

//        DistanceAndOrder fin = Dijkstra(v, currentD, order, check, count); //최종 최단거리와 순서 return됨.
//        System.out.println("Distance : " + fin.getDistance());
//        for (int i=0;i<n;i++) {
//            System.out.println("Order : " + fin.order[i]);
//        }
        Dijkstra(v,currentD,order,check,count);
        System.out.println("Final Shortest Path Distance : " + finalDistance);
        for (int i=0;i<n;i++) {
            System.out.println("Final Shortest Path Order : " + finalOrder[i]);
        }
    }

//    class DistanceAndOrder {
//        double TotalDistance;
//        int[] order;
//
//        DistanceAndOrder(double d, int[] o) {
//            this.TotalDistance = d;
//            this.order = o;
//        }
//        double getDistance(){
//            return TotalDistance;
//        }
//    }

    public void Dijkstra(int v, double TotalDistance, int[] order, boolean[] check, int count) {
        double[] distance = new double[n];
        //distance 초기화
        for (int i = 0; i < n; i++) {distance[i] = Integer.MAX_VALUE;}
        distance[v] = 0;
        //return 할 객체 생성
        //DistanceAndOrder dij = new DistanceAndOrder(TotalDistance, order);

        check[v] = true; //현재 node true
        order[count] = v; //현재 순서 저장
        double tempD;


        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (check[i]) cnt++;
        }
        if (cnt == n) { //마지막까지 다 찾았을 때!
            return;
        } else { //아닐 때!!
            for (int i = 0; i < n; i++) {
                //v와 연결된 모든 node 사이의 거리 입력.
                if (!check[i] && (maps[v][i] != 0)) {
                    //distance[i] = maps[v][i]; 필요없나??
                    //TotalDistance += maps[v][i]; 아냐.
                    tempD = TotalDistance + maps[v][i];
                    v = i;
                    Dijkstra(v, tempD, order, check, count++);
                }
            }
        }
        return;
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