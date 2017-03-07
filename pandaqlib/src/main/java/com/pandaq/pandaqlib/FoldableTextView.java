package com.pandaq.pandaqlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by PandaQ on 2017/3/4.
 * 可折叠的 TextView
 */

public class FoldableTextView extends RelativeLayout {

    //控件的一些默认属性
    private int DEFAULT_TEXTCOLOR = Color.DKGRAY; //内容默认深灰色
    private int DEFAULT_FLAGCOLOR = Color.BLUE; //标示默认蓝色
    private float DEFAULT_TEXTSIZE = 14; //字体大小默认 14sp
    private float DEFAULT_FLAGSIZE = 14; //字体大小默认 14sp
    private int DEFAULT_GRAVITY_CENTER = 0; //标示默认居中
    private int DEFAULY_TOGGLE_ID = -1; //无 toggle 图片时资源 ID =-1
    private boolean FOLDED = true; //默认对文本进行折叠
    //显示内容的 TextView
    private TextView mTvContent;
    //收起展开的图片开关
    private ImageView mIvExpandToggle;
    //收起展开的文字标示
    private TextView mTvExpandFlag;

    public FoldableTextView(Context context) {
        this(context, null);
    }

    public FoldableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.foldable_textview_layout, this);
        mTvContent = (TextView) findViewById(R.id.tv_content_text);
        mIvExpandToggle = (ImageView) findViewById(R.id.iv_expand_toggle);
        mTvExpandFlag = (TextView) findViewById(R.id.tv_expand_flag);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FoldableTextView);
        int textColor = ta.getColor(R.styleable.FoldableTextView_textColor, Color.DKGRAY);
        mTvContent.setTextColor(textColor);
        float textSize = ta.getDimension(R.styleable.FoldableTextView_textSize, DEFAULT_TEXTSIZE);
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int flagColor = ta.getColor(R.styleable.FoldableTextView_flagColor, Color.DKGRAY);
        mTvExpandFlag.setTextColor(flagColor);
        float flagSize = ta.getDimension(R.styleable.FoldableTextView_flagSize, DEFAULT_FLAGSIZE);
        mTvExpandFlag.setTextSize(TypedValue.COMPLEX_UNIT_SP, flagSize);
        int gravity = ta.getInt(R.styleable.FoldableTextView_flagGravity, DEFAULT_GRAVITY_CENTER);
        boolean folded = ta.getBoolean(R.styleable.FoldableTextView_folded, FOLDED);
        int toggleSrc = ta.getResourceId(R.styleable.FoldableTextView_toggleSrc, DEFAULY_TOGGLE_ID);
        ta.recycle();
    }
}
