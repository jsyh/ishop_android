package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Su on 2016/4/8.
 */
public class CategoryInfo2 implements Parcelable {
    private String brand_id;
    private String brand_logo;
    private String brand_name;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brand_id);
        dest.writeString(this.brand_logo);
        dest.writeString(this.brand_name);
    }

    public CategoryInfo2() {
    }

    protected CategoryInfo2(Parcel in) {
        this.brand_id = in.readString();
        this.brand_logo = in.readString();
        this.brand_name = in.readString();
    }

    public static final Parcelable.Creator<CategoryInfo2> CREATOR = new Parcelable.Creator<CategoryInfo2>() {
        @Override
        public CategoryInfo2 createFromParcel(Parcel source) {
            return new CategoryInfo2(source);
        }

        @Override
        public CategoryInfo2[] newArray(int size) {
            return new CategoryInfo2[size];
        }
    };
}
