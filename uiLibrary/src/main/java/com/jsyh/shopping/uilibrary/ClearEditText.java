package com.jsyh.shopping.uilibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.view.MotionEventCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 自带清除按钮的输入框，清除按钮的作用是清空输入框的输入内容；
 * 需要注意的是，清除按钮会占据drawableRight的位置，所以设置drawableRight会无效
 */
public class ClearEditText extends EditText {

    private SearchListener msSearchListener;

    public void setSearchListener(SearchListener msSearchListener) {
        this.msSearchListener = msSearchListener;
    }

    /**
     * 默认的清除按钮图标资源
     */
    private static final int ICON_CLEAR_DEFAULT = R.mipmap.search_clear_text;

    /**
     * 清楚按钮的图标
     */
    private Drawable drawableClear;

    public ClearEditText(Context context) {
        super(context);
        init(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        // 获取清除按钮图标资源
        int iconClear =
                typedArray.getResourceId(R.styleable.ClearEditText_iconClear, ICON_CLEAR_DEFAULT);
        drawableClear = getResources().getDrawable(iconClear);
        updateIconClear();
        typedArray.recycle();

        // 设置TextWatcher用于更新清除按钮显示状态
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (msSearchListener != null) {
                    msSearchListener.onTextChanged(s,start,before,count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateIconClear();

                if (msSearchListener != null) {
                    msSearchListener.afterTextChanged(s);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        int xDown = 0 ;
        if (action == MotionEvent.ACTION_DOWN) {

        }else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            // 点击是的 x 坐标
            xDown = (int) event.getX();

            // 清除按钮的起始区间大致为[getWidth() - getCompoundPaddingRight(), getWidth()]，
            // 点击的x坐标在此区间内则可判断为点击了清除按钮
            if (xDown >= (getWidth() - getCompoundPaddingRight()) && xDown < getWidth()) {
                // 清空文本
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 更新清除按钮图标显示
     */
    public void updateIconClear() {
        // 获取设置好的drawableLeft、drawableTop、drawableRight、drawableBottom
        Drawable[] drawables = getCompoundDrawables();
        if (length() > 0) //输入框有输入内容才显示删除按钮
        {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableClear,
                    drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null,
                    drawables[3]);
        }
    }

    /**
     * 设置清除按钮图标样式
     *
     * @param resId 图标资源id
     */
    public void setIconClear(@DrawableRes int resId) {
        drawableClear = getResources().getDrawable(resId);
        updateIconClear();
    }

    /**
     * 搜索监听
     */
    public interface SearchListener{
        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

}
