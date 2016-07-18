package com.jsyh.xjd.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private TextView mTextView;

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_pay_result);
        mTextView = (TextView) findViewById(R.id.tv);*/
        api = WXAPIFactory.createWXAPI(this, getResources().getString(R.string.WX_APP_ID));
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        //Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        /**
         *  0  成功	展示成功页面
         -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
         -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
         */
        //mTextView.setText("支付结果为：" + String.valueOf(resp.errCode));
        EventBus.getDefault().post(new WXPayResult(String.valueOf(resp.errCode)));
        finish();
    }
}