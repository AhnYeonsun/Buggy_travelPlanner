package com.example.ahn.signinwithgoogle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlanDetail extends AppCompatActivity {

    ArrayList<GroupItem> arrayList;

    Intent informIntent;
    FloatingActionButton goRecom;
    ExpandableListView elv;
    PlanDetailAdapter listAdapter;
    List<String> listDataGroup;
    HashMap<String, List<String>> listDataChild;
    TextView plan_name;
    String numStr;
    String titleStr;
    Button addBtn, transportBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference addPlanDetail;


    int num=0; //AddPlan에서 가져올부분임.(날짜 차이)

    public PlanDetail(){}
    public PlanDetail(String n, String t){
        this.numStr = n;
        this.titleStr = t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        informIntent = getIntent();
        this.numStr = informIntent.getStringExtra("days"); //**************test
        this.titleStr = informIntent.getStringExtra("title"); //****************test
        num=Integer.parseInt(numStr);
        plan_name=findViewById(R.id.planTitle);
        plan_name.setText(titleStr);

        elv = (ExpandableListView) findViewById(R.id.listview);
        prepareListData();
        listAdapter = new PlanDetailAdapter(this,listDataGroup, listDataChild);

        elv.setAdapter(listAdapter);

        //그룹선택할때
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        //그룹확장
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        //그룹축소
        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        //차일드 클릭할때
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int grouPosition, int chilPosition, long id){

                return false;
            }

        });

//        //차일드 길게 클릭할때
//        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
//                builder.setTitle("Delete Detail")
//                        .setMessage("Are you sure to delete "+ arrayList.get(groupPosition).getArrayList().get(childPosition).getValue().toString()+"?")
//                        .setCancelable(false) //뒤로 버튼 클릭시 취소 가능 설정
//                        .setPositiveButton("Yap", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                arrayList.get(groupPosition).getArrayList().remove(childPosition);
//                                adapter.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton("Noooo", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                return true;
//            }
//        });*/

        goRecom = (FloatingActionButton) findViewById(R.id.fab);
        goRecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recommend.class);
                intent.putExtra("title", informIntent.getStringExtra("title"));
                intent.putExtra("startDay", informIntent.getStringExtra("startDay"));
                intent.putExtra("endDay", informIntent.getStringExtra("endDay"));
                intent.putExtra("day", num);
                startActivity(intent);
            }
        });

        addBtn = (Button)getLayoutInflater().inflate(R.layout.list_header, null, false).findViewById(R.id.addDetail);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSetDetail = new Intent(getApplicationContext(), SetDetail.class);
                startActivity(goSetDetail);
            }
        });

    }

    private void prepareListData(){

    }


    /////////////////여기가 setDetail.class에서 인텐트 받아오는곣///////////
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        addPlanDetail = FirebaseDatabase.getInstance().getReference();

        String spot = data.getStringExtra("spot");
        Double x = data.getDoubleExtra("MapX", 0);
        Double y = data.getDoubleExtra("MapY", 0);
        String memo = data.getStringExtra("memo");

//        groupItem.get(data.getIntExtra("dayposition",0)).getArrayList().add(new ChildItems(spot, memo));
//
//        int index = data.getIntExtra("dayposition",0);
//        Log.d("DDD", spot);
//        Log.d("DDD", index+"");
//
//        GetDaysForTravel getDaysForTravel = new GetDaysForTravel();
//        String[] AllDays = getDaysForTravel.getPD();
//        Log.d("B", AllDays[0]);
//
//        String planTitle = getDaysForTravel.getTitle();
//        ChildItems childItems = new ChildItems(spot, x, y, memo, AllDays[index]);
//        addPlanDetail.child("Users").child(mUser.getUid()).child(planTitle).push().setValue(childItems);
//        notifyDataSetChanged();
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
