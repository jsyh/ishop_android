package com.jsyh.xjd.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jsyh.xjd.R;
import com.jsyh.xjd.fragment.ShopCartFragment;
import com.jsyh.xjd.model.PersonalModel;
import com.jsyh.xjd.views.PersonalView;

/**
 * Created by Su on 2015/10/20.
 * 商品详情中跳转购物车
 */
public class ShoppingCartActivity extends BaseActivity implements PersonalView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ShopCartFragment fragment = new ShopCartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.shoppingCartLayout, fragment, "cartFragment");
        transaction.commit();
    }

    @Override
    public void initTitle() {
        super.initTitle();


    }

    @Override
    public void onPersonalInfo(PersonalModel response) {

    }

    @Override
    public void setCartGoodsNums(String nums) {

    }

    @Override
    public void addCartGoodsNums(int nums) {

    }
}
