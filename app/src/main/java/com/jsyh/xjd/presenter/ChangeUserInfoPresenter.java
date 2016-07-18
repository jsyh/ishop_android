package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.views.ChangeUserInfoView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * 提交用户信息
 * Created by sks on 2016/1/11.
 */
public class ChangeUserInfoPresenter extends BasePresenter {

    private ChangeUserInfoView changeUserInfoView;

    public ChangeUserInfoPresenter(ChangeUserInfoView changeUserInfoView) {
        this.changeUserInfoView = changeUserInfoView;
    }

    public void getUserInfo(Context context, String sex, String birthday, String nickname) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("User", "modifyUser");
        params.put("key", ConfigValue.DATA_KEY);
        params.put("sex", sex);
        params.put("birthday", birthday);
        params.put("nickname", nickname);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "User/modifyUser",
                params, new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        dismiss();
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        dismiss();
                        changeUserInfoView.result(response);
                    }
                }, true);
    }
}
