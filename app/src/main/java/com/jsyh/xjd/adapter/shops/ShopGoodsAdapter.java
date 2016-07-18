package com.jsyh.xjd.adapter.shops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.ShopsInfoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 店铺列表中展示的4个商品信息
 * Created by Su on 2016/4/18.
 */
public class ShopGoodsAdapter extends BaseAdapter {
    private Context context;
    private List<ShopsInfoModel.DataBean.InfoBean.GoodsBean> goodsBeanList;

    public ShopGoodsAdapter(Context context, List<ShopsInfoModel.DataBean.InfoBean.GoodsBean> goodsBeanList) {
        this.context = context;
        this.goodsBeanList = goodsBeanList;
    }

    @Override
    public int getCount() {
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recycler_shop_goods_item, null);
            holder = new GoodsViewHolder();
            holder.mImageViewGoodsPic = (ImageView) convertView.findViewById(R.id.mImageViewGoodsPic);
            holder.mTextViewGoodsPrice = (TextView) convertView.findViewById(R.id.mTextViewGoodsPrice);
            convertView.setTag(holder);
        } else {
            holder = (GoodsViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(goodsBeanList.get(position).getGoods_thumb()).
                error(R.mipmap.goods_detail_shop_photo).into(holder.mImageViewGoodsPic);

        holder.mTextViewGoodsPrice.setText("￥" + goodsBeanList.get(position).getShop_price());
        return convertView;
    }

    public class GoodsViewHolder {
        ImageView mImageViewGoodsPic;
        TextView mTextViewGoodsPrice;
    }
}
