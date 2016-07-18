package com.jsyh.xjd.adapter.shops;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.activity.ShopActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.ShopsInfoModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索店铺信息列表
 * Created by Su on 2016/4/18.
 */


public class ShopListRecyclerAdapter extends RecyclerView.Adapter<ShopListRecyclerAdapter.ShopListViewHolder> {

    private Context context;
    private List<ShopsInfoModel.DataBean.InfoBean> infoBeanList;
    public static int VIEW_TYPE = -10;//-1即加载中,-2即加载完毕

    public ShopListRecyclerAdapter(Context context, List<ShopsInfoModel.DataBean.InfoBean> infoBeanList) {
        this.context = context;
        this.infoBeanList = infoBeanList;
    }

    @Override
    public ShopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -10) {
            View loadingView = LayoutInflater.from(context).inflate(R.layout.recycler_loading, parent, false);
            return new ShopListViewHolder(loadingView);
        } else if (viewType == -20) {
            View emptyView = LayoutInflater.from(context).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.goods_shop_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("没有找到你想要的店铺");
            return new ShopListViewHolder(emptyView);
        }

        return new ShopListViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_shop_item, null));
    }

    @Override
    public void onBindViewHolder(ShopListViewHolder holder, int position) {
        if (infoBeanList.size() > 0) {

            final ShopsInfoModel.DataBean.InfoBean infoBean = infoBeanList.get(position);
            final ShopsInfoModel.DataBean.InfoBean.ShopBean shopInfo = infoBean.getShop();

            Picasso.with(context).load(ConfigValue.IMG_IP + shopInfo.getShop_logo()).error(R.mipmap.goods_detail_shop_photo).into(holder.mImageViewShopPic);
            holder.mTextViewShopName.setText(shopInfo.getShop_name());
            String shopLevel = "";
            if (shopInfo.getShop_rage().equals("1"))
                shopLevel = "初级店铺";
            else if (shopInfo.getShop_rage().equals("2"))
                shopLevel = "中级店铺";
            else if (shopInfo.getShop_rage().equals("3"))
                shopLevel = "高级店铺";
            holder.mTextViewShopRank.setText(shopLevel);
            holder.mTextViewGoodsNum.setText("销量" + shopInfo.getSales() + " 共" + shopInfo.getGoods_sum() + "件宝贝");
            holder.mTextViewGoInShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shopIntent = new Intent(context, ShopActivity.class);
                    shopIntent.putExtra("url", shopInfo.getShop_url());
                    shopIntent.putExtra("id", shopInfo.getSupplier_id());
                    shopIntent.putExtra("attention", shopInfo.getAttention() + "");
                    Utils.startActivityWithAnimation(context, shopIntent);
                }
            });

            List<ShopsInfoModel.DataBean.InfoBean.GoodsBean> goodsBeanList = new ArrayList<>();
            if (infoBean.getGoods() != null)
                goodsBeanList.addAll(infoBean.getGoods());
            holder.mGridViewGoods.setAdapter(new ShopGoodsAdapter(context, goodsBeanList));
            holder.mGridViewGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("goodsId", infoBean.getGoods().get(position).getGoods_id());
                    Utils.startActivityWithAnimation(context, intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return infoBeanList.size() > 0 ? infoBeanList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (infoBeanList.size() <= 0) {
            return VIEW_TYPE;
        }

        return super.getItemViewType(position);
    }

    public class ShopListViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewShopPic;
        TextView mTextViewShopName;
        TextView mTextViewShopRank;
        TextView mTextViewGoodsNum;
        TextView mTextViewGoInShop;
        //RecyclerView mRecyclerViewGoods;
        GridView mGridViewGoods;

        public ShopListViewHolder(View itemView) {
            super(itemView);
            mImageViewShopPic = (ImageView) itemView.findViewById(R.id.mImageViewShopPic);
            mTextViewShopName = (TextView) itemView.findViewById(R.id.mTextViewShopName);
            mTextViewShopRank = (TextView) itemView.findViewById(R.id.mTextViewShopRank);
            mTextViewGoodsNum = (TextView) itemView.findViewById(R.id.mTextViewGoodsNum);
            mTextViewGoInShop = (TextView) itemView.findViewById(R.id.mTextViewGoInShop);
            mGridViewGoods = (GridView) itemView.findViewById(R.id.mGridViewGoods);
        }
    }

}
