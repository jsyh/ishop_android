package com.jsyh.xjd.adapter.personal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.NewAddressActivity;
import com.jsyh.xjd.model.AddressModel;
import com.jsyh.xjd.presenter.DeleteAddressPresenter;
import com.jsyh.xjd.utils.Utils;

import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.MyViewHolder> {


    private Context mContext;
    private List<AddressModel.DataBean> mDataBeanList;
    public int viewType = -1;
    private DeleteAddressPresenter mDeleteAddressPresenter;

    public AddressRecyclerAdapter(Context context, List<AddressModel.DataBean> dataBeanList, DeleteAddressPresenter deleteAddressPresenter) {
        mContext = context;
        mDataBeanList = dataBeanList;
        mDeleteAddressPresenter = deleteAddressPresenter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == -1) {
            View loadingView = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading, parent, false);
            return new MyViewHolder(loadingView);
        } else if (viewType == -2) {
            View emptyView = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.goods_shop_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("暂无地址信息");
            return new MyViewHolder(emptyView);
        }

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false));
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
            final AddressModel.DataBean dataBean = mDataBeanList.get(position);
            holder.mTextViewName.setText(dataBean.getUsername());
            holder.mTextViewPhone.setText(dataBean.getTelnumber());
            holder.mTextViewAddress.setText(dataBean.getAddress());
            if (dataBean.getIs_default().equals("0")) {
                holder.mCheckBoxDefault.setChecked(false);
            } else {
                holder.mCheckBoxDefault.setChecked(true);
            }

            //编辑
            holder.mTextViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewAddressActivity.class);
                    intent.putExtra("address", dataBean.getAddress_id());
                    Utils.startActivityWithAnimation(mContext, intent);
                }
            });
            //删除

            holder.mTextViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.showOfficialDialog((Activity) mContext, "提示", "确认删除这条地址？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //ToastUtil.showToast(mContext,"删除了");
                            mDeleteAddressPresenter.loadDelete(mContext, dataBean.getAddress_id(), "deladdress");
                        }
                    }, null);
                }
            });

            holder.mCheckBoxDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        mDeleteAddressPresenter.loadDelete(mContext,dataBean.getAddress_id(),"addrdefault");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size() > 0 ? mDataBeanList.size() : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewName;
        TextView mTextViewPhone;
        TextView mTextViewAddress;
        CheckBox mCheckBoxDefault;
        TextView mTextViewEdit;
        TextView mTextViewDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewName = (TextView) itemView.findViewById(R.id.mTextViewName);
            mTextViewPhone = (TextView) itemView.findViewById(R.id.mTextViewPhone);
            mTextViewAddress = (TextView) itemView.findViewById(R.id.mTextViewAddress);
            mCheckBoxDefault = (CheckBox) itemView.findViewById(R.id.mCheckBoxDefault);
            mTextViewEdit = (TextView) itemView.findViewById(R.id.mTextViewEdit);
            mTextViewDelete = (TextView) itemView.findViewById(R.id.mTextViewDelete);

        }
    }
}
