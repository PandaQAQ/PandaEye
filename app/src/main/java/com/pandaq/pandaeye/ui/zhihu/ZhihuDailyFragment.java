package com.pandaq.pandaeye.ui.zhihu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.ZhihuDailyAdapter;
import com.pandaq.pandaeye.adapters.ZhihuTopPagerAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.entity.ZhiHu.ZhiHuDaily;
import com.pandaq.pandaeye.entity.ZhiHu.ZhiHuStory;
import com.pandaq.pandaeye.presenter.zhihu.ZhiHuPresenter;
import com.pandaq.pandaeye.ui.ImplView.IZhiHuDailyFrag;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaqlib.loopbander.AutoScrollViewPager;
import com.pandaq.pandaqlib.loopbander.ViewGroupIndicator;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 知乎日报列表Fragment
 */
public class ZhihuDailyFragment extends BaseFragment implements IZhiHuDailyFrag, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.zhihudaily_list)
    MagicRecyclerView mZhihudailyList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    private ZhiHuPresenter mPresenter = new ZhiHuPresenter(this);
    private ZhihuDailyAdapter mZhihuDailyAdapter;
    private ArrayList<ZhiHuStory> mZhiHuStories;
    private boolean loadOrRefreshAble = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihulist_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubcription();
    }

    private void initView() {
        mZhiHuStories = new ArrayList<>();
        mZhihudailyList.setItemAnimator(new DefaultItemAnimator());
        mZhihudailyList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mZhihudailyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mZhihudailyList.refreshAble()) {
                    mRefresh.setEnabled(true);
                }
                if (mZhihudailyList.loadAble()) {
                    loadMoreData();
                }
            }
        });
        mRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white_FFFFFF));
        mRefresh.setOnRefreshListener(this);
        mRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshData();
        mPresenter.loadCache();
        mZhihudailyList.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, View view) {
                //跳转到其他界面
                Bundle bundle = new Bundle();
                Intent intent = new Intent(ZhihuDailyFragment.this.getActivity(), ZhihuStoryInfoActivity.class);
                bundle.putString(Constants.BUNDLE_KEY_TITLE, mZhiHuStories.get(position).getTitle());
                bundle.putInt(Constants.BUNDLE_KEY_ID, mZhiHuStories.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showRefreshBar() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideRefreshBar() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showLoadBar() {
    }

    @Override
    public void hideLoadBar() {

    }

    @Override
    public void refreshData() {
        if (loadOrRefreshAble) {
            mPresenter.refreshZhihuDaily();
            loadOrRefreshAble = false;
        }
    }

    @Override
    public void refreshSuccessed(ZhiHuDaily stories) {
        mZhiHuStories.clear();
        mZhiHuStories = stories.getStories();
        RelativeLayout headerView = (RelativeLayout) mZhihudailyList.getHeaderView();
        AutoScrollViewPager scrollViewPager = (AutoScrollViewPager) headerView.findViewById(R.id.scroll_pager);
        ViewGroupIndicator viewGroupIndicator = (ViewGroupIndicator) headerView.findViewById(R.id.scroll_pager_indicator);
//        //配置顶部故事
        scrollViewPager.setAdapter(new ZhihuTopPagerAdapter(this, stories.getTop_stories()));
        viewGroupIndicator.setParent(scrollViewPager);
        //配置底部列表故事
        if (mZhihuDailyAdapter == null) {
            mZhihuDailyAdapter = new ZhihuDailyAdapter(this);
            mZhihuDailyAdapter.setDatas(mZhiHuStories);
            mZhihudailyList.setAdapter(mZhihuDailyAdapter);
        } else {
            if (mZhiHuStories.size() != 0) {
                mZhihuDailyAdapter.setDatas(mZhiHuStories);
            }
        }
        loadOrRefreshAble = true;
    }

    @Override
    public void refreshFail(String errMsg) {
        loadOrRefreshAble = true;
    }

    @Override
    public void loadMoreData() {
        if (loadOrRefreshAble) {
            mPresenter.loadMoreData();
            loadOrRefreshAble = false;
        }
    }

    @Override
    public void loadSuccessed(ArrayList<ZhiHuStory> stories) {
        mZhiHuStories.addAll(stories);
        mZhihuDailyAdapter.addDatas(stories);
        loadOrRefreshAble = true;
    }

    @Override
    public void loadFail(String errMsg) {
        loadOrRefreshAble = true;
    }

    @Override
    public void onRefresh() {
        if (loadOrRefreshAble) {
            refreshData();
            loadOrRefreshAble = false;
        }
    }

}
