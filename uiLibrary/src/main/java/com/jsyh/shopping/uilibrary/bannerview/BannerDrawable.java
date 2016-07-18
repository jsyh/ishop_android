package com.jsyh.shopping.uilibrary.bannerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by baoyz on 14/10/29.
 */
public abstract class BannerDrawable extends Drawable {
	protected Context context;

	public BannerDrawable(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * @param count
	 *            广告的总数
	 * @param index
	 *            广告当前 item
	 */
	public abstract void setText(int count, int index);
}
