package com.example.soso.soso;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

//Description : make URL how bring the URL data from other class//
public class GetURL {
    private final String serviceKey = "Rc%2FaF%2FUtrBXVonYiqMAc0tttIxuHrlVynLgI7aWtcpTsIbk5rzsrUPA8fSqT0MYU%2BaE3xMZ2jtX3jxf220chCg%3D%3D";
    private final String endPoint = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/";
    private final String setting = "&arrange=B&MobileOS=ETC&MobileApp=TestApp&_type=json";

    private String searchType = "", option = "", keyword = "", contentID = "", contentTypeID = "";
    private String regionCode = "", sigunguCode="";
    GetRegionCodeHashMap rhm; //object of GetRegionCodeHashMap class
    Finding_AreaCode rs; //object of Finding_AreaCode class
    KeywordSearching ks; //object of KeywordSearching class
    GetDetailInfo GDI; //object of GetDetailInfo class
    //getInformation gi; //object of getInformation class

    public GetURL(GetRegionCodeHashMap ob1){
        rhm = ob1;
    } //constructor for GetRegionCodeHashMap class

    public GetURL(Finding_AreaCode rs){
        this.rs = rs;
    } //construct for Finding_AreaCode class

    public GetURL(KeywordSearching ks){this.ks = ks;} //construct for KeywordSearching class

    public GetURL(GetDetailInfo GDI){this.GDI = GDI;} //construct for getInformation class


    // Description : make the final URL //
    // Input : Distinguishable code integer code value //
    // Output : String value URL //
    public String integrateURL(int i) {
        String urlText = "";

        try {
            if(i==0) { //called GetRegionCodeHashMap class
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
            else if(i==1){ //called Finding_AreaCode class
                searchType = rs.getSearchType();
                regionCode = rs.getAreaCode();
                sigunguCode = rs.getSigunguCode();
                //Log.i("searchType Test :::", searchType);
                URL url = new URL(endPoint +
                        searchType +
                        "?ServiceKey=" + serviceKey +
                        "&" + "areaCode=" + regionCode+
                        "&" + "sigunguCode=" + sigunguCode +
                        "&numOfRows=40&pageNo=1"+setting);

                urlText = url.toString();
            }
            else if(i==2){
                searchType = ks.getSearchType();
                keyword = ks.getKeyword();
                regionCode = ks.getRegionCode();
                sigunguCode = ks.getSigunguCode();
                URL url = new URL(endPoint +
                searchType +
                "?ServiceKey=" + serviceKey +
                        "&"+"areaCode="+regionCode+
                        "&"+"sigunguCode="+sigunguCode+
                "&keyword=" + keyword +
                "&numOfRows=40&pageNo=1"+setting);

                urlText = url.toString();
            }
            else if(i==3) {
                searchType = GDI.getSearchType();
                contentID = GDI.getContentID();
                contentTypeID = GDI.getContentTypeID();
                URL url = new URL(endPoint +
                searchType +
                "?ServiceKey=" + serviceKey +
                "&contentId=" + contentID +
                "&contentTypeId=" + contentTypeID +
                setting);

                urlText = url.toString();
            }
            Log.i("url Test :::", urlText);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlText;
    }
}
