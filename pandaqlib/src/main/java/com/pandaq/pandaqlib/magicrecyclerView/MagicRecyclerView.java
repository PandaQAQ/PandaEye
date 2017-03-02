package com.pandaq.pandaqlib.magicrecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandaq.pandaqlib.R;

/**
 * Created by PandaQ on 2016/9/18.
 * email : 767807368@qq.com
 * 自带 header 和 footer 的 RecyclerView
 */
public class MagicRecyclerView extends RecyclerView {

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private static final int NULL_VALUE = 0;
    /**
     * 布局类型
     */
    private LAYOUT_MANAGER_TYPE layoutManagerType;
    private int[] positions;
    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition = -1;
    private int firstVisibleItemPosition = -1;

    private int childCount = -1;
    private View headerView;
    private View emptyView;
    private View footerView;
    //当前的头部视图底部外边距
    private int scrolledMargin = 0; //滑动结束时的margin
    private int distance = 0; // 每次滑动的距离
    private float multiplier = 1;//视差因子，默认值为1
    private BaseRecyclerAdapter mRecyclerAdapter;
    private BaseRecyclerAdapter.OnItemClickListener mItemClickListener;

    public MagicRecyclerView(Context context) {
        super(context);
    }

    public MagicRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MagicRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MagicRecyclerView);
        int header_layout = ta.getResourceId(R.styleable.MagicRecyclerView_header_layout, NULL_VALUE);
        int empty_layout = ta.getResourceId(R.styleable.MagicRecyclerView_emptyView, NULL_VALUE);
        int footer_layout = ta.getResourceId(R.styleable.MagicRecyclerView_footer_layout, NULL_VALUE);
        multiplier = ta.getFloat(R.styleable.MagicRecyclerView_parallaxMultiplier, multiplier);
        //取值范围为0-1
        if (multiplier > 1) {
            multiplier = 1;
        }
        if (header_layout != NULL_VALUE) {
            headerView = LayoutInflater.from(context).inflate(header_layout, null, true);
        }
        if (empty_layout != NULL_VALUE) {
            emptyView = inflate(context, empty_layout, null);
        }
        if (footer_layout != NULL_VALUE) {
            footerView = LayoutInflater.from(context).inflate(footer_layout, null, true);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(lp);
        }
        ta.recycle();
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }
        switch (layoutManagerType) {
            case LINEAR:
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (positions == null) {
                    positions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(positions);
                //获取到最后一页的最后一个控件的position
                lastVisibleItemPosition = findMax(positions);
                staggeredGridLayoutManager.findFirstVisibleItemPositions(positions);
                //获取到第一页的第一个可见控件
                firstVisibleItemPosition = findMin(positions);
                break;
        }
        //当前显示的子item的个数
        childCount = layoutManager.getChildCount();
        //当添加了头部视图的时候给头部视图添加滚动视差效果
        if (headerView != null) {
            if (firstVisibleItemPosition == 0) {
                if (distance <= headerView.getHeight()) {
                    distance = dy;
                } else {
                    distance = headerView.getHeight();
                }
                LayoutParams layoutParams = (LayoutParams) headerView.getLayoutParams();
                //重新赋值给底部边距
                scrolledMargin = -distance + scrolledMargin;
                if (scrolledMargin > 0) {
                    scrolledMargin = 0;
                }
                layoutParams.setMargins(0, 0, 0, (int) (multiplier * scrolledMargin));
                headerView.setLayoutParams(layoutParams);
            }
        }
    }

    private int findMax(int[] positions) {
        int max = positions[0];
        for (int value : positions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] positions) {
        int min = positions[0];
        for (int value : positions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        if (mRecyclerAdapter == null) {
            mRecyclerAdapter = adapter;
        }
        super.setAdapter(mRecyclerAdapter);
        if (headerView != null) {
            mRecyclerAdapter.setHeaderView(headerView);
        }
        if (footerView != null) {
            mRecyclerAdapter.setFooterView(footerView);
        }
        if (mItemClickListener != null) {
            mRecyclerAdapter.setOnItemClickListener(mItemClickListener);
        }
    }

    public boolean refreshAble() {
        //仅当滑到顶部且第一个item完全可见的状态才能刷新这个recyclerView
        return firstVisibleItemPosition == 0 && this.getChildAt(0).getTop() == 0;
    }

    public boolean loadAble() {
        return mRecyclerAdapter != null && firstVisibleItemPosition + childCount >= mRecyclerAdapter.getItemCount();
    }

    public View getHeaderView() {
        return headerView;
    }

    public View getFooterView() {
        return footerView;
    }

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener li) {
        mItemClickListener = li;
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.setOnItemClickListener(mItemClickListener);
        }
    }
}
