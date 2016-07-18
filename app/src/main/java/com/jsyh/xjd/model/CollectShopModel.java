package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class CollectShopModel extends BaseModel {

    /**
     * shopname : L&L
     * shop_logo : http://demo2.ishopv.com//data/supplier/logo/logo_supplier5.jpg
     * supplierid : 5
     * rank : 中级店铺
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
        private String shop_logo;
        private String supplierid;
        private String rank;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getSupplierid() {
            return supplierid;
        }

        public void setSupplierid(String supplierid) {
            this.supplierid = supplierid;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }
}
