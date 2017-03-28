package com.pandaq.pandaeye.config;

/**
 * Created by PandaQ on 2016/12/20.
 * email : 767807368@qq.com
 */

public class Constants {

    //NetWork Erro
    public static String ERRO = "错误信息:";
    //SecretUtil
    public static String NOSUCHALGORITHM = "不支持此种加密方式";
    public static String UNSUPPENCODING = "不支持的编码格式";
    /**
     * SharePreference
     */
    public static final String SP_NAME = "com.pandaq.pandaeye";
    /**
     * BundleKey
     */
    public static final String BUNDLE_KEY_TITLE = "title";//bundle传至用的key各处可以复用
    public static final String BUNDLE_KEY_ID = "id";
    public static final String BUNDLE_KEY_IMG_URL = "img_url";
    public static final String BUNDLE_KEY_TRANSLATION_EXPORD = "expord";//转场动画是否为expord类型
    public static final String BUNDLE_KEY_DATAID = "dataId";
    /**
     * DiskCache
     */
    public static final long CACHE_MAXSIZE = 10 * 1024 * 1024;
    public static final String CACHE_NEWS_FILE = "top_news_cache_file";
    public static final String CACHE_HEADLINE_NEWS = "headline_news_cache";
    public static final String CACHE_TEC_NEWS = "tec_news_cache";
    public static final String CACHE_SPORT_NEWS = "sport_news_cache";
    public static final String CACHE_RECOMMEND_NEWS = "recommend_news_cache";
    public static final String CACHE_DADA_NEWS = "dada_news_cache";
    public static final String CACHE_MILITARY_NEWS = "military_news_cache";
    public static final String CACHE_TRAVEL_NEWS = "travel_news_cache";

    public static final String CACHE_ZHIHU_FILE = "zhihu_cache_file";
    public static final String CACHE_ZHIHU_DAILY = "zhihu_daily_cache";

    public static final String CACHE_DOUBAN_FILE = "douban_cache_file";
    public static final String CACHE_DOUBAN_MOVIE = "douban_movie_cache";

    public static final String CACHE_VIDEO_FILE = "video_cache_file";
    public static final String CACHE_VIDEO = "video_cache";

    /**
     * video
     */
    public static final String SHOW_TYPE_IN = "IN";
    public static final String SHOW_TYPE_BANNER = "banner";
    public static final String TYPED_MORE_TITLE = "videoType";
    //热点资讯
    public static final String MOVIE_TYPE_HOT = "热点资讯";
    //精彩推荐
    public static final String MOVIE_TYPE_RECOMMEND = "精彩推荐";
    //好莱坞
    public static final String MOVIE_TYPE_HOLLYWOOD = "好莱坞";
    //专题 根据当前时间戳获取
    public static final String MOVIE_TYPE_TOPIC = "专题";
    //直播专区
    public static final String MOVIE_TYPE_LIVE = "直播专区";
    //微电影
    public static final String MOVIE_TYPE_LITTLEMOVIE = "微电影";
    //日韩精选
    public static final String MOVIE_TYPE_JSKS = "日韩精选";
    //大咖剧场
    public static final String MOVIE_TYPE_BIGBRO = "大咖剧场";
    //午夜剧场
    public static final String MOVIE_TYPE_MIDNIGHT = "午夜剧场";

    /**
     * 设置
     */
    public static final String USER_PIC = "/pandaeye/images";
    /**
     * 网易新闻
     */
    public static final String NETEASY_NEWS_HEADLINE = "T1348647909107";//头条
    public static final String NETEASY_NEWS_TEC = "T1348649580692";//科技
    public static final String NETEASY_NEWS_SPORT = "T1348649079062";//体育
    public static final String NETEASY_NEWS_RECOMMEND = "T1467284926140";//精选
    public static final String NETEASY_NEWS_DADA = "T1444289532601";//哒哒趣闻
    public static final String NETEASY_NEWS_MILITARY = "T1348648141035";//军事
    public static final String NETEASY_NEWS_TRAVEL = "T1348654204705";//旅游


}
