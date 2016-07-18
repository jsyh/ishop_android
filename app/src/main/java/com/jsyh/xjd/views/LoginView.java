package com.jsyh.xjd.views;

import com.jsyh.xjd.model.LoginModel;

/**
 * Created by Administrator on 2015/8/31.
 */
public interface LoginView {
    void login(LoginModel model);
    void error();
}
