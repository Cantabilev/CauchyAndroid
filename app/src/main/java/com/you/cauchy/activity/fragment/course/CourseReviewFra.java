package com.you.cauchy.activity.fragment.course;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.adapter.CourseMaterialAdapter;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.CourseReviewMaterialTask;
import com.you.cauchy.net.response.CourseMaterialResponse;
import com.you.cauchy.util.ToastUtil;

public class CourseReviewFra extends BaseFragment {

    private RecyclerView recyclerReview;

    private CourseMaterialAdapter adapter;

    private AbsCustomTask courseReviewMaterialTask;
    private Integer courseInfoInsId;

    @Override
    public int BindLayout() {
        return R.layout.fra_course_review;
    }

    @Override
    public void initVariables() {
        adapter = new CourseMaterialAdapter(getActivity(), null);
        courseReviewMaterialTask = new CourseReviewMaterialTask();
        courseReviewMaterialTask.setRestCallback(previewCallback);
    }

    @Override
    public void initView(View view) {
        recyclerReview = view.findViewById(R.id.recyclerReview);
        recyclerReview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerReview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {
//        List<CourseMaterialResponse> mList = new ArrayList<>();
//        mList.add(new CourseMaterialResponse());
//        mList.add(new CourseMaterialResponse());
//        mList.add(new CourseMaterialResponse());
//        adapter.setmList(mList);
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
        GSYVideoManager.onPause();
    }

    @Override
    public void destroy() {

    }

    public void setCourseInfoInsId(Integer courseInfoInsId) {
        this.courseInfoInsId = courseInfoInsId;
        if (null == courseReviewMaterialTask)
            courseReviewMaterialTask = new CourseReviewMaterialTask();
        courseReviewMaterialTask.execute(courseInfoInsId);
    }

    private RestCallback previewCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<CourseMaterialResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<CourseMaterialResponse>>() {}.getType());
                adapter.setMaterial(result.getData());
                recyclerReview.setAdapter(adapter);
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
