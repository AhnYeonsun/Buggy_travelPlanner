package com.example.ahn.signinwithgoogle;

import java.util.HashMap;
import java.util.Iterator;

public class SearchHashMap {
    private HashMap<String,String> hashMap = new HashMap<>();
    private String regionName = "";

    public SearchHashMap(HashMap hashMap, String regionName){
        this.hashMap = hashMap;
        this.regionName = regionName;
    }

    public String searching(){
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if(key==regionName){
                return hashMap.get(key);
            }
        }
        return "";
    }
}
