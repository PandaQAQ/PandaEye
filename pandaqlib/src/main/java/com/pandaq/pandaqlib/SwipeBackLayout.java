package com.pandaq.pandaqlib;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by PandaQ on 2017/3/21.
 * 实现侧滑删除Activity的 Layout
 */
public class SwipeBackLayout extends FrameLayout {
    private static final String TAG = SwipeBackLayout.class.getSimpleName();
    private View mContentView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isSilding;
    private boolean isFinish;
    private boolean canSwipe;
    private Drawable mShadowDrawable;
    private Activity mActivity;
    private List<ViewPager> mViewPagers = new LinkedList<>();

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取触发移动时间的最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mShadowDrawable = getResources().getDrawable(R.drawable.shadow_left);
    }


    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();
        //获取到 DecorView 对象
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        Log.i("decorChildCount", decor.getChildCount() + "");
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        Log.i("decorChild", decorChild.toString());
        decorChild.setBackgroundResource(background);
        //decorView 中将子布局移除
        decor.removeView(decorChild);
        //SwipeBackLayout 添加从decorView中移除布局
        addView(decorChild);
        //将ContentView设置为decorChild的父布局即添加进来的SwipeBackLayout
        setContentView();
        //将SwipeBackLayout添加进DecorView
        decor.addView(this);
    }

    private void setContentView() {
        // 后续对 mContentView 进行滑动处理就相当于将显示的 View 放在 SwipeBackLayout 这个盒子里面
        mContentView = this;
        Log.i("mContentView", mContentView.toString());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();
            getAlLViewPager(mViewPagers, this);
            Log.i(TAG, "ViewPager size = " + mViewPagers.size());
        }
    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //处理ViewPager冲突问题
        ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
        //当无触摸ViewPager或者该ViewPager未滑动到最左则不对滑动时间进行拦截
        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                canSwipe = downX <= viewWidth / 2;
                if (!canSwipe) {
                    return super.onInterceptTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!canSwipe) {
                    return super.onInterceptTouchEvent(ev);
                }
                int moveX = (int) ev.getRawX();
                // 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
                if (moveX - downX > mTouchSlop
                        && Math.abs((int) ev.getRawY() - downY) < mTouchSlop) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!canSwipe) {
                    return super.onInterceptTouchEvent(event);
                }
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (moveX - downX > mTouchSlop
                        && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                    isSilding = true;
                }
                if (moveX - downX >= 0 && isSilding) {
                    //deltaX 为单次移动的距离向右滑为负数
                    // TODO: 2017/6/22 实现 y 方向的移动，即向右任意方向滑出界面
                    mContentView.scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!canSwipe) {
                    return super.onInterceptTouchEvent(event);
                }
                isSilding = false;
                if (mContentView.getScrollX() <= -viewWidth / 4) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }
        return true;
    }

    /**
     * 获取SwipeBackLayout里面的ViewPager的集合
     */
    private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager(mViewPagers, (ViewGroup) child);
            }
        }
    }


    /**
     * 返回我们touch的ViewPager
     *
     * @param mViewPagers 添加的ViewPagers
     * @param ev          事件
     * @return 正在触摸的 ViewPager
     */
    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers) {
            v.getHitRect(mRect);
            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShadowDrawable != null && mContentView != null) {
            int left = mContentView.getLeft()
                    - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = mContentView.getTop();
            int bottom = mContentView.getBottom();
            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
        }

    }


    /**
     * 滚动出界面
     */
    private void scrollRight() {
        final int delta = (viewWidth + mContentView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mSwipeListener != null) {
            double scrollx = Math.abs(mContentView.getScrollX());
            double offset = scrollx / viewWidth;
            if (offset > 0.9) {
                offset = 1d;
            }
            mSwipeListener.swipeValue(offset);
        }
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }

    public void addViewPager(ViewPager pager) {
        mViewPagers.add(pager);
    }

    private SwipeListener mSwipeListener;

    public void addSwipeListener(SwipeListener listener) {
        mSwipeListener = listener;
    }

    public interface SwipeListener {
        void swipeValue(double value);
    }
}
