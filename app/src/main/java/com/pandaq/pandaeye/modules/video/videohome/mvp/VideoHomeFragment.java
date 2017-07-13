package com.pandaq.pandaeye.modules.video.videohome.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.video.videotypelist.mvp.TypedVideosActivity;
import com.pandaq.pandaeye.modules.video.videohome.VideoTypesAdapter;
import com.pandaq.pandaeye.modules.video.videohome.VideoTopPagerAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.BaseFragment;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaqlib.loopbander.AutoScrollViewPager;
import com.pandaq.pandaqlib.loopbander.ViewGroupIndicator;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;
import com.pandaq.pandaqlib.magicrecyclerView.SpaceDecoration;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 冒泡圈Fragment
 */
public class VideoHomeFragment extends BaseFragment implements VideoHomeContract.View, SwipeRefreshLayout.OnRefreshListener, BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.mrv_video)
    MagicRecyclerView mMrvVideo;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @BindView(R.id.empty_msg)
    TextView mEmptyMsg;
    private AutoScrollViewPager scrollViewPager;
    private ViewGroupIndicator viewGroupIndicator;
    private VideoTopPagerAdapter mPagerAdapter;
    private VideoTypesAdapter mAdapter;
    private VideoHomeContract.Presenter mPresenter;
    private ArrayList<BaseItem> mBaseItems;
    private RetDataBean.ListBean footerItem;
    private Disposable mDisposable;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        bindPresenter();
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onHiddenChanged(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSrlRefresh.setRefreshing(false);
        unbindPresenter();
        onHiddenChanged(true);
    }

    private void initView() {
        mBaseItems = new ArrayList<>();
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMrvVideo.setLayoutManager(mStaggeredGridLayoutManager);
        mMrvVideo.setItemAnimator(new DefaultItemAnimator());
        mMrvVideo.getItemAnimator().setChangeDuration(0);
        SpaceDecoration itemDecoration = new SpaceDecoration(DensityUtil.dip2px(getContext(), 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingFooter(true);
        itemDecoration.setFooterCount(1);
        itemDecoration.setHeaderCount(1);
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
        mMrvVideo.addOnItemClickListener(this);
    }

    @Override
    public void refreshData() {
        footerItem = null;
        mPresenter.loadData();
    }

    @Override
    public void refreshSuccess(ArrayList<RetDataBean.ListBean> listBeen) {
        if (listBeen == null || listBeen.size() <= 0) {
            mEmptyMsg.setVisibility(View.VISIBLE);
            mMrvVideo.setVisibility(View.INVISIBLE);
            mSrlRefresh.requestFocus();
            return;
        } else {
            mEmptyMsg.setVisibility(View.GONE);
            mMrvVideo.setVisibility(View.VISIBLE);
        }
        RetDataBean.ListBean banner = listBeen.get(0);
        if (Constants.SHOW_TYPE_BANNER.equals(banner.getShowType())) { //判断是否为 banner
            //配置顶部故事
            if (mPagerAdapter == null) {
                mPagerAdapter = new VideoTopPagerAdapter(this, banner.getChildList());
                scrollViewPager.setAdapter(mPagerAdapter);
            } else {
                mPagerAdapter.resetData(banner.getChildList());
            }
            viewGroupIndicator.setParent(scrollViewPager);
            listBeen.remove(banner);
        }
        //配置底部列表故事s
        mBaseItems.clear();
        // 避免单数个 Item 时显示不好看，将最后一个移除单独显示
        if (listBeen.size() % 2 != 0) {
            footerItem = listBeen.get(listBeen.size() - 1);
            listBeen.remove(footerItem);
        }
        for (RetDataBean.ListBean listBean : listBeen) {
            if (!TextUtils.isEmpty(listBean.getMoreURL()) &&
                    !listBean.getTitle().equals("直播专区") && !listBean.getTitle().equals("专题")) {
                BaseItem<RetDataBean.ListBean> baseItem = new BaseItem<>();
                baseItem.setData(listBean);
                mBaseItems.add(baseItem);
            }
        }
        if (mAdapter == null) {
            mAdapter = new VideoTypesAdapter(this);
            mAdapter.setBaseDatas(mBaseItems);
            mMrvVideo.setAdapter(mAdapter);
            loadFooter();
        } else {
            if (listBeen.size() != 0) {
                mAdapter.setBaseDatas(mBaseItems);
                loadFooter();
            }
        }
    }

    @Override
    public void refreshFail(String errCode, String errMsg) {
        if (mAdapter == null) {
            mEmptyMsg.setVisibility(View.VISIBLE);
            mMrvVideo.setVisibility(View.INVISIBLE);
            mSrlRefresh.requestFocus();
        }
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
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden && mSrlRefresh.isRefreshing()) { // 隐藏的时候停止 SwipeRefreshLayout 转动
            mSrlRefresh.setRefreshing(false);
        }
        if (!hidden) {
            subscribeEvent();
        } else {
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    @Override
    public void onItemClick(int position, BaseItem data, View view) {
        RetDataBean.ListBean dataBean = (RetDataBean.ListBean) data.getData();
        Intent intent = new Intent(this.getActivity(), TypedVideosActivity.class);
        intent.putExtra(Constants.TYPED_MORE_TITLE, dataBean.getTitle());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        destoryPresenter();
    }

    @Override
    public void bindPresenter() {
        if (mPresenter == null) {
            mPresenter = new VideoHomeFragPresenter();
        }
        mPresenter.bindView(this);
    }

    @Override
    public void unbindPresenter() {
        mPresenter.unbindView();
    }

    @Override
    public void destoryPresenter() {
        mPresenter.onDestory();
    }

    private void subscribeEvent() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        RxBus.getDefault()
                .toObservableWithCode(RxConstants.BACK_PRESSED_CODE, String.class)
                .subscribeWith(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String value) {
                        if (value.equals(RxConstants.BACK_PRESSED_DATA) && mMrvVideo != null) {
                            //滚动到顶部
                            mStaggeredGridLayoutManager.smoothScrollToPosition(mMrvVideo, null, 0);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        subscribeEvent();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadFooter() {
        if (footerItem == null) {
            return;
        }
        CardView cardView = (CardView) getActivity().getLayoutInflater().inflate(R.layout.video_type_item, null);
        // 此处直接 cardView.getLayoutParams() 是会返回 null的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dip2px(getContext(), 208f));
        cardView.setLayoutParams(params);
        mMrvVideo.setFooterView(cardView);
        ImageView image = (ImageView) cardView.findViewById(R.id.iv_video_type);
        TextView textView = (TextView) cardView.findViewById(R.id.tv_video_type);
        Picasso.with(getContext())
                .load(footerItem.getChildList().get(0).getPic())
                .into(image);
        textView.setText(footerItem.getTitle());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TypedVideosActivity.class);
                intent.putExtra(Constants.TYPED_MORE_TITLE, footerItem.getTitle());
                startActivity(intent);
            }
        });
    }
}
