package com.you.cauchy.adapter.wrapper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 常规 RecyclerView 适配器
 * @author Created by You
 * @email 1269859055@qq.com
 * @data 2018/8/14
 **/
public abstract class CommRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context context;
    protected List<T> mList;
    private ViewHolder.OnItemClickListener onItemClickListener;

    public CommRecyclerAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public CommRecyclerAdapter(Context context, List<T> mList, ViewHolder.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mList = mList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(context, LayoutInflater.from(context).inflate(getLayoutId(viewType), parent, false));
        holder.setOnItemClickListener(onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T t;
        if (null == mList || mList.size() -1 < position)
            t = null;
        else
            t = mList.get(position);
        convertView(holder, t, position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
    }

    public List<T> getmList() {
        return mList;
    }

    public void add(T t){
        if (null == this.mList)
            this.mList = new ArrayList<>();
        mList.add(t);
        notifyItemInserted(mList.indexOf(t));
    }
    public void addAll(List<T> list){
        if (null == this.mList)
            this.mList = list;
        else
            this.mList.addAll(list);
        notifyItemRangeInserted(this.mList.size() - list.size(), list.size());
    }
    public void remove(T t){
        if (null == this.mList)
            return;
        int pos = mList.indexOf(t);
        if(-1 == pos)
            return;
        this.mList.remove(pos);
        notifyItemRemoved(pos);
    }
    public void remove(int index){
        if (null == this.mList || this.mList.size()-1 > index)
            throw new RuntimeException("there target mList is null or index out of bounds");
        this.mList.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * 获取资源视图
     * @return
     */
    public abstract int getLayoutId(int viewType);

    public abstract void convertView(ViewHolder holder, T t, int position);
}
