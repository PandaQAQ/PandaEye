package com.pandaq.pandaeye.api;

import com.pandaq.pandaeye.modules.zhihu.home.mvp.ZhiHuDaily;
import com.pandaq.pandaeye.modules.zhihu.zhihudetail.ZhihuStoryContent;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by PandaQ on 2016/9/13.
 * email : 767807368@qq.com
 */
public interface ZhihuDailyApi {

    //获取最近的日报
    @GET("news/latest")
    Observable<ZhiHuDaily> getLatestZhihuDaily();

    //获取某一时间之前的日报（本例用于加载更多）
    @GET("news/before/{date}")
    Observable<ZhiHuDaily> getZhihuDaily(@Path("date") String date);

    @GET("news/{id}")
    Observable<ZhihuStoryContent> getStoryContent(@Path("id") String id);
}
