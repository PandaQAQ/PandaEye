package com.pandaq.pandaeye.rxbus;

/**
 * Created by PandaQ on 2017/3/10.
 * RxBus 的一些共有常量定义
 */

public class RxConstants {
    /**
     * Video  相关
     */
    // Video 已加载完所有评论的Rx事件 Code
    public final static int LOADED_ALL_COMMENT_CODE = 1000;
    // Video 已加载完所有评论的Rx事件消息内容
    public final static String LOADED_ALL_COMMENT_MSG = "我是有底线的QAQ";
    // Video 子fragment向videoInfoActivity 发送操作事件的 Code
    public final static int APPLY_DATA_CODE = 1001;
    //重新加载电影信息
    public final static int APPLY_DATA_MSG_RELOADINFO = 0;
    //刷新评论
    public final static int APPLY_DATA_MSG_REFRESHCOMMENT = 1;
    //加载更多评论
    public final static int APPLY_DATA_MSG_LOADMORECOMMENT = 2;
}
