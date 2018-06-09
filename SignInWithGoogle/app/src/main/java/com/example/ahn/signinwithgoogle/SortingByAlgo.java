package com.example.ahn.signinwithgoogle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Node;

public class SortingByAlgo extends AppCompatActivity {
    FloatingActionButton backfloatingBtn;
    public int Nodes;
    Object[] tempVertices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recom_sort_by_algo);
        backfloatingBtn = (FloatingActionButton) findViewById(R.id.backfloatingBtn);

        //추천들 전체경로 알고리즘 돌리도록 Recommeds의 Vertices 가져오기
        Intent v = getIntent();
        Object[] Vertices = (Object[]) v.getSerializableExtra("Vertices");
        Nodes = v.getIntExtra("Nodes", 0);
        tempVertices = new Object[Nodes]; //소팅 후 바뀐 순서를 넣을 곳.

        //node 5개의 graph 그리기
        Graph graph = new Graph(Nodes);
        graph.input(Vertices);
        graph.run(0);

        // result
        Double Distance = graph.getFinalDistance();
        int[] Ordered = graph.getFinalOrder();

        //ordering recoms by result and insert to DB
        //Ordered의 순서대로 Vertices와 matching시켜 setResult하기
        //ex)Ordered = {0, 4, 3, 1, 2}
        for (int i = 0; i < Nodes; i++) {
            Log.d("WHY?", i + "");
            Log.d("WHY?", Ordered[i] + "");
            tempVertices[i] = Vertices[Ordered[i]];
        }
        // Map 끝나고 마지막줄에 tempVertices -> setResult

        //맵 띄우고, Polyline그리기
        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("3dde7e865dc040ff89b50813840dbc02");
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

        // Polyline 좌표 지정 예시.
        for (int i = 0; i < Nodes; i++) {
            Log.d("VERTX", ((Vertex) tempVertices[i]).getX() + "");
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(((Vertex) tempVertices[i]).getX(), ((Vertex) tempVertices[i]).getY()));
        }

        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

        backfloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRecomWithSortedVertices = new Intent();
                goRecomWithSortedVertices.putExtra("Sorted", tempVertices);
                goRecomWithSortedVertices.putExtra("Nodes", Nodes);
                setResult(RESULT_OK, goRecomWithSortedVertices);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed () {
        Intent goRecomWithSortedVertices = new Intent();
        goRecomWithSortedVertices.putExtra("Sorted", tempVertices);
        goRecomWithSortedVertices.putExtra("Nodes", Nodes);
        setResult(RESULT_OK, goRecomWithSortedVertices);
        finish();
        super.onBackPressed();
    }
}