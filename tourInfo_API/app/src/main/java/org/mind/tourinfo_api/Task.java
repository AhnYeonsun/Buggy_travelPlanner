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
    //String clientKey = "Rc%2FaF%2FUtrBXVonYiqMAc0tttIxuHrlVynLgI7aWtcpTsIbk5rzsrUPA8fSqT0MYU%2BaE3xMZ2jtX3jxf220chCg%3D%3D";
    private String str, receiveMsg;
    private final String ID = "Rc%2FaF%2FUtrBXVonYiqMAc0tttIxuHrlVynLgI7aWtcpTsIbk5rzsrUPA8fSqT0MYU%2BaE3xMZ2jtX3jxf220chCg%3D%3D";
    HashMap<String,String> map = new HashMap<>();

    @Override
    protected String doInBackground(String... params){

        try{
            URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode?ServiceKey="+ID+"&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=TestApp&_type=json");
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestProperty("resultCode", "0000");
            connect.setRequestProperty("resultMsg", "OK");
//            connect.setRequestProperty("Service-Name","국문 관광정보 서비스");
//            connect.setRequestProperty("Service-Type","REST");
//            connect.setRequestProperty("Content-Type", "application/json");
//            connect.setRequestProperty("Response-Time","0");


            if(connect.getResponseCode()==connect.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(connect.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while((str=reader.readLine())!=null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            }
            else{
                Log.i("result is ", connect.getResponseCode()+ " error");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return receiveMsg;
    }

    public String[][] dataParser(String jsonString) {
        String code = null, name = null, rnum = null;
        String item = null, numOfRows = null, pageNo = null, totalCount = null;

        String[][] array = new String[100][3];


        try{
            JSONObject jObjectT = new JSONObject(jsonString);
            jObjectT = jObjectT.getJSONObject("response");
            jObjectT = jObjectT.getJSONObject("body");
            jObjectT = jObjectT.getJSONObject("items");

            JSONArray jarray = jObjectT.getJSONArray("item");


            for(int i=0;i<jarray.length();i++) {
                // Log.i("CheckCheck", code);

                JSONObject jObject = jarray.getJSONObject(i);

                System.out.println(jObject);
                code = jObject.optString("code");
                name = jObject.optString("name");
                rnum = jObject.optString("rnum");

                array[i][0] = code;
                array[i][1] = name;
                array[i][2] = rnum;
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return array;
    }
}