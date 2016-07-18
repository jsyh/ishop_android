package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by sks on 2015/9/29.
 */
public class Order {
    private List<OrderGoods> goods;
    private String order_id;//订单id
    private String order_sn;//订单编号
//    private String order_status;//订单状态0未确认1确认2已取消3无效4退货5配货中
//    private String shipping_status;//商品配送状态0未发货1已发货2已收货4退货
//    private String pay_status;//支付状态0未付款1付款中2已付款
    private String status;//订单状态1：待付款，2：已取消，3：已确认，4：已付款，5：配货中，6：已收货，7：已退货，8：已发货
    private String total;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderGoods> goods) {
        this.goods = goods;
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
}
