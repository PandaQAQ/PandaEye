package com.pandaq.pandaeye.modules.news.newsdetail;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface NewsDetailContract {
    interface View {
        void showProgressBar();

        void hideProgressBar();

        void loadTopNewsInfo(String newsId);

        void loadFail(String errmsg);

        void loadSuccess(NewsContent topNewsContent);

    }

    interface Presenter {
        void loadNewsContent(final String id);
    }
}
