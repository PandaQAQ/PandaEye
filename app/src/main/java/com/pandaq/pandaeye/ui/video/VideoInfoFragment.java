package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaqlib.FoldableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频简介 fragment
 */

public class VideoInfoFragment extends BaseFragment {

    @BindView(R.id.ftv_content_text)
    FoldableTextView mFtvContentText;
    @BindView(R.id.rv_recommend)
    RecyclerView mRvRecommend;
    @BindView(R.id.sv_parent)
    ScrollView mSvParent;
    @BindView(R.id.tv_empty_msg)
    TextView mTvEmptyMsg;
    private Subscription subscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_description_fragment, container, false);
        ButterKnife.bind(this, view);
        initRxbus();
        return view;
    }

    private void initRxbus() {
        subscription = RxBus
                .getDefault()
                .toObservable(MovieInfo.class)
                .subscribe(new Action1<MovieInfo>() {
                    @Override
                    public void call(MovieInfo movieInfo) {
                        mFtvContentText.setText(movieInfo.getDescription());
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
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }
}
