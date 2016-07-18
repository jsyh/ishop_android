package com.jsyh.shopping.uilibrary.bannerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
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
public class CircularDrawable extends BannerDrawable {

	private float width, intrinsicWidth;
	private float height, intrinsicHeight;
	private String text;
	private Paint paint;
	private float textCenterVerticalBaselineY;
	private float padding = 0;

	public CircularDrawable(Context context,int count, int index) {
		super(context);
		padding = Utils.dip2px(context, 10);
		if (count == 0) {
			this.text = "0";
		} else {
			this.text = (index+1) + "/" + count;
		}
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30f);
		MeasureText();
	}

	private void MeasureText() {

		float textWidth = paint.measureText(text);
		width = textWidth;
		FontMetrics fm = paint.getFontMetrics();
		height = (float) (Math.ceil(fm.descent - fm.ascent) + 2);
		if (width > height) {
			intrinsicWidth = width + padding;
			intrinsicHeight = width + padding;
		} else {
			intrinsicWidth = height + padding;
			intrinsicHeight = height + padding;
		}
		textCenterVerticalBaselineY = intrinsicHeight / 2 - fm.descent
				+ (fm.bottom - fm.top) / 2;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(text, intrinsicWidth / 2 - width / 2,
				textCenterVerticalBaselineY, paint);
		paint.setColor(0x55000000);
		canvas.drawCircle(intrinsicWidth / 2, intrinsicHeight / 2,
				intrinsicWidth / 2, paint);
//		paint.setColor(Color.WHITE);
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
		if (count == 0) {
			this.text = "0";
		} else {
			this.text = (index + 1) + "/" + count;
		}
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(30f);
		MeasureText();
		invalidateSelf();
	}
}
