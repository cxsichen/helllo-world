package com.console.canreader.fragment.RZCVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.VolkswagenGolf.GolfView1;
import com.console.canreader.activity.VolkswagenGolf.GolfView4;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class CarInfoFragment5 extends BaseFragment {

	private LayoutInflater inflater;
	TextView tv;
	TextView tv1;
	ProgressBar mSeekBar;

	public CarInfoFragment5() {

	}

	String[] str = { "L/100km", "km/L", "mpg(UK)", "mpg(US)" };

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				tv.setText(mCaninfo.CONVENIENCE_CONSUMERS_UNIT == 1 ? "l/h"
						: "gal/h");
				mSeekBar.setMax(mCaninfo.CONVENIENCE_CONSUMERS_UNIT == 1 ? 1000
						: 250);
				mSeekBar.setProgress(mCaninfo.CONVENIENCE_CONSUMERS);
				tv1.setText(mCaninfo.INSTANT_CONSUMPTION
						+ str[mCaninfo.INSTANT_CONSUMPTION_UNIT]);
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.rzc_golf_layout, null);
		mSeekBar = (ProgressBar) view.findViewById(R.id.golf_seekbar);
		tv = (TextView) view.findViewById(R.id.golf_tv);
		tv1 = (TextView) view.findViewById(R.id.golf_tv1);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
