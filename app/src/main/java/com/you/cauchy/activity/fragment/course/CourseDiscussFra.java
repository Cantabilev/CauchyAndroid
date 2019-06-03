package com.you.cauchy.activity.fragment.course;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.adapter.DiscussAdapter;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.DiscussListTask;
import com.you.cauchy.net.course.InsertDiscussTask;
import com.you.cauchy.net.response.DiscussResponse;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CourseDiscussFra extends BaseFragment {

    private Integer courseCode;
    private boolean buy;

    private EditText etContent;
    private Button btnSend;
    private LinearLayout llDiscuss;
    private RecyclerView recyclerDiscuss;
    private DiscussAdapter adapter;

    private List<DiscussResponse> discussList;
    private AbsCustomTask discussListTask, insertDiscussTask;

    @Override
    public int BindLayout() {
        return R.layout.fra_course_discuss;
    }

    @Override
    public void initVariables() {

        discussListTask = new DiscussListTask();
        discussListTask.setRestCallback(discussCallback);
        insertDiscussTask = new InsertDiscussTask();
        insertDiscussTask.setRestCallback(insertCallback);

        adapter = new DiscussAdapter(getContext(), null);
        discussList = new ArrayList<>();
    }

    @Override
    public void initView(View view) {
        recyclerDiscuss = view.findViewById(R.id.recyclerDiscuss);
        recyclerDiscuss.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerDiscuss.setItemAnimator(new DefaultItemAnimator());
        recyclerDiscuss.setAdapter(adapter);

        etContent = view.findViewById(R.id.etContent);
        btnSend = view.findViewById(R.id.btnSend);
        llDiscuss = view.findViewById(R.id.llDiscuss);
        llDiscuss.setVisibility(buy ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initEvent() {
        btnSend.setOnClickListener(v -> {
            String content = etContent.getText().toString().trim();
            if (content.length() > 0) {
                DiscussResponse discuss = new DiscussResponse();
                discuss.setCourseCode(courseCode);
                discuss.setContent(content);
                insertDiscussTask.execute(discuss);
            }
            else
                ToastUtil.show("请输入评论");
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {
        discussListTask.execute(courseCode);
    }

    @Override
    public void create() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void buy(Boolean buy){
        this.buy = buy;
        //adapter.setBuy(buy);
    }

    public void setCourseCode(Integer courseCode) {
        this.courseCode = courseCode;
    }

    public RestCallback discussCallback = new RestCallback(getContext()) {
        @Override
        public void onResponse(RestResult response) {
            // 判断是否达成接口请求目的
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<DiscussResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<DiscussResponse>>>() {}.getType());
                discussList.clear();
                discussList.addAll(result.getData());
                adapter.setmList(discussList);
                adapter.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };

    public RestCallback insertCallback = new RestCallback(getContext()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                ToastUtil.show("赞！提交成功");
                etContent.setText("");
                discussListTask.execute(courseCode);
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
