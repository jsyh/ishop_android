package com.jsyh.xjd.views;

import android.support.annotation.Nullable;

import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.GoodsInfoModel2;

import java.util.List;

public interface GoodDetatileView extends BaseView {
    void albumData(String[] albums);// 商品相册信息

    void contentData(List<String> content);// 图文信息

    //void initAttributeData(List<Attribute> attributes,List);// 属性信息

    void paramData(String param);// 基本参数


    void onLoadGoodsInfoDatas(@Nullable GoodsInfoModel2 datas);

    /**
     * 加入购物车回调
     *
     * @param data
     */
    void onAddCarShopping(@Nullable BaseModel data);

    /**
     * 收藏商品
     *
     * @param data
     */
    void onCollectGoods(BaseModel data);

    /**
     * 取消收藏商品
     *
     * @param data
     */
    void cancelCollectGoods(BaseModel data);

    void getShopCollectStatus(BaseModel baseModel);
}
