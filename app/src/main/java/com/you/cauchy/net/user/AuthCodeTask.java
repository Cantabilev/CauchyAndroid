package com.you.cauchy.net.user;

import com.you.cauchy.net.AbsCustomTask;
import com.zhy.http.okhttp.OkHttpUtils;

public class AuthCodeTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/sms/sendSMS")
                .addParams("phone", params.toString())
                .addParams("type", "registry")
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {

    }

}
