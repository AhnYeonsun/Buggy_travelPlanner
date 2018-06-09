package com.example.ahn.signinwithgoogle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanDetailAdapter extends BaseExpandableListAdapter {
    AlertDialog.Builder builder;
    ArrayList<GroupItem> groupItem;

    GroupViewHolder groupViewHolder;
    ChildViewHolder childViewHolder;

    boolean[] groupClickState;
    public String spot = "";
    Context mContext;
    boolean chk = false;
    public LayoutInflater minflater;
    ExpandableListView elv;

    public PlanDetailAdapter(Context c) {
        builder = new AlertDialog.Builder(c);
    }

    public PlanDetailAdapter(Context context, ArrayList<GroupItem> groupItem, ExpandableListView elv) {
        this.mContext = context;
        this.groupItem = groupItem;
        this.groupClickState = new boolean[groupItem.size()];

        for(int i=0;i<groupClickState.length;i++){
            groupClickState[i] = false;
        }

        this.elv = elv;
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
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = minflater.inflate(R.layout.list_header, null);
            groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.date_list);
            groupViewHolder.btnAdd = (Button) convertView.findViewById(R.id.addDetail);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        convertView.setClickable(false);
        elv = (ExpandableListView) parent;
        elv.expandGroup(groupPosition);

        groupViewHolder.tvTitle.setText(groupItem.get(groupPosition).getTitle());
        groupViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() { //여기서 이제 장소 받아줘야지
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext.getApplicationContext(), SetDetail.class);
                intent.setFlags(groupPosition);//adapter 생성시, 이미 context받아왔으니까...
                ((Activity) mContext).startActivityForResult(intent, 0);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Log.d("testing", "ChildView");

        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_row, null);

            childViewHolder.et = (TextView) convertView.findViewById(R.id.childText);

            convertView.setTag(childViewHolder);
        }
        convertView.setClickable(false);

        if (!groupItem.get(groupPosition).getArrayList().get(childPosition).getValue().equals("")) {
            String title = "", mapX = "", mapY = "", memo = "", planDay = "";
            title = groupItem.get(groupPosition).getArrayList().get(childPosition).getValue();
            childViewHolder.et.setText(title);
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
        return true;
    }



    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }


    private class GroupViewHolder {
        public TextView tvTitle;
        public Button btnAdd;
    }

    private class ChildViewHolder {
        public TextView et;
    }

}