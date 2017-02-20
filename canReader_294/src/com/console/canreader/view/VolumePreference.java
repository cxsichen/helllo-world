package com.console.canreader.view;

import com.console.canreader.R;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VolumePreference extends Preference {

	private View mContentView;

	public VolumePreference(Context context) {
		super(context);
	}

	public VolumePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VolumePreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		mContentView = LayoutInflater.from(getContext()).inflate(
				R.layout.freq_layout, parent, false);
		initView(mContentView);

		return mContentView;
	}

	private void initView(View mView) {

	}
}
