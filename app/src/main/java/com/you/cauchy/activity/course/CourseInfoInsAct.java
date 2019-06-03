package com.you.cauchy.activity.course;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.fragment.course.CoursePreviewFra;
import com.you.cauchy.activity.fragment.course.CourseReviewFra;
import com.you.cauchy.adapter.CourseFragmentAdapter;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.CourseInstanceTask;
import com.you.cauchy.net.response.CourseInfoInsResponse;
import com.you.cauchy.util.DateUtil;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;

public class CourseInfoInsAct extends BaseActivity {

    private TextView tvTitle, tvAddress, tvTime;
    private TabLayout mTabLayout;
    private ViewPager viewPager;

    private CourseFragmentAdapter courseFragmentAdapter;
    private AbsCustomTask courseInstanceTask;

    private Integer insId;
    private ArrayList<BaseFragment> fragments;

    @Override
    public void initVariables() {
        insId = getIntent().getIntExtra("ins_id", 0);

        courseInstanceTask = new CourseInstanceTask();
        courseInstanceTask.setRestCallback(courseInsDtlCallback);

        courseFragmentAdapter = new CourseFragmentAdapter(getSupportFragmentManager());
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_course_info_ins);
        tvTitle = findViewById(R.id.tvTitle);
        tvAddress = findViewById(R.id.tvAddress);
        tvTime = findViewById(R.id.tvTime);
        mTabLayout = findViewById(R.id.tabCourse);
        viewPager = findViewById(R.id.viewpager);
        initFragment();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void loadData() {
        courseInstanceTask.execute(insId);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void resume() {
        GSYVideoManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void destroy() {
        GSYVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    private void initFragment() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("预习");
        titles.add("复习");
        courseFragmentAdapter.setTitles(titles);
        fragments = new ArrayList<>();
        fragments.add(new CoursePreviewFra());
        fragments.add(new CourseReviewFra());

        courseFragmentAdapter.setFragments(fragments);

        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(courseFragmentAdapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    public static void startCourseInfoInsAct(Context context, Integer courseCodeInsId){
        Intent intent = new Intent(context, CourseInfoInsAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("ins_id", courseCodeInsId);
        context.startActivity(intent);
    }

    private RestCallback courseInsDtlCallback = new RestCallback(CourseInfoInsAct.this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<CourseInfoInsResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<CourseInfoInsResponse>>() {}.getType());
                CourseInfoInsResponse data = result.getData();
                tvTitle.setText(data.getCourseTitle());
                tvAddress.setText("上课地址：" + data.getAddress());
                tvTime.setText("上课时间：" + DateUtil.getDateString(data.getStartTime()) + " - " + DateUtil.getDateString(data.getEndTime()));
                ((CoursePreviewFra)fragments.get(0)).setCourseInfoInsId(data.getId());
                ((CourseReviewFra)fragments.get(1)).setCourseInfoInsId(data.getId());
//                courseSectionList.addAll(result.getData());
//                adapter.setmList(courseSectionList);
//                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
