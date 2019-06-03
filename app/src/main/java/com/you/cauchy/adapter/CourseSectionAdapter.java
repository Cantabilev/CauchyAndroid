package com.you.cauchy.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.response.CourseInfoInsResponse;

public class CourseSectionAdapter extends CommRecyclerAdapter<CourseInfoInsResponse> {

    private Boolean buy;

    public CourseSectionAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_course_section;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    @Override
    public void convertView(ViewHolder holder, CourseInfoInsResponse section, int position) {
        holder.setText(R.id.tvTitle, section.getCourseTitle());

        ConstraintLayout cl = holder.getView(R.id.container);
        if (buy){
            holder.setVisible(R.id.iconLock, false);
        } else {
            cl.setEnabled(false);
        }
    }
}
