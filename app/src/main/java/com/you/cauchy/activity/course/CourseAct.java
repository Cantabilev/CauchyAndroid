package com.you.cauchy.activity.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseActivity;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.fragment.course.CourseDiscussFra;
import com.you.cauchy.activity.fragment.course.CourseInfoFra;
import com.you.cauchy.activity.fragment.course.CourseSectionFra;
import com.you.cauchy.adapter.CourseFragmentAdapter;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.BuyCourseTask;
import com.you.cauchy.net.course.CourseDetailTask;
import com.you.cauchy.net.response.CourseDetailResponse;
import com.you.cauchy.ui.CommonDialog;
import com.you.cauchy.util.GlideBlurFormation;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;

public class CourseAct extends BaseActivity {

    private ImageView iconPic, iconFuzzy;
    private TextView tvCourseName, tvTeacher, tvAmount, tvPrice;
    private RelativeLayout rlPay;
    private Button btnPay;
    private TabLayout mTabLayout;
    private ViewPager viewPager;

    private CourseFragmentAdapter courseFragmentAdapter;
    private AbsCustomTask courseInfoTask, buyCourseTask;

    private Integer courseCode;
    private ArrayList<BaseFragment> fragments;
    private CommonDialog payDialog;


    @Override
    public void initVariables() {
        courseCode = getIntent().getIntExtra("courseCode", 0);
        courseInfoTask = new CourseDetailTask();
        courseInfoTask.setRestCallback(courseInfoCallback);
        buyCourseTask = new BuyCourseTask();
        buyCourseTask.setRestCallback(buyCourseCallback);
        courseFragmentAdapter = new CourseFragmentAdapter(getSupportFragmentManager());
        payDialog = new CommonDialog(this, "确定购买该课程？");
    }

    @Override
    public void initView() {
        setContentView(R.layout.act_course);
        iconPic = findViewById(R.id.iconPic);
        iconFuzzy = findViewById(R.id.iconFuzzy);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvTeacher = findViewById(R.id.tvTeacher);
        tvAmount = findViewById(R.id.tvAmount);
        tvPrice = findViewById(R.id.tvPrice);
        rlPay = findViewById(R.id.rlPay);
        btnPay = findViewById(R.id.btnPay);
        mTabLayout = findViewById(R.id.tabCourse);
        viewPager = findViewById(R.id.viewpager);

        tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        initFragment();
    }

    @Override
    public void initEvent() {
        btnPay.setOnClickListener(v -> {
            payDialog.setData(courseCode);
            payDialog.show();
        });
        payDialog.setListener((dialog, confirm) -> {
            if (confirm){
                buyCourseTask.execute(payDialog.getData());
                payDialog.dismiss();
            }
        });
    }

    @Override
    public void loadData() {
        courseInfoTask.execute(courseCode);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    private void initFragment() {

        ArrayList<String> titles = new ArrayList<>();
        titles.add("课程简介");
        titles.add("课程表");
        titles.add("教学评价");
        courseFragmentAdapter.setTitles(titles);
        fragments = new ArrayList<>();
        fragments.add(new CourseInfoFra());
        fragments.add(new CourseSectionFra());
        fragments.add(new CourseDiscussFra());
        courseFragmentAdapter.setFragments(fragments);

        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(courseFragmentAdapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    public static void startCourseAct(Context context, Integer courseCode){
        Intent intent = new Intent();
        intent.setClass(context, CourseAct.class);
        intent.putExtra("courseCode", courseCode);
        context.startActivity(intent);
    }

    private RestCallback courseInfoCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<CourseDetailResponse> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<CourseDetailResponse>>() {}.getType());
                CourseDetailResponse data = result.getData();
                Glide.with(CourseAct.this)
                        .load(data.getPicUrl())
                        .error(Glide.with(CourseAct.this).load(R.drawable.ic_error))
                        .into(iconPic);
                Glide.with(CourseAct.this)
                        .load(data.getPicUrl())
                        // 设置高斯模糊
                        .apply(RequestOptions.bitmapTransform(new GlideBlurFormation(CourseAct.this)))
                        .into(iconFuzzy);
                tvCourseName.setText("课程名称：" + data.getCourseName());
                tvTeacher.setText("主讲老师：" + data.getEmployeeName());
                ((CourseInfoFra)fragments.get(0)).setCourseInfo(data.getCourseInfo());
                ((CourseSectionFra)fragments.get(1)).buy(data.getBuy());
                ((CourseSectionFra)fragments.get(1)).setCourseCode(data.getCourseCode());
                ((CourseDiscussFra)fragments.get(2)).buy(data.getBuy());
                ((CourseDiscussFra)fragments.get(2)).setCourseCode(data.getCourseCode());
                if (data.getBuy()){
                    rlPay.setVisibility(View.GONE);
                } else {
                    tvAmount.setText("￥" + data.getPostDiscountPrice().intValue());
                    tvPrice.setText("￥" + data.getCourseAmount().intValue());
                }
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback buyCourseCallback = new RestCallback(this) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                ToastUtil.show("赞！购买成功");
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
