package com.pandaq.pandaeye.modules.video.videodetail.mvp;

import com.pandaq.pandaeye.api.ApiManager;
import com.pandaq.pandaeye.modules.video.MovieResponse;
import com.pandaq.pandaeye.BasePresenter;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/19.
 * 视频评论Presenter
 */

public class VideoCommentPresenter extends BasePresenter implements VideoCommentContract.Presenter {
    private int currentPage = 0;

    private VideoCommentContract.View mCommentFrag;

    public VideoCommentPresenter(VideoCommentContract.View commentFrag) {
        mCommentFrag = commentFrag;
    }

    /**
     * 获取视频评论
     */
    private void loadVideoComment() {
        ApiManager.getInstence()
                .getMovieService()
                .getCommentList(mCommentFrag.getDataId(), String.valueOf(currentPage + 1))
                .map(new Function<MovieResponse<CommentBean>, CommentBean>() {
                    @Override
                    public CommentBean apply(MovieResponse<CommentBean> response) {
                        currentPage = response.getData().getPnum();
                        int totalPum = response.getData().getTotalPnum();
                        if (currentPage == totalPum) { //加载完所有的评论后
                            mCommentFrag.noMore();
                        }
                        return response.getData();
                    }
                })
                .flatMap(new Function<CommentBean, Observable<CommentBean.ListBean>>() {
                    @Override
                    public Observable<CommentBean.ListBean> apply(CommentBean commentBean) throws Exception {
                        return Observable.fromIterable(commentBean.getList());
                    }
                })
                .map(new Function<CommentBean.ListBean, BaseItem>() {
                    @Override
                    public BaseItem<CommentBean.ListBean> apply(CommentBean.ListBean listBean) throws Exception {
                        BaseItem<CommentBean.ListBean> base = new BaseItem<>();
                        base.setData(listBean);
                        return base;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<BaseItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<BaseItem> value) {
                        mCommentFrag.showComment((ArrayList<BaseItem>) value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCommentFrag.loadFail();
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
