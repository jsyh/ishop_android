package com.jsyh.xjd.model;

import java.io.Serializable;

/**
 * Created by sks on 2015/9/24.
 */
public class Address implements Serializable{
    private String address_id;//收货地址id
    private String address;//详细的收货地址
    private String telnumber;//电话
    private String username;//收货人姓名
    private int is_default;//是否默认收货地址0不是默认1是默认

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
