package com.example.ahn.signinwithgoogle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SOSO on 2018-05-24.
 */

public class CreatePlanAdapter extends BaseAdapter {
    public ArrayList<CreatePlanItem> planItemList = new ArrayList<CreatePlanItem>();
    TextView date, name;

    private AlertDialog.Builder builder;

    public CreatePlanAdapter(Context c)
    {
        builder = new AlertDialog.Builder(c);
    }


    @Override
    public int getCount() {
        return planItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return planItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context=parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plan_item, parent, false);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        date=convertView.findViewById(R.id.date);
        name=convertView.findViewById(R.id.name);

        // Data Set(CreatePlanItem)에서 position에 위치한 데이터 참조 획득
        final CreatePlanItem planItem = planItemList.get(position);

        date.setText(planItem.getDate());
        name.setText(planItem.getName());

        return convertView;
    }

    public void addItem(String name, String date)
    {
        CreatePlanItem item = new CreatePlanItem();
        item.setDate(date);
        item.setName(name);
        planItemList.add(item);
    }
}
