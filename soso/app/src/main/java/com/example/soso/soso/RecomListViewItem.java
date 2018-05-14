package com.example.soso.soso;

/**
 * Created by SOSO on 2018-05-14.
 */

import android.graphics.drawable.Drawable;

public class RecomListViewItem {
    private String text;
    private Drawable icon;

    public void setIcon(Drawable icon)
    {
        this.icon=icon;
    }
    public Drawable getIcon()
    {
        return this.icon;
    }
    public void setText(String text)
    {
        this.text=text;
    }
    public String getText()
    {
        return this.text;
    }
}