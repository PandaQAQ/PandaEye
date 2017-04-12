package com.pandaq.pandaeye.api;

import com.pandaq.pandaeye.config.Config;
import com.pandaq.pandaqlib.okhttp.CustomInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 集中处理Api相关配置的Manager类
 */
public class ApiManager {

    private DouBanApi mDouBanApi;
    private ZhihuDailyApi mDailyApi;
    private NetEasyNewsApi mNewsApi;
    private MovieApi mMovieApi;
    private GithubApi mGithubApi;
    private static ApiManager sApiManager;

    private static OkHttpClient mClient;

    private ApiManager() {

    }

    public static ApiManager getInstence() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return sApiManager;
    }

    /**
     * 封装配置豆瓣API
     */
    public DouBanApi getDoubanService() {
        if (mDouBanApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mClient)
                    .baseUrl(Config.DOUBAN_API_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mDouBanApi = retrofit.create(DouBanApi.class);
        }
        return mDouBanApi;
    }

    /**
     * 封装配置知乎API
     */
    public ZhihuDailyApi getZhihuService() {
        if (mDailyApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.ZHIHU_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mDailyApi = retrofit.create(ZhihuDailyApi.class);
        }
        return mDailyApi;
    }

    /**
     * 封装网易新闻API
     */
    public NetEasyNewsApi getTopNewsServie() {
        if (mNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.NETEASY_NEWS_API)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mNewsApi = retrofit.create(NetEasyNewsApi.class);
        }
        return mNewsApi;
    }

    /**
     * 封装视频 API
     */
    public MovieApi getMovieService() {
        if (mMovieApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.MOVIE_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mMovieApi = retrofit.create(MovieApi.class);
        }
        return mMovieApi;
    }

    /**
     * 封装 gayhub API
     */
    public GithubApi getGithubService() {
        if (mGithubApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.GITHUB_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mGithubApi = retrofit.create(GithubApi.class);
        }
        return mGithubApi;
    }

}
