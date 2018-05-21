package com.example.soso.soso;

/**
 * Created by SOSO on 2018-05-14.
 */
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
    private int imgId;
    private String contentID;
    private String contentTypeID;

    public void setName(String name){
        this.name=name;
        
    }
    public String getName()
    {
        return this.name;
    }
    public void setAddress(String address){
        this.address=address;
        
    }
    public String getAddress()
    {
        return this.address;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId ;
    }
    public int getImgId() {
        return this.imgId ;
    }
    public void setInfo(String info) {this.info = info;}
    public String getInfo() {return info;}
    public void setContentID(String contentID){
        this.contentID = contentID;
    }
    public String getContentID(){return contentID;}
    public void setContentTypeID(String contentTypeID){
        this.contentTypeID = contentTypeID;
    }
    public String getContentTypeID(){return contentTypeID;}
    /*public void setText(String text) {
     this.text = text; }
     
     public String getText() {
     return this.text;
     }
     public void setIcon(String url) {
     this.url = url;
     //Log.i("****URL TEST ::::: ",this.url);
     }
     
     public Bitmap getIcon() {
     try {
     //System.out.println("AAAAAAAAAAAAAAAAA");
     this.image = new loadBitmap(this.url).execute().get();
     //System.out.println("AAAAAAAAAAAAAAAAA");
     } catch (InterruptedException e) {
     e.printStackTrace();
     } catch (ExecutionException e) {
     e.printStackTrace();
     }
     return this.image;
     }*/
    /* private class loadBitmap extends AsyncTask<String, Integer, Bitmap> {
     Bitmap bitmap = null;
     String url = "";
     
     public loadBitmap(String url){
     this.url = url;
     }
     @Override
     public Bitmap doInBackground(String... params) {
     try {
     URL url = new URL(this.url);
     Log.i("URL test :::", url.toString());
     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
     conn.connect();
     
     InputStream is = conn.getInputStream();
     
     bitmap = BitmapFactory.decodeStream(is);
     
     
     } catch (IOException e) {
     e.printStackTrace();
     }
     return bitmap;
     }
     }*/
    
}

