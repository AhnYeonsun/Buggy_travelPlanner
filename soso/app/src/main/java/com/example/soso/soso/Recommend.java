package com.example.soso.soso;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class Recommend extends AppCompatActivity {
    ListView listview1;
    Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        searchBtn=findViewById(R.id.searchBtn);
        Spinner spinner = findViewById(R.id.regionList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBtn.setText("선택된 지방: "+parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RecomListViewAdapter adapter;

        // Adapter 생성
        adapter = new RecomListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview1 = findViewById(R.id.recom_listview1);
        listview1.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.cat),
                "cat") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.dog),
                "dog") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.rabbit),
                "rabbit") ;

    }
}