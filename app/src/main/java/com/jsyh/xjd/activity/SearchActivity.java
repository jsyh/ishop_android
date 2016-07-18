package com.jsyh.xjd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.model.SearchModel;
import com.jsyh.xjd.presenter.SearchPresenter;
import com.jsyh.xjd.utils.KeyBoardUtils;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.SearchView;
import com.jsyh.shopping.uilibrary.ClearEditText;
import com.jsyh.shopping.uilibrary.adapter.listview.QuickAdapter;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener,
        SearchView {


    private SearchPresenter mPresenter;

    // -----------------头部---------------------
    private ImageView mBack;              //返回
    private ClearEditText mClearEditText;     //搜索框
    private TextView mSearAction;        //搜索

    //----------------- 热搜 -----------------

    private TagFlowLayout tagFlowLayoutHot;
    private TagFlowLayout tagFlowLayoutHistory;
    private TagAdapter<String> tagAdapterHistory;
    private LinearLayout linearLayout;


    //----------------- 历史 -----------------
    public ArrayList<String> mHistoryDatas;          //历史数据


    //----------------- auto-complete ---------
    private QuickAdapter<SearchModel> mSearchAdapter;


    private int SEARCH_TYPE = 0;        //搜索种类，0默认为搜索宝贝，1为搜索店铺

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_layout);
        findViewById(R.id.rl_search_title_bar).setBackgroundColor(ContextCompat.getColor(this, R.color.nav_color));

        mPresenter = new SearchPresenter(this);

        mHistoryDatas = new ArrayList<>();


        // ------------------- head
        mClearEditText = (ClearEditText) findViewById(R.id.cetSearch);

        mSearAction = (TextView) findViewById(R.id.tvSearAction);
        mSearAction.setOnClickListener(this);

        mBack = (ImageView) findViewById(R.id.ivBack);
        mBack.setOnClickListener(this);
        findViewById(R.id.rlback).setOnClickListener(this);

        // search spinner

        initSpinner();

        //---------------------content

        tagFlowLayoutHot = (TagFlowLayout) findViewById(R.id.tagFlowLayout_search_hot);
        tagFlowLayoutHistory = (TagFlowLayout) findViewById(R.id.tagFlowLayout_search_history);
        linearLayout = (LinearLayout) findViewById(R.id.layoutHistory);
        findViewById(R.id.tRClearHistory).setOnClickListener(this);
        //-----------------------adapter
        mPresenter.loadHotsTag(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistoryList();
    }

    private void getHistoryList() {
        List<String> strings = SPUtils.getHistory(this);
        if (strings != null && !strings.isEmpty()) {        //有历史搜索记录
            mHistoryDatas.clear();
            mHistoryDatas.addAll(strings);
            linearLayout.setVisibility(View.VISIBLE);
            initHistoryHost(mHistoryDatas);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.search_category);
        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.search_spinner_item,
                getResources().getTextArray(R.array.search_category)));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SEARCH_TYPE = position;
                //ToastUtil.showToast(SearchActivity.this, SEARCH_TYPE + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onHostTag(final List<String> datas) {

        if (datas != null && !datas.isEmpty()) {
            tagFlowLayoutHot.setAdapter(new TagAdapter<String>(datas) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.search_tag_item, tagFlowLayoutHot, false);
                    textView.setText(s);
                    return textView;
                }
            });
            tagFlowLayoutHot.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    //ToastUtil.showToast(SearchActivity.this, mHostDatas.get(position));
                    addHistorySearchWithDispatch(datas.get(position));
                    return false;
                }
            });
        }

    }

    private void initHistoryHost(final List<String> datas) {


        tagAdapterHistory = new TagAdapter<String>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.search_tag_item, tagFlowLayoutHistory, false);
                textView.setText(s);
                return textView;
            }
        };
        tagFlowLayoutHistory.setAdapter(tagAdapterHistory);
        tagFlowLayoutHistory.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //ToastUtil.showToast(SearchActivity.this, datas.get(position));
                addHistorySearchWithDispatch(datas.get(position));
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlback:

                KeyBoardUtils.closeKeybord(mClearEditText, this);
                Utils.finishActivityWithAnimation(this);

                break;
            case R.id.ivBack:   //返回
                KeyBoardUtils.closeKeybord(mClearEditText, this);
                Utils.finishActivityWithAnimation(this);
                break;

            case R.id.tvSearAction:     //搜索

                String searchKey = mClearEditText.getText().toString();
                addHistorySearchWithDispatch(searchKey);

                break;

            case R.id.tRClearHistory:          //清空历史

                SPUtils.remove(this, SPConfig.SEARCH_HISTORY_KEY);
                mHistoryDatas.clear();
                getHistoryList();
                break;


        }


    }

    /**
     * 添加历史搜索
     *
     * @param searchKey
     */
    private void addHistorySearchWithDispatch(String searchKey) {
        if (!TextUtils.isEmpty(searchKey)) {
            mClearEditText.setText(searchKey);
            SPUtils.addToHistory(this, searchKey);
            //判断是搜索店铺还是搜索宝贝
            switch (SEARCH_TYPE) {
                case 0:
                    dispatchGoodsListActivity(searchKey);
                    break;
                case 1:
                    dispatchShopListActivity(searchKey);
                    break;
            }
        } else {
            ToastUtil.showToast(this, "请输入搜索关键词！");
        }
    }

    /**
     * 跳转到   店铺列表
     *
     * @param searchKey
     */
    private void dispatchShopListActivity(String searchKey) {
        Intent intent = new Intent(this, ShopsListActivity.class);

        Bundle extras = new Bundle();
        extras.putString("keyword", searchKey);
        intent.putExtras(extras);
        Utils.startActivityWithAnimation(this, intent);
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
