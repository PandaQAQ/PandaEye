package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.model.video.CommentBean;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.rxbus.Action;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.ui.ImplView.IVedioInfoActivity;
import com.pandaq.pandaeye.ui.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by PandaQ on 2017/3/2.
 * 评论 fragment
 */

public class VideoCommentFrag extends BaseFragment {

    private Subscription subscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_comment_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initRxbus() {
        subscription = RxBus
                .getDefault()
                .toObservable(Action.class)
                .subscribe(new Action1<Action<ArrayList<CommentBean.ListBean>>>() {
                    @Override
                    public void call(Action<ArrayList<CommentBean.ListBean>> action) {
                        ArrayList<CommentBean.ListBean> movieInfo = action.getActionData();
                        showInfo(movieInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // 获取信息失败
                        mTvEmptyMsg.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onPause() {
        subscription.unsubscribe();
        super.onPause();
    }
}
