package com.jsyh.xjd.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.GoodsListFiltrateModel;
import com.jsyh.xjd.model.GoodsListModel;
import com.jsyh.xjd.views.GoodsFilterView;
import com.squareup.okhttp.Request;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.Map;

/**
 * 商品筛选 P
 */
public class GoodsFilterPresenter extends BasePresenter {


    public static final String SEARCH_TYPE = "search";
    public static final String FITRATE_TYPE = "fitrate";
    private GoodsFilterView mView;


    public GoodsFilterPresenter(GoodsFilterView mView) {
        this.mView = mView;
    }
    public void loadGoodsList(@NonNull final Context context, int order, String filtrate, String keyword, String classify_id,
                              String brand_id, String goods_type, @NonNull int page) {

        Map<String, String> params = getDefaultMD5Params("first", "index");
        if (params == null) return;
        params.put("order", order + "");
        //数组
        params.put("filtrate", filtrate);
        params.put("keyword", keyword == null ? "" : keyword);
        params.put("classify_id", classify_id == null ? "" : classify_id);
        params.put("brand_id", brand_id == null ? "" : brand_id);
        params.put("goods_type", goods_type == null ? "" : goods_type);
        params.put("c", goods_type == null ? "" : goods_type);
        params.put("type", SEARCH_TYPE);
        params.put("page", page + "");
        OkHttpClientManager.postAsyn(context, ConfigValue.CATEGORY_FILTER, params, new BaseDelegate.ResultCallback<GoodsListModel>() {

            @Override
            public void onError(Request request, Object tag, Exception e) {
                ToastUtil.showToast(context, ExceptionHelper.getMessage(e, context));
            }

            @Override
            public void onResponse(GoodsListModel response, Object tag) {
                mView.onResponse(response);
            }
        });
    }
    //获取筛选条件

    public void loadSelectOptions(@NonNull final Context context, String keyword, String goods_type, String classify_id,String brand_id) {

        Map<String, String> params = getDefaultMD5Params("first", "index");
        if (params == null) return;
        //数组
        params.put("keyword", keyword == null ? "" : keyword);
        params.put("classify_id", classify_id == null ? "" : classify_id);
        params.put("goods_type", goods_type == null ? "" : goods_type);
        params.put("brand_id",brand_id == null ? "" : brand_id);
        params.put("type", FITRATE_TYPE);
        OkHttpClientManager.postAsyn(context, ConfigValue.CATEGORY_FILTER, params, new BaseDelegate.ResultCallback<GoodsListFiltrateModel>() {

            @Override
            public void onError(Request request, Object tag, Exception e) {
                ToastUtil.showToast(context, ExceptionHelper.getMessage(e, context));
            }

            @Override
            public void onResponse(GoodsListFiltrateModel response, Object tag) {
                mView.onFiltrateResponse(response);
            }
        });
    }

}
