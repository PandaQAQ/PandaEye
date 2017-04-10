package com.pandaq.pandaeye.ui.ImplView;

import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PandaQ on 2017/3/15.
 * 分类视频界面 View 接口
 */

public interface ITypedVideosActivity {
    void loadMore();

    void noMoreVideo();

    void loadMoreSuccess(ArrayList<BaseItem> list);

    void loadFail(String errCode, String errMsg);

}
