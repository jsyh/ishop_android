package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.okhttp.Request;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.Map;

/**
 * Created by Su on 2016/5/12.
 */
public class SubmitExpressPresenter extends BasePresenter {


    public void submitExpressPresenter(final Context context, String back_id, String shipping_no, String shipping_name) {
        initLoadDialog(context);
        mLoadingDialog.show();

        Map<String, String> params = getDefaultMD5Params("user", "subcode");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("back_id", back_id);
        params.put("shipping_no", shipping_no);
        params.put("shipping_name", shipping_name);

        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/subcode", params, new BaseDelegate.ResultCallback<BaseModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                mLoadingDialog.dismiss();
                ToastUtil.showToast(context, e.getMessage().toString());
            }

            @Override
            public void onResponse(BaseModel response, Object tag) {
                mLoadingDialog.dismiss();
                ToastUtil.showToast(context, response.getMsg());
                if (response.getCode().equals("1")) {
                    Utils.finishActivityWithAnimation(context);
                }
            }
        });
    }
}
