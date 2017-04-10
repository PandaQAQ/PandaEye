package com.pandaq.pandaeye.config;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 应用配置类，用于配置
 */
public class Config {
    public static final boolean DEBUG = true;
    public static String DOUBAN_API_URL = "http://api.douban.com/v2/";
    public static String ZHIHU_API_URL = "http://news-at.zhihu.com/api/4/";
    public static String NETEASY_NEWS_API = "http://c.m.163.com/nc/article/";
    public static String MOVIE_API_URL = "http://api.svipmovie.com/front/";
    public static String GITHUB_API_URL = "https://api.github.com/";
    /**
     * 视频分类ID
     */
    //热点资讯
    public static final String MOVIE_TYPE_HOT = "402834815584e463015584e539700019";
    //精彩推荐
    public static final String MOVIE_TYPE_RECOMMEND = "402834815584e463015584e53843000b";
    //好莱坞
    public static final String MOVIE_TYPE_HOLLYWOOD = "402834815584e463015584e538140009";
    //专题 根据当前时间戳获取
//    public static String MOVIE_TYPE_TOPIC = "402834815584e463015584e538140009";
    //直播专区
    public static final String MOVIE_TYPE_LIVE = "402834815584e463015584e537d00006";
    //微电影
    public static final String MOVIE_TYPE_LITTLEMOVIE = "402834815584e463015584e538ea0012";
    //日韩精选
    public static final String MOVIE_TYPE_JSKS = "402834815584e463015584e538a2000f";
    //大咖剧场
    public static final String MOVIE_TYPE_BIGBRO = "402834815584e463015584e537bf0005";
    //午夜剧场
    public static final String MOVIE_TYPE_MIDNIGHT = "402834815584e463015584e5390d0014";
}
