package com.jsyh.xjd.model;

/**
 * Created by gxc on 2016/3/1.
 */
public class Bouns_me {

    /**
     * min_goods_amount : 使用最低标准
     * status : 红包状态
     * type_money : 红包价值
     * type_name : 红包名称
     * use_end_date : 红包到期时间
     * status_num : 红包状态码   0:未使用，1：已使用，2：未开始，3：已过期
     * use_start_date : 开始时间
     */

    private String min_goods_amount;
    private String status;
    private String type_money;
    private String type_name;
    private String use_end_date;
    private int status_num;
    private String use_start_date;

    public String getMin_goods_amount() {
        return min_goods_amount;
    }

    public void setMin_goods_amount(String min_goods_amount) {
        this.min_goods_amount = min_goods_amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType_money(String type_money) {
        this.type_money = type_money;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public void setUse_end_date(String use_end_date) {
        this.use_end_date = use_end_date;
    }

    public String getStatus() {
        return status;
    }

    public String getType_money() {
        return type_money;
    }

    public String getType_name() {
        return type_name;
    }

    public String getUse_end_date() {
        return use_end_date;
    }

    public int getStatus_num() {
        return status_num;
    }

    public void setStatus_num(int status_num) {
        this.status_num = status_num;
    }

    public String getUse_start_date() {
        return use_start_date;
    }

    public void setUse_start_date(String use_start_date) {
        this.use_start_date = use_start_date;
    }
}
