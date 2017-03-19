package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;

/**
 * Created by PandaQ on 2017/3/19.
 * 视频评论界面接口
 */

public interface IVideoCommentFrag {
    void loadComment();

    void refreshComment();

    String getDataId();

    void showComment(ArrayList<BaseItem> listBeen);

    void noMore();

    void loadFail();
}
