package com.you.cauchy.net.course;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class CourseInstanceTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/courseCodeInstance/getInstanceDetail")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("courseInfoInsId", params.toString())
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {

    }
}
