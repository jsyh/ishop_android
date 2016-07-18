package com.jsyh.xjd.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.presenter.ChangePasswordPresenter;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

/**
 * Created by sks on 2015/9/17.
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private String oldPass, newPass, newPassConfirm;
    private EditText mEditTextOldPassword, mEditTextPassword, mEditTextPasswordConfirm;

    @Override
    public void initView() {
        setContentView(R.layout.activity_changepassword);

        findViewById(R.id.mImageViewBack).setOnClickListener(this);
        mEditTextOldPassword = (EditText) findViewById(R.id.mEditTextOldPassword);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mEditTextPasswordConfirm = (EditText) findViewById(R.id.mEditTextPasswordConfirm);
        findViewById(R.id.mTextViewFinish).setOnClickListener(this);
    }


    private void submit() {

        oldPass = mEditTextOldPassword.getText().toString().trim();
        if (TextUtils.isEmpty(oldPass)) {
            ToastUtil.showToast(this, "请输入旧密码！");
            return;
        }
        newPass = mEditTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(newPass)) {
            ToastUtil.showToast(this, "请输入新密码！");
            return;
        }
        newPassConfirm = mEditTextPasswordConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(newPassConfirm)) {
            ToastUtil.showToast(this, "请确认新密码！");
            return;
        }
        if (!newPass.equals(newPassConfirm)) {
            ToastUtil.showToast(this, "两次新密码输入不一致！");
            return;
        }

        new ChangePasswordPresenter(this).submitChangeInfor(oldPass, newPass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mImageViewBack:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mTextViewFinish:
                submit();
                break;
        }
    }
}
