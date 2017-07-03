package com.pandaq.pandaeye.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pandaq.pandaeye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends SwipeBackActivity implements ViewPager.OnPageChangeListener {

    public static String BUNDLE_KEY_IMAGES = "imageUrls";
    public static String BUNDLE_KEY_CURIMAGE = "curImageUrl";
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_html_images)
    ViewPager mVpHtmlImages;

    private ArrayList<String> images;
    private String curImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        images = bundle.getStringArrayList(BUNDLE_KEY_IMAGES);
        curImage = bundle.getString(BUNDLE_KEY_CURIMAGE);
    }


    private void initView() {
        mVpHtmlImages.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        mVpHtmlImages.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mVpHtmlImages.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
