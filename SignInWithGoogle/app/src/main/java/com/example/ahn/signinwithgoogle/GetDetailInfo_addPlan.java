package com.example.ahn.signinwithgoogle;

import android.util.Log;

public class GetDetailInfo_addPlan {
    RecomListViewItem data;
    String detailInfo = "";

    public GetDetailInfo_addPlan(RecomListViewItem item, String message){
        data = item;
        detailInfo = message;
    }

    public void getTitle(){ data.getName(); Log.i("ASDASDASD : ",data.getName());}

    public void getAddress() { data.getAddress(); }

    public void getMapX() { data.getMapX(); }

    public void getMapY() { data.getMapY(); }

    public String getDetailInfo(){return detailInfo;}

    ////////온도온도 : 요기서 저기 GET함수들 불러서 바로 디비에 때려박으면되염/////////////
}
