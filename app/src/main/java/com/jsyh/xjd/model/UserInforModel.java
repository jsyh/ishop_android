package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/9/21.
 */
public class UserInforModel extends BaseModel {

    private List<UserInfor> data;

    public List<UserInfor> getData() {
        return data;
    }

    public void setData(List<UserInfor> data) {
        this.data = data;
    }
}
