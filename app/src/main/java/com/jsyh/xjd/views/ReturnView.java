package com.jsyh.xjd.views;

import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.ReturnGoodsModel;

/**
 * Created by Su on 2016/5/10.
 */
public interface ReturnView {
    void onError(String msg);

    void onApplyResponse(BaseModel response);

    void onGetResponse(ReturnGoodsModel returnGoodsModel);

}
