package com.example.ahn.signinwithgoogle;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlanDetail extends AppCompatActivity {
    Intent informIntent;
    FloatingActionButton goRecom;
    ExpandableListView elv;
    PlanDetailAdapter listAdapter;
    private ArrayList<GroupItem> listDataGroup = new ArrayList<>();
    TextView plan_name;
    String numStr;
    String titleStr;
    Button addBtn, transportBtn;
    public AlertDialog.Builder builder;

    private FirebaseAuth mAuth;
    private DatabaseReference addPlanDetail;
    private DatabaseReference readplanDetail;
    private DatabaseReference forDateDB;

    public String[] AllDays;
    public String planTitle;


    public int num = 0; //AddPlan에서 가져올부분임.(날짜 차이)

    public PlanDetail() {
    }

    public PlanDetail(String n, String t) {
        this.numStr = n;
        this.titleStr = t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

//        GetDaysForTravel getDaysForTravel = new GetDaysForTravel();// 이부분도 수정필........................
//        AllDays = getDaysForTravel.getPD();
//        planTitle = getDaysForTravel.getTitle();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        addPlanDetail = FirebaseDatabase.getInstance().getReference();

        elv = (ExpandableListView) findViewById(R.id.listviewDetail);
        elv.setGroupIndicator(null);


        //그룹확장
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //Log.d("BBBBB","GRORORORO");
            }
        });

        //그룹축소
        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("BBBBB","GRORORORO");
                return false;
            }
        });
        //차일드 클릭할때
        builder = new AlertDialog.Builder(this);
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
                Log.d("VVVV","VVVVV");
                String title = "", mapX = "", mapY = "", memo = "", planDay = "";

                title = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getValue();
                mapX = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getPlanMapX().toString();
                mapY = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getPlanMapY().toString();
                memo = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getDetailMessage();

                builder.setTitle(title +"정보")
                        .setMessage("위치 정보   \nX 좌표 : "+mapX+"\bnY 좌표 : "+mapY+"\n\n"+"메모 : "+memo+"\n\n") //이게 정보 받아주는 함수
                        .setCancelable(false)
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();

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
        informIntent = getIntent();
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


    }
    public void setListFromDatabase(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        forDateDB = FirebaseDatabase.getInstance().getReference().child("forDate").child(mUser.getUid());

        informIntent = getIntent(); //AddPlan에서 받아주는 곳. 사실상 이름만 받아와서, 나머지 내용들은 DB에서 가져오면 됨.
        this.titleStr = informIntent.getStringExtra("title");
        //forDateDB에서 여행정보 가져와서 AllDays 같은거 만들어주는 곳.
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("CHECK HERE", child.getKey());
                    //Plan plan = child.getValue(Plan.class);
                    if (child.getKey().equals(titleStr)) {
                        TravelInfo t = child.getValue(TravelInfo.class);
                        //title, startDate, endDate, numDates
                        num = (int) t.InfoNumDates;
                        Log.d("CHECK22",num+"");
                        planTitle = t.InfoTitle;
                        String sd = t.InfoStartDate;
                        String ed = t.InfoEndDate;
                        Log.d("CHECK22",planTitle);
                        CalculateDays calculateDays = new CalculateDays(sd, ed, num, planTitle);
                        AllDays = calculateDays.days;
                        Log.d("CHECK22",AllDays[0]);
                        Log.d("CHECK22",AllDays[1]);
                        Log.d("CHECK22",AllDays[2]);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        forDateDB.addListenerForSingleValueEvent(valueEventListener);
    }
    public void prepareListGroupData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        Log.d("XXX", "in list view");
        Log.d("XXX", "in list view"+num);
        listDataGroup.clear();
        //Adding group data
        for (int i = 0; i < num; i++) {
            Log.d("XXX", "in group view");
            listDataGroup.add(new GroupItem(AllDays[i]));
        }
    }
    public void prepareListChildData(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        //Users DB에서 불러와서 child item 만들어주는 곳.
        readplanDetail = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child(planTitle);
        ValueEventListener valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("XXX", "in value event listener");
                    Plan plan = child.getValue(Plan.class);
                    for (int i = 0; i < num; i++) {
                        if (plan.Day.equals(AllDays[i])) {
                            listDataGroup.get(i).getArrayList().add(new ChildItems(plan.title, plan.mapX, plan.mapY, plan.message, plan.Day));
                        }
                    }

                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        readplanDetail.addListenerForSingleValueEvent(valueEventListener2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CC", "1");
//        listAdapter.onActivityResult(requestCode, resultCode, data);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        addPlanDetail = FirebaseDatabase.getInstance().getReference();

        String spot = data.getStringExtra("spot");
        Double x = data.getDoubleExtra("MapX", 0);
        Double y = data.getDoubleExtra("MapY", 0);
        String memo = data.getStringExtra("memo");

        int index = data.getIntExtra("dayposition", 0);
        Log.d("RE", index + "");
//        GetDaysForTravel getDaysForTravel = new GetDaysForTravel();
//        AllDays = getDaysForTravel.getPD();
//        planTitle = getDaysForTravel.getTitle();

        //Plan 형식 : String title, String address, double mapX, double mapY, String message
        Plan plan = new Plan(spot, "", x, y, memo, AllDays[index]);
        addPlanDetail.child("Users").child(mUser.getUid()).child(planTitle).push().setValue(plan);
        listDataGroup.get(index).getArrayList().add(new ChildItems(spot, x, y, memo, AllDays[index]));
        Log.d("CC", "3");
    }


    @Override
    public void onResume() {
        super.onResume();
        setListFromDatabase(); //여행 정보 가져오기

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareListGroupData(); //Group 만들기
                Log.d("RRRRRRR","GROUPPP");
                //try {
                    //String temp = readplanDetail.child("Users").getKey();
                    Log.d("RRRRRRR","ok");
                    prepareListChildData(); //Child 만들기
                //}catch (NullPointerException ne){

                //}finally {
                    Log.d("RRRRRRR","in finally");
                    listAdapter = new PlanDetailAdapter(PlanDetail.this, listDataGroup, elv);
                    elv.setAdapter(listAdapter);
                    listAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                //}
            }
        }, 1000);

    }
}
