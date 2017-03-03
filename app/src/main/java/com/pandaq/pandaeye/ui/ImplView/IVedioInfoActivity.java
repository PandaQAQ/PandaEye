package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.entity.video.MovieInfo;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频详情页接口
 */

public interface IVedioInfoActivity {
    String getDataId();

    void loadVideoInfoSuccess(MovieInfo info);

    void loadVideoInfoFail(String errCode, String errMsg);

}
