package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/10/8.
 */
public class CreateOrder {
    private String address_id;
    private String address;
    private String user_name;
    private String mobile;
    private String total;//产品总价格
    private String integrala;
    private String integral;//用户可用积分
    private List<Goods> goods;

    public String getIntegrala() {
        return integrala;
    }

    public void setIntegrala(String integrala) {
        this.integrala = integrala;
    }

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
