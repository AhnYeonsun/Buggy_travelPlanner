package com.example.ahn.signinwithgoogle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SortingByAlgo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Detail-plan 에서 가져오는 장소만큼 for loop
        //지금은 5개만 예시로!

        Object[] Vertices = new Object[5];

        Vertices[0] = new Vertex(0, 0.0, 0.0);
        Vertices[1] = new Vertex(1, 3.0, 5.0);
        Vertices[2] = new Vertex(2, 1.0, 1.0);
//        Vertices[3] = new Vertex(3, 1.0, 1.0);
//        Vertices[4] = new Vertex(4, 1.0, 5.0);

        //node 5개의 graph 그리기
        Graph graph = new Graph(3);
        graph.input(Vertices);
        graph.run(0);
        //graph.dijkstra();
    }
}
