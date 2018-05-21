package com.example.soso.soso;

/**
 * Created by ahn on 2018-05-06.
 */

public class UserInfo {

    public String name;
    public String email;
    public String pw;

    public UserInfo(String name, String email, String pw){
        this.name = name;
        this.email = email;
        this.pw = pw;
    }

    String getEmail(){
        return email;
    }
    String getPw(){
        return pw;
    }
    String getName(){ return name; }
}
