package com.example.cuihuaadmin.myapplication;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cuihuaadmin.myapplication.model.FilterItem;
import com.example.cuihuaadmin.myapplication.model.SearchConditionItem;
import java.util.List;

/**
 * Created by apple on 17/7/1.
 */

public class SearchConditionFilterContentAdapter extends BaseAdapter {
    private ViewHolder mHolder;
    private ItemSelectListener mItemSelectListener;
    private PriceFillListener mPriceFillListener;
    private List<SearchConditionItem> mItems;
    private String marketPriceUp;//价格查询上限
    private String marketPriceDown;//价格查询下限
    private LayoutInflater mLayoutInflater ;

    public SearchConditionFilterContentAdapter(Context context, List<SearchConditionItem> items) {
        mItems = items;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setPrice(String marketPriceDown, String marketPriceUp) {
        this.marketPriceDown = marketPriceDown;
        this.marketPriceUp = marketPriceUp;
    }

    public void setItemSelectListener(ItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    public void setPriceFillListener(PriceFillListener listener) {
        mPriceFillListener = listener;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position).key == FilterItem.PRICE) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchConditionItem item = mItems.get(position);
        int viewType = getItemViewType(position);
        if (convertView == null) {
            mHolder = new ViewHolder();
            if(viewType==0) {
                convertView = mLayoutInflater.inflate(R.layout.list_item_search_condition, null, false);
                mHolder.mIconImage = (ImageView) convertView.findViewById(R.id.iv_select);
                mHolder.mLabelText = (TextView) convertView.findViewById(R.id.tv_condition);
                mHolder.mButtonLayout = convertView.findViewById(R.id.rl_search_button);
                convertView.setTag(R.layout.list_item_search_condition,mHolder);
            }else{
                convertView = mLayoutInflater.inflate(R.layout.list_item_search_filter_price_condition, null, false);
                mHolder.mEtPriceDown = (EditText) convertView.findViewById(R.id.etPriceDown);
                mHolder.mEtPriceUp = (EditText) convertView.findViewById(R.id.etPriceUp);
                convertView.setLayoutParams(new AbsListView.LayoutParams(MyApplication.getInstance().getScreenWidth()-100, -2));
                convertView.setTag(R.layout.list_item_search_filter_price_condition,mHolder);
                notifyDataSetChanged();
            }

        } else {
            if(viewType == 0) {
                mHolder = (ViewHolder) convertView.getTag(R.layout.list_item_search_condition);
            }else{
                mHolder = (ViewHolder) convertView.getTag(R.layout.list_item_search_filter_price_condition);
            }
        }

        if (item != null&&viewType==0) {
            if (FilterItem.SIZE.equals(item.key)) {
                mHolder.mLabelText.setText(item.name.replace("mm", ""));
            } else {
                mHolder.mLabelText.setText(item.name);
            }
            mHolder.mLabelText.setEnabled(item.isSelected);
            if (item.isSelected) {
                mHolder.mIconImage.setVisibility(View.VISIBLE);
                mHolder.mButtonLayout.setBackgroundResource(R.drawable.shape_search_condition_item_selected);
            } else {
                mHolder.mIconImage.setVisibility(View.GONE);
                mHolder.mButtonLayout.setBackgroundResource(R.drawable.shape_search_condition_item_normal);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isSelected = item.isSelected;
                    if (FilterItem.STYLE.equals(item.key)) {
                        for (SearchConditionItem searchConditionItem : mItems) {
                            searchConditionItem.isSelected = false;
                        }
                    }
                    item.isSelected = !isSelected;
                    if (mItemSelectListener != null) {
                        mItemSelectListener.selectItem(item.key, item.name, item.isSelected);
                    }
                    notifyDataSetChanged();
                }
            });

        }else{
            if (!TextUtils.isEmpty(marketPriceDown)) {
                mHolder.mEtPriceDown.setText(marketPriceDown);
                mHolder.mEtPriceDown.setSelection(marketPriceDown.length());
            }else{
                mHolder.mEtPriceDown.setText(EMPTY_VALUE);
            }

            if (!TextUtils.isEmpty(marketPriceUp)) {
                mHolder.mEtPriceUp.setText(marketPriceUp);
                mHolder.mEtPriceUp.setSelection(marketPriceUp.length());
            }else{
                mHolder.mEtPriceUp.setText(EMPTY_VALUE);
            }
            mHolder.mEtPriceDown.addTextChangedListener(priceDownWatcher);
            mHolder.mEtPriceUp.addTextChangedListener (priceUpWatcher);
        }
        return convertView;
    }

    private static final String EMPTY_VALUE = "";

    private TextWatcher priceDownWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            marketPriceDown = s.toString();
            mPriceFillListener.onFillPriceDown(s.toString());
        }
    };

    private TextWatcher priceUpWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            marketPriceUp = s.toString();
            mPriceFillListener.onFillPriceUp(s.toString());
        }
    };

    public static class ViewHolder {
        ImageView mIconImage;
        TextView mLabelText;
        View mButtonLayout;
        EditText mEtPriceDown;
        EditText mEtPriceUp;
    }

    public interface ItemSelectListener {
        void selectItem(String key, String selection, boolean isSelected);
    }

    public interface PriceFillListener{
        void onFillPriceDown(String price);
        void onFillPriceUp(String price);
    }
}
