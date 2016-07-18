package com.jsyh.xjd.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.GoodsInfoActivity;
import com.jsyh.xjd.activity.GoodsListActivity;
import com.jsyh.xjd.activity.MainActivity;
import com.jsyh.xjd.activity.SearchActivity;
import com.jsyh.xjd.activity.WebActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.qrzxing.CaptureActivity;
import com.jsyh.xjd.umeng.share.Share;
import com.jsyh.xjd.utils.Utils;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import java.io.File;


public class HomeFragment extends BaseFragment implements MainActivity.RefreshCallback {
    private PullRefreshLayout pullRefreshLayout;
    private WebView webView;
    private LinearLayout lineLayTitle;
    //	private static final String url = "http://shopapi.99-k.com/index.php/goods_index";
    private float lastY;

    //是否有顶部导航栏
    private boolean isTitleBar;

    private Share mShare;
    private MainActivity mainActivity;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(ConfigValue.DESCRIPTOR);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "创建TaskFragment");

        mShare = new Share(getActivity());
        //addWXPlatform();
    }


    private View rootView;


    private int refreshTag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
//		return inflater.inflate(R.layout.fragment_home, container, false);

        isTitleBar = getActivity().getResources().getBoolean(R.bool.is_nav);

        return rootView;
    }

    @Override
    protected void initTitle() {
        //super.initTitle();
        //	title.setText(R.string.string_Home);
        lineLayTitle = (LinearLayout) getView().findViewById(R.id.lineLayTitle);
        lineLayTitle.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.home_nav_color));
        getView().findViewById(R.id.txtScanning).setOnClickListener(this);
        getView().findViewById(R.id.lineLaySearch).setOnClickListener(this);
        getView().findViewById(R.id.txtShare).setOnClickListener(this);
        //getView().findViewById(R.id.txtMessage).setOnClickListener(this);
        if (!isTitleBar)
            lineLayTitle.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        if (refreshTag == 0)
            initViewUrl();
    }

    @SuppressLint("JavascriptInterface")
    protected void initViewUrl() {
        refreshTag++;
        mainActivity.setRefresh(this);

        webView = (WebView) getView().findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        if (Utils.NO_NETWORK_STATE == Utils.isNetworkAvailable(getContext())) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        } else
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置缓存模式
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);

        File externalStorageDir = Environment.getExternalStorageDirectory();

        String cacheDirPath = externalStorageDir.getAbsolutePath() +
                File.separator + "xiaojd" + File.separator + "webViewCache";
        // 设置数据库缓存路径
        webView.getSettings().setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        webView.getSettings().setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(ConfigValue.MAIN_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
                    //分类
                    //Utils.showToast(context, "跳到分类");
                    ((MainActivity) getActivity()).setCurrentPage(1);
                } else if (url.contains("stores.php")) {
                    //店铺街
                    ((MainActivity) getActivity()).setCurrentPage(2);
                } else if (url.contains("flow.php")) {
                    //购物车
                    ((MainActivity) getActivity()).setCurrentPage(3);
                } else if (url.contains("user.php")) {
                    //个人中心
                    ((MainActivity) getActivity()).setCurrentPage(4);
                } else if (url.contains("goods.php?")) {
                    //跳到商品详情页
                    String[] list = url.split("id\\=");
                    Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                    intent.putExtra("goodsId", list[1]);
                    Utils.startActivityWithAnimation(context, intent);

                } else if (url.contains("search.php?")) {
                    //跳转到类别，热门，推荐等
                    String[] list = url.split("intro\\=");
                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra("goods_type", list[1]);
                    Utils.startActivityWithAnimation(context, intent);
                } else if (url.contains("brand.php?")) {
                    String[] list = url.split("id\\=");
                    Intent intent = new Intent(context, GoodsListActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("brand_id", list[1]);
                    intent.putExtras(extras);
                    //context.startActivity(intent);
                    Utils.startActivityWithAnimation(context, intent);
                } else if (url.contains("brand.php")) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("title", "品牌街");
                    intent.putExtra("url", url);
                    Utils.startActivityWithAnimation(context, intent);
                } else if (url.contains("activity.php")) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("title", "优惠活动");
                    intent.putExtra("url", url);
                    Utils.startActivityWithAnimation(context, intent);
                } else if (url.contains("pro_search.php")) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("title", "团购");
                    intent.putExtra("url", url);
                    Utils.startActivityWithAnimation(context, intent);
                } else if (url.contains("exchange.php")) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("title", "积分商城");
                    intent.putExtra("url", url);
                    Utils.startActivityWithAnimation(context, intent);
                } else {
                    webView.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (isTitleBar && lineLayTitle.getVisibility() == View.GONE)
                    lineLayTitle.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }
        });
        //webViewScroolChangeListener();
        pullRefreshLayout = (PullRefreshLayout) getView().findViewById(R.id.pullRefreshLayout);
        pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_Bitmap);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //webView.loadUrl(url);
                        webView.reload();
                        pullRefreshLayout.setRefreshing(false);
                    }
                }, 800);
            }

            @Override
            public void onMove(boolean ismove) {
                if (isTitleBar && !ismove)
                    lineLayTitle.setVisibility(View.VISIBLE);
            }
        });
        pullRefreshLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        lastY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        /*if (isTitleBar) {

                            if ((motionEvent.getY() - lastY) > 0)
                                lineLayTitle.setVisibility(View.GONE);
                            else
                                lineLayTitle.setVisibility(View.VISIBLE);
                        }*/
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.txtScanning:
                Intent itCapture = new Intent(getActivity(), CaptureActivity.class);
                startActivity(itCapture);
                break;
            case R.id.lineLaySearch:
                Intent itSear = new Intent(getActivity(), SearchActivity.class);
                //startActivity(itSear);
                Utils.startActivityWithAnimation(context, itSear);
                break;
            case R.id.txtShare:
                // new Share(getActivity(), "小京东", "小京东购物APP", "http://appup.99-k.com/ecshopxjd/index.html", "http://demo2.ishopv.com/mapp/data/afficheimg/1395294388718461791.jpg");
                //share.addWXPlatform();
                mShare.setContent("小京东", "小京东购物APP", "http://appup.99-k.com/ecshopxjd/index.html", "http://demo2.ishopv.com/mapp/data/afficheimg/1395294388718461791.jpg");
                mController.openShare(getActivity(), false);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void isRefresh(boolean flag) {
        if (flag)
            pullRefreshLayout.setDrawable(flag);
    }
}