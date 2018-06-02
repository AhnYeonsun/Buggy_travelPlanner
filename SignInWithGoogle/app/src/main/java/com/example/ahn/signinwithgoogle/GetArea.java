package com.example.ahn.signinwithgoogle;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

// Description : Get region codes and save to hash map class //

public class GetArea {
    public HashMap<String, String> regionHashMap = new HashMap<>(); //Hash Map that saving the region code (etc 서울, 경기도...)
    public HashMap<String, String[]> sigunguHashMap = new HashMap<>(); //Hash Map that saving the 시,군,구 code(etc 강남구, 남양주시)

    final private String searchType = "areaCode";
    private String option = "null"; //For sigunguCode searching option. Will saves the areaCode
    private String urlText = ""; //Saves the URL
    private String resultText = ""; //Saves result of search
    private int[] codeArr = new int[17]; //Saves the integer of areaCode


    public GetArea() { } //constructor

    public String getSearchType() {
        return this.searchType;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return this.option;
    }

    public void setURL() {
        this.urlText = new GetURL(this).integrateURL(0); //object of GetURL.interagetURL()
    }


    // Description : get region code function. //
    public void getRegionCode() {
        String resultData = ""; //string value for saving the data

        setOption("null"); //setting the option value for "null"
        setURL();
        try {
            resultData = new ConnectionAPI(urlText).execute().get();
            //Log.i("result data ::: ", resultData);
            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    // Description : get sigungu code function. //
    public void getSigunguCode(String region) {
        Iterator<String> iterator = regionHashMap.keySet().iterator(); //iterator for regionCodeHashMap
        String resultData = ""; //string value for saving the data

        setOption("areaCode=" + region);
        setURL();
        try {
                resultData = new ConnectionAPI(urlText).execute().get();
                dataParser((resultData));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        //for loop that number of region codes
//        for (int i = 0; i < codeArr.length; i++) {
//            option = "areaCode=" + Integer.toString(codeArr[i]); //setting the option value "areaCode=(number of areaCode)
//            //Log.i("option test :::", option);
//            setURL(); //setting the option value
//            try {
//                resultData = new ConnectionAPI(urlText).execute().get();
//                dataParser((resultData));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
    }


    // Description : parsing the data //
    // Input : String value the data //
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
                //Log.i("Code test :::: ", code);
                //Log.i("Name test :::: ", name);

                //Get the region code//
                if(getOption().equals("null")) {
                    regionHashMap.put(name, code); //add data to hash map
                    codeArr[i] = Integer.parseInt(code); //put the region code data(integer) to codeArr
                    Log.i("codeArr test ::: ", name);
                }
                else{ //Get the sigungu code//
                    hashValue[0] = option;
                    hashValue[1] = code;
                    Log.i("CODE test :::", name);
                    sigunguHashMap.put(name,hashValue); //add data to hash map
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

