package com.pandaq.pandaeye.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pandaq.pandaeye.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/12/21.
 * email : 767807368@qq.com
 */

public class NavItem extends RelativeLayout {

    @BindView(R.id.iv_start_icon)
    ImageView mIvStartIcon;
    @BindView(R.id.tv_action_title)
    TextView mTvActionTitle;
    @BindView(R.id.tv_action_state)
    TextView mTvActionState;

    public NavItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.nav_item_layout, this);
        ButterKnife.bind(view);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavItem);
        String action = ta.getString(R.styleable.NavItem_action);
        int actionState = ta.getInteger(R.styleable.NavItem_actionState, -1);
        if (actionState == 0) {
//            mTvActionState.setText(context.getString(R.string.off));
            mTvActionState.setVisibility(GONE);
        } else if (actionState == 1) {
            mTvActionState.setVisibility(VISIBLE);
//            mTvActionState.setText(context.getString(R.string.on));
        }
        mTvActionTitle.setText(action != null ? action : "");
        int startInco = ta.getResourceId(R.styleable.NavItem_startIcon, 0);
        if (startInco != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mIvStartIcon.setImageDrawable(context.getResources().getDrawable(startInco, context.getTheme()));
            } else {
                mIvStartIcon.setImageDrawable(context.getResources().getDrawable(startInco));
            }
        }
        int color = ta.getColor(R.styleable.NavItem_textColor, Color.GRAY);
        if (color != Color.GRAY) {
            mTvActionTitle.setTextColor(color);
            mTvActionState.setTextColor(color);
        }
        ta.recycle();
    }
}
