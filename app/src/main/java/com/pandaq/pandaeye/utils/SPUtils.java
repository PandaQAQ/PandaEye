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

    public static SharedPreferences getInstance(Context context) {
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

    public static void putStringValue(String key, String value) {
        checkSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringValue(String key, String defValue) {
        checkSp();
        return sp.getString(key, defValue);
    }

    public static void putBooleanValue(String key, boolean value) {
        checkSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanValue(String key, boolean defValue) {
        checkSp();
        return sp.getBoolean(key, defValue);
    }

    public static void putIntValue(String key, int value) {
        checkSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntValue(String key, int defValue) {
        checkSp();
        return sp.getInt(key, defValue);
    }

    public static void putFloatValue(String key, float value) {
        checkSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloatValue(String key, float defValue) {
        checkSp();
        return sp.getFloat(key, defValue);
    }

    private static void checkSp() {
        if (sp == null) {
            sp = getInstance(CustomApplication.getContext());
        }
    }
}
