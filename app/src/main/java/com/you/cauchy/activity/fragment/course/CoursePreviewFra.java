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
import com.you.cauchy.net.course.CoursePreviewMaterialTask;
import com.you.cauchy.net.response.CourseMaterialResponse;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CoursePreviewFra extends BaseFragment {

    private RecyclerView recyclerPreview;

    private CourseMaterialAdapter adapter;

    private AbsCustomTask coursePreviewMaterialTask;
    private Integer courseInfoInsId;

    @Override
    public int BindLayout() {
        return R.layout.fra_course_preview;
    }

    @Override
    public void initVariables() {
        adapter = new CourseMaterialAdapter(getActivity(), null);
        coursePreviewMaterialTask = new CoursePreviewMaterialTask();
        coursePreviewMaterialTask.setRestCallback(previewCallback);
    }

    @Override
    public void initView(View view) {
        recyclerPreview = view.findViewById(R.id.recyclerPreview);
        recyclerPreview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerPreview.setItemAnimator(new DefaultItemAnimator());
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
        if (null == coursePreviewMaterialTask)
            coursePreviewMaterialTask = new CoursePreviewMaterialTask();
        coursePreviewMaterialTask.execute(courseInfoInsId);
    }

    private RestCallback previewCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<CourseMaterialResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<CourseMaterialResponse>>() {}.getType());
                adapter.setMaterial(result.getData());
                recyclerPreview.setAdapter(adapter);
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
