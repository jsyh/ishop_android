package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/10/13.
 */
public class OrderInforModel extends BaseModel {

    /**
     * bonus : 0.00
     * integral_money : 0.00
     * integral : 0
     * order_status : 4
     * consignee : 吴彦祖
     * money_paid : 1398.00
     * shipping_fee : 8.00
     * order_amount : 0.00
     * shipping_name : 圆通速递
     * add_time : 1462347274
     * order_sn : 20160504034265
     * invoice_no : 请输入快递单号
     * goods : [{"goodsInfo":[{"goods_id":"222","goods_attr":"","goods_name":"美姿蓝 家具 床 皮床 皮艺床 双人床 真皮床","goods_number":"1","goods_thumb":"images/201507/thumb_img/222_thumb_G_1437586485852.jpg","supplier_id":"0","shop_price":"1390.00"}],"supplier_name":"这个是空"}]
     * mobile : 15266636224
     * address : 广东海南雨花区哪里的借口孤岛惊魂弄哦付过款刚弄看看鬼哭狼嚎
     * pay_time : 0
     * shipping_time : 1462318692
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String bonus;
        private String integral_money;
        private String integral;
        private String order_status;
        private String consignee;
        private String money_paid;
        private String shipping_fee;
        private String order_amount;
        private String shipping_name;
        private String add_time;
        private String order_sn;
        private String invoice_no;
        private String mobile;
        private String address;
        private String pay_time;
        private String shipping_time;
        private String confirm_time;

        public String getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(String confirm_time) {
            this.confirm_time = confirm_time;
        }

        /**
         * goodsInfo : [{"goods_id":"222","goods_attr":"","goods_name":"美姿蓝 家具 床 皮床 皮艺床 双人床 真皮床","goods_number":"1","goods_thumb":"images/201507/thumb_img/222_thumb_G_1437586485852.jpg","supplier_id":"0","shop_price":"1390.00"}]
         * supplier_name : 这个是空
         */

        private List<GoodsBean> goods;

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public String getIntegral_money() {
            return integral_money;
        }

        public void setIntegral_money(String integral_money) {
            this.integral_money = integral_money;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getMoney_paid() {
            return money_paid;
        }

        public void setMoney_paid(String money_paid) {
            this.money_paid = money_paid;
        }

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(String shipping_time) {
            this.shipping_time = shipping_time;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            private String supplier_name;
            /**
             * goods_id : 222
             * goods_attr :
             * goods_name : 美姿蓝 家具 床 皮床 皮艺床 双人床 真皮床
             * goods_number : 1
             * goods_thumb : images/201507/thumb_img/222_thumb_G_1437586485852.jpg
             * supplier_id : 0
             * shop_price : 1390.00
             */

            private List<GoodsInfoBean> goodsInfo;

            public String getSupplier_name() {
                return supplier_name;
            }

            public void setSupplier_name(String supplier_name) {
                this.supplier_name = supplier_name;
            }

            public List<GoodsInfoBean> getGoodsInfo() {
                return goodsInfo;
            }

            public void setGoodsInfo(List<GoodsInfoBean> goodsInfo) {
                this.goodsInfo = goodsInfo;
            }

            public static class GoodsInfoBean {
                private String goods_id;
                private String goods_attr;
                private String goods_name;
                private String goods_number;
                private String goods_thumb;
                private String supplier_id;
                private String shop_price;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_attr() {
                    return goods_attr;
                }

                public void setGoods_attr(String goods_attr) {
                    this.goods_attr = goods_attr;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getGoods_number() {
                    return goods_number;
                }

                public void setGoods_number(String goods_number) {
                    this.goods_number = goods_number;
                }

                public String getGoods_thumb() {
                    return goods_thumb;
                }

                public void setGoods_thumb(String goods_thumb) {
                    this.goods_thumb = goods_thumb;
                }

                public String getSupplier_id() {
                    return supplier_id;
                }

                public void setSupplier_id(String supplier_id) {
                    this.supplier_id = supplier_id;
                }

                public String getShop_price() {
                    return shop_price;
                }

                public void setShop_price(String shop_price) {
                    this.shop_price = shop_price;
                }
            }
        }
    }
}
