package com.example.soso.soso;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Recommend extends AppCompatActivity {
    final String[] contentType = {"관광지", "문화시설", "축제/공연/행사", "여행코스", "레포츠", "숙박", "쇼핑", "음식"}; //initial content type

    GetRegionHashMap regionHashMap = new GetRegionHashMap(); //object of GetRegionHashMap() function
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
    SearchHashMap hashMapSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        builder = new AlertDialog.Builder(this);
        regionHashMap.getRegionCode();

        // ******* Set the region list ********* //
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

                        // ************** Set the sigungu list ****************  //
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

                RegionSearch tour = new RegionSearch(region, sigungu, regionHashMap); // object of RegionSearch class
                tour.main();

                // Set result text
                Iterator<String> iterator3 = tour.tourList.keySet().iterator();

                while (iterator3.hasNext()) {
                    String infoText = "";
                    String title = "";
                    String addr = "";
                    int imgId = 0;

                    String key = (String) iterator3.next();

                    infoText += "\n주소 : " + tour.tourList.get(key)[0] +
                            " \n좌표 X : " + tour.tourList.get(key)[3] +
                            "   좌표 Y : " + tour.tourList.get(key)[4] +
                            "\ntel : " + tour.tourList.get(key)[5];

                    addr = tour.tourList.get(key)[0];
                    title = tour.tourList.get(key)[6];
                    imgId = classification(tour.tourList.get(key)[1]);
                    //adapter.addItem(tour.tourList.get(key)[2],title);
                    adapter.addItem(imgId, title, addr, infoText);//컨텐츠 타입, 이름, 주소 보내기, // 정보도 보내야할 것 같음
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //////////////// set the result when user search keyword //////////////////
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

                KeywordSearch tour2 = new KeywordSearch(keyword);
                tour2.main();
                // Set result text
                Iterator<String> iterator4 = tour2.tourList.keySet().iterator();
                while (iterator4.hasNext()) {
                    String infoText = "";
                    String title = "";
                    String addr = "";
                    int imgId = 0;

                    String key = (String) iterator4.next();

                    infoText += "\n주소 : " + tour2.tourList.get(key)[0] +
                            " \n좌표 X : " + tour2.tourList.get(key)[3] +
                            "   좌표 Y : " + tour2.tourList.get(key)[4] +
                            "\ntel : " + tour2.tourList.get(key)[5];

                    addr = tour2.tourList.get(key)[0];
                    title = tour2.tourList.get(key)[6];
                    imgId = classification(tour2.tourList.get(key)[1]);
                    //adapter.addItem(tour.tourList.get(key)[2],title);
                    adapter.addItem(imgId, title, addr, infoText);//컨텐츠 타입, 이름, 주소 보내기, // 정보도 보내야할 것 같음
                    adapter.notifyDataSetChanged();
                }
                // --------------------------------------------- //
            }

        });

        ///!!!!!!!!!!!!!!!!!!!!!!여기 수정해야합니다!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                RecomListViewItem item = (RecomListViewItem) new RecomListViewAdapter().getItem(position); //여기가 내가 누른 리스트뷰가 어떤 건지 확인하는건데 주석 풀면 오류남... 왜 인지는 모르겟...ㅎ.ㅎ 미안.....
                builder.setTitle("세부 정보")
                        .setMessage(item.getInfo()) //정보 넣는 부분인데, 내 생각에는 정보도 어뎁터로 넘어가서 item.getInfo 같이 불러와야 될듯? RecomListViewItem에 추가해야함
                        .setCancelable(false)
                        .setPositiveButton("여기갈랭!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //스택에 추가해주는 내용, 디비에 넣는다는 등
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

    // Description : classify the content type //
    // Input : integer content type formed string //
    // Output : String content type formed string //
    public int classification(String content) {
        int contentID = Integer.parseInt(content);
        int temp = 0;

        switch (contentID) {
            case 12:
                temp = R.drawable.attraction;//관광지
                break;
            case 14:
                temp = R.drawable.culture; //문화시설
                break;
            case 15:
                temp = R.drawable.festival; //축제,공연,행사
                break;
            case 25:
                temp = R.drawable.travel; //여행코스
                break;
            case 28:
                temp = R.drawable.leisure; //레포츠
                break;
            case 32:
                temp = R.drawable.hotel; //숙박
                break;
            case 38:
                temp = R.drawable.shopping; //쇼핑
                break;
            case 39:
                temp = R.drawable.restaurant; //음식
                break;
        }
        return temp;
    }
}
