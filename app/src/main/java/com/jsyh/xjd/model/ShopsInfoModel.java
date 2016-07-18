package com.jsyh.xjd.model;

import java.util.List;

/**
 * 搜索店铺结果模型
 * Created by Su on 2016/4/14.
 */
public class ShopsInfoModel extends BaseModel {


    /**
     * info : [{"goods":[{"goods_id":"33","goods_name":"夏装新款修身假两件无袖绣花蕾丝连衣裙","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/33_thumb_G_1437507221390.jpg","shop_price":"279.00"},{"goods_id":"34","goods_name":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/34_thumb_G_1437507487587.jpg","shop_price":"368.00"},{"goods_id":"36","goods_name":"荷叶边欧根纱刺绣短袖镂空蕾丝连衣裙 夏韩系小香风短裙A字连衣裙","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/36_thumb_G_1437508564664.jpg","shop_price":"198.00"},{"goods_id":"38","goods_name":"高端2015夏装新款修身淑坊女女装蕾丝短袖复古森女连衣裙装","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/38_thumb_G_1437514275132.jpg","shop_price":"178.00"}],"shop":{"address":"北京/北京","sales":0,"shop_logo":"/data/supplier/logo/logo_supplier5.jpg","shop_name":"L&L","shop_rage":"1","shop_url":"http://demo2.ishopv.com/mapp/supplier.php?suppId=5","supplier_id":"1"}}]
     * page_total : 3
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int page_total;
        /**
         * goods : [{"goods_id":"33","goods_name":"夏装新款修身假两件无袖绣花蕾丝连衣裙","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/33_thumb_G_1437507221390.jpg","shop_price":"279.00"},{"goods_id":"34","goods_name":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/34_thumb_G_1437507487587.jpg","shop_price":"368.00"},{"goods_id":"36","goods_name":"荷叶边欧根纱刺绣短袖镂空蕾丝连衣裙 夏韩系小香风短裙A字连衣裙","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/36_thumb_G_1437508564664.jpg","shop_price":"198.00"},{"goods_id":"38","goods_name":"高端2015夏装新款修身淑坊女女装蕾丝短袖复古森女连衣裙装","goods_thumb":"http://demo2.ishopv.com/images/201507/thumb_img/38_thumb_G_1437514275132.jpg","shop_price":"178.00"}]
         * shop : {"address":"北京/北京","sales":0,"shop_logo":"/data/supplier/logo/logo_supplier5.jpg","shop_name":"L&L","shop_rage":"1","shop_url":"http://demo2.ishopv.com/mapp/supplier.php?suppId=5","supplier_id":"1"}
         */

        private List<InfoBean> info;

        public int getPage_total() {
            return page_total;
        }

        public void setPage_total(int page_total) {
            this.page_total = page_total;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * address : 北京/北京
             * sales : 0
             * shop_logo : /data/supplier/logo/logo_supplier5.jpg
             * shop_name : L&L
             * shop_rage : 1
             * shop_url : http://demo2.ishopv.com/mapp/supplier.php?suppId=5
             * supplier_id : 1
             */

            private ShopBean shop;
            /**
             * goods_id : 33
             * goods_name : 夏装新款修身假两件无袖绣花蕾丝连衣裙
             * goods_thumb : http://demo2.ishopv.com/images/201507/thumb_img/33_thumb_G_1437507221390.jpg
             * shop_price : 279.00
             */

            private List<GoodsBean> goods;

            public ShopBean getShop() {
                return shop;
            }

            public void setShop(ShopBean shop) {
                this.shop = shop;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public static class ShopBean {
                private String address;
                private int sales;
                private String shop_logo;
                private String shop_name;
                private String shop_rage;
                private String shop_url;
                private String supplier_id;
                private String goods_sum;
                private int attention;
                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public int getSales() {
                    return sales;
                }

                public void setSales(int sales) {
                    this.sales = sales;
                }

                public String getShop_logo() {
                    return shop_logo;
                }

                public void setShop_logo(String shop_logo) {
                    this.shop_logo = shop_logo;
                }

                public String getShop_name() {
                    return shop_name;
                }

                public void setShop_name(String shop_name) {
                    this.shop_name = shop_name;
                }

                public String getShop_rage() {
                    return shop_rage;
                }

                public void setShop_rage(String shop_rage) {
                    this.shop_rage = shop_rage;
                }

                public String getShop_url() {
                    return shop_url;
                }

                public void setShop_url(String shop_url) {
                    this.shop_url = shop_url;
                }

                public String getSupplier_id() {
                    return supplier_id;
                }

                public void setSupplier_id(String supplier_id) {
                    this.supplier_id = supplier_id;
                }

                public int getAttention() {
                    return attention;
                }

                public void setAttention(int attention) {
                    this.attention = attention;
                }

                public String getGoods_sum() {
                    return goods_sum;
                }

                public void setGoods_sum(String goods_sum) {
                    this.goods_sum = goods_sum;
                }
            }

            public static class GoodsBean {
                private String goods_id;
                private String goods_name;
                private String goods_thumb;
                private String shop_price;

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

                public String getGoods_thumb() {
                    return goods_thumb;
                }

                public void setGoods_thumb(String goods_thumb) {
                    this.goods_thumb = goods_thumb;
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
