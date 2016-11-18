package com.console.launcher_console.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class RunTextView extends TextView{

	public RunTextView(Context context) {
		super(context);
		createView();
	}
	

	public RunTextView(Context context,AttributeSet attrs) {
		super(context);
	}
	public RunTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	private void createView() {
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		setFocusableInTouchMode(true);
	}
		
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if(focused){
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
			
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if(hasWindowFocus){
		super.onWindowFocusChanged(hasWindowFocus);
		}
	}
	
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
