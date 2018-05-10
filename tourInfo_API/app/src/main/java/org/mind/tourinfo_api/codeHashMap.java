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


public class codeHashMap {
    HashMap<String, String> regionCodeHashMap = new HashMap<>();
    HashMap<String, String> sigunguCodeHashMap = new HashMap<>();

    final private String searchType = "areaCode";
    private String option = "";
    private String urlText = "", resultText = "";
    private int[] codeArr = new int[10];

    public codeHashMap() {

    }


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
        this.urlText = new makeURL(this).integrateURL();
    }

    public String getURL() {
        return urlText;
    }

    public void regionCode() {
        setOption("null");
        setURL();
    }

    public void sigunguCode() {
        Iterator<String> iterator = regionCodeHashMap.keySet().iterator();
        String resultData = null;

        for (int i = 0; i < codeArr.length; i++) {
            //System.out.println("AAAAAAAAAAAA");
            option = "areaCode=" + Integer.toString(codeArr[i]);
            Log.i("option test :::", option);
            setURL();
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

    public void dataParser(String jsonString) {
        String code = null, name = null;
        //String item = null, numOfRows = null, pageNo = null, totalCount = null;

        try {
            JSONObject jObjectT = new JSONObject(jsonString);
            jObjectT = jObjectT.getJSONObject("response");
            jObjectT = jObjectT.getJSONObject("body");
            jObjectT = jObjectT.getJSONObject("items");

            JSONArray jarray = jObjectT.getJSONArray("item");

            for (int i = 0; i < jarray.length(); i++) {
                //System.out.println("jarray length test :: " + jarray.length());
                JSONObject jObject = jarray.getJSONObject(i);

                //System.out.println(jObject);
                code = jObject.optString("code");
                name = jObject.optString("name");


                if(option.equals("null")) {
                    regionCodeHashMap.put(name, code);
                    //System.out.println(jObject);
                    //codeArr[i] = code;
                    System.out.println(code);
                    codeArr[i] = Integer.parseInt(code);

                }
                else{
                    sigunguCodeHashMap.put(name,code);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setText() {
        Iterator<String> iterator1 = regionCodeHashMap.keySet().iterator();
        Iterator<String> iterator2 = sigunguCodeHashMap.keySet().iterator();
        while (iterator1.hasNext()) {
            //System.out.println("AAAAAAAAAAAAAAAAAA");
            String key = (String) iterator1.next();
            resultText += "Code : " + key + "      value : " + regionCodeHashMap.get(key) + "\n";
            //Log.i("CODE test :::", regionCodeHashMap.get(key) + key);
            //  Log.i("Print testing ::: ",option);
            //}
        }

        while (iterator2.hasNext()) {
            String key = (String) iterator2.next();
            resultText += "Code : " + key + "      value : " + sigunguCodeHashMap.get(key) + "\n";
            //Log.i("CODE test :::", sigunguCodeHashMap.get(key) + key);
        }
    }

    public String main() {
        String resultData = null;

        regionCode();
        try {
            resultData = new Task(urlText).execute().get();

            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        sigunguCode();

        setText();

        return resultText;
    }

}

