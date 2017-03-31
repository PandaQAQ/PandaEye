package com.pandaq.pandaeye.ui.setting;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.R;
import com.pandaq.pandaeye.adapters.CheckPicAdapter;
import com.pandaq.pandaeye.ui.base.SwipeBackActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        ButterKnife.bind(this);
        initImages();
        mGvPictures.setOnItemClickListener(this);
    }

    private void initImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            showSnackBar(mTvSelectAlbum, "未发现存储设备！", Snackbar.LENGTH_SHORT);
        }
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = CustomApplication.getContext().getContentResolver();
        Cursor cursor = contentResolver.query(imageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?"
                , new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
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
                            allPath.addAll(entry.getValue());
                        }
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
        File file = new File(imagePath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(file), "image/*");
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CROP_PHOTO: //裁剪照片后
                if (data != null) {
                    setPicToView(data);
                }
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
}
