package com.jsyh.shopping.uilibrary.bannerview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jsyh.shopping.uilibrary.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BannerView extends RelativeLayout {
    private ViewPager viewpager;
    private BannerDrawable bannerDrawable;
    private ImageView imageView;
    private ScheduledExecutorService scheduledExecutorService;
    private AdvAdapter adapter;
    public static final int STYLE_Circular = 0;
    public static final int STYLE_Dot = 1;
    public static final int MODE_LEFT = 0;
    public static final int MODE_CENTER = 1;
    public static final int MODE_RIGHT = 2;

    public BannerView(Context context) {
        // TODO Auto-generated constructor stub
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        // TODO Auto-generated constructor stub
        this(context, null, -1);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public void setDrawerMode(int drawerMode) {
        switch (drawerMode) {
            case STYLE_Circular:
                bannerDrawable = new CircularDrawable(getContext(), adapter.getItemSize(), viewpager.getCurrentItem() % adapter.getItemSize());
                break;
            case STYLE_Dot:
                bannerDrawable = new DotDrawable(getContext(), adapter.getItemSize(), viewpager.getCurrentItem() % adapter.getItemSize());
                break;
            default:
                break;
        }
        imageView.setBackgroundDrawable(bannerDrawable);
    }

    /**
     * @param locationMode
     */
    public void setLocationMode(int locationMode) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (locationMode) {
            case MODE_CENTER:
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case MODE_RIGHT:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(dip2px(getContext(), 5), dip2px(getContext(), 5), dip2px(getContext(), 20), dip2px(getContext(), 16));
                break;
            case MODE_LEFT:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(dip2px(getContext(), 5), dip2px(getContext(), 5), dip2px(getContext(), 5), dip2px(getContext(), 5));
                break;
            default:
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                break;
        }
        this.imageView.setLayoutParams(layoutParams);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        View baseView = View.inflate(context, R.layout.adv_view, this);
        viewpager = (ViewPager) baseView.findViewById(R.id.viewpager);
        adapter = new AdvAdapter(context);
        viewpager.setAdapter(adapter);
        imageView = (ImageView) baseView.findViewById(R.id.imageView);
        // bannerDrawable = new CircularDrawable(context, adapter.getItemSize(),
        // viewpager.getCurrentItem() % adapter.getItemSize());
        bannerDrawable = new CircularDrawable(getContext(), adapter.getItemSize(), viewpager.getCurrentItem() % adapter.getItemSize());
//        bannerDrawable = new DotDrawable(context, adapter.getItemSize(), viewpager.getCurrentItem() % adapter.getItemSize());
        imageView.setBackgroundDrawable(bannerDrawable);
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                bannerDrawable.setText(adapter.getItemSize(), arg0 % adapter.getItemSize());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        startAd();
    }

    private void startAd() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
    }

    private class ScrollTask implements Runnable {
        @Override
        public void run() {
            synchronized (viewpager) {
                handler.sendEmptyMessage(0);
            }
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
        }

        ;
    };

    public void initAdapterData(List<String> imageFilePath) {
        adapter.initData(imageFilePath);
    }

    public void initAdapterData(String[] imageFilePath) {
        if(imageFilePath.length <= 1)
            imageView.setVisibility(View.GONE);
        adapter.initData(Arrays.asList(imageFilePath));
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale - 0.5f);
    }
}
