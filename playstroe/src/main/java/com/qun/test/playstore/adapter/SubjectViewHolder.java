package com.qun.test.playstore.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.qun.test.playstore.R;
import com.qun.test.playstore.util.UIUtil;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.SubBean;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SubjectViewHolder extends BasicViewHolder<SubBean> {
    public ImageView mIvPic;
    public TextView mTitle;

    @Override
    public void bindView() {
        mItemView = UIUtil.inflate(R.layout.item_subject);
        mIvPic = mItemView.findViewById(R.id.iv_pic);
        mTitle = mItemView.findViewById(R.id.tv_title);
    }

    @Override
    public void refreshView(SubBean data) {
        mTitle.setText(data.des);
        Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + data.url).error(R.drawable.ic_default).
                placeholder(R.drawable.ic_default).into(mIvPic);
    }
}
