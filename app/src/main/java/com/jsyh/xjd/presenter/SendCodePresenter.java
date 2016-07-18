package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.SendCodeView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/9/29.
 * 发送验证码
 */
public class SendCodePresenter extends BasePresenter {
    private SendCodeView sendCodeView;

    public SendCodePresenter(SendCodeView sendCodeView) {
        this.sendCodeView = sendCodeView;
    }


    public void delCode(final Context context, String username) {
        Map<String, String> params = getDefaultMD5Params("user", "del_code");
        params.put("username", username);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/del_code",
                params, new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        sendCodeView.delCode(response);
                    }
                }, true);
    }


    public void setSendCodeView(final Context context, int type, String name) {
        Map<String, String> params = getDefaultMD5Params("user", "send");
        params.put("type", type+"");
        params.put("mobile", name);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/send",
                params, new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        sendCodeView.getCode(response);
                    }
                }, true);
    }

    //验证验证码

    public void checkCode(final Context context, String code, String mobile) {
        Map<String, String> map = getDefaultMD5Params("user", "check_code");
        map.put("code", code);
        map.put("mobile", mobile);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/check_code",
                map, new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        sendCodeView.checkCode(response);
                    }
                }, true);
    }

}
