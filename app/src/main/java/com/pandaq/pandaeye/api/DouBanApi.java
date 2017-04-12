package com.pandaq.pandaeye.api;

import com.pandaq.pandaeye.modules.douban.mvp.MovieTop250;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 豆瓣API接口
 */
public interface DouBanApi {

    @GET("movie/top250")
    Observable<MovieTop250> getTop250(@Query("start") int start, @Query("count") int count);
}
