package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 */
public class CategoryInfoModel extends BaseModel implements Parcelable {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Parcelable {

        private List<CategoryInfo> classify;
        private List<CategoryInfo2> product;
        private String prent_id;

        public List<CategoryInfo> getClassify() {
            return classify;
        }

        public void setClassify(List<CategoryInfo> classify) {
            this.classify = classify;
        }

        public List<CategoryInfo2> getProduct() {
            return product;
        }

        public void setProduct(List<CategoryInfo2> product) {
            this.product = product;
        }

        public String getPrent_id() {
            return prent_id;
        }

        public void setPrent_id(String prent_id) {
            this.prent_id = prent_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.classify);
            dest.writeList(this.product);
            dest.writeString(this.prent_id);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.classify = new ArrayList<CategoryInfo>();
            in.readList(this.classify, CategoryInfo.class.getClassLoader());
            this.product = new ArrayList<CategoryInfo2>();
            in.readList(this.product, CategoryInfo2.class.getClassLoader());
            this.prent_id = in.readString();
        }

        public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public CategoryInfoModel() {
    }

    protected CategoryInfoModel(Parcel in) {
        this.data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Parcelable.Creator<CategoryInfoModel> CREATOR = new Parcelable.Creator<CategoryInfoModel>() {
        @Override
        public CategoryInfoModel createFromParcel(Parcel source) {
            return new CategoryInfoModel(source);
        }

        @Override
        public CategoryInfoModel[] newArray(int size) {
            return new CategoryInfoModel[size];
        }
    };
}
