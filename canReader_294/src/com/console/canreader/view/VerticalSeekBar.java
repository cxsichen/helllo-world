package com.console.canreader.view;



import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class VerticalSeekBar extends SeekBar {
	
	int usrProgress=100;

	public VerticalSeekBar(Context context) {
		super(context);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldh, oldw);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	protected void onDraw(Canvas c) {
		c.rotate(-90);
		c.translate(-getHeight(), 0);

		super.onDraw(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			int i = 0;
			i = getMax() - (int) (getMax() * event.getY() / getHeight());
			setProgress(i);
			Log.i("Progress", getProgress() + "");
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			if (mOnProgressChangedListener != null)
				mOnProgressChangedListener.OnChange((getProgress()*usrProgress)/100);
			if (mOnProgressChangedListener != null
					&& event.getAction() == MotionEvent.ACTION_UP)
				mOnProgressChangedListener.OnStop((getProgress()*usrProgress)/100);
			break;

		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}
	
	public void setusrProgress(int usrProgress){
		this.usrProgress=usrProgress;
	}

	public void setMProgress(int i) {
		setProgress(i);
		onSizeChanged(getWidth(), getHeight(), 0, 0);
		mOnProgressChangedListener.OnChange((i*usrProgress)/100);
	}

	public void setNumProgress(int i) {
		float temp = 100f / usrProgress;
		setProgress((int) (i * temp));
		onSizeChanged(getWidth(), getHeight(), 0, 0);
		mOnProgressChangedListener.OnChange((int) i);
	}

	public interface OnProgressChangedListener {
		public void OnChange(int progress);

		public void OnStop(int progress);
	}

	OnProgressChangedListener mOnProgressChangedListener;

	public void setOnProgressChangedListener(
			OnProgressChangedListener OnProgressChangedListener) {
		this.mOnProgressChangedListener = OnProgressChangedListener;
	}

}