package com.jsyh.xjd.activity.me;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.personal.ReturnListAdapter;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.ReturnGoodsModel;
import com.jsyh.xjd.presenter.ReturnPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.ReturnView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2016/5/4.
 */
public class ReturnActivity extends BaseActivity implements View.OnClickListener, ReturnView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private List<ReturnGoodsModel.DataBean> mDataBeanList;
    private ReturnPresenter mReturnPresenter;
    private ReturnListAdapter mReturnListAdapter;

    @Override
    public void initData() {
        super.initData();
        mReturnPresenter = new ReturnPresenter(this);
        mDataBeanList = new ArrayList<>();
        mReturnListAdapter = new ReturnListAdapter(this, mDataBeanList, mReturnPresenter);
    }

    public void getReturnList() {
        mReturnPresenter.getReturnGoodsList(this);
        mReturnListAdapter.setViewType(-10);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReturnList();
    }

    @Override
    public void initSwipeRefreshLayout() {
        super.initSwipeRefreshLayout();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_return);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerReturnList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mReturnListAdapter);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("售后列表");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showToast(this, msg);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onApplyResponse(BaseModel response) {
        ToastUtil.showToast(this, response.getMsg());
        if (response.getCode().equals("1")) {
            getReturnList();
        }
    }

    @Override
    public void onGetResponse(ReturnGoodsModel returnGoodsModel) {
        mDataBeanList.clear();
        if (returnGoodsModel.getCode().equals("1")) {
            mDataBeanList.addAll(returnGoodsModel.getData());
        } else {
            mReturnListAdapter.setViewType(-20);
        }
        mReturnListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getReturnList();
            }
        }, 600);
    }
}
