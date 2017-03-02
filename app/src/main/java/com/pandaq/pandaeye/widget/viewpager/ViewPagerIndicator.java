package com.pandaq.pandaeye.widget.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.utils.DensityUtil;

/**
 * Created by PandaQ on 2017/3/2.
 * 自定义 ViewPager 的指示器
 */

public class ViewPagerIndicator extends LinearLayout {

    private static final String TAG = "ViewPagerIndicator";

    public static final int TRIANGLE = 1;//三角形
    public static final int LINE = 2;//下划线
    public static int selected_indicator = TRIANGLE;//要使用的指示器

    private int mTitleWidth;//每个标题宽
    private int mTriangleWidth;//三角宽
    private int mInitLeftMargin;//初始化的三角x位置
    private int mMoveMargin = 0;//计算出来的移动x位置
    private int mTriangleY;//三角形底部的位置Y
    private int mTitleCount = 9;//标题总数
    private int mVisibableCount = 3;//一屏显示标题数量
    private Paint mPaint;
    private Path mPath;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();

        //初始化一些指示器的宽高参数
        mTitleWidth = screenWidth / 3;//默认每个标题占屏幕三分之一宽
        mTriangleWidth = screenWidth / 3 / 6;//一个三角形底边占每个标题六分之一
        mInitLeftMargin = screenWidth / 3 / 2 - (mTriangleWidth / 2);//初始化第一次三角在标题下居中
        mTriangleY = DensityUtil.dip2px(context, 50);//xml标题栏高50dp
        Log.d(TAG, screenWidth + "-" + mTriangleWidth + "-" + mInitLeftMargin);

        //获取标题数量
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTitleCount = array.getInt(R.styleable.ViewPagerIndicator_titleCount, mTitleCount);
        array.recycle();

        //生成标题
        for (int i = 0; i < mTitleCount; i++) {
            TextView textView = new TextView(context);
            textView.setText("标题" + (i + 1));
            textView.setTextColor(getResources().getColor(R.color.white_FFFFFF));
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / mVisibableCount, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(lp);
            addView(textView);
        }
        //画三角形画笔
        mPaint = new Paint();
        //直角三角形
        mPath = new Path();
    }

    //指示器+容器滚动
    public void move(int pos, float offset) {
        if (selected_indicator == TRIANGLE) {
            mMoveMargin = (int) (mInitLeftMargin + pos * mTitleWidth + (offset * mTitleWidth));//三角形移动
        } else if (selected_indicator == LINE) {
            mMoveMargin = (int) (pos * mTitleWidth + (offset * mTitleWidth));//线移动
        }
        //容器滚动，在一屏倒数第二个才允许
        if (pos >= mVisibableCount - 2 && pos < mTitleCount - 2) {
            scrollTo((int) ((pos - (mVisibableCount - 2)) * mTitleWidth + (mTitleWidth * offset)), 0);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10.0f);//线宽

        if (selected_indicator == TRIANGLE) {
            if (mMoveMargin == 0) {//不是第一次draw，使用移动的值
                mMoveMargin = mInitLeftMargin;
            }
            mPath.moveTo(mMoveMargin, mTriangleY);//左点
            mPath.lineTo(mMoveMargin + mTriangleWidth, mTriangleY);//右点
            mPath.lineTo(mMoveMargin + (mTriangleWidth / 2), mTriangleY - (mTriangleWidth / 2));//上点
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        } else if (selected_indicator == LINE) {
            canvas.drawLine(mMoveMargin, mTriangleY, mTitleWidth + mMoveMargin, mTriangleY, mPaint);
        }
    }

    public void setSelected_indicator(int selected_indicator) {
        ViewPagerIndicator.selected_indicator = selected_indicator;
    }

    //选中改变字体颜色
    public void selectText(int position) {
        TextView textView;
        for (int i = 0; i < mTitleCount; i++) {
            textView = (TextView) getChildAt(i);
            if (position == i) {
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                textView.setTextColor(getResources().getColor(R.color.white_FFFFFF));
            }
        }
    }
}
