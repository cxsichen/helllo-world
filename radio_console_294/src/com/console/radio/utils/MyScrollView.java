package com.console.radio.utils;

import java.util.ArrayList;
import java.util.List;

import com.console.radio.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyScrollView extends HorizontalScrollView {

	TextView fmTv1;
	TextView fmTv2;
	TextView fmTv3;
	TextView amTv1;
	TextView amTv2;

	ImageView fmId1;
	ImageView fmId2;
	ImageView fmId3;
	ImageView amId1;
	ImageView amId2;
	
	RelativeLayout fmLy1;
	RelativeLayout fmLy2;
	RelativeLayout fmLy3;
	RelativeLayout amLy1;
	RelativeLayout amLy2;
	Context context;

	List<TextView> tvList = new ArrayList<TextView>();
	List<ImageView> ivList = new ArrayList<ImageView>();
	List<RelativeLayout> lyList = new ArrayList<RelativeLayout>();

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		fmTv1 = (TextView) findViewById(R.id.fmTv1);
		fmTv2 = (TextView) findViewById(R.id.fmTv2);
		fmTv3 = (TextView) findViewById(R.id.fmTv3);
		amTv1 = (TextView) findViewById(R.id.amTv1);
		amTv2 = (TextView) findViewById(R.id.amTv2);

		tvList.add(fmTv1);
		tvList.add(fmTv2);
		tvList.add(fmTv3);
		tvList.add(amTv1);
		tvList.add(amTv2);
		
		fmLy1=(RelativeLayout) findViewById(R.id.fmLy1);
		fmLy2=(RelativeLayout) findViewById(R.id.fmLy2);
		fmLy3=(RelativeLayout) findViewById(R.id.fmLy3);
		amLy1=(RelativeLayout) findViewById(R.id.amLy1);
		amLy2=(RelativeLayout) findViewById(R.id.amLy2);
		lyList.add(fmLy1);
		lyList.add(fmLy2);
		lyList.add(fmLy3);
		lyList.add(amLy1);
		lyList.add(amLy2);
		for (int i = 0; i < tvList.size(); i++) {
			lyList.get(i).setTag(i);
			lyList.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (onScrollChangedListener != null)
						onScrollChangedListener.OnItemSelected((int) v.getTag());
				}
			});
		}

		fmId1 = (ImageView) findViewById(R.id.fmId1);
		fmId2 = (ImageView) findViewById(R.id.fmId2);
		fmId3 = (ImageView) findViewById(R.id.fmId3);
		amId1 = (ImageView) findViewById(R.id.amId1);
		amId2 = (ImageView) findViewById(R.id.amId2);

		ivList.add(fmId1);
		ivList.add(fmId2);
		ivList.add(fmId3);
		ivList.add(amId1);
		ivList.add(amId2);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		if (onScrollChangedListener != null)
			onScrollChangedListener.OnChange(getScrollX());

	}

	public interface OnScrollChangedListener {
		public void OnChange(int position);

		public void OnItemSelected(int index);
	}

	OnScrollChangedListener onScrollChangedListener;

	public void setOnScrollChangedListener(
			OnScrollChangedListener onScrollChangedListener) {
		this.onScrollChangedListener = onScrollChangedListener;
	}

	int[] distance = { 0, 0, 80, 160, 160 };

	public void syncSeletedPosition(int item) {
		scrollTo(distance[item], 0);
	}

	public void setSelectedItem(int item) {
		for (int i = 0; i < 5; i++) {
			if (item == i) {
				tvList.get(i).setTextColor(
						context.getResources().getColor(R.color.text_selected));
				ivList.get(i).setVisibility(View.VISIBLE);
			} else {
				tvList.get(i).setTextColor(Color.WHITE);
				ivList.get(i).setVisibility(View.INVISIBLE);
			}
		}
	}

}
