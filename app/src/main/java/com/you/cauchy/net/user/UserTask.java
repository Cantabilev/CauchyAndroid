package com.you.cauchy.net.user;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {

    }

    @Override
    public <Params> void execute(Params... params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/user")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .build()
                .execute(restCallback);
    }

}
