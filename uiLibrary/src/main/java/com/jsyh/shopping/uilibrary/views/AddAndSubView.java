package com.jsyh.shopping.uilibrary.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jsyh.shopping.uilibrary.R;

public class AddAndSubView extends FrameLayout implements OnClickListener {
    Context context;
    private OnNumChangeListener onNumChangeListener;
    private TextView txt_sub;
    private TextView txt_add;
    private EditText edit_num;

    public AddAndSubView(Context context) {
        super(context);
    }

    public AddAndSubView(Context context, int num) {
        super(context);
    }

    public AddAndSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_and_sub_edittext, this);
        txt_add = (TextView) view.findViewById(R.id.txt_add);
        edit_num = (EditText) view.findViewById(R.id.edit_num);
        edit_num.addTextChangedListener(new OnTextChangeListener());
        txt_sub = (TextView) view.findViewById(R.id.txt_sub);
        txt_add.setOnClickListener(this);
        txt_sub.setOnClickListener(this);

    }

    //设置空间是否可用
    public void setEnable(boolean enable) {
        txt_add.setClickable(enable);
        txt_sub.setClickable(enable);
        edit_num.setEnabled(enable);
    }


    /**
     * 设置editText中的值
     *
     * @param num
     */
    public void setNum(int num) {
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
     *
     * @param onNumChangeListener
     */
    public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
        this.onNumChangeListener = onNumChangeListener;
    }

    /**
     * EditText输入变化事件监听器
     *
     * @author ZJJ
     */
    class OnTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            /*if (s != null)
                onNumChangeListener.onNumChange(AddAndSubView.this, Integer.parseInt(s.toString()));*/
        }
    }

    public interface OnNumChangeListener {
        /**
         * 输入框中的数值改变事件
         *
         * @param view 整个AddAndSubView
         * @param num  输入框的数值
         */
        void onNumChange(View view, int num);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String txt = edit_num.getText().toString().trim();
        int num;
        if (TextUtils.isEmpty(txt))
            num = 1;
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
    }

}
