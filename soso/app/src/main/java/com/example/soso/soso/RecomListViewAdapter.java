package com.example.soso.soso;

/**
 * Created by SOSO on 2018-05-14.
 */
import android.util.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;

import static java.util.Arrays.*;


/**
 * Created by SOSO on 2018-05-14.
 */

public class RecomListViewAdapter extends BaseAdapter{
    private ArrayList<RecomListViewItem> recomlistViewItemList =new ArrayList<RecomListViewItem>();
    ImageView iconImageView;
    TextView textView;
    String url = "";

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
        //System.out.println("AAAAAAAAAAAAAAAAA");
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recommend_item, parent, false);
        }

        iconImageView = convertView.findViewById(R.id.recommendImg);
        textView=convertView.findViewById(R.id.recommendText);

        RecomListViewItem recomListViewItem =recomlistViewItemList.get(position);

        iconImageView.setImageBitmap(recomListViewItem.getIcon());
        textView.setText(recomListViewItem.getText());

        return convertView;
    }

    public void addItem(String url, String text){
        RecomListViewItem item = new RecomListViewItem();
        item.setIcon(url);
        item.setText(text);
        recomlistViewItemList.add(item);
    }
}