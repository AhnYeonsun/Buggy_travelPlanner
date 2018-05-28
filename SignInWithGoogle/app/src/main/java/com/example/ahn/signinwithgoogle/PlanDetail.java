package com.example.ahn.signinwithgoogle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;


public class PlanDetail extends AppCompatActivity {

    ArrayList<GroupItem> arrayList;

    Intent informIntent;
    FloatingActionButton goRecom;
    ExpandableListView elv;
    DetailedPlanAdapter adapter;
    int num=0; //AddPlan에서 가져올부분임.(날짜 차이)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);
        informIntent = getIntent();
        String numStr = informIntent.getStringExtra("days");
        num=Integer.parseInt(numStr);

        arrayList = new ArrayList<>();
        //여행일수만큼 groupitem 생성
        for(int i=0;i<num+1;i++)//+1해줘야지 마지막날까지 나옴
        {
            String s = String.valueOf(i+1);
            arrayList.add(new GroupItem(s+"일차"));
        }

        elv = findViewById(R.id.listview);
        adapter = new DetailedPlanAdapter(PlanDetail.this, arrayList);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        elv.setAdapter(adapter);

        Button btn = findViewById(R.id.show); //검색기능
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, Test.class);
                //startActivity(intent);

                String all="";
                for (int i=0; i<arrayList.size(); i++){
                    for (int j=0; j<arrayList.get(i).getArrayList().size(); j++) {
                        all += arrayList.get(i).getArrayList().get(j).getValue() + "\n";
                    }
                }
                Toast.makeText(PlanDetail.this, all, Toast.LENGTH_LONG).show();
            }
        });

        goRecom = (FloatingActionButton) findViewById(R.id.fab);
        goRecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reccommend.class);
                intent.putExtra("title", informIntent.getStringExtra("title"));
                intent.putExtra("startDay", informIntent.getStringExtra("startDay"));
                intent.putExtra("endDay", informIntent.getStringExtra("endDay"));
                intent.putExtra("day", num);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        adapter.onActivityResult(requestCode, resultCode, data);
    }

    //액션바 수정해야함 -> 스택에 쌓이는 거 볼수 있도록
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds groupItem to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
