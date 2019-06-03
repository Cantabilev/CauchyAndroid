package com.you.cauchy.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.you.cauchy.CauchyApplication;
import com.you.cauchy.interfaces.IActBase;

public abstract class BaseActivity extends AppCompatActivity implements IActBase {

    protected Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 整个应用的Application
     **/
    private CauchyApplication app = null;
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, TAG + "-->onCreate()");

        // 获取整个应用的Application
        app = CauchyApplication.getInstance();
        //setSystemBarTransparent();
        initVariables();
        initView();
        initEvent();
        loadData();
        doBusiness();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, TAG + "-->onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, TAG + "-->onResume()");
        resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, TAG + "-->onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, TAG + "-->onRestart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, TAG + "-->onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, TAG + "-->onDestroy()");
        destroy();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            //        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // getWindow().setStatusBarColor(Color.TRANSPARENT);
            // getWindow().setNavigationBarColor(Color.BLACK);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().setFlags(
            //        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            //        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // 解决华为底部虚拟按键覆盖底部按钮问题
        // if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
        //     AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        // }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 获取整个应用的Application
     */
    public CauchyApplication getApp() {
        return this.app;
    }
}
