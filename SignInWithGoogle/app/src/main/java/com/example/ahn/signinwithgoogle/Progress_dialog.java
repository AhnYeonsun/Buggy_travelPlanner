package com.example.ahn.signinwithgoogle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class Progress_dialog extends AsyncTask<Void, Void, Void> {
    ProgressDialog dialog;
    int type = 0;

    public Progress_dialog(ProgressDialog dialog, int type){
        this.dialog = dialog;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        dialog.setProgress(ProgressDialog.STYLE_SPINNER);
        if(type == 1) dialog.setMessage("Loading your travel!!");
        if(type == 2) dialog.setMessage("Loading your plan!!");
        if(type == 3) dialog.setMessage("Finding shortest path...");
        if(type == 4) dialog.setMessage("Loading your bucket list!!");

        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            for(int i=0;i<5;i++){
                Thread.sleep(350);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}

