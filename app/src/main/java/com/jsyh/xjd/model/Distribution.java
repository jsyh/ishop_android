package com.jsyh.xjd.model;

import java.io.Serializable;

/**
 * Created by sks on 2015/10/10.
 */
public class Distribution implements Serializable{
    private String shipping_id;//配送方式id
    private String shipping_name;//配送名称
    private String shipp_fee;//运费
    private String shipping_desc;

    public String getShipping_desc() {
        return shipping_desc;
    }

    public void setShipping_desc(String shipping_desc) {
        this.shipping_desc = shipping_desc;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipp_fee() {
        return shipp_fee;
    }

    public void setShipp_fee(String shipp_fee) {
        this.shipp_fee = shipp_fee;
    }
}
