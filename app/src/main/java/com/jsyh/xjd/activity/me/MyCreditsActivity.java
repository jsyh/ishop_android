package com.jsyh.xjd.activity.me;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.activity.WebActivity;
import com.jsyh.xjd.adapter.personal.SignInRecordAdapter;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.SignInModel;
import com.jsyh.xjd.presenter.SignInPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.SignInView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分页面，包括签到获得积分，以及积分记录
 * Created by Su on 2016/4/26.
 */
public class MyCreditsActivity extends BaseActivity implements View.OnClickListener, SignInView {

    private static final String TAG = "credits";
    private TextView mTextViewSignIn, mTextViewSignTip, mTextViewUserName, mTextViewCredits;
    private ImageView mImageViewCreditsPhoto;
    private Button mButtonCreditsShop;

    private FrameLayout mLayoutSignIn;
    private ContentLoadingProgressBar mProgressBar;

    private RecyclerView mRecyclerViewCreditsDetailList;
    private SignInRecordAdapter mSignInRecordAdapter;

    private int points;//签到积分

    private List<SignInModel.DataBean.RecordBean> mDataBeanList = new ArrayList<>();
    private SignInPresenter mSignInPresenter;

    @Override
    public void initData() {

        mSignInRecordAdapter = new SignInRecordAdapter(this, mDataBeanList);
        mSignInPresenter = new SignInPresenter(this);
        points = getIntent().getIntExtra("points", 0);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_credits);


        mTextViewSignIn = (TextView) findViewById(R.id.mTextViewSignIn);
        mTextViewSignTip = (TextView) findViewById(R.id.mTextViewSignTip);
        mTextViewUserName = (TextView) findViewById(R.id.mTextViewUserName);
        mTextViewUserName.setText(ConfigValue.uInfor == null ? "" : ConfigValue.uInfor.getNick_name());
        mImageViewCreditsPhoto = (ImageView) findViewById(R.id.mImageViewCreditsPhoto);

        mButtonCreditsShop = (Button) findViewById(R.id.mButtonCreditsShop);
        mButtonCreditsShop.setOnClickListener(this);

        mLayoutSignIn = (FrameLayout) findViewById(R.id.mLayoutSignIn);
        mLayoutSignIn.setOnClickListener(this);
        //判断是点击积分进来还是点击签到按钮进来，点击签到按钮，并且该用户没有签到，直接进行签到操作
        if (getIntent().getBooleanExtra("sign", false) && ConfigValue.uInfor.getQd() == 0) {
            mSignInPresenter.signIn(MyCreditsActivity.this);
        }
        //若是已签到状态，直接变未已签到状态
        if (ConfigValue.uInfor.getQd() == 1) {
            changeSignStatus(300);
        }

        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.mProgressBar);
        //设置个人账户总积分
        mTextViewCredits = ((TextView) findViewById(R.id.mTextViewCredits));
        mTextViewCredits.setText(ConfigValue.uInfor == null ? "0" : ConfigValue.uInfor.getIntegration());

        mRecyclerViewCreditsDetailList = (RecyclerView) findViewById(R.id.mRecyclerViewCreditsDetailList);
        mRecyclerViewCreditsDetailList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCreditsDetailList.setAdapter(mSignInRecordAdapter);
        showProgressBar();
        mSignInPresenter.getSignInRecordList(this);
    }

    @Override
    public void initTitle() {
        ((TextView) findViewById(R.id.title)).setText("我的积分");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mLayoutSignIn:

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLayoutSignIn, "scaleX", 1.0f, 1.1f, 1.0f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mLayoutSignIn, "scaleY", 1.0f, 1.1f, 1.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator).with(objectAnimator1);
                animatorSet.setDuration(300).start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSignInPresenter.signIn(MyCreditsActivity.this);
                    }
                }, 300);
                break;

            case R.id.mButtonCreditsShop:
                String url = "http://139.196.169.165/mobile/exchange.php";
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("title", "积分商城");
                intent.putExtra("url", url);
                Utils.startActivityWithAnimation(this, intent);
                break;
        }
    }

    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerViewCreditsDetailList.setVisibility(View.GONE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerViewCreditsDetailList.setVisibility(View.VISIBLE);
    }

    //签到成功
    @Override
    public void onSuccess(BaseModel baseModel) {
        if (baseModel.getCode().equals("1")) {
            if (ConfigValue.uInfor.getIntegration() == null || ConfigValue.uInfor.getIntegration().equals("0")) {
                ConfigValue.uInfor.setIntegration(points + "");
            } else
                ConfigValue.uInfor.setIntegration((Integer.parseInt(ConfigValue.uInfor.getIntegration()) + points) + "");
            mTextViewCredits.setText(ConfigValue.uInfor == null ? "0" : ConfigValue.uInfor.getIntegration());
            changeSignStatus(0);
            mSignInPresenter.getSignInRecordList(this);
        }
        ToastUtil.showToast(this, baseModel.getMsg());
    }

    //签到失败
    @Override
    public void onFailure(Exception e) {

    }

    //获取记录列表
    @Override
    public void onSignInRecordResponse(SignInModel signInModel) {
        hideProgressBar();
        if (signInModel != null && signInModel.getData() != null) {
            mDataBeanList.clear();
            mDataBeanList.addAll(signInModel.getData().getRecord());
            mSignInRecordAdapter.notifyDataSetChanged();
        }
    }

    private void changeSignStatus(long delay) {

        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mLayoutSignIn, "rotationY", 0f, 360f);
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(mTextViewSignIn, "alpha", 1f, 0f, 1f);
        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(mTextViewSignTip, "alpha", 1f, 0f, 1f);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(objectAnimator4).with(objectAnimator5).with(objectAnimator6);
        animatorSet2.setDuration(800);
        animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet2.setStartDelay(delay);
        animatorSet2.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewSignIn.setText("已签到");
                        mTextViewSignTip.setText("+" + points + "积分");
                        mLayoutSignIn.setClickable(false);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextViewSignTip, "scaleX", 8f, 1f);
                        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mTextViewSignTip, "scaleY", 8f, 1f);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(objectAnimator).with(objectAnimator1);
                        animatorSet.setInterpolator(new BounceInterpolator());
                        animatorSet.setDuration(1000);
                        animatorSet.start();
                    }
                });
            }
        }, 400+delay);
    }

    @Override
    public void onSignInRecordFailure(Exception e) {
        hideProgressBar();
    }
}
