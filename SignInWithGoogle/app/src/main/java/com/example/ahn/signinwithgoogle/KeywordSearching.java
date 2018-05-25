package com.example.ahn.signinwithgoogle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

//Description : Searching the keyword that user input //
public class KeywordSearching {
    private final String searchType = "searchKeyword";
    private String keyword = "", regionCode = "", sigunguCode = "";
    private String urlText = "";
    public HashMap<String, String[]> tourList = new HashMap<>();
    private String addr="", ID="", img="", mapX="", mapY="", tel="", title="", typeID="";
    //addr=address of tour object, ID=unique ID, img=representative image, mapX=x coordinate, mapY=y coordinate, tel=tel number, title=name of tour object, typeID=type of tour object

    public KeywordSearching(String keyword, String regionCode, String sigunguCode){
        this.keyword = keyword;
        this.regionCode = regionCode;
        this.sigunguCode = sigunguCode;
    } //constructor

    public String getSearchType() {
        return this.searchType;
    }

    public String getKeyword(){return this.keyword;}

    public String getRegionCode(){return this.regionCode;}

    public String getSigunguCode(){return this.sigunguCode;}

    public void setURL() {
        this.urlText = new GetURL(this).integrateURL(2);
    }

    // 데이터 파싱 //
    public void dataParser(String jsonString) {
        try {
            JSONObject jObjectT = new JSONObject(jsonString);
            jObjectT = jObjectT.getJSONObject("response"); //take off "response" tag
            jObjectT = jObjectT.getJSONObject("body"); //take off "body" tag
            jObjectT = jObjectT.getJSONObject("items"); //take off "items" tag

            JSONArray jarray = jObjectT.getJSONArray("item");
            //System.out.println(jObjectT);

            //for loop that saving tour object data to hash map
            for (int i = 0; i < jarray.length(); i++) {
                String[] tourInfo = new String[7]; //String array for saving in the hash map
                JSONObject jObject = jarray.getJSONObject(i);

                //System.out.println(jObject);
                tourInfo[0] = jObject.optString("addr1");
                ID = jObject.optString("contentid");
                tourInfo[1] = jObject.optString("contenttypeid");
                tourInfo[2] = jObject.optString("firstimage");
                tourInfo[3] = jObject.optString("mapx");
                tourInfo[4] = jObject.optString("mapy");
                tourInfo[5] = jObject.optString("tel");
                tourInfo[6] = jObject.optString("title");

                tourList.put(ID,tourInfo); //add to tour list data
                //System.out.println(jObject);
                //codeArr[i] = code;
                //System.out.println(code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // main function //
    public void main() {
        String resultData = "";

        try {
            setURL();
            resultData = new ConnectionAPI(urlText).execute().get();
            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
