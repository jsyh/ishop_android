package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Liang on 2015/9/21.
 * <p/>
 * 商品信息
 */
public class GoodsInfo implements Parcelable {


    /**
     *
     "image": "images/200905/thumb_img/3_thumb_G_1241422082679.jpg",
     "goods_id": "3",
     "title": "诺基亚原装5800耳机",
     "price": "68.00",
     "is_promotion": "0",
     "pro_price": "0.00",
     "interge": "0",
     "good": 0,
     "sell_num": "4",
     "goods_number": 0
     */

    private String image;
    private String goods_id;
    private String title;
    private String price;
    private String is_promotion;
    private String pro_price;
    private String interge;
    private String good;
    private String sell_num;
    private String goods_number;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(String is_promotion) {
        this.is_promotion = is_promotion;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getInterge() {
        return interge;
    }

    public void setInterge(String interge) {
        this.interge = interge;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getSell_num() {
        return sell_num;
    }

    public void setSell_num(String sell_num) {
        this.sell_num = sell_num;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.goods_id);
        dest.writeString(this.title);
        dest.writeString(this.price);
        dest.writeString(this.is_promotion);
        dest.writeString(this.pro_price);
        dest.writeString(this.interge);
        dest.writeString(this.good);
        dest.writeString(this.sell_num);
        dest.writeString(this.goods_number);
    }

    public GoodsInfo() {
    }

    protected GoodsInfo(Parcel in) {
        this.image = in.readString();
        this.goods_id = in.readString();
        this.title = in.readString();
        this.price = in.readString();
        this.is_promotion = in.readString();
        this.pro_price = in.readString();
        this.interge = in.readString();
        this.good = in.readString();
        this.sell_num = in.readString();
        this.goods_number = in.readString();
    }

    public static final Parcelable.Creator<GoodsInfo> CREATOR = new Parcelable.Creator<GoodsInfo>() {
        public GoodsInfo createFromParcel(Parcel source) {
            return new GoodsInfo(source);
        }

        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };
}
