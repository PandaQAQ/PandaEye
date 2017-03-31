package com.pandaq.pandaeye.model.neteasynews;

import com.google.gson.annotations.SerializedName;
import com.pandaq.pandaeye.config.Constants;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 */

public class RecommendNewsList {

    @SerializedName(Constants.NETEASY_NEWS_HEALTH)

    private ArrayList<NewsBean> mRecommNewsArrayList;

    public ArrayList<NewsBean> getRecNewsArrayList() {
        return mRecommNewsArrayList;
    }
}
