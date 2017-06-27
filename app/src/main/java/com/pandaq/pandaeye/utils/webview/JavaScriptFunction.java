package com.pandaq.pandaeye.utils.webview;

import android.webkit.JavascriptInterface;

/**
 * Created by PandaQ on 2017/3/22.
 * JS 接口方法定义
 */

public interface JavaScriptFunction {
    @JavascriptInterface
    void getUrl(String string);
}
