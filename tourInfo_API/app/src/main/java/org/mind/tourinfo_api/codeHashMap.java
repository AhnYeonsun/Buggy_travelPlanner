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
    HashMap<String, String> sigunguCodeMap = new HashMap<>();

    final private String searchType = "areaCode";
    private String option = null;
    private String urlText = null, resultText = null;

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

                regionCodeHashMap.put(name, code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setText() {
        Iterator<String> iterator = regionCodeHashMap.keySet().iterator();

        while (iterator.hasNext()) {
            System.out.println("AAAAAAAAAAAAAAAAAA");
            String key = (String) iterator.next();
            resultText += "Code : " + key + "      value : " + regionCodeHashMap.get(key) + "\n";
            Log.i("CODE test :::", regionCodeHashMap.get(key) + key);

            //  Log.i("Print testing ::: ",option);
            //}
        }
    }

    public String main() {
        String resultData = null;

        regionCode();
        setURL();

        try {
            resultData = new Task(urlText).execute().get();
            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setText();

        return resultText;
    }

}

