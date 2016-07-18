package com.jsyh.xjd.model;

/**
 * Created by sks on 2015/9/21.
 */
public class UserInfor {


    /**
     * nick_name : 111111
     * sex : 0
     * integration : 0
     * address :
     * mobile :
     * email : 111111@123.com
     * user_money : 0.00
     * birthday : 0000-00-00
     * consume :
     * attention : 0
     * cart_num : 0
     * pay : 0
     * shipping : 0
     * shipping_send : 0
     * supplier : 0
     * comment : 0
     * bounts : 0
     */

    private String nick_name;
    private String sex;
    private String integration;
    private String address;
    private String mobile;
    private String email;
    private String user_money;
    private String birthday;
    private String consume;
    private String attention;
    private String cart_num;
    private String pay;
    private String shipping;
    private String shipping_send;
    private String supplier;
    private String comment;
    private String bounts;
    private int qd;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getQd() {
        return qd;
    }

    public void setQd(int qd) {
        this.qd = qd;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getCart_num() {
        return cart_num;
    }

    public void setCart_num(String cart_num) {
        this.cart_num = cart_num;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getShipping_send() {
        return shipping_send;
    }

    public void setShipping_send(String shipping_send) {
        this.shipping_send = shipping_send;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBounts() {
        return bounts;
    }

    public void setBounts(String bounts) {
        this.bounts = bounts;
    }
}
