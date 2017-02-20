package com.console.equalizer.view;

import com.console.equalizer.R;
import com.console.equalizer.util.DensityUtils;

import android.app.usage.UsageEvents.Event;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CarSelectedView extends View {
	Context context;
	private Bitmap mImg;
	private final int COLUMN = 17;
	private final int ROW = 17;
	private static int mColumnIndex = 8;
	private static int mRowIndex = 8;
	private Bitmap thumb;
	private Bitmap verLine;
	private Bitmap honLine;
	private int thumbWidth;
	private int thumbHeight;
	private float MIN_X = 66;
	private float MAX_X = 212;
	private float MIN_Y = 160;
	private float MAX_Y = 394;

	private boolean TOUCH = false;
	private float touchX = 120f;
	private float touchY = 270f;

	public CarSelectedView(Context context) {
		this(context, null);
		this.context = context;
	}

	public CarSelectedView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public CarSelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView(context, attrs, defStyleAttr);
	}

	private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

		thumb = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_tumb_eq);
		verLine = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_ver_eq);
		honLine = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_hor_eq);
		thumbWidth = thumb.getWidth();
		thumbHeight = thumb.getHeight();
	}

	int temp=20;
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(verLine, touchX-temp, 0, null);
		canvas.drawBitmap(honLine, 0, touchY-temp, null);
		canvas.drawBitmap(thumb, touchX - 3-temp, touchY - 3-temp, null);
	}

	private void checkPosition(float touchX, float touchY) {
		if (touchX > MAX_X)
			this.touchX = MAX_X;
		if (touchX < MIN_X)
			this.touchX = MIN_X;
		if (touchY > MAX_Y)
			this.touchY = MAX_Y;
		if (touchY < MIN_Y)
			this.touchY = MIN_Y;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub  
		if (event.getAction() != MotionEvent.ACTION_UP){
			if((event.getX()>MAX_X+10&&event.getY()>MAX_Y+10)||(event.getX()>MAX_X+10&&event.getY()<MIN_Y-10)
					||(event.getX()< MIN_X-10&&event.getY()>MAX_Y+10)||(event.getX()< MIN_X-10&&event.getY()<MIN_Y-10)){
				return false;
			}
		}
		touchX = event.getX();
		touchY = event.getY();
		checkPosition(touchX, touchY);
		if (event.getAction() == MotionEvent.ACTION_UP
				&& mOnPositionChangedListener != null) {
			mOnPositionChangedListener.OnChange((touchX - MIN_X)
					/ (MAX_X - MIN_X) * 100f, (touchY - MIN_Y)
					/ (MAX_Y - MIN_Y) * 100f);
		}
		invalidate();
		return true;
	}

	public void resetPosition() {
		touchX = 120f;
		touchY = 270f;
		invalidate();
	}

	public void setPosition(float mColumnIndex, float mRowIndex) {
		touchX = MIN_X + mColumnIndex * (MAX_X - MIN_X) / 100f;
		touchY = MIN_Y + mRowIndex * (MAX_Y - MIN_Y) / 100f;
		checkPosition(touchX, touchY);
		if (mOnPositionChangedListener != null) {
			mOnPositionChangedListener.OnChange((touchX - MIN_X)
					/ (MAX_X - MIN_X) * 100f, (touchY - MIN_Y)
					/ (MAX_Y - MIN_Y) * 100f);
		}
		invalidate();
	}

	public int getColumnIndex() {
		return mColumnIndex;
	}

	public int getRowIndex() {
		return mRowIndex;
	}

	public interface OnPositionChangedListener {
		public void OnChange(float mColumnIndex, float mRowIndex);
	}

	OnPositionChangedListener mOnPositionChangedListener;

	public void setOnValueChangedListener(
			OnPositionChangedListener OnPositionChangedListener) {
		this.mOnPositionChangedListener = OnPositionChangedListener;
	}

}
