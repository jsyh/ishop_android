package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.GoodsListModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/4/19.
 */
public class GoodsListRecyclerAdapter extends RecyclerView.Adapter<GoodsListRecyclerAdapter.GoodsListViewHolder> {

    private Context context;
    private List<GoodsListModel.DataBean.GoodsBean> goodsBeanList;
    public static int VIEW_TYPE = -10;//-1即加载中,-2即加载完毕

    public GoodsListRecyclerAdapter(Context context, List<GoodsListModel.DataBean.GoodsBean> goodsBeanList) {
        this.context = context;
        this.goodsBeanList = goodsBeanList;
    }

    @Override
    public GoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("REC", parent.toString() + "//////" + parent.getParent() + "///////////" + parent.getTag());
        View itemView = null;
        if (viewType == -10) {
            View loadingView = LayoutInflater.from(context).inflate(R.layout.recycler_loading, parent, false);
            return new GoodsListViewHolder(loadingView);
        } else if (viewType == -20) {
            View emptyView = LayoutInflater.from(context).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.goods_shop_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("没有找到你想要的宝贝");
            return new GoodsListViewHolder(emptyView);
        }

        if (Integer.parseInt(parent.getTag() == null ? "0" : parent.getTag().toString()) == 0)
            itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_goods_item,
                    parent, false);
        else if (Integer.parseInt(parent.getTag() == null ? "0" : parent.getTag().toString()) == 1)
            itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_goods_item_grid,
                    parent, false);
        return new GoodsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GoodsListViewHolder holder, int position) {
        if (goodsBeanList.size() > 0) {
            final GoodsListModel.DataBean.GoodsBean goodsBean = goodsBeanList.get(position);
            Picasso.with(context).load(ConfigValue.IMG_IP + "/" + goodsBean.getImage()).error(R.mipmap.goods_pic_error).into(holder.mImageViewGoodsPic);
            holder.mTextViewGoodsName.setText(goodsBean.getTitle());

            holder.mTextViewGoodsPrice.setText(Utils.getStyledTextGoodsList(context, goodsBean.getPrice()), TextView.BufferType.SPANNABLE);
            holder.mTextViewCommentsNum.setText("评论" + goodsBean.getComment_sum() + "条  " + goodsBean.getGood() + "%好评");
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsId", goodsBean.getGoods_id());
                    Utils.startActivityWithAnimation(context, intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("position", position + "--------------------------");
        if (goodsBeanList.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        return goodsBeanList.size() > 0 ? goodsBeanList.size() : 1;
    }

    public class GoodsListViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewGoodsPic;
        TextView mTextViewGoodsName;
        TextView mTextViewGoodsPrice;
        TextView mTextViewCommentsNum;
        View view;

        public GoodsListViewHolder(View itemView) {
            super(itemView);
            mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
            mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
            mTextViewGoodsPrice = (TextView) itemView.findViewById(R.id.mTextViewGoodsPrice);
            mTextViewCommentsNum = (TextView) itemView.findViewById(R.id.mTextViewCommentsNum);
            view = itemView;
        }
    }

}
