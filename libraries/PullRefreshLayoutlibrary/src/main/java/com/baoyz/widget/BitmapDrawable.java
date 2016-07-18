package com.baoyz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.TypedValue;

/**
 * Created by baoyz on 14/11/2.
 */
class BitmapDrawable extends RefreshDrawable {

	private boolean isRunning;
	private Rect mBounds;
	private Rect mTextBounds;
	private int mWidth;
	private int mHeight;
	private int maxheight;
	private int mOffsetTop;
	private int padding = 10;
	private Paint mPaint;
	private int[] mColorSchemeColors;
	private Handler mHandler = new Handler();
	private int index;
	private TextPaint textpaint;
	String finaltext = "让购物更健康";
	String state_text1 = "下拉可以刷新";
	String state_text2 = "松开后刷新";
	private float textWidth;
	private int resids[] = { R.mipmap.app_refresh_people_1,
			R.mipmap.app_refresh_people_2, R.mipmap.app_refresh_people_3 };
	private final int MAX_LEVEL = resids.length;

	BitmapDrawable(Context context, PullRefreshLayout layout) {
		super(context, layout);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(dp2px(3));
		textpaint = new TextPaint();
		textpaint.setStyle(Style.STROKE);
		textpaint.setAntiAlias(true);
		textpaint.setColor(0xff000000);
	}

	@Override
	public void setPercent(float percent) {
		mPaint.setColor(evaluate(percent, mColorSchemeColors[0],
				mColorSchemeColors[1]));
	}

	@Override
	public void setColorSchemeColors(int[] colorSchemeColors) {
		mColorSchemeColors = colorSchemeColors;
	}

	@Override
	public void offsetTopAndBottom(int offset) {
		// mTop += offset;
		mOffsetTop += offset;
		textpaint.setTextSize(25);
		textWidth = textpaint.measureText(finaltext);
		if (mOffsetTop < 0) {
			// mBounds = new RectF(getBounds().width() / 2 - mWidth / 2
			// - textWidth / 2, getBounds().bottom - mHeight, getBounds()
			// .width() / 2 + mWidth / 2 - textWidth / 2,
			// getBounds().bottom);
			// mBounds.inset(dp2px(15), dp2px(15));
		} else {
			mTextBounds = new Rect(
					(int) (getBounds().width() / 2 - textWidth / 2), 0,
					(int) (getBounds().width() / 2 + textWidth / 2), mOffsetTop);
			mBounds = new Rect(0, padding, mTextBounds.left, (int) mOffsetTop
					- padding);
			// mBounds.inset(0, dp2px(10));
			// mTextBounds.inset(0, dp2px(10));
			// mBounds.inset(dp2px(15), dp2px(15));
			// Log.d("test", "mBounds-->" + mBounds.toString());
		}
		invalidateSelf();
	}

	@Override
	public void start() {
		index = 0;
		isRunning = true;
		mHandler.post(mAnimationTask);
	}

	private Runnable mAnimationTask = new Runnable() {
		@Override
		public void run() {
			if (isRunning()) {
				index++;
				if (index >= MAX_LEVEL)
					index = 0;
				invalidateSelf();
				mHandler.postDelayed(this, 100);
			}
		}
	};

	@Override
	public void stop() {
		isRunning = false;
		mHandler.removeCallbacks(mAnimationTask);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		mWidth = getRefreshLayout().getFinalOffset();
		mHeight = mWidth;
		maxheight =(int) (mHeight*0.8);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		drawText(canvas);
		drawDrawable(canvas);
		canvas.restore();
	}

	private void drawDrawable(Canvas canvas) {
		if (isRunning) {
			Drawable drawablepeople = getContext().getResources().getDrawable(
					resids[index]);
			float scanl = mOffsetTop * 1.0f / maxheight;
			if (scanl > 1) {
				scanl = 1;
			}
			float peopleheight = maxheight * scanl;
			float peoplewidth = peopleheight
					* drawablepeople.getIntrinsicWidth()
					/ drawablepeople.getIntrinsicHeight();
			drawablepeople.setBounds((int) (mBounds.right - peoplewidth),
					(int) (mBounds.centerY() - peopleheight / 2),
					(int) (mBounds.right),
					(int) (mBounds.centerY() + peopleheight / 2));
			drawablepeople.draw(canvas);
		} else {
			if (mBounds==null)
				return;
			Drawable drawablepeople = getContext().getResources().getDrawable(
					R.mipmap.app_refresh_people_0);
			Drawable goodsDrawable = getContext().getResources().getDrawable(
					R.mipmap.app_refresh_goods_0);
			float scanl = mOffsetTop * 1.0f / maxheight;
			if (scanl > 1) {
				scanl = 1;
			}
			float peopleheight = maxheight * scanl; // ȡdrawable�ĳ���
			float peoplewidth = peopleheight
					* drawablepeople.getIntrinsicWidth()
					/ drawablepeople.getIntrinsicHeight(); // ȡdrawable�ĳ���
			float goodsWidth = goodsDrawable.getIntrinsicWidth() * peoplewidth
					/ drawablepeople.getIntrinsicWidth(); // ȡdrawable�ĳ���
			float goodsHeight = goodsDrawable.getIntrinsicHeight()
					* peoplewidth / drawablepeople.getIntrinsicWidth();
			float drawscanl=0.5f + 0.5f*scanl;
			drawablepeople.setBounds(
					(int) (mBounds.right*drawscanl- peoplewidth),
					(int) (mBounds.centerY()*drawscanl - peopleheight / 2),
					(int) (mBounds.right * drawscanl),
					(int) (mBounds.centerY()*drawscanl + peopleheight / 2));
			drawablepeople.draw(canvas);
			goodsDrawable.setBounds((int) (mBounds.right - goodsWidth),
					(int) (mBounds.centerY()*drawscanl - goodsHeight / 2),
					(int) (mBounds.right),
					(int) (mBounds.centerY()*drawscanl + goodsHeight / 2));
			goodsDrawable.draw(canvas);
		}
	}

	private void drawText(Canvas canvas) {
		if (mTextBounds==null)
			return;
		textpaint.setTextSize(25);
		textpaint.setColor(0xff808080);
		FontMetrics fm = textpaint.getFontMetrics();
		int textHeight = (int) (Math.ceil(fm.descent - fm.ascent) + 2);
		canvas.drawText(finaltext, mTextBounds.left + 10, mTextBounds.centerY()
				- textHeight / 2, textpaint);
		textpaint.setTextSize(20);
		if (isRunning) {
			canvas.drawText("正在加载中", mTextBounds.left + 10,
					mTextBounds.centerY() + textHeight / 2 + 5, textpaint);
		} else {
			if (mOffsetTop >= mHeight) {
				canvas.drawText(state_text2, mTextBounds.left + 10,
						mTextBounds.centerY() + textHeight / 2 + 5, textpaint);
			} else {
				canvas.drawText(state_text1, mTextBounds.left + 10,
						mTextBounds.centerY() + textHeight / 2 + 5, textpaint);
			}
		}

	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}

	private int evaluate(float fraction, int startValue, int endValue) {
		int startInt = startValue;
		int startA = (startInt >> 24) & 0xff;
		int startR = (startInt >> 16) & 0xff;
		int startG = (startInt >> 8) & 0xff;
		int startB = startInt & 0xff;

		int endInt = endValue;
		int endA = (endInt >> 24) & 0xff;
		int endR = (endInt >> 16) & 0xff;
		int endG = (endInt >> 8) & 0xff;
		int endB = endInt & 0xff;

		return ((startA + (int) (fraction * (endA - startA))) << 24)
				| ((startR + (int) (fraction * (endR - startR))) << 16)
				| ((startG + (int) (fraction * (endG - startG))) << 8)
				| ((startB + (int) (fraction * (endB - startB))));
	}

}
