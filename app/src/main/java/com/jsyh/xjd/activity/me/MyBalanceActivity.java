package com.jsyh.xjd.activity.me;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.utils.Utils;

/**
 * Created by Su on 2016/4/26.
 */
public class MyBalanceActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_balance);
        findViewById(R.id.mButtonRecharge).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView) findViewById(R.id.mTextViewBalance)).setText(ConfigValue.uInfor.getUser_money());
    }

    @Override
    public void initTitle() {
        ((TextView) findViewById(R.id.title)).setText("我的余额");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mButtonRecharge:
                Utils.startActivityWithAnimation(this, new Intent(this, RechargeActivity.class));

                break;
        }
    }
}
