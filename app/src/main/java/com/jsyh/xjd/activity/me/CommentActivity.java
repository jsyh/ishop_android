package com.jsyh.xjd.activity.me;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.adapter.personal.WaitCommentAdapter;
import com.jsyh.xjd.presenter.CommentsPresenter;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Su on 2016/5/4.
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView mRecyclerViewComment;
    private List<String> mGoodsIdList;
    private WaitCommentAdapter mWaitCommentAdapter;
    private String orderId;

    @Override
    public void initData() {
        super.initData();
        orderId = getIntent().getStringExtra("orderId");
        mGoodsIdList = getIntent().getStringArrayListExtra("goodsIds");

        mWaitCommentAdapter = new WaitCommentAdapter(this, mGoodsIdList);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_comment);
        mRecyclerViewComment = (RecyclerView) findViewById(R.id.mRecyclerViewComment);
        mRecyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("comment", event.getAction() + "------------------------------");
                return false;
            }
        });

        mRecyclerViewComment.setAdapter(mWaitCommentAdapter);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("发表评价");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        findViewById(R.id.mButtonConfirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mButtonConfirm:
                // ToastUtil.showToast(this, "发表评论成功");
                Utils.showOfficialDialog(this, "提示", "确认提交评论？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitComment();
                    }

                    private void submitComment() {
                        Map<String, String> commentsMap = mWaitCommentAdapter.getMap();
                        List<String> comments = new ArrayList<>();
                        for (Map.Entry<String, String> entry : commentsMap.entrySet()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("goods_id", entry.getValue().split("@@", 3)[0]);//评价商品id
                            map.put("rank", entry.getValue().split("@@", 3)[1]);//商品等级
                            if (entry.getValue().split("@@", 3)[2].isEmpty()) {
                                ToastUtil.showToast(CommentActivity.this, "请填写评论内容！");
                                return;
                            }

                            map.put("comments", entry.getValue().split("@@", 3)[2]);//评价商品内容
                            JSONObject jsonObject = new JSONObject(map);
                            comments.add(jsonObject.toString());
                        }
                        Log.d("comment", comments.toString());

                        new CommentsPresenter().submitComment(CommentActivity.this, orderId, comments.toString());
                        //Utils.finishActivityWithAnimation(this);
                    }
                }, null);
                break;
        }
    }
}
