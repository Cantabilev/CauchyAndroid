package com.you.cauchy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.you.cauchy.R;
import com.you.cauchy.entity.ExpectChildBean;
import com.you.cauchy.entity.ExpectGroupBean;

import java.util.List;

public class ExpectAdapter extends BaseExpandableRecyclerViewAdapter<ExpectGroupBean, ExpectChildBean, ExpectAdapter.GroupVH, ExpectAdapter.ChildVH> {

    private List<ExpectGroupBean> mList;

    public ExpectAdapter() {
    }

    public ExpectAdapter(List<ExpectGroupBean> list) {
        mList = list;
    }

    public List<ExpectGroupBean> getmList() {
        return mList;
    }

    public void setmList(List<ExpectGroupBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public ExpectGroupBean getGroupItem(int position) {
        return mList.get(position);
    }

    @Override
    public GroupVH onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
        return new GroupVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_expect_group, parent, false));
    }

    @Override
    public ChildVH onCreateChildViewHolder(ViewGroup parent, int childViewType) {
        return new ChildVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_expect_child, parent, false));
    }

    @Override
    public void onBindGroupViewHolder(GroupVH holder, ExpectGroupBean sampleGroupBean, boolean isExpanding) {
        holder.nameTv.setText(sampleGroupBean.getName());
        if (sampleGroupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBindChildViewHolder(ChildVH holder, ExpectGroupBean groupBean, ExpectChildBean sampleChildBean) {
        holder.tvExpectName.setText(sampleChildBean.getExpectName());
        holder.tvExpectValue.setText(sampleChildBean.getExpectValue());
    }

    static class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView foldIv;
        TextView nameTv;

        GroupVH(View itemView) {
            super(itemView);
            foldIv = (ImageView) itemView.findViewById(R.id.groupItemIndicator);
            nameTv = (TextView) itemView.findViewById(R.id.groupItemName);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
            foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        }
    }

    static class ChildVH extends RecyclerView.ViewHolder {
        TextView tvExpectName, tvExpectValue;

        ChildVH(View itemView) {
            super(itemView);
            tvExpectName = (TextView) itemView.findViewById(R.id.tvExpectName);
            tvExpectValue = (TextView) itemView.findViewById(R.id.tvExpectValue);
        }
    }
}
