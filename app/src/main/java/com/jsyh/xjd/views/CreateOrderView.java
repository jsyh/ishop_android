package com.jsyh.xjd.views;

import com.jsyh.xjd.model.CreateOrderModel;
import com.jsyh.xjd.model.SubmitOrderModel;

/**
 * Created by sks on 2015/10/8.
 */
public interface CreateOrderView {
    void getOrderInfor(CreateOrderModel model);
    void result(SubmitOrderModel model);
}
