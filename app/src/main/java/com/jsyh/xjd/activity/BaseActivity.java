package com.jsyh.xjd.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.LoginActivity;
import com.jsyh.xjd.utils.Utils;

/**
 * Created by Administrator on 2015/8/31.
 */
public class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
        initView();
        initSwipeRefreshLayout();
        initTitle();
    }


    public void initData() {

    }

    public void initView() {

    }

    public void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
    }

    public void initTitle() {
        /*if(this instanceof MainActivity){

        }*/
        //应用中页面上方的titleBar并不是同一个布局文件 - -
        View view = findViewById(R.id.custom_title_bar);
        if (view != null)
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.nav_color));


    }

    public void itLogin(Context context) {
        Intent itLogin = new Intent(context, LoginActivity.class);
        Utils.startActivityWithAnimation(context,itLogin);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Utils.finishActivityWithAnimation(this);
    }
}
