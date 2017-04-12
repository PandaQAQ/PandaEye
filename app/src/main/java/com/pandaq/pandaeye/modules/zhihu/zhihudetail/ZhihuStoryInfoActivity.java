package com.pandaq.pandaeye.modules.zhihu.zhihudetail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.activities.ShareActivity;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaeye.utils.PicassoTarget;
import com.pandaq.pandaeye.utils.x5webview.WebUtils;
import com.pandaq.pandaeye.widget.FiveThreeImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 知乎日报打开详情页面
 */
public class ZhihuStoryInfoActivity extends ShareActivity implements ZhiHuDetailContract.View {

    private static final float SCRIM_ADJUSTMENT = 0.075f;
    private String shareUrl = "";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.story_img)
    FiveThreeImageView mStoryImg;
    @BindView(R.id.zhihudaily_webview)
    WebView mZhihudailyWebview;
    @BindView(R.id.pb_load_story)
    ProgressBar mPbLoadStory;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private String story_id = "";
    private ZhihuStoryInfoPresenter mPresenter = new ZhihuStoryInfoPresenter(this);
    int[] mDeviceInfo;
    int width;
    int heigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_story_info);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initView();
        initData();
    }

    private void initView() {
        mDeviceInfo = DensityUtil.getDeviceInfo(this);
        width = mDeviceInfo[0];
        heigh = width * 3 / 5;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(shareUrl)) {
                    ZhihuStoryInfoActivity.this.showShare(shareUrl, mToolbarTitle.getText().toString());
                }
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        story_id = String.valueOf(bundle.getInt(Constants.BUNDLE_KEY_ID));
        String title = bundle.getString(Constants.BUNDLE_KEY_TITLE);
        loadZhihuStory();
        mToolbarTitle.setText(title);
        mToolbarTitle.setSelected(true);
    }

    @Override
    public void showProgressBar() {
        mPbLoadStory.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbLoadStory.setVisibility(View.GONE);
    }

    @Override
    public void loadZhihuStory() {
        mPresenter.loadStory(story_id);
    }

    @Override
    public void loadFail(String errmsg) {

    }

    @Override
    public void loadSuccess(ZhihuStoryContent zhihuStory) {
        shareUrl = zhihuStory.getShare_url();
        Target target = new PicassoTarget(this, mStoryImg, mToolbarLayout, mToolbar, mFab);
        //不设置的话会有时候不加载图片
        mStoryImg.setTag(target);
        String image = zhihuStory.getImage();
        if (!TextUtils.isEmpty(image)) {
            Picasso.with(this)
                    .load(image)
                    .resize(width, heigh)
                    .centerCrop()
                    .into(target);
        }
        startPostponedEnterTransition();
        String url = zhihuStory.getShare_url();
        boolean isEmpty = TextUtils.isEmpty(zhihuStory.getBody());
        String mBody = zhihuStory.getBody();
        String[] scc = zhihuStory.getCss();
        //如果返回的html body为空则直接 load url
        if (isEmpty) {
            mZhihudailyWebview.loadUrl(url);
        } else {
            String data = WebUtils.buildHtmlWithCss(mBody, scc, false);
            mZhihudailyWebview.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.dispose();
    }
}
