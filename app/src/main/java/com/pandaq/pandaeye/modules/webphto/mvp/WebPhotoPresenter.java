package com.pandaq.pandaeye.modules.webphto.mvp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.pandaq.pandaeye.disklrucache.SecretUtil;
import com.pandaq.pandaeye.modules.BasePresenter;
import com.pandaq.pandaeye.modules.ImpBaseView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by PandaQ on 2017/7/4.
 * 查看大图界面的 Presenter
 */

class WebPhotoPresenter extends BasePresenter implements WebPhotoContact.Presenter {
    private WebPhotoContact.View mView;
    private Context mContext;

    WebPhotoPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void savePic(final String url) {
        //Target
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String imageName = SecretUtil.getMD5Result(url) + ".png";
                File dcimFile = getDCIMFile(imageName);
                FileOutputStream ostream = null;
                if (dcimFile != null) {
                    try {
                        ostream = new FileOutputStream(dcimFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mView.savePicSuccess(dcimFile.getAbsolutePath());
                } else {
                    mView.savePicFail("图片已下载或文件不可用！");
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        //使用Picasso去下载图片
        Picasso.with(mContext).load(url).into(target);
    }

    private File getDCIMFile(String imageName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 文件可用
            File dirs = new File(Environment.getExternalStorageDirectory(),
                    "DCIM" + "/PandaEye/");
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory(),
                    "DCIM" + "/PandaEye/" + imageName);
            if (!file.exists()) { //如果文件存在（已经下载过则直接返回空）
                try {
                    //在指定的文件夹中创建文件
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return file;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void bindView(ImpBaseView view) {
        mView = (WebPhotoContact.View) view;
    }

    @Override
    public void unbindView() {
        dispose();
    }

    @Override
    public void onDestory() {
        mView = null;
        mContext = null;
    }
}
