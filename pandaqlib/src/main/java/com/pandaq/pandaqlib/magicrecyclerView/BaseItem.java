package com.pandaq.pandaqlib.magicrecyclerView;

import java.io.Serializable;

import static com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter.RecyclerItemType.TYPE_NORMAL;

/**
 * Created by PandaQ on 2016/11/16.
 * email : 767807368@qq.com
 */

public class BaseItem<T> implements Serializable {

    //数据类型
    private BaseRecyclerAdapter.RecyclerItemType mItemType;
    //实际使用的数据
    private T data;

    public BaseRecyclerAdapter.RecyclerItemType getItemType() {
        return mItemType == null ? TYPE_NORMAL : mItemType;
    }

    public void setItemType(BaseRecyclerAdapter.RecyclerItemType itemType) {
        mItemType = itemType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
