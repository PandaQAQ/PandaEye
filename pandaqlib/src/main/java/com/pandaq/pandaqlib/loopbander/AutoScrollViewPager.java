package com.pandaq.pandaqlib.loopbander;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 */
public class AutoScrollViewPager extends ViewPager implements IndicatorParentImbl {

    private IndicatorListener mIndicatorListener;

    private boolean isRunning = false;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (isRunning && msg.what == 0) {
                //让viewPager 滑动到下一页
                AutoScrollViewPager.this.setCurrentItem(getCurrentItem() + 1, true);
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }

        ;
    };

    @Override
    public void setIndicatorListener(IndicatorListener mIndicatorListener) {
        this.mIndicatorListener = mIndicatorListener;
    }

    @Override
    public int getIndicatorCount() {
        return getCount();
    }

    @Override
    public void startAutoScroll() {
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 5000);
    }

    @Override
    public void stopAutoScroll() {
        isRunning = false;
        handler.removeMessages(0);
    }

    @Override
    public void onReDraw(int index) {
        setCurrentItem(index);
        //   mIndicatorListener.setNext(getCurrentItem());
    }


    public interface OnPageClickListener {
        void onPageClick(AutoScrollViewPager pager, int position);
    }

    private PagerAdapter wrappedPagerAdapter;
    private PagerAdapter wrapperPagerAdapter;

    private InnerOnPageChangeListener listener;
    private OnPageClickListener onPageClickListener;

    public AutoScrollViewPager(Context context) {
        super(context);
        init();
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        listener = new InnerOnPageChangeListener();
        super.setOnPageChangeListener(listener);
        // startAutoScroll();
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        this.listener.setOnPageChangeListener(listener);
    }

    public OnPageClickListener getOnPageClickListener() {
        return onPageClickListener;
    }

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        wrappedPagerAdapter = adapter;
        wrapperPagerAdapter = (wrappedPagerAdapter == null) ? null : new AutoScrollPagerAdapter(adapter);
        super.setAdapter(wrapperPagerAdapter);  //为viewPager设置的是包装的adapter
        if (adapter != null && adapter.getCount() != 0) {
            adapter.notifyDataSetChanged();
            post(new Runnable() {
                @Override
                public void run() {
                    setCurrentItem(0, false);
                }
            });
        }
        startAutoScroll();
    }

    @Override
    public PagerAdapter getAdapter() {
        return wrappedPagerAdapter;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item + 1);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {

        super.setCurrentItem(item + 1, smoothScroll);
        //   mIndicatorListener.setNext(getCurrentItem());
    }

    @Override
    public int getCurrentItem() {
        int curr = super.getCurrentItem();
        return getRealPosition(curr);

    }

    //根据虚拟获取真实位置
    private int getRealPosition(int curr) {
        if (wrappedPagerAdapter != null && wrappedPagerAdapter.getCount() > 1) {
            if (curr == 0) {
                curr = wrappedPagerAdapter.getCount() - 1;
            } else if (curr == wrapperPagerAdapter.getCount() - 1) {
                curr = 0;
            } else {
                curr = curr - 1;
            }
        }
        return curr;

    }

    private int getCurrentItemOfWrapper() {
        return super.getCurrentItem();
    }

    private int getCountOfWrapper() {
        if (wrapperPagerAdapter != null) {
            return wrapperPagerAdapter.getCount();
        }
        return 0;
    }

    private int getCount() {
        if (wrappedPagerAdapter != null) {
            return wrappedPagerAdapter.getCount();
        }
        return 0;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                stopAutoScroll();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                startAutoScroll();

                break;
        }
        return super.onTouchEvent(ev);
    }


    private int lastPosition = 0;

    private class InnerOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;

        public InnerOnPageChangeListener() {

        }

        public void setOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            if (!(position >= getCount() || position == 0)
                    || (lastPosition == 0 && position == getCount())
                    || (position == getCount() && lastPosition == getCount() - 1)) {
                mIndicatorListener.moving(position - 1, positionOffset);
            }
            lastPosition = position;
            if (listener != null && position > 0 && position < getCount()) {

                listener.onPageScrolled(position - 1, positionOffset, positionOffsetPixels);
            }

        }

        @Override
        public void onPageSelected(int position) {

            final int pos = getRealPosition(position);
            if (listener != null) {
                AutoScrollViewPager.this.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onPageSelected(pos);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            /**
             *
             */
            if (state == SCROLL_STATE_IDLE && getCount() > 1) {

                //   mIndicatorListener.setNext(getCurrentItem());

                if (getCurrentItemOfWrapper() == 0) {
                    setCurrentItem(getCount() - 1, false);

                } else if (getCurrentItemOfWrapper() == getCountOfWrapper() - 1) {
                    setCurrentItem(0, false);
                }
            }

            if (listener != null) {
                listener.onPageScrollStateChanged(state);
            }
        }
    }

}
