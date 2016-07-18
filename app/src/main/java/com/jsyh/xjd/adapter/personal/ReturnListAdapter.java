package com.jsyh.xjd.adapter.personal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.WriteExpressSnActivity;
import com.jsyh.xjd.model.ReturnGoodsModel;
import com.jsyh.xjd.presenter.ReturnPresenter;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/5/11.
 */
public class ReturnListAdapter extends RecyclerView.Adapter<ReturnListAdapter.MyViewHolder> {


    private Context mContext;
    private List<ReturnGoodsModel.DataBean> mDataBeanList;
    private ReturnPresenter mReturnPresenter;
    public int viewType = -10;

    public ReturnListAdapter(Context context, List<ReturnGoodsModel.DataBean> dataBeanList, ReturnPresenter returnPresenter) {
        mContext = context;
        mDataBeanList = dataBeanList;
        mReturnPresenter = returnPresenter;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -10)
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false));
        else if (viewType == -20) {
            View emptyView = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.goods_shop_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("售后列表为空");
            return new MyViewHolder(emptyView);
        }
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_return_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mDataBeanList.size() > 0) {
            final ReturnGoodsModel.DataBean dataBean = mDataBeanList.get(position);
            holder.mTextViewShopName.setText(dataBean.getSupplier_name());
            //通过退货申请
            if (dataBean.getStatus_back().equals("0")) {
                holder.mTextViewOrderStatus.setText("请寄回商品");
                holder.mButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ToastUtil.showToast(mContext, "填写物流单号");
                        Intent intent = new Intent(mContext, WriteExpressSnActivity.class);
                        intent.putExtra("back_id", dataBean.getBack_id());
                        Utils.startActivityWithAnimation(mContext, intent);
                    }
                });
                holder.mButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ToastUtil.showToast(mContext, "取消退款");
                        Utils.showOfficialDialog((Activity) mContext, "提示", "确认取消申请?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mReturnPresenter.cancelApplyReturn(mContext, dataBean.getBack_id());
                            }
                        }, null);
                    }
                });
            } else if (dataBean.getStatus_back().equals("1")) {
                //收到货
                holder.mTextViewOrderStatus.setText("商品已经寄出");
                holder.mLayoutBottomButton.setVisibility(View.GONE);

            } else if (dataBean.getStatus_back().equals("5")) {
                //申请退款中
                holder.mTextViewOrderStatus.setText("等待卖家处理");
                holder.mButton1.setVisibility(View.GONE);
                holder.mButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ToastUtil.showToast(mContext, "取消退款");
                        Utils.showOfficialDialog((Activity) mContext, "提示", "确认取消申请?", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mReturnPresenter.cancelApplyReturn(mContext, dataBean.getBack_id());
                            }
                        }, null);
                    }
                });
            } else if (dataBean.getStatus_back().equals("6")) {
                //拒绝退货申请
                holder.mTextViewOrderStatus.setText("卖家拒绝退款");
                holder.mLayoutBottomButton.setVisibility(View.GONE);
            } else if (dataBean.getStatus_back().equals("3")) {
                //退款完成
                holder.mTextViewOrderStatus.setText("退款成功");
                holder.mLayoutBottomButton.setVisibility(View.GONE);
            } else if (dataBean.getStatus_back().equals("8")) {
                //取消退款
                holder.mTextViewOrderStatus.setText("退款关闭");
                holder.mLayoutBottomButton.setVisibility(View.GONE);
            }

            Picasso.with(mContext).load(dataBean.getGoods_thumb()).error(R.mipmap.goods_detail_shop_photo).into(holder.mImageViewGoodsPic);
            holder.mTextViewGoodsName.setText(dataBean.getGoods_name());
            holder.mTextViewGoodsType.setText(dataBean.getGoods_attr());
            holder.mTextViewDealTotal.setText("￥" + dataBean.getBack_goods_price());
            holder.mTextViewReturnTotal.setText("￥" + dataBean.getBack_goods_price());


        }
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size() > 0 ? mDataBeanList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataBeanList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewShopName;
        TextView mTextViewOrderStatus;
        ImageView mImageViewGoodsPic;
        TextView mTextViewGoodsName;
        TextView mTextViewGoodsType;
        TextView mTextViewDealTotal;
        TextView mTextViewReturnTotal;
        LinearLayout mLayoutBottomButton;
        Button mButton1;
        Button mButton2;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewShopName = (TextView) itemView.findViewById(R.id.mTextViewShopName);
            mTextViewOrderStatus = (TextView) itemView.findViewById(R.id.mTextViewOrderStatus);
            mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
            mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
            mTextViewGoodsType = (TextView) itemView.findViewById(R.id.mTextViewGoodsType);
            mTextViewDealTotal = (TextView) itemView.findViewById(R.id.mTextViewDealTotal);
            mTextViewReturnTotal = (TextView) itemView.findViewById(R.id.mTextViewReturnTotal);
            mLayoutBottomButton = (LinearLayout) itemView.findViewById(R.id.mLayoutBottomButton);
            mButton1 = (Button) itemView.findViewById(R.id.mButton1);
            mButton2 = (Button) itemView.findViewById(R.id.mButton2);
        }
    }
}
