package com.jsyh.shopping.uilibrary.popuwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jsyh.shopping.uilibrary.R;

/**
 * Created by sks on 2015/10/19.
 * 二维码
 */
public class QRCodePopupwindow extends PopupWindow {

    private View mMenuView;
    private ImageView imgCode,imgCancel;
    private Context context;
    public QRCodePopupwindow(Context context){

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.code_image_view, null);
        imgCancel = (ImageView) mMenuView.findViewById(R.id.imgCancel);
        imgCode = (ImageView) mMenuView.findViewById(R.id.imgCode);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupAnimation);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
    //显示二维码
    public void getCode(Bitmap code){
        imgCode.setImageBitmap(code);
    }
}
