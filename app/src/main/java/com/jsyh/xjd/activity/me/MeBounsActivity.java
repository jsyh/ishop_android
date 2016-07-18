package com.jsyh.xjd.activity.me;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.bouns.MeBounsAdapter;
import com.jsyh.xjd.model.Bouns_me;
import com.jsyh.xjd.model.Bouns_meModel;
import com.jsyh.xjd.presenter.MeBounsPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.MeBounsView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的红包
 * Created by gxc on 2016/3/1.
 */
public class MeBounsActivity extends BaseActivity implements View.OnClickListener, MeBounsView, SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private TextView title;
    private RecyclerView mRecyclerView;
    private MeBounsAdapter adapter;
    private MeBounsPresenter presenter;
    private List<Bouns_me> mBounsMeList;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_mebouns);
        context = this;

        presenter = new MeBounsPresenter(this);

        initRecyclerView();

        presenter.loadBouns(context);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerViewBouns);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBounsMeList = new ArrayList<>();
        adapter = new MeBounsAdapter(this, mBounsMeList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initSwipeRefreshLayout() {
        super.initSwipeRefreshLayout();

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initTitle() {

        title = (TextView) findViewById(R.id.title);
        title.setText("我的红包");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.finishActivityWithAnimation(this);
    }

    //得到红包数据
    @Override
    public void getBouns(Bouns_meModel model) {
        mSwipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadBouns(context);
            }
        }, 600);
    }
}
