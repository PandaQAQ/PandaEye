package com.pandaq.pandaeye;

import android.app.Application;
import android.content.Context;

/**
 * Created by PandaQ on 2016/12/20.
 * email : 767807368@qq.com
 */

public class CustomApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sContext == null) {
            sContext = getApplicationContext();
        }
    }
}
