package com.jsyh.xjd.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.github.yasevich.endlessrecyclerview.EndlessRecyclerView;
import com.google.gson.Gson;
import com.jsyh.xjd.R;
import com.jsyh.xjd.adapter.goods.GoodsListOptionsCategoryAdapter;
import com.jsyh.xjd.adapter.goods.GoodsListRecyclerAdapter;
import com.jsyh.xjd.model.GoodsListFiltrateModel;
import com.jsyh.xjd.model.GoodsListModel;
import com.jsyh.xjd.presenter.GoodsFilterPresenter;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.GoodsFilterView;
import com.jsyh.shopping.uilibrary.ClearEditText;
import com.jsyh.shopping.uilibrary.popuwindow.GoodsListOptionPopupWindow;
import com.jsyh.shopping.uilibrary.popuwindow.GoodsListOrderPopupWindow;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * goods list activity
 * Created by Su on 2016/4/19.
 */
public class GoodsListActivity extends BaseActivity implements GoodsFilterView, EndlessRecyclerView.Pager,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener, GoodsListOptionsCategoryAdapter.OnItemClickCallBack, View.OnTouchListener {
    private GoodsFilterPresenter mPresenter;
    private EndlessRecyclerView mRecyclerViewGoodsList;
    private GoodsListRecyclerAdapter adapter;
    private List<GoodsListModel.DataBean.GoodsBean> goodsBeanList;
    //分页
    private int TOTAL_PAGES;//总页数
    private int PAGE_ITEMS;//
    private int page = 1;
    private boolean loading = false;
    //筛选条件
    private int order = 0;//排序方法 排序的类型0综合排序1销量排序2价格由低到高3价格由高到低，4人气，（默认为0）
    private Map<String, String> filtrate;//筛选条件
    //传过来的数据
    private String goods_type;
    private String keyword;
    private String classify_id;
    private String brand_id;

    private int SEARCH_TYPE = 0;        //搜索种类，0默认为搜索宝贝，1为搜索店铺
    private ClearEditText mClearEditText;

    //排序的三个按钮
    private RadioButton mRadioButtonByOrder;
    private RadioButton mRadioButtonBySales;
    private CheckBox mCheckBoxFiltrate;


    //列表还是表格
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;


    //排序
    private GoodsListOrderPopupWindow mGoodsListOrderPopupWindow;


    //筛选
    private GoodsListOptionPopupWindow mGoodsListOptionPopupWindow;
    private View optionsView;
    private RecyclerView mRecyclerViewCategory;
    private GoodsListOptionsCategoryAdapter mCategoryAdapter;
    private List<GoodsListFiltrateModel.DataBean.ClassifyBean> mClassifyBeanList;
    private boolean openFlag = false;//是否已经展开全部分类

    //筛选条件,设置默认值
    private String price_range = "0.0-0.0";
    private int is_fare = 0;//免运费
    private int is_promotion = 0;//促销
    private int p_delivery = 0;//货到付款
    private String classify = null;//筛选id
    private ImageView mImageViewAllCategory;//展开全部分类按钮

    private ObjectAnimator animator;

    @Override
    public void initData() {
        super.initData();
        //第一次进入页面初始化请求搜索参数
        goods_type = getIntent().getStringExtra("goods_type");
        keyword = getIntent().getStringExtra("keyword");
        classify_id = getIntent().getStringExtra("classify_id");
        brand_id = getIntent().getStringExtra("brand_id");
        filtrate = new HashMap<>();
        Log.d("TAG", goods_type + "--------" + keyword + "-------------" + brand_id + "---------" + classify_id);
        //结果
        goodsBeanList = new ArrayList<>();
        mPresenter = new GoodsFilterPresenter(this);

        loadGoodsListByOptions(true);
        //获取筛选条件
    }

    //请求结果
    private void loadGoodsListByOptions(boolean ifClear) {
        Utils.closeSoftInputKeybord(this);
        //判断是否需要清空搜索结果列表
        if (ifClear) {
            goodsBeanList.clear();
        }
        initPageNumber();
        GoodsListRecyclerAdapter.VIEW_TYPE = -10;
        //获取筛选条件
        mPresenter.loadSelectOptions(this, keyword, goods_type, classify_id, brand_id);
        mPresenter.loadGoodsList(this, order, (new Gson()).toJson(filtrate), keyword, classify_id, brand_id, goods_type, page);

    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_goods_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        //排序弹出框
        mGoodsListOrderPopupWindow = new GoodsListOrderPopupWindow(this);
        //筛选弹出框
        optionsView = LayoutInflater.from(this).inflate(R.layout.goods_list_pop_options, null);
        mGoodsListOptionPopupWindow = new GoodsListOptionPopupWindow(this, optionsView);
        //点击重置，确定回调
        mGoodsListOptionPopupWindow.setOnClearConfirmClickListener(new GoodsListOptionPopupWindow.OnCallBack() {
            @Override
            public void onClear() {
                //ToastUtil.showToast(GoodsListActivity.this, "重置点击了");
                classify = null;
                //重置之后置为-1代表没有选中任何条目
                GoodsListOptionsCategoryAdapter.LAST_CHECK_POSITION = -1;
                mRecyclerViewCategory.setAdapter(mCategoryAdapter);
                mCheckBoxFiltrate.setChecked(false);
            }

            @Override
            public void onConfirm(Map<String, String> filtrateMap) {
                if (classify_id != null) {
                    filtrateMap.put("classify", classify_id);
                } else if (filtrateMap.containsKey("classify")) {
                    filtrateMap.remove("classify");
                }
                filtrate.clear();
                filtrate.putAll(filtrateMap);
                Log.d("OPTIONS", filtrate.toString());
                mCheckBoxFiltrate.setChecked(true);
                mGoodsListOptionPopupWindow.dismiss();
                loadGoodsListByOptions(true);
            }
        });
        initContentRecyclerView();
    }

    private void initContentRecyclerView() {
        mRecyclerViewGoodsList = (EndlessRecyclerView) findViewById(R.id.mRecyclerViewGoodsList);
        mRecyclerViewGoodsList.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewGoodsList.setProgressView(R.layout.item_progress);
        mRecyclerViewGoodsList.setPager(this);
        switchLayout(0);
    }

    //切换布局
    private void switchLayout(int type) {
        mRecyclerViewGoodsList.setTag(type);
        switch (type) {
            case 0:
                mRecyclerViewGoodsList.setLayoutManager(mLinearLayoutManager);
                break;
            case 1:
                mRecyclerViewGoodsList.setLayoutManager(mGridLayoutManager);
                break;
        }
        adapter = new GoodsListRecyclerAdapter(this, goodsBeanList);
        mRecyclerViewGoodsList.setAdapter(adapter);
    }


    @Override
    public void initTitle() {
        super.initTitle();

        findViewById(R.id.ivBack).setOnClickListener(this);
        findViewById(R.id.rlback).setOnClickListener(this);
        Spinner mSpinner = (Spinner) findViewById(R.id.search_category);
        mSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.search_spinner_item,
                getResources().getTextArray(R.array.search_category)));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SEARCH_TYPE = position;
                //ToastUtil.showToast(SearchActivity.this, SEARCH_TYPE + "");
                /*TextView textView = (TextView) view;
                textView.setTextColor(Color.parseColor("#999999"));
                textView.setTextSize(16);*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mClearEditText = (ClearEditText) findViewById(R.id.cetSearch);
        findViewById(R.id.tvSearAction).setOnClickListener(this);

        mRadioButtonByOrder = (RadioButton) findViewById(R.id.mRadioButtonByOrder);
        mRadioButtonBySales = (RadioButton) findViewById(R.id.mRadioButtonBySales);
        mCheckBoxFiltrate = (CheckBox) findViewById(R.id.mCheckBoxFiltrate);

        mRadioButtonByOrder.setOnTouchListener(this);

        mRadioButtonBySales.setOnClickListener(this);
        //注册touchevent而不用onClick,返回true，使拦截掉点击事件从而使得状态只能通过弹出框里改变
        mCheckBoxFiltrate.setOnTouchListener(this);
        //切换列表还是表格
        ((CheckBox) findViewById(R.id.mCheckBoxListStyle)).setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ivBack:
            case R.id.rlback:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.tvSearAction:
                keyword = mClearEditText.getText().toString();
                //点击搜索操作，上一个页面传过来的参数全部置为空
                if (!TextUtils.isEmpty(keyword)) {
                    switch (SEARCH_TYPE) {
                        case 0:
                            searchGoods();
                            break;
                        case 1:
                            dispatchShopListActivity();
                            break;
                    }
                    SPUtils.addToHistory(this, keyword);
                } else
                    ToastUtil.showToast(this, "请输入搜索关键词！");
                break;
            /*//综合排序
            case R.id.mRadioButtonByOrder:
                order = 0;
                //loadGoodsListByOptions(true);
                mGoodsListOrderPopupWindow.showAsDropDown(v);
                mGoodsListOrderPopupWindow.setOnItemClickListener(new GoodsListOrderPopupWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int orderType, String name) {
                        //Log.d("RADIO", order + "///" + name + "------------------------------------");
                        order = orderType;
                        mRadioButtonByOrder.setText(name);
                        loadGoodsListByOptions(true);
                    }
                });

                break;*/
            //销量排序
            case R.id.mRadioButtonBySales:
                order = 1;
                loadGoodsListByOptions(true);
                break;
            //点击展开所有分类
            case R.id.mImageViewAllCategory:
                if (!openFlag) {
                    GoodsListOptionsCategoryAdapter.ITEM_COUNT = mClassifyBeanList.size();
                    animator = ObjectAnimator.ofFloat(mImageViewAllCategory, "rotation", 0f, 180f).setDuration(300);
                    openFlag = true;
                } else {
                    if (mClassifyBeanList.size() >= 3)
                        GoodsListOptionsCategoryAdapter.ITEM_COUNT = 3;
                    else GoodsListOptionsCategoryAdapter.ITEM_COUNT = mClassifyBeanList.size();
                    animator = ObjectAnimator.ofFloat(mImageViewAllCategory, "rotation", 180f, 360f).setDuration(300);
                    openFlag = false;
                }
                animator.start();
                //mCategoryAdapter.notifyDataSetChanged();
                mRecyclerViewCategory.setAdapter(mCategoryAdapter);
                break;
        }
    }

    //筛选点击操作

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.mRadioButtonByOrder:
                //order = 0;
                //loadGoodsListByOptions(true);
                mGoodsListOrderPopupWindow.showAsDropDown(v);
                mGoodsListOrderPopupWindow.setOnItemClickListener(new GoodsListOrderPopupWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int orderType, String name) {
                        //Log.d("RADIO", order + "///" + name + "------------------------------------");
                        mRadioButtonByOrder.setChecked(true);
                        order = orderType;
                        mRadioButtonByOrder.setText(name);
                        loadGoodsListByOptions(true);
                    }
                }, mRadioButtonByOrder);
                break;
            case R.id.mCheckBoxFiltrate:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mGoodsListOptionPopupWindow != null)
                        mGoodsListOptionPopupWindow.showAsDropDown(v);
                }
                break;
        }
        return true;
    }

    //搜索条件置空，只按照关键字搜索
    private void initSearchOptions() {
        goods_type = null;
        classify_id = null;
        brand_id = null;
        order = 0;
        filtrate.clear();
    }

    //初始化页码
    private void initPageNumber() {
        page = 1;
        TOTAL_PAGES = 0;
    }

    //数据返回结果
    @Override
    public void onResponse(GoodsListModel goodsListModel) {
        if (goodsListModel != null) {
            if (!goodsListModel.getCode().equals("1")) {
                GoodsListRecyclerAdapter.VIEW_TYPE = -20;
                ToastUtil.showToast(this, goodsListModel.getMsg());
                //修改gridLayout列数为1
                mGridLayoutManager.setSpanCount(1);

            } else {
                //ToastUtil.showToast(this, shopsInfoModel.getMsg());
                if (goodsListModel.getData().getGoods().size() == 0) {
                    GoodsListRecyclerAdapter.VIEW_TYPE = -20;

                    mGridLayoutManager.setSpanCount(1);
                }
                TOTAL_PAGES = goodsListModel.getData().getPage_count();
                if (PAGE_ITEMS == 0) {
                    PAGE_ITEMS = goodsListModel.getData().getGoods().size();
                }
                if (goodsListModel.getData().getGoods().size() > 0) {
                    goodsBeanList.addAll(goodsListModel.getData().getGoods());
                }

                mGridLayoutManager.setSpanCount(2);

            }
            adapter.notifyDataSetChanged();
            //GoodsListRecyclerAdapter.VIEW_TYPE = -10;
            loading = false;
            mRecyclerViewGoodsList.setRefreshing(false);
        }
    }

    //筛选条件返回结果
    @Override
    public void onFiltrateResponse(GoodsListFiltrateModel filtrateModel) {

        if (!filtrateModel.getCode().equals("1")) {
            ToastUtil.showToast(this, filtrateModel.getMsg());
        } else {
            initFiltratePage(filtrateModel);
        }
    }

    //初始化筛选界面
    public void initFiltratePage(GoodsListFiltrateModel filtrateModel) {

        EditText mEditTextMin = (EditText) optionsView.findViewById(R.id.mEditTextMin);
        EditText mEditTextMax = (EditText) optionsView.findViewById(R.id.mEditTextMax);


        String[] price = filtrateModel.getData().getPrice_range().split("\\-");
        if (price.length == 2) {
            mEditTextMin.setText(price[1]);
            mEditTextMax.setText(price[0]);
            mEditTextMin.setHint(price[1]);
            mEditTextMax.setHint(price[0]);
        } else {
            mEditTextMin.setText("0.00");
            mEditTextMax.setText("0.00");
        }

        mImageViewAllCategory = (ImageView) optionsView.findViewById(R.id.mImageViewAllCategory);
        mImageViewAllCategory.setOnClickListener(this);

        mRecyclerViewCategory = (RecyclerView) optionsView.findViewById(R.id.mRecyclerViewCategory);
        mRecyclerViewCategory.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerViewCategory.setItemAnimator(new DefaultItemAnimator());

        mClassifyBeanList = filtrateModel.getData().getClassify();
        mCategoryAdapter = new GoodsListOptionsCategoryAdapter(this, mClassifyBeanList, mRecyclerViewCategory);
        if (openFlag) {
            GoodsListOptionsCategoryAdapter.ITEM_COUNT = mClassifyBeanList.size();
        } else {
            if (mClassifyBeanList.size() >= 3)
                GoodsListOptionsCategoryAdapter.ITEM_COUNT = 3;
            else GoodsListOptionsCategoryAdapter.ITEM_COUNT = mClassifyBeanList.size();
        }
        mRecyclerViewCategory.setAdapter(mCategoryAdapter);
    }

    //选择类别点击之后
    @Override
    public void onItemClick(String itemId) {
        //Utils.showToast(this, itemId + "-------------------");
        //mCategoryAdapter.notifyDataSetChanged();
        Log.d("OPTIONS", itemId + "======================");
        classify = itemId;
    }


    @Override
    public boolean shouldLoad() {
        if (adapter.getItemCount() % PAGE_ITEMS != 0) {
            return false;
        } else
            return !loading && adapter.getItemCount() / PAGE_ITEMS < TOTAL_PAGES;
    }

    @Override
    public void loadNextPage() {
        loading = true;
        page += 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadGoodsListByOptions(false);
            }
        }, 600);
    }

    /**
     * 搜索商品，按照已有条件
     */

    private void searchGoods() {
        initSearchOptions();
        loadGoodsListByOptions(true);
    }

    /**
     * 跳转到   店铺列表
     */
    private void dispatchShopListActivity() {
        Intent intent = new Intent(this, ShopsListActivity.class);
        Bundle extras = new Bundle();
        extras.putString("keyword", keyword);
        intent.putExtras(extras);
        Utils.startActivityWithAnimation(this, intent);
    }

    //切换列表与表格
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            switchLayout(1);
        else switchLayout(0);
    }
}
