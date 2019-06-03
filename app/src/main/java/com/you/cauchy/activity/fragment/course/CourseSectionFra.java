package com.you.cauchy.activity.fragment.course;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.course.CourseInfoInsAct;
import com.you.cauchy.adapter.CourseSectionAdapter;
import com.you.cauchy.adapter.wrapper.HeaderAndFooterWrapper;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.CourseSectionTask;
import com.you.cauchy.net.response.CourseInfoInsResponse;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CourseSectionFra extends BaseFragment implements ViewHolder.OnItemClickListener {

    private RecyclerView recyclerSection;

    private CourseSectionAdapter adapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    private AbsCustomTask courseSectionTask;
    
    private ArrayList<CourseInfoInsResponse> courseSectionList;

    @Override
    public int BindLayout() {
        return R.layout.fra_course_section;
    }

    @Override
    public void initVariables() {
        courseSectionTask = new CourseSectionTask();
        courseSectionTask.setRestCallback(courseSectionCallback);

        adapter = new CourseSectionAdapter(getActivity(), this);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);

        courseSectionList = new ArrayList<>();
    }

    @Override
    public void initView(View view) {
        recyclerSection = view.findViewById(R.id.recyclerSection);
        recyclerSection.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSection.setItemAnimator(new DefaultItemAnimator());
        recyclerSection.setAdapter(mHeaderAndFooterWrapper);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {

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

    @Override
    public void onItemClick(View view, int position) {
        CourseInfoInsAct.startCourseInfoInsAct(getActivity(), courseSectionList.get(position).getId());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private RestCallback courseSectionCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<CourseInfoInsResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<CourseInfoInsResponse>>>() {}.getType());
                courseSectionList.addAll(result.getData());
                adapter.setmList(courseSectionList);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };

    public void buy(Boolean buy){
        adapter.setBuy(buy);
    }

    public void setCourseCode(Integer courseCode){
        courseSectionTask.execute(courseCode);
    }
}
