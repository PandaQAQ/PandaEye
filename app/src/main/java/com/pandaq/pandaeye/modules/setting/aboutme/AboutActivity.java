package com.pandaq.pandaeye.modules.setting.aboutme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.activities.SwipeBackActivity;
import com.pandaq.pandaeye.widget.NavItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PandaQ on 2017/3/24.
 * 关于 Activity
 */

public class AboutActivity extends SwipeBackActivity implements AboutMeContract.View {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.civ_my_avatar)
    CircleImageView mCivMyAvatar;
    @BindView(R.id.tv_myname)
    TextView mTvMyname;
    @BindView(R.id.nav_github)
    NavItem mNavGithub;
    @BindView(R.id.nav_jianshu)
    NavItem mNavJianshu;
    @BindView(R.id.nav_juejin)
    NavItem mNavJuejin;
    private AboutMePresenter mPresenter = new AboutMePresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
        loadMyInfo();
    }

    @Override
    public void loadMyInfo() {
        mPresenter.loadInfo("pandaqaq");
    }

    @Override
    public void showMyInfo(UserInfo myInfo) {
        Picasso.
                with(this)
                .load(myInfo.getAvatar_url())
                .into(mCivMyAvatar);
        mTvMyname.setText(myInfo.getName());
    }

    @Override
    public void loadMyInfoFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dispose();
    }

    @OnClick({R.id.nav_github, R.id.nav_jianshu, R.id.nav_juejin})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        switch (view.getId()) {
            case R.id.nav_github:
                Uri github = Uri.parse("https://github.com/PandaQAQ");
                intent.setData(github);
                startActivity(intent);
                break;
            case R.id.nav_jianshu:
                Uri jianshu = Uri.parse("http://www.jianshu.com/u/aa53f5d59037");
                intent.setData(jianshu);
                startActivity(intent);
                break;
            case R.id.nav_juejin:
                Uri juejin = Uri.parse("https://juejin.im/user/57aaeaa3165abd0061789740");
                intent.setData(juejin);
                startActivity(intent);
                break;
        }
    }
}
