package com.pandaq.pandaeye.api;

import com.pandaq.pandaeye.entity.DouBan.MovieTop250;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 豆瓣电影API接口
 */
public interface DouBanApi {

    @GET("movie/top250")
    Observable<MovieTop250> getTop250(@Query("start") int start, @Query("count") int count);
}
