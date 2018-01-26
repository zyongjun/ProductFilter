package com.example.cuihuaadmin.myapplication.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cuihuaadmin.myapplication.model.FilterItem;
import com.example.cuihuaadmin.myapplication.R;
import com.example.cuihuaadmin.myapplication.model.SearchCondition;
import com.example.cuihuaadmin.myapplication.model.SearchConditionItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class SearchFilterHeaderView extends LinearLayout {
    private SearchFilterContentView mSearchFilterContent;

    private GridView mHeadersGrid;
    private List<SearchConditionItem> mHeaderItems;
    private SearchHeaderAdapter mAdapter;

    public void setFilterContentView(SearchFilterContentView searchFilterContentView) {
        mSearchFilterContent = searchFilterContentView;
    }

    public SearchFilterHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchFilterHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.view_product_search_filter_header, this, true);
        mHeadersGrid =  findViewById(R.id.gvHeader);
    }

    private String readSearchConditionItem(String type){
        StringBuffer sb = new StringBuffer();
        if (mItemsData.get(type) != null) {
            List<SearchConditionItem> shapeList = mItemsData.get(type);
            int i = 0;
            for (SearchConditionItem item : shapeList) {
                if(item.isSelected) {
                    if (i > 0) {
                        sb.append(QUOTE);
                    }
                    sb.append(item.name);
                    i++;
                }

            }
        }
        return sb.toString();
    }

    private static final String QUOTE = ",";
    public void filterConfirm() {
        hideKeyboard();
        FilterItem filterItem = new FilterItem();
        filterItem.shape = readSearchConditionItem(FilterItem.SHAPE);
        filterItem.seed = readSearchConditionItem(FilterItem.SEED);
        filterItem.color = readSearchConditionItem(FilterItem.COLOR);

        int marketPriceDown = 0;
        int marketPriceUp = Integer.MAX_VALUE;
        filterItem.marketPriceDown = marketPriceDown;
        filterItem.marketPriceUp = marketPriceUp;
        mSearchFilterContent.animate().translationY(-mSearchFilterContent.getHeight())
                .setDuration(200);
    }

    private boolean checkIfContain(String props, SearchConditionItem item) {
        if (TextUtils.isEmpty(props)) {
            return false;
        }
        if (props.contains(item.name)) {
            return true;
        }
        return false;
    }

    private HashMap<String,List<SearchConditionItem>> mItemsData = new HashMap<>();
    private List<SearchConditionItem> initSearchConditionHeaderList(SearchCondition conditions) {
        List<SearchConditionItem> items = new ArrayList<>();
        if (conditions.shapes != null && conditions.shapes.length > 0) {
            SearchConditionItem shapeItem = new SearchConditionItem();
            shapeItem.isSelected = true;
            shapeItem.key = FilterItem.SHAPE;
            shapeItem.name = "款式";
            List<SearchConditionItem> list = new ArrayList(conditions.shapes.length);
            for (String shape : conditions.shapes) {
                SearchConditionItem item = new SearchConditionItem();
                item.key=FilterItem.SHAPE;
                item.name=shape;
                list.add(item);
            }
            mItemsData.put(FilterItem.SHAPE,list);
            items.add(shapeItem);
        }
        if (conditions.seeds != null && conditions.seeds.length > 0) {
            SearchConditionItem seedItem = new SearchConditionItem();
            seedItem.isSelected = false;
            seedItem.key = FilterItem.SEED;
            seedItem.name = "品牌";
            items.add(seedItem);
            List<SearchConditionItem> list = new ArrayList(conditions.seeds.length);
            for (String seed : conditions.seeds) {
                SearchConditionItem item = new SearchConditionItem();
                item.key=FilterItem.SEED;
                item.name=seed;
                list.add(item);
            }
            mItemsData.put(FilterItem.SEED,list);
        }
        if (conditions.colors != null && conditions.colors.length > 0) {
            SearchConditionItem colorItem = new SearchConditionItem();
            colorItem.name ="颜色";
            colorItem.key = FilterItem.COLOR;
            items.add(colorItem);
            List<SearchConditionItem> list = new ArrayList(conditions.colors.length);
            for (String size : conditions.colors) {
                SearchConditionItem item = new SearchConditionItem();
                item.key=FilterItem.COLOR;
                item.name=size;
                list.add(item);
            }
            mItemsData.put(FilterItem.COLOR,list);
        }

        if (conditions.prices != null && conditions.prices.length > 0) {
            SearchConditionItem priceItem = new SearchConditionItem();
            priceItem.name = "价格";
            priceItem.key = FilterItem.PRICE;
            items.add(priceItem);
            List<SearchConditionItem> list = new ArrayList(conditions.prices.length);
            for (String size : conditions.prices) {
                SearchConditionItem item = new SearchConditionItem();
                item.key=FilterItem.PRICE;
                item.name=size;
                list.add(item);
            }

            mItemsData.put(FilterItem.PRICE,list);
        }
        return items;
    }

    public interface SearchFilterCallback {
        void onResetData();
        void onFilterConfirm();
    }

    public void hide(){
        if (mSearchFilterContent != null) {
            mSearchFilterContent.setVisibility(GONE);
        }
        setVisibility(GONE);
    }
    public void collopse(){
        if(mSearchFilterContent != null) {
            mSearchFilterContent.animate().translationY(-mSearchFilterContent.getHeight())
                    .setDuration(200);
            if (mAdapter != null) {
                mAdapter.collopse();
            }
        }
    }

    public void show(SearchCondition condition) {
        setVisibility(VISIBLE);
        mHeaderItems = initSearchConditionHeaderList(condition);
        mAdapter = new SearchHeaderAdapter(getContext(),mHeaderItems);
        mAdapter.setItemSelectListener(new ItemSelectListener() {
            @Override
            public void selectItem(String key, String selection, boolean isSelected) {
                hideKeyboard();
                if(isSelected){//收起
                    mSearchFilterContent.animate().translationY(-mSearchFilterContent.getHeight())
                            .setDuration(200);
                }else{//展开当前项
                    mSearchFilterContent.animate().translationY(0)
                            .setDuration(200);
                    mSearchFilterContent.show(mItemsData.get(key));
                }
            }
        });

        mHeadersGrid.setAdapter(mAdapter);

        mSearchFilterContent.setSearchFilterCallback(new SearchFilterCallback() {
            @Override
            public void onResetData() {
                resetData();
            }

            @Override
            public void onFilterConfirm() {
                filterConfirm();
            }
        });
    }

    public void resetData() {
//        Iterator iterator = mItemsData.keySet().iterator();
//        while (iterator.hasNext()) {
//           List<SearchConditionItem> list =  mItemsData.get(iterator.next());
//            for (SearchConditionItem item : list) {
//                item.isSelected = false;
//            }
//        }
    }


    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((Activity)getContext()).getCurrentFocus().getWindowToken(), 0);
    }
    public interface ItemSelectListener {
        void selectItem(String key, String selection, boolean isSelected);
    }

    private class SearchHeaderAdapter extends BaseAdapter {
        private List<SearchConditionItem> mItems = new ArrayList();
        private ItemSelectListener mItemSelectListener;
        private int mSelectedPosition = -1;
        private LayoutInflater mLayoutInflater;

        public SearchHeaderAdapter(Context context, List<SearchConditionItem> items) {
            mLayoutInflater = LayoutInflater.from(context);
            mItems = items;
        }


        public void setItemSelectListener(ItemSelectListener listener) {
            mItemSelectListener = listener;
        }

        public void collopse() {
            mSelectedPosition = -1;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems == null ? 0 : mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems == null || mItems.isEmpty() ? null : mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;
            if (convertView == null) {
                mHolder = new ViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.list_item_search_header_filter, parent, false);
                mHolder.mIconImage = convertView.findViewById(R.id.iv_select);
                mHolder.mLabelText = convertView.findViewById(R.id.tv_condition);
                mHolder.mButtonLayout = convertView.findViewById(R.id.rl_search_button);
                mHolder.mOutBoarderLayout = convertView.findViewById(R.id.llOutBoarder);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            final SearchConditionItem item = mItems.get(position);
            mHolder.mLabelText.setEnabled(mSelectedPosition == position);
            mHolder.mLabelText.setText(item.name);
            if (mSelectedPosition == position) {
                mHolder.mIconImage.setImageResource(R.mipmap.filter_header_up1);
                mHolder.mOutBoarderLayout.setSelected(true);
            } else {
                mHolder.mIconImage.setImageResource(R.mipmap.filter_header_down1);
                mHolder.mOutBoarderLayout.setSelected(false);
            }
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelectedBefore = mSelectedPosition == position;
                    if (mItemSelectListener != null) {
                        mItemSelectListener.selectItem(item.key, item.name, isSelectedBefore);
                    }
                    if (isSelectedBefore) {
                        mSelectedPosition = -1;
                    }else {
                        mSelectedPosition = position;
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
    public static class ViewHolder {
        ImageView mIconImage;
        TextView mLabelText;
        View mButtonLayout;
        View mOutBoarderLayout;
    }
}
