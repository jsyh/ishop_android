package com.jsyh.xjd.activity.me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.model.FinishRegisterEvent;
import com.jsyh.xjd.model.LoginModel;
import com.jsyh.xjd.model.UserInforModel;
import com.jsyh.xjd.presenter.LoginPresenter;
import com.jsyh.xjd.presenter.UserInforPresenter;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.LoginView;
import com.jsyh.xjd.views.UserInforView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2015/8/31.
 * 登陆
 */
public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener,
        UserInforView {
    private LoginPresenter loginPresenter;
    private UserInforPresenter uPresenter;

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;

    private String mUserName;
    private String mPassword;

    private ImageView mImageViewCancle;

    private ProgressDialog mLoadingDialog;
    UMSocialService mController;

    @Override
    public void initData() {
        super.initData();
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");

        //微博
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        //QQ
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, getResources().getString(R.string.QQ_APP_ID),
                getResources().getString(R.string.QQ_AppSecret));
        qqSsoHandler.addToSocialSDK();
        //微信
        UMWXHandler wxHandler = new UMWXHandler(getApplicationContext(), getResources().getString(R.string.WX_APP_ID),
                getResources().getString(R.string.WX_APPSecret));
        wxHandler.addToSocialSDK();

        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initDialog();
    }

    //初始化对话框
    public void initDialog() {
        mLoadingDialog = new ProgressDialog(this);
//        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setMessage("登录中");
    }


    @Subscribe
    public void onEventMainThread(FinishRegisterEvent event) {
        String userName = event.getUserName();
        String passWord = event.getPassWord();
        loginPresenter.loadLogin(this, userName, passWord);
    }

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_login);

        uPresenter = new UserInforPresenter(this);
        loginPresenter = new LoginPresenter(this);


        mEditTextUserName = (EditText) findViewById(R.id.mEditTextUserName);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);

        findViewById(R.id.mImageViewCancelLogin).setOnClickListener(this);
        findViewById(R.id.mTextViewLogin).setOnClickListener(this);
        findViewById(R.id.mTextViewForgetPass).setOnClickListener(this);
        findViewById(R.id.mTextViewRegisterNow).setOnClickListener(this);

        findViewById(R.id.mButtonSina).setOnClickListener(this);
        findViewById(R.id.mButtonQQ).setOnClickListener(this);
        findViewById(R.id.mButtonWeChat).setOnClickListener(this);
    }


    //用EventBus监听用户注册成功与否，成功之后直接进行登录操作


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mImageViewCancelLogin:
                Utils.finishActivityWithAnimation(this);
                break;
            case R.id.mTextViewLogin:
                if (verifyUserInfo())
                    loginPresenter.loadLogin(this, mUserName, mPassword);
                break;
            case R.id.mTextViewForgetPass:
                Intent intent = new Intent(this, ForgetActivity.class);
                // startActivity(intent);
                Utils.startActivityWithAnimation(this, intent);
                break;
            case R.id.mTextViewRegisterNow:
                Intent intent1 = new Intent(this, RegisterActivity.class);
                Utils.startActivityWithAnimation(this, intent1);
                break;

            case R.id.mButtonSina:
                //ToastUtil.showToast(this, "微博登陆");
                sinaLogin();

                break;
            case R.id.mButtonQQ:
                //ToastUtil.showToast(this, "qq登录");
                qqLogin();
                break;
            case R.id.mButtonWeChat:
                //ToastUtil.showToast(this, "微信登录");
                weChatLogin();
                break;
        }

    }

    private void sinaLogin() {

        //授权接口
        mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                mLoadingDialog.show();
            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
                if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                    Toast.makeText(LoginActivity.this, "授权成功.", Toast.LENGTH_SHORT).show();
                    Log.d("sina", bundle.toString());
                    mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMDataListener() {
                        @Override
                        public void onStart() {
                            //Toast.makeText(LoginActivity.this, "获取个人信息...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete(int status, Map<String, Object> info) {
                            if (status == 200 && info != null) {
                                StringBuilder sb = new StringBuilder();
                                Set<String> keys = info.keySet();
                                for (String key : keys) {
                                    sb.append(key + "=" + info.get(key).toString() + "\r\n");
                                }
                                Log.d("sina", sb.toString());
                                //进行登录操作

                                String userName = info.get("screen_name").toString();
                                String uid = info.get("uid").toString();
                                loginPresenter.socialLogin(LoginActivity.this, "weibo", uid, userName);

                            } else {
                                Log.d("sina", "发生错误：" + status);
                                mLoadingDialog.dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    mLoadingDialog.dismiss();
                }

            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                mLoadingDialog.dismiss();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                mLoadingDialog.dismiss();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
/*

        UMSsoHandler ssoHandler1 = SocializeConfig.getSocializeConfig().getSsoHandler(requestCode);
        if (ssoHandler1 != null) {
            ssoHandler1.authorizeCallBack(requestCode, resultCode, data);
        }
*/

    }

    //QQ登录
    private void qqLogin() {
        mController.doOauthVerify(mContext, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT).show();
                mLoadingDialog.show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
                mLoadingDialog.dismiss();
            }

            @Override
            public void onComplete(final Bundle value, SHARE_MEDIA platform) {
                Log.d("tencentQQ", value.toString());
                Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
                //获取相关授权信息
                mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        //Toast.makeText(LoginActivity.this, "获取个人信息...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + info.get(key).toString() + "\r\n");
                            }
                            //ToastUtil.showToast(LoginActivity.this,info.get("uid").toString()+"............"+info.get("username").toString());
                            Log.d("tencentQQ", sb.toString());

                            String userName = info.get("screen_name").toString();
                            String uid = value.getString("uid");
                            loginPresenter.socialLogin(LoginActivity.this, "qq", uid, userName);

                        } else {
                            Log.d("tencentQQ", "发生错误：" + status);
                            mLoadingDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();
                mLoadingDialog.dismiss();
            }
        });
    }


    private void weChatLogin() {
        mController.doOauthVerify(mContext, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT).show();
                mLoadingDialog.show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
                mLoadingDialog.dismiss();
            }

            @Override
            public void onComplete(final Bundle value, SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
                //获取相关授权信息
                mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        //Toast.makeText(LoginActivity.this, "获取个人信息...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + info.get(key).toString() + "\r\n");
                            }
                            Log.d("wechat", sb.toString());
                            String userName = info.get("nickname").toString();
                            String uid = value.getString("uid");
                            loginPresenter.socialLogin(LoginActivity.this, "qq", uid, userName);
                        } else {
                            Log.d("wechat", "发生错误：" + status);
                            mLoadingDialog.dismiss();
                        }

                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();
                mLoadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
        mController.getConfig().removeSsoHandler(SHARE_MEDIA.SINA);
        mController.getConfig().removePlatform(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA,SHARE_MEDIA.QQ);
        mController.getConfig().getAllPlatforms(this,mController);
    }

    //验证用户名密码是否合法
    private boolean verifyUserInfo() {
        mUserName = mEditTextUserName.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName)) {

            ToastUtil.showToast(this, "手机号不能为空！");
            return false;
        }
        mPassword = mEditTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mPassword)) {

            ToastUtil.showToast(this, "密码不能为空！");
            return false;
        }
        return true;
    }


    @Override
    public void login(LoginModel response) {
        if (response.getCode().equals("1")) {
            String key = response.getData().getKey();
            SPUtils.put(this, SPConfig.KEY, key);
            ConfigValue.DATA_KEY = key;
            uPresenter.loadInfor(this);
        }
        Utils.showToast(this, response.getMsg());
        if (mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void error() {
        if (mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    public void inforData(UserInforModel response) {
        if (response.getCode().equals("1")) {
            ConfigValue.uInfor = response.getData().get(0);
            /*String num = ConfigValue.uInfor.getCart_num() == null ? "0" : ConfigValue.uInfor.getCart_num();
            Intent mIntent = new Intent(ConfigValue.ACTION_ALTER_CARTGOODS_NUMS);
            mIntent.putExtra("cartgoodsnum", num);
            //发送广播
            sendBroadcast(mIntent);*/
            ConfigValue.DATA_CHANGE_TAG = false;
            Utils.finishActivityWithAnimation(this);
        } else
            ToastUtil.showToast(this, response.getMsg());
    }

}
