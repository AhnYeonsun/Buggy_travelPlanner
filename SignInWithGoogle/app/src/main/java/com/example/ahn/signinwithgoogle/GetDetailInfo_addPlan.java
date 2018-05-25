package com.example.ahn.signinwithgoogle;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetDetailInfo_addPlan {
    private FirebaseAuth mAuth;
    private DatabaseReference addPlan;
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
    public void addPlanToTempDB(){
        mAuth = FirebaseAuth.getInstance();
        addPlan = FirebaseDatabase.getInstance().getReference();
        FirebaseUser mUser = mAuth.getCurrentUser();
        addPlan.child("Users").child(mUser.getUid().toString()).child("TravelTemp").setValue(data);
    }
}
