package com.pandaq.pandaqlib.okhttp;

import android.content.Context;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PandaQ on 2016/11/10.
 * email : 767807368@qq.com
 */

public class CustomInterceptor implements Interceptor {
    private Context mContext;

    public CustomInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
