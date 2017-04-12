package com.pandaq.pandaeye.modules.video.videotypelist.mvp;

import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface VideoTypedContract {
    interface View {
        void loadMore();

        void noMoreVideo();

        void loadMoreSuccess(ArrayList<BaseItem> list);

        void loadFail(String errCode, String errMsg);
    }

    interface Presenter {
        void loadVideos(String id);

        void loadLives(String id);
    }
}
