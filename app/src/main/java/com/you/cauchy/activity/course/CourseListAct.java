package com.you.cauchy.activity.course;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.adapter.HotCourseAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.CourseListTask;
import com.you.cauchy.net.response.HotCourseResponse;
import com.you.cauchy.util.ToastUtil;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class CourseListAct extends BaseActivity implements ViewHolder.OnItemClickListener {

    private TextView tvTitle;
    private RecyclerView recyclerCourse;

    private HotCourseAdapter hotCourseAdapter;

    private AbsCustomTask courseListTask;
    private List<HotCourseResponse> hotCourseList;

    private String title;
    private Integer courseId;

    @Override
    public void initVariables() {
        hotCourseAdapter = new HotCourseAdapter(this, this);
        hotCourseList = new ArrayList<>();

        courseListTask = new CourseListTask();
        courseListTask.setRestCallback(courseListCallback);

        title = getIntent().getStringExtra("title");
        courseId = getIntent().getIntExtra("courseId", 0);
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_course_list);
        tvTitle = findViewById(R.id.tvTitle);
        recyclerCourse = findViewById(R.id.recyclerCourse);
        recyclerCourse.setLayoutManager(new LinearLayoutManager(this));
        recyclerCourse.setItemAnimator(new DefaultItemAnimator());
        recyclerCourse.setAdapter(hotCourseAdapter);

        tvTitle.setText(title);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {
        if (0 == courseId)
            courseListTask.execute();
        else
            courseListTask.execute(courseId);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onItemClick(View view, int position) {
        CourseAct.startCourseAct(this, hotCourseList.get(position).getCourseCode());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public static void startCourseListAct(Context context, String title, Integer courseId){
        Intent intent = new Intent(context, CourseListAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("title", title);
        if (courseId != null)
            intent.putExtra("courseId", courseId);
        context.startActivity(intent);
    }

    private RestCallback courseListCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<HotCourseResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<HotCourseResponse>>>() {}.getType());
                hotCourseList.addAll(result.getData());
                hotCourseAdapter.setmList(hotCourseList);
                hotCourseAdapter.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
