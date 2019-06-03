package com.you.cauchy.adapter;

import android.content.Context;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.response.UserExamResponse;

public class UserExamListAdapter extends CommRecyclerAdapter<UserExamResponse> {

    public UserExamListAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_exam;
    }

    @Override
    public void convertView(ViewHolder holder, UserExamResponse userExam, int position) {
        holder.setText(R.id.tvTitle, userExam.getTitle());
        holder.setText(R.id.tvScore, userExam.getScore().toString());
        holder.setText(R.id.tvFullMark, userExam.getFullMark().toString());
    }
}
