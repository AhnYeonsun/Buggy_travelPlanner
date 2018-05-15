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
    }

    public void setText(String text) {
        this.text = text; }

    public String getText() {
        return this.text;
    }

    private class loadBitmap extends AsyncTask<String, Integer, Bitmap> {
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
    }

}

