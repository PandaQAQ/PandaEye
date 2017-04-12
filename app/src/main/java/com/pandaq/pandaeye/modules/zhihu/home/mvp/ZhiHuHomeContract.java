package com.pandaq.pandaeye.modules.zhihu.home.mvp;

import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface ZhiHuHomeContract {
    interface View {
        void showRefreshBar();

        void hideRefreshBar();

        void refreshData();

        void refreshSuccessed(ZhiHuDaily stories);

        void refreshFail(String errMsg);

        void loadMoreData();

        void loadSuccessed(ArrayList<BaseItem> stories);

        void loadFail(String errMsg);
    }

    interface Presenter {
        void refreshZhihuDaily();

        void loadMoreData();

        void loadCache();
    }
}
