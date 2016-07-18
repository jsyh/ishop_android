package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by weimo on 2015/9/29.
 */
public class FilterInfo  {

    /**
     * "is_fare": 1,
     "is_promotion": 1
     */
    private String price_range;

    private String is_fare;

    private String is_promotion;

    private List<TypeInfo> classify;


    public String getIs_fare() {
        return is_fare;
    }

    public void setIs_fare(String is_fare) {
        this.is_fare = is_fare;
    }

    public String getIs_promotion() {
        return is_promotion;
    }

    public void setIs_promotion(String is_promotion) {
        this.is_promotion = is_promotion;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }

    public List<TypeInfo> getClassify() {
        return classify;
    }

    public void setClassify(List<TypeInfo> classify) {
        this.classify = classify;
    }




    public  class TypeInfo{
        /**
         * "cat_id": "1",
         "cat_name": "手机类型",
         "keywords": "",
         "cat_desc": "",
         "parent_id": "0",
         "sort_order": "50",
         "template_file": "",
         "measure_unit": "",
         "show_in_nav": "0",
         "style": "",
         "is_show": "1",
         "grade": "5",
         "filter_attr": "172,185,178"
         */


        private String cat_id;
        private String cat_name;
        private String keywords;
        private String cat_desc;
        private String parent_id;
        private String sort_order;
        private String template_file;
        private String measure_unit;
        private String show_in_nav;
        private String style;
        private String is_show;
        private String grade;
        private String filter_attr;

        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getCat_desc() {
            return cat_desc;
        }

        public void setCat_desc(String cat_desc) {
            this.cat_desc = cat_desc;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getSort_order() {
            return sort_order;
        }

        public void setSort_order(String sort_order) {
            this.sort_order = sort_order;
        }

        public String getTemplate_file() {
            return template_file;
        }

        public void setTemplate_file(String template_file) {
            this.template_file = template_file;
        }

        public String getMeasure_unit() {
            return measure_unit;
        }

        public void setMeasure_unit(String measure_unit) {
            this.measure_unit = measure_unit;
        }

        public String getShow_in_nav() {
            return show_in_nav;
        }

        public void setShow_in_nav(String show_in_nav) {
            this.show_in_nav = show_in_nav;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getFilter_attr() {
            return filter_attr;
        }

        public void setFilter_attr(String filter_attr) {
            this.filter_attr = filter_attr;
        }
    }


}
