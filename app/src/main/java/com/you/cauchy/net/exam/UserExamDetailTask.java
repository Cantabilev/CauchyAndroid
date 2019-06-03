package com.you.cauchy.net.exam;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserExamDetailTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/userExamination/detail")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("userExamId", params.toString())
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {

    }
}