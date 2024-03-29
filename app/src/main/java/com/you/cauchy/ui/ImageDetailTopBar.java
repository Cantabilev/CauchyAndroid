package com.you.cauchy.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.you.cauchy.R;

public class ImageDetailTopBar extends RelativeLayout {

    private View mView;
    private TextView mPageNum;

    /**
     * @param context 上下文
     * @param attrs   用户获取自定义view的属性
     */
    public ImageDetailTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.act_image_detail_list_top_bar, this);
        mPageNum = (TextView) mView.findViewById(R.id.pageTextId);
    }


    /**
     * 设置当前图片的页数
     *
     * @param pageNum
     */
    public void setPageNum(String pageNum) {
        mPageNum.setText(pageNum);
    }

    /**
     * 设置是否显示当前页数，为1时不需要显示
     *
     * @param isVisible
     */
    public void setPageNumVisible(int isVisible) {
        mPageNum.setVisibility(isVisible);
    }

    /**
     * 定义了一个监听接口，用于监听点击More Options事件
     */
    public interface OnMoreOptionsListener {
        void onClick(View view);
    }

}
