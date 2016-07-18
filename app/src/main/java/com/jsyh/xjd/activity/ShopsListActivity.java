package com.jsyh.xjd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.yasevich.endlessrecyclerview.EndlessRecyclerView;
import com.jsyh.xjd.R;
import com.jsyh.xjd.adapter.shops.ShopListRecyclerAdapter;
import com.jsyh.xjd.model.ShopsInfoModel;
import com.jsyh.xjd.presenter.ShopsFilterPresenter;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.ShopsFilterView;
import com.jsyh.shopping.uilibrary.ClearEditText;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索店铺列表
 * Created by Su on 2016/4/13.
 */
public class ShopsListActivity extends BaseActivity implements View.OnClickListener, ShopsFilterView, EndlessRecyclerView.Pager {

    private ClearEditText mClearEditText;
    private Spinner mSpinner;
    private String keyWord;             //搜索关键字
    private int TOTAL_PAGES;//总页数
    private int PAGE_ITEMS = 0;//一页多少条
    private int page = 1;
    private ShopsFilterPresenter mPresenter;
    private EndlessRecyclerView mRecyclerViewShopList;//店铺列表
    //private PullRefreshLayout pullRefreshLayout;
    private boolean loading = false;
    private ShopListRecyclerAdapter adapter;
    private List<ShopsInfoModel.DataBean.InfoBean> infoBeanList;
    private int SEARCH_TYPE = 0;        //搜索种类，0默认为搜索宝贝，1为搜索店铺

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        keyWord = bundle.getString("keyword", "");
        mPresenter = new ShopsFilterPresenter(this);
        loadShopList();
    }

    private void loadShopList() {
        ShopListRecyclerAdapter.VIEW_TYPE = -10;
        mPresenter.getShopsFilterData(this, page + "", keyWord);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_shops_list);
        initContentRecyclerView();
        //initPullRefreshLayout();

    }

    private void initContentRecyclerView() {
        mRecyclerViewShopList = (EndlessRecyclerView) findViewById(R.id.mRecyclerViewShopList);
        mRecyclerViewShopList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewShopList.setProgressView(R.layout.item_progress);
        mRecyclerViewShopList.setPager(this);
        infoBeanList = new ArrayList<>();
        adapter = new ShopListRecyclerAdapter(this, infoBeanList);
        mRecyclerViewShopList.setAdapter(adapter);
    }
/*

    //商品列表下拉刷新
    private void initPullRefreshLayout() {
        pullRefreshLayout = (PullRefreshLayout) findViewById(R.id.pullRefreshLayout);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_Bitmap);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getShopsFilterData(ShopsListActivity.this, page + "", keyWord);
                        pullRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }

            @Override
            public void onMove(boolean ismove) {

            }
        });
    }
*/

    @Override
    public void initTitle() {

        findViewById(R.id.ivBack).setOnClickListener(this);
        findViewById(R.id.rlback).setOnClickListener(this);
        mSpinner = (Spinner) findViewById(R.id.search_category);
        mSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                getResources().getTextArray(R.array.search_category)));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SEARCH_TYPE = position;
                //ToastUtil.showToast(SearchActivity.this, SEARCH_TYPE + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner.setSelection(1);
        mClearEditText = (ClearEditText) findViewById(R.id.cetSearch);
        mClearEditText.setText(keyWord);
        mClearEditText.clearFocus();
        findViewById(R.id.tvSearAction).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
            case R.id.rlback:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.tvSearAction:
                String searchKey = mClearEditText.getText().toString();
                addHistorySearchWithDispatch(searchKey);
                break;
        }
    }
    //搜索结果回调

    @Override
    public void onResponse(ShopsInfoModel shopsInfoModel) {

        if (!shopsInfoModel.getCode().equals("1")) {
            ShopListRecyclerAdapter.VIEW_TYPE = -20;
            ToastUtil.showToast(this, shopsInfoModel.getMsg());
        } else {
            //ToastUtil.showToast(this, shopsInfoModel.getMsg());
            if (shopsInfoModel.getData().getInfo().size() == 0) {
                ShopListRecyclerAdapter.VIEW_TYPE = -20;
            }
            TOTAL_PAGES = shopsInfoModel.getData().getPage_total();
            if (PAGE_ITEMS == 0)
                PAGE_ITEMS = shopsInfoModel.getData().getInfo().size();
            if (shopsInfoModel.getData().getInfo().size() > 0) {
                infoBeanList.addAll(shopsInfoModel.getData().getInfo());
            }

        }
        adapter.notifyDataSetChanged();
        loading = false;
        mRecyclerViewShopList.setRefreshing(false);
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
                loadShopList();
            }
        }, 600);
    }


    /**
     * 添加历史搜索
     *
     * @param searchKey
     */
    private void addHistorySearchWithDispatch(String searchKey) {
        if (!TextUtils.isEmpty(searchKey)) {
            SPUtils.addToHistory(this, searchKey);
            //判断是搜索店铺还是搜索宝贝
            switch (SEARCH_TYPE) {
                case 0:
                    dispatchGoodsListActivity(searchKey);
                    break;
                case 1:
                    //dispatchShopListActivity(searchKey);
                    page = 1;
                    TOTAL_PAGES = 0;
                    infoBeanList.clear();
                    mPresenter.getShopsFilterData(this, page + "", searchKey);
                    break;
            }
        } else {
            ToastUtil.showToast(this, "请输入搜索关键词！");
        }
    }

    /**
     * 跳转到   商品列表
     *
     * @param keyword
     */
    public void dispatchGoodsListActivity(@NonNull String keyword) {
        Intent intent = new Intent(this, GoodsListActivity.class);

        Bundle extras = new Bundle();
        extras.putString("keyword", keyword);
        intent.putExtras(extras);
        Utils.startActivityWithAnimation(this, intent);
    }
}
