package com.you.cauchy.activity.fragment;

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
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.course.CourseInfoInsAct;
import com.you.cauchy.adapter.RecordCourseAdapter;
import com.you.cauchy.adapter.TodayCourseAdapter;
import com.you.cauchy.adapter.wrapper.HeaderAndFooterWrapper;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.RecordCourseTask;
import com.you.cauchy.net.course.TodayCourseTask;
import com.you.cauchy.net.course.TotalCourseTask;
import com.you.cauchy.net.response.CourseInfoResponse;
import com.you.cauchy.net.response.HotCourseResponse;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by you on 2019/3/20 22:51
 * e-mail: 1269859055@qq.com
 */
public class StudyFra extends BaseFragment {

    private View placeholderView;

    private RecyclerView recyclerToday, recyclerRecord;
    private TextView tvTotal;
    private AbsCustomTask todayCourseTask, totalCourseTask, recordCourseTask;

    private TodayCourseAdapter todayAdapter;
    private RecordCourseAdapter recordAdapter;
    private HeaderAndFooterWrapper todayWrapper, recordWrapper;

    private List<CourseInfoResponse> todayCourseList;
    private List<HotCourseResponse> recordCourseList;

    @Override
    public int BindLayout() {
        return R.layout.fra_study;
    }

    @Override
    public void initVariables() {
        todayCourseTask = new TodayCourseTask();
        totalCourseTask = new TotalCourseTask();
        recordCourseTask = new RecordCourseTask();
        todayCourseTask.setRestCallback(todayCourseCallback);
        totalCourseTask.setRestCallback(totalCourseCallback);
        recordCourseTask.setRestCallback(recordCourseCallback);

        todayAdapter = new TodayCourseAdapter(getActivity(), todayListener);
        recordAdapter = new RecordCourseAdapter(getActivity(), recordListener);
        todayWrapper = new HeaderAndFooterWrapper(todayAdapter);
        recordWrapper = new HeaderAndFooterWrapper(recordAdapter);

        todayCourseList = new ArrayList<>();
        recordCourseList = new ArrayList<>();
    }

    @Override
    public void initView(View view) {
        recyclerToday = view.findViewById(R.id.recyclerToday);
        recyclerRecord = view.findViewById(R.id.recyclerRecord);
        tvTotal = view.findViewById(R.id.tvTotal);
        initRecycler();


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        placeholderView = inflater.inflate(R.layout.item_center_tv, null);
        placeholderView.setLayoutParams(new Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT));
        TextView tvText = placeholderView.findViewById(R.id.tvText);
        tvText.setText("今天没有课耶");
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {
        todayCourseTask.execute();
        totalCourseTask.execute();
        recordCourseTask.execute();
    }

    @Override
    public void doBusiness() {

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

    private void initRecycler() {
        recyclerToday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerToday.setItemAnimator(new DefaultItemAnimator());
        recyclerToday.setAdapter(todayWrapper);

        recyclerRecord.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRecord.setItemAnimator(new DefaultItemAnimator());
        recyclerRecord.setAdapter(recordWrapper);
    }

    private RestCallback todayCourseCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<CourseInfoResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<CourseInfoResponse>>>() {}.getType());
                todayCourseList.clear();
                todayCourseList.addAll(result.getData());
                todayAdapter.setmList(todayCourseList);
                if (todayCourseList.isEmpty()) {
                    todayWrapper.addHeaderView(placeholderView);
                }
                todayWrapper.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback totalCourseCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                double total = Double.valueOf(response.getData().toString());
                tvTotal.setText((int)total + "");
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback recordCourseCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<HotCourseResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<HotCourseResponse>>>() {}.getType());
                recordCourseList.addAll(result.getData());
                recordAdapter.setmList(recordCourseList);
                recordWrapper.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };

    private ViewHolder.OnItemClickListener todayListener = new ViewHolder.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            CourseInfoInsAct.startCourseInfoInsAct(getActivity(), todayCourseList.get(position).getId());
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
    private ViewHolder.OnItemClickListener recordListener = new ViewHolder.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };
}
