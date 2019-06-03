package com.you.cauchy.net.course;

import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.response.DiscussResponse;
import com.you.cauchy.util.Preferences;
import com.zhy.http.okhttp.OkHttpUtils;

public class InsertDiscussTask extends AbsCustomTask {

    @Override
    public <Params> void execute(Params params) {
        DiscussResponse discussResponse = (DiscussResponse) params;
        OkHttpUtils
                .post()
                .url(URL + "/api/discuss")
                .addHeader(CAUCHYSSO, Preferences.getToken())
                .addParams("courseCode", discussResponse.getCourseCode().toString())
                .addParams("employeeId", discussResponse.getEmployeeId() != null ? discussResponse.getEmployeeId().toString():"" )
                .addParams("replyTargetId", discussResponse.getReplyTargetId() != null ? discussResponse.getReplyTargetId().toString():"" )
                .addParams("replyTargetUserId", discussResponse.getReplyTargetUserId() != null ? discussResponse.getReplyTargetUserId().toString():"" )
                .addParams("replyTargetEmployeeId", discussResponse.getReplyTargetEmployeeId() != null ? discussResponse.getReplyTargetEmployeeId().toString():"" )
                .addParams("content", discussResponse.getContent())
                .build()
                .execute(restCallback);
    }

    @Override
    public <Params> void execute(Params... params) {


    }
}
