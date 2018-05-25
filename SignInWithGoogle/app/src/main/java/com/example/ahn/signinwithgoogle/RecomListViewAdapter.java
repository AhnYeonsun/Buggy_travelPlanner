package com.example.ahn.signinwithgoogle;

/**
 * Created by SOSO on 2018-05-14.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by SOSO on 2018-05-14.
 */

public class RecomListViewAdapter extends BaseAdapter{
    ArrayList<RecomListViewItem> recomlistViewItemList;// =new ArrayList<RecomListViewItem>();
    ImageView iconImageView;
    TextView titleView;
    TextView addressView;
    Drawable icon;
    String url = "";
    String info[];
    final String[] contentType = {"관광지", "문화시설", "축제/공연/행사", "여행코스", "레포츠", "숙박", "쇼핑", "음식"}; //initial content type

    public RecomListViewAdapter(){}
    public RecomListViewAdapter(Context c) { }

    public RecomListViewAdapter(ArrayList<RecomListViewItem> recomListViewAdapterArrayList) {
        recomlistViewItemList = recomListViewAdapterArrayList;
    }


    @Override
    public int getCount() {
        return recomlistViewItemList.size();
    }
    
    @Override
    public Object getItem(int position) {
        System.out.println("************* : " + position);
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
        titleView=convertView.findViewById(R.id.recommendText);
        addressView=convertView.findViewById(R.id.recommendAdd);
        RecomListViewItem recomListViewItem =recomlistViewItemList.get(position);
        
        //iconImageView.setImageBitmap(recomListViewItem.getIcon());
        titleView.setText(recomListViewItem.getName()); //이름 가져오기
        addressView.setText(recomListViewItem.getAddress()); //주소가져오기
        iconImageView.setImageResource(recomlistViewItemList.get(position).getImgId());
        
        return convertView;
    }
    
    /*public void addItem(String url, String text){
     RecomListViewItem item = new RecomListViewItem();
     item.setIcon(url);
     item.setText(text);
     recomlistViewItemList.add(item);
     }*/
    public void addItem(int img, String contentID, String contentTypeID, String name,String address, String mapX, String mapY, String imgURL){
        RecomListViewItem item = new RecomListViewItem();
     Log.d("hoohohoho",name);
        item.setName(name);
        item.setAddress(address);
        item.setImgId(img);
        item.setContentID(contentID);
        item.setContentTypeID(contentTypeID);
        item.setMapX(mapX);
        item.setMapY(mapY);
        item.setMainImg(imgURL);
        recomlistViewItemList.add(item);
    }


    public void clear(){
        recomlistViewItemList.clear();
    }
}
