package com.pandaq.pandaeye.ui.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2017/3/2.
 * 评论 fragment
 */

public class VideoCommentFrag extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_description_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
