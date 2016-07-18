package com.jsyh.xjd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liang on 2015/9/17.
 * <p/>
 * 商品信息 model
 */
public class GoodsInfoModel2 extends BaseModel {

    public GoodsInfo data;

    public class GoodsInfo {
        public CommentsModel comments;
        public Goods goods;
        public Shop shop;
        public String supplierid;
    }


    /**
     * 商品信息
     */
    public class Goods {
        public String[] album;                          //相册
        public List<Attribute> attribute;               //属性
        public List<AttributeNum> attr_number;          //库存

        public String content;                          //图文信息
        public String goods_id;                         //id
        public String goods_name;                       //商品名称
        public String goods_sn;                         //商品编号
        public String is_real;                          //是否为实物1是0否
        public int is_attention;                        //是否已经关注
        public String exchange_integral;                //兑换需要积分
        public ArrayList<Params> param;                 //基本参数
        public String repertory;                           //剩余库存

        public String sales;                            //已销售的数量
        public String shop_price;                       //商品价格
    }

    public class Shop {
        public String address;                          //地址
        public String all_goods;                           //商家全部商品

        public String attention;                           //关注店铺数
        public String new_goods;                           //新商品数
        public String servicephone;                     //手机号
        public String shop_url;
        public String shoplogo;                         //店铺logo
        public String shopname;                         //店铺名称
        public String supplier_id;                      //商家id
        public String comments_rank;
        public String rank;                         //商家等级
        public String supplier_sum;

        public String getSupplier_sum() {
            return supplier_sum;
        }

        public void setSupplier_sum(String supplier_sum) {
            this.supplier_sum = supplier_sum;
        }
    }

    public class Attribute {
        public String attr_name;
        public List<Attr_key> attr_key;

        public class Attr_key {
            public String attr_id;                          //101
            public String attr_price;                       //100
            public String attr_value;                       //"30万像素"
            public String attr_name;                        //颜色
            public String goods_attr_id;                    //大类id
        }
    }

    public class AttributeNum {
        public String number;
        public List<String> goods_attr;
    }

    public class Params implements Serializable {
        /**
         * "attr_name_id": "189",
         * "attr_name": "屏幕大小",
         * "attr_value_id": "216",
         * "attr_value": "2.0英寸"
         */
        public String attr_name;
        public String attr_value;
    }

}

