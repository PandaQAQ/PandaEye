package com.pandaq.pandaeye.modules.webphto.mvp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.activities.SwipeBackActivity;
import com.pandaq.pandaeye.modules.webphto.ImagesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoViewActivity extends SwipeBackActivity implements ViewPager.OnPageChangeListener, WebPhotoContact.View {

    public static String BUNDLE_KEY_IMAGES = "imageUrls";
    public static String BUNDLE_KEY_CURIMAGE = "curImageUrl";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_html_images)
    ViewPager mVpHtmlImages;
    @BindView(R.id.tv_photo_process)
    TextView mTvPhotoProcess;
    private int curPosition = 0;
    private WebPhotoPresenter mPresenter = new WebPhotoPresenter(this, this);

    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initData();
        initView();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        images = bundle.getStringArrayList(BUNDLE_KEY_IMAGES);
        String curImage = bundle.getString(BUNDLE_KEY_CURIMAGE);
        curPosition = images.indexOf(curImage);
    }


    private void initView() {
        mVpHtmlImages.setAdapter(new ImagesAdapter(this, images));
        mVpHtmlImages.addOnPageChangeListener(this);
        mVpHtmlImages.setCurrentItem(curPosition);
        mTvPhotoProcess.setText(curPosition + "/" + images.size());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mVpHtmlImages.setCurrentItem(position);
        curPosition = position;
        mTvPhotoProcess.setText(curPosition+1 + "/" + images.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.iv_save_pic)
    public void onViewClicked() {
        mPresenter.savePic(images.get(curPosition));
    }

    public void savePicSuccess(String path) {
        Toast.makeText(this, "图片下载至:" + path, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void savePicFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
