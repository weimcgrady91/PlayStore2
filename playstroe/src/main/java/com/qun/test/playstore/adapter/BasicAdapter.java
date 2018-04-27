package com.qun.test.playstore.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public abstract class BasicAdapter<T, E extends BasicViewHolder> extends BaseAdapter {
    public abstract View getItemView(ViewGroup parent);

    public abstract E getViewHolder(View rootView);

    public abstract void bindView(E e, int position);

    public List<T> mData;

    public BasicAdapter() {
    }

    public BasicAdapter(List<T> data) {
        mData = data;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        E viewHolder;
        if (convertView == null) {
            convertView = getItemView(parent);
            viewHolder = getViewHolder(convertView);
        } else {
            viewHolder = (E) convertView.getTag();
        }
        bindView(viewHolder, position);
        return convertView;
    }

}
