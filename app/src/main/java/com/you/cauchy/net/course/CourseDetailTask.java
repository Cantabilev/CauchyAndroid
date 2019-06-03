package com.you.cauchy.net.course;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class CourseDetailTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/courseCode/getCourseDetailInfo")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("courseCode", params.toString())
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {

    }
}
