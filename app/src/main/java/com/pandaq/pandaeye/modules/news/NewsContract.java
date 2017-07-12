package com.pandaq.pandaeye.modules.news;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface NewsContract {
    interface View extends ImpBaseView{
        void showRefreshBar();

        void hideRefreshBar();

        void refreshNews();

        void refreshNewsFail(String errorMsg);

        void refreshNewsSuccessed(ArrayList<BaseItem> topNews);

        void loadMoreNews();

        void loadMoreFail(String errorMsg);

        void loadMoreSuccessed(ArrayList<BaseItem> topNewses);

        void loadAll();
    }

    interface Presenter extends ImpBasePresenter {
        void refreshNews();

        void loadMore();

        void loadCache();
    }
}
