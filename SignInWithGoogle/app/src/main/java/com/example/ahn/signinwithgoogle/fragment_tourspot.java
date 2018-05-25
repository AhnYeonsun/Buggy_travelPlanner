package com.example.ahn.signinwithgoogle;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class fragment_tourspot extends android.support.v4.app.Fragment {
    View view, main;
    GetArea getArea = new GetArea(); //object of GetArea() function
    RecomListViewAdapter listViewAdapter;
    ListView listview1;
    //Button searchBtn;
    private String region = "", sigungu = "", contentTypeID = "";
    private String key="", title="", addr="", mapX="", mapY="", imgURL="";
    private int imgId = 0;
    AlertDialog.Builder builder;
    fragment_Recommend FR;
    Handler handler;
    HashMap<String, String[]> tourspot = new HashMap<>();
    ArrayList<RecomListViewItem> recomlistViewItemList =new ArrayList<RecomListViewItem>();

    Bundle bundle;
    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_tourspot, container, false);
        listview1 = view.findViewById(R.id.recom_tourspot);

        //listViewAdapter = new RecomListViewAdapter(getActivity());
        listViewAdapter = new RecomListViewAdapter(recomlistViewItemList);// Adapter 생성

        FR = (fragment_Recommend) getActivity();
        getArea = FR.getObject();
        //searchBtn = FR.findViewById(R.id.searchBtn);
        //searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
        recomlistViewItemList.clear();
        listview1.setAdapter(listViewAdapter);

        region = FR.getRegionItem();
        sigungu = FR.getSigunguItem();
        bundle = getArguments();
        contentTypeID = bundle.getString("contentTypeID");
        Searching_basedRegion tour = new Searching_basedRegion(region, sigungu, contentTypeID, getArea, tourspot);
        tour.main();

        Iterator<String> iterator3 = tourspot.keySet().iterator();

        while (iterator3.hasNext()) {
            String title = "";
            String addr = "", mapX = "", mapY = "", imgURL = "";
            int imgId = 0;

            String key = (String) iterator3.next();
            Log.d("asasasasa", tourspot.get(key)[6]);
            addr = tourspot.get(key)[0];
            contentTypeID = tourspot.get(key)[1];
            title = tourspot.get(key)[6];
            mapX = tourspot.get(key)[3];
            mapY = tourspot.get(key)[4];
            imgURL = tourspot.get(key)[2];
            imgId = R.drawable.attraction;

            listViewAdapter.addItem(imgId, key, contentTypeID, title, addr, mapX, mapY, imgURL);//컨텐츠 타입, 이름, 주소 보내기, // 정보도 보내야할 것 같음
            listViewAdapter.notifyDataSetChanged();


        }
        listview1.setAdapter(listViewAdapter);


        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                final RecomListViewItem item = (RecomListViewItem) new RecomListViewAdapter().getItem(position);

                GetDetailInfo getDetailInfo = new GetDetailInfo(item.getContentID(), item.getContentTypeID());
                getDetailInfo.main();

                String message = "";

                Iterator<String> iterator = getDetailInfo.detailInfoHashMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    message += key + getDetailInfo.detailInfoHashMap.get(key) + "\n";
                }

                builder = new AlertDialog.Builder(getActivity());
                final String finalMessage = message;
                builder.setTitle(item.getName())
                        .setIcon(item.getMainImg())  //이게 사진 받는 함수고
                        .setMessage(item.getAddress() + "\n" + message) //이게 정보 받아주는 함수
                        //********************************요기에 욘또니가 지도 넣어주면되염 화이또!!!!!!***********//
                        //*****좌표 X 받아오는 함수 : item.getMapX()  **************//
                        //*****좌표 Y 받아오는 함수 : item.getMapY() ***************//
                        .setCancelable(false)

                        //******************************여기갈랭! 버튼을 누르면 데이터 베이스에 넣어주기*******************//
                        .setPositiveButton("여기갈랭!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new GetDetailInfo_addPlan(item, finalMessage); //여기서 item은 내가 선택한 관광지, finalMessage는 관광지의 디테일 정보들
                            }
                        })
                        .setNegativeButton("안가 별루얌", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("daummaps://look?p="+item.getMapX()+","+item.getMapY())));
                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();
                return true;
            }
        });
        return view;
    }
}
