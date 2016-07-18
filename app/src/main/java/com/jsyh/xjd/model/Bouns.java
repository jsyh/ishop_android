package com.jsyh.xjd.model;

import java.io.Serializable;

/**
 * Created by sks on 2015/10/8.
 */
public class Bouns implements Serializable{
    private String min_goods_amount;//使用红包的最低消费
    private String type_money; //红包面值
    private String type_name; //红包名
    private String bonus_id;  //红包id
    private String bonus_type_id;  //红包类型id
    private String user_id;
    private String use_end_date;
    private String use_start_date;


    public String getMin_goods_amount() {
        return min_goods_amount;
    }

    public void setMin_goods_amount(String min_goods_amount) {
        this.min_goods_amount = min_goods_amount;
    }

    public String getType_money() {
        return type_money;
    }

    public void setType_money(String type_money) {
        this.type_money = type_money;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getBonus_id() {
        return bonus_id;
    }

    public void setBonus_id(String bonus_id) {
        this.bonus_id = bonus_id;
    }

    public String getBonus_type_id() {
        return bonus_type_id;
    }

    public void setBonus_type_id(String bonus_type_id) {
        this.bonus_type_id = bonus_type_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUse_end_date() {
        return use_end_date;
    }

    public void setUse_end_date(String use_end_date) {
        this.use_end_date = use_end_date;
    }

    public String getUse_start_date() {
        return use_start_date;
    }

    public void setUse_start_date(String use_start_date) {
        this.use_start_date = use_start_date;
    }
}
