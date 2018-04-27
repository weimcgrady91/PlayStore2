package com.qun.test.playstore.fragment;

import android.support.v4.util.LruCache;


/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class FragmentFactory {
    private static volatile FragmentFactory sFactory;
    private final LruCache<Integer, BaseFragment> mLruCache;

    private FragmentFactory() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024)/8;
        mLruCache = new LruCache<>(maxSize);
    }

    public static FragmentFactory getInstance() {
        if (sFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFactory == null) {
                    sFactory = new FragmentFactory();
                }
            }
        }
        return sFactory;
    }

    private void putFragment(int position, BaseFragment fragment) {
        mLruCache.put(position, fragment);
    }

    public BaseFragment getFragment(int position) {
        BaseFragment fragment = mLruCache.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = HomeFragment.newInstance();
                    break;
                case 1:
                    fragment = AppFragment.newInstance();
                    break;
                case 2:
                    fragment = GameFragment.newInstance();
                    break;
                case 3:
                    fragment = SubjectFragment.newInstance();
                    break;
                case 4:
                    fragment = RecommendFragment.newInstance();
                    break;
                case 5:
                    fragment = CategoryFragment.newInstance();
                    break;
                case 6:
                    fragment = HotFragment.newInstance();
                    break;
            }
            putFragment(position,fragment);
        }
        return fragment;
    }
}
