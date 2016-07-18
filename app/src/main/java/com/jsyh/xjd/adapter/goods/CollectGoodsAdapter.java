package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.model.CollectGoodsModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/4/26.
 */
public class CollectGoodsAdapter extends RecyclerView.Adapter<CollectGoodsAdapter.MyViewHolder> {
    private Context mContext;
    private List<CollectGoodsModel.DataBean> mDataBeanList;
    public int viewType = -1;

    public boolean checkState = false;
    public DeleteCollectListener mDeleteCollectListener;

    public CollectGoodsAdapter(Context context, List<CollectGoodsModel.DataBean> dataBeanList) {
        mContext = context;
        mDataBeanList = dataBeanList;
        mDeleteCollectListener = (DeleteCollectListener) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            View loadingView = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false);
            return new MyViewHolder(loadingView);
        } else if (viewType == -2) {
            View emptyView = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            return new MyViewHolder(emptyView);
        }

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_collecte_goods_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (mDataBeanList.size() > 0) {
            final CollectGoodsModel.DataBean dataBean = mDataBeanList.get(position);
            Picasso.with(mContext).load(dataBean.getGoods_img()).error(R.mipmap.goods_pic_error).into(holder.mImageViewGoodsPic);
            holder.mTextViewGoodsName.setText(dataBean.getGoods_name());
            holder.mTextViewGoodsPrice.setText(Utils.getStyledTextGoodsList(mContext, dataBean.getGoods_price()), TextView.BufferType.SPANNABLE);
            holder.mTextViewCommentsNum.setText("评论" + dataBean.getGoods_comment() + "条  " + dataBean.getGoods_rage() + "%好评");
            if (holder.mCheckBoxGoods.isChecked())
                holder.mCheckBoxGoods.setChecked(false);
            if (!checkState) {
                holder.mCheckBoxGoods.setVisibility(View.GONE);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra("goodsId", dataBean.getGoods_id());
                        Utils.startActivityWithAnimation(mContext, intent);
                    }
                });
            } else {
                holder.mCheckBoxGoods.setVisibility(View.VISIBLE);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.mCheckBoxGoods.isChecked())
                            holder.mCheckBoxGoods.setChecked(false);
                        else {
                            holder.mCheckBoxGoods.setChecked(true);
                        }
                        mDeleteCollectListener.deleteCollectListener(dataBean, holder.mCheckBoxGoods.isChecked());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size() > 0 ? mDataBeanList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("recycler", "getItemViewType------------------");
        if (mDataBeanList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBoxGoods;
        ImageView mImageViewGoodsPic;
        TextView mTextViewGoodsName;
        TextView mTextViewGoodsPrice;
        TextView mTextViewCommentsNum;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCheckBoxGoods = (CheckBox) itemView.findViewById(R.id.mCheckBoxGoods);
            mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
            mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
            mTextViewGoodsPrice = (TextView) itemView.findViewById(R.id.mTextViewGoodsPrice);
            mTextViewCommentsNum = (TextView) itemView.findViewById(R.id.mTextViewCommentsNum);
            view = itemView;
        }

    }

    public interface DeleteCollectListener {
        void deleteCollectListener(CollectGoodsModel.DataBean dataBean, boolean add);
    }
}
