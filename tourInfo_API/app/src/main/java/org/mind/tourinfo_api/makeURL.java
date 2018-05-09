package org.mind.tourinfo_api;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class makeURL {
    private final String serviceKey = "Rc%2FaF%2FUtrBXVonYiqMAc0tttIxuHrlVynLgI7aWtcpTsIbk5rzsrUPA8fSqT0MYU%2BaE3xMZ2jtX3jxf220chCg%3D%3D";
    private final String endPoint = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/";
    private final String setting = "&MobileOS=ETC&MobileApp=TestApp&_type=json";

    private String searchType = null, option = null;
    codeHashMap ob;

    public makeURL(codeHashMap ob1){
        ob = ob1;
    }

    public String integrateURL() {
        String urlText = null;
        try {
            searchType = ob.getSearchType();
            option = ob.getOption();
            //Log.i("searchType Test :::", searchType);
            URL url = new URL(endPoint +
                    searchType +
                    "?ServiceKey=" + serviceKey +
                    "&" + this.option +
                    setting);

            urlText = url.toString();
            //Log.i("url Test :::", urlText);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlText;
    }

}
