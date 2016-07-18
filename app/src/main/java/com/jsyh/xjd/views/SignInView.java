package com.jsyh.xjd.views;

import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.SignInModel;

/**
 * 签到需要实现的方法
 * Created by Su on 2016/6/21.
 */
public interface SignInView {
    //签到成功与失败
    void onSuccess(BaseModel baseModel);
    void onFailure(Exception e);

    void onSignInRecordResponse(SignInModel signInModel);
    void onSignInRecordFailure(Exception e);
}
