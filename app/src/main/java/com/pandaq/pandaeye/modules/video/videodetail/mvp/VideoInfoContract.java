package com.pandaq.pandaeye.modules.video.videodetail.mvp;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

/**
 * Created by PandaQ on 2017/4/12.
 * 767807368@qq.com
 */

public interface VideoInfoContract {
    interface View extends ImpBaseView {
        void loadInfo();

        String getDataId();

        void loadInfoFail(String errCode, String errMsg);

        void loadInfoSuccess(MovieInfo movieInfo);
    }

    interface Presenter extends ImpBasePresenter {
        void loadVideoInfo();
    }
}
