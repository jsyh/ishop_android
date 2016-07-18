package com.jsyh.xjd.adapter.shops;

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
import com.jsyh.xjd.activity.ShopActivity;
import com.jsyh.xjd.model.CollectShopModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/4/26.
 */
public class CollectShopsAdapter extends RecyclerView.Adapter<CollectShopsAdapter.MyViewHolder> {
    private Context mContext;
    private List<CollectShopModel.DataBean> mDataBeanList;
    public int viewType = -1;

    public boolean checkState = false;
    public DeleteCollectListener mDeleteCollectListener;

    public CollectShopsAdapter(Context context, List<CollectShopModel.DataBean> dataBeanList) {
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

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_collect_shop_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (mDataBeanList.size() > 0) {
            final CollectShopModel.DataBean dataBean = mDataBeanList.get(position);
            Picasso.with(mContext).load(dataBean.getShop_logo()).error(R.mipmap.goods_detail_shop_photo).into(holder.mImageViewShopPic);
            holder.mTextViewName.setText(dataBean.getShopname());
            if (dataBean.getRank().equals("初级店铺"))
                holder.mImageViewLevel.setImageResource(R.mipmap.personal_collect_shop_primary);
            else if (dataBean.getRank().equals("中级店铺"))
                holder.mImageViewLevel.setImageResource(R.mipmap.personal_collect_shop_middle);
            else if (dataBean.getRank().equals("中级店铺"))
                holder.mImageViewLevel.setImageResource(R.mipmap.personal_collect_shop_senior);
            if (holder.mCheckBoxGoods.isChecked())
                holder.mCheckBoxGoods.setChecked(false);

            if (!checkState) {
                holder.mCheckBoxGoods.setVisibility(View.GONE);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShopActivity.class);
                        intent.putExtra("id", dataBean.getSupplierid());
                        intent.putExtra("attention", "1");
                        intent.putExtra("url",dataBean.getUrl());
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
        ImageView mImageViewShopPic;
        TextView mTextViewName;
        ImageView mImageViewLevel;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCheckBoxGoods = (CheckBox) itemView.findViewById(R.id.mCheckBoxGoods);
            mImageViewShopPic = (ImageView) itemView.findViewById(R.id.mImageViewShopPic);
            mTextViewName = (TextView) itemView.findViewById(R.id.mTextViewName);
            mImageViewLevel = (ImageView) itemView.findViewById(R.id.mImageViewLevel);
            view = itemView;
        }

    }

    public interface DeleteCollectListener {
        void deleteCollectListener(CollectShopModel.DataBean dataBean, boolean add);
    }
}
