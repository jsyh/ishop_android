package com.jsyh.xjd.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jsyh.shopping.uilibrary.drawable.CartDrawable;
import com.jsyh.shopping.uilibrary.popuwindow.QRCodePopupwindow;
import com.jsyh.shopping.uilibrary.views.AddAndSubView;
import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.CreateOrderActivity;
import com.jsyh.xjd.activity.me.LoginActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.fragment.DetailFragment;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.GoodsInfoModel2;
import com.jsyh.xjd.presenter.DetailPresenter;
import com.jsyh.xjd.qrzxing.CreateQRCode;
import com.jsyh.xjd.umeng.share.Share;
import com.jsyh.xjd.utils.SPUtils;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.GoodDetatileView;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bleu.widget.slidedetails.SlideDetailsLayout;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener, GoodDetatileView, AddAndSubView.OnNumChangeListener {

    private CoordinatorLayout mClLayout;

    //网络错误用到的view
    private ViewStub mNetworkStub;
    private View mNetworkErrorLayout;
    private Button mReloadRequest;
    //显示内容的布局
    private RelativeLayout mRlContentView;

    private View footView;

    //头部，滚动图片
    private InfiniteIndicatorLayout mIilLayout;
    //下部，基本信息
    private TextView mTvGoodsName;
    private TextView mTvGoodsPrice;

    private TextView mTvGoodsAttr;
    private LinearLayout mLlShare;
    //按键
    private Button mBtnAddCart;
    private TextView mTvConfirmAttr;

    private Button mBtnBuy;


    private LinearLayout mLayoutCollect;
    private LinearLayout mLayoutService;
    private LinearLayout mLayoutShop;
    private TextView mTextViewCollect;
    private CheckBox mCheckBoxCollect;
    //购物车
    private CartDrawable cartDrawable;

    //详细页面
    private DetailFragment detailFragment;
    //分享及二维码弹出框初始化
    private QRCodePopupwindow qrCodePopupwindow;
    //商品详情presenter
    private DetailPresenter mPresenter;
    //商品信息
    private GoodsInfoModel2 mGoodsInfo;
    private String goodsSelectedAttr;
    //商品属性
    private BottomSheetDialog dialog;
    private List<GoodsInfoModel2.Attribute> attributes;                                          //商品所有的属性
    private List<GoodsInfoModel2.AttributeNum> attributeNumList;                                  //记录商品库存集合
    public Map<String, GoodsInfoModel2.Attribute.Attr_key> selectAttrs = new HashMap<>();           // 选择的属性集合

    public Map<String, GoodsInfoModel2.Attribute.Attr_key> selectAttrsTemp = new HashMap<>();         // 临时选择的属性集合

    private ImageView mIvGoodsImgAttr;                                                              //属性界面图片
    private TextView mTvGoodsPriceAttr;
    private TextView mTvGoodsLeftAttr;
    private TextView mTvGoodsSelectedAttr;

    private LinearLayout mLlAttr;
    private AddAndSubView mGoodsNums;


    private String selectedAttrStr = "";
    private String selectedAttrPrice = "";
    private String selectedAttrId = "[]";
    private String selectedAttrIdTemp = "[]";
    private String goodsLeft;                                                                   //商品剩余库存
    private int selectNums = 1;//默认选择一件

    //商品详情页，顶部导航栏随着上滑改变颜色透明度

    private LinearLayout mLayoutTitle;
    private ImageButton mIbtnBackBlack;
    private ImageButton mIbtnBackWhite;
    private ImageButton mIbtnCartBlack;
    private ImageButton mIbtnCartWhite;

    private int nextFlag = -1;//点击选择属性确定键之后的操作，-1不进行任何操作，0加入购物车，1立即购买

    //分享弹框
    private BottomSheetDialog shareDialog;

    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(ConfigValue.DESCRIPTOR);

    private Share mShare;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_goodsinfo);
        mClLayout = (CoordinatorLayout) findViewById(R.id.goodsInfoLayout);
        //网络状态决定页面显示情况
        mNetworkStub = (ViewStub) findViewById(com.jsyh.xjd.R.id.vsNetworkError);
        mNetworkErrorLayout = mNetworkStub.inflate();
        mReloadRequest = (Button) mNetworkErrorLayout.findViewById(com.jsyh.xjd.R.id.btnReloadNetwork);
        mReloadRequest.setOnClickListener(this);
        mNetworkErrorLayout.setVisibility(View.GONE);

        initTitle();
        initContent();

        //详细信息页面
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        detailFragment = new DetailFragment();
        fragmentTransaction.add(R.id.detailFrame, detailFragment, "detailFragment").commit();
        //二维码弹出框
        qrCodePopupwindow = new QRCodePopupwindow(this);

        //属性页面

        initAttributedPage();
        //初始化购物车
        /*setCartGoodsNums("");*/
    }

    private void initContent() {
        mRlContentView = (RelativeLayout) findViewById(R.id.rlContentView);
        ListView mLtContent = (ListView) findViewById(R.id.list_baseContent);
        //headerView = LayoutInflater.from(this).inflate(R.layout.detail_header_layout, null);
        footView = LayoutInflater.from(this).inflate(R.layout.goodinfo_foot_view, null);
        //滚动图片
        mIilLayout = (InfiniteIndicatorLayout) footView.findViewById(R.id.indicatorLayout);
        //商品基本信息
        mTvGoodsName = (TextView) footView.findViewById(R.id.goodsinfo_name);
        mTvGoodsPrice = (TextView) footView.findViewById(R.id.goodsinfo_price);


        mTvGoodsAttr = (TextView) footView.findViewById(R.id.goodsinfo_attr);
        mTvGoodsAttr.setOnClickListener(this);
        mLlShare = (LinearLayout) footView.findViewById(R.id.detail_share);
        mLlShare.setOnClickListener(this);

        //mLtContent.addHeaderView(headerView);
        mLtContent.addFooterView(footView);
        mLtContent.setAdapter(null);
        //ListView监听
        onListScrollListener(mLtContent);
        //按键
        mBtnAddCart = (Button) findViewById(R.id.btn_addCart);
        mBtnAddCart.setOnClickListener(this);
        mBtnBuy = (Button) findViewById(R.id.btn_buy);
        mBtnBuy.setOnClickListener(this);

        mLayoutCollect = (LinearLayout) findViewById(R.id.txt_collection);
        mLayoutCollect.setOnClickListener(this);
        mCheckBoxCollect = (CheckBox) findViewById(R.id.mCheckBoxCollect);
        mTextViewCollect = (TextView) findViewById(R.id.mTextViewCollection);
        mLayoutService = (LinearLayout) findViewById(R.id.txt_service);
        mLayoutService.setOnClickListener(this);
        mLayoutShop = (LinearLayout) findViewById(R.id.txt_shop);
        mLayoutShop.setOnClickListener(this);
    }

    //ListView滑动监听
    private void onListScrollListener(ListView listView) {
        final SlideDetailsLayout container = (SlideDetailsLayout) findViewById(R.id.Fl_content);
        //final FrameLayout detailFrame = (FrameLayout) findViewById(R.id.detailFrame);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("List", footView.getY() + "------" + footView.getTranslationY());
                float y = Math.abs(footView.getY());
                y = y > 400 ? 400 : y;
                // Log.d("List2", y + "-------" + container.getY() + "-------------" + detailFrame.getY() + "---------" + detailFrame.getTop());
                //Log.d("List2", mLayoutTitle.getHeight()+"");
                if (y <= 200) {
                    mIbtnBackBlack.setAlpha(0.0f);
                    mIbtnCartBlack.setAlpha(0.0f);
                    mIbtnBackWhite.setAlpha(1 - y / 200);
                    mIbtnCartWhite.setAlpha(1 - y / 200);
                } else {
                    mIbtnBackBlack.setAlpha(y / 200 - 1);
                    mIbtnCartBlack.setAlpha(y / 200 - 1);
                    mIbtnBackWhite.setAlpha(0.0f);
                    mIbtnCartWhite.setAlpha(0.0f);
                }
                mLayoutTitle.setAlpha(y / 400);
            }
        });

    }

    private void initAttributedPage() {
        dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_select_attribute, null);
        mIvGoodsImgAttr = (ImageView) view.findViewById(R.id.goodsImage);
        mTvGoodsPriceAttr = (TextView) view.findViewById(R.id.textPrice);
        view.findViewById(R.id.mImageViewClose).setOnClickListener(this);
        mTvGoodsLeftAttr = (TextView) view.findViewById(R.id.goodsLeft);
        mTvGoodsSelectedAttr = (TextView) view.findViewById(R.id.mTextViewSelected);

        mTvConfirmAttr = (TextView) view.findViewById(R.id.mTextViewConfirm);

        mTvConfirmAttr.setOnClickListener(this);
        mLlAttr = (LinearLayout) view.findViewById(R.id.layout_Attribute);
        mGoodsNums = (AddAndSubView) view.findViewById(com.jsyh.shopping.uilibrary.R.id.goodsNum);
        mGoodsNums.setOnNumChangeListener(this);
        dialog.setContentView(view);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                     @Override
                                     public void onShow(DialogInterface dialog) {
                                         BottomSheetDialog d = (BottomSheetDialog) dialog;

                                         FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
                                         BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                                     }
                                 }

        );
    }

    @Override
    public void initTitle() {
        View mTitleView = findViewById(R.id.detail_title_bar);
        mLayoutTitle = (LinearLayout) mTitleView.findViewById(R.id.mLayoutTitle);
        mIbtnBackBlack = (ImageButton) mTitleView.findViewById(R.id.mIbBackBlack);
        mIbtnBackWhite = (ImageButton) mTitleView.findViewById(R.id.mIbBackWhite);
        mIbtnCartBlack = (ImageButton) mTitleView.findViewById(R.id.mIbCartBlack);
        mIbtnCartWhite = (ImageButton) mTitleView.findViewById(R.id.mIbCartWhite);
        mIbtnBackWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.finishActivityWithAnimation(GoodsInfoActivity.this);
            }
        });
        mIbtnCartWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.online(GoodsInfoActivity.this)) {
                    Intent intentCart = new Intent(GoodsInfoActivity.this, ShoppingCartActivity.class);
                    Utils.startActivityWithAnimation(GoodsInfoActivity.this, intentCart);
                } else Utils.toLogin(GoodsInfoActivity.this);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        mPresenter = new DetailPresenter(mContext);
        if (Utils.NO_NETWORK_STATE != Utils.isNetworkAvailable(this)) {
            mPresenter.LoadDetatileData(getGoodsId(),0);
        }

    }


    /**
     * 获取传递的 商品 ID
     *
     * @return
     */
    @CheckResult
    private String getGoodsId() {

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            throw new IllegalArgumentException("必须传递数据");
        }

        String goodsId = extras.getString("goodsId");
        if (TextUtils.isEmpty(goodsId)) {
            throw new NullPointerException();
        }

        return goodsId;
    }

    @Override
    public void onClick(View v) {
        if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(this)) {
            netWorkError();
            Utils.showToast(this, getResources().getString(R.string.network_errot_toast));
            return;
        } else {
            netWorkNormal();
            switch (v.getId()) {
                case R.id.goodsinfo_attr:
                    if (!dialog.isShowing()) {
                        nextFlag = -1;
                        dialog.show();
                        // onAttributedSelected();
                    }
                    break;
                //分享按钮
                case R.id.detail_share:
                    //shareSelectPopupwindow.showAtLocation(mClLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    shareSelectDialog();
                    break;

                //收藏按钮
                case R.id.txt_collection:
                    onGoodsCollected();
                    break;
                case R.id.txt_service:
                    onCallService(mGoodsInfo.data.shop.servicephone);
                    break;
                case R.id.txt_shop:
                    //ToastUtil.showToast(this, mGoodsInfo.data.shop.shop_url);
                    Intent shopIntent = new Intent(this, ShopActivity.class);
                    shopIntent.putExtra("url", mGoodsInfo.data.shop.shop_url);
                    shopIntent.putExtra("attention", mGoodsInfo.data.shop.attention);
                    shopIntent.putExtra("id", mGoodsInfo.data.shop.supplier_id);
                    Utils.startActivityWithAnimation(this, shopIntent);
                    break;
                case R.id.btn_addCart:
                    onAddGoodsToCart();
                    break;
                case R.id.mTextViewConfirm:
                    //onAddGoodsToCart();
                    onSelectedConfirm();
                    break;
                case R.id.btn_buy:
                    onBuyNow();
                    break;

                //从这里掉友盟的分享
                case R.id.txtFriend:
                    //分享的标题
                    //分享的内容
                    //跳转的url
                    //分享时显示的图片
                    shareDialog.dismiss();
                    //new Share(this, mGoodsInfo.data.goods.goods_name, mGoodsInfo.data.goods.goods_name, "http://appup.99-k.com/ecshopxjd/index.html", mGoodsInfo.data.goods.album[0]);
                    mController.openShare(this, false);
                    break;
                //二维码图片
                case R.id.txtQRCode:
                    shareDialog.dismiss();
                    qrCodePopupwindow.showAtLocation(mClLayout, Gravity.CENTER, 0, 0);
                    //这里传入商品的id  格式：goods_id:id
                    Bitmap bitmap = CreateQRCode.getQRCode(this, "goods_id:" + getGoodsId());
                    qrCodePopupwindow.getCode(bitmap);
                    break;
                case R.id.btnReloadNetwork:
                    mPresenter.LoadDetatileData(getGoodsId(),0);
                    onResume();
                    break;
                //全部评论
                case R.id.mTextViewAllComments:
                    //ToastUtil.showToast(this, "全部评论");
                    Intent intent = new Intent(this, GoodsCommentsActivity.class);
                    /*Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("comments",mGoodsInfo.data.comments.comment);
                    intent.putExtra("comments", bundle);*/
                    intent.putExtra("comments", mGoodsInfo.data.comments);
                    Utils.startActivityWithAnimation(this, intent);
                    break;
                case R.id.mTextViewContact:
                    //ToastUtil.showToast(this, mGoodsInfo.data.shop.servicephone + "");
                    onCallService(mGoodsInfo.data.shop.servicephone);
                    break;
                case R.id.mTextViewGoIn:
                    //ToastUtil.showToast(this, mGoodsInfo.data.shop.shop_url);
                    Intent shopIntent1 = new Intent(this, ShopActivity.class);
                    shopIntent1.putExtra("url", mGoodsInfo.data.shop.shop_url);
                    shopIntent1.putExtra("attention", mGoodsInfo.data.shop.attention);
                    shopIntent1.putExtra("id", mGoodsInfo.data.shop.supplier_id);
                    Utils.startActivityWithAnimation(this, shopIntent1);
                    break;
                case R.id.mImageViewClose:
                    if (dialog.isShowing())
                        dialog.dismiss();
                    break;

            }
        }
    }

    private void shareSelectDialog() {
        shareDialog = new BottomSheetDialog(this);
        View convertView = LayoutInflater.from(this).inflate(R.layout.share_popupwindow, mClLayout, false);
        shareDialog.setContentView(convertView);
        shareDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                          @Override
                                          public void onShow(DialogInterface dialog) {
                                              BottomSheetDialog d = (BottomSheetDialog) dialog;

                                              FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
                                              BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                                          }
                                      }

        );
        shareDialog.show();
        convertView.findViewById(R.id.txtFriend).setOnClickListener(this);
        convertView.findViewById(R.id.txtQRCode).setOnClickListener(this);
    }

    public void onCallService(final String phoneNum) {
        if (phoneNum == null || phoneNum.equals("")) {
            ToastUtil.showToast(this, "号码不存在！");
        } else {
            Utils.showOfficialDialog(this, "提示", "呼叫" + phoneNum + "？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                    if (ActivityCompat.checkSelfPermission(GoodsInfoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }

    }

    //选择属性页面点击确定
    private void onSelectedConfirm() {
        selectNums = mGoodsNums.getNum();
        selectAttrs.clear();
        selectAttrs.putAll(selectAttrsTemp);
        getSelectedAttr(selectAttrs);
        selectedAttrId = selectedAttrIdTemp;
        mTvGoodsPrice.setText(Utils.getStyledText(this, selectedAttrPrice), TextView.BufferType.SPANNABLE);
        mTvGoodsAttr.setText(selectedAttrStr);
        if (canBuyAddCart()) {
            switch (nextFlag) {
                case 0:
                    onAddGoodsToCart();
                    break;
                case 1:
                    onBuyNow();
                    break;
            }
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }

    private void onBuyNow() {
        if (!SPUtils.get(this, "key", "").equals("")) {
            if (canBuyAddCart()) {
                Intent intent = new Intent(this, CreateOrderActivity.class);
                Log.d("goodsidnumber", getGoodsId() + "-" + mGoodsNums.getNum());
                intent.putExtra("goods_id", getGoodsId());
                intent.putExtra("goods_number", selectNums + "");
                intent.putExtra("goods_attr", selectedAttrId);
                //intent.putExtra("intentType", "2");
                intent.putExtra("type", 2);
                Utils.startActivityWithAnimation(this, intent);
            } else {
                if (!dialog.isShowing()) {
                    nextFlag = 1;
                    dialog.show();
                    // onAttributedSelected();
                }
            }

        } else {
            Intent itLogin = new Intent(this, LoginActivity.class);
            Utils.startActivityWithAnimation(this, itLogin);
        }
    }

    //判断能否购买与加入购物车

    public boolean canBuyAddCart() {
        if (selectNums > 0) {
            if (selectNums <= Integer.parseInt(goodsLeft)) {
                if (attributes == null || (attributes != null && attributes.size() == 0) ||
                        (attributes != null && attributes.size() > 0 && selectAttrs.size() == attributes.size())) {
                    return true;
                } else {
                    for (int i = 0; i < attributes.size(); i++) {
                        boolean flag = false;
                        for (Map.Entry<String, GoodsInfoModel2.Attribute.Attr_key> entry : selectAttrs.entrySet()) {
                            if (attributes.get(i).attr_key.contains(entry.getValue())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            Utils.showToast(this, "请选择" + attributes.get(i).attr_name);
                            return false;
                        }
                    }
                    return false;
                }

            } else {
                ToastUtil.showToast(this, "库存不足！");
                return false;
            }
        } else {
            //ToastUtil.showToast(this, "请填写商品数量！");
            return false;
        }
    }

    private void onGoodsCollected() {
        if (Utils.NO_NETWORK_STATE != Utils.isNetworkAvailable(this)) {
            if (!SPUtils.get(this, "key", "").equals("") && ConfigValue.uInfor != null) {
                if (!mCheckBoxCollect.isChecked())
                    mPresenter.addCollect(getGoodsId(), 0);
                else
                    mPresenter.cancelCollect(getGoodsId(), 0);
            } else {
                Intent itLogin = new Intent(this, LoginActivity.class);
                Utils.startActivityWithAnimation(this, itLogin);
            }
        } else {
            netWorkError();
        }
    }


    //加入购物车
    public void onAddGoodsToCart() {
        if (Utils.online(this)) {
            if (canBuyAddCart()) {
                boolean res;
                res = mPresenter.addShoppingCar(String.valueOf(mGoodsNums.getNum()), getGoodsId(), selectedAttrId);
            } else {
                if (!dialog.isShowing()) {
                    nextFlag = 0;
                    dialog.show();
                    // onAttributedSelected();
                }
            }
        } else {
            Utils.toLogin(this);
        }
    }

    //切换布局，网络错误状态
    public void netWorkError() {
        mNetworkErrorLayout.setVisibility(View.VISIBLE);
        mRlContentView.setVisibility(View.GONE);
    }

    //网络正常状态
    public void netWorkNormal() {
        mNetworkErrorLayout.setVisibility(View.GONE);
        mRlContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void albumData(String[] albums) {
        if (albums != null && albums.length > 0) {
            for (String url : albums) {
                DefaultSliderView textSliderView = new DefaultSliderView(mContext);
                textSliderView
                        .image(url)
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                mIilLayout.addSlider(textSliderView);
            }
            mIilLayout.setDirection(5000);
            mIilLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
            mIilLayout.startAutoScroll();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIilLayout.stopAutoScroll();
    }

    @Override
    public void contentData(List<String> content) {

    }

    //初始化属性
    public void initAttributeData(final List<GoodsInfoModel2.Attribute> attributes, final List<GoodsInfoModel2.AttributeNum> attributeNumList) {

        for (int i = 0; i < attributes.size(); i++) {
            View rootView = LayoutInflater.from(this).inflate(com.jsyh.shopping.uilibrary.R.layout.fragment_single_choose, null);
            rootView.setId(10000 + i);
            mLlAttr.addView(rootView);
            TextView tv_attribute = (TextView) rootView.findViewById(com.jsyh.shopping.uilibrary.R.id.tv_attribute);
            tv_attribute.setText(attributes.get(i).attr_name);
            final TagFlowLayout tagFlowLayout_attribute_value = (TagFlowLayout) rootView.findViewById(com.jsyh.shopping.uilibrary.R.id.tagFlowLayout_attribute_value);
            final int finalI = i;
            tagFlowLayout_attribute_value.setAdapter(new TagAdapter<GoodsInfoModel2.Attribute.Attr_key>(attributes.get(i).attr_key) {
                @Override
                public View getView(FlowLayout parent, int position, GoodsInfoModel2.Attribute.Attr_key t) {
                    TextView tv = (TextView) getLayoutInflater().inflate(com.jsyh.shopping.uilibrary.R.layout.select_attribute_item, tagFlowLayout_attribute_value, false);
                    tv.setText(t.attr_value);
                    return tv;
                }
            });
            //final int finalI = i;
            tagFlowLayout_attribute_value.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {

                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    // TODO Auto-generated method stub
                    //选中某个属性之后判断是否已经选择同类属性，是的话删除同类属性加入新选择属性，不是直接加入。同时判断是否选择属性数量等于所欲属性大类数量，确定剩余库存
                    /*selectAttrsTemp.add(attributes.get(finalI).attr_key.get(position));
                    onSelectedAttr(selectAttrsTemp, 0);*/
                    String keyTemp = attributes.get(finalI).attr_key.get(position).goods_attr_id;
                    if (selectAttrsTemp.containsKey(keyTemp)) {
                        selectAttrsTemp.remove(keyTemp);
                        selectAttrsTemp.put(keyTemp, attributes.get(finalI).attr_key.get(position));
                    } else {
                        selectAttrsTemp.put(keyTemp, attributes.get(finalI).attr_key.get(position));
                    }
                    //计算价格与已选
                    getSelectedAttr(selectAttrsTemp);

                    //修改库存量,相等说明属性已经选完
                    if (selectAttrsTemp.size() == attributes.size()) {
                        //先找出所选属性的id集合
                        List<String> selectedAttrIds = new ArrayList<>();
                        for (Map.Entry<String, GoodsInfoModel2.Attribute.Attr_key> entry : selectAttrsTemp.entrySet()) {
                            selectedAttrIds.add(entry.getValue().attr_id);
                        }
                        String goodsLeftTemp = "0";
                        for (int i = 0; i < attributeNumList.size(); i++) {
                            if (attributeNumList.get(i).goods_attr.containsAll(selectedAttrIds)) {
                                goodsLeftTemp = attributeNumList.get(i).number;
                                break;
                            }
                        }
                        goodsLeft = goodsLeftTemp;
                    } else {
                        goodsLeft = mGoodsInfo.data.goods.repertory;
                    }
                    mTvGoodsLeftAttr.setText("剩余库存" + goodsLeft + "件");

                    return true;
                }
            });
        }

    }

    @Override
    public void paramData(String param) {

    }

    //返回数据
    @Override
    public void onLoadGoodsInfoDatas(@Nullable GoodsInfoModel2 datas) {
        if (datas != null) {
            mGoodsInfo = datas;
            //设置滚动图片
            albumData(mGoodsInfo.data.goods.album);
            //设置详细信息页面
            String contentHtml = mGoodsInfo.data.goods.content;
            List<GoodsInfoModel2.Params> paramsList = mGoodsInfo.data.goods.param;
            if (contentHtml != null)
                detailFragment.setDataToFragment(contentHtml, paramsList);
            //设置商品名称
            mTvGoodsName.setText(mGoodsInfo.data.goods.goods_name);
            //设置商品价格
            selectedAttrPrice = mGoodsInfo.data.goods.shop_price;

            selectedAttrStr = "选择 颜色分类";

            mTvGoodsPrice.setText(Utils.getStyledText(this, selectedAttrPrice), TextView.BufferType.SPANNABLE);

            mTvGoodsPriceAttr.setText("￥" + selectedAttrPrice);

            goodsLeft = mGoodsInfo.data.goods.repertory;

            mTvGoodsLeftAttr.setText("剩余库存" + goodsLeft + "件");

            initGoodsCommentsShop();
            //设置商品属性
            attributes = mGoodsInfo.data.goods.attribute;
            attributeNumList = mGoodsInfo.data.goods.attr_number;

            if (attributes != null && attributes.size() > 0)
                initAttributeData(attributes, attributeNumList);
            //mTvGoodsSnAttr.setText("商品编号：");
            Picasso.with(this).load(mGoodsInfo.data.goods.album[0]).error(R.mipmap.goods_detail_shop_photo).into(mIvGoodsImgAttr);

            //设置评论

            if (datas.data.goods.is_attention == 1) {
                mCheckBoxCollect.setChecked(true);
                mTextViewCollect.setText("已关注");
            }
            //注册友盟分享
            mShare = new Share(this);
            mShare.setContent(mGoodsInfo.data.goods.goods_name, mGoodsInfo.data.goods.goods_name, "http://appup.99-k.com/ecshopxjd/index.html", mGoodsInfo.data.goods.album[0]);

        }
    }

    private void initGoodsCommentsShop() {
        //设置商品评价数，销量，地址
        ((TextView) footView.findViewById(R.id.mTextViewCommentsNum)).setText("评价" + mGoodsInfo.data.comments.comment.size() + "条");
        ((TextView) footView.findViewById(R.id.mTextViewSalesNum)).setText("销量" + mGoodsInfo.data.goods.sales + "笔");
        ((TextView) footView.findViewById(R.id.mTextViewAddress)).setText(mGoodsInfo.data.shop.address);

        //显示评论，如果评论数为0，则不显示
        ((TextView) footView.findViewById(R.id.mTextViewComments)).setText("宝贝评价(" + mGoodsInfo.data.comments.comment.size() + ")");

        LinearLayout mLlComments = (LinearLayout) footView.findViewById(R.id.mLinearLayoutComments);
        if (mGoodsInfo.data.comments.comment.size() == 0) {
            mLlComments.setVisibility(View.GONE);
        } else {
            ((TextView) footView.findViewById(R.id.mTextViewBad)).setText("差评" + mGoodsInfo.data.comments.bad);
            ((TextView) footView.findViewById(R.id.mTextViewMedium)).setText("中评" + mGoodsInfo.data.comments.medium);
            ((TextView) footView.findViewById(R.id.mTextViewGood)).setText("好评" + mGoodsInfo.data.comments.good);
            ((TextView) footView.findViewById(R.id.mTextViewUserName)).setText(mGoodsInfo.data.comments.comment.get(0).content_name);
            ((TextView) footView.findViewById(R.id.mTextViewCommentContent)).setText(mGoodsInfo.data.comments.comment.get(0).content);
            ((TextView) footView.findViewById(R.id.mTextViewBuyRecord)).setText(mGoodsInfo.data.comments.comment.get(0).gmjl.replaceAll("\\r\\n", "  "));
            footView.findViewById(R.id.mTextViewAllComments).setOnClickListener(this);
        }

        //判断是否是自营
        LinearLayout mLlShop = (LinearLayout) footView.findViewById(R.id.mLinearLayoutShop);
        if (mGoodsInfo.data.supplierid.equals("0")) {
            //等于0为自营，没有店铺
            mLlShop.setVisibility(View.GONE);
            mLayoutShop.setVisibility(View.GONE);
            mLayoutService.setVisibility(View.GONE);
        } else {
            mLayoutShop.setVisibility(View.VISIBLE);
            mLayoutService.setVisibility(View.VISIBLE);
            mLlShop.setVisibility(View.VISIBLE);
            ImageView mIvShopLogo = (ImageView) footView.findViewById(R.id.mImageViewShopPic);
            Picasso.with(this).load(mGoodsInfo.data.shop.shoplogo).error(R.mipmap.goods_detail_shop_photo).into(mIvShopLogo);
            ((TextView) footView.findViewById(R.id.mTextViewShopName)).setText(mGoodsInfo.data.shop.shopname);
            if (mGoodsInfo.data.shop.rank.equals("1")) {
                ((TextView) footView.findViewById(R.id.mTextViewShopRank)).setText("初级店铺");
            } else if (mGoodsInfo.data.shop.rank.equals("2")) {

                ((TextView) footView.findViewById(R.id.mTextViewShopRank)).setText(mGoodsInfo.data.shop.rank + "中级店铺");
            } else if (mGoodsInfo.data.shop.rank.equals("3")) {
                ((TextView) footView.findViewById(R.id.mTextViewShopRank)).setText(mGoodsInfo.data.shop.rank + "高级店铺");

            }

            ((TextView) footView.findViewById(R.id.mTextViewAllGoodsNum)).setText(mGoodsInfo.data.shop.all_goods);
            ((TextView) footView.findViewById(R.id.mTextViewNewGoodsNum)).setText(mGoodsInfo.data.shop.new_goods);
            ((TextView) footView.findViewById(R.id.mTextViewAttentionNum)).setText(mGoodsInfo.data.shop.getSupplier_sum());
            ((TextView) footView.findViewById(R.id.mTextViewGoodsDes)).setText(mGoodsInfo.data.shop.comments_rank);
            ((TextView) footView.findViewById(R.id.mTextViewShopDes)).setText(mGoodsInfo.data.shop.comments_rank);
            ((TextView) footView.findViewById(R.id.mTextViewLogDes)).setText(mGoodsInfo.data.shop.comments_rank);
            footView.findViewById(R.id.mTextViewContact).setOnClickListener(this);
            footView.findViewById(R.id.mTextViewGoIn).setOnClickListener(this);
        }
    }


    @Override
    public void onAddCarShopping(@Nullable BaseModel data) {
        Utils.showToast(this, data.getMsg());
        if (data != null && data.getCode().equals(ConfigValue.Success_Code)) {
            //加入成功
            //1. 修改 购物车图片 圆点增加
            /*int goodsNums = mGoodsNums.getNum();
            //cartDrawable = (CartDrawable) text_cart.getCompoundDrawables()[1];
            *//*cartDrawable = (CartDrawable) mIBRight.getDrawable();
            cartDrawable.addNums(goodsNums);
            cartDrawable.start();*//*

            Intent mIntent = new Intent(ConfigValue.ACTION_ALTER_CARTGOODS_NUMS);
            mIntent.putExtra("addGoodsNums", goodsNums);
            //发送广播
            sendBroadcast(mIntent);*/
            ConfigValue.DATA_CHANGE_TAG = true;
        }
    }

    //收藏回调
    @Override
    public void onCollectGoods(BaseModel data) {
        Utils.showToast(this, data.getMsg());
        if (data.getCode().equals("1")) {
            mCheckBoxCollect.setChecked(true);
            mTextViewCollect.setText("已关注");
            ConfigValue.DATA_CHANGE_TAG = true;
        }
        //mCheckBoxCollection.setChecked(true);
    }

    //取消商品回调
    @Override
    public void cancelCollectGoods(BaseModel data) {
        Utils.showToast(this, data.getMsg());
        if (data.getCode().equals("1")) {
            mCheckBoxCollect.setChecked(false);
            mTextViewCollect.setText("关注");
            ConfigValue.DATA_CHANGE_TAG = true;
        }
        // mCheckBoxCollection.setChecked(false);
    }

    @Override
    public void getShopCollectStatus(BaseModel baseModel) {

    }


    @Override
    public void error(String msg, Object tag) {

    }

    //已选商品的属性
    public void getSelectedAttr(Map<String, GoodsInfoModel2.Attribute.Attr_key> attrKeys) {
        //所选属性价格之和
        BigDecimal bigDecimal = new BigDecimal(0.00);
        //所选属性id
        List<String> selectedAttrList = new ArrayList<>();

        selectedAttrStr = "";
        selectedAttrPrice = "";
        selectedAttrId = "";
        if (attrKeys.size() > 0) {
            for (Map.Entry<String, GoodsInfoModel2.Attribute.Attr_key> entry : attrKeys.entrySet()) {
                selectedAttrStr += entry.getValue().attr_value + ",";

                bigDecimal = bigDecimal.add(new BigDecimal(entry.getValue().attr_price));

                selectedAttrList.add(entry.getValue().attr_id);
            }
            //属性的价格之和加上商品原价
            bigDecimal = bigDecimal.add(new BigDecimal(mGoodsInfo.data.goods.shop_price));
            //商品属性的名称字符串，用于前台显示
            selectedAttrStr = "已选：" + mGoodsNums.getNum() + "件 " + selectedAttrStr.substring(0, selectedAttrStr.length() - 1);

            selectedAttrPrice = bigDecimal.toString();
            //属性集合字符串
            selectedAttrIdTemp = selectedAttrList.toString();
        } else {
            selectedAttrStr = "已选：" + mGoodsNums.getNum() + "件 ";
            selectedAttrPrice = (new BigDecimal(mGoodsInfo.data.goods.shop_price)).toString();
            //属性集合字符串
            selectedAttrIdTemp = selectedAttrList.toString();
        }

        mTvGoodsPriceAttr.setText("￥" + selectedAttrPrice);
        mTvGoodsSelectedAttr.setText(selectedAttrStr);
    }

    @Override
    public void onNumChange(View view, int num) {
        getSelectedAttr(selectAttrsTemp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(this)) {
            netWorkError();
        } else {
            netWorkNormal();
            /*PersonalPresenter mPersonalPresenter = new PersonalPresenter(this);
            mPersonalPresenter.getPersonalInfo(this);*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /*//返回个人信息（购物车中商品数量）
    @Override
    public void onPersonalInfo(PersonalModel response) {
        setCartGoodsNums(response.getData().getNumber());
    }

    @Override
    public void setCartGoodsNums(String nums) {
        CartDrawable cartDrawable = new CartDrawable(this, 0);
        cartDrawable.setCatNum(nums);
        *//*text_cart.setCompoundDrawablesWithIntrinsicBounds(null, cartDrawable, null, null);*//*
        mIBRight.setImageDrawable(cartDrawable);
    }

    @Override
    public void addCartGoodsNums(int nums) {

    }*/
}

