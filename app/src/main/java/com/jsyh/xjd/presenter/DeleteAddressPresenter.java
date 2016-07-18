package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.AddressView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/10/10.
 * 删除地址网络请求
 */
public class DeleteAddressPresenter extends BasePresenter {
    private AddressView addressView;
    public DeleteAddressPresenter(AddressView addressView){
        this.addressView = addressView;
    }
    public void loadDelete(final Context context,String id,String action){
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("goods", action);
        params.put("key", ConfigValue.DATA_KEY);
        params.put("address_id",id);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "goods/" + action,
                params, new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        mLoadingDialog.dismiss();
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        mLoadingDialog.dismiss();
                        addressView.delete(response);
                    }
                },true);
    }
}
