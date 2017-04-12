package com.pandaq.pandaeye.modules.news.military;

import com.google.gson.annotations.SerializedName;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.modules.news.NewsBean;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 */

public class MilitaryNewsList {

    @SerializedName(Constants.NETEASY_NEWS_MILITARY)

    private ArrayList<NewsBean> mMilitaryNewsArrayList;

    public ArrayList<NewsBean> getMilitaryNewsArrayList() {
        return mMilitaryNewsArrayList;
    }
}
