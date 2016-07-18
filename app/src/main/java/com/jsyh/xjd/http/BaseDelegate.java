package com.jsyh.xjd.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseDelegate {
    protected Handler mDelivery;
    private Gson mGson;
    protected OkHttpClient mOkHttpClient;

    public BaseDelegate(OkHttpClient mOkHttpClient) {
        mGson = new Gson();
        mDelivery = new Handler(Looper.getMainLooper());
        this.mOkHttpClient = mOkHttpClient;
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public void onBefore(Request request) {
        }

        public void onAfter() {
        }

        public abstract void onError(Request request, Object tag, Exception e);

        public abstract void onResponse(T response, Object tag);
    }

    private final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(Request request, Object tag, Exception e) {

        }

        @Override
        public void onResponse(String response, Object tag) {

        }
    };

    protected void deliveryResult(Context context, ResultCallback callback, Request request) {
        if (callback == null)
            callback = DEFAULT_RESULT_CALLBACK;
        final ResultCallback resCallBack = callback;
        // UI thread
        callback.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, resCallBack);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
                    Logger.json(string);
                    Log.d("RES", string);

                    if (resCallBack.mType == String.class) {
                        sendSuccessResultCallback(response.request(), string, resCallBack);
                    } else {
                        Object o = mGson.fromJson(string, resCallBack.mType);
                        sendSuccessResultCallback(response.request(), o, resCallBack);
                    }

                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, resCallBack);
                } catch (com.google.gson.JsonParseException e)// Json解析的错误
                {
                    Log.d("RES", e.getMessage() + "------" + e.toString());
                    sendFailedStringCallback(response.request(), e, resCallBack);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(request, request.tag(), e);
                callback.onAfter();
            }
        });
    }

    private void sendSuccessResultCallback(final Request request, final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object, request.tag());
                callback.onAfter();
            }
        });
    }
}
