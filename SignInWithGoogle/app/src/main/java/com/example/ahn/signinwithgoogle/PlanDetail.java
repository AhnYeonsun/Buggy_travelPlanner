package com.example.ahn.signinwithgoogle;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PlanDetail extends AppCompatActivity {
    Intent informIntent;
    FloatingActionButton goRecom;
    ExpandableListView elv;
    PlanDetailAdapter listAdapter;
    private ArrayList<GroupItem> listDataGroup = new ArrayList<>();
    TextView plan_name;
    String numStr;
    String titleStr;
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

        ProgressDialog di = new ProgressDialog(this);
        Progress_dialog dialog = new Progress_dialog(di, 2);
        dialog.execute();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        addPlanDetail = FirebaseDatabase.getInstance().getReference();

        elv = (ExpandableListView) findViewById(R.id.listview);
        elv.setGroupIndicator(null);

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        //차일드 클릭할때
        builder = new AlertDialog.Builder(this);
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
                String title = "", memo = "", address = "";

                title = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getValue();

                memo = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getDetailMessage();

                address = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getAddress();

                listDataGroup.get(groupPosition).getArrayList().get(childPosition);

                builder.setTitle(title)
                        .setMessage("\n" + "Address : " + address + "\n\nInfo : \n" + memo + "\n\n") //이게 정보 받아주는 함수
                        .setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
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

        elv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(elv.getPackedPositionType(id)==elv.PACKED_POSITION_TYPE_CHILD){
                    final int groupPosition  = elv.getPackedPositionGroup(id);
                    final int childPosition = elv.getPackedPositionChild(id);
                    builder.setTitle("Delete Detail")
                            .setMessage("Are you sure to delete "+ listDataGroup.get(groupPosition).getArrayList().get(childPosition).getValue()+"?")
                            .setCancelable(false) //뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    listDataGroup.get(groupPosition).getArrayList().remove(childPosition);
                                    listAdapter.notifyDataSetChanged();

                                    //DB에서도 지우기
                                    //child의 title 가져와야 함.
                                    //DB의 애들과 비교 후, title이 같으면 삭제.
                                    final String deleteTitle = listDataGroup.get(groupPosition).getArrayList().get(childPosition).getValue();
                                    readplanDetail = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child(planTitle);
                                    ValueEventListener deleteChildListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child : dataSnapshot.getChildren()){
                                                Plan p = child.getValue(Plan.class);
                                                if((p.title).equals(deleteTitle)){
                                                    readplanDetail.child(child.getKey()).removeValue();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    }; readplanDetail.addListenerForSingleValueEvent(deleteChildListener);

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
                return true;
            }
        });

        informIntent = getIntent();
        plan_name=findViewById(R.id.planTitle);
        plan_name.setText(informIntent.getStringExtra("title").toString());
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
                    if (child.getKey().equals(titleStr)) {
                        TravelInfo t = child.getValue(TravelInfo.class);
                        //title, startDate, endDate, numDates
                        num = (int) t.InfoNumDates;
                        planTitle = t.InfoTitle;
                        String sd = t.InfoStartDate;
                        String ed = t.InfoEndDate;
                        CalculateDays calculateDays = new CalculateDays(sd, ed, num, planTitle);
                        AllDays = calculateDays.days;
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

        listDataGroup.clear();
        //Adding group data
        for (int i = 0; i < num; i++) {
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
                    Plan plan = child.getValue(Plan.class);
                    for (int i = 0; i < num; i++) {
                        if (plan.Day.equals(AllDays[i])) {
                            listDataGroup.get(i).getArrayList().add(new ChildItems(plan.title, plan.mapX, plan.mapY, plan.message, plan.Day, plan.address));
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

//        listAdapter.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==7){
        }
        else {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser mUser = mAuth.getCurrentUser();
            addPlanDetail = FirebaseDatabase.getInstance().getReference();
            String spot = data.getStringExtra("spot");
            Double x = data.getDoubleExtra("MapX", 0);
            Double y = data.getDoubleExtra("MapY", 0);
            String memo = data.getStringExtra("memo");
            String address = data.getStringExtra("Address");

            int index = data.getIntExtra("dayposition", 0);

            //Plan 형식 : String title, String address, double mapX, double mapY, String message
            Plan plan = new Plan(spot, address, x, y, memo, AllDays[index]);
            addPlanDetail.child("Users").child(mUser.getUid()).child(planTitle).push().setValue(plan);
            listDataGroup.get(index).getArrayList().add(new ChildItems(spot, x, y, memo, AllDays[index], address));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ProgressDialog di = new ProgressDialog(this);
        Progress_dialog dialog = new Progress_dialog(di, 2);
        dialog.execute();

        setListFromDatabase(); //여행 정보 가져오기

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareListGroupData(); //Group 만들기
                //try {
                //String temp = readplanDetail.child("Users").getKey();
                prepareListChildData(); //Child 만들기
                //}catch (NullPointerException ne){

                //}finally {
                listAdapter = new PlanDetailAdapter(PlanDetail.this, listDataGroup, elv);
                elv.setAdapter(listAdapter);
                listAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
                //}
            }
        }, 1000);

    }
}
