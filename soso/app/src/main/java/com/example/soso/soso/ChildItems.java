package com.example.soso.soso;

import android.util.Log;

/**
 * Created by SOSO on 2018-05-20.
 */
public class ChildItems {
    String value;

    public ChildItems(String value) {
        this.value = value;
    }

    public String getValue() {
        Log.d("value**********",value);
        return value;

    }

    public void setValue(String value) {
        this.value = value;
    }
}
