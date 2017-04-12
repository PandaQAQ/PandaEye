package com.pandaq.pandaeye.modules.news.health;

import com.google.gson.annotations.SerializedName;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.modules.news.NewsBean;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 */

public class HealthNewsList {

    @SerializedName(Constants.NETEASY_NEWS_HEALTH)

    private ArrayList<NewsBean> mHealthNewsArrayList;

    public ArrayList<NewsBean> getRecNewsArrayList() {
        return mHealthNewsArrayList;
    }
}
