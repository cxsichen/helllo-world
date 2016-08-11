package com.console.launcher_console.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.console.launcher_console.util.DensityUtils;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FMView extends View {
	float level = 98.5f;
	private Paint paintLine;
	private Paint paint;
	private Paint paintPrimery;
	private Context context;
	private int layoutWidth;
	private int layoutHeight;
	private float fisrtValue = 85;
	private float midValue = 85;
	private int lineWidth = 0;
	private float userValue=0;

	private void intalPaint() {
		paintLine = new Paint();
		paintLine.setColor(0x8AFFFFFF);
		paintLine.setStrokeWidth(DensityUtils.dip2px(context, 1));

		paint = new Paint();
		paint.setTextSize(16f);
		paint.setColor(0x8AFFFFFF);
		paint.setAntiAlias(true);
	}

	private void initdate() {
		// TODO Auto-generated method stub
		layoutWidth = this.getWidth();
		layoutHeight = this.getHeight();
		lineWidth = DensityUtils.dip2px(context, 8);
		midValue = (float) (fisrtValue - 0.8f) + layoutWidth / lineWidth / 2f
				* 0.2f;
	}

	public FMView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		intalPaint();
	}

	public FMView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		intalPaint();
	}

	public FMView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
		intalPaint();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		initdate();
		int startPosition = 0;
		int tem = (int) fisrtValue;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 4; j++) {
				canvas.drawLine(startPosition,
						layoutHeight - DensityUtils.dip2px(context, 24),
						startPosition, layoutHeight, paintLine);
				startPosition += lineWidth;
			}
			canvas.drawLine(startPosition,
					layoutHeight - DensityUtils.dip2px(context, 48),
					startPosition, layoutHeight, paintLine);
			if (tem < 100) {
				canvas.drawText(tem + "", startPosition - 8, layoutHeight
						- DensityUtils.dip2px(context, 56), paint);
			} else {
				canvas.drawText(tem + "", startPosition - 12, layoutHeight
						- DensityUtils.dip2px(context, 56), paint);
			}
			startPosition += lineWidth;
			tem++;
		}
		if(userValue!=0){
			scrollTo(
					Math.round((userValue - midValue) * (float) lineWidth * 5f
							- (float) lineWidth / 2), 0);
		}

	}

	public void setValue(float value) {
		userValue=value;
		if (value >= 85f && value <= 109) {
			if (onValueChangedListener != null) {
				onValueChangedListener.OnChange((new BigDecimal(Float
						.toString(value)))
						.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
			}
			scrollTo(
					Math.round((value - midValue) * (float) lineWidth * 5f
							- (float) lineWidth / 2), 0);
		}

	}

	public float add(float d1, float d2) {
		BigDecimal b1 = new BigDecimal(Float.toString(d1));
		BigDecimal b2 = new BigDecimal(Float.toString(d2));
		return b1.add(b2).floatValue();

	}

	public int mul(float d1, int d2) {
		BigDecimal b1 = new BigDecimal(Float.toString(d1));
		BigDecimal b2 = new BigDecimal(Integer.toString(d2));
		return b1.multiply(b2).intValue();
	}

	public float mul(float d1, float d2) {
		BigDecimal b1 = new BigDecimal(Float.toString(d1));
		BigDecimal b2 = new BigDecimal(Float.toString(d2));
		return b1.multiply(b2).floatValue();
	}

	public interface OnValueChangedListener {
		public void OnChange(float value);
	}

	OnValueChangedListener onValueChangedListener;

	public void setOnValueChangedListener(
			OnValueChangedListener onValueChangedListener) {
		this.onValueChangedListener = onValueChangedListener;
	}

}