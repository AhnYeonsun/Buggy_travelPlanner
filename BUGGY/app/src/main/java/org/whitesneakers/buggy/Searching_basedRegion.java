package org.whitesneakers.buggy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

//Description : Search the specific region's tour data and save the hash map//
public class Searching_basedRegion {
    private String region = "", sigungu = "", contentTypeID = ""; //String value region, sigungu that user's selected
    private GetArea regionCodeO; //class object of GetArea
    private final String searchType = "areaBasedList";
    private String areaCode = "", sigunguCode = "";
    private String urlText = "";
    public HashMap<String, String[]> tourList = new HashMap<>();
    private String addr="", ID="", img="", mapX="", mapY="", tel="", title="", typeID="";
    //addr=address of tour object, ID=unique ID, img=representative image, mapX=x coordinate, mapY=y coordinate, tel=tel number, title=name of tour object, typeID=type of tour object


    public Searching_basedRegion(String region, String sigungu, String contentTypeID, GetArea regionCode, HashMap<String, String[]> hashmap) { //constructor
        this.region = region;
        this.sigungu = sigungu;
        this.contentTypeID = contentTypeID;
        this.regionCodeO = regionCode;
        this.tourList = hashmap;
    }

    // Description : return the search type for make URL //
    // Input : none //
    // Output : search type = "areaBasedList" //
    public String getSearchType() {
        return this.searchType;
    }

    // Description : return the areaCode for make URL //
    // Input : none //
    // Output : area code value that user selected //
    public String getAreaCode() {
        return this.areaCode;
    }

    // Description : return the sigunguCode for make URL //
    // Input : none //
    // Output : sigungu code value that user selected //
    public String getSigunguCode() {
        return this.sigunguCode;
    }

    public String getContentTypeID(){return this.contentTypeID;}

    // Description : bring the URL from GetURL class //
    // Input : none //
    // Output : none //
    public void setURL() {
        this.urlText = new GetURL(this).integrateURL(1);
    }

    // Description : find the region, singungu code //
    // Input : none //
    // Output : none //
    public void searchRegionCode() {
        Iterator<String> iterator1 = regionCodeO.regionHashMap.keySet().iterator();
        Iterator<String> iterator2 = regionCodeO.sigunguHashMap.keySet().iterator();

        //while loop for search the region code in regionCodeHashMap
        while (iterator1.hasNext()) {
            String key = (String) iterator1.next();
            //System.out.println("Key test :::: "+this.region);
            if (key.equals(this.region)) {
                areaCode = regionCodeO.regionHashMap.get(key);
                break;
            }
        }

        //while loop for search the sigungu code in sigunguCodeHashMap
        while (iterator2.hasNext()) {
            String key = (String) iterator2.next();
            if (key.equals(this.sigungu)) {
                sigunguCode = regionCodeO.sigunguHashMap.get(key)[1];
            }
            //Log.i("CODE test :::", sigunguCodeHashMap.get(key) + key);
        }
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

                System.out.println(jObject);
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

        searchRegionCode();
        setURL();
        try {
            resultData = new ConnectionAPI(urlText).execute().get();
            dataParser((resultData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
