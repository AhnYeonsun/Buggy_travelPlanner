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

        Vertices[0] = new Vertex(0, 3.0, 0.0);
        Vertices[1] = new Vertex(1, 1.0, 1.0);
        Vertices[2] = new Vertex(2, 3.0, 4.0);
        Vertices[3] = new Vertex(3, 6.0, 2.0);
        Vertices[4] = new Vertex(4, 1.0, 5.0);

        //node 5개의 graph 그리기
        Graph graph = new Graph(5);
        graph.input(Vertices);
        graph.run(0);
        //graph.dijkstra();
    }
}
