package com.jsyh.xjd.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsCollectActivity;
import com.jsyh.xjd.activity.ShopsCollectActivity;
import com.jsyh.xjd.activity.me.AboutActivity;
import com.jsyh.xjd.activity.me.AddressListActivity;
import com.jsyh.xjd.activity.me.CommentsListActivity;
import com.jsyh.xjd.activity.me.FeedbackActivity;
import com.jsyh.xjd.activity.me.LoginActivity;
import com.jsyh.xjd.activity.me.MeBounsActivity;
import com.jsyh.xjd.activity.me.MyBalanceActivity;
import com.jsyh.xjd.activity.me.MyCreditsActivity;
import com.jsyh.xjd.activity.me.MyOrderActivity;
import com.jsyh.xjd.activity.me.MyShareActivity;
import com.jsyh.xjd.activity.me.ReturnActivity;
import com.jsyh.xjd.activity.me.SetupActicity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.model.BusEvent;
import com.jsyh.xjd.model.UserInforModel;
import com.jsyh.xjd.presenter.UserInforPresenter;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.UserInforView;
import com.jsyh.shopping.uilibrary.drawable.CartDrawable;
import com.jsyh.shopping.uilibrary.uiutils.ImageUtils;
import com.jsyh.shopping.uilibrary.views.CircleImageView;

import java.io.File;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 个人中心
 * Created by Su on 2016/4/23.
 */
public class PersonalFragment extends BaseFragment implements UserInforView {

    private View mView;
    private Context mContext;
    //登录注册还是加载头像
    private LinearLayout mLayoutLoginRegister;

    private LinearLayout mLayoutUser;

    private CircleImageView mCircleImageView;
    private Bitmap bitmap;


    private UserInforPresenter mUserInforPresenter;

    private TextView mTextViewSignIn;//签到
    private TextView mTextViewUnPaid, mTextViewUnSend, mTextViewUnReceived, mTextViewUnComment;

    private CartDrawable cartDrawable;
    private CartDrawable cartDrawable1;
    private CartDrawable cartDrawable2;
    private CartDrawable cartDrawable3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (mView == null)
            mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_personal, container, false);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        Log.d("EventBus", "onCreateView...");
        return mView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d("EventBus", "onDestroy...");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("EventBus", "onStop...");
    }


    @Override
    protected void initView() {
        //初始化并加载个人信息
        mUserInforPresenter = new UserInforPresenter(this);

        mLayoutLoginRegister = (LinearLayout) mView.findViewById(R.id.mLayoutLoginRegister);
        mLayoutLoginRegister.setOnClickListener(this);
        mLayoutUser = (LinearLayout) mView.findViewById(R.id.mLayoutUser);

        mView.findViewById(R.id.mLayoutUser).setOnClickListener(this);
        mView.findViewById(R.id.mImageViewSetUp).setOnClickListener(this);
        mTextViewSignIn = (TextView) mView.findViewById(R.id.mTextViewSignIn);
        mTextViewSignIn.setOnClickListener(this);

        mCircleImageView = (CircleImageView) mView.findViewById(R.id.mCircleImageViewPhoto);

        mView.findViewById(R.id.mLayoutRedPocket).setOnClickListener(this);
        mView.findViewById(R.id.mLayoutCredits).setOnClickListener(this);
        mView.findViewById(R.id.mLayoutBalance).setOnClickListener(this);


        mView.findViewById(R.id.mTableRowTotalOrders).setOnClickListener(this);
        mTextViewUnPaid = (TextView) mView.findViewById(R.id.mTextViewUnPaid);
        mTextViewUnPaid.setOnClickListener(this);
        mTextViewUnSend = (TextView) mView.findViewById(R.id.mTextViewUnSend);
        mTextViewUnSend.setOnClickListener(this);
        mTextViewUnReceived = (TextView) mView.findViewById(R.id.mTextViewUnReceived);
        mTextViewUnReceived.setOnClickListener(this);
        mTextViewUnComment = (TextView) mView.findViewById(R.id.mTextViewUnComment);
        mTextViewUnComment.setOnClickListener(this);
        mView.findViewById(R.id.mTextViewService).setOnClickListener(this);

        mView.findViewById(R.id.mTableRowCollectedGoods).setOnClickListener(this);
        mView.findViewById(R.id.mTableRowCollectedShops).setOnClickListener(this);
        mView.findViewById(R.id.mTableRowAddress).setOnClickListener(this);
        mView.findViewById(R.id.mTableRowComments).setOnClickListener(this);
        mView.findViewById(R.id.mTableRowShare).setOnClickListener(this);

        mView.findViewById(R.id.mTableRowHelp).setOnClickListener(this);
        mView.findViewById(R.id.mTableRowAbout).setOnClickListener(this);
    }


    @Override
    protected void initTitle() {


    }

    @Override
    protected void initData() {
        ConfigValue.DATA_KEY = (String) SPUtils.get(mContext, SPConfig.KEY, "");
        if (ConfigValue.DATA_KEY != null && !ConfigValue.DATA_KEY.equals("") && ConfigValue.uInfor != null) {
            initUserData();
        }
    }

    @Override
    public void onResume() {
        //先从本地查找用户key
        Log.d("EventBus", "resume.....................执行了");
        super.onResume();
        ConfigValue.DATA_KEY = (String) SPUtils.get(mContext, SPConfig.KEY, "");
        if (!ConfigValue.DATA_KEY.equals("") && ConfigValue.DATA_CHANGE_TAG) {
            ConfigValue.uInfor = null;
            mUserInforPresenter.loadInfor(mContext);
            Log.d("userinfo", "onResume.........................中请求数据了");
        } else {
            initUserData();
        }
        //加载头像
        initHeadPhoto();
    }

    @Override
    public void inforData(UserInforModel model) {
        if (model.getCode().equals("1")) {
            ConfigValue.uInfor = model.getData().get(0);
            String cartNum = ConfigValue.uInfor.getCart_num();
            // setCartNums(cartNum);//设置购物车数量
            ConfigValue.DATA_CHANGE_TAG = false;
        } else {
            if (model.getCode().equals("-220")) {
                SPUtils.remove(mContext, SPConfig.KEY);
            }
            ConfigValue.DATA_CHANGE_TAG = true;
            Utils.showToast(mContext, model.getMsg());
        }
        // ToastUtil.showToast(this, model.getMsg());
        initUserData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLayoutLoginRegister:
                Intent itLogin = new Intent(getActivity(), LoginActivity.class);
                Utils.startActivityWithAnimation(mContext, itLogin);
                break;

            case R.id.mLayoutUser:
            case R.id.mImageViewSetUp:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, SetupActicity.class));
                break;
            case R.id.mTextViewSignIn:
                //签到
                if (ConfigValue.uInfor != null) {

                    Intent intent = new Intent(mContext, MyCreditsActivity.class);
                    intent.putExtra("sign", true);
                    intent.putExtra("points",ConfigValue.uInfor.getPoints());
                    Utils.startActivityWithAnimation(mContext, intent);
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mLayoutRedPocket:
                //红包
                if (ConfigValue.uInfor != null)
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, MeBounsActivity.class));
                else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mLayoutCredits:
                //积分
                if (ConfigValue.uInfor != null) {

                    Intent intent = new Intent(mContext, MyCreditsActivity.class);
                    intent.putExtra("sign", false);
                    intent.putExtra("points",ConfigValue.uInfor.getPoints());
                    Utils.startActivityWithAnimation(mContext, intent);
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mLayoutBalance:
                //余额
                if (ConfigValue.uInfor != null)
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, MyBalanceActivity.class));
                else {
                    Utils.toLogin(mContext);
                }
                break;

            case R.id.mTableRowTotalOrders:
                //全部订单
                if (ConfigValue.uInfor != null) {
                    Intent orderIntent = new Intent(mContext, MyOrderActivity.class);
                    orderIntent.putExtra("position", 0);
                    Utils.startActivityWithAnimation(mContext, orderIntent);
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTextViewUnPaid:
                //待付款
                if (ConfigValue.uInfor != null) {
                    Intent orderIntent = new Intent(mContext, MyOrderActivity.class);
                    orderIntent.putExtra("position", 1);
                    Utils.startActivityWithAnimation(mContext, orderIntent);
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTextViewUnSend:
                //待发货
                if (ConfigValue.uInfor != null) {
                    Intent orderIntent = new Intent(mContext, MyOrderActivity.class);
                    orderIntent.putExtra("position", 2);
                    Utils.startActivityWithAnimation(mContext, orderIntent);
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTextViewUnReceived:
                if (ConfigValue.uInfor != null) {
                    Intent orderIntent = new Intent(mContext, MyOrderActivity.class);
                    orderIntent.putExtra("position", 3);
                    Utils.startActivityWithAnimation(mContext, orderIntent);
                } else {
                    Utils.toLogin(mContext);
                }
                //待收货
                break;
            case R.id.mTextViewUnComment:
                if (ConfigValue.uInfor != null) {
                    Intent orderIntent = new Intent(mContext, MyOrderActivity.class);
                    orderIntent.putExtra("position", 4);
                    Utils.startActivityWithAnimation(mContext, orderIntent);
                } else {
                    Utils.toLogin(mContext);
                }
                //待评价
                break;
            case R.id.mTextViewService:
                //售后
                //ToastUtil.showToast(mContext,);
                if (ConfigValue.uInfor != null) {
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, ReturnActivity.class));
                } else {
                    Utils.toLogin(mContext);
                }
                break;

            case R.id.mTableRowCollectedGoods:
                //收藏
                if (ConfigValue.uInfor != null)
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, GoodsCollectActivity.class));
                else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTableRowCollectedShops:
                if (Utils.online(mContext)) {
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, ShopsCollectActivity.class));
                } else {
                    Utils.toLogin(mContext);
                }

                break;
            case R.id.mTableRowAddress:
                if (Utils.online(mContext)) {
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, AddressListActivity.class));
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTableRowComments:
                if (Utils.online(mContext)) {
                    Utils.startActivityWithAnimation(mContext, new Intent(mContext, CommentsListActivity.class));
                } else {
                    Utils.toLogin(mContext);
                }
                break;
            case R.id.mTableRowShare:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, MyShareActivity.class));
                break;

            case R.id.mTableRowHelp:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, FeedbackActivity.class));
                break;
            //关于U
            case R.id.mTableRowAbout:
                Utils.startActivityWithAnimation(mContext, new Intent(mContext, AboutActivity.class));
                break;
        }

    }

    @Subscribe
    public void onEventMainThread(BusEvent event) {
        Log.d("EventBus", event.getCode());
        //if (event.getCode().equals("1"))
        initUserData();
    }


    private void initUserData() {
        if (cartDrawable == null)
            cartDrawable = new CartDrawable(mContext, 2, R.mipmap.personal_order_unpaid);
        if (cartDrawable1 == null)
            cartDrawable1 = new CartDrawable(mContext, 2, R.mipmap.personal_order_unsend);
        if (cartDrawable2 == null)
            cartDrawable2 = new CartDrawable(mContext, 2, R.mipmap.personal_order_unreceived);
        if (cartDrawable3 == null)
            cartDrawable3 = new CartDrawable(mContext, 2, R.mipmap.personal_order_uncomment);

        if (ConfigValue.uInfor != null) {
            mLayoutLoginRegister.setVisibility(View.GONE);
            mLayoutUser.setVisibility(View.VISIBLE);
            mTextViewSignIn.setVisibility(View.VISIBLE);
            //设置签到状态
            if (ConfigValue.uInfor.getQd() == 0) {
                mTextViewSignIn.setText("签到");
            } else if (ConfigValue.uInfor.getQd() == 1) {
                mTextViewSignIn.setText("已签到");
            }

            if (ConfigValue.uInfor.getNick_name() != null)
                ((TextView) mView.findViewById(R.id.mTextViewUserName)).setText(ConfigValue.uInfor.getNick_name());
            if (ConfigValue.uInfor.getBounts() != null)
                ((TextView) mView.findViewById(R.id.mTextViewRedPocket)).setText(ConfigValue.uInfor.getBounts());
            if (ConfigValue.uInfor.getIntegration() != null)
                ((TextView) mView.findViewById(R.id.mTextViewCredits)).setText(ConfigValue.uInfor.getIntegration());
            if (ConfigValue.uInfor.getUser_money() != null)
                ((TextView) mView.findViewById(R.id.mTextViewBalance)).setText(ConfigValue.uInfor.getUser_money());
            //待付款"pay": 9, 待发货"shipping_send": 0,  代收货"shipping": 0,  待评价角标"comment": 0,
            if (ConfigValue.uInfor.getAttention() != null)
                ((TextView) mView.findViewById(R.id.mTextViewCollectedGoods)).setText(ConfigValue.uInfor.getAttention());
            if (ConfigValue.uInfor.getSupplier() != null)
                ((TextView) mView.findViewById(R.id.mTextViewCollectedShops)).setText(ConfigValue.uInfor.getSupplier());

            //设置角标
            cartDrawable.setCatNum(ConfigValue.uInfor.getPay());
            mTextViewUnPaid.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable, null, null);

            cartDrawable1.setCatNum(ConfigValue.uInfor.getShipping_send());
            mTextViewUnSend.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable1, null, null);

            cartDrawable2.setCatNum(ConfigValue.uInfor.getShipping());
            mTextViewUnReceived.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable2, null, null);

            cartDrawable3.setCatNum(ConfigValue.uInfor.getComment());
            mTextViewUnComment.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable3, null, null);

        } else {
            mLayoutLoginRegister.setVisibility(View.VISIBLE);
            mLayoutUser.setVisibility(View.GONE);
            mTextViewSignIn.setVisibility(View.GONE);
            ((TextView) mView.findViewById(R.id.mTextViewRedPocket)).setText("0");
            ((TextView) mView.findViewById(R.id.mTextViewCredits)).setText("0");
            ((TextView) mView.findViewById(R.id.mTextViewBalance)).setText("0");
            ((TextView) mView.findViewById(R.id.mTextViewCollectedGoods)).setText("0");
            ((TextView) mView.findViewById(R.id.mTextViewCollectedShops)).setText("0");

            cartDrawable.setCatNum("0");
            mTextViewUnPaid.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable, null, null);

            cartDrawable1.setCatNum("0");
            mTextViewUnSend.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable1, null, null);

            cartDrawable2.setCatNum("0");
            mTextViewUnReceived.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable2, null, null);

            cartDrawable3.setCatNum("0");
            mTextViewUnComment.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable3, null, null);
        }
    }

    //图片保存在本地，查看本地有无头像，若有则显示，没有则不显示

    private void initHeadPhoto() {
        if (ConfigValue.uInfor != null) {
            String filePath = ConfigValue.HEAD_PHOTO_DIR + File.separator + ConfigValue.uInfor.getMobile() + ".jpg";
            File file = new File(filePath);
            if (file.exists()) {
                //if (bitmap == null)
                bitmap = ImageUtils.compressBitmap(filePath, 200, 200);
                if (bitmap != null) {
                    mCircleImageView.setImageBitmap(bitmap);
                /*if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }*/
                }
            }
        }
    }
}
