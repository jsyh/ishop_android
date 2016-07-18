package com.jsyh.shopping.uilibrary.popuwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.jsyh.shopping.uilibrary.R;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 筛选弹出框
 * Created by Su on 2016/4/21.
 */
public class GoodsListOptionPopupWindow extends PopupWindow implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private View optionsView;
    private Context mContext;
    //筛选条件,设置默认值
    //private String price_range = "0.0-0.0";

    private String oldMinPrice = "0.0";
    private String oldMaxPrice = "0.0";

    private String is_fare = "";//免运费
    private String is_promotion = "";//促销
    private String p_delivery = "";//货到付款
    private String classify = null;//筛选id

    private Map<String, String> filtrate;//筛选条件

    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private EditText mEditTextMin;
    private EditText mEditTextMax;

    private OnCallBack mOnCallBack;

    public GoodsListOptionPopupWindow(Context context, View view) {
        super(context);
        this.optionsView = view;
        this.mContext = context;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(optionsView);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        initFiltratePage();
    }

    //初始化筛选界面
    public void initFiltratePage() {

        mEditTextMin = (EditText) optionsView.findViewById(R.id.mEditTextMin);
        mEditTextMax = (EditText) optionsView.findViewById(R.id.mEditTextMax);
        if (!TextUtils.isEmpty(mEditTextMin.getText()))
            oldMinPrice = mEditTextMin.getText().toString();
        if (!TextUtils.isEmpty(mEditTextMax.getText()))
            oldMaxPrice = mEditTextMax.getText().toString();


        mCheckBox1 = (CheckBox) optionsView.findViewById(R.id.mCheckBox1);
        mCheckBox2 = (CheckBox) optionsView.findViewById(R.id.mCheckBox2);
        mCheckBox3 = (CheckBox) optionsView.findViewById(R.id.mCheckBox3);

        mCheckBox1.setOnCheckedChangeListener(this);
        mCheckBox2.setOnCheckedChangeListener(this);
        mCheckBox3.setOnCheckedChangeListener(this);

        RecyclerView mRecyclerViewCategory = (RecyclerView) optionsView.findViewById(R.id.mRecyclerViewCategory);

        Button mButtonClear = (Button) optionsView.findViewById(R.id.mButtonClear);
        mButtonClear.setOnClickListener(this);

        Button mButtonConfirm = (Button) optionsView.findViewById(R.id.mButtonConfirm);
        mButtonConfirm.setOnClickListener(this);

        //String[] price = filtrateModel.getData().getPrice_range().split("\\-");
        filtrate = new HashMap<>();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.equals(mCheckBox1)) {
            if (isChecked)
                is_fare = "1";
            else is_fare = "";
        } else if (buttonView.equals(mCheckBox2)) {
            if (isChecked)
                is_promotion = "1";
            else is_promotion = "";
        } else if (buttonView.equals(mCheckBox3)) {
            if (isChecked)
                p_delivery = "1";
            else p_delivery = "";
        }

        // ToastUtil.showToast(mContext, is_fare + "//////////////////" + is_promotion + "////////////////////" + p_delivery);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.mButtonClear) {
            mCheckBox1.setChecked(false);
            mCheckBox2.setChecked(false);
            mCheckBox3.setChecked(false);
            mEditTextMin.setText(oldMinPrice);
            mEditTextMax.setText(oldMaxPrice);
            mOnCallBack.onClear();
        } else if (i == R.id.mButtonConfirm) {
            String newMinPrice, newMaxPrice;
            if (!TextUtils.isEmpty(mEditTextMin.getText())) {
                newMinPrice = mEditTextMin.getText().toString();
                if (Double.parseDouble(newMinPrice) < Double.parseDouble(oldMinPrice)) {
                    ToastUtil.showToast(mContext, "最低价格小于当前列表最低价格！请重新输入！");
                    return;
                }
            } else {
                ToastUtil.showToast(mContext, "最低价格不能为空!");
                return;
            }
            if (!TextUtils.isEmpty(mEditTextMax.getText())) {
                newMaxPrice = mEditTextMax.getText().toString();
                if (Double.parseDouble(newMaxPrice) > Double.parseDouble(oldMaxPrice)) {
                    ToastUtil.showToast(mContext, "最高价格大于当前列表最高价格！请重新输入！");
                    return;
                }
            } else {
                ToastUtil.showToast(mContext, "最高价格不能为空!");
                return;
            }

            filtrate.put("price_range", newMinPrice + "-" + newMaxPrice);
            filtrate.put("is_fare", is_fare + "");
            filtrate.put("is_promotion", is_promotion + "");
            filtrate.put("p_delivery", p_delivery + "");
            mOnCallBack.onConfirm(filtrate);

        }
    }

    //设置点击监听
    public void setOnClearConfirmClickListener(OnCallBack onCallBack) {
        this.mOnCallBack = onCallBack;
    }


    public interface OnCallBack {
        void onClear();

        void onConfirm(Map<String, String> filtrateMap);
    }
}
