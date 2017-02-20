package com.console.canreader.fragment.SSVolkswagenGolf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.VolkswagenGolf.GolfView1;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class CarInfoFragment2 extends BaseFragment {

	private String canName = "";
	private String canFirtName = "";

	TextView consumption;
	TextView travelling_time;
	TextView range;
	TextView speed;
	TextView distance;
	Context mContext;
	DecimalFormat fnum = new DecimalFormat("##0.0");

	public CarInfoFragment2() {

	}

	
	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				if (mCaninfo.CONSUMPTION_SINCE_REFUELING == 0xff) {
					consumption.setText("--");
				} else {
					consumption.setText(fnum
							.format(mCaninfo.CONSUMPTION_SINCE_REFUELING * 0.1f)
							+ "l/km");
				}

				if (mCaninfo.RANGE_SINCE_REFUELINGT == 0xff) {
					range.setText("--");
				} else {
					range.setText(mCaninfo.RANGE_SINCE_REFUELINGT + "km");
				}

				distance.setText(fnum
						.format(mCaninfo.DISTANCE_SINCE_REFUELING * 0.1f) + "km");

				if ((mCaninfo.TRAVELLINGTIME_SINCE_REFUELINGT / 60) != 0) {
					travelling_time.setText(mCaninfo.TRAVELLINGTIME_SINCE_REFUELINGT
							/ 60
							+ mContext.getResources().getString(R.string.hour)
							+ mCaninfo.TRAVELLINGTIME_SINCE_REFUELINGT
							% 60
							+ mContext.getResources()
									.getString(R.string.minute));
				} else {
					travelling_time.setText(mCaninfo.TRAVELLINGTIME_SINCE_REFUELINGT
							+ mContext.getResources()
									.getString(R.string.minute));
				}
				
				speed.setText(mCaninfo.SPEED_SINCE_REFUELINGT + "km/h");

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
		View view = inflater.inflate(R.layout.carinfo_layout_2, null);
		initView(view);
		mContext=getActivity();
		return view;
	}

	private void initView(View pageView) {
		// TODO Auto-generated method stub
		consumption = (TextView) pageView.findViewById(R.id.consumption);
		travelling_time = (TextView) pageView
				.findViewById(R.id.travelling_time);
		range = (TextView) pageView.findViewById(R.id.range);
		speed = (TextView) pageView.findViewById(R.id.speed);
		distance = (TextView) pageView.findViewById(R.id.distance);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
