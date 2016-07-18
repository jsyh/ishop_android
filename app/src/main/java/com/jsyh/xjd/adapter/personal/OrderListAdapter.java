package com.jsyh.xjd.adapter.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.CommentActivity;
import com.jsyh.xjd.activity.me.LookOrderActivity;
import com.jsyh.xjd.activity.me.PayActivity;
import com.jsyh.xjd.model.OrderModelInfo;
import com.jsyh.xjd.presenter.OrderManagerPresenter;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {


    private Context mContext;
    private List<OrderModelInfo.DataBean> mDataBeanList;
    public int viewType = -1;
    private OrderManagerPresenter mOrderManagerPresenter;

    public OrderListAdapter(Context context, List<OrderModelInfo.DataBean> dataBeanList, OrderManagerPresenter managerPresenter) {
        mContext = context;
        mDataBeanList = dataBeanList;
        mOrderManagerPresenter = managerPresenter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == -1) {
            View loadingView = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false);
            return new MyViewHolder(loadingView);
        } else if (viewType == -2) {
            View emptyView = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.personal_order_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("暂无相关订单");
            return new MyViewHolder(emptyView);
        }
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_order_shop_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("recycler", "getItemViewType------------------");
        if (mDataBeanList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mDataBeanList.size() > 0) {
            final OrderModelInfo.DataBean dataBean = mDataBeanList.get(position);
            holder.mTextViewShopName.setText(dataBean.getShop_name());
            holder.mTextViewGoodsNum.setText("共" + dataBean.getGoods().size() + "件商品");
            holder.mTextViewTotalPrice.setText(Utils.getStyledTextCartGoodsPrice(mContext,
                    (new BigDecimal(dataBean.getTotal())).toString()), TextView.BufferType.SPANNABLE);
            holder.mRecyclerViewOrderGoodsList.setLayoutManager(new LinearLayoutManager(mContext));
            holder.mRecyclerViewOrderGoodsList.setAdapter(new OrderGoodsAdapter2(dataBean.getGoods()));
            //进入订单详情
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LookOrderActivity.class);
                    intent.putExtra("orderId", dataBean.getOrder_id());
                    intent.putExtra("orderStatus", dataBean.getStatus());
                    intent.putExtra("service", dataBean.getService_phone());
                    Utils.startActivityWithAnimation(mContext, intent);
                }
            });
            holder.mRecyclerViewOrderGoodsList.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Intent intent = new Intent(mContext, LookOrderActivity.class);
                        intent.putExtra("orderId", dataBean.getOrder_id());
                        intent.putExtra("orderStatus", dataBean.getStatus());
                        intent.putExtra("service", dataBean.getService_phone());

                        Utils.startActivityWithAnimation(mContext, intent);
                    }
                    return false;
                }
            });

            switch (Integer.parseInt(dataBean.getStatus())) {
                case 0:

                    break;
                case 1:
                    //待付款
                    holder.mTextViewOrderStatus.setText("等待买家付款");

                    holder.mTableRowBottomButton.setVisibility(View.VISIBLE);
                    holder.mButton1.setVisibility(View.VISIBLE);
                    holder.mButton2.setVisibility(View.VISIBLE);
                    holder.mButton3.setVisibility(View.VISIBLE);

                    holder.mButton1.setText("联系卖家");
                    holder.mButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "联系卖家");
                            if (!dataBean.getService_phone().equals("暂无联系方式")) {
                                Utils.showOfficialDialog((Activity) mContext, "提示", "呼叫" + dataBean.getService_phone() + "？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dataBean.getService_phone()));
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
                            } else ToastUtil.showToast(mContext, dataBean.getService_phone());
                        }
                    });
                    holder.mButton2.setText("取消订单");
                    holder.mButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.showOfficialDialog((Activity) mContext, "提示", "确认取消此订单？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderManagerPresenter.cancelOrder(mContext, dataBean.getOrder_id());
                                }
                            }, null);
                        }
                    });
                    holder.mButton3.setText("付款");
                    holder.mButton3.setBackgroundResource(R.drawable.order_button_bg_shape_red);
                    holder.mButton3.setTextColor(Color.parseColor("#ff5000"));
                    holder.mButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, PayActivity.class);
                            intent.putExtra("ordernumber", dataBean.getOrder_id());
                            intent.putExtra("ordermoney", dataBean.getTotal() + "");
                            Utils.startActivityWithAnimation(mContext, intent);
                        }
                    });
                    break;
                case 2:
                    //待发货
                    holder.mTextViewOrderStatus.setText("买家已付款");
                    holder.mTableRowBottomButton.setVisibility(View.GONE);
                    /*holder.mButton3.setText("评价");
                    holder.mButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "去评价");
                            Intent intent = new Intent(mContext, CommentActivity.class);
                            intent.putExtra("orderId", dataBean.getOrder_id());
                            intent.putStringArrayListExtra("goodsIds",getGoodsIdPic(dataBean.getGoods()));
                            Utils.startActivityWithAnimation(mContext, intent);
                        }
                    });*/
                    break;
                case 3:
                    //代收货
                    holder.mTextViewOrderStatus.setText("卖家已发货");

                    holder.mTableRowBottomButton.setVisibility(View.VISIBLE);

                    holder.mButton1.setVisibility(View.GONE);
                    holder.mButton2.setVisibility(View.GONE);
                    holder.mButton3.setVisibility(View.VISIBLE);

                    /*holder.mButton2.setText("物流信息");
                    holder.mButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.showToast(mContext, "物流信息");
                        }
                    });*/
                    holder.mButton3.setText("确认收货");
                    holder.mButton3.setBackgroundResource(R.drawable.order_button_bg_shape_red);
                    holder.mButton3.setTextColor(Color.parseColor("#ff5000"));
                    holder.mButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.showOfficialDialog((Activity) mContext, "提示", "确认收货？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderManagerPresenter.sureOrder(mContext, dataBean.getOrder_id());
                                }
                            }, null);
                        }
                    });
                    break;
                case 4:
                    //待评价
                    holder.mTextViewOrderStatus.setText("交易成功");

                    holder.mTableRowBottomButton.setVisibility(View.VISIBLE);
                    holder.mButton1.setVisibility(View.GONE);
                    holder.mButton2.setVisibility(View.VISIBLE);
                    holder.mButton3.setVisibility(View.VISIBLE);

                    holder.mButton2.setText("删除订单");
                    holder.mButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "删除订单");
                            Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除订单？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderManagerPresenter.deleteOrder(mContext, dataBean.getOrder_id());
                                }
                            }, null);
                        }
                    });
                    holder.mButton3.setText("评价");
                    holder.mButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "去评价");
                            Intent intent = new Intent(mContext, CommentActivity.class);
                            intent.putExtra("orderId", dataBean.getOrder_id());
                            intent.putStringArrayListExtra("goodsIds", getGoodsIdPic(dataBean.getGoods()));
                            Utils.startActivityWithAnimation(mContext, intent);
                        }
                    });
                    break;
                case 5:
                    //取消订单
                    holder.mTextViewOrderStatus.setText("交易关闭");

                    holder.mTableRowBottomButton.setVisibility(View.VISIBLE);
                    holder.mButton1.setVisibility(View.GONE);
                    holder.mButton2.setVisibility(View.VISIBLE);
                    holder.mButton3.setVisibility(View.GONE);

                    holder.mButton2.setText("删除订单");
                    holder.mButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "删除订单");
                            Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除订单？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderManagerPresenter.deleteOrder(mContext, dataBean.getOrder_id());
                                }
                            }, null);
                        }
                    });
                    break;
                case 6:
                    //申请退货中
                    holder.mTextViewOrderStatus.setText("退款/售后");
                    holder.mTableRowBottomButton.setVisibility(View.VISIBLE);

                    holder.mButton1.setVisibility(View.GONE);
                    holder.mButton2.setVisibility(View.VISIBLE);
                    holder.mButton3.setVisibility(View.GONE);

                    holder.mButton2.setText("删除订单");
                    holder.mButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "删除订单");
                            Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除订单？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderManagerPresenter.deleteOrder(mContext, dataBean.getOrder_id());
                                }
                            }, null);
                        }
                    });
                    break;
                case 7:
                    //评价完毕的
                    holder.mTextViewOrderStatus.setText("已评价");

                    holder.mTableRowBottomButton.setVisibility(View.VISIBLE);
                    holder.mButton1.setVisibility(View.GONE);
                    holder.mButton2.setVisibility(View.VISIBLE);
                    holder.mButton3.setVisibility(View.GONE);

                    holder.mButton2.setText("删除订单");
                    holder.mButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ToastUtil.showToast(mContext, "删除订单");
                            Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除订单？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderManagerPresenter.deleteOrder(mContext, dataBean.getOrder_id());
                                }
                            }, null);
                        }
                    });
                    break;
            }

        }
    }

    //获取所有商品的id与图片地址，和订单号一并传过去

    public ArrayList<String> getGoodsIdPic(List<OrderModelInfo.DataBean.GoodsBean> goods) {
        List<String> list = new ArrayList<>();
        for (OrderModelInfo.DataBean.GoodsBean goodsBean : goods) {
            list.add(goodsBean.getGoods_id() + "@@" + goodsBean.getGoods_thumb());
        }
        return (ArrayList<String>) list;
    }


    @Override
    public int getItemCount() {
        return mDataBeanList.size() > 0 ? mDataBeanList.size() : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewShopName;
        TextView mTextViewOrderStatus;
        RecyclerView mRecyclerViewOrderGoodsList;
        TextView mTextViewGoodsNum;
        TextView mTextViewTotalPrice;
        TableRow mTableRowBottomButton;
        Button mButton1;
        Button mButton2;
        Button mButton3;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewShopName = (TextView) itemView.findViewById(R.id.mTextViewShopName);
            mTextViewOrderStatus = (TextView) itemView.findViewById(R.id.mTextViewOrderStatus);
            mRecyclerViewOrderGoodsList = (RecyclerView) itemView.findViewById(R.id.mRecyclerViewOrderGoodsList);

            mTextViewGoodsNum = (TextView) itemView.findViewById(R.id.mTextViewGoodsNum);
            mTextViewTotalPrice = (TextView) itemView.findViewById(R.id.mTextViewTotalPrice);
            mTableRowBottomButton = (TableRow) itemView.findViewById(R.id.mTableRowBottomButton);
            mButton1 = (Button) itemView.findViewById(R.id.mButton1);
            mButton2 = (Button) itemView.findViewById(R.id.mButton2);
            mButton3 = (Button) itemView.findViewById(R.id.mButton3);
            mView = itemView;
        }
    }

    /**
     * 商品adapter
     */

    public class OrderGoodsAdapter2 extends RecyclerView.Adapter<OrderGoodsAdapter2.MyViewHolder2> {

        List<OrderModelInfo.DataBean.GoodsBean> mGoodsBeanList;

        public OrderGoodsAdapter2(List<OrderModelInfo.DataBean.GoodsBean> goodsBeanList) {
            mGoodsBeanList = goodsBeanList;
        }

        @Override
        public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.recycler_create_order_goods_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder2 holder, int position) {
            if (mGoodsBeanList.size() > 0) {
                OrderModelInfo.DataBean.GoodsBean goodsBean = mGoodsBeanList.get(position);

                Picasso.with(mContext).load(goodsBean.getGoods_thumb()).error(R.mipmap.goods_pic_error).into(holder.mImageViewGoodsPic);

                holder.mTextViewGoodsName.setText(goodsBean.getGoods_name());
                holder.mTextViewGoodsNum.setText("X " + goodsBean.getGoods_number());

                holder.mTextViewGoodsPrice.setText(Utils.getStyledTextCartGoodsPrice(mContext, goodsBean.getGoods_price()), TextView.BufferType.SPANNABLE);
                holder.mTextViewGoodsType.setText(goodsBean.getAttr());
            }
        }


        @Override
        public int getItemCount() {
            return mGoodsBeanList.size();
        }

        public class MyViewHolder2 extends RecyclerView.ViewHolder {

            ImageView mImageViewGoodsPic;
            TextView mTextViewGoodsName;
            TextView mTextViewGoodsType;
            TextView mTextViewGoodsPrice;
            TextView mTextViewGoodsNum;

            View mView;

            public MyViewHolder2(View itemView) {
                super(itemView);
                mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
                mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
                mTextViewGoodsType = (TextView) itemView.findViewById(R.id.mTextViewGoodsType);
                mTextViewGoodsPrice = (TextView) itemView.findViewById(R.id.mTextViewGoodsPrice);
                mTextViewGoodsNum = (TextView) itemView.findViewById(R.id.mTextViewGoodsNum);
                mView = itemView;
            }
        }
    }

}
