package com.jsyh.xjd.config;

/**
 * Created by Liang on 2015/9/17.
 *  所有的 SharedPreferences Key 保存位置
 *
 * 注意:
 *      1. 字段必须加注释，且内容不能重复。
 *
 *
 */
public interface SPConfig {

    /**
     * 商品展示的模式
     *  0  : linear 模式
     *  1 ： gride 模式
     */
    public static final String GOODS_SHOW_MODEL_KEY = "GoodsShowModel";
    /**
     * 登陆返回的key
     */
    public static final String KEY = "key";

    /**
     * 历史搜索key
     */
    public static final String SEARCH_HISTORY_KEY = "SearchHistoryKey";
    /**
     * 首次启动的标识
     */
    public static final String FIRST = "appfirststart";
}
