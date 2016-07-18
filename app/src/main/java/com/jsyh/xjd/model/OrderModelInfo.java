package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/5/3.
 */
public class OrderModelInfo extends BaseModel {


    /**
     * shop_name : 自营网站
     * goods : [{"goods_name":"苹果（Apple）iPhone 6 Plus (A1524) 16GB 金色 移动联通电信4G手机","goods_price":"5688.00","goods_thumb":"images/201507/thumb_img/113_thumb_G_1437524324289.jpg","goods_number":"1"},{"goods_name":"三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机","goods_price":"6088.00","goods_thumb":"images/201507/thumb_img/155_thumb_G_1437530585448.jpg","goods_number":"1"}]
     * total : 11776
     * order_sn : 20160503062020
     * order_id : 18
     * status : 1
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        private String shop_name;
        private String total;
        private String order_sn;
        private String order_id;
        private String status;
        private String service_phone;

        public String getService_phone() {
            return service_phone;
        }

        public void setService_phone(String service_phone) {
            this.service_phone = service_phone;
        }

        /**
         * goods_name : 苹果（Apple）iPhone 6 Plus (A1524) 16GB 金色 移动联通电信4G手机
         * goods_price : 5688.00
         * goods_thumb : images/201507/thumb_img/113_thumb_G_1437524324289.jpg
         * goods_number : 1
         */

        private List<GoodsBean> goods;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            private String goods_name;
            private String goods_price;
            private String goods_thumb;
            private String goods_number;
            private String goods_id;
            private String attr;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getAttr() {
                return attr;
            }

            public void setAttr(String attr) {
                this.attr = attr;
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

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public String getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(String goods_number) {
                this.goods_number = goods_number;
            }
        }
    }
}
