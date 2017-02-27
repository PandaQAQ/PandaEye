package com.pandaq.pandaeye.ui.bubble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/9.
 * email : 767807368@qq.com
 * 冒泡圈Fragment
 */
public class BubblCircleFragment extends BaseFragment {
    @BindView(R.id.test)
    TextView mTest;
    @BindView(R.id.image)
    ImageView mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bubble_fragment, container, false);
        ButterKnife.bind(this, view);
        Picasso.with(this.getContext())
                .load("http://cms-bucket.nosdn.127.net/a65200c64b2b41b69e0b879f4026edaf20170217090556.png")
                .into(mImage);
        return view;
    }
}
