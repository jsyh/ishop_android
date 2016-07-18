package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.UserInforModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.UserInforView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by sks on 2015/9/29.
 * 用户信息网络请求
 */
public class UserInforPresenter extends BasePresenter {
    private UserInforView userInforView;
    public UserInforPresenter(UserInforView userInforView){
        this.userInforView = userInforView;
    }
    public void loadInfor(final Context context){
        Map<String,String> params = getDefaultMD5Params("user","infomation");
        params.put("key", ConfigValue.DATA_KEY);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/userinfo", params,
                new BaseDelegate.ResultCallback<UserInforModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e,context));
                    }

                    @Override
                    public void onResponse(UserInforModel response, Object tag) {
                        userInforView.inforData(response);
                    }
                },true);
    }
}
