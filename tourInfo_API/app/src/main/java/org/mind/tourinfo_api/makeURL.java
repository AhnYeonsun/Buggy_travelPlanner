package org.mind.tourinfo_api;

import java.net.MalformedURLException;
import java.net.URL;

//Description : make URL how bring the URL data from other class//
public class makeURL {
    private final String serviceKey = "Rc%2FaF%2FUtrBXVonYiqMAc0tttIxuHrlVynLgI7aWtcpTsIbk5rzsrUPA8fSqT0MYU%2BaE3xMZ2jtX3jxf220chCg%3D%3D";
    private final String endPoint = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/";
    private final String setting = "&MobileOS=ETC&MobileApp=TestApp&_type=json";

    private String searchType = "", option = "";
    private String areaCode = "", sigunguCode="";
    getRegionHashMap ob; //object of getRegionHashMap class
    regionSearch rs; //object of regionSearch class

    public makeURL(getRegionHashMap ob1){
        ob = ob1;
    } //constructor for getRegionHashMap class

    public makeURL(regionSearch rs){
        this.rs = rs;
    } //construct for regionSearch class

    // Description : make the final URL //
    // Input : Distinguishable code integer code value //
    // Output : String value URL //
    public String integrateURL(int i) {
        String urlText = "";

        try {
            if(i==0) { //called getRegionHashMap class
                searchType = ob.getSearchType();
                option = ob.getOption();
                //Log.i("searchType Test :::", searchType);
                URL url = new URL(endPoint +
                        searchType +
                        "?ServiceKey=" + serviceKey +
                        "&" + this.option +
                        setting);

                urlText = url.toString();
            }
            else if(i==1){ //called regionSearch class
                searchType = rs.getSearchType();
                areaCode = rs.getAreaCode();
                sigunguCode = rs.getSigunguCode();
                //Log.i("searchType Test :::", searchType);
                URL url = new URL(endPoint +
                        searchType +
                        "?ServiceKey=" + serviceKey +
                        "&" + "areaCode=" + areaCode+
                        "&" + "sigunguCode=" + sigunguCode +
                        "&"+setting);

                urlText = url.toString();
            }
            //Log.i("url Test :::", urlText);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlText;
    }
}
