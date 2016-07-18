package com.jsyh.xjd.activity.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.goods.OrderExchangeGoodsAdapter;
import com.jsyh.xjd.model.CreateOrderModel;
import com.jsyh.xjd.model.SubmitOrderModel;
import com.jsyh.xjd.presenter.CreateOrderPresenter;
import com.jsyh.xjd.presenter.SubmitOrderPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CreateOrderView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2015/10/8.
 * <p/>
 * 生成订单,积分兑换
 */
public class CreateExchangeOrderActivity extends BaseActivity implements View.OnClickListener, CreateOrderView {
    private CreateOrderPresenter coPresenter;
    private SubmitOrderPresenter soPresenter;

    private String goods_id, goods_attr, goods_number, points, pay_points;

    private int type;

    private TextView mTextViewAddressName, mTextViewAddressPhone, mTextViewAddress, mTextViewTotal;

    private RecyclerView mRecyclerViewOrderGoods;

    private Button mButtonConfirm;

    private List<CreateOrderModel.DataBean.GoodsBean> mGoodsBeanList;
    private OrderExchangeGoodsAdapter mOrderGoodsAdapter;
    private String give_integral;
    //最后能兑换的钱数
    //积分可以兑换的钱

    private BigDecimal ps_price;
    private String ps_id = "";

    private String bounsId = "0";

    private CreateOrderModel.DataBean mDataBean;

    @Override
    public void initData() {
        super.initData();
        coPresenter = new CreateOrderPresenter(this);
        soPresenter = new SubmitOrderPresenter(this);
        goods_attr = getIntent().getStringExtra("goods_attr");
        goods_id = getIntent().getStringExtra("goods_id");
        goods_number = getIntent().getStringExtra("goods_number");

        points = getIntent().getStringExtra("points");
        pay_points = getIntent().getStringExtra("pay_points");

        type = getIntent().getIntExtra("type", 2);
        mGoodsBeanList = new ArrayList<>();
        mOrderGoodsAdapter = new OrderExchangeGoodsAdapter(this, mGoodsBeanList, points);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderInfo();
    }

    private void getOrderInfo() {
        coPresenter.loadOrderInfor(this, type, points, pay_points, goods_number, goods_attr, goods_id);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_create_exchange_order);
        //姓名，电话，红包，积分
        mTextViewAddressName = (TextView) findViewById(R.id.mTextViewAddressName);
        mTextViewAddressPhone = (TextView) findViewById(R.id.mTextViewAddressPhone);
        mTextViewAddress = (TextView) findViewById(R.id.mTextViewAddress);

        mTextViewTotal = (TextView) findViewById(R.id.mTextViewTotal);

        mButtonConfirm = (Button) findViewById(R.id.mButtonConfirm);
        mButtonConfirm.setOnClickListener(this);

        mRecyclerViewOrderGoods = (RecyclerView) findViewById(R.id.mRecyclerViewOrderGoods);
        mRecyclerViewOrderGoods.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewOrderGoods.setAdapter(mOrderGoodsAdapter);
        mRecyclerViewOrderGoods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        findViewById(R.id.mTableRowAddress).setOnClickListener(this);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("确认订单");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mTableRowAddress:
                Utils.startActivityWithAnimation(this, new Intent(this, AddressListActivity.class));
                break;
            case R.id.mButtonConfirm:
                Utils.showOfficialDialog(this, "提示", "确认提交订单？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String integral_money, final_integral;
                        integral_money = "0";
                        final_integral = "0";

                        soPresenter.request(CreateExchangeOrderActivity.this, goods_id, mDataBean.getAddress_id(), points + "", integral_money,
                                goods_attr, "0", ps_price.toString(), ps_price.toString(), ps_id, bounsId, goods_number, type, "","0");
                        Log.d("submitOrder", "goods_Id=" + goods_id + ",mDataBean.getAddress_id()=" + mDataBean.getAddress_id() + ",finalIntegral= " + final_integral + ",integral_money=" +
                                integral_money + ",goods_attr=" + goods_attr + ",totalPrice=" + "" + ",newTotalPrice=" + "" + ",ps_id=" + ps_id + ",psprice=" + ps_price.toString() + ",bounsId=" + bounsId + ",goods_number" +
                                goods_number + ",type=" + type + ",message=" + "");

                    }
                }, null);
                break;
        }
    }

    //获取订单详情结果
    @Override
    public void getOrderInfor(CreateOrderModel model) {
        //有默认地址，code为1
        if (model.getCode().equals("1")) {
            mDataBean = model.getData();

            mTextViewAddressName.setText(mDataBean.getUser_name());
            mTextViewAddressPhone.setText(mDataBean.getMobile());
            mTextViewAddress.setText(mDataBean.getAddress());

            mGoodsBeanList.clear();
            mGoodsBeanList.addAll(mDataBean.getGoods());

            getPeisong(mDataBean.getGoods());

            points = mDataBean.getPost_points();

            mTextViewTotal.setText(points);
            //getTotalPrice(mDataBean.getGoods());
            //calcIntegral(mDataBean.getIntegral(), model.getData().getIntegral_scale(), model.getData().getIntegral_percent());

        } else if (model.getCode().equals("3")) {
            Utils.showOfficialDialog(this, "提示", "当前用户没有默认收货地址，是否立即设置？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Utils.startActivityWithAnimation(CreateExchangeOrderActivity.this, new Intent(CreateExchangeOrderActivity.this, AddressListActivity.class));
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Utils.finishActivityWithAnimation(CreateExchangeOrderActivity.this);
                }
            });
        }
        mOrderGoodsAdapter.notifyDataSetChanged();
    }

    //计算所有邮费与所有配送id
    public void getPeisong(List<CreateOrderModel.DataBean.GoodsBean> goodsBeanList) {
        ps_price = new BigDecimal("0.00");
        ps_id = "";
        if (goodsBeanList.size() > 0) {
            ps_id = goodsBeanList.get(0).getPs_id();
            ps_price = new BigDecimal(goodsBeanList.get(0).getPs_price());
        }
        Log.d("ps_price", ps_price.toString());
    }


    /*//计算积分兑换,100000,2,0.01
    public void calcIntegral(String integral, String integral_scale, String integral_percent) {
        this.integral = integral;
        this.integral_scale = integral_scale;
        this.integral_percent = integral_percent;

        //当无积分，积分等于0，积分小于100，总金额小于100
        if (integral == null || integral.equals("0") || Integer.parseInt(integral) < 100 || Double.parseDouble(totalPrice) < 100d) {
            mSwitchCredits.setClickable(false);
            mTextViewCredits.setText("没有可用积分");
            return;
        }
        //计算积分一共能兑换多少钱。每100积分可兑换0.01元

        BigDecimal exchangeablePriceByIntegral = (new BigDecimal((int) Double.parseDouble(integral) / 100)).multiply(new BigDecimal(integral_percent));

        //计算总金额一共能兑换多少
        BigDecimal exchangeablePriceByTotalPrice = new BigDecimal((int) Double.parseDouble(totalPrice) / 100 * Integer.parseInt(integral_scale));

        if (exchangeablePriceByIntegral.compareTo(exchangeablePriceByTotalPrice) == 1) {
            creditsPrice = exchangeablePriceByTotalPrice;
        } else {
            creditsPrice = exchangeablePriceByIntegral;
        }


        //finalIntegral = creditsPrice.divide(new BigDecimal(integral_percent)).intValue() * 100;

        finalIntegral = creditsPrice.multiply(new BigDecimal("100")).intValue() * 100;
        //最终价格
        //totalPrice = Double.parseDouble(totalPrice) - creditsPrice + "";
        mTextViewCredits.setText("可用" + finalIntegral + "积分抵￥" + creditsPrice + "元");
    }*/

    @Override
    public void result(SubmitOrderModel model) {

        ToastUtil.showToast(this, model.getMsg());
        if (model.getCode().equals("1")) {
            Intent intent = new Intent(this, PayActivity.class);
            //intent.putExtra("ordernumber", model.getData().getOrder_sn());
            String ordernumber = model.getData().getOrder_sn().toString();
            intent.putExtra("ordernumber", ordernumber.substring(1, ordernumber.length() - 1));
            intent.putExtra("ordermoney", ps_price.toString());
            Utils.startActivityWithAnimation(this, intent);
            finish();
        }
    }
}
