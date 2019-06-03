package com.you.cauchy;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.you.cauchy.util.Preferences;
import com.you.cauchy.util.ToastUtil;

/**
 * @author liuqiqi
 * @date 2019/3/12 18:04
 */
public class CauchyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static CauchyApplication instance=null;
    public static CauchyApplication getInstance() {
        return instance;
    }
    private Context context;

    private static int MAX_MEM = (int) Runtime.getRuntime().maxMemory() / 4;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = CauchyApplication.this;
        Preferences.init(this);
        ToastUtil.init(this);
    }

    public Context getContext(){
        return context;
    }
}
