package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/4/28.
 */
public class ShopCartModel extends BaseModel {


    /**
     * goods : [{"rec_id":"92","goods_id":"34","goods_name":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","goods_price":"368.00","number":"2","attrvalue_id":"8,10","goods_attr":"颜色:米色 \n尺码:S \n","goods_img":"images/201507/thumb_img/34_thumb_G_1437507487587.jpg","collectlist":1},{"rec_id":"93","goods_id":"36","goods_name":"荷叶边欧根纱刺绣短袖镂空蕾丝连衣裙 夏韩系小香风短裙A字连衣裙","goods_price":"198.00","number":"1","attrvalue_id":"20,23","goods_attr":"颜色:白色 \n尺码:XL \n","goods_img":"images/201507/thumb_img/36_thumb_G_1437508564664.jpg","collectlist":1},{"rec_id":"94","goods_id":"38","goods_name":"高端2015夏装新款修身淑坊女女装蕾丝短袖复古森女连衣裙装","goods_price":"178.00","number":"1","attrvalue_id":"32,33","goods_attr":"颜色:粉色 \n尺码:XL \n","goods_img":"images/201507/thumb_img/38_thumb_G_1437514275132.jpg","collectlist":1}]
     * shopname : 天L&amp;L
     * goods_id : 0
     * url : http://demo2.ishopv.com/mapp/supplier.php?suppId=5
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String shopname;
        private int goods_id;
        private String url;
        private String shop_id;
        private int attention;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public int getAttention() {
            return attention;
        }

        public void setAttention(int attention) {
            this.attention = attention;
        }

        /**
         * rec_id : 92
         * goods_id : 34
         * goods_name : 夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙
         * goods_price : 368.00
         * number : 2
         * attrvalue_id : 8,10
         * goods_attr : 颜色:米色
         尺码:S

         * goods_img : images/201507/thumb_img/34_thumb_G_1437507487587.jpg
         * collectlist : 1
         */

        private List<GoodsBean> goods;

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            private String rec_id;
            private String goods_id;
            private String goods_name;
            private String goods_price;
            private String number;
            private String attrvalue_id;
            private String goods_attr;
            private String goods_img;
            private int collectlist;

            public String getRec_id() {
                return rec_id;
            }

            public void setRec_id(String rec_id) {
                this.rec_id = rec_id;
            }

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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getAttrvalue_id() {
                return attrvalue_id;
            }

            public void setAttrvalue_id(String attrvalue_id) {
                this.attrvalue_id = attrvalue_id;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public int getCollectlist() {
                return collectlist;
            }

            public void setCollectlist(int collectlist) {
                this.collectlist = collectlist;
            }
        }
    }

    @Override
    public String toString() {
        return "ShopCartModel{" +
                "data=" + data +
                '}';
    }
}
