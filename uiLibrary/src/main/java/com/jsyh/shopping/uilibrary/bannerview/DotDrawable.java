package com.jsyh.shopping.uilibrary.bannerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;

import com.jsyh.shopping.uilibrary.uiutils.Utils;

/**
 * 
 * @ClassName: CircularDrawable
 * @Description: TODO(广告样式的指引条样式 圆形带数字) 仿照京东
 * @author yaodingding
 * @date 2015-9-17 上午9:56:36
 * 
 */
public class DotDrawable extends BannerDrawable {

	private float intrinsicWidth;
	private float intrinsicHeight;
	private Paint paint;
	private float textCenterVerticalBaselineY;
	private float padding = 0;
	private int count;
	private int index;
	private float singleDotWidth = 0;// 单个圆点的宽度

	public DotDrawable(Context context, int count, int index) {
		super(context);
		padding = Utils.dip2px(context, 5);
		singleDotWidth = Utils.dip2px(context, 15);
		this.count = count;
		this.index = index;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(30f);
		MeasureWidth();
	}

	private void MeasureWidth() {
		// TODO Auto-generated method stub
		intrinsicWidth = (singleDotWidth) * count;
		intrinsicHeight = singleDotWidth;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
	}

	@Override
	public void draw(Canvas canvas) {
		for (int i = 0; i < count; i++) {
			if (i == index) {
				paint.setColor(Color.WHITE);
			} else {
				paint.setColor(0xff808080);
			}
			canvas.drawCircle((singleDotWidth) * i + singleDotWidth / 2, intrinsicHeight / 2, (singleDotWidth - padding) / 2, paint);
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIntrinsicWidth() {
		return (int) intrinsicWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return (int) intrinsicHeight;
	}

	@Override
	public void setText(int count, int index) {
		// TODO Auto-generated method stub
		this.count = count;
		this.index = index;
		MeasureWidth();
		invalidateSelf();
	}
}
