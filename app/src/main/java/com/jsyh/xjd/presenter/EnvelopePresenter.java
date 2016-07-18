package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BounsModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.EnvelopeView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/10/12.
 * 获取红包列表
 */
public class EnvelopePresenter extends BasePresenter {
    private EnvelopeView envelopeView;
    public EnvelopePresenter(EnvelopeView envelopeView){
        this.envelopeView = envelopeView;
    }

    /**
     *
     * @param context
     * @param money   订单的总价格
     */
    public void request(final Context context,String money){
        Map<String, String> params = getDefaultMD5Params("order", "bouns");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("money",money);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "order/bouns",
                params, new BaseDelegate.ResultCallback<BounsModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(BounsModel response, Object tag) {
                        envelopeView.result(response);
                    }
                },true);
    }
}
