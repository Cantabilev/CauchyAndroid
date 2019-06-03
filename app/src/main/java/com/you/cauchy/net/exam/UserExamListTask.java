package com.you.cauchy.net.exam;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserExamListTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {

    }

    @Override
    public <Params> void execute(Params... params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/userExamination")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .build()
                .execute(restCallback);
    }
}