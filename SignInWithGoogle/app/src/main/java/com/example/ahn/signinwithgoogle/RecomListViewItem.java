package com.example.ahn.signinwithgoogle;

/**
 * Created by SOSO on 2018-05-14.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class RecomListViewItem {
    private String url;
    private Bitmap image;
    private Drawable icon;

    private String name;
    private String address;
    private int contentImgId;
    private String contentID;
    private String contentTypeID;
    private double mapX = 0., mapY = 0.;
    private String imgURL;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setImgId(int imgId) {
        this.contentImgId = imgId;
    }

    public int getImgId() {
        return this.contentImgId;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentTypeID(String contentTypeID) {
        this.contentTypeID = contentTypeID;
    }

    public String getContentTypeID() {
        return contentTypeID;
    }

    public void setMapX(String mapX) {
        try {
            this.mapX = Double.parseDouble(mapX);
        }catch(NumberFormatException e){
            this.mapX = 0;
        }
    }

    public double getMapX() {
        return mapX;
    }

    public void setMapY(String mapY) {
        try {
            this.mapY = Double.parseDouble(mapY);
        }catch(NumberFormatException e){
            this.mapY = 0;
        }
    }

    public double getMapY() {
        return mapY;
    }

    public void setMainImg(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getMainImg(){return imgURL;}
}

