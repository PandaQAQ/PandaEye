package com.pandaq.pandaeye.model.api;

import com.pandaq.pandaeye.model.neteasynews.TopNewsList;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 * 网易新闻Api
 */
public interface TopNewsApi {
    @GET("headline/{type}/{index}-20.html")
    Observable<TopNewsList> getTopNews(@Path("type") String type, @Path("index") String index);

    @GET("{id}/full.html")
    Observable<ResponseBody> getNewsContent(@Path("id") String id);
}
