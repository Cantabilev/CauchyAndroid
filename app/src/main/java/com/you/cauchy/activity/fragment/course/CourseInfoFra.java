package com.you.cauchy.activity.fragment.course;

import android.view.View;
import android.widget.TextView;

import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;

public class CourseInfoFra extends BaseFragment {

    private TextView tvCourseInfo;

    @Override
    public int BindLayout() {
        return R.layout.fra_course_info;
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initView(View view) {
        tvCourseInfo = view.findViewById(R.id.tvInfo);
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

    public void setCourseInfo(String courseInfo){
        tvCourseInfo.setText(courseInfo);
    }
}
