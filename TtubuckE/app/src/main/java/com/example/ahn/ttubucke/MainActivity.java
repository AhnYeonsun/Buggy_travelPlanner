package com.example.ahn.ttubucke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Detail-plan 에서 가져오는 장소만큼 for loop
        //지금은 5개만 예시로!

        Object[] Vertices = new Object[5];

//        Vertices[0] = new Vertex(0, 124.0, 33.0);
//        Vertices[1] = new Vertex(1, 126.0, 37.0);
//        Vertices[2] = new Vertex(2, 132.0, 33.0);
//        Vertices[3] = new Vertex(3, 132.0, 38.0);
//        Vertices[4] = new Vertex(4, 130.0, 35.0);

        //node 5개의 graph 그리기
        Graph graph = new Graph(5);
        graph.input(Vertices);
        //graph.dijkstra(0);
    }
}
