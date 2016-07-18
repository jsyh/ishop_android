package com.jsyh.xjd.activity.me;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.presenter.SubmitExpressPresenter;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by Su on 2016/5/12.
 */
public class WriteExpressSnActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEditTextExpressSn, mEditTextExpressName;
    private String back_id, shipping_no, shipping_name;

    @Override
    public void initData() {
        super.initData();
        back_id = getIntent().getStringExtra("back_id");
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_write_express_sn);
        mEditTextExpressSn = (EditText) findViewById(R.id.mEditTextExpressSn);
        mEditTextExpressName = (EditText) findViewById(R.id.mEditTextExpressName);
        findViewById(R.id.mButtonSubmit).setOnClickListener(this);
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ((TextView) findViewById(R.id.title)).setText("填写物流单号");
        findViewById(R.id.fl_Left).setOnClickListener(this);
    }

    //验证是否输入
    public void verify() {
        shipping_no = mEditTextExpressSn.getText().toString().trim();
        if (TextUtils.isEmpty(shipping_no)) {
            ToastUtil.showToast(this, "请填写快递单号！");
            return;
        }
        shipping_name = mEditTextExpressName.getText().toString().trim();
        if (TextUtils.isEmpty(shipping_name)) {
            ToastUtil.showToast(this, "请填写快递名称！");
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mButtonSubmit:
                Utils.showOfficialDialog(this, "提示", "确认提交单号？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //验证条件是否为空
                        verify();
                        submitExpressSn();
                    }
                }, null);
                break;
        }
    }

    public void submitExpressSn() {
        new SubmitExpressPresenter().submitExpressPresenter(this, back_id, shipping_no, shipping_name);
    }
}
