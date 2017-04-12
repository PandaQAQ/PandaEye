package com.pandaq.pandaeye.modules.news.headline;

import com.google.gson.annotations.SerializedName;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.modules.news.NewsBean;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 */

public class TopNewsList {

    @SerializedName(Constants.NETEASY_NEWS_HEADLINE)

    private ArrayList<NewsBean> mTopNewsArrayList;

    public ArrayList<NewsBean> getTopNewsArrayList() {
        return mTopNewsArrayList;
    }
}
