package com.jsyh.xjd.adapter.cart;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsCollectActivity;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.activity.ShopActivity;
import com.jsyh.xjd.model.ShopCartModel;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.shopping.uilibrary.dialog.AlterGoodsNumDialog;
import com.jsyh.shopping.uilibrary.views.ShopCartAddAndSubView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车按店铺划分商品
 * Created by Su on 2016/4/28.
 */
public class CartShopsAdapter extends RecyclerView.Adapter<CartShopsAdapter.MyViewHolder> {

    private Context mContext;
    private List<ShopCartModel.DataBean> mDataBeanList;//传过来的数据

    public List<ShopCartModel.DataBean.GoodsBean> mAllSelectGoodsBeanList;//选中的所有数据

    public int viewType = -10;

    public static OnItemSelectListener mOnItemSelectListener;//点击某项商品之后回调接口


    public CartShopsAdapter(Context context, List<ShopCartModel.DataBean> dataBeanList) {
        mContext = context;
        mDataBeanList = dataBeanList;
        mAllSelectGoodsBeanList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -10) {
            View loadingView = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false);
            return new MyViewHolder(loadingView);
        } else if (viewType == -20) {
            View emptyView = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.cart_empty_data);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("购物车快饿瘪了T.T");
            ((TextView) emptyView.findViewById(R.id.mTextView2)).setText("看看关注有要购买的吗");
            emptyView.findViewById(R.id.mButtonToCollect).setVisibility(View.VISIBLE);
            emptyView.findViewById(R.id.mButtonToCollect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.online(mContext))
                        Utils.startActivityWithAnimation(mContext, new Intent(mContext, GoodsCollectActivity.class));
                    else Utils.toLogin(mContext);
                }
            });
            return new MyViewHolder(emptyView);
        }

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_shop_cart_item, parent, false));
    }

    //根据已选商品集合与最新数据根据id取交集
    public List<ShopCartModel.DataBean.GoodsBean> getRetainList(List<ShopCartModel.DataBean.GoodsBean> goodsBeanList) {
        List<ShopCartModel.DataBean.GoodsBean> itemSelectGoodsBeanList = new ArrayList<>();//存新数据
        List<ShopCartModel.DataBean.GoodsBean> tempList = new ArrayList<>();//存就旧数据
        if (mAllSelectGoodsBeanList.size() > 0) {
            for (ShopCartModel.DataBean.GoodsBean goodsBean : goodsBeanList) {
                for (ShopCartModel.DataBean.GoodsBean goodsBean1 : mAllSelectGoodsBeanList) {
                    if (goodsBean1.getRec_id().equals(goodsBean.getRec_id())) {
                        tempList.add(goodsBean1);//旧数据去掉，新数据放进去，List的retailAll是求交集
                        itemSelectGoodsBeanList.add(goodsBean);
                    }
                }
            }
        }
        mAllSelectGoodsBeanList.removeAll(tempList);
        mAllSelectGoodsBeanList.addAll(itemSelectGoodsBeanList);
        return itemSelectGoodsBeanList;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final List<ShopCartModel.DataBean.GoodsBean> itemSelectGoodsBeanList = new ArrayList<>();

        if (mDataBeanList.size() > 0) {
            final ShopCartModel.DataBean dataBean = mDataBeanList.get(position);

            holder.mTextViewShopName.setText(dataBean.getShopname());

            holder.mRecyclerViewCartShopsList.setLayoutManager(new LinearLayoutManager(mContext));
            final CartGoodsAdapter mCartGoodsAdapter = new CartGoodsAdapter(mContext, dataBean.getGoods());
            //前台设置为全选，则将选择全部商品集合 mSelectDataGoodsBeanList填满，局部选择集合填满，若为全部不选，则全部清除

            List<ShopCartModel.DataBean.GoodsBean> tmpGoodsBeanList = getRetainList(dataBean.getGoods());
            /*if (selectAll) {
                itemSelectGoodsBeanList.clear();
                itemSelectGoodsBeanList.addAll(dataBean.getGoods());

                mCartGoodsAdapter.mSelectGoodsBeanList.clear();
                mCartGoodsAdapter.mSelectGoodsBeanList.addAll(dataBean.getGoods());


            } else {

                itemSelectGoodsBeanList.clear();
                mCartGoodsAdapter.mSelectGoodsBeanList.clear();


                itemSelectGoodsBeanList.addAll(tmpGoodsBeanList);
                mCartGoodsAdapter.mSelectGoodsBeanList.addAll(tmpGoodsBeanList);
                //mAllSelectGoodsBeanList.removeAll(dataBean.getGoods());
            }*/

            itemSelectGoodsBeanList.clear();
            mCartGoodsAdapter.mSelectGoodsBeanList.clear();


            itemSelectGoodsBeanList.addAll(tmpGoodsBeanList);
            mCartGoodsAdapter.mSelectGoodsBeanList.addAll(tmpGoodsBeanList);


            mOnItemSelectListener.onItemSelectListener(mAllSelectGoodsBeanList);

            holder.mRecyclerViewCartShopsList.setAdapter(mCartGoodsAdapter);

            //店铺是否选中
            if (itemSelectGoodsBeanList.size() == dataBean.getGoods().size()) {
                holder.mCheckBoxShopCheck.setChecked(true);
            } else {
                holder.mCheckBoxShopCheck.setChecked(false);
            }

            //自定义监听,监听选择商品的变化
            mCartGoodsAdapter.setOnGoodsItemSelectListener(new CartGoodsAdapter.OnGoodsSelectListener() {
                @Override
                public void onGoodsSelectListener(ShopCartModel.DataBean.GoodsBean goodsBean, boolean add) {
                    //Utils.showToast(mContext, goodsBeanList.size() + "--------------------");
                    if (add) {
                        itemSelectGoodsBeanList.add(goodsBean);
                        mAllSelectGoodsBeanList.add(goodsBean);
                    } else {
                        itemSelectGoodsBeanList.remove(goodsBean);
                        mAllSelectGoodsBeanList.remove(goodsBean);
                    }
                    if (itemSelectGoodsBeanList.size() == dataBean.getGoods().size()) {
                        holder.mCheckBoxShopCheck.setChecked(true);
                    } else {
                        holder.mCheckBoxShopCheck.setChecked(false);
                    }

                    Log.d("cart", "总商品数mSelectDataGoodsBeanList的大小为" + mAllSelectGoodsBeanList.size() + "-----------单选---------该店铺选择的商品数量为：" + add + itemSelectGoodsBeanList.size());
                    mOnItemSelectListener.onItemSelectListener(mAllSelectGoodsBeanList);
                }
            });

            //点击店铺checkBox,子项全部选中,全部取消选中

            holder.mFrameLayoutCheckBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        if (!holder.mCheckBoxShopCheck.isChecked()) {
                            holder.mCheckBoxShopCheck.setChecked(true);

                            itemSelectGoodsBeanList.clear();
                            itemSelectGoodsBeanList.addAll(dataBean.getGoods());

                            mCartGoodsAdapter.mSelectGoodsBeanList.clear();
                            mCartGoodsAdapter.mSelectGoodsBeanList.addAll(dataBean.getGoods());


                            mAllSelectGoodsBeanList.removeAll(dataBean.getGoods());//可能没有全部选择，删除选中项，假如U所有项
                            Log.d("cart", "总商品数mSelectDataGoodsBeanList的大小为" + mAllSelectGoodsBeanList.size() + "----------点击店铺remove----------该店铺选择的商品数量为：" + itemSelectGoodsBeanList.size());
                            mAllSelectGoodsBeanList.addAll(dataBean.getGoods());


                        } else {
                            holder.mCheckBoxShopCheck.setChecked(false);
                            mAllSelectGoodsBeanList.removeAll(dataBean.getGoods());
                            itemSelectGoodsBeanList.removeAll(dataBean.getGoods());
                            mCartGoodsAdapter.mSelectGoodsBeanList.clear();
                        }
                        mCartGoodsAdapter.notifyDataSetChanged();
                        Log.d("cart", "总商品数mSelectDataGoodsBeanList的大小为" + mAllSelectGoodsBeanList.size() + "---------点击店铺之后-----------该店铺选择的商品数量为：" + itemSelectGoodsBeanList.size());
                        mOnItemSelectListener.onItemSelectListener(mAllSelectGoodsBeanList);
                    }

                    return true;
                }
            });
            holder.mLayoutGoShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtil.showToast(mContext, "缺shop_id与attention字段！");
                    if (!dataBean.getShop_id().equals("0")) {
                        Intent shopIntent = new Intent(mContext, ShopActivity.class);
                        shopIntent.putExtra("url", dataBean.getUrl());
                        shopIntent.putExtra("id", dataBean.getShop_id());
                        shopIntent.putExtra("attention", dataBean.getAttention() + "");
                        Utils.startActivityWithAnimation(mContext, shopIntent);
                    }
                }
            });
        }

    }
    //此处mOnItemSelectListener持有ShopCartFragment.mContext的引用，因为是静态的，发生了内存泄漏
    public void setOnItemSelectListener(OnItemSelectListener itemSelectListener) {
        mOnItemSelectListener = itemSelectListener;
    }

    //public void removeOnItemSelectListener

    public interface OnItemSelectListener {

        void onItemSelectListener(List<ShopCartModel.DataBean.GoodsBean> goodsBeanList);

        void onItemDeleteListener(ShopCartModel.DataBean.GoodsBean goodsBean);

        void onAlterGoodsNumListener(ShopCartModel.DataBean.GoodsBean goodsBean, String newNum);


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
        CheckBox mCheckBoxShopCheck;
        FrameLayout mFrameLayoutCheckBox;
        TextView mTextViewShopName;
        RecyclerView mRecyclerViewCartShopsList;
        FrameLayout mLayoutGoShop;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCheckBoxShopCheck = (CheckBox) itemView.findViewById(R.id.mCheckBoxShopCheck);
            mTextViewShopName = (TextView) itemView.findViewById(R.id.mTextViewShopName);
            mRecyclerViewCartShopsList = (RecyclerView) itemView.findViewById(R.id.mRecyclerViewCartShopsList);
            mFrameLayoutCheckBox = (FrameLayout) itemView.findViewById(R.id.mLayoutCheckBox);
            mLayoutGoShop = (FrameLayout) itemView.findViewById(R.id.mLayoutGoShop);
        }
    }


    /**
     * /商品adapter
     */


    public static class CartGoodsAdapter extends RecyclerView.Adapter<CartGoodsAdapter.MyViewHolder> {

        private Context mContext;
        private List<ShopCartModel.DataBean.GoodsBean> mGoodsBeanList;

        public List<ShopCartModel.DataBean.GoodsBean> mSelectGoodsBeanList = new ArrayList<>();
        public ShopCartModel.DataBean.GoodsBean mGoodsBean;

        private OnGoodsSelectListener mOnGoodsSelectListener;


        public CartGoodsAdapter(Context context, List<ShopCartModel.DataBean.GoodsBean> goodsBeanList) {
            mContext = context;
            mGoodsBeanList = goodsBeanList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_cart_goods_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ShopCartModel.DataBean.GoodsBean goodsBean = mGoodsBeanList.get(position);

            if (mSelectGoodsBeanList.contains(goodsBean)) {
                holder.mCheckBoxGoodsCheck.setChecked(true);
            } else holder.mCheckBoxGoodsCheck.setChecked(false);


            holder.mLayoutCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGoodsBean = goodsBean;

                    if (!holder.mCheckBoxGoodsCheck.isChecked()) {
                        holder.mCheckBoxGoodsCheck.setChecked(true);
                        if (!mSelectGoodsBeanList.contains(goodsBean))
                            mSelectGoodsBeanList.add(goodsBean);
                    } else {
                        holder.mCheckBoxGoodsCheck.setChecked(false);
                        if (mSelectGoodsBeanList.contains(goodsBean))
                            mSelectGoodsBeanList.remove(goodsBean);
                    }
                    mOnGoodsSelectListener.onGoodsSelectListener(mGoodsBean, holder.mCheckBoxGoodsCheck.isChecked());
                }
            });

            Picasso.with(mContext).load(goodsBean.getGoods_img()).error(R.mipmap.goods_pic_error).into(holder.mImageViewGoodsPic);
            holder.mTextViewGoodsName.setText(goodsBean.getGoods_name());
            holder.mTextViewGoodsType.setText(goodsBean.getGoods_attr().replaceAll("\\n", " "));
            holder.mTextViewGoodsPrice.setText(Utils.getStyledTextCartGoodsPrice(mContext, goodsBean.getGoods_price()), TextView.BufferType.SPANNABLE);
            holder.mShopCartAddAndSubView.setNum(Integer.parseInt(goodsBean.getNumber()));

            //购物车数量加减操作
            holder.mShopCartAddAndSubView.setOnNumChangeListener(new ShopCartAddAndSubView.OnNumChangeListener() {
                @Override
                public void onNumChange(int num) {
                    mOnItemSelectListener.onAlterGoodsNumListener(goodsBean, num + "");
                }
            });

            //点击弹出对话框修改数量
            holder.mShopCartAddAndSubView.edit_num.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        AlterGoodsNumDialog dialog = new AlterGoodsNumDialog(mContext, Integer.parseInt(goodsBean.getNumber()), new AlterGoodsNumDialog.OnAlertGoodsNumDialogListener() {
                            @Override
                            public void cancel() {
                                Log.d("cart", "取消了...");
                            }

                            @Override
                            public void confirm(String newNums) {
                                Log.d("cart", "修改为.-------.." + newNums);
                                mOnItemSelectListener.onAlterGoodsNumListener(goodsBean, newNums + "");
                            }
                        });
                        dialog.show();
                    }
                    return true;
                }
            });


            holder.mTableRowGoGoodsInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("goodsId", goodsBean.getGoods_id());
                    Utils.startActivityWithAnimation(mContext, intent);
                }
            });

            holder.mTableRowGoGoodsInfo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除此商品?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mOnItemSelectListener.onItemDeleteListener(goodsBean);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return mGoodsBeanList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            CheckBox mCheckBoxGoodsCheck;
            FrameLayout mLayoutCheckBox;
            ImageView mImageViewGoodsPic;
            TextView mTextViewGoodsName;

            TextView mTextViewGoodsType;
            TextView mTextViewGoodsPrice;

            ShopCartAddAndSubView mShopCartAddAndSubView;

            TableRow mTableRowGoGoodsInfo;

            public MyViewHolder(View itemView) {
                super(itemView);

                mCheckBoxGoodsCheck = (CheckBox) itemView.findViewById(R.id.mCheckBoxGoodsCheck);
                mImageViewGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewGoodsPic);
                mTextViewGoodsName = (TextView) itemView.findViewById(R.id.mTextViewGoodsName);
                mTextViewGoodsType = (TextView) itemView.findViewById(R.id.mTextViewGoodsType);
                mTextViewGoodsPrice = (TextView) itemView.findViewById(R.id.mTextViewGoodsPrice);
                mLayoutCheckBox = (FrameLayout) itemView.findViewById(R.id.mLayoutCheckBox);
                mShopCartAddAndSubView = (ShopCartAddAndSubView) itemView.findViewById(R.id.mShopCartAddSubView);

                mTableRowGoGoodsInfo = (TableRow) itemView.findViewById(R.id.mTableRowGoGoodsInfo);
            }
        }


        public void setOnGoodsItemSelectListener(OnGoodsSelectListener mListener) {
            mOnGoodsSelectListener = mListener;
        }

        public interface OnGoodsSelectListener {
            void onGoodsSelectListener(ShopCartModel.DataBean.GoodsBean goodsBean, boolean add);
        }

    }


}
