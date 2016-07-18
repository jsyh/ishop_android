package com.jsyh.shopping.uilibrary.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;

import com.jsyh.shopping.uilibrary.R;

public class CartDrawable extends Drawable implements Animatable {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Drawable icon_cart;
    private RectF numRectF = null;
    private float maxpadding = 0;
    private String catNum = "20";
    private Handler mHandler = new Handler();
    private boolean isRunning;
    private int mLevel = 10;

    public CartDrawable(Context context, int flag, int imgId) {
        if (flag == 0)
            icon_cart = context.getResources().getDrawable(R.mipmap.home_cart);
        else if (flag == 1)
            icon_cart = context.getResources().getDrawable(R.mipmap.home_cart_press);
        else if (flag == 2)
            icon_cart = context.getResources().getDrawable(imgId);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mWidth = icon_cart.getIntrinsicWidth();
        mHeight = icon_cart.getIntrinsicHeight();
        //numRectF = new RectF(mWidth * 2 / 3 - maxpadding, maxpadding, mWidth - maxpadding, mWidth / 3f + maxpadding);
        numRectF = new RectF(mWidth * 5 / 8, maxpadding - mWidth / 8, mWidth * 9 / 8, mWidth * 3 / 8);

    }

    @Override
    public void draw(Canvas canvas) {
        icon_cart.setBounds(0, 0, icon_cart.getIntrinsicWidth(), icon_cart.getIntrinsicHeight());
        icon_cart.draw(canvas);
        if (!(TextUtils.isEmpty(catNum) || catNum.equals("0"))) {
            if (Integer.parseInt(catNum) > 99) {
                drawText(canvas, "99+");
            } else {
                drawText(canvas, catNum);
            }
        }
    }

    private void drawText(Canvas canvas, String text) {
        // 计算字体宽度 算出圆形的大小
        mPaint.setColor(0xffff0000);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mPaint.setTypeface(font);
        mPaint.setTextSize(numRectF.height() / 1.5f);
        float textWidth = mPaint.measureText(text);
        if (textWidth > numRectF.width()) {
            numRectF.left = mWidth - textWidth - textWidth / 2;
        }
        canvas.drawRoundRect(numRectF, 20, 20, mPaint);
        mPaint.setColor(0xffffffff);
        FontMetrics fm = mPaint.getFontMetrics();
        float textCenterVerticalBaselineY = (float) (numRectF.centerY()) - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(text, numRectF.centerX() - textWidth / 2, textCenterVerticalBaselineY, mPaint);
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setCatNum(String catNum) {
        this.catNum = catNum;
        invalidateSelf();
    }

    public void addOne() {
        this.catNum = Integer.parseInt(catNum) + 1 + "";
    }

    public void addNums(int nums) {
        this.catNum = Integer.parseInt(catNum) + nums + "";
    }

    @Override
    public boolean isRunning() {
        // TODO Auto-generated method stub
        return isRunning;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        isRunning = true;
        mHandler.post(mAnimationTask);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        isRunning = false;
        mHandler.removeCallbacks(mAnimationTask);
    }

    private Runnable mAnimationTask = new Runnable() {
        @Override
        public void run() {
            if (isRunning()) {
                if (mLevel <= -10) {
                    mLevel = 10;
                    stop();
                    return;
                } else if (mLevel > 0) {
                    numRectF.inset(-0.3f, -0.3f);
                    invalidateSelf();
                    mHandler.postDelayed(this, 10);
                } else if (mLevel <= 0) {
                    numRectF.inset(0.3f, 0.3f);
                    invalidateSelf();
                    mHandler.postDelayed(this, 10);
                }
                mLevel--;
            }
        }
    };

}
