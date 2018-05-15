package com.example.soso.soso;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// Description : connecting the API server and bring the data from API database
public class connectAPI extends AsyncTask<String, Void, String> {
    private String url; //String value for URL
    private String str, receiveMsg;

    // constructor //
    public connectAPI(String urlText){
        this.url = urlText;
    }

    protected String doInBackground(String... params) {
        StrictMode.enableDefaults(); //ANR prevention of occurrence
        try {
            URL url = new URL(this.url); //object of URL

            //Log.i("URL test ::: ", url.toString());
            HttpURLConnection connect = (HttpURLConnection) url.openConnection(); //HTTP connection

            connect.setRequestProperty("Service-Name", "국문 관광정보 서비스");
            connect.setRequestProperty("Service-Type", "REST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Response-Time", "0");

            //If HTTP connection is successful
            if (connect.getResponseCode() == connect.HTTP_OK) {
                //Log.i("URL test ::: ", url.toString());
                InputStreamReader tmp = new InputStreamReader(connect.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                //Log.i("receiveMsg : ", receiveMsg);

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
