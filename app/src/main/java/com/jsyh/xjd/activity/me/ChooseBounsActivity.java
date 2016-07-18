package com.jsyh.xjd.activity.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.bouns.ChooseBounsAdapter;
import com.jsyh.xjd.model.Bouns;
import com.jsyh.xjd.model.BounsModel;
import com.jsyh.xjd.presenter.EnvelopePresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.EnvelopeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2016/5/3.
 */
public class ChooseBounsActivity extends BaseActivity implements EnvelopeView, View.OnClickListener {

    private Context context;
    private TextView title;
    private RecyclerView mRecyclerView;
    private ChooseBounsAdapter adapter;
    private EnvelopePresenter presenter;
    private List<Bouns> mBounsMeList;
    private String totalPrice;

    @Override
    public void initData() {
        super.initData();
        totalPrice = getIntent().getStringExtra("total");
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_mebouns);
        context = this;

        presenter = new EnvelopePresenter(this);

        initRecyclerView();

        presenter.request(context, totalPrice);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerViewBouns);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBounsMeList = new ArrayList<>();
        adapter = new ChooseBounsAdapter(this, mBounsMeList);
        mRecyclerView.setAdapter(adapter);
    }

    /*   @Override
       public void initSwipeRefreshLayout() {
           super.initSwipeRefreshLayout();
           mSwipeRefreshLayout.setOnRefreshListener(this);
       }
   */
    @Override
    public void initTitle() {

        title = (TextView) findViewById(R.id.title);
        title.setText("选择红包");
        ((TextView) findViewById(R.id.ensure)).setText("取消选择");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        findViewById(R.id.fl_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.fl_right:
                Intent intent = new Intent(mContext, CreateOrderActivity.class);
                intent.putExtra("title", "选择红包");
                ((Activity) mContext).setResult(-20, intent);
                Utils.finishActivityWithAnimation(mContext);
                break;
        }
    }


    @Override
    public void result(BounsModel model) {
        if (null != model) {
            if (model.getCode().equals("1")) {
                mBounsMeList.clear();
                mBounsMeList.addAll(model.getData());
                if (model.getData() != null && model.getData().size() == 0) {
                    adapter.viewType = -2;
                }
            } else {
                adapter.viewType = -2;
            }
            adapter.notifyDataSetChanged();
        }
    }
}
