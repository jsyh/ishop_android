package com.jsyh.xjd.adapter.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.PersonalCommentsModel;
import com.jsyh.xjd.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Su on 2016/4/27.
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.MyViewHolder> {


    private Context mContext;
    private List<PersonalCommentsModel.DataBean.CommnetBean> mCommnetBeanList;
    public int viewType = -1;

    public CommentsListAdapter(Context context, List<PersonalCommentsModel.DataBean.CommnetBean> commnetBeanList) {
        mContext = context;
        mCommnetBeanList = commnetBeanList;
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
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("您没有任何评论");
            return new MyViewHolder(emptyView);
        }

        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_comment_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("recycler", "getItemViewType------------------");
        if (mCommnetBeanList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mCommnetBeanList.size() > 0) {
            PersonalCommentsModel.DataBean.CommnetBean commnetBean = mCommnetBeanList.get(position);
            Picasso.with(mContext).load(commnetBean.getGoods_img()).error(R.mipmap.goods_pic_error).into(holder.mImageViewCommentGoodsPic);
            holder.mTextViewCommentGoodsName.setText(commnetBean.getGoods_name());
            if (commnetBean.getRank().equals("1")) {
                holder.mTextViewCommentRank.setText("已差评");

            } else if (commnetBean.getRank().equals("2") || commnetBean.getRank().equals("3")) {
                holder.mTextViewCommentRank.setText("已中评");
            } else if (commnetBean.getRank().equals("4") || commnetBean.getRank().equals("5")) {
                holder.mTextViewCommentRank.setText("已好评");
            }
            holder.mTextViewCommentContent.setText(commnetBean.getContent());
            holder.mTextViewCommentTime.setText(Utils.time2(mContext, Long.parseLong(commnetBean.getOrder_time())) + "  " + commnetBean.getGoods_attr());
        }
    }

    @Override
    public int getItemCount() {
        return mCommnetBeanList.size() > 0 ? mCommnetBeanList.size() : 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewCommentGoodsPic;

        TextView mTextViewCommentGoodsName;

        TextView mTextViewCommentRank;

        TextView mTextViewCommentContent;

        TextView mTextViewCommentTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageViewCommentGoodsPic = (ImageView) itemView.findViewById(R.id.mImageViewCommentGoodsPic);

            mTextViewCommentGoodsName = (TextView) itemView.findViewById(R.id.mTextViewCommentGoodsName);

            mTextViewCommentRank = (TextView) itemView.findViewById(R.id.mTextViewCommentRank);

            mTextViewCommentContent = (TextView) itemView.findViewById(R.id.mTextViewCommentContent);

            mTextViewCommentTime = (TextView) itemView.findViewById(R.id.mTextViewCommentTime);
        }
    }
}
