package org.mind.tourinfo_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    getRegionHashMap regionHashMap = new getRegionHashMap(); //object of getRegionHashMap() function
    String resultText = new String(); // set text string

    TextView result;
    Spinner regionList; //select box for region list
    Spinner sigunguList; //select box for sigungu list
    Button searchBtn; //Button for searching
    String region="", sigungu="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regionHashMap.main(); //get regionHashMap.main()

        // Set the region list //
        regionList = (Spinner)findViewById(R.id.regionList); //spinner of region list
        ArrayList<String> items1 =  new ArrayList<String>(Arrays.asList("")); //array list of spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items1);

        // add to spinner list
        Iterator<String> iterator1 = regionHashMap.regionCodeHashMap.keySet().iterator();
        while (iterator1.hasNext()) {
            //System.out.println("AAAAAAAAAAAAAAAAAA");
            String key = (String) iterator1.next();
            adapter1.add(key);
            //Log.i("CODE test :::", regionCodeHashMap.get(key) + key);
            //  Log.i("Print testing ::: ",option);
            //}
        }
        adapter1.notifyDataSetChanged();
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionList.setAdapter(adapter1);
        //------------------------------//

        // Set the sigungu list //
        sigunguList = (Spinner)findViewById(R.id.sigunguList); //spinner of sigungu list
        ArrayList<String> items2 =  new ArrayList<String>(Arrays.asList("")); //array list of spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items2);

        // Add to spinner list //
        Iterator<String> iterator2 = regionHashMap.sigunguCodeHashMap.keySet().iterator();
        while (iterator2.hasNext()) {
            //System.out.println("AAAAAAAAAAAAAAAAAA");
            String key = (String) iterator2.next();
            adapter2.add(key);
            //Log.i("CODE test :::", regionCodeHashMap.get(key) + key);
            //  Log.i("Print testing ::: ",option);
            //}
        }
        adapter2.notifyDataSetChanged();
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sigunguList.setAdapter(adapter2);
        //------------------------------//

        // set the result when user search region base //
//        result = (TextView) findViewById(R.id.tourInfo); //search result text
//        searchBtn = findViewById(R.id.searchBtn);
//        searchBtn.setOnClickListener(new View.OnClickListener() { //when user click the search button, put the user selected data
//            @Override
//            public void onClick(View v) {
//                resultText="";
//                region = (String)regionList.getSelectedItem();
//                sigungu = (String)sigunguList.getSelectedItem();
//
//                regionSearch tour = new regionSearch(region, sigungu, regionHashMap); // object of regionSearch class
//                tour.main();
//
//                // Set result text
//                Iterator<String> iterator3 = tour.tourList.keySet().iterator();
//                while (iterator3.hasNext()) {
//                    String key = (String) iterator3.next();
//                    resultText += "ID : " + key + "\n      addr1 : " + tour.tourList.get(key)[0] + " \n 컨텐츠 타입 : "+  tour.tourList.get(key)[1]+
//                            " \n   이미지 : " + tour.tourList.get(key)[2] + " \n      좌표 X : " + tour.tourList.get(key)[3] +
//                            "    좌표 Y : " + tour.tourList.get(key)[4]+ " \n    tel : "+ tour.tourList.get(key)[5]+
//                            " \n   title : "+ tour.tourList.get(key)[6]+"\n";
//                    //Log.i("CODE test :::", sigunguCodeHashMap.get(key) + key);
//                }
//                result.setText(resultText);
//            }
//        });
        // -------------------------------------------//

        // set the result when user search keyword //
        result = (TextView) findViewById(R.id.tourInfo);
        String keyword = "";
        try {
            keyword = URLEncoder.encode("강원", "UTF-8");
        }catch(UnsupportedEncodingException e){ };

        keywordSearch tour2 = new keywordSearch(keyword);
        tour2.main();
        // Set result text
        Iterator<String> iterator4 = tour2.tourList.keySet().iterator();
        while (iterator4.hasNext()) {
            String key = (String) iterator4.next();
            resultText += "ID : " + key + "\n      addr1 : " + tour2.tourList.get(key)[0] + " \n 컨텐츠 타입 : "+  tour2.tourList.get(key)[1]+
                    " \n   이미지 : " + tour2.tourList.get(key)[2] + " \n      좌표 X : " + tour2.tourList.get(key)[3] +
                    "    좌표 Y : " + tour2.tourList.get(key)[4]+ " \n    tel : "+ tour2.tourList.get(key)[5]+
                    " \n   title : "+ tour2.tourList.get(key)[6]+"\n";
            //Log.i("CODE test :::", sigunguCodeHashMap.get(key) + key);
        }
        result.setText(resultText);
        // --------------------------------------------- //
    }
}