package com.example.cuihuaadmin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    private void setDisplaySize() {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        MyApplication.getInstance().setScreenHeight(displayMetrics.heightPixels);
        MyApplication.getInstance().setScreenWidth(displayMetrics.widthPixels);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDisplaySize();
    }
}
