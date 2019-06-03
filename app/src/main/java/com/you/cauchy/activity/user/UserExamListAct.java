package com.you.cauchy.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.Constraints;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.activity.exam.ExamAct;
import com.you.cauchy.adapter.UserExamListAdapter;
import com.you.cauchy.adapter.wrapper.HeaderAndFooterWrapper;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.exam.UserExamListTask;
import com.you.cauchy.net.response.UserExamResponse;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class UserExamListAct extends BaseActivity implements ViewHolder.OnItemClickListener {

    private RecyclerView recyclerExamRecord;
    private TextView tvPlaceholder;
    private View header;

    private UserExamListAdapter adapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    private AbsCustomTask userExamListTask;

    private List<UserExamResponse> list;

    @Override
    public void initVariables() {
        userExamListTask = new UserExamListTask();
        userExamListTask.setRestCallback(examListCallback);

        adapter = new UserExamListAdapter(this, this);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);

        list = new ArrayList<>();
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_user_exam_list);

        recyclerExamRecord = findViewById(R.id.recyclerExamRecord);
        recyclerExamRecord.setLayoutManager(new LinearLayoutManager(this));
        recyclerExamRecord.setItemAnimator(new DefaultItemAnimator());
        tvPlaceholder = findViewById(R.id.tvPlaceholder);

        header = LayoutInflater.from(this).inflate(R.layout.item_exam, null);
        header.setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
        mHeaderAndFooterWrapper.addHeaderView(header);
        recyclerExamRecord.setAdapter(mHeaderAndFooterWrapper);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {
        userExamListTask.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onItemClick(View view, int position) {
        ExamAct.startExamAct(this, null, adapter.getmList().get(position - mHeaderAndFooterWrapper.getHeadersCount()).getId());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public static void startUserListAct(Context context){
        Intent intent = new Intent(context, UserExamListAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private RestCallback examListCallback = new RestCallback(this){

        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<UserExamResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<UserExamResponse>>>() {}.getType());
                list.addAll(result.getData());
                if (list.isEmpty()){
                    recyclerExamRecord.setVisibility(View.GONE);
                    tvPlaceholder.setVisibility(View.VISIBLE);
                    return;
                }
                adapter.setmList(list);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            } else
                ToastUtil.show(response.getMessage());
        }
    };
}
