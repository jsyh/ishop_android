package com.jsyh.xjd.activity.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.personal.CommentsListAdapter;
import com.jsyh.xjd.model.PersonalCommentsModel;
import com.jsyh.xjd.presenter.CommentsPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.CommentsView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Su on 2016/5/4.
 */
public class CommentsListActivity extends BaseActivity implements View.OnClickListener, CommentsView {
    private CommentsPresenter mCommentsPresenter;

    private List<PersonalCommentsModel.DataBean.CommnetBean> mCommnetBeanList;
    private RecyclerView mRecyclerComments;
    private CommentsListAdapter mCommentsListAdapter;
    private TextView mTextViewUserName, mTextViewCommentsNum;

    @Override
    public void initData() {
        super.initData();
        mCommentsPresenter = new CommentsPresenter(this);
        mCommentsPresenter.getCommentsList(this, 0);
        mCommnetBeanList = new ArrayList<>();
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_comments);

        mTextViewUserName = (TextView) findViewById(R.id.mTextViewUserName);
        mTextViewCommentsNum = (TextView) findViewById(R.id.mTextViewCommentsNum);

        mRecyclerComments = (RecyclerView) findViewById(R.id.mRecyclerComments);
        mCommentsListAdapter = new CommentsListAdapter(this, mCommnetBeanList);
        mRecyclerComments.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerComments.setAdapter(mCommentsListAdapter);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("我的评论");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
        }
    }

    @Override
    public void onResponse(PersonalCommentsModel model) {
        if (model.getCode().equals("1")) {
            mCommnetBeanList.clear();
            mCommnetBeanList.addAll(model.getData().getCommnet());

            mTextViewUserName.setText("用户名：" + model.getData().getUsername());
            mTextViewCommentsNum.setText("发布" + model.getData().getCount() + "条评论");
        } else {
            mCommentsListAdapter.viewType = -2;
        }
        mCommentsListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String s) {

    }
}
