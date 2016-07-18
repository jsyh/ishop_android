package com.jsyh.xjd.presenter;

import android.content.Context;
import android.util.Log;

import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.xjd.http.BaseDelegate;
import com.jsyh.xjd.http.ExceptionHelper;
import com.jsyh.xjd.http.OkHttpClientManager;
import com.jsyh.xjd.model.HotSearchModel;
import com.jsyh.xjd.views.SearchView;
import com.squareup.okhttp.Request;

import java.util.Map;

/**
 * 搜索  layer
 */
public class SearchPresenter extends BasePresenter{



    private SearchView mSearchView;

    public SearchPresenter(SearchView mSearchView) {
        this.mSearchView = mSearchView;
    }

    public void loadHotsTag(final Context context) {
        initLoadDialog(context);
        mLoadingDialog.show();
        Map<String, String> params = getDefaultMD5Params("first", "keywords");

        if (params == null) return;

        OkHttpClientManager.postAsyn(context, ConfigValue.HOT_SEARCH, params, new BaseDelegate.ResultCallback<HotSearchModel>() {
            @Override
            public void onError(Request request, Object tag, Exception e) {
                ExceptionHelper.getMessage(e, context);
                mLoadingDialog.dismiss();
            }

            @Override
            public void onResponse(HotSearchModel response, Object tag) {
                if (response != null) {
                    mSearchView.onHostTag(response.getData());
                    mLoadingDialog.dismiss();
                }
            }
        });

    }





    /**
     * 加载历史搜索
     */
    /*public void loadHistory(Context context) {


        try {

            String o = (String) SPUtils.get(context, SPConfig.SEARCH_HISTORY_KEY, ObjectSerializer.serialize(new ArrayList<String>()));
            ArrayList<String> datas = (ArrayList<String>) ObjectSerializer.deserialize(o);
            mSearchView.onHistory(datas);


        } catch (IOException e) {
            mSearchView.onHistory(null);
            e.printStackTrace();
        }

    }*/


    public void loadAutoComplete() {

        Log.d("tag", "load  data .....");


    }





}
