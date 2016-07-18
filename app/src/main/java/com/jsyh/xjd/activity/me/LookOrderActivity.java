package com.jsyh.xjd.activity.me;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.personal.OrderDetailListAdapter;
import com.jsyh.xjd.model.OrderInforModel;
import com.jsyh.xjd.presenter.OrderInforPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.OrderInforView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su on 2016/5/4.
 * 查看订单信息
 */
public class LookOrderActivity extends BaseActivity implements View.OnClickListener, OrderInforView {
    private String orderId, orderStatus, servicePhone;
    private OrderInforPresenter mOrderInforPresenter;

    private TextView mTextViewFrom, mTextViewSFSn, mTextViewAddressName, mTextViewAddressPhone, mTextViewAddress;
    private RecyclerView mRecyclerOrderGoods;
    private TextView mTextViewShopName, mTextViewPeisong, mTextViewBouns, mTextViewCredits, mTextViewTotal;
    private TextView mTextViewOrderSn, mTextViewCreateTime, mTextViewPayTime, mTextViewSendTime, mTextViewDealTime;

    private Button mButtonContract, mButtonComment;

    private List<OrderInforModel.DataBean.GoodsBean.GoodsInfoBean> mGoodsInfoBeanList;
    private OrderDetailListAdapter mDetailListAdapter;

    @Override
    public void initData() {
        super.initData();
        orderId = getIntent().getStringExtra("orderId");
        orderStatus = getIntent().getStringExtra("orderStatus");
        servicePhone = getIntent().getStringExtra("service");

        mOrderInforPresenter = new OrderInforPresenter(this);
        mGoodsInfoBeanList = new ArrayList<>();
        mDetailListAdapter = new OrderDetailListAdapter(this, mGoodsInfoBeanList, orderStatus, orderId);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_lookorder);
        mOrderInforPresenter.loadOrderInfor(this, orderId);

        LinearLayout mLayoutSendInfo = (LinearLayout) findViewById(R.id.mLayoutSendInfo);
        TableRow mTableRowPayTime = (TableRow) findViewById(R.id.mTableRowPayTime);
        TableRow mTableRowSendTime = (TableRow) findViewById(R.id.mTableRowSendTime);
        TableRow mTableRowDealTime = (TableRow) findViewById(R.id.mTableRowDealTime);


        mTextViewFrom = (TextView) findViewById(R.id.mTextViewFrom);
        mTextViewSFSn = (TextView) findViewById(R.id.mTextViewSFSn);
        mTextViewAddressName = (TextView) findViewById(R.id.mTextViewAddressName);
        mTextViewAddressPhone = (TextView) findViewById(R.id.mTextViewAddressPhone);
        mTextViewAddress = (TextView) findViewById(R.id.mTextViewAddress);
        findViewById(R.id.mButtonCopy).setOnClickListener(this);
        findViewById(R.id.mButtonCopy1).setOnClickListener(this);

        mTextViewShopName = (TextView) findViewById(R.id.mTextViewShopName);
        mTextViewPeisong = (TextView) findViewById(R.id.mTextViewPeisong);
        mTextViewBouns = (TextView) findViewById(R.id.mTextViewBouns);
        mTextViewCredits = (TextView) findViewById(R.id.mTextViewCredits);
        mTextViewTotal = (TextView) findViewById(R.id.mTextViewTotal);

        mButtonContract = (Button) findViewById(R.id.mButtonContract);
        mButtonContract.setOnClickListener(this);
        mButtonComment = (Button) findViewById(R.id.mButtonComment);
        mButtonComment.setOnClickListener(this);

        mRecyclerOrderGoods = (RecyclerView) findViewById(R.id.mRecyclerViewOrderGoods);
        mRecyclerOrderGoods.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerOrderGoods.setAdapter(mDetailListAdapter);
        mRecyclerOrderGoods.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mTextViewOrderSn = (TextView) findViewById(R.id.mTextViewOrderSn);
        mTextViewCreateTime = (TextView) findViewById(R.id.mTextViewCreateTime);
        mTextViewPayTime = (TextView) findViewById(R.id.mTextViewPayTime);
        mTextViewSendTime = (TextView) findViewById(R.id.mTextViewSendTime);
        mTextViewDealTime = (TextView) findViewById(R.id.mTextViewDealTime);
        //待付款
        if (orderStatus.equals("1")) {
            mLayoutSendInfo.setVisibility(View.GONE);
            mTableRowPayTime.setVisibility(View.GONE);
            mTableRowSendTime.setVisibility(View.GONE);
            mTableRowDealTime.setVisibility(View.GONE);

        } else if (orderStatus.equals("2")) {
            //待发货
            mLayoutSendInfo.setVisibility(View.GONE);
            mTableRowPayTime.setVisibility(View.VISIBLE);
            mTableRowSendTime.setVisibility(View.GONE);
            mTableRowDealTime.setVisibility(View.GONE);

        } else if (orderStatus.equals("3")) {
            //待收货
            mLayoutSendInfo.setVisibility(View.VISIBLE);
            mTableRowPayTime.setVisibility(View.VISIBLE);
            mTableRowSendTime.setVisibility(View.VISIBLE);
            mTableRowDealTime.setVisibility(View.GONE);

        } else if (orderStatus.equals("4")) {
            //待评价
            mLayoutSendInfo.setVisibility(View.VISIBLE);
            mTableRowPayTime.setVisibility(View.VISIBLE);
            mTableRowSendTime.setVisibility(View.VISIBLE);
            mTableRowDealTime.setVisibility(View.VISIBLE);
            mButtonComment.setVisibility(View.VISIBLE);
            mButtonComment.setOnClickListener(this);

        } else if (orderStatus.equals("5")) {
            //取消
            mLayoutSendInfo.setVisibility(View.GONE);
            mTableRowPayTime.setVisibility(View.GONE);
            mTableRowSendTime.setVisibility(View.GONE);
            mTableRowDealTime.setVisibility(View.GONE);
        } else if (orderStatus.equals("6")) {
            //售后中
            mLayoutSendInfo.setVisibility(View.VISIBLE);
            mTableRowPayTime.setVisibility(View.VISIBLE);
            mTableRowSendTime.setVisibility(View.VISIBLE);
            mTableRowDealTime.setVisibility(View.VISIBLE);
        } else if (orderStatus.equals("7")) {
            mLayoutSendInfo.setVisibility(View.VISIBLE);
            mTableRowPayTime.setVisibility(View.VISIBLE);
            mTableRowSendTime.setVisibility(View.VISIBLE);
            mTableRowDealTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("订单详情");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mButtonCopy:
                //复制运单号
                String pasteData = mTextViewSFSn.getText().toString().trim();
                if (TextUtils.isEmpty(pasteData)) {
                    ToastUtil.showToast(this, "单号为空！");
                    return;
                }
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", pasteData);
                clipboardManager.setPrimaryClip(clipData);
                ToastUtil.showToast(this, "已复制到剪切板");

                break;
            case R.id.mButtonCopy1:

                String pasteOrderSn = mTextViewOrderSn.getText().toString().trim();

                if (TextUtils.isEmpty(pasteOrderSn)) {
                    ToastUtil.showToast(this, "订单号为空！");
                    return;
                }
                ClipboardManager clipboardManager1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData1 = ClipData.newPlainText("text", pasteOrderSn);
                clipboardManager1.setPrimaryClip(clipData1);
                ToastUtil.showToast(this, "已复制到剪切板");
                break;

            case R.id.mButtonComment:
                //评论
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putStringArrayListExtra("goodsIds", getGoodsIdPic(mGoodsInfoBeanList));
                Utils.startActivityWithAnimation(mContext, intent);

                break;
            case R.id.mButtonContract:
                if (servicePhone != null && !servicePhone.equals("暂无联系方式")) {
                    Utils.showOfficialDialog((Activity) mContext, "提示", "呼叫" + servicePhone + "？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + servicePhone));
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            mContext.startActivity(intent);
                        }
                    }, null);
                } else ToastUtil.showToast(mContext, "暂无联系方式");
                break;

        }
    }

    //获取所有商品的id与图片地址，和订单号一并传过去

    public ArrayList<String> getGoodsIdPic(List<OrderInforModel.DataBean.GoodsBean.GoodsInfoBean> goods) {
        List<String> list = new ArrayList<>();
        for (OrderInforModel.DataBean.GoodsBean.GoodsInfoBean goodsBean : goods) {
            list.add(goodsBean.getGoods_id() + "@@" + goodsBean.getGoods_thumb());
        }
        return (ArrayList<String>) list;
    }


    @Override
    public void result(OrderInforModel model) {

        if (model.getCode().equals("1")) {
            mTextViewFrom.setText(model.getData().getShipping_name());
            mTextViewSFSn.setText(model.getData().getInvoice_no());
            mTextViewAddressName.setText("收货人：" + model.getData().getConsignee());
            mTextViewAddressPhone.setText(model.getData().getMobile());
            mTextViewAddress.setText("收货地址：" + model.getData().getAddress());

            mTextViewShopName.setText(model.getData().getGoods().get(0).getSupplier_name());

            mGoodsInfoBeanList.addAll(model.getData().getGoods().get(0).getGoodsInfo());
            mDetailListAdapter.notifyDataSetChanged();
            //运费
            mTextViewPeisong.setText("￥" + model.getData().getShipping_fee());
            //红包
            mTextViewBouns.setText("-￥" + model.getData().getBonus());
            //积分
            //mTextViewCredits.setText("-￥" + model.getData().getIntegral_money());
            mTextViewCredits.setText(model.getData().getIntegral());
            //实际付（含运费）
            mTextViewTotal.setText(Utils.getStyledTextGoodsList(mContext,
                    model.getData().getMoney_paid()), TextView.BufferType.SPANNABLE);

            mTextViewOrderSn.setText(model.getData().getOrder_sn());
            //???
            mTextViewCreateTime.setText(Utils.time2(this, Long.parseLong(model.getData().getAdd_time())));
            mTextViewPayTime.setText(Utils.time2(this, Long.parseLong(model.getData().getPay_time())));
            mTextViewSendTime.setText(Utils.time2(this, Long.parseLong(model.getData().getShipping_time())));
            mTextViewDealTime.setText(Utils.time2(this, Long.parseLong(model.getData().getConfirm_time())));
        } else {
            ToastUtil.showToast(this, model.getMsg());
        }
    }
}
