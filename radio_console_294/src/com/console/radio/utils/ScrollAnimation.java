package com.console.radio.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ScrollAnimation extends Animation {

	private View view;
	private int targetScrollX;
	private int startScrollX;
	private int totalValue;

	public ScrollAnimation(View view, int targetScrollX) {
		super();
		this.view = view;
		this.targetScrollX = targetScrollX;

		startScrollX = view.getScrollX();
		totalValue = this.targetScrollX - startScrollX;

		int time = Math.abs(totalValue);
		setDuration(time);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		int currentScrollX = (int) (startScrollX + totalValue
				* interpolatedTime);
		view.scrollTo(currentScrollX, 0);
	}

}
