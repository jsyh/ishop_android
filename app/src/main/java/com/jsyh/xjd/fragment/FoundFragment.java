package com.jsyh.xjd.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.activity.GoodsListActivity;
import com.jsyh.xjd.activity.ShopActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.utils.Utils;

import java.io.File;

/**
 * Created by Su on 2016/4/23.
 */
public class FoundFragment extends BaseFragment {

    private View mView;

    private WebView webView;

    private String homeUrl;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null)
            mView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_found, container, false);
        return mView;
    }

    @Override
    protected void initView() {
        webView = (WebView) mView.findViewById(R.id.webView);

        initWebView();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.mSwipeRefreshLayout);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);

    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(getActivity())) {
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

        homeUrl = ConfigValue.FOUND_URL;

        webView.loadUrl(homeUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                /*String[] strings = url.split("index.php/");
                if (strings[1].contains("type")){
                    String[] list = strings[1].split("/");
                    Intent intent = new Intent(getActivity(), GoodsFilterActivity1.class);
                    intent.putExtra("type", list[1]);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                    intent.putExtra("goodsId", strings[1]);
                    startActivity(intent);
                }*/
                if (url.contains("catalog.php")) {
                    Utils.showToast(getActivity(), "跳到分类");
                } else if (url.contains("flow.php")) {
                    Utils.showToast(getActivity(), "跳到购物车");
                } else if (url.contains("user.php")) {
                    Utils.showToast(getActivity(), "跳到个人中心");
                } else if (url.contains("goods.php?")) {
                    //跳到商品详情页
                    String[] list = url.split("id\\=");
                    Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                    intent.putExtra("goodsId", list[1]);
                    Utils.startActivityWithAnimation(getActivity(), intent);

                } else if (url.contains("search.php?")) {
                    //跳转到类别，热门，推荐等
                    String[] list = url.split("intro\\=");
                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra("type", list[1]);
                    Utils.startActivityWithAnimation(getActivity(), intent);
                } else if (url.contains("supplier.php?")) {
                    //进入店铺
                    String[] list = url.split("suppId\\=");
                    Intent shopIntent = new Intent(getActivity(), ShopActivity.class);
                    shopIntent.putExtra("url", url);
                    shopIntent.putExtra("attention", "0");
                    shopIntent.putExtra("id", list[1]);
                    Utils.startActivityWithAnimation(getActivity(), shopIntent);
                } else if (url.contains("msgrd?")) {

                    /*Tencent tencent = Tencent.createInstance("1105377286",context.getApplicationContext());
                    ToastUtil.showToast(context,url);*/


                    /*Tencent mTencent = Tencent.createInstance("1105377286", context.getApplicationContext());
                    WPA mWPA = new WPA(context, mTencent.getQQToken());
                    int ret = mWPA.startWPAConversation(getActivity(), "656743657", "");*/
                    String[] list = url.split("uin\\=");
                    String[] list1 = list[1].split("\\&");
                    final String qq = list1[0];
                    Utils.showOfficialDialog(getActivity(), "提示", "是否打开qq与" + qq + "临时对话？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1&src_type=web&web_src=b.qq.com";
                            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(newUrl));
                            if (context.getPackageManager().resolveActivity(in,
                                    PackageManager.MATCH_DEFAULT_ONLY) != null) {
                                context.startActivity(in);
                            } else {
                                Toast.makeText(context, "未检测到您的手机QQ", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, null);

                } else if (url.contains("street_cat1")) {
                    //店铺街分类
                    webView.loadUrl(url);
                    //webView.reload();
                } else if (url.contains("street_cat2")) {
                    webView.loadUrl(url);
                } else if (url.contains("street_cat3")) {
                    webView.loadUrl(url);
                } else if (url.contains("street_cat4")) {
                    webView.loadUrl(url);
                } else if (url.contains("street_cat5")) {
                    webView.loadUrl(url);
                } else if (url.contains("street_cat6")) {
                    webView.loadUrl(url);
                } else if (url.contains("street_cat7")) {
                    webView.loadUrl(url);
                } else if (url.contains("street_cat8")) {
                    webView.loadUrl(url);
                }else webView.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //webView.reload();
                                webView.loadUrl(homeUrl);
                            }
                        }, 600);
                    }
                });

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void initTitle() {
        super.initTitle();
        title.setText("店铺街");
    }
}
