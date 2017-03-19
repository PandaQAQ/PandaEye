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
    // 加载完视频信息后将url回传
    public final static int LOADED_DATA_CODE = 1002;
    // 将加载的视频对应的 picurl 传递过来
    public final static int LOADED_VIDEO_PIC_CODE = 1003;
    // 接收视频 Id
    public final static int ACCEPT_VIDEO_DATAID = 0;
    /**
     * 退出应用前先滚动到顶部
     */
    public final static int BACK_PRESSED_CODE = 1003;
    public final static String BACK_PRESSED_DATA = "backPressed";
}
