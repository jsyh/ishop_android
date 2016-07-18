package com.jsyh.xjd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.jsyh.shopping.uilibrary.adapter.DemoPagerAdapter;
import com.jsyh.shopping.uilibrary.views.CircleIndicator;
import com.jsyh.shopping.uilibrary.views.ImageFragment;
import com.jsyh.xjd.R;
import com.jsyh.xjd.utils.ResourcesUtil;

/**
 * 引导页
 * Created by sks on 2015/12/4.
 */
public class Guide extends FragmentActivity implements ImageFragment.IntentBack {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        ViewPager defaultViewpager = (ViewPager) findViewById(R.id.viewpager_default);
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);

        //在这里获取所有的图片

        String[] guideImages = getResources().getStringArray(R.array.guide_images);
        int[] idRes = new int[guideImages.length];
        for (int i = 0; i < guideImages.length; i++) {
            idRes[i] = ResourcesUtil.getMipmapId(this, guideImages[i]);
        }


        DemoPagerAdapter defaultPagerAdapter = new DemoPagerAdapter(getSupportFragmentManager(),idRes, this);
        defaultViewpager.setAdapter(defaultPagerAdapter);
        defaultIndicator.setViewPager(defaultViewpager);
    }

    @Override
    public void getIntentCallBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
