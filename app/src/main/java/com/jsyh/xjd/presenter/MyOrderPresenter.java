package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.OrderModelInfo;
import com.jsyh.xjd.views.MyOrderView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/9/29.
 */
public class MyOrderPresenter extends BasePresenter {
    private MyOrderView myOrderView;

    public MyOrderPresenter(MyOrderView myOrderView) {
        this.myOrderView = myOrderView;
    }

    public void loadOrder(final Context context, final int type) {
        Map<String, String> params = getDefaultMD5Params("user", "order");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("type", type + "");
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/order",
                params, new BaseDelegate.ResultCallback<OrderModelInfo>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {

                    }

                    @Override
                    public void onResponse(OrderModelInfo response, Object tag) {
                        myOrderView.orderList(response);
                    }
                }

                , true);
    }
}
