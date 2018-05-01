package com.qun.test.playstore.adapter;

import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qun.test.playstore.App;
import com.qun.test.playstore.R;
import com.qun.test.playstore.Util.UIUtil;
import com.qun.test.playstore.constant.ServerConfig;
import com.qun.test.playstore.domain.AppInfo;
import com.qun.test.playstore.domain.HomeBean;
import com.squareup.picasso.Picasso;


/**
 * Created by Administrator on 2018/4/30.
 */

public class HomeViewHolder extends BasicViewHolder<AppInfo> {
    public ImageView mImgIcon;
    public TextView mTvName;
    public TextView mTvSize;
    public TextView mTvDes;
    public RatingBar mRatingBar;

    @Override
    public void bindView() {
        mItemView = UIUtil.inflate(R.layout.item_home_frg);
        mTvName = mItemView.findViewById(R.id.tv_name);
        mImgIcon = mItemView.findViewById(R.id.img_icon);
        mTvSize = mItemView.findViewById(R.id.tv_size);
        mTvDes = mItemView.findViewById(R.id.tv_des);
        mRatingBar = mItemView.findViewById(R.id.rating_bar);
    }

    @Override
    public void refreshView(AppInfo data) {
        mTvName.setText(data.name);
        mTvSize.setText(data.size + "");
        mTvDes.setText(data.des);
        mRatingBar.setRating(data.stars);
        mTvSize.setText(Formatter.formatFileSize(App.sContext, data.size)
        );
        Picasso.get().load(ServerConfig.HOST + ServerConfig.PATH_IMAGE + "?name=" + data.iconUrl).error(R.drawable.ic_default).
                placeholder(R.drawable.ic_default).into(mImgIcon);
    }
}
