package com.pandaq.pandaeye.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pandaq.pandaeye.CustomApplication;
import com.pandaq.pandaeye.config.Constants;

/**
 * Created by PandaQ on 2016/12/20.
 * email : 767807368@qq.com
 */

public class SPUtils {
    private static SharedPreferences sp;

    public static SharedPreferences instance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void clearAllData() {
        checkSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    private SharedPreferences.Editor edit() {
        checkSp();
        return sp.edit();
    }

    private static void checkSp() {
        if (sp == null) {
            sp = instance(CustomApplication.getContext());
        }
    }
}
