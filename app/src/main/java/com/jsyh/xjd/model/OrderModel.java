package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/9/29.
 */
public class OrderModel extends BaseModel {
    private List<Order> data;

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
