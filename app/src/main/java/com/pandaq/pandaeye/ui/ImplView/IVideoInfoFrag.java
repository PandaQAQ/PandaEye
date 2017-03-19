package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.model.video.MovieInfo;

/**
 * Created by PandaQ on 2017/3/19.
 * 视频信息界面接口
 */

public interface IVideoInfoFrag {
    void loadInfo();

    String getDataId();

    void loadInfoFail(String errCode, String errMsg);

    void loadInfoSuccess(MovieInfo movieInfo);
}
