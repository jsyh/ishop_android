package com.jsyh.xjd.config;

import android.os.Environment;

import com.jsyh.xjd.model.UserInfor;

import java.io.File;

/**
 * Created by sks on 2015/9/17.
 */
public class ConfigValue {
    // 请求服务器 成功code
    public static final String Success_Code = "1";
    // 请求服务器失败code
    public static final String Error_Code = "0";

    /**
     * 商品详情页修改购物车数量发送广播
     */
    //public static final String ACTION_ALTER_CARTGOODS_NUMS = "com.jsyh.onlineshopping.activity.mainactivity";


    //请求服务器地址（主页）太郎屋
    public static final String MAIN_URL = "http://139.196.169.165/mobile/";
    public static final String FOUND_URL = "http://139.196.169.165/mobile/stores.php";//店铺街
    //请求服务器地址(接口)
    public static final String APP_IP = "http://192.168.1.134/tailangwu/index.php/";
    //图片请求地址
    public static final String IMG_IP = "http://139.196.169.165";
    public static final String DESCRIPTOR = "http://139.196.169.165/mapp";

    /*请求服务器地址（主页）小京东*/
    /*public static final String MAIN_URL = "http://demo2.ishopv.com/mapp/";
    public static final String FOUND_URL = "http://demo2.ishopv.com/mapp/stores.php";//店铺街
    //请求服务器地址(接口)
    public static final String APP_IP = "http://demo2.ishopv.com/api_demo/index.php/";

    public static final String IMG_IP = "http://demo2.ishopv.com";
    public static final String DESCRIPTOR = "http://demo2.ishopv.com/mapp";*/


    //用户个人资料
    public static UserInfor uInfor = null;
    //数据是否改变标识，若为true，回到首页之后就需要刷新个人信息数据
    public static boolean DATA_CHANGE_TAG = true;
    //若为true，回到购物车页后就刷新购物车
    public static boolean SHOP_CART_TAG = true;
    //请求数据用到的key
    public static String DATA_KEY = "";
    public static final String GoodsInfo = APP_IP + "goods/goodsInfo";

    /**
     * 热搜
     */
    public static final String HOT_SEARCH = APP_IP + "first/keywords";
    /**
     * 加入购物车
     */
    public static final String ADD_CART_SHOPPING = APP_IP + "goods/addcart";
    /**
     * 收藏商品
     */
    public static final String COLLECT_GOODS = APP_IP + "goods/collect";
    /**
     * 取消收藏商品
     */
    public static final String NOCOLLECT_GOODS = APP_IP + "goods/qcollect";

    //购物车列表
    public static final String CartGoodsList = APP_IP + "goods/cartlist";
    //修改商品数量
    public static final String AlterGoodsNumber = APP_IP + "goods/charnum";
    //购物车中删除商品
    public static final String DeleteCartGoods = APP_IP + "goods/delcart";
    //关注商品列表
    public static final String CollectGoodsList = APP_IP + "goods/collectlist";
    //初始化个人信息
    public static final String InitPersonalInfo = APP_IP + "goods/cartnum";
    /**
     * 分类
     */
    public static final String CATEGORY = APP_IP + "first/classify";


    /**
     * 筛选 filter
     */
    public static final String CATEGORY_FILTER = APP_IP + "first/index";

    public static int iconFlag = 0;//用来改变购物车图片的标识，0：未选中，1：选中

    /**
     * 头像所在位置
     */
    public static final String HEAD_PHOTO_DIR = Environment.getExternalStorageDirectory()
            .getPath() + File.separator + "xiaojd" + File.separator + "avatar";

    /**
     * 搜索店铺信息
     */
    public static final String SHOPS_FILTER = APP_IP + "First/shop_sear";

}
