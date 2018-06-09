package com.example.ahn.signinwithgoogle;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class GetDetailInfo {

    private final String searchType = "detailIntro";
    private String contentID = "", contentTypeID = "";
    private String urlText = "";
    public HashMap<String, String> detailInfoHashMap = new HashMap<>();

    public GetDetailInfo(String contentID, String contentTypeID) {
        this.contentID = contentID;
        this.contentTypeID = contentTypeID;
        //this.detailInfoHashMap.clear();
    } //constructor


    // Description : return the search type for make URL //
    // Input : none //
    // Output : search type = "areaBasedList" //
    public String getSearchType() {
        return this.searchType;
    }


    // Description : return the content ID for make URL //
    // Input : none //
    // Output : contentID //
    public String getContentID() {
        return this.contentID;
    }


    // Description : return the content type ID for make URL //
    // Input : none //
    // Output : contentTypeID //
    public String getContentTypeID() {
        return this.contentTypeID;
    }


    // Description : bring the URL from makeURL class //
    // Input : none //
    // Output : none //
    public void setURL() {
        this.urlText = new GetURL(this).integrateURL(3);
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
            jObjectT = jObjectT.getJSONObject("item");
            //JSONArray jarray = jObjectT.getJSONArray("item");
            //System.out.println(jObjectT);
            //Log.i("GGGGGGGGGGG", jarray.length()+"");
            //for loop that saving tour object data to hash map
           // for (int i = 0; i < jarray.length(); i++) {
                //JSONObject jObject = jarray.getJSONObject(0);

               // Log.i("GGGGGGGGGGG", jObject.optString("contenttypeid"));

                switch (jObjectT.optString("contenttypeid")) {
                    case "76":
                        detailInfoHashMap.put("< Info center >\n ", removeTag(jObjectT.optString("infocenter")+"\n"+"\n"));
                        detailInfoHashMap.put("< Opening date >\n ", removeTag(jObjectT.optString("opendate")+"\n"+"\n"));
                        detailInfoHashMap.put("< Closed >\n", removeTag(jObjectT.optString("restdate")+"\n"));
                        detailInfoHashMap.put("< Operation time >\n ", removeTag(jObjectT.optString("usestime")+"\n"+"\n"));
                        detailInfoHashMap.put("< Use season >\n", removeTag(jObjectT.optString("useseason")+"\n"+"\n"));
                        break;

                    case "78":
                        detailInfoHashMap.put("< Info center > \n", removeTag(jObjectT.optString("infocenterculture")+"\n\n")) ;
                        detailInfoHashMap.put("< Closed > \n", removeTag(jObjectT.optString("restdate")+"\n\n"));
                        detailInfoHashMap.put("< Operation time > \n", removeTag(jObjectT.optString("usetimeculture")+"\n\n"));
                        detailInfoHashMap.put("< Admission > \n", removeTag(jObjectT.optString("usefee")+"\n\n"));
                        detailInfoHashMap.put("< Discount Info >\n" , removeTag(jObjectT.optString("discountinfo")+"\n\n"));
                        detailInfoHashMap.put("< Operation time >\n", removeTag(jObjectT.optString("usetimeculture")+"\n\n"));
                        break;

                    case "85":
                        detailInfoHashMap.put("< Place >\n" , removeTag(jObjectT.optString("eventplace")+"\n\n"));
                        detailInfoHashMap.put("< Start date >\n" , removeTag(jObjectT.optString("eventstartdate")+"\n\n"));
                        detailInfoHashMap.put("< End date >\n" , removeTag(jObjectT.optString("eventenddate")+"\n\n"));
                        detailInfoHashMap.put("< Homepage > \n" , removeTag(jObjectT.optString("eventhomepage")+"\n\n"));
                        detailInfoHashMap.put("< Booking >\n" , removeTag(jObjectT.optString("bookingplace")+"\n\n"));
                        detailInfoHashMap.put("< Spending time >\n" , removeTag(jObjectT.optString("spendtimefestival")+"\n\n"));
                        detailInfoHashMap.put("< Discount Info >\n " , removeTag(jObjectT.optString("discountinfofestival")+"\n\n"));
                        break;

                    case "75":
                        detailInfoHashMap.put("< Info center >\n" , removeTag(jObjectT.optString("infocenterleports")+"\n\n"));
                        detailInfoHashMap.put("< Open period >\n" , removeTag(jObjectT.optString("openperiod")+"\n\n"));
                        detailInfoHashMap.put("< Closed >\n" , removeTag(jObjectT.optString("restdateleports")+"\n\n"));
                        detailInfoHashMap.put("< Spending time >\n" , removeTag(jObjectT.optString("usetimeleports")+"\n\n"));
                        detailInfoHashMap.put("< Reservation >\n" , removeTag(jObjectT.optString("reservation")+"\n\n"));
                        detailInfoHashMap.put("< Admission >\n" , removeTag(jObjectT.optString("usefeeleports")+"\n\n"));
                        break;

                    case "80":
                        detailInfoHashMap.put("< Info center >\n " , removeTag(jObjectT.optString("infocenterlodging")+"\n\n"));
                        detailInfoHashMap.put("< Booking >\n " , removeTag(jObjectT.optString("reservationurl")+"\n\n"));
                        detailInfoHashMap.put("< Room type >\n " , removeTag(jObjectT.optString("roomtype")+"\n\n"));
                        detailInfoHashMap.put("< Room type >\n" , removeTag(jObjectT.optString("roomtype")+"\n\n"));
                        detailInfoHashMap.put("< Can cooking? >\n" , removeTag(jObjectT.optString("chkcooking")+"\n\n"));
                        detailInfoHashMap.put("< facility >\n" , removeTag(jObjectT.optString("subfacility")+"\n\n"));
                        break;

                    case "79":
                        detailInfoHashMap.put("< Info center >\n" , removeTag(jObjectT.optString("infocentershopping")+"\n\n"));
                        detailInfoHashMap.put("< Open date >\n" , removeTag(jObjectT.optString("opendateshopping")+"\n\n"));
                        detailInfoHashMap.put("< Closed >\n" , removeTag(jObjectT.optString("restdateshopping")+"\n\n"));
                        detailInfoHashMap.put("< Operation time >\n" , removeTag(jObjectT.optString("opentime")+"\n\n"));
                        detailInfoHashMap.put("< Fair day >\n " , removeTag(jObjectT.optString("fairday")+"\n\n"));
                        break;

                    case "82":
                        detailInfoHashMap.put("< Info center >\n" , removeTag(jObjectT.optString("infocenterfood")+"\n\n"));
                        detailInfoHashMap.put("< Open time >\n" , removeTag(jObjectT.optString("opentimefood")+"\n\n"));
                        detailInfoHashMap.put("< Closed >\n" , removeTag(jObjectT.optString("restdatefood")+"\n\n"));
                        detailInfoHashMap.put("< Main menu >\n" , removeTag(jObjectT.optString("firstmenu")+"\n\n"));
                        detailInfoHashMap.put("< Discount Info >\n" , removeTag(jObjectT.optString("discountinfofood")+"\n\n"));
                        detailInfoHashMap.put("< Can credit card? >\n" , removeTag(jObjectT.optString("chkcreditcardfood")+"\n\n"));
                        break;
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // main function //
    public void main() {
        String resultData = "";

        setURL();
        try {
            resultData = new ConnectionAPI(urlText).execute().get();
            dataParser(resultData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String removeTag(String html) throws Exception {
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }


}
