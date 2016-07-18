package com.jsyh.xjd.views;

import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.ShopCartModel;

/**
 * Created by Pisces on 2015/10/9.
 */
public interface CartGoodsView {
    void onShopCartList(ShopCartModel shopCartModel);

    void alterCartGoodsNumber(BaseModel baseModel);

    void deleteCartGoods(BaseModel baseModel);
}
