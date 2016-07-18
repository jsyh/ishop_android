package com.jsyh.xjd.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jsyh.xjd.R;
import com.jsyh.xjd.utils.Utils;

import java.io.File;

/**
 * 原生套网页页面
 * Created by Su on 2016/5/31.
 */
public class WebActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private String title;
    private String url;
    private WebView webView;

    @Override
    public void initData() {
        super.initData();
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_web_page);
        webView = (WebView) findViewById(R.id.webView);
        initWebView();
    }

    @Override
    public void initSwipeRefreshLayout() {
        super.initSwipeRefreshLayout();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initTitle() {

        ((TextView) findViewById(R.id.title)).setText(title);
        findViewById(R.id.fl_Left).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_Left:
                Utils.finishActivityWithAnimation(this);
                break;
        }
    }

    private void initWebView() {
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
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {

                if (url.contains("brand.php?")) {
                    //品牌跳转
                    String[] list = url.split("id\\=");
                    Intent intent = new Intent(WebActivity.this, GoodsListActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("brand_id", list[1]);
                    intent.putExtras(extras);
                    //context.startActivity(intent);
                    Utils.startActivityWithAnimation(WebActivity.this, intent);
                } else if (url.contains("category.php?")) {
                    //跳转到商品列表
                    String[] list = url.split("id\\=");

                    Intent intent = new Intent(WebActivity.this, GoodsListActivity.class);
                    Bundle extras = new Bundle();
                    //      extras.putString("keyword", keyword);
                    extras.putString("classify_id", list[1]);
                    intent.putExtras(extras);
                    Utils.startActivityWithAnimation(WebActivity.this, intent);
                } else if (url.contains("goods.php?")) {
                    //跳到商品详情页
                    String[] list = url.split("id\\=");
                    Intent intent = new Intent(WebActivity.this, GoodsInfoActivity.class);
                    intent.putExtra("goodsId", list[1]);
                    Utils.startActivityWithAnimation(WebActivity.this, intent);
                } else if (url.contains("exchange.php?") && url.contains("act=view")) {
                    //积分兑换
                    String[] list = url.split("id\\=");
                    String id = list[1].split("\\&")[0];
                    Intent intent = new Intent(WebActivity.this, GoodsInfoExchangeActivity.class);
                    intent.putExtra("goodsId", id);
                    Utils.startActivityWithAnimation(WebActivity.this, intent);
                    //Toast.makeText(WebActivity.this, id, Toast.LENGTH_SHORT).show();
                } else
                    webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //webView.reload();
                webView.loadUrl(url);
            }
        }, 600);
    }
}
