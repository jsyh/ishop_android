package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/10/8.
 */
public class CreateOrderModel extends BaseModel {

    /**
     * goods : [{"goodsinfo":[{"goods_name":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","goods_price":"368.00","goods_number":"1","is_real":"1","goods_attr_id":"10,9","goods_thumb":"http://demo2.ishopv.com/http://demo2.ishopv.com/images/201507/thumb_img/34_thumb_G_1437507487587.jpg","supplier_id":"5","attr":[{"attr_value":"S","attr_name":"尺码"},{"attr_value":"棕色","attr_name":"颜色"}]}],"supplier_name":"天L&amp;L"}]
     * address : 福建南平建阳市给客户门口快捷键
     * address_id : 35
     * mobile : 187224361254
     * user_name : 马云
     * integral_scale : 2
     * integral_percent : 0.01
     * total : 368
     * integral : 0
     * post_points : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String address;
        private String address_id;
        private String mobile;
        private String user_name;
        private String integral_scale;
        private String integral_percent;
        private String total;
        private String integral;
        private String post_points;
        private String give_integral;

        public String getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(String give_integral) {
            this.give_integral = give_integral;
        }

        /**
         * goodsinfo : [{"goods_name":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","goods_price":"368.00","goods_number":"1","is_real":"1","goods_attr_id":"10,9","goods_thumb":"http://demo2.ishopv.com/http://demo2.ishopv.com/images/201507/thumb_img/34_thumb_G_1437507487587.jpg","supplier_id":"5","attr":[{"attr_value":"S","attr_name":"尺码"},{"attr_value":"棕色","attr_name":"颜色"}]}]
         * supplier_name : 天L&amp;L
         */

        private List<GoodsBean> goods;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIntegral_scale() {
            return integral_scale;
        }

        public void setIntegral_scale(String integral_scale) {
            this.integral_scale = integral_scale;
        }

        public String getIntegral_percent() {
            return integral_percent;
        }

        public void setIntegral_percent(String integral_percent) {
            this.integral_percent = integral_percent;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getPost_points() {
            return post_points;
        }

        public void setPost_points(String post_points) {
            this.post_points = post_points;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            private String supplier_name;
            private String peisong;
            private String ps_id;
            private String ps_price;

            public String getPeisong() {
                return peisong;
            }

            public void setPeisong(String peisong) {
                this.peisong = peisong;
            }

            public String getPs_id() {
                return ps_id;
            }

            public void setPs_id(String ps_id) {
                this.ps_id = ps_id;
            }

            public String getPs_price() {
                return ps_price;
            }

            public void setPs_price(String ps_price) {
                this.ps_price = ps_price;
            }

            public List<GoodsinfoBean> getGoodsInfo() {
                return goodsInfo;
            }

            public void setGoodsInfo(List<GoodsinfoBean> goodsInfo) {
                this.goodsInfo = goodsInfo;
            }

            /**
             * goods_name : 夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙
             * goods_price : 368.00
             * goods_number : 1
             * is_real : 1
             * goods_attr_id : 10,9
             * goods_thumb : http://demo2.ishopv.com/http://demo2.ishopv.com/images/201507/thumb_img/34_thumb_G_1437507487587.jpg
             * supplier_id : 5
             * attr : [{"attr_value":"S","attr_name":"尺码"},{"attr_value":"棕色","attr_name":"颜色"}]
             */

            private List<GoodsinfoBean> goodsInfo;

            public String getSupplier_name() {
                return supplier_name;
            }

            public void setSupplier_name(String supplier_name) {
                this.supplier_name = supplier_name;
            }


            public static class GoodsinfoBean {
                private String goods_name;
                private String goods_price;
                private String goods_number;
                private String is_real;
                private String goods_attr_id;
                private String goods_thumb;
                private String supplier_id;
                /**
                 * attr_value : S
                 * attr_name : 尺码
                 */

                private List<AttrBean> attr;

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

                public String getGoods_number() {
                    return goods_number;
                }

                public void setGoods_number(String goods_number) {
                    this.goods_number = goods_number;
                }

                public String getIs_real() {
                    return is_real;
                }

                public void setIs_real(String is_real) {
                    this.is_real = is_real;
                }

                public String getGoods_attr_id() {
                    return goods_attr_id;
                }

                public void setGoods_attr_id(String goods_attr_id) {
                    this.goods_attr_id = goods_attr_id;
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

                public List<AttrBean> getAttr() {
                    return attr;
                }

                public void setAttr(List<AttrBean> attr) {
                    this.attr = attr;
                }

                public static class AttrBean {
                    private String attr_value;
                    private String attr_name;

                    public String getAttr_value() {
                        return attr_value;
                    }

                    public void setAttr_value(String attr_value) {
                        this.attr_value = attr_value;
                    }

                    public String getAttr_name() {
                        return attr_name;
                    }

                    public void setAttr_name(String attr_name) {
                        this.attr_name = attr_name;
                    }
                }
            }
        }
    }
}
