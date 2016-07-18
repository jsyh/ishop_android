package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.ReturnGoodsModel;
import com.jsyh.xjd.views.ReturnView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by Su on 2016/5/10.
 */
public class ReturnPresenter extends BasePresenter {
    private ReturnView mReturnView;

    public ReturnPresenter(ReturnView returnView) {
        mReturnView = returnView;
    }

    //提交退货申请
    public void submitApply(Context context, String orderId, String goodsId, String postscript, String back_reason, String money, int type) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("user", "back_goods");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("order_id", orderId != null ? orderId : "");
        params.put("goods_id", goodsId != null ? goodsId : "");
        params.put("postscript", postscript != null ? postscript : "");
        params.put("back_reason", back_reason != null ? back_reason : "");
        params.put("money", money != null ? money : "");
        params.put("type", type + "");

        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/back_goods", params, new BaseDelegate.ResultCallback<BaseModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                mReturnView.onError(e.getMessage());
                mLoadingDialog.dismiss();
            }

            @Override
            public void onResponse(BaseModel response, Object tag) {
                mReturnView.onApplyResponse(response);
                mLoadingDialog.dismiss();
            }
        });
    }

    //获取退货申请列表
    public void getReturnGoodsList(Context context) {
        Map<String, String> params = getDefaultMD5Params("user", "back_order");
        params.put("key", ConfigValue.DATA_KEY);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/back_order", params, new BaseDelegate.ResultCallback<ReturnGoodsModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                mReturnView.onError(e.getMessage());
            }

            @Override
            public void onResponse(ReturnGoodsModel response, Object tag) {
                mReturnView.onGetResponse(response);
            }
        });
    }

    //取消申请退货
    public void cancelApplyReturn(Context context, String back_id) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("user", "unapply");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("back_id", back_id != null ? back_id : "");
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/unapply", params, new BaseDelegate.ResultCallback<BaseModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                mLoadingDialog.dismiss();
                mReturnView.onError(e.getMessage());
            }

            @Override
            public void onResponse(BaseModel response, Object tag) {
                mLoadingDialog.dismiss();
                mReturnView.onApplyResponse(response);
            }
        });

    }
}
