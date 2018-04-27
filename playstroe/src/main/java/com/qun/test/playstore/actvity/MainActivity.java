package com.qun.test.playstore.actvity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.lib.pagetabindicator.TabPageIndicator;
import com.qun.test.playstore.R;
import com.qun.test.playstore.fragment.BaseFragment;
import com.qun.test.playstore.fragment.FragmentFactory;


public class MainActivity extends BaseImmerseActivity {
    public Toolbar mToolbar;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    private ViewPager mViewPager;
    private TabPageIndicator mPageIndicator;
    private HomeAdapter mAdapter;
    private int[] titleIds = new int[]{
            R.string.page_title_home,
            R.string.page_title_app,
            R.string.page_title_game,
            R.string.page_title_subject,
            R.string.page_title_recommend,
            R.string.page_title_catetory,
            R.string.page_title_hot
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewCreated() {
        //        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayout, getResources().getColor(R.color.colorPrimary),0);
        initActionBar();
        initDrawerLayout();
        initContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDrawerLayout.removeDrawerListener(mDrawerToggle);
        mViewPager.removeOnPageChangeListener(mViewPagerChangerListener);
    }

    private void initContent() {
        mViewPager = findViewById(R.id.view_pager);
        mAdapter = new HomeAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mViewPagerChangerListener);
        mPageIndicator = findViewById(R.id.pager_indicator);
        mPageIndicator.setViewPager(mViewPager);
    }

    private ViewPager.OnPageChangeListener mViewPagerChangerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class HomeAdapter extends FragmentStatePagerAdapter {
        public HomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.getInstance().getFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return titleIds.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titleIds[position]);
        }
    }

    private void initActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void initDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
