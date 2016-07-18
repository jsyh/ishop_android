package com.jsyh.shopping.uilibrary.popuwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jsyh.shopping.uilibrary.R;

/**
 * Created by Su on 2016/4/21.
 */
public class GoodsListOrderPopupWindow extends PopupWindow {

    private Context mContext;
    private View mView;
    private int order;
    private String name;

    public GoodsListOrderPopupWindow(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.goods_list_pop_order, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(mView);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    //点击监听
    public void setOnItemClickListener(final OnItemClickListener listener, RadioButton radioButton) {

        /**
         * 此处问题，如果用radioGroup监听，如果选中一个之后再次点击该radioButton，不出发onCheckedChanged方法
         */

        /*RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.mRadioGroup);

        //radioGroup.getCheckedRadioButtonId();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mRadioButton1) {
                    name = "综合排序";
                    order = 0;

                } else if (checkedId == R.id.mRadioButton2) {
                    name = "价格从低到高";
                    order = 2;

                } else if (checkedId == R.id.mRadioButton3) {
                    name = "价格从高到低";
                    order = 3;

                } else if (checkedId == R.id.mRadioButton4) {
                    name = "信用排序";
                    order = 4;

                }
                dismiss();
                listener.onClick(order, name);
            }
        });*/

        //判断如果综合排序没有被选中，则下拉选项全部未选中状态

        if (!radioButton.isChecked()) {
            RadioGroup radioGroup = (RadioGroup) mView.findViewById(R.id.mRadioGroup);
            radioGroup.clearCheck();
        }

        mView.findViewById(R.id.mRadioButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "综合排序";
                order = 0;
                dismiss();
                listener.onClick(order, name);
            }
        });

        mView.findViewById(R.id.mRadioButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "价格从低到高";
                order = 2;
                dismiss();
                listener.onClick(order, name);
            }
        });
        mView.findViewById(R.id.mRadioButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "价格从高到低";
                order = 3;
                dismiss();
                listener.onClick(order, name);
            }
        });
        mView.findViewById(R.id.mRadioButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "信用排序";
                order = 4;
                dismiss();
                listener.onClick(order, name);
            }
        });

    }

    //点击radioGroup回调接口，返回点击的是哪个RadioButton

    public interface OnItemClickListener {
        void onClick(int orderType, String name);
    }

}
