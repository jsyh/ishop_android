package com.jsyh.xjd.adapter.bouns;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.Bouns_me;
import com.jsyh.xjd.utils.Utils;

import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class MeBounsAdapter extends RecyclerView.Adapter<MeBounsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Bouns_me> mBounsMeList;
    public int viewType = -1;

    public MeBounsAdapter(Context context, List<Bouns_me> bounsMeList) {
        mContext = context;
        mBounsMeList = bounsMeList;
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
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("您没有红包");
            return new MyViewHolder(emptyView);
        }

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.bouns_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("recycler", "getItemViewType------------------");
        if (mBounsMeList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mBounsMeList.size() > 0) {
            final Bouns_me item = mBounsMeList.get(position);
            holder.money.setText("￥" + item.getType_money());
            holder.name.setText(item.getType_name());
            holder.condition.setText("满" + item.getMin_goods_amount() + "可用");
            String start = Utils.time2(mContext, Long.parseLong(item.getUse_start_date()));
            String end = Utils.time2(mContext, Long.parseLong(item.getUse_end_date()));
            holder.time.setText("有效期" + start.replaceAll("-", ".") + "-" + end.replaceAll("-", "."));

            switch (item.getStatus_num()) {
                case 0:
                case 2:
                    // status.setText("未开始");
                    holder.mImageViewUsable.setImageResource(R.mipmap.personal_bouns_usable);
                    holder.mLayoutUsableBg.setBackgroundResource(R.drawable.personal_bouns_right_bg);
                    holder.mImageViewLose.setVisibility(View.GONE);
                    break;
                case 1:
                case 3:
                    //status.setText("已过期");
                    holder.mImageViewUsable.setImageResource(R.mipmap.personal_bouns_disable);
                    holder.mLayoutUsableBg.setBackgroundResource(R.drawable.personal_bouns_lose_bg);
                    holder.mImageViewLose.setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        return mBounsMeList.size() > 0 ? mBounsMeList.size() : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewUsable;
        TextView name;
        TextView time;
        LinearLayout mLayoutUsableBg;
        TextView money;
        TextView condition;
        ImageView mImageViewLose;

        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageViewUsable = (ImageView) itemView.findViewById(R.id.mImageViewUsable);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            mLayoutUsableBg = (LinearLayout) itemView.findViewById(R.id.mLayoutUsableBg);
            money = (TextView) itemView.findViewById(R.id.money);
            condition = (TextView) itemView.findViewById(R.id.condition);
            mImageViewLose = (ImageView) itemView.findViewById(R.id.mImageViewLose);
            mView = itemView;
        }
    }
}
