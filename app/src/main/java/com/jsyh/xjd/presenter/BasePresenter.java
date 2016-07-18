package com.jsyh.xjd.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.jsyh.xjd.R;
import com.jsyh.xjd.utils.DigestUtils;
import com.jsyh.xjd.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang on 2015/9/21.
 */
public abstract class BasePresenter {


    protected ProgressDialog    mLoadingDialog;

    private static final String densityKey = "99-k";


    protected void initLoadDialog(@NonNull Context context) {

        mLoadingDialog = new ProgressDialog(context);
//        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setCanceledOnTouchOutside(false);

        mLoadingDialog.setMessage(context.getResources().getString(R.string.data_loding));

    }

    /**
     *  MD5的Token
     * @return
     *          <p>model+action+时间戳+key做md5加密传递到服务器99-k ymd</p>
     */
    @CheckResult
    protected String getMD5OfToken(String model , String action) {

        if (TextUtils.isEmpty(model) || TextUtils.isEmpty(action)) {
            throw new IllegalArgumentException("paramter exception");
        }

        StringBuffer md5String = new StringBuffer();

        md5String.append(model);
        md5String.append(action);

        String timeTemplater = TimeUtils.getCurrentTimeInString(new SimpleDateFormat("yyyyMMdd"));

        md5String.append(timeTemplater);

        md5String.append(densityKey);

        try {
            return  DigestUtils.md5(md5String.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 返回一个带有 token 的 MAP
     * @param model
     * @param action
     * @return
     */
    @CheckResult
    protected Map<String ,String> getDefaultMD5Params(@NonNull String model , @NonNull String action){

        Map<String, String> params = new HashMap<>();
        try {
            String token = getMD5OfToken(model, action);
            if (TextUtils.isEmpty(token)) return  null;

            params.put("api_token", token);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 关闭数据加载框
     */
    protected void dismiss(){

        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }








}
