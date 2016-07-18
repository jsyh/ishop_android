package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.ForgetView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/9/30.
 * 找回密码信息提交
 */
public class ForgetPresenter extends BasePresenter {

    private ForgetView forgetView;

    public ForgetPresenter(ForgetView forgetView){
        this.forgetView = forgetView;
    }

    public void submitData(final Context context,String code,String name,String password){
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String,String> params = getDefaultMD5Params("user","password");
        params.put("username",name);
        params.put("passwd",password);
        params.put("code",code);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/password",
                params, new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        mLoadingDialog.dismiss();
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        mLoadingDialog.dismiss();
                        forgetView.getData(response);
                    }
                },true);
    }
}
