package com.pandaq.pandaeye.api;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.config.Config;
import com.pandaq.pandaqlib.okhttp.CustomInterceptor;
import com.pandaq.pandaqlib.okhttp.CustomNetWorkInterceptor;
import com.pandaq.pandaqlib.okhttp.HttpsUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 集中处理Api相关配置的Manager类
 */
public class ApiManager {

    private DouBanApi mDouBanApi;
    private ZhihuDailyApi mDailyApi;
    private TopNewsApi mNewsApi;
    private static ApiManager sApiManager;

    public static ApiManager getInstence() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    /**
     * 封装配置豆瓣API
     */
    public DouBanApi getDoubanService() {
        if (mDouBanApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.baseDouBanUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
                    .baseUrl(Config.baseZhiHuUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mDailyApi = retrofit.create(ZhihuDailyApi.class);
        }
        return mDailyApi;
    }

    /**
     * 封装网易新闻API
     */
    public TopNewsApi getTopNewsServie() {
        if (mNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.baseNewsUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mNewsApi = retrofit.create(TopNewsApi.class);
        }
        return mNewsApi;
    }

    /**
     * POST测试API封装
     */
    public TestPostApi loginService(InputStream inputStream) throws IOException {
        TestPostApi testPostApi;
        OkHttpClient mOkHttpClient = new HttpsUtil()
//                .getTrustAllClient()
                .getTrusClient(inputStream)
                .newBuilder()
                .addInterceptor(new CustomInterceptor(CustomApplication.sContext))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseTestUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
        testPostApi = retrofit.create(TestPostApi.class);
        return testPostApi;
    }
}
