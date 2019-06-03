package com.you.cauchy.activity.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.adapter.ExpectAdapter;
import com.you.cauchy.entity.ExpectChildBean;
import com.you.cauchy.entity.ExpectGroupBean;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.CourseTask;
import com.you.cauchy.net.response.CourseResponse;
import com.you.cauchy.net.response.ExpectNameResponse;
import com.you.cauchy.net.response.MoneyDetailResponse;
import com.you.cauchy.net.response.UserExpectResponse;
import com.you.cauchy.net.user.ExpectInsertTask;
import com.you.cauchy.net.user.ExpectListTask;
import com.you.cauchy.net.user.ExpectTask;
import com.you.cauchy.ui.ExpectDialog;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpectAct extends BaseActivity implements ExpectDialog.OnCloseListener {

    private RecyclerView recyclerExpect;
    private FloatingActionButton fab;

    private ExpectDialog expectDialog;

    private ExpectAdapter adapter;

    private AbsCustomTask userExpectTask, courseTask, expectListTask, expectInsertTask;
    private List<UserExpectResponse> listSource;
    private List<ExpectNameResponse> expectNameList;
    private List<CourseResponse> courseList;

    @Override
    public void initVariables() {
        userExpectTask = new ExpectTask();
        userExpectTask.setRestCallback(userExpectCallback);
        courseTask = new CourseTask();
        courseTask.setRestCallback(courseCallback);
        expectListTask = new ExpectListTask();
        expectListTask.setRestCallback(expectListCallback);
        expectInsertTask = new ExpectInsertTask();
        expectInsertTask.setRestCallback(expectInsertCallback);

        adapter = new ExpectAdapter();
        listSource = new ArrayList<>();

        expectDialog = new ExpectDialog(this);
        expectDialog.setListener(this);
        expectNameList = new ArrayList<>();
        courseList = new ArrayList<>();
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_expect);

        recyclerExpect = findViewById(R.id.recyclerExpect);
        recyclerExpect.setLayoutManager(new LinearLayoutManager(this));
        recyclerExpect.setItemAnimator(new DefaultItemAnimator());

        fab = findViewById(R.id.fab);
    }

    @Override
    public void initEvent() {
        fab.setOnClickListener(v -> {
            expectDialog.show();
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {
        userExpectTask.execute();
        courseTask.execute();
        expectListTask.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    public static void startExpectAct(Context context){
        Intent intent = new Intent(context, ExpectAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private RestCallback userExpectCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<UserExpectResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<UserExpectResponse>>>() {}.getType());
                listSource.clear();
                listSource.addAll(result.getData());
                Map<Integer, List<UserExpectResponse>> courseMap = listSource.stream().collect(Collectors.groupingBy(UserExpectResponse::getCourseId));
                List<ExpectGroupBean> group = new ArrayList<>();
                List<ExpectChildBean> childes;
                for (Integer key : courseMap.keySet()){
                    childes = new ArrayList<>();
                    List<UserExpectResponse> expects = courseMap.get(key);
                    for (UserExpectResponse e : expects){
                        ExpectChildBean child = new ExpectChildBean();
                        child.setId(e.getId());
                        child.setCourseId(e.getCourseId());
                        child.setCourseName(e.getCourseName());
                        child.setExpectName(e.getExpectName());
                        child.setExpectValue(e.getExpectValue());
                        childes.add(child);
                    }
                    group.add(new ExpectGroupBean(childes, expects.get(0).getCourseName()));
                }
                adapter.setmList(group);
                recyclerExpect.setAdapter(adapter);
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback courseCallback = new RestCallback(this){

        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<CourseResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<CourseResponse>>>() {}.getType());
                courseList.addAll(result.getData());
                expectDialog.setCourseList(courseList);
            } else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback expectListCallback = new RestCallback(this){

        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<ExpectNameResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<ExpectNameResponse>>>() {}.getType());
                expectNameList.addAll(result.getData());
                expectDialog.setExpectNameList(expectNameList);
            } else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback expectInsertCallback = new RestCallback(this){

        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                ToastUtil.show("赞！添加成功");
                userExpectTask.execute();
            } else
                ToastUtil.show(response.getMessage());
        }
    };

    @Override
    public void onClick(Dialog dialog, boolean confirm, Integer courseId, Integer expectNameId, String value) {
        if (!confirm)
            return;
        if (null == courseId){
            ToastUtil.show("请选择所属课程");
            return;
        }
        if (null == expectNameId){
            ToastUtil.show("请选择期望选项");
            return;
        }
        if ("".equals(value)){
            ToastUtil.show("请填写有效愿景");
            return;
        }
        expectInsertTask.execute(courseId, expectNameId, value);
        dialog.dismiss();
    }
}
