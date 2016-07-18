package com.jsyh.xjd.presenter;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.GoodsInfoModel2;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.views.GoodDetatileView;
import com.jsyh.xjd.http.BaseDelegate;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * @author yaodingding
 * @ClassName: DetailPresenter
 * @Description: TODO(商品详情 请求页面)
 * @date 2015-9-24 上午10:06:36
 */
public class DetailPresenter extends BasePresenter {
    private GoodDetatileView goodDetatileView;
    private Context mContext;

    public DetailPresenter(Context mContext) {
        this.mContext = mContext;
        this.goodDetatileView = (GoodDetatileView) mContext;
    }

    // 加载详情数据
    public void LoadDetatileData(String goods_id, int type) {
        // String album[]={"http://i.imgur.com/DvpvklR.png","http://i.imgur.com/DvpvklR.png","http://i.imgur.com/DvpvklR.png"};
        // goodDetatileView.albumData(album);
        // List<Attribute> attributes=new ArrayList<GoodsInfoModel.Attribute>();
        // GoodsInfoModel goodsInfoModel=new GoodsInfoModel();
        // attributes.add(goodsInfoModel.new Attribute("颜色",new String[]{"黄色","蓝色"}));
        // attributes.add(goodsInfoModel.new Attribute("尺寸",new String[]{"XL","XXL"}));
        initLoadDialog(mContext);
        mLoadingDialog.show();

        Map<String, String> params = getDefaultMD5Params("goods", "goodsinfo");

        if (params == null) return;

        if (type == 1) {
            params.put("type", type + "");
        }
        params.put("goods_id", goods_id);
        //获取商品详情加入参数key，能获取此商品是否已经被关注,不加key参数默认全部不关注
        String key = (String) SPUtils.get(mContext, "key", "");
        if (!TextUtils.isEmpty(key)) params.put("key", key);
        OkHttpClientManager.postAsyn(mContext, ConfigValue.GoodsInfo, params, new BaseDelegate.ResultCallback<GoodsInfoModel2>() {

            @Override
            public void onError(Request request, Object tag, Exception e) {
                mLoadingDialog.dismiss();

            }

            @Override
            public void onResponse(GoodsInfoModel2 goodsInfoModel2, Object tag) {

                if (goodsInfoModel2 != null && TextUtils.equals(ConfigValue.Success_Code, goodsInfoModel2.getCode())) {
                     mLoadingDialog.dismiss();
                    if (goodsInfoModel2.data != null) {
                        // FIXME: 15/10/11 修改为 调用 onLoadGoodsInfoDatas(),再这个方法里，再分别调用 下面注释的方法

                        goodDetatileView.onLoadGoodsInfoDatas(goodsInfoModel2);
//						goodDetatileView.albumData(goodsInfoModel2.data.album);
//						goodDetatileView.attributeData(goodsInfoModel2.data.attribute);

                        // FIXME: 15/10/11 liang  参数和可笑

//						List<String> datas = new ArrayList<String>();
//
//						datas.add(goodsInfoModel2.data.content);
//						datas.add(goodsInfoModel2.data.sales);
//						datas.add(goodsInfoModel2.data.sales);
//						goodDetatileView.contentData(datas);
                    }
                }

            }
        });

    }


    /**
     * 加入购物车
     *
     * @param num
     * @param goodsId
     * @param attrValue
     */
    @CheckResult
    public boolean addShoppingCar(@NonNull String num, @NonNull String goodsId, @NonNull String attrValue) {

        String key = (String) SPUtils.get(mContext, "key", "");
        if (TextUtils.isEmpty(key)) return false;

        Map<String, String> params = getDefaultMD5Params("goods", "addcart");

        if (params == null) return false;

        params.put("key", key);
        params.put("num", num);                    //商品数量
        params.put("goods_id", goodsId);        //商品id
        if (!attrValue.equals(""))
            params.put("attrvalue_id", attrValue);    //商品的可选属性id


        OkHttpClientManager.postAsyn(mContext, ConfigValue.ADD_CART_SHOPPING, params,
                new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        goodDetatileView.onAddCarShopping(response);
                    }
                });

        return true;

    }

    /**
     * 添加收藏
     *
     * @param goodsId
     * @return
     */
    public boolean addCollect(String goodsId, int type) {
        String key = (String) SPUtils.get(mContext, "key", "");
        if (TextUtils.isEmpty(key)) return false;
        Map<String, String> params = getDefaultMD5Params("goods", "collect");
        if (params == null) return false;
        params.put("type", type + "");
        params.put("key", key);
        params.put("id_values", goodsId);

        OkHttpClientManager.postAsyn(mContext, ConfigValue.COLLECT_GOODS, params,
                new BaseDelegate.ResultCallback<BaseModel>() {

                    @Override
                    public void onError(Request request, Object tag, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        goodDetatileView.onCollectGoods(response);
                    }
                });

        return true;
    }

    /**
     * 取消收藏
     *
     * @param goodsId
     * @return
     */
    public boolean cancelCollect(String goodsId, int type) {
        String key = (String) SPUtils.get(mContext, "key", "");
        if (TextUtils.isEmpty(key)) return false;
        Map<String, String> params = getDefaultMD5Params("goods", "qcollect");
        if (params == null) return false;
        params.put("key", key);
        params.put("type", type + "");
        params.put("id_values", goodsId);

        OkHttpClientManager.postAsyn(mContext, ConfigValue.NOCOLLECT_GOODS, params,
                new BaseDelegate.ResultCallback<BaseModel>() {

                    @Override
                    public void onError(Request request, Object tag, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        goodDetatileView.cancelCollectGoods(response);
                    }
                });

        return true;
    }

    //获取店铺是否关注状态

    public void getShopCollectStatus(String shopId) {
        initLoadDialog(mContext);
        mLoadingDialog.show();

        String key = (String) SPUtils.get(mContext, "key", "");
        if (TextUtils.isEmpty(key)) return;
        Map<String, String> params = getDefaultMD5Params("first", "attention");
        if (params == null) return;
        params.put("supplier_id", shopId);
        params.put("key", key);
        OkHttpClientManager.postAsyn(mContext, ConfigValue.APP_IP + "first/attention", params,
                new BaseDelegate.ResultCallback<BaseModel>() {
                    @Override
                    public void onError(Request request, Object tag, Exception e) {
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(BaseModel response, Object tag) {
                        goodDetatileView.getShopCollectStatus(response);
                        mLoadingDialog.dismiss();
                    }
                });
    }
}
