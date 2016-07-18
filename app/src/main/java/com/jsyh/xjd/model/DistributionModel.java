package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/10/10.
 */
public class DistributionModel extends BaseModel {
    private List<Distribution> data;

    public List<Distribution> getData() {
        return data;
    }

    public void setData(List<Distribution> data) {
        this.data = data;
    }
}
