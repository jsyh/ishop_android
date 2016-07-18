package com.jsyh.xjd.adapter.personal;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.SignInModel;
import com.jsyh.xjd.utils.Utils;

import java.util.List;

/**
 * 签到记录
 * Created by Su on 2016/6/21.
 */
public class SignInRecordAdapter extends RecyclerView.Adapter<SignInRecordAdapter.MyViewHolder> {

    private final int VIEW_TYPE = -200;//空数据提示


    private Context mContext;
    private List<SignInModel.DataBean.RecordBean> mDataBeanList;

    public SignInRecordAdapter(Context context, List<SignInModel.DataBean.RecordBean> dataBeanList) {
        mContext = context;
        mDataBeanList = dataBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType != VIEW_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_credits_record_item, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.recycler_empty, parent, false);
            view.setBackgroundColor(Color.WHITE);
            TextView textView1 = (TextView) view.findViewById(R.id.mTextView1);
            textView1.setText("积分明细记录为空");
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mDataBeanList.size() > 0) {
            holder.mTextViewOrderSn.setText(mDataBeanList.get(position).getName());
            holder.mTextViewDate.setText(Utils.time2(mContext, Long.parseLong(mDataBeanList.get(position).getTime())));
            holder.mTextViewCreditsNum.setText("+" + mDataBeanList.get(position).getPoints());
        }
    }

    @Override
    public int getItemCount() {
        return mDataBeanList.size() == 0 ? 1 : mDataBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataBeanList.size() == 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewOrderSn;
        TextView mTextViewDate;
        TextView mTextViewCreditsNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewOrderSn = (TextView) itemView.findViewById(R.id.mTextViewOrderSn);
            mTextViewDate = (TextView) itemView.findViewById(R.id.mTextViewDate);
            mTextViewCreditsNum = (TextView) itemView.findViewById(R.id.mTextViewCreditsNum);
        }
    }
}
