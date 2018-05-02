package com.qun.test.playstore.domain;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class CategoryBean {
    public List<Category> infos;
    public String title;

    public class Category {
        public String name1;
        public String name2;
        public String name3;
        public String url1;
        public String url2;
        public String url3;

        @Override
        public String toString() {
            return "Category{" +
                    "name1='" + name1 + '\'' +
                    ", name2='" + name2 + '\'' +
                    ", name3='" + name3 + '\'' +
                    ", url1='" + url1 + '\'' +
                    ", url2='" + url2 + '\'' +
                    ", url3='" + url3 + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "categorys=" + infos +
                ", title='" + title + '\'' +
                '}';
    }
}
