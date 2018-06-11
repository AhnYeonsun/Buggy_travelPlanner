package org.whitesneakers.buggy;

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
            }
            initOrder[0][i] = i;
            initOrder[1][i] = ((Vertex)Vertices[i]).vertexID;
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

        Dijkstra(v, currentD, order, check);
    }

    public void Dijkstra(int v, double TotalDistance, int[] order, boolean[] check) {
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
            if (finalDistance > TotalDistance) {
                finalDistance = TotalDistance;
                for (int i = 0; i < n; i++) {
                    finalOrder[i] = tempO[i];
                }
            }
            return;
        } else { //아닐 때!!
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