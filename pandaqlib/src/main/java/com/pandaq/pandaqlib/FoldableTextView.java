package com.pandaq.pandaqlib;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by PandaQ on 2017/3/4.
 * 可折叠的 TextView
 */

public class FoldableTextView extends RelativeLayout implements View.OnClickListener {

    /* 默认位置 */
    private static final int DEFAULT_FLAG_GRAVITY = 0;

    /* 默认文本大小 */
    private static final int DEFAULT_TEXT_SIZE = 14;

    /* 默认文本颜色 */
    private static final int DEFAULT_TEXT_COLOR = Color.DKGRAY;

    /* 默认提示文本颜色 */
    private static final int DEFAULT_FLAG_COLOR = Color.BLUE;

    /* 默认最高行数 */
    private static final int MAX_COLLAPSED_LINES = 3;

    /* 默认动画执行时间 */
    private static final int DEFAULT_ANIM_DURATION = 200;

    /*展开标示布局*/
    private LinearLayout mLlParent;

    /*内容textview*/
    protected TextView mTvContent;

    /*展开收起textview*/
    protected TextView mTvExpandCollapse;
    /*展开收起imageview*/
    private ImageView mIvImage;

    /*是否有重新绘制*/
    private boolean mRelayout;

    /*默认收起*/
    private boolean mCollapsed = true;
    private int gravity = DEFAULT_FLAG_GRAVITY;
    /*展开图片*/
    private Drawable mExpandDrawable;
    /*收起图片*/
    private Drawable mCollapseDrawable;
    /*动画执行时间*/
    private int mAnimationDuration;
    /*是否正在执行动画*/
    private boolean mAnimating;
    /* 展开收起状态回调 */
    private OnExpandStateChangeListener mListener;
    /* listview等列表情况下保存每个item的收起/展开状态 */
    private SparseBooleanArray mCollapsedStatus;
    /* 列表位置 */
    private int mPosition;

    /*设置内容最大行数，超过隐藏*/
    private int mMaxCollapsedLines;

    /*这个linerlayout容器的高度*/
    private int mCollapsedHeight;

    /*内容tv真实高度（含padding）*/
    private int mTextHeightWithMaxLines;

    /*内容tvMarginTopAmndBottom高度*/
    private int mMarginBetweenTxtAndBottom;

    /*内容颜色*/
    private int contentTextColor;
    /*收起展开颜色*/
    private int collapseExpandTextColor;
    /*内容字体大小*/
    private float contentTextSize;
    /*收起展字体大小*/
    private float collapseExpandTextSize;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mMarginBetweenTxtAndBottom = getHeight() - mTvContent.getHeight();
        }
    };


    public FoldableTextView(Context context) {
        this(context, null);
    }

    public FoldableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FoldableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * 初始化属性
     */
    private void init(AttributeSet attrs) {
        mCollapsedStatus = new SparseBooleanArray();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FoldableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.FoldableTextView_minLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.FoldableTextView_animDuration, DEFAULT_ANIM_DURATION);
        mExpandDrawable = typedArray.getDrawable(R.styleable.FoldableTextView_toggleSrcOpen);
        mCollapseDrawable = typedArray.getDrawable(R.styleable.FoldableTextView_toggleSrcClose);

        if (mExpandDrawable == null) {
            mExpandDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_green_arrow_up);
        }
        if (mCollapseDrawable == null) {
            mCollapseDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_green_arrow_down);
        }
        contentTextColor = typedArray.getColor(R.styleable.FoldableTextView_textColor, DEFAULT_TEXT_COLOR);
        contentTextSize = typedArray.getDimension(R.styleable.FoldableTextView_textSize, DEFAULT_TEXT_SIZE);
        collapseExpandTextColor = typedArray.getColor(R.styleable.FoldableTextView_flagColor, DEFAULT_FLAG_COLOR);
        collapseExpandTextSize = typedArray.getDimension(R.styleable.FoldableTextView_flagSize, DEFAULT_TEXT_SIZE);
        gravity = typedArray.getInt(R.styleable.FoldableTextView_flagGravity, DEFAULT_FLAG_GRAVITY);
        typedArray.recycle();
        // default visibility is gone
        setVisibility(GONE);
    }

    /**
     * 渲染完成时初始化view
     */
    @Override
    protected void onFinishInflate() {
        findViews();
        if (gravity != DEFAULT_FLAG_GRAVITY) {
            //设置收起展开位置：中或者右
            LayoutParams layoutParams = (LayoutParams) mLlParent.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, R.id.ll_parent);
            mLlParent.setLayoutParams(layoutParams);
        }
        super.onFinishInflate();
    }

    /**
     * 初始化viwe
     */
    private void findViews() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.foldable_textview_layout, this);
        mTvContent = (TextView) findViewById(R.id.tv_content_text);
        mTvContent.setOnClickListener(this);
        mIvImage = (ImageView) findViewById(R.id.iv_expand_toggle);
        mTvExpandCollapse = (TextView) findViewById(R.id.tv_expand_flag);
        setDrawbleAndText();
        mTvExpandCollapse.setOnClickListener(this);
        mLlParent = (LinearLayout) findViewById(R.id.ll_parent);
        mTvContent.setTextColor(contentTextColor);
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentTextSize);
        mTvExpandCollapse.setTextColor(collapseExpandTextColor);
        mTvExpandCollapse.setTextSize(TypedValue.COMPLEX_UNIT_SP, collapseExpandTextSize);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (mTvExpandCollapse.getVisibility() != View.VISIBLE) {
            return;
        }
        mCollapsed = !mCollapsed;
        //修改收起/展开图标、文字
        setDrawbleAndText();
        //保存位置状态
        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mCollapsed);
        }
        // 执行展开/收起动画
        mAnimating = true;
        ValueAnimator valueAnimator;
        if (mCollapsed) {
//            mTvContent.setMaxLines(mMaxCollapsedLines);
            valueAnimator = ValueAnimator.ofInt(getHeight(), mCollapsedHeight);
        } else {
            valueAnimator = ValueAnimator.ofInt(getHeight(), getHeight() +
                    mTextHeightWithMaxLines - mTvContent.getHeight());
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                mTvContent.setMaxHeight(animatedValue - mMarginBetweenTxtAndBottom);
                getLayoutParams().height = animatedValue;
                requestLayout();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // 动画结束后发送结束的信号
                /// clear the animation flag
                mAnimating = false;
                // notify the listener
                if (mListener != null) {
                    mListener.onExpandStateChanged(mTvContent, !mCollapsed);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setDuration(mAnimationDuration);
        valueAnimator.start();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 当动画还在执行状态时，拦截事件，不让child处理
        return mAnimating;
    }

    /**
     * 重新测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;
        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mTvExpandCollapse.setVisibility(View.GONE);
        mTvContent.setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果内容真实行数小于等于最大行数，不处理
        if (mTvContent.getLineCount() <= mMaxCollapsedLines) {
            return;
        }
        // 获取内容tv真实高度（含padding）
        mTextHeightWithMaxLines = getRealTextViewHeight(mTvContent);

        // 如果是收起状态，重新设置最大行数
        if (mCollapsed) {
            mTvContent.setMaxLines(mMaxCollapsedLines);
        }
        mTvExpandCollapse.setVisibility(View.VISIBLE);

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            mTvContent.post(mRunnable);
            // 保存这个容器的测量高度
            mCollapsedHeight = getMeasuredHeight();
        }
    }

    /**
     * 获取内容tv真实高度（含padding）
     */
    private static int getRealTextViewHeight(TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    /**
     * 设置收起展开图标位置和文字
     */
    private void setDrawbleAndText() {
        mIvImage.setImageDrawable(mCollapsed ? mCollapseDrawable : mExpandDrawable);
        mTvExpandCollapse.setText(mCollapsed ? getResources().getString(R.string.expand_text) : getResources().getString(R.string.close_text));
    }


    /*********暴露给外部调用方法***********/

    /**
     * 设置收起/展开监听
     *
     * @param listener
     */
    public void setOnExpandStateChangeListener(OnExpandStateChangeListener listener) {
        mListener = listener;
    }

    /**
     * 设置内容
     *
     * @param text
     */
    public void setText(CharSequence text) {
        mRelayout = true;
        mTvContent.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置内容，列表情况下，带有保存位置收起/展开状态
     *
     * @param text
     * @param position
     */
    public void setText(CharSequence text, int position) {
        mPosition = position;
        //获取状态，如无，默认是true:收起
        mCollapsed = mCollapsedStatus.get(position, true);
        clearAnimation();
        //设置收起/展开图标和文字
        setDrawbleAndText();
        mTvExpandCollapse.setText(mCollapsed ? getResources().getString(R.string.expand_text) : getResources().getString(R.string.close_text));
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    /**
     * 获取内容
     *
     * @return
     */
    public CharSequence getText() {
        if (mTvContent == null) {
            return "";
        }
        return mTvContent.getText();
    }

    /**
     * 定义状态改变接口
     */
    public interface OnExpandStateChangeListener {
        /**
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }
}
