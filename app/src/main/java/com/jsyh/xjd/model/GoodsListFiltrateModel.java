package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/4/21.
 */
public class GoodsListFiltrateModel extends BaseModel{

    /**
     * price_range : 6400.00-0.00
     * classify : [{"cat_id":"1","cat_name":"食品生鲜"},{"cat_id":"2","cat_name":"服装服饰"},{"cat_id":"3","cat_name":"个护化妆"},{"cat_id":"4","cat_name":"手机数码"},{"cat_id":"5","cat_name":"家用电器"},{"cat_id":"6","cat_name":"家纺家居"},{"cat_id":"7","cat_name":"酒类饮料"},{"cat_id":"8","cat_name":"母婴用品"}]
     * is_fare : 1
     * is_promotion : 1
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String price_range;
        private int is_fare;
        private int is_promotion;
        /**
         * cat_id : 1
         * cat_name : 食品生鲜
         */

        private List<ClassifyBean> classify;

        public String getPrice_range() {
            return price_range;
        }

        public void setPrice_range(String price_range) {
            this.price_range = price_range;
        }

        public int getIs_fare() {
            return is_fare;
        }

        public void setIs_fare(int is_fare) {
            this.is_fare = is_fare;
        }

        public int getIs_promotion() {
            return is_promotion;
        }

        public void setIs_promotion(int is_promotion) {
            this.is_promotion = is_promotion;
        }

        public List<ClassifyBean> getClassify() {
            return classify;
        }

        public void setClassify(List<ClassifyBean> classify) {
            this.classify = classify;
        }

        public static class ClassifyBean {
            private String cat_id;
            private String cat_name;

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }
        }
    }
}
