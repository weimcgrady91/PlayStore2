package com.qun.test.playstore.adapter;

import android.view.View;

import com.qun.test.playstore.R;
import com.qun.test.playstore.util.UIUtil;

/**
 * Created by Administrator on 2018/4/30.
 */

public class LoadMoreViewHolder extends BasicViewHolder<Integer> {
    public static final int STATE_NONE = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_LOAD_ERROR = 2;
    public View mLlError;
    public View mLlLoading;
    public View mLlNoMoreData;
    public boolean mHasMore;

    public LoadMoreViewHolder(boolean hasMore) {
        super();
        mHasMore = hasMore;
        if (mHasMore) {
            mData = STATE_LOAD_MORE;
            refreshView(mData);
        }
    }

    @Override
    public void bindView() {
        mItemView = UIUtil.inflate(R.layout.item_load_more);
        mLlError = mItemView.findViewById(R.id.ll_error);
        mLlLoading = mItemView.findViewById(R.id.ll_loading);
        mLlNoMoreData = mItemView.findViewById(R.id.ll_no_more_date);
    }

    @Override
    public void refreshView(Integer data) {
        mData = data;
        switch (data) {
            case STATE_LOAD_MORE:
                mLlLoading.setVisibility(View.VISIBLE);
                mLlError.setVisibility(View.GONE);
                mLlNoMoreData.setVisibility(View.GONE);
                break;

            case STATE_LOAD_ERROR:
                mLlError.setVisibility(View.VISIBLE);
                mLlLoading.setVisibility(View.GONE);
                mLlNoMoreData.setVisibility(View.GONE);
                break;
            case STATE_NONE:
                mLlNoMoreData.setVisibility(View.VISIBLE);
                mLlError.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.GONE);
                break;
        }
    }
}
