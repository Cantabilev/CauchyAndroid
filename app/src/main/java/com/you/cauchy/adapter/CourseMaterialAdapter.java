package com.you.cauchy.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.constant.Constant;
import com.you.cauchy.net.response.CourseMaterialResponse;
import com.you.cauchy.net.response.ExaminationResponse;

import java.util.ArrayList;
import java.util.List;

public class CourseMaterialAdapter extends CommRecyclerAdapter<CourseMaterialResponse> {

    private CourseMaterialResponse material;

    public CourseMaterialAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_course_material;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void convertView(ViewHolder holder, CourseMaterialResponse courseMaterialResponse, int position) {

        // tvLabel      recyclerContent
        switch (position){
            case 0:
                holder.setText(R.id.tvLabel, "图片");
                fillContent(holder.getView(R.id.recyclerContent), material.getPicUrls(), Constant.MaterialType.PIC);
                break;
            case 1:
                holder.setText(R.id.tvLabel, "视频");
                fillContent(holder.getView(R.id.recyclerContent), material.getVideoUrls(), Constant.MaterialType.VIDEO);
                break;
            case 2:
                holder.setText(R.id.tvLabel, "测试");
                fillContent(holder.getView(R.id.recyclerContent), material.getExaminations(), Constant.MaterialType.EXAM);
                break;
            default:
                break;
        }
    }

    public void setMaterial(CourseMaterialResponse material) {
        this.material = material;
    }

    private <T> void  fillContent(RecyclerView recyclerView, List<T> urls, Constant.MaterialType materialType){
        if (urls == null || urls.size() == 0){
            recyclerView.setVisibility(View.GONE);
            return;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, materialType == Constant.MaterialType.PIC ? 3 : 1);
        CourseMaterialImageAdapter imageAdapter = new CourseMaterialImageAdapter<T>(context, materialType, urls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        imageAdapter.notifyDataSetChanged();
    }
}
