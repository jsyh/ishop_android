package com.jsyh.xjd.activity.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.utils.CacheManager;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by su on 2015/9/16.
 */
public class SetupActicity extends BaseActivity implements View.OnClickListener {

    private TextView mTextViewCacheSize;

    @Override
    public void initView() {
        setContentView(R.layout.activity_setup);
        findViewById(R.id.mTableRowInfo).setOnClickListener(this);
        findViewById(R.id.mTableRowHelp).setOnClickListener(this);
        findViewById(R.id.mTableRowAbout).setOnClickListener(this);
        findViewById(R.id.mTableRowVersion).setOnClickListener(this);
        findViewById(R.id.mTableRowCache).setOnClickListener(this);
        findViewById(R.id.mButtonExit).setOnClickListener(this);

        mTextViewCacheSize = (TextView) findViewById(R.id.mTextViewCacheSize);
        count();
    }

    private void count() {
        String size = null;
        try {
            size = CacheManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTextViewCacheSize.setText(size);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mTableRowInfo:
                if (ConfigValue.uInfor != null)
                    Utils.startActivityWithAnimation(this, new Intent(this, MeAccountActivity.class));
                else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTableRowHelp:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, FeedbackActivity.class));
                break;
            //关于U
            case R.id.mTableRowAbout:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, AboutActivity.class));
                break;
            case R.id.mTableRowVersion:
                ToastUtil.showToast(this, "当前为最新版本");
                break;
            case R.id.mTableRowCache:
                // ToastUtil.showToast(this, "清除缓存");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                mBuilder.setMessage("清除所有缓存？").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheManager.clearAllCache(SetupActicity.this);
                        count();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
            case R.id.mButtonExit:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("退出当前账户？").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.remove(SetupActicity.this, SPConfig.KEY);
                        ConfigValue.uInfor = null;
                        Utils.finishActivityWithAnimation(SetupActicity.this);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
        }
    }

    @Override
    public void initTitle() {
        super.initTitle();
        findViewById(R.id.fl_Left).setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText("设置");
    }
}
