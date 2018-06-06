package com.example.ahn.signinwithgoogle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class Progress_dialog extends AsyncTask<Void, Void, Void> {
    ProgressDialog dialog;

    public Progress_dialog(ProgressDialog dialog){
        this.dialog = dialog;
    }

    @Override
    protected void onPreExecute() {
        dialog.setProgress(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("여행을 가져오는 중이에요!!");
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
                Thread.sleep(250);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}

