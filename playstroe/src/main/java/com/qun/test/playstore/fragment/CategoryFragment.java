package com.qun.test.playstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qun.test.playstore.App;
import com.qun.test.playstore.R;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.CategoryBean;
import com.qun.test.playstore.util.UIUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CategoryFragment extends BaseFragment {

    private ListView mLv;
    private CategoryAdapter mAdapter;

    @Override
    public View obtainRootView(Context context) {
        View contentView = UIUtil.inflate(R.layout.fragment_home);
        mLv = contentView.findViewById(R.id.lv);
        mLv.setSelector(new ColorDrawable());
        mLv.setDivider(null);
        mLv.setCacheColorHint(Color.TRANSPARENT);
        return contentView;
    }

    public class CategoryAdapter extends BaseAdapter {
        List<CategoryBean> data;

        public CategoryAdapter(List<CategoryBean> data) {
            this.data = data;
        }

        public void addData(List<CategoryBean> bean) {
            this.data = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = UIUtil.inflate(R.layout.item_category);
                viewHolder = new ViewHolder();
                viewHolder.title = convertView.findViewById(R.id.tv_title);
                viewHolder.lv = convertView.findViewById(R.id.lv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            convertView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , data.get(position).infos.size() * UIUtil.dip2px(110)));
            viewHolder.title.setText(this.data.get(position).title);
            Log.e("weiqun12345", "aa=" + this.data.get(position).infos.toString());
            MyAdapter adapter = new MyAdapter(this.data.get(position).infos);
            viewHolder.lv.setAdapter(adapter);
            return convertView;
        }
    }

    public class MyAdapter extends BaseAdapter {
        public List<CategoryBean.Category> infos;

        public MyAdapter(List<CategoryBean.Category> infos) {
            this.infos = infos;
            Log.e("weiqun12345", "aa2 size=" + this.infos.size() + ",aa2=" + this.infos.toString());
        }

        @Override
        public int getCount() {
            Log.e("weiqun12345", ",mmm:infos.size=" + infos.size());
            return infos.size();
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
            Log.e("weiqun123456", "catetory=" + category.toString());
            viewHolder.title1.setText(category.name1);
            if (!TextUtils.isEmpty(category.url1))
                Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + category.url1).error(R.drawable.ic_default).
                        placeholder(R.drawable.ic_default).into(viewHolder.icon1);
            viewHolder.title2.setText(category.name2);
            if (!TextUtils.isEmpty(category.url2))
                Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + category.url2).error(R.drawable.ic_default).
                        placeholder(R.drawable.ic_default).into(viewHolder.icon2);
            viewHolder.title3.setText(category.name3);
            if (!TextUtils.isEmpty(category.url3))
                Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + category.url3).error(R.drawable.ic_default).
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

    public class ViewHolder {
        TextView title;
        ListView lv;
    }


    @Override
    public void onLoadSuccess(String result) {
        Log.e("weiqun12345", "result=" + result);
        Gson gson = new Gson();
        List<CategoryBean> bean = gson.fromJson(result, new TypeToken<List<CategoryBean>>() {
        }.getType());
        Log.e("weiqun12345", "bean.toString()" + bean.toString());
        if (bean.size() == 0) {
            updateViewState(VIEW_STATE_EMPTY);
        } else {
            mAdapter = new CategoryAdapter(bean);
            mLv.setAdapter(mAdapter);
            updateViewState(VIEW_STATE_SUCCESS);
        }
    }

    @Override
    public void onLoadFailure() {
        Toast.makeText(App.sContext, "loadFailure", Toast.LENGTH_SHORT).show();
        if (mAdapter.getCount() == 0) {
            updateViewState(VIEW_STATE_ERROR);
        } else {
            mAdapter.addData(null);
            updateViewState(VIEW_STATE_SUCCESS);
        }
    }

    @Override
    public String getRequestUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConfig.HOST);
        sb.append(ServerConfig.PATH_CATEGORY);
        sb.append("?index=0");
        Log.e("weiqun12345", "url=" + sb.toString());
        return sb.toString();
    }

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {
        CategoryFragment homeFragment = new CategoryFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
