package com.you.cauchy.net.user;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class ExpectInsertTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {

    }

    @Override
    public <Params> void execute(Params... params) {
        OkHttpUtils
                .post()
                .url(URL + "/api/expect/insertExpect")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("courseId", params[0].toString())
                .addParams("expectNameId", params[1].toString())
                .addParams("expectValue", params[2].toString())
                .build()
                .execute(restCallback);
    }

}
