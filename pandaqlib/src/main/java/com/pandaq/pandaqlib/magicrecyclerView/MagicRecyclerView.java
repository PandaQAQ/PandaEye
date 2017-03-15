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

import java.util.ArrayList;

import static com.pandaq.pandaqlib.magicrecyclerView.BaseRecyclerAdapter.RecyclerItemType.TYPE_TAGS;

/**
 * Created by PandaQ on 2016/9/18.
 * email : 767807368@qq.com
 */
public class MagicRecyclerView extends RecyclerView {
    private static final int NULL_VALUE = 0;
    private BaseRecyclerAdapter.OnItemClickListener mItemClickListener;

    private enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * 布局类型
     */
    private LAYOUT_MANAGER_TYPE layoutManagerType;
    private int[] positions;
    private int firstVisibleItemPosition = -1;
    private View headerView;
    private View footerView;
    private int childCount = -1;
    //当前的头部视图底部外边距
    private int scrolledMargin = 0; //滑动结束时的margin
    private int distance = 0; // 每次滑动的距离
    private float multiplier = 1;//视差因子，默认值为1
    private BaseRecyclerAdapter mRecyclerAdapter;
    private OnTagChangeListener mOnTagChangeListener;
    private ArrayList<String> tags; //用于存放显示过的 tag
    private String currentTag;//当前正在显示的 tag
    private boolean tagChanged;

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
        int header_layout = ta.getResourceId(R.styleable.MagicRecyclerView_header_layout, 0);
        int footer_layout = ta.getResourceId(R.styleable.MagicRecyclerView_footer_layout, 0);
        multiplier = ta.getFloat(R.styleable.MagicRecyclerView_parallaxMultiplier, multiplier);
        //取值范围为0-1
        if (multiplier > 1) {
            multiplier = 1;
        }
        if (header_layout != NULL_VALUE) {
            headerView = LayoutInflater.from(context).inflate(header_layout, null, true);
        }
        if (footer_layout != NULL_VALUE) {
            footerView = LayoutInflater.from(context).inflate(footer_layout, null, true);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(lp);
        }
        ta.recycle();
        tags = new ArrayList<>();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
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
                break;
            case GRID:
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (positions == null) {
                    positions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(positions);
                staggeredGridLayoutManager.findFirstVisibleItemPositions(positions);
                //获取到第一页的第一个可见控件
                firstVisibleItemPosition = findMin(positions);
                break;
        }
        //当前显示的子item的个数
        childCount = layoutManager.getChildCount();
        //当添加了头部视图且当前头部视图可见的时候给头部视图添加滚动视差效果
        if (headerView != null && firstVisibleItemPosition == 0) {
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
        int firstItemType = getAdapter().getItemViewType(firstVisibleItemPosition);
        // 如果是未添加过的 Tag 则加入到 ArrayList 中
        if (firstItemType == TYPE_TAGS.getiNum() && !tags.contains(mRecyclerAdapter.getTag(firstVisibleItemPosition))) {
            tags.add(mRecyclerAdapter.getTag(firstVisibleItemPosition));
        }
        if (mOnTagChangeListener != null) {
            if (dy > 0) { //向上滑动，滑到Tag时触发
                if (firstItemType == TYPE_TAGS.getiNum()) { //上滑到 Item 变 Tag 时触发
                    currentTag = mRecyclerAdapter.getTag(firstVisibleItemPosition);
                    mOnTagChangeListener.onChange(currentTag);
                }
            } else {
                int lastItemType = getAdapter().getItemViewType(firstVisibleItemPosition + 1);
                if (firstItemType != TYPE_TAGS.getiNum() && lastItemType == TYPE_TAGS.getiNum()) { //下滑到 Tag 上边沿时触发
                    if (tags.size() == 0) {
                        return;
                    }
                    if (!tagChanged) {
                        int lastTagPosition = tags.indexOf(currentTag) >= 1 ? tags.indexOf(currentTag) - 1 : 0;
                        String tag = tags.get(lastTagPosition);
                        mOnTagChangeListener.onChange(tag);
                        currentTag = tag;
                        tagChanged = true;
                    }
                } else {
                    tagChanged = false;
                }
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
        if (mItemClickListener != null) {
            mRecyclerAdapter.setOnItemClickListener(mItemClickListener);
        }
        if (headerView != null) {
            mRecyclerAdapter.setHeaderView(headerView);
        }
        if (footerView != null) {
            mRecyclerAdapter.setFooterView(footerView);
        }
    }

    public boolean refreshAble() {
        //仅当滑到顶部且第一个item完全可见的状态才能刷新这个recyclerView
        return firstVisibleItemPosition == 0 && this.getChildAt(0).getTop() == 0;
    }

    public boolean loadAble() {
        return mRecyclerAdapter != null && firstVisibleItemPosition + childCount >= mRecyclerAdapter.getItemCount();
    }

    public boolean tagGone() {
        return firstVisibleItemPosition == 0;
    }

    public View getHeaderView() {
        return headerView;
    }

    public View getFooterView() {
        return footerView;
    }

    public void addOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        if (mRecyclerAdapter != null) {
            this.mItemClickListener = null;
            mRecyclerAdapter.setOnItemClickListener(mItemClickListener);
        }
    }

    public void addOnTagChangeListener(OnTagChangeListener listener) {
        this.mOnTagChangeListener = listener;
    }

    public interface OnTagChangeListener {
        void onChange(String newTag);
    }
}
