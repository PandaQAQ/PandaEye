package com.pandaq.pandaeye.modules.webphto.mvp;

import com.pandaq.pandaeye.modules.ImpBasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;

/**
 * Created by PandaQ on 2017/7/4.
 * 查看网页图片的 Contact
 */

interface WebPhotoContact {
    interface View extends ImpBaseView {
        void savePicSuccess(String path);

        void savePicFail(String msg);
    }

    interface Presenter extends ImpBasePresenter {
        void savePic(String url);
    }
}
