package com.jsyh.xjd.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.LoginActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.model.BaseModel;
import com.jsyh.xjd.model.GoodsInfoModel2;
import com.jsyh.xjd.presenter.DetailPresenter;
import com.jsyh.xjd.utils.Utils;
import com.jsyh.xjd.views.GoodDetatileView;

import java.io.File;
import java.util.List;

/**
 * Created by Su on 2016/4/6.
 */
public class ShopActivity extends BaseActivity implements View.OnClickListener, GoodDetatileView {
    private WebView webView;
    private String url, attention = "0", id;
    private TextView mTextViewCollect;
    private boolean collectFlag = false;

    private DetailPresenter detailPresenter;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_shop);
        webView = (WebView) findViewById(R.id.webView);
        //获取传过来的url地址
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        //attention = intent.getStringExtra("attention");
        id = intent.getStringExtra("id");
        //获取店铺关注状态
        detailPresenter = new DetailPresenter(this);
        detailPresenter.getShopCollectStatus(id);

        initWebView();
    }

    //根据店铺id获取是否已经关注


    private void initWebView() {
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(this)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        } else
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置缓存模式
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);

        File externalStorageDir = Environment.getExternalStorageDirectory();

        String cacheDirPath = externalStorageDir.getAbsolutePath() +
                File.separator + "xiaojd" + File.separator + "shopWebViewCache";
        // 设置数据库缓存路径
        webView.getSettings().setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        webView.getSettings().setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("catalog.php")) {
                    // Utils.showToast(ShopActivity.this, "跳到分类");
                } else if (url.contains("flow.php")) {
                    // Utils.showToast(ShopActivity.this, "跳到购物车");
                } else if (url.contains("user.php")) {
                    //Utils.showToast(ShopActivity.this, "跳到个人中心");
                } else if (url.contains("goods.php?")) {
                    //跳到商品详情页
                    String[] list = url.split("id\\=");
                    Intent intent = new Intent(ShopActivity.this, GoodsInfoActivity.class);
                    intent.putExtra("goodsId", list[1]);
                    Utils.startActivityWithAnimation(ShopActivity.this, intent);

                } else if (url.contains("search.php?")) {
                    //跳转到类别，热门，推荐等
                    String[] list = url.split("intro\\=");
                    Intent intent = new Intent(ShopActivity.this, GoodsListActivity.class);
                    intent.putExtra("type", list[1]);
                    Utils.startActivityWithAnimation(ShopActivity.this, intent);
                } else if (url.contains("mobile")) {
                    Intent intent = new Intent(ShopActivity.this, MainActivity.class);
                    intent.putExtra("intentType",100);
                    Utils.startActivityWithAnimation(ShopActivity.this, intent);
                } else if (url.contains("tel:")) {
                    final String phoneNum = url.substring(3);

                    Utils.showOfficialDialog(ShopActivity.this, "提示", "呼叫" + phoneNum + "？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                            if (ActivityCompat.checkSelfPermission(ShopActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                } else {
                    webView.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    public void initTitle() {
        super.initTitle();
        findViewById(R.id.fl_Left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.finishActivityWithAnimation(ShopActivity.this);
            }
        });
        TextView mTextViewTitle = (TextView) findViewById(R.id.title);
        mTextViewTitle.setText("店铺");
        mTextViewCollect = (TextView) findViewById(R.id.ensure);
        mTextViewCollect.setText("收藏");
        if (attention.equals("1")) {
            collectFlag = true;
            mTextViewCollect.setText("已收藏");
        }

        findViewById(R.id.fl_right).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (Utils.online(this)) {
            if (collectFlag) {
                detailPresenter.cancelCollect(id, 1);
            } else detailPresenter.addCollect(id, 1);
        } else {
            Utils.startActivityWithAnimation(this, new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onCollectGoods(BaseModel data) {
        Utils.showToast(this, data.getMsg());
        if (data.getCode().equals("1")) {
            // ConfigValue.uInfor.setAttention(Integer.parseInt(ConfigValue.uInfor) + 1 + "");
            collectFlag = true;
            mTextViewCollect.setText("已收藏");
            ConfigValue.DATA_CHANGE_TAG = true;
        }
    }

    @Override
    public void cancelCollectGoods(BaseModel data) {
        Utils.showToast(this, data.getMsg());
        if (data.getCode().equals("1")) {
            collectFlag = false;
            mTextViewCollect.setText("收藏");
            ConfigValue.DATA_CHANGE_TAG = true;
        }
    }

    //获取店铺关注状态
    @Override
    public void getShopCollectStatus(BaseModel baseModel) {
        if (baseModel.getCode().equals("1")) {
            attention = "1";
        } else attention = "0";

        if (attention.equals("1")) {
            collectFlag = true;
            mTextViewCollect.setText("已收藏");
        }
    }

    @Override
    public void error(String msg, Object tag) {

    }

    @Override
    public void albumData(String[] albums) {

    }

    @Override
    public void contentData(List<String> content) {

    }

    @Override
    public void paramData(String param) {

    }

    @Override
    public void onLoadGoodsInfoDatas(@Nullable GoodsInfoModel2 datas) {

    }

    @Override
    public void onAddCarShopping(@Nullable BaseModel data) {

    }

}
