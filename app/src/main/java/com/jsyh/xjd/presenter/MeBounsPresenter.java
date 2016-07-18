package com.jsyh.xjd.presenter;

import android.content.Context;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.Bouns_meModel;
import com.jsyh.xjd.views.MeBounsView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * 获取我的红包
 * Created by gxc on 2016/3/1.
 */
public class MeBounsPresenter extends BasePresenter {

    private MeBounsView meBounsView;

    public MeBounsPresenter(MeBounsView meBounsView) {
        this.meBounsView = meBounsView;
    }

    public void loadBouns(Context context) {
        Map<String, String> params = getDefaultMD5Params("user", "bonus");
        params.put("key", ConfigValue.DATA_KEY);
        OkHttpClientManager.postAsyn(context, ConfigValue.APP_IP + "user/bonus", params,
                new BaseDelegate.ResultCallback<Bouns_meModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        dismiss();
                    }

                    @Override
                    public void onResponse(Bouns_meModel response, Object tag) {
                        meBounsView.getBouns(response);
                    }
                }, true);
    }
}
