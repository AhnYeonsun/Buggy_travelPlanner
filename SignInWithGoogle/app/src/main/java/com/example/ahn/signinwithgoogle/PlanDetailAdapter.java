package com.example.ahn.signinwithgoogle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


// ***** 온돈온돈 ******//
public class PlanDetailAdapter extends BaseExpandableListAdapter {
    AlertDialog.Builder builder;
    Button addBtn;
    private List<String> group;
    private HashMap<String, List<String>> child;
    private FirebaseAuth mAuth;
    private DatabaseReference addPlanDetail;
    private DatabaseReference readplanDetail;

    public String[] AllDays;
    public String planTitle;

    public String spot = "";
    Context mContext;

    public LayoutInflater minflater;
    public PlanDetailAdapter(Context c)
    {
        builder=new AlertDialog.Builder(c);
    }
    public PlanDetailAdapter(Context context, List<String> group, HashMap<String, List<String>> child) {
        this.mContext = context;
        this.group = group;
        this.child = child;
    }

    public void setInflater(LayoutInflater mInflater) {
        this.minflater = mInflater;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  this.child.get(this.group.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.child.get(this.group.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_header, null);
        }

        TextView headerList = (TextView) convertView.findViewById(R.id.date_list);
        headerList.setText(headerTitle);

        addBtn = (Button)convertView.findViewById(R.id.addDetail);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSetDetail = new Intent(mContext.getApplicationContext(), SetDetail.class);
                ((Activity)mContext).startActivityForResult(goSetDetail,0);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        TextView txtChild = (TextView) convertView.findViewById(R.id.textview);
        txtChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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

        int index = data.getIntExtra("dayposition",0);

        //Plan 형식 : String title, String address, double mapX, double mapY, String message
        //Plan 형식에 Day 추가한 contructor 만들어야함
        //ChildItems childItems = new ChildItems(spot, x, y, memo, AllDays[index]);
        Plan plan = new Plan(spot, "", x, y, memo, AllDays[index]);
        addPlanDetail.child("Users").child(mUser.getUid()).child(planTitle).push().setValue(plan);
    }

}
