package com.pandaq.pandaeye.modules.setting;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.activities.SwipeBackActivity;
import com.pandaq.pandaeye.utils.ViewUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PandaQ on 2017/3/30.
 * Email:767807368@qq.com
 */

public class ChoosePhotoActivity extends SwipeBackActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_select_album)
    TextView mTvSelectAlbum;
    @BindView(R.id.gv_pictures)
    GridView mGvPictures;
    private Map<String, ArrayList<String>> picMap = new HashMap<>();
    private Disposable mDisposable;
    private CheckPicAdapter mPicAdapter;
    private final int CROP_PHOTO = 10;
    private BottomSheetDialog mBottomSheetDialog;
    private ArrayList<ImageFileBean> mImageBeen;
    private final int ACTION_TAKE_PHOTO = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePhotoActivity.this.finish();
            }
        });
        mImageBeen = new ArrayList<>();
        requestRunTimePermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionCall() {
            @Override
            public void requestSuccess() {
                initImages();
            }

            @Override
            public void refused() {
                Toast.makeText(ChoosePhotoActivity.this, "请授予读写和相机权限", Toast.LENGTH_LONG).show();
            }
        });
        mGvPictures.setOnItemClickListener(this);
    }

    private void initImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showSnackBar(mTvSelectAlbum, "未发现存储设备！", Snackbar.LENGTH_SHORT);
        }
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = CustomApplication.getContext().getContentResolver();
        Cursor cursor = contentResolver.query(imageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?"
                , new String[]{"image/jpeg", "image/jpg"}, MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor == null) {
            showSnackBar(mTvSelectAlbum, "未发现存储设备！", Snackbar.LENGTH_SHORT);
            return;
        }
        while (cursor.moveToNext()) {
            //图片路径名
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //图片父路径名
            String parentPath = new File(path).getParentFile().getName();
            if (!picMap.containsKey(parentPath)) {
                ArrayList<String> childList = new ArrayList<>();
                childList.add(path);
                picMap.put(parentPath, childList);
            } else {
                picMap.get(parentPath).add(path);
            }
        }
        cursor.close();
        Observable.create(new ObservableOnSubscribe<Map<String, ArrayList<String>>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, ArrayList<String>>> e) throws Exception {
                e.onNext(picMap);
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<Map<String, ArrayList<String>>, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> apply(Map<String, ArrayList<String>> stringArrayListMap) throws Exception {
                        ArrayList<String> allPath = new ArrayList<>();
                        for (Map.Entry<String, ArrayList<String>> entry : stringArrayListMap.entrySet()) {
                            ImageFileBean imageFileBean = new ImageFileBean();
                            imageFileBean.setFileName(entry.getKey());
                            imageFileBean.setImages(entry.getValue());
                            imageFileBean.setTopImage(entry.getValue().get(0));
                            mImageBeen.add(imageFileBean);
                            allPath.addAll(entry.getValue());
                        }
                        allPath.add(0, "ic_action_camera");
                        ImageFileBean all = new ImageFileBean();
                        all.setFileName(getString(R.string.all_pictures));
                        all.setTopImage(allPath.get(1)); //去掉相机图片
                        all.setImages(allPath);
                        mImageBeen.add(0, all);
                        return allPath;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ArrayList<String> value) {
                        showPics(value);
                        initBottomDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showSnackBar(mTvSelectAlbum, e.getMessage(), Snackbar.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showPics(ArrayList<String> value) {
        if (mPicAdapter == null) {
            mPicAdapter = new CheckPicAdapter(this, value);
            mGvPictures.setAdapter(mPicAdapter);
        } else {
            mPicAdapter.setPicPaths(value);
        }
    }

    private void initBottomDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null, false);
        mBottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = (RecyclerView) mBottomSheetDialog.findViewById(R.id.rv_album_list);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        AlbumAdapter adapter = new AlbumAdapter(mImageBeen, this);
        adapter.setItemClickListener(new AlbumAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ArrayList<String> images) {
                showPics(images);
                mBottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        setBehaviorCallback();
    }

    private void setBehaviorCallback() {
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        assert view != null;
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String imagePath = mPicAdapter.getItem(position);
        if (imagePath.equals("ic_action_camera")) {
            takePhoto();
        } else {
            cropPic(imagePath);
        }
    }


    /**
     * 拍照
     */
    private void takePhoto() {
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File file = new File(ViewUtils.getAppFile(this, "images"));
            File mPhotoFile = new File(ViewUtils.getAppFile(this, "images/user_take.jpg"));
            if (!file.exists()) {
                boolean result = file.mkdirs();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, "com.pandaq.pandaeye.fileprovider", mPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
            }
            startActivityForResult(intent, ACTION_TAKE_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cropPic(String imagePath) {
        File file = new File(imagePath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, "com.pandaq.pandaeye.fileprovider", file);
            intent.setDataAndType(contentUri, "image/*");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0.1);
        intent.putExtra("aspectY", 0.1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        intent.putExtra("scale", true);
        startActivityForResult(intent, CROP_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File mPhotoFile = new File(ViewUtils.getAppFile(this, "images/user_take.jpg"));
        switch (requestCode) {
            case CROP_PHOTO: //裁剪照片后
                if (data != null) {
                    setPicToView(data);
                }
                //裁剪后删除拍照的照片
                if (mPhotoFile.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    mPhotoFile.delete();
                }
                break;
            case ACTION_TAKE_PHOTO:
                if (mPhotoFile.exists()) {
                    cropPic(ViewUtils.getAppFile(this, "images/user_take.jpg"));
                }
                break;
        }
    }

    /**
     * 将获取的头像显示在imageview中
     *
     * @param picData 裁剪后的图片数据
     */
    private void setPicToView(Intent picData) {
        setResult(RESULT_OK, picData);
        this.finish();
    }

    @OnClick(R.id.tv_select_album)
    public void onViewClicked() {
        if (mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
        } else {
            mBottomSheetDialog.show();
        }
    }

}
