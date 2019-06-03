package com.you.cauchy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.BuyCourseTask;
import com.you.cauchy.net.response.HotCourseResponse;
import com.you.cauchy.ui.CommonDialog;
import com.you.cauchy.util.ToastUtil;

import java.util.List;

public class HotCourseAdapter extends CommRecyclerAdapter<HotCourseResponse> {

    private AbsCustomTask buyCourseTask;
    private CommonDialog payDialog;

    public HotCourseAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
        buyCourseTask = new BuyCourseTask();
        buyCourseTask.setRestCallback(buyCourseCallback);
        payDialog = new CommonDialog(context, "确定购买该课程？");
        payDialog.setListener((dialog, confirm) -> {
            if (confirm){
                buyCourseTask.execute(payDialog.getData());
                payDialog.dismiss();
            }
        });
    }

    public HotCourseAdapter(Context context, List<HotCourseResponse> list, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, list, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_course_code;
    }

    @Override
    public void convertView(ViewHolder holder, HotCourseResponse hotCourse, int position) {
        ImageView iconShow = holder.getView(R.id.iconShow);
        Glide.with(context.getApplicationContext())
                .load(hotCourse.getPicUrl())
                .error(Glide.with(context.getApplicationContext()).load(R.drawable.ic_error))
                .into(iconShow);
        holder.setText(R.id.tvCourseTitle, hotCourse.getCourseName());
        holder.setText(R.id.tvCourseAmount, "￥" + hotCourse.getCourseAmount().toString());
        holder.setText(R.id.tvShow, "当前容量:(" + hotCourse.getSituation()+"/"+hotCourse.getCapacity() + ")");
        holder.setOnClickListener(R.id.btnPay, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog.setData(hotCourse.getCourseCode());
                payDialog.show();
            }
        });
    }

    private RestCallback buyCourseCallback = new RestCallback(context) {
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
