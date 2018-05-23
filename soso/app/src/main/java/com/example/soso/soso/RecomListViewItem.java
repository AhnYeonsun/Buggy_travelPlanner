package com.example.soso.soso;

/**
 * Created by SOSO on 2018-05-14.
 */

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RecomListViewItem {
    private String text;
    private String url;
    private Bitmap image;
    private Drawable icon;
    private String info;

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

        try {
            this.image = new loadBitmap(this.imgURL).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Drawable getMainImg(){return changeBitmapToDrawable();}

    public Drawable changeBitmapToDrawable(){
        Drawable drawableImg = new BitmapDrawable(image);
        return drawableImg;
    }

    private class loadBitmap extends AsyncTask<String, Integer, Bitmap> {
        Bitmap bitmap = null;
        String url = "";

        public loadBitmap(String url) {
            this.url = url;
        }

        @Override
        public Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(this.url);
                Log.i("URL TEST : ", url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();

                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

}

