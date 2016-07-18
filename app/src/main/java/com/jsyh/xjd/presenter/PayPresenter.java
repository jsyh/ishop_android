package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.PayModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.PayView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/10/22.
 * 支付
 */
public class PayPresenter extends BasePresenter {

    private PayView payView;

    public PayPresenter(PayView payView) {
        this.payView = payView;
    }

    /**
     * @param context
     * @param type    //支付类型
     * @param orderId //订单id
     */
    public void setPay(final Context context, String type, String orderId) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("order", "pay");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("order_id", orderId);
        params.put("type", type);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "order/pay", params,
                new BaseDelegate.ResultCallback<PayModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        mLoadingDialog.dismiss();
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(PayModel response, Object tag) {
                        mLoadingDialog.dismiss();
                        payView.aliPayResponse(response);
                    }
                });
    }

    /**
     * 微信支付
     *
     * @param mContext
     * @param orderId  //订单id
     */
    public void wxPay(Context mContext,String type, String orderId) {

        Map<String, String> params = getDefaultMD5Params("order", "pay");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("order_id", orderId);
        params.put("type",type);
       // String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        //测试
        OkHttpClientManager.postAsyn(mContext, ConfigValue.APP_IP + "order/pay",params,new BaseDelegate.ResultCallback<String>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                payView.wxPayError(request,tag,e);
            }

            @Override
            public void onResponse(String response, Object tag) {
                payView.wxPayResponse(response);
            }
        });


    }

}
