package com.jsyh.xjd.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;

import com.jsyh.xjd.utils.Utils;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

//====================PostDelegate=======================
public class PostDelegate extends BaseDelegate {
	public PostDelegate(OkHttpClient mOkHttpClient) {
		super(mOkHttpClient);
	}

	private Request buildPostFormRequest(Context context, String url, Map<String, String> params, boolean forceNetWork, Object tag) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		FormEncodingBuilder builder = null;
		if (params.size() > 0) {
			builder = new FormEncodingBuilder();
		}
		Set<Map.Entry<String, String>> entries = params.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			builder.add(entry.getKey(), entry.getValue());
		}
		Request.Builder reqBuilder = null;
		if (forceNetWork) {// 强制走网络 对于特殊请求
			reqBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK).url(url);
		} else if (Utils.isNetworkAvailable(context) == 0) {// 没有网络则强制请求缓存中数据
			reqBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_CACHE).url(url);
		} else {// 否则走网络
			reqBuilder = new Request.Builder().url(url);
		}
		if (builder != null) {
			RequestBody requestBody = builder.build();
			reqBuilder.post(requestBody);
		}
		if (tag != null) {
			reqBuilder.tag(tag);
		}
		return reqBuilder.build();
	}

	/**
	 * 同步的Post请求
	 */
	public Response post(Context context, String url, Map<String, String> params) throws IOException {
		return post(context, url, params, false, null);
	}

	public Response post(Context context, String url, Map<String, String> params, boolean forceNetWork) throws IOException {
		return post(context, url, params, false, null);
	}

	public Response post(Context context, String url, Map<String, String> params, boolean forceNetWork, Object tag) throws IOException {
		Request request = buildPostFormRequest(context, url, params, forceNetWork, tag);
		Response response = mOkHttpClient.newCall(request).execute();
		return response;
	}

	/**
	 * 异步的Post请求
	 */
	public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback) {
		postAsyn(context, url, params, callback, false, null);
	}

	public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback, boolean forceNetWork) {
		postAsyn(context, url, params, callback, forceNetWork, null);
	}

	public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback, Object tag) {
		postAsyn(context, url, params, callback, false, null);
	}

	public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback, boolean forceNetWork, Object tag) {
		Request request = buildPostFormRequest(context, url, params, forceNetWork, tag);
		deliveryResult(context, callback, request);
	}

}
