package org.mind.tourinfo_api;

import android.util.Log;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

// Description : Get region codes and save to hash map class //

public class getRegionHashMap {
    public HashMap<String, String> regionCodeHashMap = new HashMap<>(); //Hash Map that saving the region code (etc 서울, 경기도...)
    public HashMap<String, String[]> sigunguCodeHashMap = new HashMap<>(); //Hash Map that saving the 시,군,구 code(etc 강남구, 남양주시)

    final private String searchType = "areaCode";
    private String option = ""; //For sigunguCode searching option. Will saves the areaCode
    private String urlText = ""; //Saves the URL
    private String resultText = ""; //Saves result of search
    private int[] codeArr = new int[10]; //Saves the integer of areaCode


    public getRegionHashMap() { } //constructor

    // Description : return the string searchType value. references maekURL class //
    //input : none //
    // output : search type(areaCode)
    public String getSearchType() {
        return this.searchType;
    }

    // input : string option //
    // output : none //
    public void setOption(String option) {
        this.option = option;
    }

    // Description : return the string option value. references makeURL class //
    // input : none //
    // output : string option //
    public String getOption() {
        return this.option;
    }

    // description : bring the URL from makeURL class //
    // input : none //
    // output : none //
    public void setURL() {
        this.urlText = new makeURL(this).integrateURL(0); //object of makeURL.interagetURL()
    }

    // Description : get region code function. //
    // Input : none //
    // Output : none //
    public void getRegionCode() {
        String resultData = ""; //string value for saving the data

        setOption("null"); //setting the option value for "null"
        setURL();
        try {
            resultData = new Task(urlText).execute().get();
            Log.i("result data ::: ", resultData);
            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Description : get sigungu code function. //
    // Input : none //
    // Output : none //
    public void getSigunguCode() {
        Iterator<String> iterator = regionCodeHashMap.keySet().iterator(); //iterator for regionCodeHashMap
        String resultData = ""; //string value for saving the data

        //for loop that number of region codes
        for (int i = 0; i < codeArr.length; i++) {
            option = "areaCode=" + Integer.toString(codeArr[i]); //setting the option value "areaCode=(number of areaCode)
            //Log.i("option test :::", option);
            setURL(); //setting the option value
            try {
                resultData = new Task(urlText).execute().get();
                dataParser((resultData));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    //List view 만들면 여기서는 필요없을듯 //
//    protected String setText(String... strings) {
//            Iterator<String> iterator1 = regionCodeHashMap.keySet().iterator();
//            Iterator<String> iterator2 = sigunguCodeHashMap.keySet().iterator();
//            while (iterator1.hasNext()) {
//                //System.out.println("AAAAAAAAAAAAAAAAAA");
//                String key = (String) iterator1.next();
//                resultText += "Code : " + key + "      value : " + regionCodeHashMap.get(key) + "\n";
//                //Log.i("CODE test :::", regionCodeHashMap.get(key) + key);
//                //  Log.i("Print testing ::: ",option);
//                //}
//            }
//
//            while (iterator2.hasNext()) {
//                String key = (String) iterator2.next();
//                resultText += "Code : " + key + "      value : " + sigunguCodeHashMap.get(key)[0] + "  "+  sigunguCodeHashMap.get(key)[1]+"\n";
//                Log.i("CODE test :::", sigunguCodeHashMap.get(key)[1] + key);
//            }
//        return resultText;
//    }

    // Description : parsing the data //
    // Input : String value the data //
    // Output : none //
    public void dataParser(String jsonString) {
        try {
            JSONObject jObjectT = new JSONObject(jsonString);
            jObjectT = jObjectT.getJSONObject("response"); //take off "response" tag
            jObjectT = jObjectT.getJSONObject("body"); //take off "body" tag
            jObjectT = jObjectT.getJSONObject("items"); //take off "items" tag

            JSONArray jarray = jObjectT.getJSONArray("item"); //saving the "item" tag data to array[] jarray
            //System.out.println(jObjectT);

            //for loop that saving "code" tag and "name" tag data to hash map
            for (int i = 0; i < jarray.length(); i++) {
                String code = "", name = ""; //String values that saving "code" tag, "name" tag
                String[] hashValue = new String[2]; //String array value that saving codeArea and "name" tag

                JSONObject jObject = jarray.getJSONObject(i); //get object from jarray[i]

                //System.out.println(jObject);
                code = jObject.optString("code"); //get tag "code" data
                name = jObject.optString("name"); //get tag "name" data

                //Get the region code//
                if(option.equals("null")) {
                    regionCodeHashMap.put(name, code); //add data to hash map
                    codeArr[i] = Integer.parseInt(code); //put the region code data(integer) to codeArr
                    //Log.i("codeArr test ::: ", codeArr[i]+"");
                }
                else{ //Get the sigungu code//
                    hashValue[0] = option;
                    hashValue[1] = code;
                    Log.i("CODE test :::", hashValue[1]);
                    sigunguCodeHashMap.put(name,hashValue); //add data to hash map
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //main function
    public void main() {
        getRegionCode();
        getSigunguCode();

        //setText();
    }
}

