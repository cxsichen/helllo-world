package com.console.canreader.fragment.SSVolkswagenGolf;

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
	TextView tv2;
	TextView tv3;
	ProgressBar mSeekBar;

	public CarInfoFragment5() {

	}

    String[] strGroup={"1/4","3/8","1/2","1","3/2","2"};

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				tv.setText(mCaninfo.CONVENIENCE_CONSUMERS_UNIT == 0 ? "l/h"
						: "gal/h");
				tv2.setText("右转向灯:"+(mCaninfo.LIGHT_RIHGTTURN_LAMP_SETTING==1?"开":"关"));
				tv3.setText("左转向灯:"+(mCaninfo.LIGHT_LEFTTURN_LAMP_SETTING==1?"开":"关"));
				mSeekBar.setProgress(mCaninfo.CONVENIENCE_CONSUMERS);
				tv1.setText(strGroup[mCaninfo.CONVENIENCE_PERCENT]);
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
		View view = inflater.inflate(R.layout.ss_golf_layout, null);
		mSeekBar = (ProgressBar) view.findViewById(R.id.ss_golf_seekbar);
		tv = (TextView) view.findViewById(R.id.ss_golf_tv);
		tv2=(TextView) view.findViewById(R.id.ssgolf_tv1);
		tv3=(TextView) view.findViewById(R.id.ssgolf_tv2);
		tv1 = (TextView) view.findViewById(R.id.ss_golf_percent);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
