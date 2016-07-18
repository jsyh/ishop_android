package com.jsyh.shopping.uilibrary.bannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告显示
 *
 * @author Administrator
 */
public class AdvAdapter extends PagerAdapter {
    private Context context;
    List<String> imageFilePath = new ArrayList<String>();
    private Map<Integer, ImageView> imageViewMap = new HashMap<Integer, ImageView>();
    private List<String> urls = new ArrayList<String>();

    public AdvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        if (imageFilePath.size() >= 2) {
            return Integer.MAX_VALUE;
        } else {
            return imageFilePath.size() == 0 ? 1 : imageFilePath.size();
        }
    }

    public int getItemSize() {
        return imageFilePath.size() == 0 ? 1 : imageFilePath.size();
    }

    /**
     * 复用对象. true 复用对象 false 用的是object
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁对象
     *
     * @param position 将要被销毁对象的索引位置
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = imageViewMap.remove(position);
        container.removeView(imageView);
        imageView = null;

    }

    /**
     * 初始化一个view对象
     *
     * @param position 将要被创建的对象的索引位置
     */
    @SuppressLint("NewApi")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 先把对象添加到viewpager中, 再返回当前对象
        ImageView imageView = new ImageView(context);
        if (imageFilePath.size() > 0) {
            int index = position % imageFilePath.size();
            //Picasso.with(context).load(imageFilePath.get(index)).resize(container.getMeasuredWidth(), container.getMeasuredHeight()).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(context).load(imageFilePath.get(index)).into(imageView);
        }
        container.addView(imageView);
        imageViewMap.put(position, imageView);
        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void initData(List<String> imageFilePath) {
        // TODO Auto-generated method stub
        this.imageFilePath.clear();
        this.imageFilePath.addAll(imageFilePath);
        notifyDataSetChanged();
    }

}
