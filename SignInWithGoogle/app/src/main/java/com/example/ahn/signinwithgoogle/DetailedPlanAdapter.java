package com.example.ahn.signinwithgoogle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailedPlanAdapter extends BaseExpandableListAdapter {
    AlertDialog.Builder builder;
    ArrayList<GroupItem> groupItem;

    GroupViewHolder groupViewHolder;
    ChildViewHolder childViewHolder;

    public String spot = "";
    Context mContext;
    boolean chk = false;
    public LayoutInflater minflater;
    public DetailedPlanAdapter(Context c)
    {
        builder=new AlertDialog.Builder(c);
    }
    public DetailedPlanAdapter(Context context, ArrayList<GroupItem> groupItem) {
        this.mContext = context;
        this.groupItem = groupItem;
    }

    public void setInflater(LayoutInflater mInflater) {
        this.minflater = mInflater;
    }

    @Override
    public int getGroupCount() {
        return groupItem.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupItem.get(i).getArrayList().size();
    }

    @Override
    public Object getGroup(int i) {
        return groupItem.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return groupItem.get(i).getArrayList().get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.d("testing", "GroupView");
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = minflater.inflate(R.layout.list_header, null);
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.date_list);
            groupViewHolder.btnAdd = (Button) convertView.findViewById(R.id.addDetail);
            groupViewHolder.btnTransport=(Button) convertView.findViewById(R.id.transport);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);

        groupViewHolder.tvTitle.setText(groupItem.get(groupPosition).getTitle());

        groupViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() { //여기서 이제 장소 받아줘야지
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext.getApplicationContext(), SetDetail.class);
                intent.setFlags(groupPosition);//adapter 생성시, 이미 context받아왔으니까...
                ((Activity)mContext).startActivityForResult(intent,0);


            }
        });


        groupViewHolder.btnTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //여기 연선이 해야되는 부분!!
                groupItem.get(groupPosition).getArrayList().add(new ChildItems("다윤이"));
                getChildrenCount(groupPosition);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("Detailed Plan Adapter","onActivityResult");
        spot = data.getStringExtra("spot");
        //  Log.d("TEST",data.getStringExtra("dayposition"));
        groupItem.get(data.getIntExtra("dayposition",0)).getArrayList().add(new ChildItems(spot));

        //getChildrenCount(groupPosition);
        notifyDataSetChanged();

        //Log.d("SPOT", spot);
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Log.d("testing", "ChildView");
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_row, null);

            childViewHolder.et = (TextView) convertView.findViewById(R.id.textview);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        if (!groupItem.get(groupPosition).getArrayList().get(childPosition).getValue().equals("")) {
            System.out.println("WWWWWWW : " + groupItem.get(groupPosition).getArrayList().get(childPosition).getValue());
            childViewHolder.et.setText(groupItem.get(groupPosition).getArrayList().get(childPosition).getValue());
        } else {
            Log.i("WWWWWWWW ::::::: ", groupItem.get(groupPosition).getArrayList().get(childPosition).getValue());
            childViewHolder.et.setText("안녕");
        }
        childViewHolder.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final EditText Caption = (EditText) v;
                    groupItem.get(groupPosition).getArrayList().get(childPosition).setValue(Caption.getText().toString());
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    private class GroupViewHolder {
        public TextView tvTitle;
        public Button btnAdd;
        public Button btnTransport;
    }

    private class ChildViewHolder {
        public TextView et;
    }

}
