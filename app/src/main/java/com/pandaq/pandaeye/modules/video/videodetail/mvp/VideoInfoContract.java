package com.pandaq.pandaeye.modules.video.videodetail.mvp;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface VideoInfoContract {
    interface View {
        void loadInfo();

        String getDataId();

        void loadInfoFail(String errCode, String errMsg);

        void loadInfoSuccess(MovieInfo movieInfo);
    }

    interface Presenter {
        void loadVideoInfo();
    }
}
