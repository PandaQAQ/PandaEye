package com.pandaq.pandaeye.modules.news.newsdetail;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

interface NewsDetailContract {
    interface View extends ImpBaseView{
        void showProgressBar();

        void hideProgressBar();

        void loadTopNewsInfo(String newsId);

        void loadFail(String errmsg);

        void loadSuccess(NewsContent topNewsContent);

    }

    interface Presenter extends ImpBasePresenter{
        void loadNewsContent(final String id);
    }
}
