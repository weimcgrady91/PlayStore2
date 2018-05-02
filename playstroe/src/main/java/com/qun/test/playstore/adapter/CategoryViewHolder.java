package com.qun.test.playstore.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qun.test.playstore.R;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.CategoryBean;
import com.qun.test.playstore.util.UIUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class CategoryViewHolder extends BasicViewHolder<CategoryBean> {
    public TextView mTvTitle;
    public ListView mLv;
    private MyAdapter mAdapter;

    @Override
    public void bindView() {
        mItemView = UIUtil.inflate(R.layout.item_category);
        mTvTitle = mItemView.findViewById(R.id.tv_title);
        mLv = mItemView.findViewById(R.id.lv);
        mAdapter = new MyAdapter();
        mLv.setAdapter(mAdapter);
    }

    public class MyAdapter extends BaseAdapter {
        public List<CategoryBean.Category> infos;

        @Override
        public int getCount() {
            return infos == null ? 0 : infos.size();
        }

        public void setData(List<CategoryBean.Category> infos) {
            this.infos = infos;
            notifyDataSetChanged();
        }

        @Override
        public CategoryBean.Category getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder viewHolder;
            if (convertView == null) {
                convertView = UIUtil.inflate(R.layout.item_category_gv);
                viewHolder = new ItemViewHolder();
                viewHolder.title1 = convertView.findViewById(R.id.tv_name1);
                viewHolder.title2 = convertView.findViewById(R.id.tv_name2);
                viewHolder.title3 = convertView.findViewById(R.id.tv_name3);
                viewHolder.icon1 = convertView.findViewById(R.id.iv_icon1);
                viewHolder.icon2 = convertView.findViewById(R.id.iv_icon2);
                viewHolder.icon3 = convertView.findViewById(R.id.iv_icon3);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder) convertView.getTag();
            }
            CategoryBean.Category category = infos.get(position);
            viewHolder.title1.setText(category.name1);
            Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + category.url1).error(R.drawable.ic_default).
                    placeholder(R.drawable.ic_default).into(viewHolder.icon1);
            viewHolder.title1.setText(category.name2);
            Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + category.url1).error(R.drawable.ic_default).
                    placeholder(R.drawable.ic_default).into(viewHolder.icon2);
            viewHolder.title1.setText(category.name3);
            Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + category.url1).error(R.drawable.ic_default).
                    placeholder(R.drawable.ic_default).into(viewHolder.icon3);
            return convertView;
        }
    }

    public class ItemViewHolder {
        TextView title1;
        TextView title2;
        TextView title3;
        ImageView icon1;
        ImageView icon2;
        ImageView icon3;
    }

    @Override
    public void refreshView(CategoryBean data) {
        mAdapter.setData(data.infos);
    }
}
