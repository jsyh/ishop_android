package com.jsyh.xjd.adapter.bouns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.CreateOrderActivity;
import com.jsyh.xjd.model.Bouns;
import com.jsyh.xjd.utils.Utils;

import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class ChooseBounsAdapter extends RecyclerView.Adapter<ChooseBounsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Bouns> mBounsMeList;
    public int viewType = -1;

    public ChooseBounsAdapter(Context context, List<Bouns> bounsMeList) {
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
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("您没有可用红包");
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
            final Bouns item = mBounsMeList.get(position);
            holder.money.setText("￥" + item.getType_money());
            holder.name.setText(item.getType_name());
            holder.condition.setText("满" + item.getMin_goods_amount() + "可用");
            String start = Utils.time2(mContext, Long.parseLong(item.getUse_start_date()));
            String end = Utils.time2(mContext, Long.parseLong(item.getUse_end_date()));
            holder.time.setText("有效期" + start.replaceAll("-", ".") + "-" + end.replaceAll("-", "."));

            holder.mImageViewUsable.setImageResource(R.mipmap.personal_bouns_usable);
            holder.mLayoutUsableBg.setBackgroundResource(R.drawable.personal_bouns_right_bg);
            holder.mImageViewLose.setVisibility(View.GONE);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CreateOrderActivity.class);
                    intent.putExtra("title", "使用红包满" + item.getMin_goods_amount() + "减" + item.getType_money());
                    intent.putExtra("id", item.getBonus_id());
                    intent.putExtra("save", item.getType_money());
                    ((Activity) mContext).setResult(-10, intent);
                    Utils.finishActivityWithAnimation(mContext);
                }
            });
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
