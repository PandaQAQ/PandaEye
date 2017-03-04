package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.VideoListAdapter;
import com.pandaq.pandaeye.adapters.VideoTopPagerAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.entity.video.RetDataBean;
import com.pandaq.pandaeye.entity.zhihu.ZhiHuStory;
import com.pandaq.pandaeye.presenter.video.VideoFragPresenter;
import com.pandaq.pandaeye.ui.ImplView.IVideoListFrag;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaqlib.loopbander.AutoScrollViewPager;
import com.pandaq.pandaqlib.loopbander.ViewGroupIndicator;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;
import com.pandaq.pandaqlib.magicrecyclerView.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 冒泡圈Fragment
 */
public class VideoCircleFragment extends BaseFragment implements IVideoListFrag, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.mrv_video)
    MagicRecyclerView mMrvVideo;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private AutoScrollViewPager scrollViewPager;
    private ViewGroupIndicator viewGroupIndicator;
    private VideoTopPagerAdapter mPagerAdapter;
    private VideoListAdapter mAdapter;
    private VideoFragPresenter mPresenter = new VideoFragPresenter(this);
    private ArrayList<BaseItem> mBaseItems;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mBaseItems = new ArrayList<>();
        mMrvVideo.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mMrvVideo.setItemAnimator(new DefaultItemAnimator());
        mMrvVideo.getItemAnimator().setChangeDuration(0);
        SpaceDecoration itemDecoration = new SpaceDecoration(DensityUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mMrvVideo.addItemDecoration(itemDecoration);
        FrameLayout headerView = (FrameLayout) mMrvVideo.getHeaderView();
        scrollViewPager = (AutoScrollViewPager) headerView.findViewById(R.id.scroll_pager);
        viewGroupIndicator = (ViewGroupIndicator) headerView.findViewById(R.id.scroll_pager_indicator);
        mSrlRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_FFFFFF));
        mSrlRefresh.setOnRefreshListener(this);
        mSrlRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshData();
        mPresenter.loadCache();
    }

    @Override
    public void refreshData() {
        mPresenter.loadData();
    }

    @Override
    public void refreshSuccess(ArrayList<RetDataBean.ListBean> listBeen) {
        for (RetDataBean.ListBean listBean : listBeen) { //事实上只会执行一次，Banner 为第一个 item
            if (Constants.SHOW_TYPE_BANNER.equals(listBean.getShowType())) { //判断是否为 banner
                //配置顶部故事
                if (mPagerAdapter == null) {
                    mPagerAdapter = new VideoTopPagerAdapter(this, listBean.getChildList());
                    scrollViewPager.setAdapter(mPagerAdapter);
                } else {
                    mPagerAdapter.resetData(listBean.getChildList());
                    mPagerAdapter.notifyDataSetChanged();
                }
                viewGroupIndicator.setParent(scrollViewPager);
                listBeen.remove(listBean);
                break;
            }
        }
        //配置底部列表故事
        for (RetDataBean.ListBean listBean : listBeen) {
            BaseItem<RetDataBean.ListBean> baseItem = new BaseItem<>();
            baseItem.setData(listBean);
            mBaseItems.add(baseItem);
        }
        if (mAdapter == null) {
            mAdapter = new VideoListAdapter(this);
            mAdapter.setBaseDatas(mBaseItems);
            mMrvVideo.setAdapter(mAdapter);
        } else {
            if (listBeen.size() != 0) {
                mAdapter.setBaseDatas(mBaseItems);
            }
        }
    }

    @Override
    public void refreshFail(String errCode, String errMsg) {

    }

    @Override
    public void showProgressBar() {
        if (!mSrlRefresh.isRefreshing())
            mSrlRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        mSrlRefresh.setRefreshing(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onRefresh() {
        refreshData();
    }
}
