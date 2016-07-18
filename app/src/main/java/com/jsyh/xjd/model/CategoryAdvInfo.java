package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by momo on 2015/10/15.
 * <p/>
 * 一级分类 中的广告 图片
 */
public class CategoryAdvInfo implements Parcelable {

    /**
     * "goods_name": "恒基伟业G101",
     * "goods_thumb": "images/200905/thumb_img/16_thumb_G_1241968949103.jpg"
     */


    private String goods_name;
    private String goods_thumb;


    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_thumb() {
        return goods_thumb;
    }

    public void setGoods_thumb(String goods_thumb) {
        this.goods_thumb = goods_thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goods_thumb);
        dest.writeString(this.goods_name);
    }

    public CategoryAdvInfo() {
    }

    protected CategoryAdvInfo(Parcel in) {
        this.goods_thumb = in.readString();
        this.goods_name = in.readString();
    }

    public static final Parcelable.Creator<CategoryAdvInfo> CREATOR = new Parcelable.Creator<CategoryAdvInfo>() {
        public CategoryAdvInfo createFromParcel(Parcel source) {
            return new CategoryAdvInfo(source);
        }

        public CategoryAdvInfo[] newArray(int size) {
            return new CategoryAdvInfo[size];
        }
    };
}
