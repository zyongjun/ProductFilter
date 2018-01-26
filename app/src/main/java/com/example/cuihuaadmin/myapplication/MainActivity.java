package com.example.cuihuaadmin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.example.cuihuaadmin.myapplication.model.SearchCondition;
import com.example.cuihuaadmin.myapplication.widget.SearchFilterContentView;
import com.example.cuihuaadmin.myapplication.widget.SearchFilterHeaderView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private SearchFilterHeaderView mSearchFilterHeaderView;
    private SearchFilterContentView mSearchFilterContentView;
    private SearchCondition mSearchCondition;
    private String JSON = "{\n" +
            "  \"style\": \"手链\",\n" +
            "  \"colors\": [\n" +
            "    \"绿色\",\n" +
            "    \"白色\",\n" +
            "    \"紫色\",\n" +
            "    \"飘花\",\n" +
            "    \"翡色\",\n" +
            "    \"三彩\",\n" +
            "    \"春带彩\",\n" +
            "    \"黑色\",\n" +
            "    \"其他 \"\n" +
            "  ],\n" +
            "  \"prices\": [\n" +
            "    \"0-3000\"\n" +
            "  ],\n" +
            "  \"seeds\": [\n" +
            "    \"苹果\",\n" +
            "    \"谷歌\",\n" +
            "    \"阿里\",\n" +
            "    \"京东\",\n" +
            "    \"腾讯\",\n" +
            "    \"其他\"\n" +
            "  ],\n" +
            "  \"shapes\": [\n" +
            "    \"圆的\",\n" +
            "    \"扁的\",\n" +
            "    \"其他\"\n" +
            "  ],\n" +
            "  \"cateName\": \"翡翠\"\n" +
            "}";

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
        mSearchFilterHeaderView = findViewById(R.id.searchFilterHeader);
        mSearchFilterContentView =  findViewById(R.id.searchFilterContent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        Gson gson = new Gson();
        mSearchCondition = gson.fromJson(JSON,SearchCondition.class);
        if (mSearchCondition != null) {
            mSearchFilterHeaderView.setFilterContentView(mSearchFilterContentView);
            mSearchFilterHeaderView.show(mSearchCondition);
        }else{
            mSearchFilterHeaderView.hide();
        }
    }

}
