package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.CreateOrderModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class OrderGoodsAdapter extends RecyclerView.Adapter<OrderGoodsAdapter.MyViewHolder> {


    private Context mContext;
    private List<CreateOrderModel.DataBean.GoodsBean> mDataBeanList;
    public int viewType = -1;

    public OrderGoodsAdapter(Context context, List<CreateOrderModel.DataBean.GoodsBean> dataBeanList) {
        mContext = context;
        mDataBeanList = dataBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == -1) {
            View loadingView = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false);
            return new MyViewHolder(loadingView);
        } /*else if (viewType == -2) {
            View emptyView = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.goods_shop_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("暂无地址信息");
            return new MyViewHolder(emptyView);
        }
*/
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_create_order_shop_item, parent, false));
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
            CreateOrderModel.DataBean.GoodsBean goodsBean = mDataBeanList.get(position);
            holder.mTextViewShopName.setText(goodsBean.getSupplier_name());

            holder.mTextViewPeisong.setText(goodsBean.getPeisong());
            if (goodsBean.getPs_price().equals("0")) {
                holder.mTextViewPeisongPrice.setText("￥0.00");
            } else {
                holder.mTextViewPeisongPrice.setText("运费 ￥" + goodsBean.getPs_price());
            }

            holder.mTextViewGoodsNum.setText("共" + getGoodsNums(goodsBean.getGoodsInfo())[0] + "件商品  合计：");
            holder.mTextViewTotal.setText(Utils.getStyledTextCartGoodsPrice(mContext, getGoodsNums(goodsBean.getGoodsInfo())[1]), TextView.BufferType.SPANNABLE);


            holder.mRecyclerViewCartShopsList.setLayoutManager(new LinearLayoutManager(mContext));
            holder.mRecyclerViewCartShopsList.setAdapter(new OrderGoodsAdapter2(goodsBean.getGoodsInfo()));

        }
    }

    //获取商品件数与总价
    public String[] getGoodsNums(List<CreateOrderModel.DataBean.GoodsBean.GoodsinfoBean> goodsinfo) {
        String[] total = new String[2];
        if (goodsinfo != null && goodsinfo.size() > 0) {
            int num = 0;
            BigDecimal totalPrice = new BigDecimal(0.00);
            for (CreateOrderModel.DataBean.GoodsBean.GoodsinfoBean goodsinfoBean : goodsinfo) {
                num += Integer.parseInt(goodsinfoBean.getGoods_number());
                BigDecimal singlePrice = new BigDecimal(goodsinfoBean.getGoods_price());
                BigDecimal number = new BigDecimal(goodsinfoBean.getGoods_number());
                totalPrice = totalPrice.add(singlePrice.multiply(number));
            }
            total[0] = num + "";
            total[1] = totalPrice.toString();
            return total;
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return mDataBeanList.size() > 0 ? mDataBeanList.size() : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewShopName;
        RecyclerView mRecyclerViewCartShopsList;
        TextView mTextViewPeisong;
        TextView mTextViewPeisongPrice;
        EditText mEditTextMessage;

        TextView mTextViewGoodsNum;
        TextView mTextViewTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewShopName = (TextView) itemView.findViewById(R.id.mTextViewShopName);
            mRecyclerViewCartShopsList = (RecyclerView) itemView.findViewById(R.id.mRecyclerViewCartShopsList);
            mTextViewPeisong = (TextView) itemView.findViewById(R.id.mTextViewPeisong);
            mTextViewPeisongPrice = (TextView) itemView.findViewById(R.id.mTextViewPeisongPrice);
            mEditTextMessage = (EditText) itemView.findViewById(R.id.mEditTextMessage);

            mTextViewGoodsNum = (TextView) itemView.findViewById(R.id.mTextViewGoodsNum);
            mTextViewTotal = (TextView) itemView.findViewById(R.id.mTextViewTotal);

        }
    }

    /**
     * 商品adapter
     */

    public class OrderGoodsAdapter2 extends RecyclerView.Adapter<OrderGoodsAdapter2.MyViewHolder2> {

        List<CreateOrderModel.DataBean.GoodsBean.GoodsinfoBean> mGoodsinfoBeanList;

        public OrderGoodsAdapter2(List<CreateOrderModel.DataBean.GoodsBean.GoodsinfoBean> goodsinfoBeanList) {
            mGoodsinfoBeanList = goodsinfoBeanList;
        }

        @Override
        public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.recycler_create_order_goods_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder2 holder, int position) {
            if (mGoodsinfoBeanList.size() > 0) {
                CreateOrderModel.DataBean.GoodsBean.GoodsinfoBean goodsinfoBean = mGoodsinfoBeanList.get(position);

                Picasso.with(mContext).load(goodsinfoBean.getGoods_thumb()).error(R.mipmap.goods_pic_error).into(holder.mImageViewGoodsPic);

                holder.mTextViewGoodsName.setText(goodsinfoBean.getGoods_name());
                holder.mTextViewGoodsNum.setText("X" + goodsinfoBean.getGoods_number());

                holder.mTextViewGoodsPrice.setText(Utils.getStyledTextCartGoodsPrice(mContext, goodsinfoBean.getGoods_price()), TextView.BufferType.SPANNABLE);

                holder.mTextViewGoodsType.setText(getGoodsType(goodsinfoBean.getAttr()));
            }
        }

        public String getGoodsType(List<CreateOrderModel.DataBean.GoodsBean.GoodsinfoBean.AttrBean> attr) {
            if (attr != null && attr.size() > 0) {
                String tmp = "";
                for (int i = 0; i < attr.size(); i++) {
                    tmp += attr.get(i).getAttr_name() + ":" + attr.get(i).getAttr_value() + "  ";
                }
                return tmp;
            }
            return "";
        }

        @Override
        public int getItemCount() {
            return mGoodsinfoBeanList.size();
        }

        public class MyViewHolder2 extends RecyclerView.ViewHolder {

            ImageView mImageViewGoodsPic;
            TextView mTextViewGoodsName;
            TextView mTextViewGoodsType;
            TextView mTextViewGoodsPrice;
            TextView mTextViewGoodsNum;

            public MyViewHolder2(View itemView) {
                super(itemView);
                mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
                mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
                mTextViewGoodsType = (TextView) itemView.findViewById(R.id.mTextViewGoodsType);
                mTextViewGoodsPrice = (TextView) itemView.findViewById(R.id.mTextViewGoodsPrice);
                mTextViewGoodsNum = (TextView) itemView.findViewById(R.id.mTextViewGoodsNum);
            }
        }
    }

}
