package com.jsyh.shopping.uilibrary.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by sks on 2015/10/23.
 * 带滑动监听的webview
 */
public class MyWedView extends WebView {

    public ScrollInterface mScrollInterface;
    public MyWedView(Context context) {
        super(context);
    }

    public MyWedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        //mScrollInterface.onSChanged(l, t, oldl, oldt);

    }

    public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {

        this.mScrollInterface = scrollInterface;

    }

    public interface ScrollInterface {

        public void onSChanged(int l, int t, int oldl, int oldt);

    }
}
