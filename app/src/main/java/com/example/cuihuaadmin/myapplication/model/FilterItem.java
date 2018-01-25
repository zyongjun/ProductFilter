package com.example.cuihuaadmin.myapplication.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by apple on 16/6/29.
 */
public class FilterItem implements Serializable {
    public String cateName;//品名 （翡翠）
    public String style;//商品的款式
    public int goodsAmount;//大于0代表有货 小于等于0代表全部 不传是查询全部
    public String seed;//种分
    public String color;//颜色
    public String shape;//形状
    public String size;//圈口
    public int marketPriceUp = Integer.MAX_VALUE;//价格查询上限
    public int marketPriceDown;//价格查询下限
    public String showName;
    public String inset;//是否镶嵌
    public String price;//价格
    public int newFlag;//最新商品排序，0 默认排序，1 最新商品排在最前
    public int priceUpDown;//价格升降排序，0 升序排序（默认），1降序排序(新版本：0：不排序 1：升序 2：降序)
    public SubFilter[] child;
    public boolean isSelected;
    public String imgUrl;
    public String styleId;
    public int id;
    public String cateId;
    public int searchType;//0代表搜索和商品列表里面的筛选，1代表首页点击分类筛选
    public boolean isSearch;
    public int guideType;// 0 无引导 1 价格引导  2 圈口引导-戒指 3 圈口引导-手镯 4 品类引导

    public static final String SIZE = "size";
    public static final String COLOR = "color";
    public static final String SHAPE = "shape";
    public static final String INSET = "inset";
    public static final String SEED = "seed";
    public static final String PRICE = "prices";
    public static final String MARKET_PRICE_UP = "marketPriceUp";
    public static final String MARKET_PRICE_DOWN = "marketPriceDown";
    public static final String STYLE = "style";

    public static class SubFilter implements Serializable {
        public String imgUrl;
        public String showName;
        public String shape;
        public int sort;
    }

    public void setType4Filter() {
        searchType = 1;
        isSearch=false;
    }

    public void setType4Search() {
        searchType = 0;
    }

    @Override
    public String toString() {
        return "FilterItem{" +
                "cateName='" + cateName + '\'' +
                ", style='" + style + '\'' +
                ", goodsAmount=" + goodsAmount +
                ", seed='" + seed + '\'' +
                ", color='" + color + '\'' +
                ", shape='" + shape + '\'' +
                ", size='" + size + '\'' +
                ", marketPriceUp=" + marketPriceUp +
                ", marketPriceDown=" + marketPriceDown +
                ", showName='" + showName + '\'' +
                ", inset='" + inset + '\'' +
                ", price='" + price + '\'' +
                ", newFlag=" + newFlag +
                ", priceUpDown=" + priceUpDown +
                ", child=" + Arrays.toString(child) +
                ", isSelected=" + isSelected +
                ", imgUrl='" + imgUrl + '\'' +
                ", styleId='" + styleId + '\'' +
                ", id=" + id +
                ", cateId='" + cateId + '\'' +
                ", searchType=" + searchType +
                '}';
    }

    public FilterItem cloneSelf() {
        FilterItem item = new FilterItem();
        item.cateName = this.cateName;
        item.style = this.style;
        item.goodsAmount = this.goodsAmount;
        item.seed = this.seed;
        item.color = this.color;
        item.shape = this.shape;
        item.size = this.size;
        item.marketPriceUp = this.marketPriceUp;
        item.marketPriceDown = this.marketPriceDown;
        item.showName = this.showName;
        item.inset = this.inset;
        item.price = this.price;
        item.newFlag = this.newFlag;
        item.priceUpDown = this.priceUpDown;
        item.isSelected = this.isSelected;
        item.child = this.child;
        return item;
    }
}
