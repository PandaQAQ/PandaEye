package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaeye.entity.video.CommentBean;
import com.pandaq.pandaeye.entity.video.MovieInfo;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频详情页接口
 */

public interface IVedioInfoActivity {
    String getDataId();

    void loadVideoInfoSuccess(MovieInfo info);

    void loadVideoInfoFail(String errCode, String errMsg);

    void refreshCommentSuccess(ArrayList<CommentBean.ListBean> commentBean);

    void refreshCommentFail(String errCode, String errMsg);

    void loadCommentSuccess(ArrayList<CommentBean.ListBean> commentBean);

    void loadCommentFail(String errCode, String errMsg);

    void noMoreComment();
}
