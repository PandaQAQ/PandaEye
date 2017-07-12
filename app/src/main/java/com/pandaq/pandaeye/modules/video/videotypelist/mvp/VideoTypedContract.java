package com.pandaq.pandaeye.modules.video.videotypelist.mvp;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

interface VideoTypedContract {
    interface View extends ImpBaseView {
        void loadMore();

        void noMoreVideo();

        void loadMoreSuccess(ArrayList<BaseItem> list);

        void loadFail(String errCode, String errMsg);
    }

    interface Presenter extends ImpBasePresenter {
        void loadVideos(String id);

        void loadLives(String id);

        void loadData(String title);
    }
}
