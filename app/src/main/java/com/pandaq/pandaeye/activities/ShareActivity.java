package com.pandaq.pandaeye.activities;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by PandaQ on 2017/3/23.
 * 带分享的 Activity 继承至 SwipeBackActivity
 */

/**
 * 分享文本
 */
public class ShareActivity extends SwipeBackActivity {
    public void showShare(String url, String shareTitle) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(shareIntent, shareTitle));
    }

    /**
     * 分享多内容功能
     *
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void showShare(String activityTitle, String msgTitle, String msgText,
                          String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            intent.setType("image/*");
            File file = new File("file:///android_asset/splash_01.jpg");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }
}
