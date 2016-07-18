package com.jsyh.shopping.uilibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by momo on 2015/10/10.
 *
 * 因为有个 bug  ，暂时先用这个。
 *
 * bug ： 使用 系统的，向下滑动的时候，toolbar  布局不显示。。。
 */
public class CoordinatorLayoutFixup extends CoordinatorLayout {

    private TestListener testListener;



    public void setTestListener(TestListener testListener) {
        this.testListener = testListener;
    }

    public CoordinatorLayoutFixup(Context context) {
        super(context);
    }

    public CoordinatorLayoutFixup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatorLayoutFixup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (testListener != null) {
            testListener.touchEvent();
        }

    }

    public interface TestListener{

        void touchEvent();
    }
}
