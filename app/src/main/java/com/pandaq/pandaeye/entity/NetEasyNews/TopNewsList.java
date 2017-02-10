package com.pandaq.pandaeye.entity.NetEasyNews;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 */

public class TopNewsList {

    @SerializedName("T1348647909107")

    private ArrayList<TopNews> mTopNewsArrayList;

    public ArrayList<TopNews> getTopNewsArrayList() {
        return mTopNewsArrayList;
    }
}
