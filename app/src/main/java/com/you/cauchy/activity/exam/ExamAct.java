package com.you.cauchy.activity.exam;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.adapter.ExamQuestionAdapter;
import com.you.cauchy.adapter.UserExamQuestionAdapter;
import com.you.cauchy.adapter.wrapper.HeaderAndFooterWrapper;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.InsertUserExamTask;
import com.you.cauchy.net.exam.ExamDetailTask;
import com.you.cauchy.net.exam.UserExamDetailTask;
import com.you.cauchy.net.response.ExamDetailResponse;
import com.you.cauchy.net.response.UserExamDetailResponse;
import com.you.cauchy.net.response.UserExamResponse;
import com.you.cauchy.util.ToastUtil;

public class ExamAct extends BaseActivity {

    private TextView tvTitle, tvSubtitle;
    private RecyclerView recyclerQuestion;
    private Button btnSubmit;

    private ExamQuestionAdapter adapterExam;
    private UserExamQuestionAdapter adapterUserExam;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    private AbsCustomTask examDetailTask, insertUserExamTask, userExamDetailTask;

    // 只有一个有值，examId: 添加测试时存在    userExamId: 用户查看测试存在
    private Integer examId, userExamId;
    // 是否启用编辑
    private boolean enable = false;

    @Override
    public void initVariables() {
        examId = getIntent().getIntExtra("examId", 0);
        userExamId = getIntent().getIntExtra("userExamId", 0);
        enable  = examId != 0 && userExamId == 0 ;

        if (enable){
            adapterExam = new ExamQuestionAdapter(ExamAct.this, null);
            mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapterExam);

            examDetailTask = new ExamDetailTask();
            examDetailTask.setRestCallback(examDetailCallback);
            insertUserExamTask = new InsertUserExamTask();
            insertUserExamTask.setRestCallback(insertUserExamCallback);
        } else {
            adapterUserExam = new UserExamQuestionAdapter(ExamAct.this, null);
            mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapterUserExam);

            userExamDetailTask = new UserExamDetailTask();
            userExamDetailTask.setRestCallback(userExamDetailCallback);
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_exam);
        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        recyclerQuestion = findViewById(R.id.recyclerQuestion);
        recyclerQuestion.setLayoutManager(new LinearLayoutManager(this));
        recyclerQuestion.setItemAnimator(new DefaultItemAnimator());
        recyclerQuestion.setAdapter(mHeaderAndFooterWrapper);

        // 如果 启用编辑 才添加提交按钮
        if (enable){
            View view = LayoutInflater.from(this).inflate(R.layout.item_footer_submit, recyclerQuestion, false);
            btnSubmit = view.findViewById(R.id.btnSubmit);
            mHeaderAndFooterWrapper.addFootView(view);
        }
    }

    @Override
    public void initEvent() {
        if (enable)
            btnSubmit.setOnClickListener(v -> submitExam());
    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {
        if (enable)
            examDetailTask.execute(examId);
        else
            userExamDetailTask.execute(userExamId);

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private void submitExam(){
        insertUserExamTask.execute(examId, recyclerQuestion, adapterExam.getmList());
    }

    public static void startExamAct(Context context, Integer examId, Integer userExamId){
        Intent intent = new Intent(context, ExamAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("examId", examId);
        intent.putExtra("userExamId", userExamId);
        context.startActivity(intent);
    }

    private RestCallback examDetailCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<ExamDetailResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<ExamDetailResponse>>() {}.getType());
                ExamDetailResponse exam = result.getData();
                tvTitle.setText(exam.getExam().getTitle());
                tvSubtitle.setText(exam.getExam().getSubtitle());
                adapterExam.setmList(exam.getQuestions());
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback insertUserExamCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                ToastUtil.show("赞！提交成功");
                finish();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback userExamDetailCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<UserExamDetailResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<UserExamDetailResponse>>() {}.getType());
                UserExamDetailResponse userExam = result.getData();

                tvTitle.setText(userExam.getUserExam().getTitle());
                tvSubtitle.setText(userExam.getUserExam().getSubtitle());
                adapterUserExam.setmList(userExam.getQuestions());
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
