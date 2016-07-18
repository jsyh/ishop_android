package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.SubmitOrderModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CreateOrderView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/10/12.
 */
public class SubmitOrderPresenter extends BasePresenter {
    private CreateOrderView createOrderView;

    public SubmitOrderPresenter(CreateOrderView createOrderView) {
        this.createOrderView = createOrderView;
    }

    /**
     * @param context
     * @param goods_id
     * @param address_id
     * @param integral
     * @param integral_money
     * @param attr_id
     * @param amount
     * @param money_paid
     * @param shipping_fee
     * @param expressage_id
     * @param bonus_id
     * @param goods_number
     * @param type
     * @param message
     * @param give_integral
     */
    public void request(final Context context, String goods_id, String address_id, String integral, String integral_money, String attr_id, String amount, String money_paid,
                        String shipping_fee, String expressage_id, String bonus_id, String goods_number, int type, String message, String give_integral) {
        Map<String, String> params = getDefaultMD5Params("order", "create");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("goods_id", goods_id != null ? goods_id : "");
        params.put("address_id", address_id != null ? address_id : "");
        params.put("integral", integral != null ? integral : "");
        params.put("integral_money", integral_money != null ? integral_money : "");
        params.put("attr_id", attr_id != null ? attr_id.substring(1, attr_id.length() - 1) : "");//立即购买时需要传
        params.put("amount", amount != null ? amount : "");
        params.put("money_paid", money_paid != null ? money_paid : "");

        params.put("shipping_fee", shipping_fee != null ? shipping_fee : "");//立即购买时需要传
        params.put("expressage_id", expressage_id != null ? expressage_id : "");
        params.put("bonus_id", bonus_id != null ? bonus_id : "");
        params.put("goods_number", goods_number != null ? goods_number : "");
        params.put("type", type + "");
        params.put("message", message != null ? message : "");
        params.put("give_integral", give_integral != null ? give_integral : "");
        initLoadDialog(context);
        mLoadingDialog.show();
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "order/create",
                params, new BaseDelegate.ResultCallback<SubmitOrderModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        mLoadingDialog.dismiss();
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(SubmitOrderModel response, Object tag) {
                        mLoadingDialog.dismiss();
                        createOrderView.result(response);
                    }
                }, true);
    }
}
