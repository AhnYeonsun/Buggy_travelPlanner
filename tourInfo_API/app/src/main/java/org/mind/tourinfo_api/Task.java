package org.mind.tourinfo_api;

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


public class Task extends AsyncTask<String, Void, String> {
    private String url;
    private String str, receiveMsg;
    private String searchType, option;
    HashMap<String, String> regionCodeMap = new HashMap<>();

    public Task(String urlText){
        this.url = urlText;
    }

    protected String doInBackground(String... params) {

        try {
            URL url = new URL(this.url);

            //Log.i("URL test ::: ", url.toString());
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestProperty("Service-Name", "국문 관광정보 서비스");
            connect.setRequestProperty("Service-Type", "REST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Response-Time", "0");

            if (connect.getResponseCode() == connect.HTTP_OK) {
                //Log.i("URL test ::: ", url.toString());
                InputStreamReader tmp = new InputStreamReader(connect.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("result is ", connect.getResponseCode() + " error");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }


}
