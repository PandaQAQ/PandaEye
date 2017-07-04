package com.pandaq.pandaeye.modules.webphto.mvp;

/**
 * Created by PandaQ on 2017/7/4.
 * 查看网页图片的 Contact
 */

public interface WebPhotoContact {
    interface View {
        void savePicSuccess(String path);

        void savePicFail(String msg);
    }

    interface Presenter {
        void savePic(String url);
    }
}
