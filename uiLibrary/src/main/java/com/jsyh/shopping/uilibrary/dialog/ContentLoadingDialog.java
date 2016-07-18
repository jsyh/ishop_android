package com.jsyh.shopping.uilibrary.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jsyh.shopping.uilibrary.R;

/**
 * Created by Su on 2016/4/19.
 */
public class ContentLoadingDialog extends PopupWindow{
    Context context;
    public ContentLoadingDialog(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.content_progress_dialog, null),
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;
    }

    public void show() {
        //setBackgroundDrawable(new ColorDrawable(0x0000));
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);
        showAtLocation(getContentView().getRootView(), Gravity.CENTER, 0, 0);
    }
}
