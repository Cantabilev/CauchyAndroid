package com.you.cauchy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.you.cauchy.R;
import com.you.cauchy.activity.comm.ImageDetailsAct;
import com.you.cauchy.activity.exam.ExamAct;
import com.you.cauchy.constant.Constant;
import com.you.cauchy.engine.SampleListener;
import com.you.cauchy.net.response.ExaminationResponse;
import com.you.cauchy.util.ScreenUtil;
import com.you.cauchy.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class  CourseMaterialImageAdapter<T> extends RecyclerView.Adapter<CourseMaterialImageAdapter.ViewHolder> {

    private final static String TAG = "ImageAdapter";

    private List<T> mData;
    private Context mContext;
    private Constant.MaterialType materialType;
    private GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public CourseMaterialImageAdapter(Context context, Constant.MaterialType materialType, List<T> resData) {
        this.materialType = materialType;
        this.mData = resData;
        this.mContext = context;
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_material_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        switch (materialType){
            case PIC:
                setImgSize((List<String>) mData, mContext, viewHolder.norImg);
                break;
            case VIDEO:break;
            case EXAM:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (materialType){
            case PIC:
                holder.norImg.setVisibility(View.VISIBLE);
                holder.player.setVisibility(View.GONE);
                holder.tvExam.setVisibility(View.GONE);
                fillImageList(position, holder.norImg);
                break;
            case VIDEO:
                holder.norImg.setVisibility(View.GONE);
                holder.player.setVisibility(View.VISIBLE);
                holder.tvExam.setVisibility(View.GONE);
                loadVideo(holder, position);
                break;
            case EXAM:
                holder.norImg.setVisibility(View.GONE);
                holder.player.setVisibility(View.GONE);
                holder.tvExam.setVisibility(View.VISIBLE);
                loadExam(holder, position);
                break;
        }
    }

    private void loadVideo(final ViewHolder holder, int position) {
        String url = mData.get(position).toString();
        String title = "课程学习";
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(url)
                .setVideoTitle(title)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!holder.player.isIfCurrentIsFullscreen()) {
                            GSYVideoManager.instance().setNeedMute(false);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                }).build(holder.player);

        //增加title
        holder.player.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        holder.player.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        holder.player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.player.startWindowFullscreen(mContext, true, true);
            }
        });
    }

    private void loadExam(ViewHolder holder, int position) {
        ExaminationResponse exam = (ExaminationResponse) mData.get(position);
        holder.tvExam.setText(exam.getTitle());
        holder.tvExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamAct.startExamAct(mContext, exam.getId(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<T> data) {
        this.mData = data;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView norImg;
        StandardGSYVideoPlayer player;
        TextView tvExam;
        ViewHolder(View itemView) {
            super(itemView);
            norImg = (ImageView) itemView.findViewById(R.id.norImg);
            player = (StandardGSYVideoPlayer) itemView.findViewById(R.id.gsy_player);
            tvExam = itemView.findViewById(R.id.tvExam);
        }
    }

    private void fillImageList(int position, ImageView norImg){
        Glide.with(mContext)
                .load(mData.get(position))
                .error(Glide.with(mContext).load(R.drawable.ic_error))
                .into(norImg);
        norImg.setOnClickListener(v -> ImageDetailsAct.startImageDetailAct(mContext, (ArrayList<String>) mData, position));
    }

    /**
     * 根据图片的数量，设置不同的尺寸
     *
     * @param datas
     * @param context
     * @param norImg
     */
    private static void setImgSize(List<String> datas, Context context, ImageView norImg) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        if (datas.size() == 1) {
            setSingleImgSize(context, norImg);
        } else if (datas.size() == 2 || datas.size() == 4) {
            setDoubleImgSize(context, norImg);
        } else if (datas.size() == 3 || datas.size() >= 5) {
            setThreeImgSize(context, norImg);
        }
    }

    private static void setDoubleImgSize(Context context, ImageView norImg) {
        FrameLayout.LayoutParams norImgLayout = (FrameLayout.LayoutParams) norImg.getLayoutParams();
        norImgLayout.width = ScreenUtil.getScreenWidth(context) / 2;
        norImgLayout.height = ScreenUtil.getScreenWidth(context) / 2;
    }

    private static void setSingleImgSize(Context context, ImageView norImg) {
        FrameLayout.LayoutParams norImgLayout = (FrameLayout.LayoutParams) norImg.getLayoutParams();
        norImgLayout.width = ScreenUtil.getScreenWidth(context);
        norImgLayout.height = (int) (ScreenUtil.getScreenWidth(context) * 0.7);
    }

    private static void setThreeImgSize(Context context, ImageView norImg) {
        FrameLayout.LayoutParams norImgLayout = (FrameLayout.LayoutParams) norImg.getLayoutParams();
        norImgLayout.width = ScreenUtil.getScreenWidth(context) / 3;
        norImgLayout.height = ScreenUtil.getScreenWidth(context) / 3;
    }
}
