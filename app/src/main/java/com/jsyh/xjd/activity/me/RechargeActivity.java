package com.jsyh.xjd.activity.me;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.BaseActivity;
import com.jsyh.xjd.alipay.PayResult;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.PayModel;
import com.jsyh.xjd.presenter.RechargePresenter;
import com.jsyh.xjd.utils.KeyBoardUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.PayView;
import com.jsyh.xjd.wxapi.WXPay;
import com.jsyh.xjd.wxapi.WXPayResult;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by sks on 2015/10/28.
 * 账户充值
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener, PayView {

    TextView title;
    ImageView back;
    private Context context;
    private EditText editMoney;
    private TextView btnEnsure;
    private TextView txtBalance;
    private String money;

    private WXPay wxPay;
    private RechargePresenter rPresenter;

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        BigDecimal beforeBigDecimal = new BigDecimal(ConfigValue.uInfor.getUser_money());
                        ConfigValue.uInfor.setUser_money(beforeBigDecimal.add(new BigDecimal(money)).toString());
                        Utils.showToast(context, "支付成功");
                        Utils.finishActivityWithAnimation(RechargeActivity.this);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Utils.showToast(context, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Utils.showToast(context, "支付失败");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    if (!(boolean) msg.obj)
                        Utils.showToast(context, "检查结果为：" + msg.obj);
                    else
                        rPresenter.requestAliPay(context, money, 1);
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_recharge);
        //注册EventBus
        EventBus.getDefault().register(this);
        context = this;
        rPresenter = new RechargePresenter(this);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        back.setBackgroundResource(R.mipmap.ic_back);
        title.setText("账户充值");
        findViewById(R.id.fl_Left).setOnClickListener(this);
        txtBalance = (TextView) findViewById(R.id.txtBalance);
        txtBalance.setText("账户余额：￥" + ConfigValue.uInfor.getUser_money());
        editMoney = (EditText) findViewById(R.id.editMoney);
        btnEnsure = (TextView) findViewById(R.id.btnSure);
        editMoney.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            money = editMoney.getText().toString().trim();
            if (!money.equals("")) {
                btnEnsure.setTextColor(getResources().getColor(R.color.white));
                btnEnsure.setBackgroundResource(R.drawable.login_on_bg);
                btnEnsure.setClickable(true);
                btnEnsure.setOnClickListener(RechargeActivity.this);
            } else {
                btnEnsure.setTextColor(getResources().getColor(R.color.gary_text));
                btnEnsure.setBackgroundResource(R.drawable.login_off_bg);
                btnEnsure.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_Left:
                KeyBoardUtils.closeKeybord(this);
                break;
            case R.id.btnSure:
                //check();
                selectPayWay();
                //rPresenter.request(context, money);
                break;
        }
    }

    //选择支付方式
    private void selectPayWay() {
        View payView = LayoutInflater.from(this).inflate(R.layout.personal_recharge_type_view, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("选择支付方式")
                .setView(payView)
                .create();
        alertDialog.show();
        payView.findViewById(R.id.rlAlipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rPresenter.requestAliPay(context, money, 1);
                alertDialog.dismiss();
            }
        });
        payView.findViewById(R.id.rlWXpay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信支付
                //rPresenter.requestWXPay(context, money, 2);
                wxPay();
                alertDialog.dismiss();
            }
        });
    }

    private void wxPay() {
        wxPay = new WXPay(getApplicationContext());
        wxPay.regApp();
        boolean wxpay = wxPay.checkWXVerison();
        if (!wxpay) {
            ToastUtil.showToast(this, "您当前的微信版本不支持支付功能");
            return;
        }
        rPresenter.requestWXPay(context, money, 2);
    }

    @Override
    public void aliPayResponse(PayModel model) {
        if (model.getCode().equals("1")) {
            pay(model.getData());
        } else
            Utils.showToast(context, model.getMsg());
    }

    @Override
    public void wxPayResponse(String response) {
        if (response != null && response.length() > 0) {
            Log.e("get server pay params:", response);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                String code = jsonObject.getString("code");
                if (code.equals("1")) {
                    JSONObject json = jsonObject.getJSONObject("data");
                    if (null != json && !json.has("retcode")) {
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId = json.getString("appid");
                        req.partnerId = json.getString("partnerid");
                        req.prepayId = json.getString("prepayid");
                        req.nonceStr = json.getString("noncestr");
                        req.timeStamp = json.getString("timestamp");
                        req.packageValue = json.getString("package");
                        req.sign = json.getString("sign");
                        req.extData = "app data"; // optional
                        Toast.makeText(RechargeActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        wxPay.pay(req);
                    } else {
                        Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
                        Toast.makeText(RechargeActivity.this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RechargeActivity.this, "请登录后操作！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PAY_GET", "异常：" + e.getMessage());
                Toast.makeText(RechargeActivity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("PAY_GET", "服务器请求错误");
            Toast.makeText(RechargeActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void wxPayError(Request request, Object tag, Exception e) {
        Toast.makeText(RechargeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventMainThread(WXPayResult payResult) {
        /**
         *  0  成功	展示成功页面
         -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
         -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
         */
        if (payResult.getResCode().equals("0")) {
            ToastUtil.showToast(this, "支付成功");
            BigDecimal beforeBigDecimal = new BigDecimal(ConfigValue.uInfor.getUser_money());
            ConfigValue.uInfor.setUser_money(beforeBigDecimal.add(new BigDecimal(money)).toString());
            Utils.finishActivityWithAnimation(RechargeActivity.this);
        } else if (payResult.getResCode().equals("-1")) {
            ToastUtil.showToast(this, "支付异常：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等");
        } else if (payResult.getResCode().equals("-2")) {
            ToastUtil.showToast(this, "取消支付");
        }
    }


    public void pay(final String payInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RechargeActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check() {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(RechargeActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
    }
}
