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
    /**
     * DiskCache
     */
    public static final long CACHE_MAXSIZE = 10 * 1024 * 1024;
    public static final String CACHE_TOPNEWS_FILE = "top_news_cache_file";
    public static final String CACHE_TOPNEWS = "top_news_cache";

    public static final String CACHE_ZHIHU_FILE = "zhihu_cache_file";
    public static final String CACHE_ZHIHU_DAILY = "zhihu_daily_cache";

    public static final String CACHE_DOUBAN_FILE = "douban_cache_file";
    public static final String CACHE_DOUBAN_MOVIE = "douban_movie_cache";
}
