package com.you.cauchy.net.user;

import com.you.cauchy.net.AbsCustomTask;
import com.zhy.http.okhttp.OkHttpUtils;

public class ValidateTokenTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .post()
                .url(URL + "/api/user/validateToken")
                .addParams("sso", params.toString())
                .build()
                .execute(restCallback);
    }

    @Override
    public final <Params> void execute(Params... params) {

    }
}
