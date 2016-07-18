package com.jsyh.xjd.activity.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.model.FinishRegisterEvent;
import com.jsyh.xjd.model.PayModel;
import com.jsyh.xjd.presenter.RegisterPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.RegisterView;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by Su on 2016/4/24.
 */
public class FinishRegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView {

    private EditText mEditTextPassword, mEditTextPasswordConfirm;

    private String mUserName, mVerifyCode, mPassword, mPasswordConfirm;
    private RegisterPresenter mRegisterPresenter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_finish_register);
        mRegisterPresenter = new RegisterPresenter(this);
        mUserName = getIntent().getStringExtra("username");
        mVerifyCode = getIntent().getStringExtra("code");

        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mEditTextPasswordConfirm = (EditText) findViewById(R.id.mEditTextPasswordConfirm);

        findViewById(R.id.mImageViewBack).setOnClickListener(this);
        findViewById(R.id.mTextViewFinish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mImageViewBack:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mTextViewFinish:
                if (confirmPassword()) {
                    //new ForgetPresenter(this).submitData(this, mVerifyCode, mUserName, mPassword);
                    mRegisterPresenter.finishRegister(this, mUserName, mPassword, mVerifyCode);
                    // ToastUtil.showToast(this, "提交注册");
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
    public void register(PayModel payModel) {
        if (payModel.getCode().equals("1")) {
            Utils.finishActivityWithAnimation(this);
            //发送消息
            EventBus.getDefault().post(new FinishRegisterEvent(mUserName, mPassword));
        }
        ToastUtil.showToast(this, payModel.getMsg());
    }
}
