package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/10/22.
 */
public class SubmitOrderModel extends BaseModel {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> order_sn;

        public List<String> getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(List<String> order_sn) {
            this.order_sn = order_sn;
        }
    }
}
