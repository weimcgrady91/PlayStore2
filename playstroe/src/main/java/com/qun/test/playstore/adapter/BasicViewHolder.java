package com.qun.test.playstore.adapter;

import android.view.View;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public abstract class BasicViewHolder {
    public View mConvertView ;

    public BasicViewHolder(View convertView) {
        mConvertView = convertView;
        findView();
        convertView.setTag(this);
    }
    public abstract void findView();
}
