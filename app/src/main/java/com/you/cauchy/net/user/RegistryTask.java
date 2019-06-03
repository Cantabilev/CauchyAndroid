package com.you.cauchy.net.user;

import com.you.cauchy.net.AbsCustomTask;
import com.zhy.http.okhttp.OkHttpUtils;

public class RegistryTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {

    }

    @Override
    public <Params> void execute(Params... params) {
        OkHttpUtils
                .post()
                .url(URL + "/api/user/registryUser")
                .addParams("account", params[0].toString())
                .addParams("password", params[1].toString())
                .addParams("phone", params[2].toString())
                .addParams("code", params[3].toString())
                .build()
                .execute(restCallback);
    }

}
