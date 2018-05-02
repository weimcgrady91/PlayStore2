package com.qun.test.playstore.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public abstract class BasicAdapter<T> extends BaseAdapter {
    public abstract BasicViewHolder<T> getViewHolder();

    public abstract void loadMore();

    public LoadMoreViewHolder mLoadMoreViewHolder;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOAD_MORE = 1;
    public List<T> mData = new ArrayList<>();


    public BasicAdapter() {
    }

    public BasicAdapter(List<T> data) {
        mData = data;
    }

    public int getDataSize() {
        return mData.size();
    }

    public int getDataIndex() {
        if (mData.size() == 0) {
            return 0;
        } else {
            return mData.size() - 1;
        }

    }

    public void addData(List<T> data) {
        if (data == null) {
            mLoadMoreViewHolder.refreshView(LoadMoreViewHolder.STATE_LOAD_ERROR);
        } else if (data.size() == 0) {
            mLoadMoreViewHolder.refreshView(LoadMoreViewHolder.STATE_NONE);
        } else {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public T getItem(int position) {
        if (position == getCount() - 1) {
            return null;
        } else {
            return mData.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BasicViewHolder viewHolder;
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == TYPE_LOAD_MORE) {
                viewHolder = new LoadMoreViewHolder(hasMore());
            } else {
                viewHolder = getViewHolder();
            }
        } else {
            viewHolder = (BasicViewHolder) convertView.getTag();
        }
        if (type == TYPE_LOAD_MORE ) {
            mLoadMoreViewHolder = (LoadMoreViewHolder) viewHolder;
            mLoadMoreViewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLoadMoreViewHolder.getData() == LoadMoreViewHolder.STATE_LOAD_ERROR){
                        mLoadMoreViewHolder.refreshView(LoadMoreViewHolder.STATE_LOAD_MORE);
                        loadMore();
                    }
                }
            });
            if (mLoadMoreViewHolder.getData() == LoadMoreViewHolder.STATE_LOAD_MORE) {
                loadMore();
            }
        } else {
            viewHolder.setData(mData.get(position));

        }
        return viewHolder.mItemView;
    }

    public boolean hasMore() {
        return true;
    }
}
