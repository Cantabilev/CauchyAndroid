package com.you.cauchy.net.course;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class CourseSectionTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/courseCodeInstance/getInstanceList")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("courseCode", params.toString())
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {

    }
}
