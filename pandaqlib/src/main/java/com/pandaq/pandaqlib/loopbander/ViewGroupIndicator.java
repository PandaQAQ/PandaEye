package com.pandaq.pandaqlib.loopbander;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.pandaq.pandaqlib.R;

import static android.graphics.Paint.Style;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 */
public class ViewGroupIndicator extends View implements IndicatorListener {

    public static final String TAG = ViewGroupIndicator.class.getSimpleName();

    private Context context;

    //绘制当前页面的Paint
    private Paint mSelectedPaint;
    //绘制其他页面的Paint
    private Paint mUnselectedPaint;

    //半径
    private int mSelectedRadius;
    private int mUnselectedRadius;

    //绘图颜色
    private int mSelectedColor;
    private int mUnselectedColor;
    //数量
    private int mIndicatorCount;
    //选中的下标
    private int mSelectedIndicatorIndex = 0;


    //两个点之间的距离
    private int dis;
    //当前页面指示器的x坐标
    private float indecatorX;

    private IndicatorParentImbl parent;

    //是否正在移动
    private boolean isMoving = false;
    //是否正在移动到下一个
    private boolean isMoveNext = false;

    //移动比率
    private float disR;

    public ViewGroupIndicator(Context context) {
        this(context, null);
    }

    public ViewGroupIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroupIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        initIndicator(attrs);

    }

    public void setParent(IndicatorParentImbl parent) {
        this.parent = parent;

        parent.setIndicatorListener(this);

        mIndicatorCount = parent.getIndicatorCount();
        invalidate();

    }


    //初始化
    private void initIndicator(AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.indicator,
                R.attr.indicatorDefStyleAttr,
                R.style.DefaultIndicatorTheme);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.indicator_radius) {
                mUnselectedRadius = mSelectedRadius = (int) a.getDimension(attr,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.indicator_distance_from_two_indicator) {
                dis = (int) a.getDimension(attr,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.indicator_selected_color) {
                mSelectedColor = a.getColor(attr, Color.WHITE);

            } else if (attr == R.styleable.indicator_unselected_color) {
                mUnselectedColor = a.getColor(attr, Color.WHITE);

            }
        }
        a.recycle();

        mIndicatorCount = 0;


        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(mSelectedColor);
        mSelectedPaint.setStrokeWidth(2);
        mSelectedPaint.setStyle(Style.FILL);
        mSelectedPaint.setAntiAlias(true);

        mUnselectedPaint = new Paint();
        mUnselectedPaint.setColor(mUnselectedColor);
        mUnselectedPaint.setStrokeWidth(2);
        mUnselectedPaint.setStyle(Style.STROKE);
        mUnselectedPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        if (mSelectedIndicatorIndex >= mIndicatorCount)
            mSelectedIndicatorIndex = mIndicatorCount - 1;
        if (mSelectedIndicatorIndex < 0)
            mSelectedIndicatorIndex = 0;

        int cx = (getWidth() - paddingLeft - paddingRight - mUnselectedRadius * 2 * (mIndicatorCount - 1) - dis * (mIndicatorCount - 1)) / 2;

        //获取到第一个点的x坐标
        float startX = (float) (getWidth() - paddingLeft - paddingRight - mUnselectedRadius * 2 * (mIndicatorCount - 1) - dis * (mIndicatorCount - 1)) / 2;

        int cy = getHeight() / 2;

        //绘制除当前页面的indicator之外的所有的点
        for (int i = 0; i < mIndicatorCount; i++) {
            canvas.drawCircle(cx, cy, mUnselectedRadius, mUnselectedPaint);
            cx += dis + mUnselectedRadius * 2;
        }
        if (indecatorX == 0) indecatorX = startX;
        //绘制指示当前页面的indicator
        canvas.drawCircle(indecatorX, cy, mSelectedRadius, mSelectedPaint);
        //如果正在移动，根据移动比率进行移动
        if (isMoving) {
            indecatorX = startX + (dis + mUnselectedRadius * 2) * (mSelectedIndicatorIndex + disR);
            isMoving = false;
            invalidate();
        }

    }

    @Override
    public void moving(int position, float dis) {
        isMoving = true;
        mSelectedIndicatorIndex = position;
        this.disR = dis;
        invalidate();

    }


    public void start() {
        if (parent != null) {
            parent.onReDraw(mSelectedIndicatorIndex);
            parent.startAutoScroll();
        }
    }

    public void stop() {
        if (parent != null) {
            parent.stopAutoScroll();
        }
    }

    @Override
    public void notifyDateChanged(int count) {
        mIndicatorCount = count;
        invalidate();
    }
}
