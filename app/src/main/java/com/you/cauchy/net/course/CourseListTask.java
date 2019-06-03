package com.you.cauchy.net.course;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class CourseListTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/courseCode")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("courseId", params.toString())
                .addParams("all", "true")
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/courseCode")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("all", "true")
                .build()
                .execute(restCallback);
    }
}
