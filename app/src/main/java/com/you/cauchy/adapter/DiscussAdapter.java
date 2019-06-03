package com.you.cauchy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.net.AbsCustomTask;
import com.you.cauchy.net.RestCallback;
import com.you.cauchy.net.RestResult;
import com.you.cauchy.net.course.BuyCourseTask;
import com.you.cauchy.net.response.DiscussResponse;
import com.you.cauchy.net.response.HotCourseResponse;
import com.you.cauchy.ui.CommonDialog;
import com.you.cauchy.util.TimeUtils;
import com.you.cauchy.util.ToastUtil;

import java.util.List;

public class DiscussAdapter extends CommRecyclerAdapter<DiscussResponse> {

    public DiscussAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    public DiscussAdapter(Context context, List<DiscussResponse> list, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, list, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_discuss;
    }

    @Override
    public void convertView(ViewHolder holder, DiscussResponse discuss, int position) {
        ImageView iconShow = holder.getView(R.id.iconAvatar);
        Glide.with(context.getApplicationContext())
                .load(discuss.getUserAvatar())
                .error(Glide.with(context.getApplicationContext()).load(R.drawable.ic_error))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(iconShow);
        holder.setText(R.id.tvFlow, (position + 1) + "楼" );
        holder.setText(R.id.tvName, discuss.getUserName());
        holder.setText(R.id.tvTime, TimeUtils.instance(context).buildTimeString(discuss.getCreateTime().getTime()));
        holder.setText(R.id.tvContent, discuss.getContent());
    }

}
