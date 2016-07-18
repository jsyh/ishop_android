
package com.jsyh.shopping.uilibrary.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jsyh.shopping.uilibrary.R;


/**
 * Created by Su on 2015/10/12.
 * 自定义商品数量对话框
 */

public class AlterGoodsNumDialog extends AlertDialog implements View.OnClickListener {
    private Context context;
    private int goodsNumber;
    private int goodsNumberBefore;
    private EditText mEditTextNum;
    private ImageButton mButtonSub;

    //定义回调事件，用于dialog的点击事件

    public interface OnAlertGoodsNumDialogListener {
        //取消按钮
        void cancel();

        //确定按钮
        void confirm(String newNums);
    }

    private OnAlertGoodsNumDialogListener listener;

    public AlterGoodsNumDialog(Context context, int goodsNum, OnAlertGoodsNumDialogListener listener) {
        super(context);
        this.context = context;
        this.goodsNumber = goodsNumberBefore = goodsNum;
        this.listener = listener;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.alter_cancel) {

                listener.cancel();
                AlterGoodsNumDialog.this.dismiss();

            } else if (i == R.id.alter_confirm) {
                String str = mEditTextNum.getText().toString().trim();
                if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "0")) {
                    goodsNumber = Integer.parseInt(str);
                    //修改后的商品数如果与传过来的相同，则不修改
                    if (goodsNumber != goodsNumberBefore) {
                        listener.confirm(goodsNumber + "");

                        AlterGoodsNumDialog.this.dismiss();
                    } else {

                        AlterGoodsNumDialog.this.dismiss();
                    }
                } else
                    Toast.makeText(context, "商品数量不能为空！", Toast.LENGTH_SHORT).show();

            }
        }
    };

    //重新dismiss方法强制关闭软键盘
    @Override
    public void dismiss() {
        InputMethodManager mInputMethodManager;
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alter_goods_number);


/**
 * 继承自AlertDialog，自定义view上的可编辑控件都是默认不弹出软键盘的。是因为在AlertDialog中有个类
 * AlertController设置了mWindow.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
 * WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);隐藏了键盘，所以要去掉这个flags，写法如下，
 * 或者直接继承自Dialog，软键盘正常弹出
 *
 *
 * !!!dialog消失的时候软键盘不消失是什么gui
 */


        AlterGoodsNumDialog.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        mEditTextNum = (EditText) findViewById(R.id.goods_number);
        mEditTextNum.setText(goodsNumber + "");


        mButtonSub = (ImageButton) findViewById(R.id.number_sub);
        ImageButton mButtonAdd = (ImageButton) findViewById(R.id.number_add);

        //布局按钮设置监听,增加减少
        mButtonSub.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);

        //取消，确定
        Button mButtonCancel = (Button) findViewById(R.id.alter_cancel);
        Button mButtonConfirm = (Button) findViewById(R.id.alter_confirm);

        mButtonCancel.setOnClickListener(onClickListener);
        mButtonConfirm.setOnClickListener(onClickListener);

    }


    /**
     * Resource IDs cannot be used in a switch statement in Android library modules less... (Ctrl+F1)
     * Validates using resource IDs in a switch statement in Android library module. Resource IDs are non final
     * in the library projects since SDK tools r14, means that the library code cannot treat these IDs as constants.
     * Constant expression required
     */

    @Override
    public void onClick(View v) {
        int i = v.getId();


        String goodsNumberStr = mEditTextNum.getText().toString().trim();


        if (!TextUtils.isEmpty(goodsNumberStr)) {
            goodsNumber = Integer.parseInt(goodsNumberStr);
            if (i == R.id.number_sub) {
                if (goodsNumber > 1) {
                    goodsNumber -= 1;
                    mEditTextNum.setText(goodsNumber + "");
                }
            } else if (i == R.id.number_add) {
                goodsNumber += 1;
                mEditTextNum.setText(goodsNumber + "");

            }
        } else
            Toast.makeText(context, "商品数量不能为空！", Toast.LENGTH_SHORT).show();
    }
}

