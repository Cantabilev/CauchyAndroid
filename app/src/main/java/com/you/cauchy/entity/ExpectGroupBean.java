package com.you.cauchy.entity;

import android.support.annotation.NonNull;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;

import java.util.List;

import lombok.Data;

@Data
public class ExpectGroupBean  implements BaseExpandableRecyclerViewAdapter.BaseGroupBean<ExpectChildBean> {

    private List<ExpectChildBean> list;
    private String name;

    public ExpectGroupBean(List<ExpectChildBean> list, String name) {
        this.list = list;
        this.name = name;
    }

    @Override
    public int getChildCount() {
        return list.size();
    }

    @Override
    public boolean isExpandable() {
        return getChildCount() > 0;
    }

    @Override
    public ExpectChildBean getChildAt(int index) {
        return list.size() <= index ? null : list.get(index);
    }
}
