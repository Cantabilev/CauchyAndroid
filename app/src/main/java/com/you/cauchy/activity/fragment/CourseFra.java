package com.you.cauchy.activity.fragment;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.you.cauchy.R;
import com.you.cauchy.activity.BaseFragment;
import com.you.cauchy.activity.course.CourseAct;
import com.you.cauchy.activity.course.CourseListAct;
import com.you.cauchy.adapter.HotCourseAdapter;
import com.you.cauchy.adapter.wrapper.HeaderAndFooterWrapper;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.BuyCourseTask;
import com.you.cauchy.net.course.HotCourseTask;
import com.you.cauchy.net.course.RecommendTask;
import com.you.cauchy.net.response.CourseCodeRecommendResponse;
import com.you.cauchy.net.response.HotCourseResponse;
import com.you.cauchy.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseFra extends BaseFragment implements OnBannerListener, ViewHolder.OnItemClickListener {

    private Button btnMathArts, btnMathScience, btnEnglish;
    private Button btnTicket;
    private Banner banner;
    private RecyclerView recyclerCourse;

    private HotCourseAdapter hotCourseAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    private AbsCustomTask recommendTask, hotCourseTask;
    private List<CourseCodeRecommendResponse> recommendList;
    private List<String> picUrls, titleUrls;
    private List<HotCourseResponse> hotCourseList;

    @Override
    public int BindLayout() {
        return R.layout.fra_course;
    }

    @Override
    public void initVariables() {
        recommendTask = new RecommendTask();
        hotCourseTask = new HotCourseTask();
        recommendTask.setRestCallback(recommendCallback);
        hotCourseTask.setRestCallback(hotCallback);

        hotCourseAdapter = new HotCourseAdapter(getActivity(), this);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(hotCourseAdapter);

        recommendList = new ArrayList<>();
        picUrls = new ArrayList<>();
        titleUrls = new ArrayList<>();

        hotCourseList = new ArrayList<>();
    }

    @Override
    public void initView(View view) {
        btnMathArts = view.findViewById(R.id.btnMathArts);
        btnMathScience = view.findViewById(R.id.btnMathScience);
        btnEnglish = view.findViewById(R.id.btnEnglish);
        btnTicket = view.findViewById(R.id.btnTicket);

        banner = view.findViewById(R.id.recommend);
        initBanner();

        recyclerCourse = view.findViewById(R.id.recyclerCourse);
        recyclerCourse.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCourse.setItemAnimator(new DefaultItemAnimator());
        recyclerCourse.setAdapter(mHeaderAndFooterWrapper);
    }

    @Override
    public void initEvent() {
        btnMathArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListAct.startCourseListAct(getContext(), "考研数学-文科", 2);
            }
        });
        btnMathScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListAct.startCourseListAct(getContext(), "考研数学-理工", 1);
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListAct.startCourseListAct(getContext(), "考研英语", 3);
            }
        });
        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListAct.startCourseListAct(getContext(), "全部课程", null);
            }
        });
    }

    @Override
    public void loadData() {
        recommendTask.execute();
        hotCourseTask.execute();
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

    private void initBanner(){
        //设置样式，里面有很多种样式可以自己都看看效果
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new MyLoader());
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        banner.setBannerAnimation(Transformer.Default);
        //轮播图片的文字
        banner.setBannerTitles(titleUrls);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        banner.setImages(picUrls)
                .setOnBannerListener(this);
    }

    /**
     * 轮播监听
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        CourseAct.startCourseAct(getActivity(), recommendList.get(position).getCourseCode());
    }

    @Override
    public void onItemClick(View view, int position) {
        CourseAct.startCourseAct(getActivity(), hotCourseList.get(position).getCourseCode());
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    /**
     * 网络加载图片
     * 使用了Glide图片加载框架
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load((String) path)
                    .error(Glide.with(context.getApplicationContext()).load(R.drawable.ic_error))
                    .into(imageView);
        }
    }

    private RestCallback recommendCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<CourseCodeRecommendResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<CourseCodeRecommendResponse>>>() {}.getType());
                recommendList.addAll(result.getData());
                picUrls = recommendList.stream().map(CourseCodeRecommendResponse::getRecommendUrl).collect(Collectors.toList());
                titleUrls = recommendList.stream().map(CourseCodeRecommendResponse::getCourseName).collect(Collectors.toList());
                banner.setImages(picUrls);
                banner.setBannerTitles(titleUrls);
                banner.start();
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
    private RestCallback hotCallback = new RestCallback(getActivity()) {
        @Override
        public void onResponse(RestResult response) {
            if (response.getCode() == RestResult.SUCCESS) {
                Gson gson = new Gson();
                RestResult<List<HotCourseResponse>> result = gson.fromJson(gson.toJson(response), new TypeToken<RestResult<List<HotCourseResponse>>>() {}.getType());
                hotCourseList.addAll(result.getData());
                hotCourseAdapter.setmList(hotCourseList);
            }
            else
                ToastUtil.show(response.getMessage());
        }
    };
}
