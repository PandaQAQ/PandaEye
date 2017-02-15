package com.pandaq.pandaeye.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pandaq.pandaeye.R;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @param errorMsg        提示信息
     * @param LENGTH          提示显示时长（Snackbar.SHORT or SnackBar.LONG）
     * @param action          按钮文字
     * @param onClickListener 按钮点击事件
     */
    public void showSnackBar(View mContainer, String errorMsg, int LENGTH, String action, View.OnClickListener onClickListener) {
        Snackbar lSnackbar = null;
        lSnackbar = Snackbar.make(mContainer, errorMsg, LENGTH).setAction(action, onClickListener);
        View lView = lSnackbar.getView();
//        TextView snackText = (TextView) lView.findViewById(R.id.snackbar_text);
        TextView snackAction = (TextView) lView.findViewById(R.id.snackbar_action);
        snackAction.setTextColor(getResources().getColor(R.color.white_FFFFFF));
//        //对两个控件进行自定义设置
        lView.setBackgroundColor(getResources().getColor(R.color.black_414040));
        lSnackbar.show();
    }

    /**
     * @param errorMsg 提示信息
     * @param LENGTH   提示显示时长（Snackbar.SHORT or SnackBar.LONG）
     */
    public void showSnackBar(View mContainer, String errorMsg, int LENGTH) {
        Snackbar lSnackbar = null;
        lSnackbar = Snackbar.make(mContainer, errorMsg, LENGTH);
        View lView = lSnackbar.getView();
//        //对两个控件进行自定义设置
        lView.setBackgroundColor(getResources().getColor(R.color.black_414040));
        lSnackbar.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
