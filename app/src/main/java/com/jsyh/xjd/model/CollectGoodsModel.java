package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/4/26.
 */
public class CollectGoodsModel extends BaseModel {

    /**
     * goods_id : 162
     * goods_name : HTC One M9w 联通4G手机 乌金灰
     * goods_price : 3837.00
     * goods_img : http://demo2.ishopv.com/images/201507/thumb_img/162_thumb_G_1437530928053.jpg
     * goods_comment : 0
     * goods_rage : 0.00
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_img;
        private int goods_comment;
        private String goods_rage;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public int getGoods_comment() {
            return goods_comment;
        }

        public void setGoods_comment(int goods_comment) {
            this.goods_comment = goods_comment;
        }

        public String getGoods_rage() {
            return goods_rage;
        }

        public void setGoods_rage(String goods_rage) {
            this.goods_rage = goods_rage;
        }
    }
}
