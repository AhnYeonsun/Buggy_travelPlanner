package com.example.soso.soso;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Recommend extends AppCompatActivity {
    final String[] contentType = {"관광지", "문화시설", "축제/공연/행사", "여행코스", "레포츠", "숙박", "쇼핑", "음식"}; //initial content type

    GetRegionCodeHashMap regionHashMap = new GetRegionCodeHashMap(); //object of GetRegionCodeHashMap() function
    RecomListViewAdapter adapter;
    ListView listview1;
    Button searchBtn, keywordBtn;
    Spinner regionList, sigunguList;
    String region = "", sigungu = "";
    EditText keywordEditText;
    AlertDialog.Builder builder;
    ArrayList<String> items2;
    ArrayAdapter<String> adapter2;
    String regionCode = new String();
    boolean check = false;
    String contentID = "", contentTypeID = "";
    SearchHashMap hashMapSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        builder = new AlertDialog.Builder(this);
        regionHashMap.getRegionCode();

        // ******* 지역 스피너 리스트 ********* //
        regionList = findViewById(R.id.regionList);
        ArrayList<String> items1 = new ArrayList<String>(Arrays.asList("")); //array list of spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items1);
        // add to spinner list
        Iterator<String> iterator1 = regionHashMap.regionCodeHashMap.keySet().iterator();
        while (iterator1.hasNext()) {
            String key = (String) iterator1.next();
            adapter1.add(key);
        }
        adapter1.notifyDataSetChanged();
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionList.setAdapter(adapter1);
        // *************************************** //


        sigunguList = (Spinner) findViewById(R.id.sigunguList); //spinner of sigungu list

        searchBtn = findViewById(R.id.searchBtn);
        items2 = new ArrayList<String>(Arrays.asList("")); //array list of spinner
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items2);


            regionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(check) {
                        items2.clear();
                        adapter2.clear();
                        sigunguList.setAdapter(adapter2);
                        regionHashMap.sigunguCodeHashMap.clear();
                        regionCode = new SearchHashMap(regionHashMap.regionCodeHashMap, parent.getItemAtPosition(position).toString()).searching();
                        regionHashMap.getSigunguCode(regionCode);

                        // ************** 시군구 스피너 리스트 ****************  //
                        // Add to spinner list
                        Iterator<String> iterator2 = regionHashMap.sigunguCodeHashMap.keySet().iterator();
                        while (iterator2.hasNext()) {
                            String key = (String) iterator2.next();
                            //Log.i("****Key test ***", key);
                            adapter2.add(key);
                        }

                        adapter2.notifyDataSetChanged();
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // ****************************************** //
                    }
                    else{
                        check=true;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    System.out.println("AAAAAAAAAAAAA");
                }
            });


        // 리스트뷰 참조 및 Adapter달기
        listview1 = findViewById(R.id.recom_listview1);

        adapter = new RecomListViewAdapter(this);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adapter 생성
                //adapter = new RecomListViewAdapter()
                adapter.clear();
                listview1.setAdapter(adapter);
                region = (String) regionList.getSelectedItem();
                sigungu = (String) sigunguList.getSelectedItem();

                Finding_AreaCode tour = new Finding_AreaCode(region, sigungu, regionHashMap);
                tour.main();

                Iterator<String> iterator3 = tour.tourList.keySet().iterator();

                while (iterator3.hasNext()) {
                    String title = "";
                    String addr = "", mapX="", mapY="",imgURL="";
                    int imgId = 0;

                    String key = (String) iterator3.next();

                    addr = tour.tourList.get(key)[0];
                    contentTypeID = tour.tourList.get(key)[1];
                    title = tour.tourList.get(key)[6];
                    mapX = tour.tourList.get(key)[3];
                    mapY = tour.tourList.get(key)[4];
                    imgURL = tour.tourList.get(key)[2];
                    imgId = classification(tour.tourList.get(key)[1]);

                    adapter.addItem(imgId, key, contentTypeID, title, addr, mapX, mapY, imgURL);//컨텐츠 타입, 이름, 주소 보내기, // 정보도 보내야할 것 같음
                    adapter.notifyDataSetChanged();
                }
            }
        });


        //////////////// 키워드 검색 //////////////////
        keywordEditText = (EditText) findViewById(R.id.keywordEdit);
        keywordBtn = (Button) findViewById(R.id.keywordBtn);
        keywordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                listview1.setAdapter(adapter);

                String keyword = keywordEditText.getText().toString();
                try {
                    keyword = URLEncoder.encode(keyword, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }

                region = (String) regionList.getSelectedItem();
                sigungu = (String) sigunguList.getSelectedItem();

                Finding_AreaCode find_areaCode = new Finding_AreaCode(region, sigungu, regionHashMap);
                find_areaCode.searchRegionCode();
                String regionCode = find_areaCode.getAreaCode();
                String sigunguCode = find_areaCode.getSigunguCode();

                KeywordSearching tour2 = new KeywordSearching(keyword,regionCode, sigunguCode);
                tour2.main();
                // Set result text
                Iterator<String> iterator4 = tour2.tourList.keySet().iterator();
                while (iterator4.hasNext()) {
                    String title = "";
                    String addr = "", mapX="", mapY="", imgURL="", contentTypeID="";
                    int imgId = 0;

                    String key = (String) iterator4.next();

                    addr = tour2.tourList.get(key)[0];
                    contentTypeID = tour2.tourList.get(key)[1];
                    title = tour2.tourList.get(key)[6];
                    mapX = tour2.tourList.get(key)[3];
                    mapY = tour2.tourList.get(key)[4];
                    imgURL = tour2.tourList.get(key)[2];
                    imgId = classification(tour2.tourList.get(key)[1]);

                    adapter.addItem(imgId, key, contentTypeID, title, addr, mapX, mapY, imgURL);//컨텐츠 타입, 이름, 주소 보내기, // 정보도 보내야할 것 같음
                    adapter.notifyDataSetChanged();
                }
                // --------------------------------------------- //
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
                return false;
            }
        });
    }

    // 컨텐츠 타입 구분해주는 함수 //
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
