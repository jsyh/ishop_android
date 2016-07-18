package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/5/1.
 */
public class AddressModel extends BaseModel{

    /**
     * address_id : 28
     * address : 河北石家庄裕华区新课国际广场1302不啊啊看就在看看是不是困死了那算了在哪看着老师
     * username : 礼拜
     * telnumber : 15200036221
     * is_default : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String address_id;
        private String address;
        private String username;
        private String telnumber;
        private String is_default;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTelnumber() {
            return telnumber;
        }

        public void setTelnumber(String telnumber) {
            this.telnumber = telnumber;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
