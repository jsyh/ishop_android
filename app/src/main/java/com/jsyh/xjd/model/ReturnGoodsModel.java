package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/5/11.
 */
public class ReturnGoodsModel extends BaseModel{

    /**
     * supplier_name : 网店自营
     * goods_name : 苹果（Apple）iPhone 6 Plus (A1524) 16GB 金色 移动联通电信4G手机
     * back_goods_price : 5688.00
     * back_goods_number : 1
     * status_back : 5
     * goods_attr : 颜色:银色
     * goods_thumb : http://demo2.ishopv.com/images/201507/thumb_img/113_thumb_G_1437524324289.jpg
     * back_id : 56
     * invoice_no : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String supplier_name;
        private String goods_name;
        private String back_goods_price;
        private String back_goods_number;
        private String status_back;
        private String goods_attr;
        private String goods_thumb;
        private String back_id;
        private String invoice_no;

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getBack_goods_price() {
            return back_goods_price;
        }

        public void setBack_goods_price(String back_goods_price) {
            this.back_goods_price = back_goods_price;
        }

        public String getBack_goods_number() {
            return back_goods_number;
        }

        public void setBack_goods_number(String back_goods_number) {
            this.back_goods_number = back_goods_number;
        }

        public String getStatus_back() {
            return status_back;
        }

        public void setStatus_back(String status_back) {
            this.status_back = status_back;
        }

        public String getGoods_attr() {
            return goods_attr;
        }

        public void setGoods_attr(String goods_attr) {
            this.goods_attr = goods_attr;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public String getBack_id() {
            return back_id;
        }

        public void setBack_id(String back_id) {
            this.back_id = back_id;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }
    }
}
