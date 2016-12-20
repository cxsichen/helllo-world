package com.console.canreader.fragment.SSVolkswagenGolf;

import java.text.DecimalFormat;
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

public class CarInfoFragment4 extends BaseFragment {

	private String canName = "";
	private String canFirtName = "";

	CanInfo mCanInfo;
	TextView vehicleNo;
	TextView carCheckDays;
	TextView carCheckDaysTitle;
	TextView oilCheckDistance;
	TextView oilCheckDistanceTitle;
	TextView oilchangeDays;
	TextView oilchangeDaysTitle;
	TextView oilchangeDistance;
	TextView oilchangeDistanceTitle;
	Context mContext;

	DecimalFormat fnum = new DecimalFormat("##0.00");

	public CarInfoFragment4() {

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				shwoView(mCaninfo);
			}
		}
	}

	private void shwoView(CanInfo caninfo) {
		// TODO Auto-generated method stub
		vehicleNo.setText(caninfo.VEHICLE_NO);

		switch (caninfo.INSPECTON_DAYS_STATUS) {
		case 0:
			carCheckDaysTitle.setText(mContext.getResources().getString(
					R.string.inspection_days));
			carCheckDays.setText("--");
			break;
		case 1:
			carCheckDaysTitle.setText(mContext.getResources().getString(
					R.string.inspection_need_days));
			carCheckDays.setText(caninfo.INSPECTON_DAYS
					+ mContext.getResources().getString(R.string.day));
			break;
		case 2:
			carCheckDaysTitle.setText(mContext.getResources().getString(
					R.string.inspection_overdue_days));
			carCheckDays.setText(caninfo.INSPECTON_DAYS
					+ mContext.getResources().getString(R.string.day));
			break;
		default:
			break;
		}

		switch (caninfo.INSPECTON_DISTANCE_STATUS) {
		case 0:
			oilCheckDistanceTitle.setText(mContext.getResources().getString(
					R.string.inspection_distance));
			oilCheckDistance.setText("--");
			break;
		case 1:
			oilCheckDistanceTitle.setText(mContext.getResources().getString(
					R.string.inspection_need_distance));
			oilCheckDistance.setText(caninfo.INSPECTON_DISTANCE
					+ (caninfo.INSPECTON_DISTANCE_UNIT == 0 ? "km" : "mi"));
			break;
		case 2:
			oilCheckDistanceTitle.setText(mContext.getResources().getString(
					R.string.inspection_overdue_distance));
			oilCheckDistance.setText(caninfo.INSPECTON_DISTANCE
					+ (caninfo.INSPECTON_DISTANCE_UNIT == 0 ? "km" : "mi"));
			break;
		default:
			break;
		}

		switch (caninfo.OILCHANGE_SERVICE_DAYS_STATUS) {
		case 0:
			oilchangeDaysTitle.setText(mContext.getResources().getString(
					R.string.oil_change_service_days));
			oilchangeDays.setText("--");
			break;
		case 1:
			oilchangeDaysTitle.setText(mContext.getResources().getString(
					R.string.oil_change_service_need_days));
			oilchangeDays.setText(caninfo.OILCHANGE_SERVICE_DAYS
					+ mContext.getResources().getString(R.string.day));
			break;
		case 2:
			oilchangeDaysTitle.setText(mContext.getResources().getString(
					R.string.oil_change_service_overdue_days));
			oilchangeDays.setText(caninfo.OILCHANGE_SERVICE_DAYS
					+ mContext.getResources().getString(R.string.day));
			break;
		default:
			break;
		}

		switch (caninfo.OILCHANGE_SERVICE_DISTANCE_STATUS) {
		case 0:
			oilchangeDistanceTitle.setText(mContext.getResources().getString(
					R.string.oil_change_service_distance));
			oilchangeDistance.setText("--");
			break;
		case 1:
			oilchangeDistanceTitle.setText(mContext.getResources().getString(
					R.string.oil_change_service_need_distance));
			oilchangeDistance.setText(caninfo.OILCHANGE_SERVICE_DISTANCE
					+ (caninfo.INSPECTON_DISTANCE_UNIT == 0 ? "km"
							: "mi"));
			break;
		case 2:
			oilchangeDistanceTitle.setText(mContext.getResources().getString(
					R.string.oil_change_overdue_distance));
			oilchangeDistance.setText(caninfo.OILCHANGE_SERVICE_DISTANCE
					+ (caninfo.INSPECTON_DISTANCE_UNIT == 0 ? "km"
							: "mi"));
			break;
		default:
			break;
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
		View view = inflater.inflate(R.layout.carinfo_layout_4, null);
		initView(view);
		mContext = getActivity();
		return view;
	}

	private void initView(View pageView) {
		// TODO Auto-generated method stub
		vehicleNo = (TextView) pageView.findViewById(R.id.vehicleNo);
		carCheckDays = (TextView) pageView.findViewById(R.id.carCheckDays);
		carCheckDaysTitle = (TextView) pageView
				.findViewById(R.id.carCheckDaysTitle);
		oilCheckDistance = (TextView) pageView
				.findViewById(R.id.oilCheckDistance);
		oilCheckDistanceTitle = (TextView) pageView
				.findViewById(R.id.oilCheckDistanceTitle);
		oilchangeDays = (TextView) pageView.findViewById(R.id.oilchangeDays);
		oilchangeDaysTitle = (TextView) pageView
				.findViewById(R.id.oilchangeDaysTitle);
		oilchangeDistance = (TextView) pageView
				.findViewById(R.id.oilchangeDistance);
		oilchangeDistanceTitle = (TextView) pageView
				.findViewById(R.id.oilchangeDistanceTitle);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
