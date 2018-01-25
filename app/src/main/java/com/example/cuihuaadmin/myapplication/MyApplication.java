package com.example.cuihuaadmin.myapplication;

import android.app.Application;

/**
 * Created by cuihuaadmin on 2018/1/25.
 */

public class MyApplication  extends Application{
    private static MyApplication INSTANCE;
    private int mScreenWidth,mScreenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    public void setScreenWidth(int screenWidth) {
        mScreenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        mScreenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }
}
