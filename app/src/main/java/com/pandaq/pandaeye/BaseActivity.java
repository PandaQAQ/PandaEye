package com.pandaq.pandaeye;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by PandaQ on 2016/9/8.
 * email : 767807368@qq.com
 * 所有Activity的基类
 */
public class BaseActivity extends AppCompatActivity {
    //静态变量，在 PicassoTarget 中修改 status 颜色时也动态修改
    public static int currentStatusColor;
    private final int permissionRequestCode = 110;
    private PermissionCall mPermissionCall;

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

    public void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /* 处理权限问题*/

    /**
     * 对子类提供的申请权限方法
     *
     * @param permissions 申请的权限
     */
    public void requestRunTimePermissions(String[] permissions, PermissionCall call) {
        if (permissions == null || permissions.length == 0)
            return;
        mPermissionCall = call;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            //提示已经拥有权限
            mPermissionCall.requestSuccess();
        } else {
            //申请权限
            requestPermission(permissions, permissionRequestCode);
        }
    }

    public boolean checkPermissionGranted(String... permissions) {
        boolean result = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(final String[] permissions, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.attention)
                    .setMessage(R.string.content_to_request_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                mPermissionCall.requestSuccess();
                mPermissionCall = null;
            } else {
                mPermissionCall.refused();
                mPermissionCall = null;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface PermissionCall {
        //申请成功
        void requestSuccess();

        //拒绝
        void refused();
    }
}
