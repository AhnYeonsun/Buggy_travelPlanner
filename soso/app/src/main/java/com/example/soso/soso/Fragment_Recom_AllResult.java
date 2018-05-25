package com.example.soso.soso;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Iterator;

public class Fragment_Recom_AllResult extends android.support.v4.app.Fragment {
    View view, main;
    GetArea getArea = new GetArea(); //object of GetArea() function
    RecomListViewAdapter listViewAdapter;
    ListView listview1;
    Button searchBtn;
    private String region = "", sigungu = "";
    AlertDialog.Builder builder;
    String contentTypeID = "";
    fragment_Recommend FR;

    public Fragment_Recom_AllResult(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__searchl, container, false);
        listview1 = view.findViewById(R.id.recom_listview1);

        listViewAdapter = new RecomListViewAdapter(this);
        listViewAdapter = new RecomListViewAdapter();// Adapter 생성


        FR = (fragment_Recommend) getActivity();
        getArea = FR.getObject();
        searchBtn = FR.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listViewAdapter.clear();
                listview1.setAdapter(listViewAdapter);

                region = FR.getRegionItem();
                sigungu = FR.getSigunguItem();

                Searching_basedRegion tour = new Searching_basedRegion(region, sigungu, getArea);
                tour.main();

                Iterator<String> iterator3 = tour.tourList.keySet().iterator();

                while (iterator3.hasNext()) {
                    String title = "";
                    String addr = "", mapX = "", mapY = "", imgURL = "";
                    int imgId = 0;

                    String key = (String) iterator3.next();

                    addr = tour.tourList.get(key)[0];
                    contentTypeID = tour.tourList.get(key)[1];
                    title = tour.tourList.get(key)[6];
                    mapX = tour.tourList.get(key)[3];
                    mapY = tour.tourList.get(key)[4];
                    imgURL = tour.tourList.get(key)[2];
                    imgId = classification(tour.tourList.get(key)[1]);

                    listViewAdapter.addItem(imgId, key, contentTypeID, title, addr, mapX, mapY, imgURL);//컨텐츠 타입, 이름, 주소 보내기, // 정보도 보내야할 것 같음
                    listViewAdapter.notifyDataSetChanged();
                }
            }
        });

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
                        .setMessage(item.getAddress()+"\n"+message) //이게 정보 받아주는 함수
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
                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();
                return true;
            }
        });
        return view;
    }

    public void view_listData() {
        // 리스트뷰 참조 및 Adapter달기



    }

    public int classification(String content) {
        int temp = 0;

        switch (content) {
            case "12":
                temp = R.drawable.attraction;//관광지
                break;
            case "14":
                temp = R.drawable.culture; //문화시설
                break;
            case "15":
                temp = R.drawable.festival; //축제,공연,행사
                break;
            case "25":
                temp = R.drawable.travel; //여행코스
                break;
            case "28":
                temp = R.drawable.leisure; //레포츠
                break;
            case "32":
                temp = R.drawable.hotel; //숙박
                break;
            case "38":
                temp = R.drawable.shopping; //쇼핑
                break;
            case "39":
                temp = R.drawable.restaurant; //음식
                break;
        }
        return temp;
    }
}