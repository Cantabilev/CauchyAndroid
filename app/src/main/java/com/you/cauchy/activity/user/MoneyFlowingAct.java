package com.you.cauchy.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.adapter.MoneyFlowingAdapter;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.response.MoneyDetailResponse;
import com.you.cauchy.net.user.MoneyFlowingTask;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MoneyFlowingAct extends BaseActivity {

    private TextView tvCash, tvFree;
    private RecyclerView recyclerMoney;

    private MoneyFlowingAdapter moneyFlowingAdapter;
    private List<MoneyDetailResponse.MoneyFlowing> moneyList;

    private AbsCustomTask moneyFlowingTask;

    @Override
    public void initVariables() {
        moneyFlowingAdapter = new MoneyFlowingAdapter(this, null);
        moneyList = new ArrayList<>();

        moneyFlowingTask = new MoneyFlowingTask();
        moneyFlowingTask.setRestCallback(moneyCallback);
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_money_flowing);
        tvCash = findViewById(R.id.tvCash);
        tvFree = findViewById(R.id.tvFree);
        recyclerMoney = findViewById(R.id.recyclerMoney);
        recyclerMoney.setLayoutManager(new LinearLayoutManager(this));
        recyclerMoney.setItemAnimator(new DefaultItemAnimator());



    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void doBusiness() {
        moneyFlowingTask.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    public static void startMoneyFlowingAct(Context context){
        Intent intent = new Intent(context, MoneyFlowingAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private RestCallback moneyCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<MoneyDetailResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<MoneyDetailResponse>>() {}.getType());
                MoneyDetailResponse data = result.getData();
                tvCash.setText(data.getCashAmount().intValue() + "");
                tvFree.setText(data.getFreeAmount().intValue() + "");
                moneyList.addAll(data.getFeeDetail());
                moneyFlowingAdapter.setmList(moneyList);
                recyclerMoney.setAdapter(moneyFlowingAdapter);
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
