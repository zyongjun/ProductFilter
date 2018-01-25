package com.example.cuihuaadmin.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.cuihuaadmin.myapplication.R;
import com.example.cuihuaadmin.myapplication.SearchConditionFilterContentAdapter;
import com.example.cuihuaadmin.myapplication.model.SearchConditionItem;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class SearchFilterContentView extends LinearLayout implements View.OnClickListener{

    private GridView mGvContent;

    public SearchFilterContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchFilterContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.view_product_search_filter_content, this, true);
        mGvContent = (GridView) findViewById(R.id.gvContent);
        Button btnReset = (Button)findViewById(R.id.btn_clear);
        btnReset.setOnClickListener(this);
        Button btnConfirm = (Button)findViewById(R.id.btn_sure);
        btnConfirm.setOnClickListener(this);
    }

    private SearchFilterHeaderView.SearchFilterCallback mSearchFilterCallback;
    public void setSearchFilterCallback(SearchFilterHeaderView.SearchFilterCallback searchFilterCallback) {
        mSearchFilterCallback = searchFilterCallback;
    }

    private String marketPriceUp;//价格查询上限
    private String marketPriceDown;//价格查询下限

    public void setMarketPriceDown(String marketPriceDown) {
        this.marketPriceDown = marketPriceDown;
    }
    public String getMarketPriceUp() {
        return marketPriceUp;
    }

    public void setMarketPriceUp(String marketPriceUp) {
        this.marketPriceUp = marketPriceUp;
    }
    public String getMarketPriceDown() {
        return marketPriceDown;
    }
    private List<SearchConditionItem> mSearchConditions;
    private SearchConditionFilterContentAdapter mAdapter;
    public void show(List<SearchConditionItem> items) {
        setVisibility(VISIBLE);
        mSearchConditions = items;
        mAdapter = new SearchConditionFilterContentAdapter(getContext(), mSearchConditions);
        mAdapter.setPrice(marketPriceDown,marketPriceUp);

        mAdapter.setItemSelectListener(new SearchConditionFilterContentAdapter.ItemSelectListener() {
            @Override
            public void selectItem(String key, String selection, boolean isSelected) {
//                if(isSelected){//收起
//
//                }else{//展开当前项
//                }
//                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setPriceFillListener(new SearchConditionFilterContentAdapter.PriceFillListener() {
            @Override
            public void onFillPriceDown(String price) {
                marketPriceDown = price;
            }

            @Override
            public void onFillPriceUp(String price) {
                marketPriceUp = price;
            }
        });
        mGvContent.setAdapter(mAdapter);
    }

    public void resetSelect() {
        marketPriceUp = null;
        marketPriceDown = null;
        mAdapter.setPrice(marketPriceDown,marketPriceUp);
        for (SearchConditionItem mSearchCondition : mSearchConditions) {
            mSearchCondition.isSelected = false;
        }
        mAdapter.notifyDataSetChanged();
        mSearchFilterCallback.onResetData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                resetSelect();
                break;
            case R.id.btn_sure:
                mSearchFilterCallback.onFilterConfirm();
                break;
        }
    }
}
