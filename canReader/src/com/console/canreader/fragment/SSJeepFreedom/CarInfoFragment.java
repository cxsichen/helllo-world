package com.console.canreader.fragment.SSJeepFreedom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.graphics.Color;
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

public class CarInfoFragment extends BaseFragment {

	private String canName = "";
	private String canFirtName = "";

	CanInfo mCanInfo;
	TextView oil;
	TextView battery;
	TextView safety_salt;
	TextView handbrake;
	TextView clean;
	TextView enginee;
	TextView driving_speed;
	TextView distance;
	ImageView icObdWasherFluid;
	ImageView icObdSeatBelt;
	ImageView icObdBattery;
	ImageView icObdHandBrake;
	ImageView icFuel;
	ImageView speedHand;

	DecimalFormat fnum = new DecimalFormat("##0.00");
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:

				break;
			default:
				break;
			}
		}
	};

	public CarInfoFragment() {

	}

	String[] strGroup = { "--", "P档", "N档", "R档", "D档", "S档" };

	@Override
	public void show(CanInfo caninfo) {
		// TODO Auto-generated method stub
		super.show(caninfo);
		if (caninfo != null) {
			if (caninfo.CHANGE_STATUS == 10) {
				if (caninfo.REMAIN_FUEL != -1)
					oil.setText(caninfo.REMAIN_FUEL + " %");
				if (caninfo.BATTERY_VOLTAGE != -1)
					battery.setText(getResources().getString(
							R.string.battery_info)
							+  "--");

				if (caninfo.FUEL_WARING_SIGN == 1) {
					icFuel.setColorFilter(Color.RED);
				} else {
					icFuel.setColorFilter(null);
				}

				if (caninfo.BATTERY_WARING_SIGN == 1) {
					icObdBattery.setColorFilter(Color.RED);
				} else {
					icObdBattery.setColorFilter(null);
				}

				if (caninfo.SAFETY_BELT_STATUS == 0) {
					safety_salt.setText(getResources().getString(
							R.string.safety_salt_info)
							+ getResources().getString(R.string.normal));
					icObdSeatBelt.setColorFilter(null);
				} else if (caninfo.SAFETY_BELT_STATUS == 1) {
					safety_salt.setText(getResources().getString(
							R.string.safety_salt_info)
							+ getResources().getString(R.string.untie));
					icObdSeatBelt.setColorFilter(Color.RED);
				} else {
					safety_salt.setText(getResources().getString(
							R.string.safety_salt_info));
					icObdSeatBelt.setColorFilter(null);
				}
				handbrake.setText("档位\n"
						+ "--");

				clean.setText("节气门位置\n" +  "--");

				if (caninfo.ENGINE_SPEED != -1)
					enginee.setText((int) caninfo.ENGINE_SPEED + " RPM");
				driving_speed.setText((int) caninfo.DRIVING_SPEED + "KM/H");
				distance.setText("--");
				speedHand.setRotation(1.125f * caninfo.DRIVING_SPEED);
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.dashboard_main_2, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	private void initView(View pageView) {
		// TODO Auto-generated method stub
		oil = (TextView) pageView.findViewById(R.id.oil);
		battery = (TextView) pageView.findViewById(R.id.battery);
		safety_salt = (TextView) pageView.findViewById(R.id.safety_salt);
		handbrake = (TextView) pageView.findViewById(R.id.handbrake);
		clean = (TextView) pageView.findViewById(R.id.clean);
		enginee = (TextView) pageView.findViewById(R.id.enginee);
		driving_speed = (TextView) pageView.findViewById(R.id.driving_speed);
		distance = (TextView) pageView.findViewById(R.id.distance);
		speedHand = (ImageView) pageView.findViewById(R.id.speed_hand);
		icObdWasherFluid = (ImageView) pageView
				.findViewById(R.id.icObdWasherFluid);
		icObdSeatBelt = (ImageView) pageView.findViewById(R.id.icObdSeatBelt);
		icObdBattery = (ImageView) pageView.findViewById(R.id.icObdBattery);
		icObdHandBrake = (ImageView) pageView.findViewById(R.id.icObdHandBrake);
		icFuel = (ImageView) pageView.findViewById(R.id.icFuel);

	}

}
