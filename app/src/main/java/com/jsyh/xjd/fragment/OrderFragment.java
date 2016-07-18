package com.jsyh.xjd.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsyh.xjd.R;
import com.jsyh.xjd.adapter.personal.OrderListAdapter;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.BusEvent;
import com.jsyh.xjd.model.OrderModelInfo;
import com.jsyh.xjd.presenter.MyOrderPresenter;
import com.jsyh.xjd.presenter.OrderManagerPresenter;
import com.jsyh.xjd.views.MyOrderView;
import com.jsyh.xjd.views.OrderManagerView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements MyOrderView, OrderManagerView, SwipeRefreshLayout.OnRefreshListener {

    private View mView;

    private int currentPosition;
    private Context mContext;

    private MyOrderPresenter mMyOrderPresenter;
    private OrderManagerPresenter mOrderManagerPresenter;
    private RecyclerView recyclerView;
    private OrderListAdapter mOrderListAdapter;
    private List<OrderModelInfo.DataBean> mDataBeanList;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        currentPosition = args.getInt("position", 0);
    }

    public static OrderFragment newInstance(int position) {

        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_order, container, false);
        mContext = getActivity();
        initViews();
        initSwipeRefreshLayout();
        Log.d("order", "onCreateView..." + currentPosition);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        recyclerView = (RecyclerView) mView.findViewById(R.id.mRecyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBeanList = new ArrayList<>();
        mMyOrderPresenter = new MyOrderPresenter(this);
        mOrderManagerPresenter = new OrderManagerPresenter(this);
        mOrderListAdapter = new OrderListAdapter(mContext, mDataBeanList, mOrderManagerPresenter);
        recyclerView.setAdapter(mOrderListAdapter);
    }

    public void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.mSwipeRefreshLayout);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("order", "onResume..." + currentPosition);
        loadOrderList();
    }

    @Subscribe
    public void onEventMainThread(BusEvent event) {
        if (event.getCode().equals("needRefresh")) {
            Log.d("eventBus", "order list need to refresh。。。。。。。");
            loadOrderList();
        }
    }

    public void loadOrderList() {
        mMyOrderPresenter.loadOrder(mContext, currentPosition);
    }

    @Override
    public void error() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void orderList(OrderModelInfo model) {
        if (model.getCode().equals("1")) {
            mDataBeanList.clear();
            mDataBeanList.addAll(model.getData());
        } else {
            mDataBeanList.clear();
            mOrderListAdapter.viewType = -2;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mOrderListAdapter.notifyDataSetChanged();
        //recyclerView.setAdapter(mOrderListAdapter);
    }


    //订单管理
    @Override
    public void result(BaseModel model) {
        ToastUtil.showToast(mContext, model.getMsg());
        //loadOrderList();
        EventBus.getDefault().post(new BusEvent("needRefresh"));
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadOrderList();
            }
        }, 600);
    }


}
