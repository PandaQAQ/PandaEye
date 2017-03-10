package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.VideoInfoAdapter;
import com.pandaq.pandaeye.model.video.MovieInfo;
import com.pandaq.pandaeye.rxbus.Action;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaqlib.FoldableTextView;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;
import com.pandaq.pandaqlib.magicrecyclerView.SpaceDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by PandaQ on 2017/3/2.
 * 视频简介 fragment
 */

public class VideoInfoFragment extends BaseFragment {

    @BindView(R.id.tv_empty_msg)
    TextView mTvEmptyMsg;
    @BindView(R.id.mrv_recommend)
    MagicRecyclerView mMrvRecommend;
    private FoldableTextView mFoldableTextView;
    private Subscription subscription;
    private ArrayList<BaseItem> mBaseItems;
    private VideoInfoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_description_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        initRxbus();
        return view;
    }

    private void initView() {
        mBaseItems = new ArrayList<>();
        mFoldableTextView = (FoldableTextView) mMrvRecommend.getHeaderView().findViewById(R.id.ftv_content_text);
        mMrvRecommend.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mMrvRecommend.setItemAnimator(new DefaultItemAnimator());
        mMrvRecommend.getItemAnimator().setChangeDuration(0);
        SpaceDecoration itemDecoration = new SpaceDecoration(DensityUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mMrvRecommend.addItemDecoration(itemDecoration);
    }

    private void initRxbus() {
        subscription = RxBus
                .getDefault()
                .toObservable(Action.class)
                .subscribe(new Action1<Action>() {
                    @Override
                    public void call(Action action) {
                        MovieInfo movieInfo = (MovieInfo) action.getActionData();
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

    private void showInfo(MovieInfo movieInfo) {
        String sb = "上映时间：" + movieInfo.getAirTime() + "\n" +
                "导演：" + movieInfo.getDirector() + "\n" +
                "主演：" + movieInfo.getActors() + "\n" +
                "剧情简介：" + movieInfo.getDescription();
        mFoldableTextView.setText(sb);
        mBaseItems.clear();
        for (MovieInfo.ListBean.ChildListBean listBean : movieInfo.getList().get(1).getChildList()) {
            BaseItem<MovieInfo.ListBean.ChildListBean> baseItem = new BaseItem<>();
            baseItem.setData(listBean);
            mBaseItems.add(baseItem);
        }
        if (mAdapter == null) {
            mAdapter = new VideoInfoAdapter(this);
            mAdapter.setBaseDatas(mBaseItems);
            mMrvRecommend.setAdapter(mAdapter);
        }
    }

    @Override
    public void onPause() { //界面不可见时就取消绑定
        subscription.unsubscribe();
        super.onPause();
    }
}
