package com.pandaq.pandaqlib.okhttp;

import android.content.Context;
import android.database.ContentObserver;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by PandaQ on 2016/11/10.
 * email : 767807368@qq.com
 */

public class CustomNetWorkInterceptor implements Interceptor {

    private Context mContext;

    public CustomNetWorkInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (NetWorkUtil.isNetWorkAvailable(mContext)) {
            int maxAge = 60; // 在线缓存在1分钟内可读取
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
