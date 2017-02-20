package com.console.launcher_console.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SelectImageView extends ImageView {
	
	public SelectImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SelectImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SelectImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	private Bitmap bitmap;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getActionMasked()) {

		case MotionEvent.ACTION_DOWN:
			this.setAlpha((int) 125);
			break;
		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_CANCEL:
			this.setAlpha((int) 255);
			break;
		default:
			break;

		}

		return super.onTouchEvent(event);

	}

}
