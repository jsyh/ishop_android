package com.jsyh.xjd.activity.me;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.OrderViewPagerAdapter;
import com.jsyh.xjd.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2015/9/29.
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private int defaultPosition;


    @Override
    public void initData() {
        super.initData();
        defaultPosition = getIntent().getIntExtra("position", 0);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_myorder);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPagerOrder);
        List<String> mStringList = new ArrayList<>();
        mStringList.add("全部");
        mStringList.add("待付款");
        mStringList.add("待发货");
        mStringList.add("待收货");
        mStringList.add("待评价");

       final OrderViewPagerAdapter viewPagerAdapter = new OrderViewPagerAdapter(getSupportFragmentManager(),mStringList);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(defaultPosition);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("我的订单");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.finishActivityWithAnimation(this);
    }
}
