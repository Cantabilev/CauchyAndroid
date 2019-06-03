package com.you.cauchy.net.exam;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class ExamDetailTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        OkHttpUtils
                .get()
                .url(URL + "/api/examination/detail")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("examId", params.toString())
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {

    }
}