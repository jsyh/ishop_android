package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/10/13.
 */
public class OrderInfor {

    private String consignee;//收货人
    private String add_time;//订单创建时间
    private String address;//地址
    private String mobile;//手机号
    private String money_paid;//实际付费
    private String order_amount;//订单费用  其实和money_paid一样
    private String order_sn;//订单号
    private String order_status;//订单状态0未确认1确认2已取消3无效4退货
    private String pay_name;//支付方式
//    private String pay_status;//支付状态0未付款1付款中2已付款
    private String shipping_fee;//运费
    private String shipping_name;//配送方式
//    private String shipping_status;//商品配送状态0未发货1已发货2已收货4退货
    private String total;//商品总价格
    private List<OrderGoods> goods;
    private String bonus;//红包金额
    private String integral;//使用的积分
    private String integral_money;//积分对应金钱

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
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

    public String getMoney_paid() {
        return money_paid;
    }

    public void setMoney_paid(String money_paid) {
        this.money_paid = money_paid;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderGoods> goods) {
        this.goods = goods;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIntegral_money() {
        return integral_money;
    }

    public void setIntegral_money(String integral_money) {
        this.integral_money = integral_money;
    }
}
