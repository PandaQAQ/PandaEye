package com.pandaq.pandaeye.api;

import com.pandaq.pandaeye.entity.NetEasyNews.TopNewsList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 * 网易新闻Api
 */
public interface TopNewsApi {
    @GET("{type}/{index}-20.html")
    Observable<TopNewsList> getTopNews(@Path("type") String type, @Path("index") String index);

    @GET("{id}/full.html")
    Observable<String> getNewsContent(@Path("id") String id);
}
