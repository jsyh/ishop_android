package com.jsyh.xjd.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.CollectGoodsModel;
import com.jsyh.xjd.model.CollectShopModel;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CollectGoodsView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * Created by Su on 2015/10/20.
 */
public class CollectGoodsPresenter extends BasePresenter {

    private CollectGoodsView mCollectGoodsView;

    public CollectGoodsPresenter(CollectGoodsView mCollectGoodsView) {
        this.mCollectGoodsView = mCollectGoodsView;
    }


    /**
     * 关注商品列表
     */
    public void getCollectList(final Context context,int type) {
        Map<String, String> params = getDefaultMD5Params("goods", "collectlist");
        String key = (String) SPUtils.get(context, "key", "");

        params.put(SPConfig.KEY, key);
        params.put("type",type+"");
        OkHttpClientManager.postAsyn(context, ConfigValue.CollectGoodsList, params,
                new BaseDelegate.ResultCallback<CollectGoodsModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(CollectGoodsModel response, Object tag) {
                        mCollectGoodsView.getCollectList(response);
                    }
                });
    }

    /**
     * 关注店铺列表
     */
    public void getCollectShopList(final Context context,int type) {
        Map<String, String> params = getDefaultMD5Params("goods", "collectlist");
        String key = (String) SPUtils.get(context, "key", "");

        params.put(SPConfig.KEY, key);
        params.put("type",type+"");
        OkHttpClientManager.postAsyn(context, ConfigValue.CollectGoodsList, params,
                new BaseDelegate.ResultCallback<CollectShopModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(context, ExceptionHelper.getMessage(e, context));
                    }

                    @Override
                    public void onResponse(CollectShopModel response, Object tag) {
                        mCollectGoodsView.getCollectShopList(response);
                    }
                });
    }
    /**
     * 取消收藏
     *
     * @return
     */
    public boolean cancelCollect(final Context mContext, String id_values, int type) {
        String key = (String) SPUtils.get(mContext, "key", "");
        if (TextUtils.isEmpty(key)) return false;
        Map<String, String> params = getDefaultMD5Params("goods", "qcollect");
        if (params == null) return false;

        params.put("key", key);
        params.put("id_values", id_values);
        params.put("type", type + "");
        OkHttpClientManager.postAsyn(mContext, ConfigValue.NOCOLLECT_GOODS, params,
                new BaseDelegate.ResultCallback<BaseModel>() {

                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        Utils.showToast(mContext, ExceptionHelper.getMessage(e, mContext));
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        mCollectGoodsView.delCollectList(response);
                    }
                });

        return true;
    }

}
