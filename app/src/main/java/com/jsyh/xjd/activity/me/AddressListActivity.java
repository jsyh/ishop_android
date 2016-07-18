package com.jsyh.xjd.activity.me;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.personal.AddressRecyclerAdapter;
import com.jsyh.xjd.model.AddressModel;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.presenter.AddressPresenter;
import com.jsyh.xjd.presenter.DeleteAddressPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.AddressView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su on 20160501
 * 收货地址管理
 */
public class AddressListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AddressView {

    private AddressRecyclerAdapter mAddressRecyclerAdapter;
    private RecyclerView mRecyclerView;

    private AddressPresenter mAddressPresenter;
    private DeleteAddressPresenter mDeleteAddressPresenter;

    private List<AddressModel.DataBean> mAddressModelList;

    @Override
    public void initData() {
        super.initData();
        mAddressPresenter = new AddressPresenter(this);
        mAddressModelList = new ArrayList<>();
        mDeleteAddressPresenter = new DeleteAddressPresenter(this);
        mAddressRecyclerAdapter = new AddressRecyclerAdapter(this, mAddressModelList, mDeleteAddressPresenter);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_addresslist);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerViewAddress);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAddressRecyclerAdapter);
        findViewById(R.id.mButtonNewAddress).setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    public void getAddressList() {
        mAddressPresenter.setAddressData(this);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("地址管理");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void initSwipeRefreshLayout() {
        super.initSwipeRefreshLayout();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mButtonNewAddress:
                Utils.startActivityWithAnimation(this, new Intent(this, NewAddressActivity.class));
                break;
        }
    }

    //刷新下拉
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAddressList();
            }
        }, 600);
    }

    @Override
    public void getAddressList(AddressModel response) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (response.getCode().equals("1")) {
            mAddressModelList.clear();
            mAddressModelList.addAll(response.getData());
            if (response.getData().size() == 0)
                mAddressRecyclerAdapter.viewType = -2;
        } else {
            mAddressRecyclerAdapter.viewType = -2;
            ToastUtil.showToast(this,response.getMsg());
        }

        mAddressRecyclerAdapter.notifyDataSetChanged();
        //ToastUtil.showToast(this, response.getMsg());
    }

    @Override
    public void delete(BaseModel model) {
        ToastUtil.showToast(this, model.getMsg());
        getAddressList();
    }
}
