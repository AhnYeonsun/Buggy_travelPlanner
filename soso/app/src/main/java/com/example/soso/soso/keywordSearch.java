package com.example.soso.soso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

//Description : Searching the keyword that user input //
public class KeywordSearch {
    private final String searchType = "searchKeyword";
    private String keyword = "";
    private String urlText = "";
    public HashMap<String, String[]> tourList = new HashMap<>();
    private String addr="", ID="", img="", mapX="", mapY="", tel="", title="", typeID="";
    //addr=address of tour object, ID=unique ID, img=representative image, mapX=x coordinate, mapY=y coordinate, tel=tel number, title=name of tour object, typeID=type of tour object

    public KeywordSearch(String keyword){
        this.keyword = keyword;
    } //constructor

    // Description : return the search type for make URL //
    // Input : none //
    // Output : search type = "areaBasedList" //
    public String getSearchType() {
        return this.searchType;
    }

    // Description : return the keyword for make URL //
    // Input : none //
    // Output : keyword //
    public String getKeyword(){return this.keyword;}

    // Description : bring the URL from MakeURL class //
    // Input : none //
    // Output : none //
    public void setURL() {
        this.urlText = new MakeURL(this).integrateURL(2);
    }

    // Description : parsing the data //
    // Input : String value the data //
    // Output : none //
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
                addr = jObject.optString("addr1");
                tourInfo[0] = addr;
                ID = jObject.optString("contentid");
                typeID = jObject.optString("contenttypeid");
                tourInfo[1] = typeID;
                img = jObject.optString("firstimage");
                tourInfo[2] = img;
                mapX = jObject.optString("mapx");
                tourInfo[3] = mapX;
                mapY = jObject.optString("mapy");
                tourInfo[4] = mapY;
                tel = jObject.optString("tel");
                tourInfo[5] = tel;
                title = jObject.optString("title");
                tourInfo[6] = title;

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

        setURL();
        try {
            resultData = new ConnectAPI(urlText).execute().get();
            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
