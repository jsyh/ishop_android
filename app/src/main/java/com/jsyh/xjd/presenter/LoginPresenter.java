package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.LoginModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.LoginView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/31.
 */
public class LoginPresenter extends BasePresenter {
    private LoginView loginView;
    protected Context context;// 上下文

    //    protected RequestQueue mQueue;// 网络请求队列
    public LoginPresenter(LoginView loginView) {
//        this.mQueue = Volley.newRequestQueue(context);
        this.loginView = loginView;
    }

    public void loadLogin(final Context context, String name, String password) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("user", "passwd");
        params.put("user", name);
        params.put("passwd", password);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP
                + "user/login", params, new BaseDelegate.ResultCallback<LoginModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                mLoadingDialog.dismiss();
            }

            @Override
            public void onResponse(LoginModel response, Object tag) {
                loginView.login(response);
                mLoadingDialog.dismiss();
            }
        }, true);
    }

    //三方登录
    public void socialLogin(final Context context, String type, String uid, String nickname) {
        Map<String, String> params = getDefaultMD5Params("user", "auto_login");

        params.put("type", type);
        params.put("uid", uid);
        params.put("username", nickname);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/auto_login", params, new BaseDelegate.ResultCallback<LoginModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                loginView.error();
            }

            @Override
            public void onResponse(LoginModel response, Object tag) {
                loginView.login(response);
            }
        });

    }
}
