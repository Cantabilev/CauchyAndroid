package com.you.cauchy.adapter;

import android.content.Context;

import com.you.cauchy.R;
import com.you.cauchy.adapter.wrapper.CommRecyclerAdapter;
import com.you.cauchy.adapter.wrapper.ViewHolder;
import com.you.cauchy.constant.Constant;
import com.you.cauchy.net.response.MoneyDetailResponse;
import com.you.cauchy.util.DateUtil;


public class MoneyFlowingAdapter extends CommRecyclerAdapter<MoneyDetailResponse.MoneyFlowing> {

    public MoneyFlowingAdapter(Context context, ViewHolder.OnItemClickListener onItemClickListener) {
        super(context, onItemClickListener);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_money_flowing;
    }

    @Override
    public void convertView(ViewHolder holder, MoneyDetailResponse.MoneyFlowing moneyFlowing, int position) {
        holder.setText(R.id.tvId, moneyFlowing.getId() + "");
        holder.setText(R.id.tvTime, DateUtil.getDateString(moneyFlowing.getCreateTime()));
        holder.setText(R.id.tvCategory, Constant.moneyCategoryMap.get(moneyFlowing.getCategory()));
        String amount;
        // 根据种类设置 amount 颜色
        if ("consume".equals(moneyFlowing.getCategory())){
            amount = "-" + moneyFlowing.getAmount().intValue();
            holder.setTextColorRes(R.id.tvAmount, R.color.colorRed);
        } else {
            amount = "+" + moneyFlowing.getAmount().intValue();
            holder.setTextColorRes(R.id.tvAmount, R.color.colorPrimary);
        }
        holder.setText(R.id.tvAmount, amount);
    }
}
