package com.jsyh.xjd.model;

/**
 * Created by sks on 2015/10/8.
 */
public class Goods {
    private String goods_img;
    private String price;
    private String shop_num;
    private String shipping;//是否有运费  1免运费   0  不免
    private String title;

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop_num() {
        return shop_num;
    }

    public void setShop_num(String shop_num) {
        this.shop_num = shop_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
