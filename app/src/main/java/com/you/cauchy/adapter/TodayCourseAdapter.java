package com.you.cauchy.adapter;

import android.content.Context;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.response.CourseInfoResponse;
import com.you.cauchy.util.DateUtil;

import java.util.List;

public class TodayCourseAdapter extends CommRecyclerAdapter<CourseInfoResponse> {

    public TodayCourseAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    public TodayCourseAdapter(Context context, List<CourseInfoResponse> list, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, list, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_center_tv;
    }

    @Override
    public void convertView(ViewHolder holder, CourseInfoResponse course, int position) {
        holder.setText(R.id.tvText, DateUtil.getTimeString(course.getStartTime()) + " " + course.getCourseName());
    }
}
