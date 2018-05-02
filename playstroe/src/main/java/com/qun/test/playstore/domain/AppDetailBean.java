package com.qun.test.playstore.domain;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class AppDetailBean {
    public String author;
    public String date;
    public String des;
    public String downloadNum;
    public String downloadUrl;
    public String iconUrl;
    public String id;
    public String name;
    public String packageName;
    public ArrayList<Safe> safe;
    public int size;
    public float stars;
    public String version;

    public class Safe {
        public String safeDes;
        public String safeDesColor;
        public String safeDesUrl;
        public String safeUrl;

        @Override
        public String toString() {
            return "Safe{" +
                    "safeDes='" + safeDes + '\'' +
                    ", safeDesColor='" + safeDesColor + '\'' +
                    ", safeDesUrl='" + safeDesUrl + '\'' +
                    ", safeUrl='" + safeUrl + '\'' +
                    '}';
        }
    }

    public ArrayList<String> screen;

    @Override
    public String toString() {
        return "AppDetailBean{" +
                "author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", des='" + des + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", safe=" + safe +
                ", size=" + size +
                ", stars=" + stars +
                ", version='" + version + '\'' +
                ", screen=" + screen +
                '}';
    }
}
