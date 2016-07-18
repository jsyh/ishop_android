package com.jsyh.shopping.uilibrary.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jsyh.shopping.uilibrary.R;

public class ShopCartAddAndSubView extends FrameLayout implements OnClickListener {
    Context context;
    private OnNumChangeListener onNumChangeListener;
    private TextView txt_sub;
    private TextView txt_add;
    public EditText edit_num;

    public ShopCartAddAndSubView(Context context) {
        super(context);
    }

    public ShopCartAddAndSubView(Context context, int num) {
        super(context);
    }

    public ShopCartAddAndSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.shop_cart_add_sub, this);
        txt_add = (TextView) view.findViewById(R.id.txt_add);
        edit_num = (EditText) view.findViewById(R.id.edit_num);
        edit_num.setText("0");
        txt_sub = (TextView) view.findViewById(R.id.txt_sub);
        txt_add.setOnClickListener(this);
        txt_sub.setOnClickListener(this);

    }

    /**
     * 设置editText中的值
     *
     * @param num
     */
    public void setNum(int num) {
        edit_num.setText(num + "");
    }


    /**
     * 获取editText中的值
     *
     * @return
     */
    public int getNum() {
        String txt = edit_num.getText().toString().trim();
        int num;
        if (TextUtils.isEmpty(txt))
            num = 0;
        else num = Integer.parseInt(txt);
        return num;
    }

    /**
     * 设置EditText文本变化监听
     */
    public void setOnNumChangeListener(OnNumChangeListener numChangeListener) {
        this.onNumChangeListener = numChangeListener;
    }

    public interface OnNumChangeListener {
        /**
         * 输入框中的数值改变事件
         *
         * @param num 输入框的数值
         */
        void onNumChange(int num);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String txt = edit_num.getText().toString().trim();
        int num;
        if (TextUtils.isEmpty(txt))
            num = 0;
        else num = Integer.parseInt(txt);
        if (v.getId() == R.id.txt_add) {
            num++;
            edit_num.setText(num + "");

        } else if (v.getId() == R.id.txt_sub) {
            num--;
            if (num < 1) {
                num = 1;
            }
            edit_num.setText(num + "");
        }

        onNumChangeListener.onNumChange(num);
    }

}
