package com.pandaq.pandaqlib.magicrecyclerView;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;

/**
 * Created by PandaQ on 2017/3/1.
 * recyclerView 分割线
 */

public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int halfSpace;
    private boolean mPaddingEdgeSide = true;
    private boolean mPaddingStart = true;
    private boolean mPaddingHeader = false;
    private boolean mPaddingFooter = false;
    private int headerCount = 0;
    private int footerCount = 0;

    public void setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
    }

    public void setFooterCount(int footerCount) {
        this.footerCount = footerCount;
    }

    public SpaceDecoration(int space) {
        this.halfSpace = space / 2;
    }

    public void setPaddingEdgeSide(boolean mPaddingEdgeSide) {
        this.mPaddingEdgeSide = mPaddingEdgeSide;
    }

    public void setPaddingStart(boolean mPaddingStart) {
        this.mPaddingStart = mPaddingStart;
    }

    public void setPaddingHeader(boolean mPaddingHeader) {
        this.mPaddingHeader = mPaddingHeader;
    }

    public void setPaddingFooter(boolean mPaddingFooter) {
        this.mPaddingFooter = mPaddingFooter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = 0;
        int orientation = 0;
        int spanIndex = 0;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        } else if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            spanCount = 1;
            spanIndex = 0;
        }
        //普通Item的尺寸
        if ((position >= headerCount && position < parent.getAdapter().getItemCount() - footerCount)) {
            int gravity;
            if (spanIndex == 0 && spanCount > 1) {
                gravity = Gravity.START;
            } else if (spanIndex == spanCount - 1 && spanCount > 1) {
                gravity = Gravity.END;
            } else if (spanCount == 1) {
                gravity = Gravity.FILL_HORIZONTAL;
            } else {
                gravity = Gravity.CENTER;
            }
            if (orientation == OrientationHelper.VERTICAL) {
                switch (gravity) {
                    case Gravity.START:
                        if (mPaddingEdgeSide) {
                            outRect.left = halfSpace * 2;
                        }
                        outRect.right = halfSpace;
                        break;
                    case Gravity.END:
                        outRect.left = halfSpace;
                        if (mPaddingEdgeSide) {
                            outRect.right = halfSpace * 2;
                        }
                        break;
                    case Gravity.FILL_HORIZONTAL:
                        if (mPaddingEdgeSide) {
                            outRect.left = halfSpace * 2;
                            outRect.right = halfSpace * 2;
                        }
                        break;
                    case Gravity.CENTER:
                        outRect.left = halfSpace;
                        outRect.right = halfSpace;
                        break;
                }
                if (headerCount > 0 && position - headerCount < spanCount && mPaddingStart) {
                    outRect.top = halfSpace * 2;
                }
                outRect.bottom = halfSpace * 2;
            } else {
                switch (gravity) {
                    case Gravity.START:
                        if (mPaddingEdgeSide) {
                            outRect.bottom = halfSpace * 2;
                        }
                        outRect.top = halfSpace;
                        break;
                    case Gravity.END:
                        outRect.bottom = halfSpace;
                        if (mPaddingEdgeSide) {
                            outRect.top = halfSpace * 2;
                        }
                        break;
                    case Gravity.FILL_HORIZONTAL:
                        if (mPaddingEdgeSide) {
                            outRect.left = halfSpace * 2;
                            outRect.right = halfSpace * 2;
                        }
                        break;
                    case Gravity.CENTER:
                        outRect.bottom = halfSpace;
                        outRect.top = halfSpace;
                        break;
                }
                if (position - headerCount < spanCount && mPaddingStart) {
                    outRect.left = halfSpace * 2;
                }
                outRect.right = halfSpace * 2;
            }
        } else if (position == parent.getAdapter().getItemCount() - footerCount) { // footer
            if (mPaddingFooter) { //并且需要padding Header&Footer
                if (orientation == OrientationHelper.VERTICAL) {
                    if (mPaddingEdgeSide) {
                        outRect.left = halfSpace * 2;
                        outRect.right = halfSpace * 2;
                    }
                    outRect.bottom = halfSpace * 2;
                } else {
                    if (mPaddingEdgeSide) {
                        outRect.top = halfSpace * 2;
                        outRect.bottom = halfSpace * 2;
                    }
                    outRect.left = halfSpace * 2;
                }
            } else {
                outRect.top = 0;
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = 0;
            }
        } else { //healder
            if (mPaddingHeader) { //并且需要padding Header&Footer
                if (orientation == OrientationHelper.VERTICAL) {
                    if (mPaddingEdgeSide) {
                        outRect.left = halfSpace * 2;
                        outRect.right = halfSpace * 2;
                    }
                    outRect.bottom = halfSpace * 2;
                } else {
                    if (mPaddingEdgeSide) {
                        outRect.top = halfSpace * 2;
                        outRect.bottom = halfSpace * 2;
                    }
                    outRect.left = halfSpace * 2;
                }
            } else {
                outRect.top = 0;
                outRect.bottom = 0;
                outRect.left = 0;
                outRect.right = 0;
            }
        }
    }


}
