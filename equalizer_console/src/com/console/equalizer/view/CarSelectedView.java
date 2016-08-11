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
	private Paint mBackLinePaint;
	private Paint mBoldLinePaint;
	private LineGroup colunmLineGroup;
	private LineGroup rowLineGroup;
	private static int mColumnIndex = 8;
	private static int mRowIndex = 8;
	private Bitmap thumb;
	private int thumbWidth;
	private int thumbHeight;
	private int MAX_INDEX = 15;
	private int MIN_INDEX = 1;

	private boolean TOUCH = false;
	private float touchX = 0f;
	private float touchY = 0f;

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
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.carSelectedView, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.carSelectedView_carBg:
				mImg = BitmapFactory.decodeResource(getResources(),
						a.getResourceId(attr, 0));
				break;
			}
		}

		a.recycle();
		init();

		colunmLineGroup = new LineGroup();
		rowLineGroup = new LineGroup();

		thumb = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_thumb);
		thumbWidth = thumb.getWidth();
		thumbHeight = thumb.getHeight();
	}

	private void init() {
		// TODO Auto-generated method stub
		mBackLinePaint = new Paint();

		mBackLinePaint.setAntiAlias(true);
		mBackLinePaint.setColor(context.getResources().getColor(
				R.color.colorPrimary));
		mBackLinePaint.setStrokeWidth(DensityUtils.dip2px(context, 1)); // 1dp
																		// width
		mBackLinePaint.setStyle(Paint.Style.FILL);

		mBoldLinePaint = new Paint();

		mBoldLinePaint.setAntiAlias(true);
		mBoldLinePaint.setColor(context.getResources().getColor(
				R.color.colorAccent));
		mBoldLinePaint.setStrokeWidth(DensityUtils.dip2px(context, 4)); // 1dp
																		// width
		mBoldLinePaint.setStyle(Paint.Style.FILL);

	}

	private void initdata() {
		// TODO Auto-generated method stub

		// row
		rowLineGroup.lineWidth = DensityUtils.dip2px(context, 1);
		rowLineGroup.intervals = (float) (getHeight() - getPaddingTop()
				- getPaddingBottom() - rowLineGroup.lineWidth)
				/ (float) (ROW - 1);
		float fisrtDivider = (float) (getHeight() - (getPaddingTop()
				+ getPaddingBottom() + rowLineGroup.intervals * (ROW - 1) + rowLineGroup.lineWidth)) / 2f;
		rowLineGroup.startX = getPaddingLeft();
		rowLineGroup.stopX = getWidth() - getPaddingRight();
		rowLineGroup.startY = getPaddingTop() + fisrtDivider;
		rowLineGroup.stopY = getPaddingTop() + fisrtDivider
				+ rowLineGroup.lineWidth;

		// column

		colunmLineGroup.lineWidth = DensityUtils.dip2px(context, 1);
		colunmLineGroup.intervals = (float) (getWidth() - getPaddingLeft()
				- getPaddingRight() - colunmLineGroup.lineWidth)
				/ (float) (COLUMN - 1);
		fisrtDivider = (float) (getWidth() - (getPaddingLeft()
				+ getPaddingRight() + colunmLineGroup.intervals * (COLUMN - 1) + colunmLineGroup.lineWidth)) / 2f;
		colunmLineGroup.startY = getPaddingTop();
		colunmLineGroup.stopY = getHeight() - getPaddingBottom();
		colunmLineGroup.startX = getPaddingLeft() + fisrtDivider;
		colunmLineGroup.stopX = getPaddingLeft() + fisrtDivider
				+ colunmLineGroup.lineWidth;

	}

	@Override
	protected void onDraw(Canvas canvas) {

		initdata();

		// 画15排
		for (int i = 0; i < ROW; i++) {
			canvas.drawLine(rowLineGroup.startX, rowLineGroup.startY
					+ rowLineGroup.intervals * i, rowLineGroup.stopX,
					rowLineGroup.startY + rowLineGroup.intervals * i,
					mBackLinePaint);
		}
		// 画15列
		for (int i = 0; i < COLUMN; i++) {
			canvas.drawLine(colunmLineGroup.startX + colunmLineGroup.intervals
					* i, colunmLineGroup.startY, colunmLineGroup.startX
					+ colunmLineGroup.intervals * i, colunmLineGroup.stopY,
					mBackLinePaint);
		}

		if (true) {
			canvas.drawLine(rowLineGroup.startX, rowLineGroup.startY
					+ rowLineGroup.intervals * mRowIndex, rowLineGroup.stopX,
					rowLineGroup.startY + rowLineGroup.intervals * mRowIndex,
					mBoldLinePaint);

			canvas.drawLine(colunmLineGroup.startX + colunmLineGroup.intervals
					* mColumnIndex, colunmLineGroup.startY,
					colunmLineGroup.startX + colunmLineGroup.intervals
							* mColumnIndex, colunmLineGroup.stopY,
					mBoldLinePaint);
			canvas.drawBitmap(thumb,
					colunmLineGroup.startX + colunmLineGroup.intervals
							* mColumnIndex - thumbWidth / 2,
					rowLineGroup.startY + rowLineGroup.intervals * mRowIndex
							- thumbHeight / 2, null);
		} else {
			//根据触碰点画线 被放弃
			canvas.drawLine(rowLineGroup.startX, touchY
					- rowLineGroup.lineWidth, rowLineGroup.stopX, touchY
					- rowLineGroup.lineWidth, mBoldLinePaint);

			canvas.drawLine(touchX - colunmLineGroup.lineWidth,
					colunmLineGroup.startY, touchX - colunmLineGroup.lineWidth,
					colunmLineGroup.stopY, mBoldLinePaint);
			canvas.drawBitmap(thumb, touchX - thumbWidth / 2, touchY
					- thumbHeight / 2, null);
		}
	}

	private void checkPosition(int mColumnIndex, int mRowIndex) {
		if (mColumnIndex > MAX_INDEX)
			this.mColumnIndex = MAX_INDEX;
		if (mColumnIndex < MIN_INDEX)
			this.mColumnIndex = MIN_INDEX;
		if (mRowIndex > MAX_INDEX)
			this.mRowIndex = MAX_INDEX;
		if (mRowIndex < MIN_INDEX)
			this.mRowIndex = MIN_INDEX;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mColumnIndex = Math.round((event.getX() - colunmLineGroup.startX)
				/ colunmLineGroup.intervals);
		mRowIndex = Math.round((event.getY() - rowLineGroup.startX)
				/ rowLineGroup.intervals);
		checkPosition(mColumnIndex, mRowIndex);
		if(event.getAction()==MotionEvent.ACTION_UP&&mOnPositionChangedListener != null){
			//初始行被空出来了
			mOnPositionChangedListener.OnChange(mColumnIndex-1, mRowIndex-1);
		}
		invalidate();
		return true;
	}

	public void resetPosition() {
		mColumnIndex = 8;
		mRowIndex = 8;
		invalidate();
	}

	public void setPosition(int mColumnIndex, int mRowIndex) {
		this.mColumnIndex = mColumnIndex+1;
		this.mRowIndex = mRowIndex+1;
		checkPosition(this.mColumnIndex, this.mRowIndex);
		invalidate();
	}

	public int getColumnIndex() {
		return mColumnIndex;
	}

	public int getRowIndex() {
		return mRowIndex;
	}

	public interface OnPositionChangedListener {
		public void OnChange(int mColumnIndex, int mRowIndex);
	}

	OnPositionChangedListener mOnPositionChangedListener;

	public void setOnValueChangedListener(
			OnPositionChangedListener OnPositionChangedListener) {
		this.mOnPositionChangedListener = OnPositionChangedListener;
	}

	class LineGroup {
		float startX;
		float startY;
		float stopX;
		float stopY;
		float intervals;
		float lineWidth;
	}

}
