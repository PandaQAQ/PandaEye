package com.pandaq.pandaeye.presenter.video;

import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.model.video.CommentBean;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.model.video.MovieResponse;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.ui.ImplView.IVedioInfoActivity;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频详情页面的 Presenter
 */

public class VideoInfoPresenter extends BasePresenter {
    private IVedioInfoActivity mInfoActivity;
    private int currentPage = 1;

    public VideoInfoPresenter(IVedioInfoActivity infoActivity) {
        mInfoActivity = infoActivity;
    }

    /**
     * 获取视频详情页
     */
    public void loadVideoInfo() {
        Subscription subscription = ApiManager.getInstence()
                .getMovieService()
                .getMovieDaily(mInfoActivity.getDataId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResponse<MovieInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        RxBus.getDefault().post(e);
                    }

                    @Override
                    public void onNext(MovieResponse<MovieInfo> movieInfo) {
                        mInfoActivity.loadVideoInfoSuccess(movieInfo.getData());
                        //发送数据到 fragment 中
                        RxBus.getDefault().post(movieInfo.getData());
                    }
                });
        addSubscription(subscription);
    }

    /**
     * 获取视频评论
     */
    private void loadVideoComment() {
        Subscription subscription = ApiManager.getInstence()
                .getMovieService()
                .getCommentList(mInfoActivity.getDataId(), String.valueOf(currentPage))
                .map(new Func1<MovieResponse<CommentBean>, CommentBean>() {
                    @Override
                    public CommentBean call(MovieResponse<CommentBean> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的评论后
                            RxBus.getDefault().postWithCode(RxConstants.LOADED_ALL_COMMENT_CODE, RxConstants.LOADED_ALL_COMMENT_MSG);
                        }
                        return response.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        RxBus.getDefault().post(e);
                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
                        RxBus.getDefault().post(commentBean);
                    }
                });
        addSubscription(subscription);

    }

    /**
     * 刷新评论
     */
    public void refreshComment() {
        currentPage = 1;
        loadVideoComment();
    }

    /**
     * 加载更多评论
     */
    public void loadMoreComment() {
        loadVideoComment();
    }
}
