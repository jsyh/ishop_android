package com.jsyh.xjd.adapter.goods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.model.CommentsModel2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Su on 2016/4/5.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<CommentsModel2> commentsList;
    private Context context;
    private int viewType = -10;

    public CommentsAdapter(List<CommentsModel2> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -10) {

            View emptyView = LayoutInflater.from(context).inflate(R.layout.recycler_empty, parent, false);
            ImageView imageView = (ImageView) emptyView.findViewById(R.id.mImageViewEmpty);
            imageView.setImageResource(R.mipmap.goods_shop_empty);
            ((TextView) emptyView.findViewById(R.id.mTextView1)).setText("没有符合要求的评论");
            return new CommentsViewHolder(emptyView);
        }
        return new CommentsViewHolder(LayoutInflater.from(context).inflate(R.layout.comments_item, null));
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        if (commentsList.size() > 0) {

            holder.mTextViewUserName.setText(commentsList.get(position).content_name);
            holder.mTextViewCommentContent.setText(commentsList.get(position).content);
            long time = Long.parseLong(commentsList.get(position).time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

            holder.mTextViewBuyRecordTime.setText(sdf.format(new Date(time * 1000)));
            holder.mTextViewBuyRecord.setText(commentsList.get(position).gmjl.replaceAll("\\r\\n","  "));
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size() > 0 ? commentsList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (commentsList.size() <= 0)
            return viewType;
        return super.getItemViewType(position);
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewUserName;
        private TextView mTextViewCommentContent;
        private TextView mTextViewBuyRecordTime;
        private TextView mTextViewBuyRecord;

        public CommentsViewHolder(View itemView) {
            super(itemView);

            mTextViewUserName = (TextView) itemView.findViewById(R.id.mTextViewUserName);
            mTextViewCommentContent = (TextView) itemView.findViewById(R.id.mTextViewCommentContent);
            mTextViewBuyRecordTime = (TextView) itemView.findViewById(R.id.mTextViewBuyRecordTime);
            mTextViewBuyRecord = (TextView) itemView.findViewById(R.id.mTextViewBuyRecord);
        }
    }
}
