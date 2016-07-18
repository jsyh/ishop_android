package com.jsyh.xjd.views;

import com.jsyh.xjd.model.BaseModel;

/**
 * Created by sks on 2015/9/29.
 */
public interface SendCodeView {
    void delCode(BaseModel model);
    void getCode(BaseModel model);
    void checkCode(BaseModel model);
}
