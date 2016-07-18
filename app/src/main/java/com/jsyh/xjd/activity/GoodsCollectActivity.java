package com.jsyh.xjd.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.adapter.goods.CollectGoodsAdapter;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.CollectGoodsModel;
import com.jsyh.xjd.model.CollectShopModel;
import com.jsyh.xjd.presenter.CollectGoodsPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CollectGoodsView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2015/10/20.
 */
public class GoodsCollectActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CollectGoodsView, CollectGoodsAdapter.DeleteCollectListener {

    private CollectGoodsPresenter mPresenter;
    private RecyclerView mRecyclerView;

    private CollectGoodsAdapter mGoodsAdapter;
    private List<CollectGoodsModel.DataBean> mDataBeanList;
    private List<CollectGoodsModel.DataBean> mDelDataBeanList;

    private TextView mTextViewEdit;
    private Button mButtonDelete;
    public boolean checkState = false;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_collect);
        mButtonDelete = (Button) findViewById(R.id.mButtonDelete);
        mButtonDelete.setOnClickListener(this);
        mButtonDelete.setClickable(false);
        mPresenter = new CollectGoodsPresenter(this);
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCollectList(this, 0);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerCollectGoods);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBeanList = new ArrayList<>();
        mDelDataBeanList = new ArrayList<>();
        mGoodsAdapter = new CollectGoodsAdapter(this, mDataBeanList);
        mRecyclerView.setAdapter(mGoodsAdapter);
    }

    @Override
    public void initSwipeRefreshLayout() {
        super.initSwipeRefreshLayout();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("收藏的宝贝");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        mTextViewEdit = (TextView) findViewById(R.id.ensure);
        findViewById(R.id.fl_right).setOnClickListener(this);
        mTextViewEdit.setText("编辑");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.fl_right:
                if (!checkState) {
                    mDelDataBeanList.clear();
                    mTextViewEdit.setText("完成");
                    checkState = true;
                    mButtonDelete.setVisibility(View.VISIBLE);
                } else {
                    mTextViewEdit.setText("编辑");
                    checkState = false;
                    mButtonDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.main_line_color));
                    mButtonDelete.setClickable(false);
                    mButtonDelete.setVisibility(View.GONE);
                }
                mGoodsAdapter.checkState = checkState;
                mGoodsAdapter.notifyDataSetChanged();

                break;
            case R.id.mButtonDelete:
                if (mDelDataBeanList.size() == 0) {
                    ToastUtil.showToast(this, "您未选中任何商品!");
                    return;
                }

                deleteCollectGoods(mDelDataBeanList);
                break;
        }
    }

    private void deleteCollectGoods(final List<CollectGoodsModel.DataBean> delDataBeanList) {
        Utils.showOfficialDialog(this, "提示", "确定取消收藏？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String delGoodsId = "";
                for (CollectGoodsModel.DataBean dataBean : delDataBeanList) {
                    delGoodsId += dataBean.getGoods_id() + ",";
                }
                //ToastUtil.showToast(this, delGoodsId.substring(0, delGoodsId.length() - 1));
                mPresenter.cancelCollect(GoodsCollectActivity.this, delGoodsId, 0);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getCollectList(GoodsCollectActivity.this, 0);
            }
        }, 600);
    }

    @Override
    public void getCollectList(CollectGoodsModel collectGoodsModel) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (collectGoodsModel != null) {
            //ToastUtil.showToast(this, collectGoodsModel.getMsg());
            if (collectGoodsModel.getCode().equals("1")) {
                mDataBeanList.clear();
                mTextViewEdit.setVisibility(View.VISIBLE);
                mDataBeanList.addAll(collectGoodsModel.getData());
            } else {
                mGoodsAdapter.viewType = -2;
                mDataBeanList.clear();
                mTextViewEdit.setVisibility(View.GONE);
                mButtonDelete.setVisibility(View.GONE);
                ConfigValue.uInfor.setAttention("0");
            }
            mGoodsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void delCollectList(BaseModel baseModel) {
        if (baseModel.getCode().endsWith("1")) {
            mDelDataBeanList.clear();
            mPresenter.getCollectList(this, 0);
            mButtonDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.main_line_color));
            ConfigValue.DATA_CHANGE_TAG = true;
        }
        ToastUtil.showToast(this, baseModel.getMsg());
    }

    @Override
    public void deleteCollectListener(CollectGoodsModel.DataBean dataBean, boolean add) {
        if (add) {
            mDelDataBeanList.add(dataBean);
        } else {
            mDelDataBeanList.remove(dataBean);
        }
        if (mDelDataBeanList.size() > 0) {
            mButtonDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.main_text_selected_color));
            mButtonDelete.setClickable(true);
        } else {
            mButtonDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.main_line_color));
            mButtonDelete.setClickable(false);
        }
    }

    @Override
    public void getCollectShopList(CollectShopModel collectShopModel) {

    }
}
