package com.jsyh.xjd.http;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @ClassName: OkHttpClientManager网络请求类 * @author yaodingding
 * @date 2015-9-22 下午1:56:46
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private PostDelegate mPostDelegate = null;
    private GetDelegate mGetDelegate = null;

    private OkHttpClientManager(Context context) {
        mOkHttpClient = new OkHttpClient();
        mPostDelegate = new PostDelegate(mOkHttpClient);
        mGetDelegate = new GetDelegate(mOkHttpClient);
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        mOkHttpClient.setCache(cache);
        // cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        /* just for test !!! */
        mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

    }

    public static OkHttpClientManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager(context);
                }
            }
        }
        return mInstance;
    }

    public PostDelegate _getPostDelegate() {
        return mPostDelegate;
    }

    private GetDelegate _getGetDelegate() {
        return mGetDelegate;
    }

    public static GetDelegate getGetDelegate(Context context) {
        return getInstance(context)._getGetDelegate();
    }

    public static PostDelegate getPostDelegate(Context context) {
        return getInstance(context)._getPostDelegate();
    }

    /**
     * ============Get方便的访问方式============
     */
    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, false, null);
    }

    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, forceNetWork, null);
    }

    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback, boolean forceNetWork, Object tag) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, forceNetWork, tag);
    }

    /**
     * ============POST方便的访问方式===============
     */
    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback) {
        String requestUrl = url + "?";
        String p = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            p += entry.getKey() + "=" + entry.getValue() + "&";
        }
        requestUrl = requestUrl + p.substring(0, p.length() - 1);

        Log.d("POSTURL", requestUrl);

        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, null);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        String requestUrl = url + "?";
        String p = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            p += entry.getKey() + "=" + entry.getValue() + "&";
        }
        requestUrl = requestUrl + p.substring(0, p.length() - 1);

        Log.d("POSTURL", requestUrl);

        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, forceNetWork);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, Object tag) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, tag);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, boolean forceNetWork, Object tag) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, forceNetWork, tag);
    }


    public static void cancelTag(Context context, Object tag) {
        getInstance(context)._cancelTag(tag);
    }

    private void _cancelTag(Object tag) {
        mOkHttpClient.cancel(tag);
    }

    public static OkHttpClient getClinet(Context context) {
        return getInstance(context).client();
    }

    private OkHttpClient client() {
        return mOkHttpClient;
    }

}
