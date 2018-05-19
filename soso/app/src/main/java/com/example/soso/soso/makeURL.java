package com.example.soso.soso;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

//Description : make URL how bring the URL data from other class//
public class MakeURL {
    private final String serviceKey = "Rc%2FaF%2FUtrBXVonYiqMAc0tttIxuHrlVynLgI7aWtcpTsIbk5rzsrUPA8fSqT0MYU%2BaE3xMZ2jtX3jxf220chCg%3D%3D";
    private final String endPoint = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/";
    private final String setting = "&MobileOS=ETC&MobileApp=TestApp&_type=json";

    private String searchType = "", option = "", keyword = "", contentID = "", contentTypeID = "";
    private String areaCode = "", sigunguCode="";
    GetRegionHashMap rhm; //object of GetRegionHashMap class
    RegionSearch rs; //object of RegionSearch class
    KeywordSearch ks; //object of KeywordSearch class
    //getInformation gi; //object of getInformation class

    public MakeURL(GetRegionHashMap ob1){
        rhm = ob1;
    } //constructor for GetRegionHashMap class

    public MakeURL(RegionSearch rs){
        this.rs = rs;
    } //construct for RegionSearch class

    public MakeURL(KeywordSearch ks){this.ks = ks;} //construct for KeywordSearch class

    //public MakeURL(getInformation gi){this.gi = gi;} //construct for getInformation class


    // Description : make the final URL //
    // Input : Distinguishable code integer code value //
    // Output : String value URL //
    public String integrateURL(int i) {
        String urlText = "";

        try {
            if(i==0) { //called GetRegionHashMap class
                searchType = rhm.getSearchType();
                option = rhm.getOption();
                //Log.i("searchType Test :::", searchType);
                URL url = new URL(endPoint +
                        searchType +
                        "?ServiceKey=" + serviceKey +
                        "&numOfRows=100&pageNo=1&" + this.option +
                        setting);

                urlText = url.toString();
            }
            else if(i==1){ //called RegionSearch class
                searchType = rs.getSearchType();
                areaCode = rs.getAreaCode();
                sigunguCode = rs.getSigunguCode();
                //Log.i("searchType Test :::", searchType);
                URL url = new URL(endPoint +
                        searchType +
                        "?ServiceKey=" + serviceKey +
                        "&" + "areaCode=" + areaCode+
                        "&" + "sigunguCode=" + sigunguCode +
                        "&numOfRows=40&pageNo=1"+setting);

                urlText = url.toString();
            }
            else if(i==2){
                searchType = ks.getSearchType();
                keyword = ks.getKeyword();
                URL url = new URL(endPoint +
                searchType +
                "?ServiceKey=" + serviceKey +
                "&keyword=" + keyword +
                "&numOfRows=40&pageNo=1"+setting);

                urlText = url.toString();
            }
//            else if(i==3) {
//                searchType = gi.getSearchType();
//                contentID = gi.getContentID();
//                contentTypeID = gi.getContentTypeID();
//                URL url = new URL(endPoint +
//                searchType +
//                "?ServiceKey=" + serviceKey +
//                "&contentID=" + contentID +
//                "&contentTypeID=" + contentTypeID +
//                setting);
//
//                urlText = url.toString();
//            }
            Log.i("url Test :::", urlText);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlText;
    }
}
