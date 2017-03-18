package com.pandaq.pandaeye.presenter.video;

import com.pandaq.pandaeye.model.api.ApiManager;
import com.pandaq.pandaeye.model.video.CommentBean;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.model.video.MovieResponse;
import com.pandaq.pandaeye.presenter.BasePresenter;
import com.pandaq.pandaeye.ui.ImplView.IVedioInfoActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频详情页面的 Presenter
 */

public class VideoInfoPresenter extends BasePresenter {
    private IVedioInfoActivity mInfoActivity;
    private int currentPage = 0;
    private String dataId = "";

    public VideoInfoPresenter(IVedioInfoActivity infoActivity) {
        mInfoActivity = infoActivity;
    }

    /**
     * 获取视频详情页
     */
    public void loadVideoInfo() {
        ApiManager.getInstence()
                .getMovieService()
                .getMovieDaily(mInfoActivity.getDataId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse<MovieInfo>>() {
                    @Override
                    public void onError(Throwable e) {
//                        RxBus.getDefault().postWithCode(RxConstants.LOAD_DATA_INFO_RESULT, e);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(MovieResponse<MovieInfo> movieInfo) {
                        mInfoActivity.loadVideoInfoSuccess(movieInfo.getData());
                        //发送数据到 fragment 中
//                        RxBus.getDefault().postWithCode(RxConstants.LOAD_DATA_INFO_RESULT, movieInfo.getData());
                    }
                });
    }

    /**
     * 获取视频评论
     */
    private void loadVideoComment() {
        ApiManager.getInstence()
                .getMovieService()
                .getCommentList(mInfoActivity.getDataId(), String.valueOf(currentPage + 1))
                .map(new Function<MovieResponse<CommentBean>, CommentBean>() {
                    @Override
                    public CommentBean apply(MovieResponse<CommentBean> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的评论后
//                            RxBus.getDefault().postWithCode(RxConstants.LOADED_ALL_COMMENT_CODE, RxConstants.LOADED_ALL_COMMENT_MSG);
                        }
                        return response.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentBean>() {
                    @Override
                    public void onError(Throwable e) {
//                        RxBus.getDefault().postWithCode(RxConstants.LOAD_DATA_COMMENT_RESULT, e);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
//                        RxBus.getDefault().postWithCode(RxConstants.LOAD_DATA_COMMENT_RESULT, commentBean);
                    }
                });

    }

    /**
     * 刷新评论
     */
    public void refreshComment() {
        currentPage = 0;
        loadVideoComment();
    }

    /**
     * 加载更多评论
     */
    public void loadMoreComment() {
        loadVideoComment();
    }
}
