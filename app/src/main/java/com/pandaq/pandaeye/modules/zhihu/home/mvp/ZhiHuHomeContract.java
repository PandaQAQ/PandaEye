package com.pandaq.pandaeye.modules.zhihu.home.mvp;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

interface ZhiHuHomeContract {
    interface View extends ImpBaseView {
        void showRefreshBar();

        void hideRefreshBar();

        void refreshData();

        void refreshSuccessed(ZhiHuDaily stories);

        void refreshFail(String errMsg);

        void loadMoreData();

        void loadSuccessed(ArrayList<BaseItem> stories);

        void loadFail(String errMsg);
    }

    interface Presenter extends ImpBasePresenter {
        void refreshZhihuDaily();

        void loadMoreData();

        void loadCache();
    }
}
