package com.jsyh.xjd.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.presenter.ForgetPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.ForgetView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by Su on 2016/4/24.
 */
public class NewPasswordActivity extends BaseActivity implements View.OnClickListener, ForgetView {

    private EditText mEditTextPassword, mEditTextPasswordConfirm;

    private String mUserName, mVerifyCode, mPassword, mPasswordConfirm;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_new_password);

        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mEditTextPasswordConfirm = (EditText) findViewById(R.id.mEditTextPasswordConfirm);

        findViewById(R.id.mImageViewBack).setOnClickListener(this);
        findViewById(R.id.mTextViewFinish).setOnClickListener(this);

        mUserName = getIntent().getStringExtra("username");
        mVerifyCode = getIntent().getStringExtra("code");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mImageViewBack:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mTextViewFinish:
                if (confirmPassword()) {
                    new ForgetPresenter(this).submitData(this, mVerifyCode, mUserName, mPassword);
                }
                break;
        }
    }

    public boolean confirmPassword() {
        mPassword = mEditTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mPassword)) {
            ToastUtil.showToast(this, "请输入密码!");
            return false;
        }
        mPasswordConfirm = mEditTextPasswordConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(mPasswordConfirm)) {
            ToastUtil.showToast(this, "请确认密码！");
            return false;
        }

        if (!mPassword.equals(mPasswordConfirm)) {
            ToastUtil.showToast(this, "两次输入密码不一致！请重新输入！");
            return false;
        }
        return true;

    }

    @Override
    public void getData(BaseModel model) {
        ToastUtil.showToast(this, model.getMsg());
        if (model.getCode().equals("1")) {
            //设置新密码成功
            Utils.finishActivityWithAnimation(this);
        }
    }
}
