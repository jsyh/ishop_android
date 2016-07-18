package com.jsyh.xjd.views;

import com.jsyh.xjd.model.AddressModel;
import com.jsyh.xjd.model.BaseModel;

/**
 * Created by sks on 2015/9/24.
 */
public interface AddressView {
    void getAddressList(AddressModel response);
    void delete(BaseModel model);
}
