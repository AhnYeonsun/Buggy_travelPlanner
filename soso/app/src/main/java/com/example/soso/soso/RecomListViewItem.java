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
    loadBitmap Task;

    public void setIcon(String url) {
        this.url = url;
        //Log.i("****URL TEST ::::: ",this.url);
    }

    public Bitmap getIcon() {
        try {
            System.out.println("AAAAAAAAAAAAAAAAA");
            image = (Bitmap) Task.execute().get();
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
        ImageView imageV;

        @Override
        public Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                //Log.i("URL test :::", url.toString());
                HttpURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setDoInput(true);
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

