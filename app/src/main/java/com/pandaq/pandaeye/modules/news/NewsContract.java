package com.pandaq.pandaeye.modules.news;

import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface NewsContract {
    interface View {
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

    interface Presenter {
        void refreshNews();

        void loadMore();

        void loadCache();
    }
}
