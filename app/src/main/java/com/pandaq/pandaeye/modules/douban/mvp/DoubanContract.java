package com.pandaq.pandaeye.modules.douban.mvp;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface DoubanContract {
    interface View extends ImpBaseView{
        void showProgressBar();

        void hideProgressBar();

        void loadMoreData();

        void refreshData();

        void loadSuccessed(ArrayList<BaseItem> movieSubjects);

        void loadFail(String errMsg);

        void refreshSucceed(ArrayList<BaseItem> movieSubjects);

        void refreshFail(String errMsg);
    }

    interface Presenter extends ImpBasePresenter {
        void refreshData();

        void loadMoreData();

        void loadCache();
    }
}
