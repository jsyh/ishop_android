package com.jsyh.xjd.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Liang on 2015/9/17.
 *
 * 商品信息 model
 */
public class GoodsInfoModel extends BaseModel  {


    private MyData data;

    public MyData getData() {
        return data;
    }

    public void setData(MyData data) {
        this.data = data;
    }

    public static class MyData implements Parcelable {
        private List<GoodsInfo> goods;


        private String count;          //总共条数
        private String page;           //当前页码

        private String page_count;     //总共多少页

        private String page_size;      //每页多少条数据

        private String goods_type;      //筛选的时候要用到


        public List<GoodsInfo> getGoods() {
            return goods;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public void setGoods(List<GoodsInfo> goods) {
            this.goods = goods;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPage_count() {
            return page_count;
        }

        public void setPage_count(String page_count) {
            this.page_count = page_count;
        }

        public String getPage_size() {
            return page_size;
        }

        public void setPage_size(String page_size) {
            this.page_size = page_size;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(goods);
            dest.writeString(this.count);
            dest.writeString(this.page);
            dest.writeString(this.page_count);
            dest.writeString(this.page_size);
        }

        public MyData() {
        }

        protected MyData(Parcel in) {
            this.goods = in.createTypedArrayList(GoodsInfo.CREATOR);
            this.count = in.readString();
            this.page = in.readString();
            this.page_count = in.readString();
            this.page_size = in.readString();
        }

        public static final Creator<MyData> CREATOR = new Creator<MyData>() {
            public MyData createFromParcel(Parcel source) {
                return new MyData(source);
            }

            public MyData[] newArray(int size) {
                return new MyData[size];
            }
        };
    }


}
