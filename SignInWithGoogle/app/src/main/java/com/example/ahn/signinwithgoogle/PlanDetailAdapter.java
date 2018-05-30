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


// ***** 온돈온돈 ******//
public class PlanDetailAdapter extends BaseExpandableListAdapter {
    AlertDialog.Builder builder;
    ArrayList<GroupItem> groupItem;

    GroupViewHolder groupViewHolder;
    ChildViewHolder childViewHolder;

    public String spot = "";
    Context mContext;
    boolean chk = false;
    public LayoutInflater minflater;
    public PlanDetailAdapter(Context c)
    {
        builder=new AlertDialog.Builder(c);
    }
    public PlanDetailAdapter(Context context, ArrayList<GroupItem> groupItem) {
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
                ((Activity)mContext).startActivityForResult(intent,0); //여기서 인텐트 보내가지고 받아오는 정보들을... line 120에서 계속
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
        //여기서 받아가지고 날짜 별로 넣어주는데 이제는 이렇게 안하고 디비에 넣은다음에 가져와야해요
        //일단 아직 스팟밖에 안받아주는데 이건 추후(곧) 추가될 부분
        //dayposition은 들어가는 날짜(ex. 1일차, 2일차 등등)
        //여기서는 디비에 넣어주는 것만 하면됨
        //디비에서 가져와서 업데이트 해줄 listview는 line 134부터 계속
        spot = data.getStringExtra("spot");
        groupItem.get(data.getIntExtra("dayposition",0)).getArrayList().add(new ChildItems(spot));

        notifyDataSetChanged();
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_row, null);

            childViewHolder.et = (TextView) convertView.findViewById(R.id.textview);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        if (!groupItem.get(groupPosition).getArrayList().get(childPosition).getValue().equals("")) {
            //여기서 플랜 하나하나별로 출력해줌. getValue()는 일단 spot만 받아옴 추후(한시간뒤,,) 추가예정
            childViewHolder.et.setText(groupItem.get(groupPosition).getArrayList().get(childPosition).getValue());
        }
        else { //이부분은 데이터가 안들어와있을 때 해주는 부분인데 이젠 디비에 넣을거니까 유저가 입력을 안하면 그냥 원래 상태로 출력해주면됨
            //childViewHolder.et.setText("안녕");
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
