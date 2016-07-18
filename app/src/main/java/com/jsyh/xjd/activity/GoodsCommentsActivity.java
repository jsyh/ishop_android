package com.jsyh.xjd.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.adapter.goods.CommentsAdapter;
import com.jsyh.xjd.model.CommentsModel;
import com.jsyh.xjd.model.CommentsModel2;
import com.jsyh.xjd.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2016/4/5.
 * 评论页面
 */
public class GoodsCommentsActivity extends BaseActivity implements View.OnClickListener {
    private CommentsAdapter mCommentsAdapter;
    private List<CommentsModel2> commentsList;

    private CommentsModel commentsModel;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_detail_comments);
        Intent intent = getIntent();

        commentsModel = intent.getParcelableExtra("comments");

        TextView mTextViewAll = (TextView) findViewById(R.id.mTextViewAll);
        TextView mTextViewGood = (TextView) findViewById(R.id.mTextViewGood);
        TextView mTextViewMedium = (TextView) findViewById(R.id.mTextViewMedium);
        TextView mTextViewBad = (TextView) findViewById(R.id.mTextViewBad);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        //获取传值数据

        mTextViewAll.setText("全部(" + commentsModel.comment.size() + ")");
        mTextViewGood.setText("好评(" + commentsModel.good + ")");
        mTextViewMedium.setText("中评(" + commentsModel.medium + ")");
        mTextViewBad.setText("差评(" + commentsModel.bad + ")");

        mTextViewAll.setOnClickListener(this);
        mTextViewGood.setOnClickListener(this);
        mTextViewMedium.setOnClickListener(this);
        mTextViewBad.setOnClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsList = new ArrayList<>();

        commentsList.addAll(commentsModel.comment);

        mCommentsAdapter = new CommentsAdapter(commentsList, this);
        mRecyclerView.setAdapter(mCommentsAdapter);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        findViewById(R.id.fl_Left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.finishActivityWithAnimation(GoodsCommentsActivity.this);
            }
        });
        TextView mTextViewTitle = (TextView) findViewById(R.id.title);
        mTextViewTitle.setText("评论");
    }

    @Override
    public void onClick(View v) {
        commentsList.clear();

        switch (v.getId()) {
            case R.id.mTextViewAll:
                commentsList.addAll(commentsModel.comment);
                mCommentsAdapter.notifyDataSetChanged();
                break;
            case R.id.mTextViewGood:
                for (CommentsModel2 commentsModel2 : commentsModel.comment) {
                    if (commentsModel2.rank.equals("5"))
                        commentsList.add(commentsModel2);
                }
                break;
            case R.id.mTextViewMedium:
                for (CommentsModel2 commentsModel2 : commentsModel.comment) {
                    if (commentsModel2.rank.equals("3"))
                        commentsList.add(commentsModel2);
                }
                break;
            case R.id.mTextViewBad:
                for (CommentsModel2 commentsModel2 : commentsModel.comment) {
                    if (commentsModel2.rank.equals("1"))
                        commentsList.add(commentsModel2);
                }
                break;
        }

        mCommentsAdapter.notifyDataSetChanged();

    }
}
