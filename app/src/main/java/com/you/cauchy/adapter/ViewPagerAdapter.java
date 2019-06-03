package com.you.cauchy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.you.cauchy.R;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<String> mDatas;
    private Context mContext;
    public OnSingleTagListener onSingleTagListener;

    public interface OnSingleTagListener {
        void onTag();
    }

    public void setOnSingleTagListener(OnSingleTagListener onSingleTagListener) {
        this.onSingleTagListener = onSingleTagListener;
    }


    public ViewPagerAdapter(ArrayList<String> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View mView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_imagedetails, null);


        final FrameLayout bgLayout = (FrameLayout) mView.findViewById(R.id.bgLayout);
        //预览缩略图的控件
        final PhotoView preNorImg = (PhotoView) mView.findViewById(R.id.previewImg);
        //显示原图的控件
        final PhotoView norImgView = (PhotoView) mView.findViewById(R.id.norImg);

        setOnClickListener(preNorImg, bgLayout, norImgView);

        Glide.with(mContext)
                .load(mDatas.get(position))
                .error(Glide.with(mContext.getApplicationContext()).load(R.drawable.ic_error))
                .into(norImgView);
        //new MyAsync(norImgView, bgLayout, preNorImg).execute();

        container.addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return mView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void displayNormalImg(Bitmap bitmap, PhotoView photoView) {
        photoView.setImageBitmap(bitmap);
    }

    private void hidePreviewImg(PhotoView norImg) {
        if (norImg.getVisibility() == View.VISIBLE){
            norImg.setVisibility(View.GONE);
        }
    }

    private void setOnClickListener(PhotoView preNorImg, FrameLayout bgLayout, PhotoView normalImg) {
        bgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSingleTagListener.onTag();
            }
        });
        preNorImg.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                onSingleTagListener.onTag();
            }

            @Override
            public void onOutsidePhotoTap() {
                onSingleTagListener.onTag();
            }
        });
        normalImg.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                onSingleTagListener.onTag();
            }

            @Override
            public void onOutsidePhotoTap() {
                onSingleTagListener.onTag();
            }
        });
    }

    class MyAsync extends AsyncTask<String, Void, File> {

        private PhotoView norImgView, preNorImg;
        private FrameLayout bgLayout;

        public MyAsync(PhotoView norImgView, FrameLayout bgLayout, PhotoView prenorImgView) {
            this.norImgView = norImgView;
            this.bgLayout = bgLayout;
            this.preNorImg = prenorImgView;
        }

        @Override
        protected File doInBackground(String... strings) {
            try {
                return Glide.with(mContext).load(strings[0]).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(File file) {
            hidePreviewImg(preNorImg);
            norImgView.setVisibility(View.VISIBLE);
            displayNormalImg(BitmapFactory.decodeFile(file.getAbsolutePath()), norImgView);
        }
    }
}
