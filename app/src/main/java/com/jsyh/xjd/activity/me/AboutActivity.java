package com.jsyh.xjd.activity.me;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by sks on 2015/10/20.
 * 关于
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_about);
        findViewById(R.id.mTableRowVersion).setOnClickListener(this);
        findViewById(R.id.mTableRowHelp).setOnClickListener(this);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("关于");
        findViewById(R.id.fl_Left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.finishActivityWithAnimation(AboutActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTableRowVersion:
                ToastUtil.showToast(this,"当前为最新版本");
                break;
            case R.id.mTableRowHelp:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, FeedbackActivity.class));
                break;
        }
    }
}
