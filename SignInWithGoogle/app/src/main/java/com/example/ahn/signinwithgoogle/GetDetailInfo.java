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
                    case "12":
                        detailInfoHashMap.put("문의 및 안내 :", jObjectT.optString("infocenter"));
                        detailInfoHashMap.put("개장일 :", jObjectT.optString("opendate"));
                        detailInfoHashMap.put("쉬는 날 :", jObjectT.optString("restdate"));
                        detailInfoHashMap.put("이용 시기 :", jObjectT.optString("useseason"));
                        detailInfoHashMap.put("이용 시간 :", jObjectT.optString("usestime"));
                        break;

                    case "14":
                        detailInfoHashMap.put("할인 정보 :" , jObjectT.optString("discountinfo"));
                        detailInfoHashMap.put("문의 및 정보 :", jObjectT.optString("infocenterculture"));
                        detailInfoHashMap.put("쉬는 날 :", jObjectT.optString("restdate"));
                        detailInfoHashMap.put("이용 요금", jObjectT.optString("usefee"));
                        detailInfoHashMap.put("이용 시간", jObjectT.optString("usetimeculture"));
                        break;

                    case "15":
                        detailInfoHashMap.put("예매처 :" , jObjectT.optString("bookingplace"));
                        detailInfoHashMap.put("할인 정보 :" , jObjectT.optString("discountinfofestival"));
                        detailInfoHashMap.put("행사 종료일 :" , jObjectT.optString("eventenddate"));
                        detailInfoHashMap.put("행사 홈페이지 :" , jObjectT.optString("eventhomepage"));
                        detailInfoHashMap.put("행사 장소 :" , jObjectT.optString("eventplace"));
                        detailInfoHashMap.put("행사 시작일 :" , jObjectT.optString("eventstartdate"));
                        detailInfoHashMap.put("관람 소요시간 :" , jObjectT.optString("spendtimefestival"));
                        detailInfoHashMap.put("이용 요금 :" , jObjectT.optString("usetimefestival"));
                        break;

                    case "25":
                        detailInfoHashMap.put("코스 총 거리 :" , jObjectT.optString("distance"));
                        detailInfoHashMap.put("문의 및 안내 :" , jObjectT.optString("infocentertourcourse"));
                        detailInfoHashMap.put("코스 일정 :" , jObjectT.optString("schedule"));
                        detailInfoHashMap.put("코스 총 소요시간 :" , jObjectT.optString("taketime"));
                        detailInfoHashMap.put("코스 테마 :" , jObjectT.optString("theme"));
                        break;

                    case "28":
                        detailInfoHashMap.put("문의 및 안내 :" , jObjectT.optString("infocenterleports"));
                        detailInfoHashMap.put("개장 기간 :" , jObjectT.optString("openperiod"));
                        detailInfoHashMap.put("예약 안내 :" , jObjectT.optString("reservation"));
                        detailInfoHashMap.put("쉬는 날 :" , jObjectT.optString("restdateleports"));
                        detailInfoHashMap.put("입장료 :" , jObjectT.optString("usefeeleports"));
                        detailInfoHashMap.put("이용 시간 :" , jObjectT.optString("usetimeleports"));
                        break;

                    case "32":
                        detailInfoHashMap.put("취사 가능 여부 :" , jObjectT.optString("chkcooking"));
                        detailInfoHashMap.put("문의 및 안내 :" , jObjectT.optString("infocenterlodging"));
                        detailInfoHashMap.put("예약 안내 :" , jObjectT.optString("reservationurl"));
                        detailInfoHashMap.put("객실 유형 :" , jObjectT.optString("roomtype"));
                        detailInfoHashMap.put("부대 시설 :" , jObjectT.optString("subfacility"));
                        break;

                    case "38":
                        detailInfoHashMap.put("장서는 날" , jObjectT.optString("fairday"));
                        detailInfoHashMap.put("문의 및 안내 :" , jObjectT.optString("infocentershopping"));
                        detailInfoHashMap.put("개장일 :" , jObjectT.optString("opendateshopping"));
                        detailInfoHashMap.put("영업 시간 :" , jObjectT.optString("opentime"));
                        detailInfoHashMap.put("쉬는 날 :" , jObjectT.optString("restdateshopping"));
                        break;

                    case "39":
                        detailInfoHashMap.put("신용카드 가능 여부 :" , jObjectT.optString("chkcreditcardfood"));
                        detailInfoHashMap.put("할인 정보 :" , jObjectT.optString("discountinfofood"));
                        detailInfoHashMap.put("대표 메뉴 :" , jObjectT.optString("firstmenu"));
                        detailInfoHashMap.put("문의 및 안내 :" , jObjectT.optString("infocenterfood"));
                        detailInfoHashMap.put("영업 시간 :" , jObjectT.optString("opentimefood"));
                        detailInfoHashMap.put("쉬는 날 :" , jObjectT.optString("restdatefood"));
                        break;
                }
                //System.out.println(jObject);
                //codeArr[i] = code;
                //System.out.println(code);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // main function //
    public void main() {
        String resultData = "";

        setURL();
        try {
            resultData = new ConnectionAPI(urlText).execute().get();
            Log.i("TTTTTTTT", resultData);
            dataParser(resultData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
