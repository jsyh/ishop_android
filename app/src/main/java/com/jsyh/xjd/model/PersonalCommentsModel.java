package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/5/4.
 */
public class PersonalCommentsModel extends BaseModel {


    /**
     * count : 3
     * commnet : [{"order_time":"1462391634","rank":"5","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","goods_name":"三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机","user_name":"马云","goods_attr":"颜色:绿色\r","goods_img":"http://demo2.ishopv.com/images/201507/thumb_img/155_thumb_G_1437530585448.jpg"},{"order_time":"1462391634","rank":"5","content":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","goods_name":"三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机","user_name":"马云","goods_attr":"颜色:灰色\r","goods_img":"http://demo2.ishopv.com/images/201507/thumb_img/155_thumb_G_1437530585448.jpg"},{"order_time":"1462391649","rank":"5","content":"                            啊啊啊啊啊啊啊啊啊啊阿啊啊啊啊啊啊啊","goods_name":"三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机","user_name":"马云","goods_attr":"颜色:绿色\r","goods_img":"http://demo2.ishopv.com/images/201507/thumb_img/155_thumb_G_1437530585448.jpg"},{"order_time":"1462391649","rank":"5","content":"                            啊啊啊啊啊啊啊啊啊啊阿啊啊啊啊啊啊啊","goods_name":"三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机","user_name":"马云","goods_attr":"颜色:灰色\r","goods_img":"http://demo2.ishopv.com/images/201507/thumb_img/155_thumb_G_1437530585448.jpg"},{"order_time":"1462391666","rank":"5","content":"                            的发送到发送到发送到","goods_name":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","user_name":"马云","goods_attr":"尺码:XL\r颜色:棕色\r","goods_img":"http://demo2.ishopv.com/images/201507/thumb_img/34_thumb_G_1437507487587.jpg"}]
     * username : 马云
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String count;
        private String username;
        /**
         * order_time : 1462391634
         * rank : 5
         * content : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
         * goods_name : 三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机
         * user_name : 马云
         * goods_attr : 颜色:绿色
         * goods_img : http://demo2.ishopv.com/images/201507/thumb_img/155_thumb_G_1437530585448.jpg
         */

        private List<CommnetBean> commnet;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<CommnetBean> getCommnet() {
            return commnet;
        }

        public void setCommnet(List<CommnetBean> commnet) {
            this.commnet = commnet;
        }

        public static class CommnetBean {
            private String order_time;
            private String rank;
            private String content;
            private String goods_name;
            private String user_name;
            private String goods_attr;
            private String goods_img;

            public String getOrder_time() {
                return order_time;
            }

            public void setOrder_time(String order_time) {
                this.order_time = order_time;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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
        }
    }
}
