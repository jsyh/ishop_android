package com.jsyh.xjd.activity.me;

import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.utils.Utils;

/**
 * Created by Su on 2016/5/5.
 */
public class MyShareActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_my_share);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("我的分享");
        findViewById(R.id.fl_Left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.finishActivityWithAnimation(MyShareActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
