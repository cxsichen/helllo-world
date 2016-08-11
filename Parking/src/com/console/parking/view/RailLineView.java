package com.console.parking.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.console.parking.util.DisplayUtil;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RailLineView extends View {
	float level = 98.5f;
	private Paint paint;
	private Context context;
	private int layoutWidth;
	private int layoutHeight;
	private Path  leftPath;
	private Path  rightPath;
	
	private void initPaint() {
		paint=new Paint();
	    paint.setAntiAlias(true);                       //设置画笔为无锯齿  
	    paint.setColor(Color.MAGENTA);                    //设置画笔颜色  
	    paint.setStrokeWidth((float) 5.0);              //线宽  
	    paint.setStyle(Style.STROKE);  

	}

	private void initdata() {
		// TODO Auto-generated method stub
		layoutWidth = this.getWidth();
		layoutHeight = this.getHeight();

	}

	public RailLineView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initPaint();
	}

	public RailLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initPaint();
	}

	public RailLineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
		initPaint();

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// TODO Auto-generated method stub  
		initdata();
	    
	    leftPath = new Path();
	    rightPath = new Path();
		leftPath.moveTo(0, layoutHeight-30);
		rightPath.moveTo(layoutWidth, layoutHeight-30);
	    
        // 连接路径到点

	    leftPath.quadTo(150, 400,leftDest*(300f/540f) , 250); 
        canvas.drawPath(leftPath, paint);
        
        rightPath.quadTo(layoutWidth-150, 400,(rightDest+400)*(300f/540f), 250); 
        canvas.drawPath(rightPath, paint);

	}
    private int leftDest=0;
    private int rightDest=200;
    
    public void setValue(int value){
    	leftDest=value;
    	rightDest=value;
    	invalidate();
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