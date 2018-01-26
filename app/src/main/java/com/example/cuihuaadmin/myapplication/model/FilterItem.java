package com.example.cuihuaadmin.myapplication.model;

import java.io.Serializable;

/**
 * Created by apple on 16/6/29.
 */
public class FilterItem implements Serializable {
    public String style;//商品的款式
    public String seed;//品牌
    public String color;//颜色
    public String shape;//形状
    public int marketPriceUp = Integer.MAX_VALUE;//价格查询上限
    public int marketPriceDown;//价格查询下限
    public static final String SIZE = "size";
    public static final String COLOR = "color";
    public static final String SHAPE = "shape";
    public static final String SEED = "seed";
    public static final String PRICE = "prices";
    public static final String STYLE = "style";

}
