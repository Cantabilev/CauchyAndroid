package com.you.cauchy.net;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.you.cauchy.util.ToastUtil;
import com.zhy.http.okhttp.callback.Callback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;

public abstract class RestCallback extends Callback<RestResult> {

    // url:https://www.jianshu.com/p/c62332fcf8f4
    private ZLoadingDialog dialog;

    public RestCallback(Context context) {
        if (null == context)
            return;
        dialog = new ZLoadingDialog(context);
        dialog.setCancelable(false);
        dialog.setLoadingBuilder(Z_TYPE.INTERTWINE)// 设置类型
                .setLoadingColor(Color.parseColor("#008577"))//颜色
                .setHintText("Loading...");
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        // 开启 dialog
        if (null != dialog)
            dialog.show();
    }

    @Override
    public RestResult parseNetworkResponse(Response response) throws IOException {
        String string = response.body().string();
        RestResult result = new Gson().fromJson(string, RestResult.class);
        return result;
    }

    @Override
    public void onAfter() {
        super.onAfter();
        // 关闭 dialog
        if (null != dialog)
            dialog.dismiss();
    }

    @Override
    public void onError(Request request, Exception e) {
        ToastUtil.show("网络有点问题...");
    }
}
