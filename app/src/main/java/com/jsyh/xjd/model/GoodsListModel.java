package com.jsyh.xjd.model;

import java.util.List;

/**
 * goods list model
 * Created by Su on 2016/4/19.
 */
public class GoodsListModel extends BaseModel{


    /**
     * goods : [{"image":"images/201507/thumb_img/34_thumb_G_1437507487587.jpg","goods_id":"34","title":"夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙","price":"368.00","is_promotion":"1","pro_price":"299.00","interge":"0","good":"5","sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/232_thumb_G_1437587987847.jpg","goods_id":"232","title":"奇居良品 欧式家居装饰摆件 可莉尔裂纹贴花陶瓷水果盘","price":"274.00","is_promotion":"1","pro_price":"32.80","interge":"2","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/140_thumb_G_1437528915082.jpg","goods_id":"140","title":"雅鹿全棉四件套纯棉套件床上用品 回忆蓝 1.8米床","price":"333.00","is_promotion":"0","pro_price":"0.00","interge":"0","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/200_thumb_G_1437534571298.jpg","goods_id":"200","title":"爱度AY800蓝牙音箱手机电脑迷你音响无线便携插卡低音炮 带蓝牙自拍 土豪金","price":"98.00","is_promotion":"0","pro_price":"0.00","interge":"0","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/137_thumb_G_1437528855059.jpg","goods_id":"137","title":"LOVO 罗莱家纺出品全棉斜纹床品套件四件套 兔斯基","price":"399.00","is_promotion":"0","pro_price":"0.00","interge":"0","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/30_thumb_G_1437506667557.jpg","goods_id":"30","title":"日本进口 KRACIE（KRACIE）牌玫瑰香味糖果32g","price":"12.00","is_promotion":"1","pro_price":"9.90","interge":"0","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/3_thumb_G_1437501601556.jpg","goods_id":"3","title":"美国西北车厘子 1斤装 进口水果新鲜樱桃水果","price":"29.00","is_promotion":"1","pro_price":"26.00","interge":"0","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/155_thumb_G_1437530585448.jpg","goods_id":"155","title":"三星 Galaxy S6 edge（G9250）32G版 铂光金 移动联通电信4G手机","price":"6088.00","is_promotion":"0","pro_price":"0.00","interge":"60","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/45_thumb_G_1437516035103.jpg","goods_id":"45","title":"2015夏装韩版修身白色小西装女薄外套","price":"399.00","is_promotion":"0","pro_price":"0.00","interge":"0","good":0,"sell_num":0,"goods_number":0},{"image":"images/201507/thumb_img/100_thumb_G_1437523484060.jpg","goods_id":"100","title":"苹果（Apple）iPhone 6 (A1586) 16GB 金色 移动联通电信4G手机","price":"4888.00","is_promotion":"0","pro_price":"0.00","interge":"30","good":0,"sell_num":0,"goods_number":0}]
     * page_count : 1
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int page_count;
        /**
         * image : images/201507/thumb_img/34_thumb_G_1437507487587.jpg
         * goods_id : 34
         * title : 夏装甜美爱心提花蕾丝连衣裙女 宽松欧根纱背心裙
         * price : 368.00
         * is_promotion : 1
         * pro_price : 299.00
         * interge : 0
         * good : 5
         * sell_num : 0
         * goods_number : 0
         */

        private List<GoodsBean> goods;

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            private String image;
            private String goods_id;
            private String title;
            private String price;
            private String is_promotion;
            private String pro_price;
            private String interge;
            private String comment_sum;
            private String good;
            private int sell_num;
            private int goods_number;

            public String getComment_sum() {
                return comment_sum;
            }

            public void setComment_sum(String comment_sum) {
                this.comment_sum = comment_sum;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getIs_promotion() {
                return is_promotion;
            }

            public void setIs_promotion(String is_promotion) {
                this.is_promotion = is_promotion;
            }

            public String getPro_price() {
                return pro_price;
            }

            public void setPro_price(String pro_price) {
                this.pro_price = pro_price;
            }

            public String getInterge() {
                return interge;
            }

            public void setInterge(String interge) {
                this.interge = interge;
            }

            public String getGood() {
                return good;
            }

            public void setGood(String good) {
                this.good = good;
            }

            public int getSell_num() {
                return sell_num;
            }

            public void setSell_num(int sell_num) {
                this.sell_num = sell_num;
            }

            public int getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(int goods_number) {
                this.goods_number = goods_number;
            }
        }
    }
}
