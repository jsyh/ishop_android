package com.jsyh.xjd.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.gson.JsonParseException;
import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.MyOrderActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.config.SPConfig;
import com.jsyh.xjd.fragment.CategoryFragment;
import com.jsyh.xjd.fragment.FoundFragment;
import com.jsyh.xjd.fragment.HomeFragment;
import com.jsyh.xjd.fragment.PersonalFragment;
import com.jsyh.xjd.fragment.ShopCartFragment;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.GsonUtils;
import com.jsyh.xjd.model.BusEvent;
import com.jsyh.xjd.model.MenuItemModel;
import com.jsyh.xjd.model.MenuModel;
import com.jsyh.xjd.model.UserInforModel;
import com.jsyh.xjd.presenter.UserInforPresenter;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.UserInforView;
import com.jsyh.shopping.uilibrary.drawable.CartDrawable;
import com.squareup.okhttp.Request;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements
        OnCheckedChangeListener, UserInforView {
    private static FragmentManager fMgr;
    private RadioButton rbHome;
    private RadioButton rbCategory;
    private RadioButton rbFound;
    private RadioButton rbShoppingCart;
    private RadioButton rbMe;
    private RadioGroup radioGroup;

    private PushAgent mPushAgent;


    //private PersonalPresenter mPresenter;//初始化个人信息
    private UserInforPresenter mUserInforPresenter;
    public String goodsNum = "";
    //是否显示底部导航栏
    private boolean isBottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();
        ConfigValue.DATA_KEY = (String) SPUtils.get(this, SPConfig.KEY, "");
        // 获取FragmentManager实例
        fMgr = getSupportFragmentManager();
        dealBottomButtonsClickEvent();
        initFragment();
        //网络检查
        if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(this)) {
            Utils.showToast(this, "网络好像阻塞了哦，亲");
        }
        //开启推送服务
        mPushAgent = PushAgent.getInstance(getApplicationContext());
        mPushAgent.onAppStart();
        //开启推送并设置注册的回调处理
        mPushAgent.enable(mRegisterCallback);
        //添加别名
//		new AddAliasTask(ConfigValue.QQAPP_KEY, ALIAS_TYPE.QQ).execute();
        //初始化并加载个人信息
        mUserInforPresenter = new UserInforPresenter(this);

    }


    @Override
    public void inforData(UserInforModel model) {
        if (model.getCode().equals("1")) {
            ConfigValue.uInfor = model.getData().get(0);
            goodsNum = ConfigValue.uInfor.getCart_num();
            setCartNums(goodsNum);
            ConfigValue.DATA_CHANGE_TAG = false;

            EventBus.getDefault().post(new BusEvent(model.getCode()));
        } else {
            if (model.getCode().equals("-220")) {
            }
            SPUtils.remove(this, SPConfig.KEY);
            ConfigValue.uInfor = null;
            //Utils.showToast(this, model.getMsg());
            ConfigValue.DATA_CHANGE_TAG = true;
            ToastUtil.showToast(this, model.getMsg());

            EventBus.getDefault().post(new BusEvent(model.getCode()));
        }
    }

    //增加购物车角标
    public void setCartNums(String cartNums) {
        goodsNum = cartNums;
        CartDrawable cartDrawable = new CartDrawable(this, ConfigValue.iconFlag, 0);
        cartDrawable.setCatNum(cartNums);
        rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable, null, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //先从本地查找用户key
        ConfigValue.DATA_KEY = (String) SPUtils.get(this, SPConfig.KEY, "");
        if (!ConfigValue.DATA_KEY.equals("") /*&& ConfigValue.DATA_CHANGE_TAG*/) {
            //ConfigValue.uInfor = null;
            mUserInforPresenter.loadInfor(this);
            Log.d("userinfo", "resume.....................Main中请求数据了");
        } else {

        }
        Log.d("userinfo", "MainActivity,resume.....................执行了" + "------" + ConfigValue.DATA_KEY + "--------" + ConfigValue.DATA_CHANGE_TAG);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra("intentType", 0);
        Log.d("intentType", type + "------------");
        if (type == 100) {
            setCurrentPage(0);
        } else if (type == 200) {
            setCurrentPage(4);
            Utils.startActivityWithAnimation(this, new Intent(this, MyOrderActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mBroadcastReseiver);
        ConfigValue.uInfor = null;
        ConfigValue.DATA_CHANGE_TAG = true;
        ConfigValue.SHOP_CART_TAG = true;
        Log.d("userinfo", "MainActivity,onDestroy.....................执行了" + "------" + ConfigValue.DATA_KEY + "--------" + ConfigValue.DATA_CHANGE_TAG);
        mPushAgent = null;
        mRegisterCallback = null;
    }

    /**
     * 初始化首个Fragment
     */
    private void initFragment() {
        FragmentTransaction ft = fMgr.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.fragmentRoot, homeFragment, "homeFragment");
        ft.addToBackStack("homeFragment");
        ft.commit();

    }

    /**
     * 处理底部点击事件
     */
    private void dealBottomButtonsClickEvent() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbHome = (RadioButton) findViewById(R.id.rbHome);
        rbCategory = (RadioButton) findViewById(R.id.rbCategory);
        rbFound = (RadioButton) findViewById(R.id.rbFound);
        rbShoppingCart = (RadioButton) findViewById(R.id.rbShoppingCart);
        rbMe = (RadioButton) findViewById(R.id.rbMe);

        loadProperties();

        radioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 设置当前选中哪个页面
     *
     * @param itemId
     */
    public void setCurrentPage(int itemId) {
        // radioGroup.check(itemId);
        switch (itemId) {
            case 0:
                rbHome.setChecked(true);
                break;
            case 1:
                rbCategory.setChecked(true);
                break;
            case 2:
                rbFound.setChecked(true);
                break;
            case 3:
                rbShoppingCart.setChecked(true);
                break;
            case 4:
                rbMe.setChecked(true);
                break;
        }
    }


    /**
     * 先读取配置文件，确定显示与否
     */
    private void initConfig() {

        isBottomMenu = getResources().getBoolean(R.bool.is_bottom_menu);
    }

    /**
     * 从bottom.json中读取RadioGroup与RadioButton属性
     */
    private void loadProperties() {
        String json = Utils.readJsonFileFromAsset(this, "bottom.json");
        if (TextUtils.isEmpty(json))
            throw new JsonParseException("json 解析异常");

        MenuModel menuModel = GsonUtils.jsonToBean(json, MenuModel.class);

        //是否开启底部导航
        if (isBottomMenu) {
            List<MenuItemModel> menuItemModelList = menuModel.getItems();
            //获取文字两种状态的颜色
            String textNormalColor = menuModel.getTextNormalColor();
            String textPressColor = menuModel.getTextPressColor();

            ColorStateList textColorStateList = Utils.createColorStateList(Color.parseColor(textNormalColor),
                    Color.parseColor(textPressColor));

            String itemNormalColor = menuModel.getItemNormalColor();

            radioGroup.setBackgroundColor(Color.parseColor(itemNormalColor));

            for (int i = 0; i < menuItemModelList.size(); i++) {
                //获得每个item的两种状态下的图片
                MenuItemModel item = menuItemModelList.get(i);
                StateListDrawable topIconStateDrawable =
                        Utils.generatorDrawableState(this, item.getIconNormal(),
                                item.getIconPress());
                topIconStateDrawable.setBounds(0, 0, topIconStateDrawable.getMinimumWidth(),
                        topIconStateDrawable.getMinimumHeight());
                switch (i) {
                    case 0:
                        rbHome.setText(item.getName());
                        rbHome.setTextColor(textColorStateList);
                        rbHome.setCompoundDrawables(null, topIconStateDrawable, null, null);
                        break;
                    case 1:
                        rbCategory.setText(item.getName());
                        rbCategory.setTextColor(textColorStateList);
                        rbCategory.setCompoundDrawables(null, topIconStateDrawable, null, null);
                        break;
                    case 2:
                        rbFound.setText(item.getName());
                        rbFound.setTextColor(textColorStateList);
                        rbFound.setCompoundDrawables(null, topIconStateDrawable, null, null);
                        break;
                    case 3:
                        rbShoppingCart.setText(item.getName());
                        rbShoppingCart.setTextColor(textColorStateList);
                        rbShoppingCart.setCompoundDrawables(null, topIconStateDrawable, null, null);
                        break;
                    case 4:
                        rbMe.setText(item.getName());
                        rbMe.setTextColor(textColorStateList);
                        rbMe.setCompoundDrawables(null, topIconStateDrawable, null, null);
                        break;
                }
            }

        } else {
            radioGroup.setVisibility(View.GONE);
        }

    }


    /**
     * 从back stack弹出所有的fragment，保留首页的那个
     */
    public static boolean popAllFragments() {
        for (int i = 0; i < fMgr.getBackStackEntryCount(); i++) {
            fMgr.popBackStack();
        }
        return true;
    }

    // 点击返回按钮
    @Override
    public void onBackPressed() {
        if (fMgr.findFragmentByTag("homeFragment") != null
                && !fMgr.findFragmentByTag("homeFragment").isResumed()) {
            rbHome.toggle();
        } else {
            // super.onBackPressed();
//            AppManager.getAppManager().finishAllActivity();
            this.finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbHome)
            rc.isRefresh(false);
        else
            rc.isRefresh(true);
        FragmentTransaction ft;
        switch (checkedId) {
            case R.id.rbHome:
                ConfigValue.iconFlag = 0;
                CartDrawable cartDrawableH = new CartDrawable(this, ConfigValue.iconFlag, 0);
                cartDrawableH.setCatNum(goodsNum);
                rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawableH, null, null);
                ft = fMgr.beginTransaction();
                if (fMgr.findFragmentByTag("homeFragment") != null) {
                    ft.replace(R.id.fragmentRoot,
                            fMgr.findFragmentByTag("homeFragment"), "homeFragment");
                    ft.commitAllowingStateLoss();
                } else {
                    HomeFragment taskFragment = new HomeFragment();
                    ft.replace(R.id.fragmentRoot, taskFragment, "homeFragment");
                    ft.addToBackStack("homeFragment");
                    ft.commit();
                }
                break;
            case R.id.rbCategory:
                ConfigValue.iconFlag = 0;
                CartDrawable cartDrawableC = new CartDrawable(this, ConfigValue.iconFlag, 0);
                cartDrawableC.setCatNum(goodsNum);
                rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawableC, null, null);
                ft = fMgr.beginTransaction();
                if (fMgr.findFragmentByTag("categoryFragment") != null) {
                    ft.replace(R.id.fragmentRoot,
                            fMgr.findFragmentByTag("categoryFragment"),
                            "categoryFragment");
                    ft.commitAllowingStateLoss();
                } else {
                    CategoryFragment categoryFragment = new CategoryFragment();
                    ft.replace(R.id.fragmentRoot, categoryFragment,
                            "categoryFragment");
                    ft.addToBackStack("categoryFragment");
                    ft.commit();
                }
                break;
            case R.id.rbFound:
                ConfigValue.iconFlag = 0;
                CartDrawable cartDrawableF = new CartDrawable(this, ConfigValue.iconFlag, 0);
                cartDrawableF.setCatNum(goodsNum);
                rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawableF, null, null);

                ft = fMgr.beginTransaction();
                if (fMgr.findFragmentByTag("foundFragment") != null) {
                    ft.replace(R.id.fragmentRoot,
                            fMgr.findFragmentByTag("foundFragment"),
                            "foundFragment");
                    ft.commitAllowingStateLoss();
                } else {
                    FoundFragment foundFragment = new FoundFragment();
                    ft.replace(R.id.fragmentRoot, foundFragment,
                            "foundFragment");
                    ft.addToBackStack("foundFragment");
                    ft.commit();
                }
                break;

            case R.id.rbShoppingCart:
                ConfigValue.iconFlag = 1;
                CartDrawable cartDrawable = new CartDrawable(this, ConfigValue.iconFlag, 0);
                cartDrawable.setCatNum(goodsNum);
                rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable, null, null);
                ft = fMgr.beginTransaction();
                if (fMgr.findFragmentByTag("shopCartFragment") != null) {
                    ft.replace(R.id.fragmentRoot,
                            fMgr.findFragmentByTag("shopCartFragment"),
                            "shopCartFragment");
                    ft.commitAllowingStateLoss();
                } else {
                    ShopCartFragment shopCartFragment = new ShopCartFragment();
                    ft.replace(R.id.fragmentRoot, shopCartFragment,
                            "shopCartFragment");
                    ft.addToBackStack("shopCartFragment");
                    ft.commit();
                }
                break;

            case R.id.rbMe:
                ConfigValue.iconFlag = 0;
                CartDrawable cartDrawableM = new CartDrawable(this, ConfigValue.iconFlag, 0);
                cartDrawableM.setCatNum(goodsNum);
                rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawableM, null, null);
                ft = fMgr.beginTransaction();
                if (fMgr.findFragmentByTag("personalFragment") != null) {
                    ft.replace(R.id.fragmentRoot,
                            fMgr.findFragmentByTag("personalFragment"), "personalFragment");
                    ft.commitAllowingStateLoss();
                } else {
                    PersonalFragment personalFragment = new PersonalFragment();
                    ft.replace(R.id.fragmentRoot, personalFragment, "personalFragment");
                    ft.addToBackStack("meFragment");
                    ft.commit();
                }
//                OkHttpClientManager.postAsyn(getApplicationContext(), "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/user.gson", null, new MyResultCallback(), true, "1");
                break;
            default:
                break;
        }

    }

    private RefreshCallback rc;


    public interface RefreshCallback {
        void isRefresh(boolean flag);
    }

    public void setRefresh(RefreshCallback rc) {
        this.rc = rc;
    }


    public class MyResultCallback extends BaseDelegate.ResultCallback<User> {

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            setTitle("loading...");
        }

        @Override
        public void onAfter() {
            super.onAfter();
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Request request, Object tag, Exception e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onResponse(User u, Object tag) {
            // TODO Auto-generated method stub
            Log.d("test", u.password + "");
            Log.d("test", tag + "");
        }
    }

    public Handler handler = new Handler();
    //此处是注册的回调处理
    //参考集成文档的1.7.10
    //http://dev.umeng.com/push/android/integration#1_7_10
    public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {

        @Override
        public void onRegistered(String registrationId) {
            // TODO Auto-generated method stub
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Log.e("token", mPushAgent.getRegistrationId());
                    // TODO Auto-generated method stub
                    if (mPushAgent.isRegistered()) {
                        //成功
                    }
                }
            });
        }
    };

    class AddAliasTask extends AsyncTask<Void, Void, Boolean> {

        String alias;
        String aliasType;

        public AddAliasTask(String aliasString, String aliasTypeString) {
            // TODO Auto-generated constructor stub
            this.alias = aliasString;
            this.aliasType = aliasTypeString;
        }

        protected Boolean doInBackground(Void... params) {
            try {
                return mPushAgent.addAlias(alias, aliasType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (Boolean.TRUE.equals(result)) {

            }

        }

    }

    /*//获取个人信息
    @Override
    public void onPersonalInfo(PersonalModel response) {
        if (response.getCode().equals("1"))
            this.setCartGoodsNums(response.getData().getNumber());
    }

    //修改购物车角标回调
    @Override
    public void setCartGoodsNums(String nums) {
        goodsNum = nums;
        CartDrawable cartDrawable = new CartDrawable(this, ConfigValue.iconFlag);
        cartDrawable.setCatNum(nums);
        rbShoppingCart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable, null, null);
    }
*/
    //角标增加，用于在商品详情页增加商品至购物车时调用


    /*@Override
    public void addCartGoodsNums(int nums) {
        int now = Integer.parseInt(goodsNum) + nums;
        goodsNum = String.valueOf(now);
        CartDrawable cartDrawable = (CartDrawable) rbShoppingCart.getCompoundDrawables()[1];
        cartDrawable.addNums(nums);
        cartDrawable.start();
    }*/

    //注册广播接收商品详情页修改购物车数量之后发送的广播

    /*private BroadcastReceiver mBroadcastReseiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConfigValue.ACTION_ALTER_CARTGOODS_NUMS)) {
                int addNum = intent.getIntExtra("addGoodsNums", 0);
                String total = intent.getStringExtra("cartgoodsnum");
                int homeFlag = intent.getIntExtra("home_home", 0);
                if (addNum != 0)
                    addCartGoodsNums(addNum);
                if (null != total && !total.equals(""))
                    setCartGoodsNums(total);
                if (homeFlag != 0)
                    if (fMgr.findFragmentByTag("homeFragment") != null
                            && !fMgr.findFragmentByTag("homeFragment").isResumed()) {
                        rbHome.toggle();
                    }
            }
        }
    };*/

    /*public void registerBroadcastReceiver() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConfigValue.ACTION_ALTER_CARTGOODS_NUMS);
        //注册广播
        registerReceiver(mBroadcastReseiver, mIntentFilter);
    }*/


}
