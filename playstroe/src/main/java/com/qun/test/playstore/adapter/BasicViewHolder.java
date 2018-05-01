package com.qun.test.playstore.adapter;

import android.view.View;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public abstract class BasicViewHolder<T> {
    public View mItemView;
    public T mData;

    public BasicViewHolder() {
        bindView();
        mItemView.setTag(this);
    }

    public void setData(T data) {
        mData = data;
        refreshView(data);
    }

    public T getData() {
        return mData;
    }

    public abstract void bindView();

    public abstract void refreshView(T data);

}
