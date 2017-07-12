package com.pandaq.pandaeye.modules.zhihu.zhihudetail;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

interface ZhiHuDetailContract {
    interface View extends ImpBaseView{

        void loadZhihuStory();

        void loadFail(String errmsg);

        void loadSuccess(ZhihuStoryContent zhihuStory);
    }

    interface Presenter extends ImpBasePresenter{
        void loadStory(String id);
    }
}
