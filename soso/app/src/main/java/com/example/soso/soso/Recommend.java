package com.example.soso.soso;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Recommend extends AppCompatActivity {
    final String[] contentType = {"관광지", "문화시설", "축제/공연/행사", "여행코스", "레포츠", "숙박", "쇼핑", "음식"}; //initial content type

    getRegionHashMap regionHashMap = new getRegionHashMap(); //object of getRegionHashMap() function
    RecomListViewAdapter adapter;
    ListView listview1;
    Button searchBtn;
    Spinner regionList, sigunguList;
    String region = "", sigungu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        regionHashMap.main();

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

        // ************** Set the sigungu list **************** //
        sigunguList = (Spinner) findViewById(R.id.sigunguList); //spinner of sigungu list
        ArrayList<String> items2 = new ArrayList<String>(Arrays.asList("")); //array list of spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items2);

        // Add to spinner list
        Iterator<String> iterator2 = regionHashMap.sigunguCodeHashMap.keySet().iterator();
        while (iterator2.hasNext()) {
            String key = (String) iterator2.next();
            adapter2.add(key);
        }
        adapter2.notifyDataSetChanged();
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sigunguList.setAdapter(adapter2);
        // ****************************************** //

        searchBtn=findViewById(R.id.searchBtn);
        regionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchBtn.setText("선택된 지역: "+parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Adapter 생성


        // 리스트뷰 참조 및 Adapter달기
        listview1 = findViewById(R.id.recom_listview1);

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter = new RecomListViewAdapter();
                listview1.setAdapter(adapter);
                region = (String)regionList.getSelectedItem();
                sigungu = (String)sigunguList.getSelectedItem();

                regionSearch tour = new regionSearch(region, sigungu, regionHashMap); // object of regionSearch class
                tour.main();

                // Set result text
                Iterator<String> iterator3 = tour.tourList.keySet().iterator();
                String infoText = "";
                while (iterator3.hasNext()) {
                    String key = (String) iterator3.next();

                    infoText += "ID : " + key + "\naddr1 : " + tour.tourList.get(key)[0] + "\n 컨텐츠 타입 : "+  classification(tour.tourList.get(key)[1])+
                            " \n이미지 : " + tour.tourList.get(key)[2] + " \n좌표 X : " + tour.tourList.get(key)[3] +
                            "   좌표 Y : " + tour.tourList.get(key)[4]+ " \ntel : "+ tour.tourList.get(key)[5]+
                            " \ntitle : "+ tour.tourList.get(key)[6]+"\n";

                    adapter.addItem(tour.tourList.get(key)[2],infoText);

                    //Log.i("CODE test :::", sigunguCodeHashMap.get(key) + key);
                }

            }
        });
    }

    // Description : classify the content type //
    // Input : integer content type formed string //
    // Output : String content type formed string //
    public String classification(String content) {
        int contentID = Integer.parseInt(content);

        switch(contentID){
            case 12:
                return contentType[0];
            case 14:
                return contentType[1];
            case 15:
                return contentType[2];
            case 25:
                return contentType[3];
            case 28:
                return contentType[4];
            case 32:
                return contentType[5];
            case 38:
                return contentType[6];
            case 39:
                return contentType[7];
        }
        return "";
    }
}