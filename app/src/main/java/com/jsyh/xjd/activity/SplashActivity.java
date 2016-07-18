package com.jsyh.xjd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.jsyh.xjd.R;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.utils.SPUtils;

/**
 * 启动页
 * Created by sks on 2015/11/16.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        String flag = (String) SPUtils.get(this, SPConfig.FIRST, "");

        //是否有引导页
        boolean isGuidePage = getResources().getBoolean(R.bool.is_guide_images);

        if ((null != flag && !flag.equals("")) || !isGuidePage) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, 2900);
        } else {
            SPUtils.put(this, SPConfig.FIRST, "1");
            Intent intent = new Intent(this, Guide.class);
            startActivity(intent);
            finish();
        }
    }
}
