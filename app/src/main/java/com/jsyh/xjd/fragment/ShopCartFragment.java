package com.jsyh.xjd.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.MainActivity;
import com.jsyh.xjd.activity.ShoppingCartActivity;
import com.jsyh.xjd.activity.me.CreateOrderActivity;
import com.jsyh.xjd.adapter.cart.CartShopsAdapter;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.ShopCartModel;
import com.jsyh.xjd.presenter.CartGoodsPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CartGoodsView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ShopCartFragment extends BaseFragment implements CartGoodsView, CartShopsAdapter.OnItemSelectListener, View.OnTouchListener {

    private static final String TAG = "leakcanary";
    private View mView;//主布局
    private RecyclerView mRecyclerView;
    private CheckBox mCheckBoxSelectAll;//全选
    private Button mButtonSettleAccount;//结算
    private TextView mTextViewTotalPrice;//总价
    private TableRow mTableRowTotalPrice;//总价布局
    private LinearLayout mLayoutSettleAccount;//底部布局
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean settleAccountDeleteFlag = true;//结算还是删除标记


    private Context mContext;
    private CartGoodsPresenter mCartGoodsPresenter;

    private CartShopsAdapter mCartShopsAdapter;
    private List<ShopCartModel.DataBean> mDataBeanList;

    private List<ShopCartModel.DataBean.GoodsBean> mSelectedGoodsBeanList = new ArrayList<>();


    private boolean index = false;//是多选删除还是单个长按删除true为多选删除
    private ShopCartModel.DataBean.GoodsBean mGoodsBean;//单选要删除的


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (mView == null) {
            Log.d("cart", "fragment----重新创建？");
            mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_shop_cart, container, false);
            Log.d("cart", "fragment----onCreateView？");
            mCartGoodsPresenter = new CartGoodsPresenter(this);
            //getShopCartGoodsList();
            initViews();
        }
        return mView;
    }


    private void getShopCartGoodsList() {
        mCartGoodsPresenter.getCartGoodsData(mContext);
        Log.d("cart", "查询方法执行了");
    }


    private void initViews() {
        Log.d("cart", "initView方法执行了");
        initSwipeRefreshLayout();
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.mRecyclerViewCartShopsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBeanList = new ArrayList<>();
        mCartShopsAdapter = new CartShopsAdapter(mContext, mDataBeanList);
        mRecyclerView.setAdapter(mCartShopsAdapter);
        mCartShopsAdapter.setOnItemSelectListener(this);


        mLayoutSettleAccount = (LinearLayout) mView.findViewById(R.id.mLayoutSettleAccount);
        mCheckBoxSelectAll = (CheckBox) mView.findViewById(R.id.mCheckBoxSelectAll);
        mCheckBoxSelectAll.setOnTouchListener(this);
        mTableRowTotalPrice = (TableRow) mView.findViewById(R.id.mTableRowTotalPrice);
        mTextViewTotalPrice = (TextView) mView.findViewById(R.id.mTextViewTotalPrice);
        mTextViewTotalPrice.setText(Utils.getStyledTextCartGoodsPrice(mContext, "0.00"), TextView.BufferType.SPANNABLE);
        mButtonSettleAccount = (Button) mView.findViewById(R.id.mButtonSettleAccount);
        mButtonSettleAccount.setOnClickListener(this);
    }


    public void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.mSwipeRefreshLayout);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getShopCartGoodsList();
                    }
                }, 600);
            }
        });
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initTitle() {
        super.initTitle();
        title.setText("购物车");
        back.setVisibility(View.GONE);
        ensure.setText("编辑");
        right.setOnClickListener(this);
        if (mContext instanceof ShoppingCartActivity) {
            back.setVisibility(View.VISIBLE);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.finishActivityWithAnimation(mContext);
                }
            });
        }
    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_right:
                switchLayout();
                break;
            case R.id.mButtonSettleAccount:
                if (mSelectedGoodsBeanList.size() > 0) {

                    if (settleAccountDeleteFlag) {
                        //ToastUtil.showToast(mContext, "结算");


                        Intent intent = new Intent(mContext, CreateOrderActivity.class);
                        intent.putExtra("type", 0);
                        intent.putExtra("goods_id", getRecIdStr());
                        Utils.startActivityWithAnimation(mContext, intent);

                        mCartShopsAdapter.mAllSelectGoodsBeanList.clear();//去结算之后情况已选择的集合
                        mSelectedGoodsBeanList.clear();
                    } else {
                        //ToastUtil.showToast(mContext, "删除");
                        Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除此商品?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                index = true;
                                mCartGoodsPresenter.deleteCartGoods(mContext, getRecIdStr());
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                } else ToastUtil.showToast(mContext, "请选择商品！");

                break;

        }

    }


    //根据选择的商品得到所有商品id
   /* public String getAllGoodsId() {
        String tmp = "";
        for (ShopCartModel.DataBean.GoodsBean goodsBean : mSelectedGoodsBeanList) {
            tmp += goodsBean.getGoods_id() + ",";
        }
        return tmp.substring(0, tmp.length() - 1);
    }*/

    //根据选择的商品得到rec_id
    public String getRecIdStr() {
        if (mSelectedGoodsBeanList.size() > 0) {
            String tmp = "";
            for (ShopCartModel.DataBean.GoodsBean goodsBean : mSelectedGoodsBeanList) {
                tmp += goodsBean.getRec_id() + ",";
            }
            return tmp.substring(0, tmp.length() - 1);
        }
        return null;
    }


    //点击编辑切换页面布局
    private void switchLayout() {
        if (settleAccountDeleteFlag) {
            ensure.setText("完成");
            mTableRowTotalPrice.setVisibility(View.INVISIBLE);
            mButtonSettleAccount.setText("删除(" + mSelectedGoodsBeanList.size() + ")");
            mButtonSettleAccount.setBackgroundColor(Color.parseColor("#ff3b30"));


            settleAccountDeleteFlag = false;
        } else {
            ensure.setText("编辑");
            mTableRowTotalPrice.setVisibility(View.VISIBLE);
            mButtonSettleAccount.setText("结算(" + mSelectedGoodsBeanList.size() + ")");
            mButtonSettleAccount.setBackgroundColor(Color.parseColor("#ff5000"));

            settleAccountDeleteFlag = true;
        }
    }

    //选择商品的回调

    @Override
    public void onItemSelectListener(List<ShopCartModel.DataBean.GoodsBean> goodsBeanList) {
        if (goodsBeanList != null) {
            mSelectedGoodsBeanList.clear();
            mSelectedGoodsBeanList.addAll(goodsBeanList);
            //ToastUtil.showToast(mContext, goodsBeanList.size() + ".......................");
            //Log.d("cart", "前台显示选中全部商品个数为：--------" + goodsBeanList.size() + "");
            // mTextViewTotalPrice.setText(getTotalPrice()+"");
        } else {
            mSelectedGoodsBeanList.clear();
        }

        if (mDataBeanList.size() > 0) {
            if (getGoodsItemsCount(mDataBeanList) == mSelectedGoodsBeanList.size()) {
                mCheckBoxSelectAll.setChecked(true);
            } else {
                mCheckBoxSelectAll.setChecked(false);
            }
        }
        mTextViewTotalPrice.setText(Utils.getStyledTextCartGoodsPrice(mContext, getTotalPrice()), TextView.BufferType.SPANNABLE);
        if (!settleAccountDeleteFlag) {
            mButtonSettleAccount.setText("删除(" + mSelectedGoodsBeanList.size() + ")");
        } else {
            mButtonSettleAccount.setText("结算(" + mSelectedGoodsBeanList.size() + ")");
        }

    }

    @Override
    public void onItemDeleteListener(ShopCartModel.DataBean.GoodsBean goodsBean) {
        index = false;
        mGoodsBean = goodsBean;
        mCartGoodsPresenter.deleteCartGoods(mContext, goodsBean.getRec_id());
    }

    //修改购物车商品数量回调接口
    @Override
    public void onAlterGoodsNumListener(ShopCartModel.DataBean.GoodsBean goodsBean, String newNum) {
        if (goodsBean.getNumber().equals(newNum) && newNum.equals("1")) {
            ToastUtil.showToast(mContext, "不能再少了亲!");
            return;
        }
        mCartGoodsPresenter.alterCartGoodsNumber(mContext, goodsBean.getRec_id(), Integer.parseInt(newNum));
    }

    //获取所有商品数量
    public int getGoodsItemsCount(List<ShopCartModel.DataBean> dataBeanList) {
        int count = 0;
        if (dataBeanList.size() > 0) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                count += dataBeanList.get(i).getGoods().size();
            }
        }
        return count;
    }

    //获取所有商品的价格总和
    public String getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0.00);
        if (mSelectedGoodsBeanList.size() > 0) {
            for (ShopCartModel.DataBean.GoodsBean goodsBean : mSelectedGoodsBeanList) {
                BigDecimal singlePrice = new BigDecimal(goodsBean.getGoods_price());
                BigDecimal number = new BigDecimal(goodsBean.getNumber());
                totalPrice = totalPrice.add(singlePrice.multiply(number));
            }
            Log.d("shopcart", totalPrice.toString());
            return totalPrice.toString();
        }
        return "0.00";
    }

    //获取所有商品

    public List<ShopCartModel.DataBean.GoodsBean> getAllGoods() {
        List<ShopCartModel.DataBean.GoodsBean> goodsBeanList = new ArrayList<>();
        if (mDataBeanList.size() > 0) {
            for (ShopCartModel.DataBean dataBean : mDataBeanList) {
                goodsBeanList.addAll(dataBean.getGoods());
            }
        }
        return goodsBeanList;
    }

    //全选按钮
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!mCheckBoxSelectAll.isChecked()) {
                mCheckBoxSelectAll.setChecked(true);
                mCartShopsAdapter.mAllSelectGoodsBeanList.clear();
                mCartShopsAdapter.mAllSelectGoodsBeanList.addAll(getAllGoods());
                mCartShopsAdapter.notifyDataSetChanged();
                onItemSelectListener(getAllGoods());
            } else {
                mCheckBoxSelectAll.setChecked(false);
                mCartShopsAdapter.mAllSelectGoodsBeanList.clear();
                mCartShopsAdapter.notifyDataSetChanged();
                onItemSelectListener(null);
            }
        }
        return true;
    }

    @Override
    public void onShopCartList(ShopCartModel shopCartModel) {
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d("shopcart", shopCartModel.toString());
        mDataBeanList.clear();
        if (shopCartModel.getCode().equals("1")) {
            mDataBeanList.addAll(shopCartModel.getData());
            mLayoutSettleAccount.setVisibility(View.VISIBLE);
            ensure.setVisibility(View.VISIBLE);
        } else {
            mCartShopsAdapter.viewType = -20;
            mLayoutSettleAccount.setVisibility(View.GONE);
            ensure.setVisibility(View.GONE);
        }
        //如果新查询的数据大小大于已经选择的则不全选

        if (getActivity() instanceof MainActivity)
            ((MainActivity) mContext).setCartNums(getCartGoodsNums(mDataBeanList));

        mCartShopsAdapter.notifyDataSetChanged();
        //onItemSelectListener(mSelectedGoodsBeanList);
    }

    //根据返回值获取购物车中商品数量
    public String getCartGoodsNums(List<ShopCartModel.DataBean> dataBeanList) {
        if (dataBeanList.size() > 0) {
            int temp = 0;
            for (ShopCartModel.DataBean dataBean : dataBeanList) {
                for (ShopCartModel.DataBean.GoodsBean goodsBean : dataBean.getGoods()) {
                    String number = goodsBean.getNumber();
                    temp += Integer.parseInt(number);
                }
            }
            return temp + "";
        } else return "0";
    }


    @Override
    public void alterCartGoodsNumber(BaseModel baseModel) {
        if (!baseModel.getCode().equals("1")) {
            ToastUtil.showToast(mContext, baseModel.getMsg());
        }
        getShopCartGoodsList();
    }

    @Override
    public void deleteCartGoods(BaseModel baseModel) {
        ToastUtil.showToast(mContext, baseModel.getMsg());
        if (baseModel.getCode().equals("1")) {
            if (index) {
                mCartShopsAdapter.mAllSelectGoodsBeanList.clear();//删除之后没有选中的，将集合清空
                mSelectedGoodsBeanList.clear();
            } else {
                if (mCartShopsAdapter.mAllSelectGoodsBeanList.contains(mGoodsBean))
                    mCartShopsAdapter.mAllSelectGoodsBeanList.remove(mGoodsBean);
                if (mSelectedGoodsBeanList.contains(mGoodsBean)) {
                    mSelectedGoodsBeanList.remove(mGoodsBean);
                }
            }
        }
        if (mContext instanceof ShoppingCartActivity)
            ConfigValue.DATA_CHANGE_TAG = true;
        //ToastUtil.showToast(mContext,"从详情来的！");
        getShopCartGoodsList();
    }

    @Override
    public void onResume() {
        super.onResume();
        getShopCartGoodsList();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: shopCartFragment 执行了");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: shopCartFragment 执行了");
        CartShopsAdapter.mOnItemSelectListener = null;
    }
}
