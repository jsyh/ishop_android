package com.jsyh.xjd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.CheckResult;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jsyh.xjd.R;
import com.jsyh.xjd.activity.me.LoginActivity;
import com.jsyh.xjd.config.ConfigValue;
import com.jsyh.shopping.uilibrary.dialog.MyDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sks on 2015/9/16.
 */
public class Utils {


    public static final int NO_NETWORK_STATE = 0;     //无网络
    public static final int WIFI_STATE = 1;     //wifi


    /*
     * 取得应用的版本号,就是哪个版本,
     */
    public static String longVersionName(Context context) {
        String mVersion = null;
        if (mVersion == null) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                mVersion = pi.versionName;

            } catch (NameNotFoundException e) {
                mVersion = ""; // failed, ignored
            }
        }
        return mVersion;
    }

    /*
     * 取得应用的版本号,就是修改次.
     */
    public static int longVersionCode(Context context) {
        int mVersionCode = 0;
        if (mVersionCode == 0) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                mVersionCode = pi.versionCode;

            } catch (NameNotFoundException e) {
                mVersionCode = 0; // failed, ignored
            }
        }
        return mVersionCode;
    }

    //使用Toast
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //客户端认证加密字符串
    public static String AppMD5String(Context context, String model, String action) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            date = df.parse(df.format(new Date()));
        } catch (ParseException e) {
        }
        ;
        long time = date.getTime();
//        long timecurrentTimeMillis = System.currentTimeMillis();
        return md5(model + action + time + "99-k");
    }

    //字符串md5加密
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale - 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //时间戳转时间
    public static String time(Context context, long s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(s * 1000));
        return date;
    }

    private static MyDialog myDialog;

    //启动自定义dialog
    public static void showDialog(Activity context, String title, String content, String leftText, String rightText,
                                  View.OnClickListener onClickListener) {
        myDialog = new MyDialog(context, R.style.my_dialog, title, content, leftText, rightText, onClickListener);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    //启动官方自带dialog
    public static void showOfficialDialog(Activity context, String title, String content,
                                          DialogInterface.OnClickListener mPositiveListener,
                                          DialogInterface.OnClickListener mNegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(content).setPositiveButton("确定", mPositiveListener).setNegativeButton("取消", mNegativeListener);
        builder.show();
    }

    //销毁dialog
    public static void dismissDialog() {
        if (myDialog.isShowing())
            myDialog.dismiss();
    }

    public static String readJsonFileFromAsset(Context context, String name) {

        if (TextUtils.isEmpty(name)) return null;

        String data = null;

        try {
            InputStream is = context.getAssets().open(name);

            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();

            data = new String(bytes, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return data;

    }


    /**
     * 对TextView设置不同状态时其文字颜色。
     */
    @CheckResult
    public static ColorStateList createColorStateList(int normal, int checked) {

        int[] colors = new int[]{
                checked,
                checked,
                checked,
                normal};
        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_checked};
        states[3] = new int[]{};

        ColorStateList colorList = new ColorStateList(states, colors);

        return colorList;
    }

    /**
     * 根据图片的名字，反射取得id
     *
     * @param context
     * @param norIcon
     * @param checkedIcon
     * @return
     */
    public static StateListDrawable generatorDrawableState(Context context, String norIcon, String checkedIcon) {

        StateListDrawable stateListDrawable = new StateListDrawable();

        // TODO: 2015/11/30 zheli 可能会有异常的情况，如果找不到相应的图片的时候Caused by: android.content.res.Resources$NotFoundException: Resource ID #0x0
        Drawable defDrawable = ContextCompat.getDrawable(context, ResourcesUtil.getMipmapId(context, norIcon));
        Drawable checkDrawable = ContextCompat.getDrawable(context, ResourcesUtil.getMipmapId(context, checkedIcon));

        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkDrawable);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, checkDrawable);

        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, checkDrawable);

        stateListDrawable.addState(new int[]{}, defDrawable);

        return stateListDrawable;
    }

    public static StateListDrawable generatorDrawableState(int normalColor, int pressColor) {

        StateListDrawable stateListDrawable = new StateListDrawable();


        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(pressColor));
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(pressColor));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressColor));
        stateListDrawable.addState(new int[]{}, new ColorDrawable(normalColor));


        return stateListDrawable;
    }


    //时间戳转时间
    public static String time2(Context context, long s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(s * 1000));
        return date;
    }

    //activity切换动画
    public static void setLeftOutRightInAnimation(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public static void setRightOutLeftInAnimation(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    //设置价格不同大小字体
    public static SpannableString getStyledText(Context context, String text) {

        if (text != null && !text.equals("")) {
            String newText = "￥" + text;
            SpannableString styledText = new SpannableString(newText);
            String[] parts;
            if (newText.contains(".")) {
                parts = newText.split("\\.");
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style0), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style1), 1, parts[0].length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style0), parts[0].length() + 1, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style0), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style1), 1, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return styledText;
        }
        Utils.showToast(context, "价格不能为空！！！！！");
        return null;
    }

    //设置价格不同大小字体
    public static SpannableString getStyledTextGoodsList(Context context, String text) {

        if (text != null && !text.equals("")) {
            String newText = "￥" + text;
            SpannableString styledText = new SpannableString(newText);
            String[] parts;
            if (newText.contains(".")) {
                parts = newText.split("\\.");
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style3), 1, parts[0].length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style2), parts[0].length() + 1, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style3), 1, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return styledText;
        }
        Utils.showToast(context, "价格不能为空！！！！！");
        return null;
    }

    //设置购物车商品价格不同大小字体
    public static SpannableString getStyledTextCartGoodsPrice(Context context, String text) {

        if (text != null && !text.equals("")) {
            String newText = "￥" + text;
            SpannableString styledText = new SpannableString(newText);
            String[] parts;
            if (newText.contains(".")) {
                parts = newText.split("\\.");
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style5), 1, parts[0].length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style4), parts[0].length() + 1, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                styledText.setSpan(new TextAppearanceSpan(context, R.style.style5), 1, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return styledText;
        }
        Utils.showToast(context, "价格不能为空！！！！！");
        return null;
    }
    //带动画的跳转activity
    public static void startActivityWithAnimation(Context context, Intent intent) {
        context.startActivity(intent);
       // setLeftOutRightInAnimation(context);
    }

    public static void finishActivityWithAnimation(Context context) {
        ((Activity) context).finish();
       // setRightOutLeftInAnimation(context);
    }
    //关闭软键盘

    public static void closeSoftInputKeybord(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputMethodManager.isActive())
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
    }

    //是否已经登录
    public static boolean online(Context context) {
        if (!SPUtils.get(context, "key", "").equals("") && ConfigValue.uInfor != null) {
            return true;
        } else {
            return false;
        }
    }

    //跳到登录页
    public static void toLogin(Context mContext) {
        Utils.startActivityWithAnimation(mContext, new Intent(mContext, LoginActivity.class));
    }
}
