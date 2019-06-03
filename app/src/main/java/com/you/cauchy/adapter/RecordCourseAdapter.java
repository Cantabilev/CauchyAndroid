package com.you.cauchy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.you.cauchy.R;
import com.you.cauchy.activity.course.CourseAct;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.response.HotCourseResponse;
import com.you.cauchy.util.ToastUtil;

import java.util.List;

public class RecordCourseAdapter extends CommRecyclerAdapter<HotCourseResponse> {

    public RecordCourseAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    public RecordCourseAdapter(Context context, List<HotCourseResponse> list, ViewHolder.OnItemClickListener onItemClickListener) {
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
        holder.setText(R.id.btnPay, "进入学习");
        holder.setOnClickListener(R.id.btnPay, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseAct.startCourseAct(context, hotCourse.getCourseCode());
            }
        });
    }
}
