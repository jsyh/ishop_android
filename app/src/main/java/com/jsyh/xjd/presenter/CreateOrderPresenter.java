package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.CreateOrderModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CreateOrderView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/10/8.
 * 获取新的订单信息
 */
public class CreateOrderPresenter extends BasePresenter {
    private CreateOrderView createOrderView;

    public CreateOrderPresenter(CreateOrderView createOrderView) {
        this.createOrderView = createOrderView;
    }

    /**
     * @param type         来源（0加入购物车商品购买，1积分兑换,2直接购买）
     * @param points       对该商品需要多少积分（只针对积分兑换）
     * @param pay_points   对换该积分使用的积分
     * @param goods_number 商品个数（仅当type=2时使用）
     * @param attr_id      属性id仅当type为1 和2的时候）格式（1,2,3）字符串
     * @param goods_id     商品id(传值格式：“1,2,3”
     */

    public void loadOrderInfor(final Context context, int type, String points, String pay_points, String goods_number, String attr_id,
                               String goods_id) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("order", "confirm");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("type", type + "");
        params.put("points", points != null ? points : "");
        params.put("pay_points", pay_points != null ? pay_points : "");
        params.put("goods_number", goods_number != null ? goods_number : "");
        params.put("attr_id", attr_id != null ? attr_id.substring(1, attr_id.length() - 1) : "");
        params.put("goods_id", goods_id != null ? goods_id : "");
//        params.put("number", number);


        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "order/confirm",
                params, new BaseDelegate.ResultCallback<CreateOrderModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        mLoadingDialog.dismiss();
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(CreateOrderModel response, Object tag) {
                        mLoadingDialog.dismiss();
                        createOrderView.getOrderInfor(response);
                    }
                }, true);
    }
}
