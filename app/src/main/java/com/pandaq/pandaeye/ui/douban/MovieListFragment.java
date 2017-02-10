package com.pandaq.pandaeye.ui.douban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.MovieListAdapter;
import com.pandaq.pandaeye.entity.DouBan.MovieSubject;
import com.pandaq.pandaeye.presenter.douban.DouBanMoviePresenter;
import com.pandaq.pandaeye.ui.ImplView.IMovieListFrag;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter;
import com.pandaq.pandaqlib.magicrecyclerView.MagicRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 豆瓣top250电影列表Fragment
 */
public class MovieListFragment extends BaseFragment implements IMovieListFrag {
    @BindView(R.id.movie_list)
    MagicRecyclerView mMovieList;
    private ArrayList<MovieSubject> mMovieSubjects;
    private MovieListAdapter mMovieListAdapter;
    private DouBanMoviePresenter mPresenter = new DouBanMoviePresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movielist_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMovieSubjects = new ArrayList<>();
        mMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mMovieList.loadAble()) {
                    loadMoreData();
                }
            }
        });
        mMovieList.setItemAnimator(new DefaultItemAnimator());
        mMovieList.setLayoutManager(layoutManager);
        refreshData();
        mMovieList.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                System.out.println(mMovieSubjects.get(position).getTitle());
            }
        });
    }

    @Override
    public void showProgressBar() {
        //显示加载进度条
    }

    @Override
    public void hideProgressBar() {
        //隐藏加载进度条
    }

    @Override
    public void showEmptyMessage() {
        //显示无信息时的界面View
    }

    @Override
    public void hideEmptyMessage() {
        //显示信息时隐藏掉无信息时的界面View
    }

    @Override
    public void loadMoreData() {
        //加载要显示的数据
        mPresenter.loadMoreData();
    }

    @Override
    public void refreshData() {
        //刷新数据
        mPresenter.refreshData();
    }

    @Override
    public void loadSuccessed(ArrayList<MovieSubject> movieSubjects) {
        mMovieListAdapter.addDatas(movieSubjects);
    }

    @Override
    public void loadFail(String errMsg) {
        //SnackBar提示错误信息
    }

    @Override
    public void refreshSucceed(ArrayList<MovieSubject> movieSubjects) {
        mMovieSubjects.clear();
        mMovieSubjects = movieSubjects;
        //如果是刚进入时刷新则新建一个Adapter，否则只是更新数据源
        if (mMovieListAdapter == null) {
            mMovieListAdapter = new MovieListAdapter(this);
            mMovieListAdapter.setDatas(movieSubjects);
            mMovieList.setAdapter(mMovieListAdapter);
        } else {
            mMovieListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshFail(String errMsg) {
        //SnackBar提示错误信息

    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubcription();
    }
}
