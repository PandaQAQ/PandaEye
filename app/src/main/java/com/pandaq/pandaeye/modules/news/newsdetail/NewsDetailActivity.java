package com.pandaq.pandaeye.modules.news.newsdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.activities.ShareActivity;
import com.pandaq.pandaeye.config.Constants;
import com.pandaq.pandaeye.utils.DensityUtil;
import com.pandaq.pandaeye.utils.PicassoTarget;
import com.pandaq.pandaeye.utils.webview.JavaScriptFunction;
import com.pandaq.pandaeye.utils.webview.WebUtils;
import com.pandaq.pandaeye.widget.FiveThreeImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends ShareActivity implements NewsDetailContract.View {
    private String html_url = "";
    @BindView(R.id.news_img)
    FiveThreeImageView mNewsImg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.wv_topnews_content)
    WebView mWvTopnewsContent;
    @BindView(R.id.cl_topnews_content_parent)
    CoordinatorLayout mClTopnewsContentParent;
    @BindView(R.id.pb_topnews_content)
    ProgressBar mPbTopnewsContent;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private int width;
    private int heigh;
    private NewsDetailPresenter mPresenter = new NewsDetailPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_info);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initView();
        initData();
    }

    private void initView() {
        int[] deviceInfo = DensityUtil.getDeviceInfo(this);
        width = deviceInfo[0];
        heigh = width * 3 / 5;
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(html_url)) {
                    NewsDetailActivity.this.showShare(html_url, mToolbarTitle.getText().toString());
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
        String news_id = bundle.getString(Constants.BUNDLE_KEY_ID);
        String newsImg = bundle.getString(Constants.BUNDLE_KEY_IMG_URL);
        String title = bundle.getString(Constants.BUNDLE_KEY_TITLE);
        html_url = bundle.getString(Constants.BUNDLE_KEY_HTML_URL);
        loadTopNewsInfo(news_id);
        Target target = new PicassoTarget(this, mNewsImg, mToolbarLayout, mToolbar, mFab);
        //不设置的话会有时候不加载图片
        mNewsImg.setTag(target);
        if (!TextUtils.isEmpty(newsImg)) {
            Picasso.with(this)
                    .load(newsImg)
                    .resize(width, heigh)
                    .into(target);
        }
        mToolbarTitle.setText(title);
        mToolbarTitle.setSelected(true);
    }

    @Override
    public void showProgressBar() {
        mPbTopnewsContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbTopnewsContent.setVisibility(View.GONE);
    }

    @Override
    public void loadTopNewsInfo(String news_id) {
        mPresenter.loadNewsContent(news_id);
    }

    @Override
    public void loadFail(String errmsg) {
        showSnackBar(mClTopnewsContentParent, Constants.ERRO + errmsg, Snackbar.LENGTH_SHORT);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void loadSuccess(NewsContent topNewsContent) {
        WebSettings settings = mWvTopnewsContent.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);
        mWvTopnewsContent.addJavascriptInterface(new JavaScriptFunction() {
            @Override
            @JavascriptInterface
            public void getUrl(String string) {
                Toast.makeText(CustomApplication.getContext(), string, Toast.LENGTH_LONG).show();
            }
        }, "JavaScriptFunction");
        // 加载新闻数据，如果图片跟标题图相同则不加载
        String htmlBody = WebUtils.newsInsertPic(topNewsContent);
        String html = WebUtils.buildHtmlForIt(htmlBody, false);
        mWvTopnewsContent.loadDataWithBaseURL(WebUtils.BASE_URL, html, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.dispose();
    }
}
