package com.example.soso.soso;

/**
 * Created by SOSO on 2018-05-14.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by SOSO on 2018-05-14.
 */

public class RecomListViewAdapter extends BaseAdapter{
    private ArrayList<RecomListViewItem> recomlistViewItemList =new ArrayList<RecomListViewItem>();
    ImageView iconImageView;
    TextView textView;
    public RecomListViewAdapter() {

    }
    @Override
    public int getCount() {
        return recomlistViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return recomlistViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recommend_item, parent, false);
        }

        iconImageView = convertView.findViewById(R.id.recommendImg);
        textView=convertView.findViewById(R.id.recommendText);

        RecomListViewItem recomListViewItem =recomlistViewItemList.get(position);

        iconImageView.setImageDrawable(recomListViewItem.getIcon());
        textView.setText(recomListViewItem.getText());

        return convertView;
    }
    public void addItem(Drawable icon, String text){
        RecomListViewItem item = new RecomListViewItem();

        item.setIcon(icon);
        item.setText(text);

        recomlistViewItemList.add(item);
    }
}