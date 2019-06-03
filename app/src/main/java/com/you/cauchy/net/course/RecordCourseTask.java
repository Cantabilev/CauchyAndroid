package com.you.cauchy.net.course;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class RecordCourseTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {

    }

    @Override
    public <Params> void execute(Params... params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/courseOrder/getSignUpRecord")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .build()
                .execute(restCallback);
    }
}
