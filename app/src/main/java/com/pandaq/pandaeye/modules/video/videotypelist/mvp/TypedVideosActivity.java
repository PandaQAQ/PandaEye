package com.pandaq.pandaeye.modules.video.videotypelist.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.video.videodetail.VideoInfoActivity;
import com.pandaq.pandaeye.modules.video.videotypelist.TypedVideosAdapter;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.activities.SwipeBackActivity;
import com.pandaq.pandaqlib.magicrecyclerView.BaseItem;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/15.
 * 分类视频列表界面
 */

public class TypedVideosActivity extends SwipeBackActivity implements VideoTypedContract.View, BaseRecyclerAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.mrv_video_list)
    MagicRecyclerView mMrvVideoList;
    @BindView(R.id.tv_empty_msg)
    TextView mTvEmptyMsg;
    private String mMoreTitle;
    private TypedVideosPresenter mPresenter = new TypedVideosPresenter(this);
    private TypedVideosAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typed_videos);
        ButterKnife.bind(this);
        mMoreTitle = getIntent().getStringExtra(Constants.TYPED_MORE_TITLE);
        mToolbar.setTitle(mMoreTitle);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMrvVideoList.setLayoutManager(new LinearLayoutManager(this));
        mMrvVideoList.setItemAnimator(new DefaultItemAnimator());
        mMrvVideoList.getItemAnimator().setChangeDuration(0);
        mMrvVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!loading && mMrvVideoList.loadAble()) {
                    loadMore();
                }
            }
        });
        loadMore();
    }

    private boolean loading = false;

    @Override
    public void loadMore() {
        loading = true;
        mPresenter.loadData(mMoreTitle);
    }

    @Override
    public void noMoreVideo() {
        TextView ftText = (TextView) mMrvVideoList.getFooterView().findViewById(R.id.tv_footer_text);
        ProgressBar ftPB = (ProgressBar) mMrvVideoList.getFooterView().findViewById(R.id.cp_progressbar);
        ftPB.setVisibility(View.GONE);
        ftText.setText(getString(R.string.nomore_data));
    }

    @Override
    public void loadMoreSuccess(ArrayList<BaseItem> list) {
        loading = false;
        mTvEmptyMsg.setVisibility(View.GONE);
        if (mAdapter == null) {
            mAdapter = new TypedVideosAdapter(this);
            mAdapter.setBaseDatas(list);
            mMrvVideoList.setAdapter(mAdapter);
            mMrvVideoList.addOnItemClickListener(this);
        } else {
            mAdapter.addBaseDatas(list);
        }
    }

    @Override
    public void loadFail(String errCode, String errMsg) {
        loading = false;
        mTvEmptyMsg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        mPresenter.dispose();
        super.onPause();
    }

    @Override
    public void onItemClick(int position, BaseItem data, View view) {
        TypedVideos.ListBean listBean = (TypedVideos.ListBean) data.getData();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, VideoInfoActivity.class);
        bundle.putString(Constants.BUNDLE_KEY_ID, listBean.getDataId());
        bundle.putString(Constants.BUNDLE_KEY_TITLE, listBean.getTitle());
        bundle.putString(Constants.BUNDLE_KEY_IMG_URL, listBean.getPic());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
