package com.jsyh.xjd.activity.me;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.ReturnGoodsModel;
import com.jsyh.xjd.presenter.ReturnPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.ReturnView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by Su on 2016/5/10.
 */
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, ReturnView {

    private TextView mTextViewTitle;
    private EditText mEditTextReturnReason, mEditTextReturnPrice, mEditTextReturnIntro, mEditTextFixReason, mEditTextFixIntro;
    private String mReturnReason = "", mReturnPrice = "", mReturnIntro = "", mFixReason = "", mFixIntro = "";
    private LinearLayout mLayout1, mLayout2;
    private boolean flag = false;

    private String goodsId, orderId, goodsPrice;

    private ReturnPresenter mReturnPresenter;

    @Override
    public void initData() {
        super.initData();
        goodsId = getIntent().getStringExtra("goodsId");
        orderId = getIntent().getStringExtra("orderId");
        goodsPrice = getIntent().getStringExtra("goodsPrice");
        mReturnPresenter = new ReturnPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_apply_service);
        findViewById(R.id.mButtonSubmitApply).setOnClickListener(this);
        ((RadioGroup) findViewById(R.id.mRadioGroupApply)).setOnCheckedChangeListener(this);
        mEditTextReturnReason = (EditText) findViewById(R.id.mEditTextReturnReason);
        mEditTextReturnPrice = (EditText) findViewById(R.id.mEditTextReturnPrice);
        mEditTextReturnPrice.setText("￥" + goodsPrice);
        mEditTextReturnIntro = (EditText) findViewById(R.id.mEditTextReturnIntro);

        mEditTextFixReason = (EditText) findViewById(R.id.mEditTextFixReason);
        mEditTextFixIntro = (EditText) findViewById(R.id.mEditTextFixIntro);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        mTextViewTitle = (TextView) findViewById(R.id.title);
        mTextViewTitle.setText("申请退货");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        mLayout1 = (LinearLayout) findViewById(R.id.mLayout1);
        mLayout2 = (LinearLayout) findViewById(R.id.mLayout2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mButtonSubmitApply:
                //ToastUtil.showToast(this, "提交");
                Utils.showOfficialDialog(this, "提示", "确认提交申请吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getReturnReason();
                        if (!flag) {
                            //ToastUtil.showToast(ApplyServiceActivity.this, "申请退货" + mReturnReason + "/" + mReturnPrice + "/" + mReturnIntro);
                            mReturnPresenter.submitApply(ApplyServiceActivity.this, orderId, goodsId, mReturnIntro, mReturnReason, goodsPrice, 1);
                        } else {
                            mReturnPresenter.submitApply(ApplyServiceActivity.this, orderId, goodsId, mReturnIntro, mReturnReason, null, 3);
                            // ToastUtil.showToast(ApplyServiceActivity.this, "申请维修" + mFixReason + "/" + mFixIntro);
                        }
                    }
                }, null);
                break;
        }
    }

    public void getReturnReason() {

        mReturnReason = mEditTextReturnReason.getText().toString().trim();
        mReturnPrice = mEditTextReturnPrice.getText().toString().trim();
        mReturnIntro = mEditTextReturnIntro.getText().toString().trim();

        mFixReason = mEditTextFixReason.getText().toString().trim();
        mFixIntro = mEditTextFixIntro.getText().toString().trim();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.mRadioButton1:
                mLayout1.setVisibility(View.VISIBLE);
                mLayout2.setVisibility(View.GONE);
                mTextViewTitle.setText("申请退货");
                flag = false;
                break;
            case R.id.mRadioButton2:
                mLayout1.setVisibility(View.GONE);
                mLayout2.setVisibility(View.VISIBLE);
                mTextViewTitle.setText("申请维修");
                flag = true;
                break;
        }
    }

    //调用提交申请接口返回值

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onApplyResponse(BaseModel response) {
        if (response.getCode().equals("1")) {
            Utils.finishActivityWithAnimation(this);
        }
        ToastUtil.showToast(this, response.getMsg());
    }

    @Override
    public void onGetResponse(ReturnGoodsModel returnGoodsModel) {

    }
}
