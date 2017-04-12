package com.pandaq.pandaeye.modules.zhihu.zhihudetail;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface ZhiHuDetailContract {
    interface View {
        void showProgressBar();

        void hideProgressBar();

        void loadZhihuStory();

        void loadFail(String errmsg);

        void loadSuccess(ZhihuStoryContent zhihuStory);
    }

    interface Presenter {
        void loadStory(String id);
    }
}
