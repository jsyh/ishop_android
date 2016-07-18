package com.jsyh.xjd.wxapi;

import android.content.Context;

import com.jsyh.xjd.R;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Su on 2016/1/18.
 */
public class WXPay {

    private Context mContext;

    private IWXAPI api;

    public WXPay(Context mContext) {
        this.mContext = mContext;
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(mContext, mContext.getResources().getString(R.string.WX_APP_ID), false);
    }

    // 将该app注册到微信

    public void regApp() {
        //api.registerApp(Constants.APP_ID);
        api.registerApp(mContext.getResources().getString(R.string.WX_APP_ID));
    }

    //检查微信版本是否支持支付

    public boolean checkWXVerison() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    public void pay(PayReq req) {
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

}
