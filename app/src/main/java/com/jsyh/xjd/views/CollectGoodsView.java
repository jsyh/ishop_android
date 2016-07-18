package com.jsyh.xjd.views;

import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.CollectGoodsModel;
import com.jsyh.xjd.model.CollectShopModel;

/**
 * Created by Su on 2015/10/20.
 */
public interface CollectGoodsView {
    void getCollectList(CollectGoodsModel collectGoodsModel);
    void getCollectShopList(CollectShopModel collectShopModel);
    void delCollectList(BaseModel baseModel);
}
