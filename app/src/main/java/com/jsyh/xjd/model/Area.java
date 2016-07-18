package com.jsyh.xjd.model;

import java.io.Serializable;

/**
 * Created by sks on 2015/10/9.
 */
public class Area implements Serializable {
    private String region_name;
    private String region_id;

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
}
