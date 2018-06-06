package com.example.ahn.signinwithgoogle;

import android.util.Log;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Graph {
    private int n;              // The number of vertices
    private double maps[][];    // distance from i to j
    private int initOrder[][];  // initial order

    public Graph(int n) {
        this.n = n;
        maps = new double[n][n];
        initOrder = new int[2][n]; //0번째는 0,1,2,3,4..., 1번째는 111,222,333,444...
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
                Log.d("33652 " + i + " to " + j + " distance : ", String.valueOf(maps[i][j]) + "\n");
            }
            initOrder[0][i] = i;
            initOrder[1][i] = ((Vertex)Vertices[i]).vertexID;
            Log.d("33652" +"initOrder[0][i] : ",initOrder[0][i]+"");
            Log.d("33652" +"initOrder[1][i] : ",initOrder[1][i]+"");
        }
    }

    private double finalDistance;   // 최종거리
    private int[] finalOrder;       // 최종순서

    public Double getFinalDistance(){
        return finalDistance;
    }
    public int[] getFinalOrder(){
        return finalOrder;
    }

    public void run(int v) { //시작 노드 번호
        boolean[] check = new boolean[n];       //해당 노드를 방문했는지 체크할 변수
        double currentD;                        //현재총거리
        finalDistance = Double.MAX_EXPONENT;    //최종거리
        int order[] = new int[n];               //현재순서
        finalOrder = new int[n];                //최종순서


        //distance값 최대로 초기화.
        for (int i = 0; i < n; i++) {
            check[i] = false;           //전부 방문 안함
            finalOrder[i] = -1;         //순서 초기화
            order[i] = -1;              //순서 초기화
        }

        //시작노드값 초기화.
        check[0] = true;         //첫 node 방문함.
        currentD = 0.0;          //현재 거리 0
        order[0] = initOrder[0][0];

//        DistanceAndOrder fin = Dijkstra(v, currentD, order, check, count); //최종 최단거리와 순서 return됨.
//        System.out.println("Distance : " + fin.getDistance());
//        for (int i=0;i<n;i++) {
//            System.out.println("Order : " + fin.order[i]);
//        }
        Dijkstra(v, currentD, order, check);
        Log.d("33652" + " Final Distance : ", finalDistance+"");
        for (int i = 0; i < n; i++) {
            Log.d("33652" + " Final Order : ", finalOrder[i]+"");
        }
    }

    public void Dijkstra(int v, double TotalDistance, int[] order, boolean[] check) {
        Log.d("33652" + " Current1 : ", v+" / "+TotalDistance+"");
        for (int i = 0; i < n; i++){
            Log.d("33652" + " Current2 : ", order[i]+" / "+check[i]);
        }
        double[] distance = new double[n];
        //distance 초기화
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[v] = 0; //현재 v = 0

        int[] tempO = new int[n];
        boolean[] tempCheck = new boolean[n];
        for (int i = 0; i < n; i++) {
            tempO[i] = order[i];
            tempCheck[i] = check[i];
        }tempCheck[v] = true;
        double tempD;
        int tempV;

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (tempCheck[i]) cnt++;
        }
        if (cnt == n) { //마지막까지 다 찾았을 때!
            Log.d("33652" + " Last route : ", cnt+"");
            if (finalDistance > TotalDistance) {
                finalDistance = TotalDistance;
                for (int i = 0; i < n; i++) {
                    finalOrder[i] = tempO[i];
                }
            }
            Log.d("33652" + " tempFin : ", finalDistance+"");
            for (int i = 0; i < n; i++) {
                Log.d("33652" + " tempOrd : ", finalOrder[i] + "");
            }
            return;
        } else { //아닐 때!!
            Log.d("33652" + " In route : ", cnt+"");
            for (int i = 0; i < n; i++) {
                //v와 연결된 모든 node 사이의 거리 입력.
                if (!check[i] && (maps[v][i] != 0)) {
                    tempD = TotalDistance + maps[v][i];
                    tempV = i;
                    tempO[cnt] = i;
                    Dijkstra(tempV, tempD, tempO, tempCheck);
                }
            }
            return;
        }
    }
}