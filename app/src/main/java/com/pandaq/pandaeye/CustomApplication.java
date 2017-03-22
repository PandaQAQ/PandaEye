package com.pandaq.pandaeye;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by PandaQ on 2016/12/20.
 * email : 767807368@qq.com
 */

public class CustomApplication extends Application {
    public static CustomApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sApplication == null) {
            sApplication = this;
        }
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        //预加载腾讯浏览服务 X5 内核
        QbSdk.initX5Environment(getContext(), null);

    }

    public static Context getContext() {
        return sApplication.getApplicationContext();
    }

    /**
     * 获取应用的版本号
     *
     * @return 应用版本号，默认返回1
     */
    public static int getAppVersionCode() {
        Context context = getContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
