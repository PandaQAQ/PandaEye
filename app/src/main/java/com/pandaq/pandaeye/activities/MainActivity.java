package com.pandaq.pandaeye.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pandaq.pandaeye.BaseActivity;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.modules.news.NewsMainFragment;
import com.pandaq.pandaeye.modules.setting.ChoosePhotoActivity;
import com.pandaq.pandaeye.modules.setting.aboutme.AboutActivity;
import com.pandaq.pandaeye.modules.video.videohome.mvp.VideoHomeFragment;
import com.pandaq.pandaeye.modules.zhihu.home.mvp.ZhihuDailyFragment;
import com.pandaq.pandaeye.rxbus.RxBus;
import com.pandaq.pandaeye.rxbus.RxConstants;
import com.pandaq.pandaeye.utils.BlurImageUtils;
import com.pandaq.pandaeye.utils.DataCleanManager;
import com.pandaq.pandaeye.utils.ViewUtils;
import com.pandaq.pandaeye.widget.NavItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PandaQ on 2016/9/7.
 * email : 767807368@qq.com
 */
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener {

    private final int ABOUT_ME = 10;
    private final int FAVORITE = 11;
    private final int VIDEO = 12;
    private final int SHARE = 13;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.bottom_navgation)
    BottomNavigationBar mBottomNavgation;
    @BindView(R.id.fl_bottom_navgation)
    FrameLayout mFlBottomNavgation;
    @BindView(R.id.userimage)
    CircleImageView mUserimage;
    @BindView(R.id.navigation_header_container)
    LinearLayout mNavigationHeaderContainer;
    private Fragment mCurrentFrag;
    private FragmentManager fm;
    private Fragment mZhihuFragment;
    private Fragment mNewsFragment;
    private Fragment mBubbleFragment;
    private BottomSheetBehavior mBottomSheetBehavior;
    private boolean drawerOpen = false;
    private NavItem mCleanItem;
    private int drawerIntentAction;
    private final int ACTION_GET_PIC = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fm = getSupportFragmentManager();
        mBottomSheetBehavior = BottomSheetBehavior.from(mFlBottomNavgation);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        initView();
    }

    float down_y;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (drawerOpen) {
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() - down_y > 20) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (event.getY() - down_y < -150) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getY() - down_y > 20) {
                    if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
                if (event.getY() - down_y < -150) {
                    if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 几个Fragment都采用懒加载的方式，只有当用户可见状态下才做数据初始化操作
     */
    private void initView() {
        mZhihuFragment = new ZhihuDailyFragment();
        mBubbleFragment = new VideoHomeFragment();
        mNewsFragment = new NewsMainFragment();
        Picasso.with(this)
                .load("file://" + ViewUtils.getAppFile(this, "images/user.png"))
                .error(getResources().getDrawable(R.drawable.userimage))
                .into(mUserimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap overlay = BlurImageUtils.blur(mUserimage, 3, 3);
                        mNavigationHeaderContainer.setBackground(new BitmapDrawable(getResources(), overlay));
                    }

                    @Override
                    public void onError() {
                        Bitmap overlay = BlurImageUtils.blur(mUserimage, 3, 3);
                        mNavigationHeaderContainer.setBackground(new BitmapDrawable(getResources(), overlay));
                    }
                });
        initNavigation();
        switchContent(mZhihuFragment);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
                drawerOpen = true;
                mCleanItem.setTvActionState(DataCleanManager.getTotalCacheSize(MainActivity.this));
                // action 初始化
                drawerIntentAction = 0;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerOpen = false;
                switch (drawerIntentAction) {
                    case FAVORITE:
                        break;
                    case VIDEO:
                        break;
                    case ABOUT_ME:
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case SHARE:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mUserimage.setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.nav_favorite).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.nav_download).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.nav_share).setOnClickListener(this);
        mDrawerLayout.findViewById(R.id.nav_about).setOnClickListener(this);
        mCleanItem = (NavItem) mDrawerLayout.findViewById(R.id.nav_clean);
        mCleanItem.setOnClickListener(this);
    }

    private void initNavigation() {
        mBottomNavgation
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                .addItem(new BottomNavigationItem(R.drawable.ic_home, getString(R.string.nav_00_title)).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_view_headline, getString(R.string.nav_01_title)).setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_live_tv, getString(R.string.nav_02_title)).setActiveColorResource(R.color.colorPrimary))
                //暂时没想好做个神马功能
//                .addItem(new BottomNavigationItem(R.drawable.ic_explore, getString(R.string.nav_03_title)).setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .setTabSelectedListener(this)
                .initialise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                switchContent(mZhihuFragment);
                break;
            case 1:
                switchContent(mNewsFragment);
                break;
            case 2:
                switchContent(mBubbleFragment);
                break;
//            case 3:
//                switchContent(mMovieFragment);
//                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }

    /**
     * 动态添加fragment，不会重复创建fragment
     *
     * @param to 将要加载的fragment
     */
    public void switchContent(Fragment to) {
        if (mCurrentFrag != to) {
            if (!to.isAdded()) {// 如果to fragment没有被add则增加一个fragment
                if (mCurrentFrag != null) {
                    fm.beginTransaction().hide(mCurrentFrag).commit();
                }
                fm.beginTransaction()
                        .add(R.id.main_view, to)
                        .commit();
            } else {
                fm.beginTransaction().hide(mCurrentFrag).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mCurrentFrag = to;
        }
    }

    private Long firstTime = 0L;

    @Override
    public void onBackPressed() {
        if (drawerOpen) {
            mDrawerLayout.closeDrawers();
            return;
        }
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        RxBus.getDefault().postWithCode(RxConstants.BACK_PRESSED_CODE, RxConstants.BACK_PRESSED_DATA);
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            showLongToast(this, getString(R.string.back_again_exit));
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userimage:
                takePhoto();
                break;
            case R.id.nav_favorite:
                drawerIntentAction = FAVORITE;
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_download:
                drawerIntentAction = VIDEO;
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                drawerIntentAction = SHARE;
                this.showShare("发现有趣的熊猫眼！https://github.com/PandaQAQ/PandaEye/blob/master/README.md", "分享下载地址");
                break;
            case R.id.nav_about:
                drawerIntentAction = ABOUT_ME;
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_clean:
                DataCleanManager.clearAllCache(this);
                mCleanItem.setTvActionState(DataCleanManager.getTotalCacheSize(this));
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(this, ChoosePhotoActivity.class);
        startActivityForResult(intent, ACTION_GET_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_GET_PIC && data != null) {
            Bitmap bitmap = data.getExtras().getParcelable("data");
            mUserimage.setImageBitmap(bitmap);
            Bitmap overlay = BlurImageUtils.blur(mUserimage, 3, 3);
            mNavigationHeaderContainer.setBackground(new BitmapDrawable(getResources(), overlay));
            saveUserImage(bitmap);
        }
    }

    private void saveUserImage(Bitmap bitmap) {
        // 保存头像到sdcard
        FileOutputStream fos;
        try {
            File file = new File(ViewUtils.getAppFile(this, "images"));
            File image = new File(ViewUtils.getAppFile(this, "images/user.png"));
            if (!file.exists()) {
                file.mkdirs();
                if (!image.exists()) {
                    image.createNewFile();
                }
            }
            fos = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showShare(String url, String shareTitle) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(shareIntent, shareTitle));
    }
}
