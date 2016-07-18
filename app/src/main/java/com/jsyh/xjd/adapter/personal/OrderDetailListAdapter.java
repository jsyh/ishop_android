package com.jsyh.xjd.adapter.personal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.ApplyServiceActivity;
import com.jsyh.xjd.model.OrderInforModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class OrderDetailListAdapter extends RecyclerView.Adapter<OrderDetailListAdapter.MyViewHolder> {


    private Context mContext;
    private List<OrderInforModel.DataBean.GoodsBean.GoodsInfoBean> mGoodsInfoBeanList;
    public int viewType = -1;
    private String orderStatus, orderId;

    public OrderDetailListAdapter(Context context, List<OrderInforModel.DataBean.GoodsBean.GoodsInfoBean> goodsBeanList, String status, String orderId) {
        mContext = context;
        mGoodsInfoBeanList = goodsBeanList;
        orderStatus = status;
        this.orderId = orderId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == -1) {
            View loadingView = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false);
            return new MyViewHolder(loadingView);
        }
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_order_detail_goods_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("recycler", "getItemViewType------------------");
        if (mGoodsInfoBeanList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mGoodsInfoBeanList.size() > 0) {
            final OrderInforModel.DataBean.GoodsBean.GoodsInfoBean goodsInfoBean = mGoodsInfoBeanList.get(position);
            holder.mTextViewGoodsName.setText(goodsInfoBean.getGoods_name());
            Picasso.with(mContext).load(goodsInfoBean.getGoods_thumb()).error(R.mipmap.goods_detail_shop_photo).into(holder.mImageViewGoodsPic);
            holder.mTextViewGoodsPrice.setText("￥" + goodsInfoBean.getShop_price());
            holder.mTextViewGoodsType.setText(goodsInfoBean.getGoods_attr());
            holder.mTextViewGoodsNum.setText("x " + goodsInfoBean.getGoods_number());
            if (orderStatus.equals("4")) {
                holder.mLayoutAfterSalesService.setVisibility(View.VISIBLE);
            }
            /*if (orderStatus.equals("1")) {
                holder.mLayoutAfterSalesService.setVisibility(View.VISIBLE);
            }*/
            holder.mButtonAfterSalesService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtil.showToast(mContext, "售后");
                    Intent intent = new Intent(mContext, ApplyServiceActivity.class);
                    intent.putExtra("goodsId", goodsInfoBean.getGoods_id());
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("goodsPrice", goodsInfoBean.getShop_price());
                    Utils.startActivityWithAnimation(mContext, intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mGoodsInfoBeanList.size() > 0 ? mGoodsInfoBeanList.size() : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewGoodsPic;
        TextView mTextViewGoodsName;
        TextView mTextViewGoodsType;
        TextView mTextViewGoodsPrice;
        TextView mTextViewGoodsNum;
        Button mButtonAfterSalesService;
        RelativeLayout mLayoutAfterSalesService;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
            mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
            mTextViewGoodsType = (TextView) itemView.findViewById(R.id.mTextViewGoodsType);
            mTextViewGoodsPrice = (TextView) itemView.findViewById(R.id.mTextViewGoodsPrice);
            mTextViewGoodsNum = (TextView) itemView.findViewById(R.id.mTextViewGoodsNum);
            mButtonAfterSalesService = (Button) itemView.findViewById(R.id.mButtonAfterSalesService);
            mLayoutAfterSalesService = (RelativeLayout) itemView.findViewById(R.id.mLayoutAfterSalesService);
        }
    }
}
