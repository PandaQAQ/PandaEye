package com.pandaq.pandaeye.model.api;

import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.neteasynews.AutoNewsList;
import com.pandaq.pandaeye.model.neteasynews.DadaNewsList;
import com.pandaq.pandaeye.model.neteasynews.MilitaryNewsList;
import com.pandaq.pandaeye.model.neteasynews.RecommNewsList;
import com.pandaq.pandaeye.model.neteasynews.TecNewsList;
import com.pandaq.pandaeye.model.neteasynews.TopNewsList;
import com.pandaq.pandaeye.model.neteasynews.TravelNewsList;
import com.pandaq.pandaeye.ui.news.TravelFragment;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by PandaQ on 2016/9/20.
 * email : 767807368@qq.com
 * 网易新闻Api 都默认一次加载20条
 */
public interface NetEasyNewsApi {
    @GET(Constants.NETEASY_NEWS_HEADLINE + "/{index}-20.html")
    Observable<TopNewsList> getTopNews(@Path("index") String index);

    @GET(Constants.NETEASY_NEWS_TEC + "/{index}-20.html")
    Observable<TecNewsList> getTecNews(@Path("index") String index);

    @GET(Constants.NETEASY_NEWS_AUTO + "/{index}-20.html")
    Observable<AutoNewsList> getAutoNews(@Path("index") String index);

    @GET(Constants.NETEASY_NEWS_RECOMMEND + "/{index}-20.html")
    Observable<RecommNewsList> getRecommendNews(@Path("index") String index);

    @GET(Constants.NETEASY_NEWS_DADA + "/{index}-20.html")
    Observable<DadaNewsList> getDadaNews(@Path("index") String index);

    @GET(Constants.NETEASY_NEWS_MILITARY + "/{index}-20.html")
    Observable<MilitaryNewsList> getMilitaryNews(@Path("index") String index);

    @GET(Constants.NETEASY_NEWS_TRAVEL + "/{index}-20.html")
    Observable<TravelNewsList> getTravelNews(@Path("index") String index);

    @GET("{id}/full.html")
    Observable<ResponseBody> getNewsContent(@Path("id") String id);
}
