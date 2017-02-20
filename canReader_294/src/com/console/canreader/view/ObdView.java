package com.console.canreader.view;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;

public class ObdView extends ViewPageFactory {

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
	Context mContext;

	DecimalFormat fnum = new DecimalFormat("##0.00");

	public ObdView(Context context, int layout) {
		super(context, layout);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public void initView() {
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

	@Override
	public void showView(CanInfo caninfo) {
		// TODO Auto-generated method stub
		if (caninfo.REMAIN_FUEL != -1)
			oil.setText(caninfo.REMAIN_FUEL + " L");
		if (caninfo.BATTERY_VOLTAGE != -1)
			battery.setText(mContext.getResources().getString(
					R.string.battery_info)
					+ fnum.format(caninfo.BATTERY_VOLTAGE) + " V");

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
			safety_salt.setText(mContext.getResources().getString(
					R.string.safety_salt_info)
					+ mContext.getResources().getString(R.string.normal));
			icObdSeatBelt.setColorFilter(null);
		} else if (caninfo.SAFETY_BELT_STATUS == 1) {
			safety_salt.setText(mContext.getResources().getString(
					R.string.safety_salt_info)
					+ mContext.getResources().getString(R.string.untie));
			icObdSeatBelt.setColorFilter(Color.RED);
		} else {
			safety_salt.setText(mContext.getResources().getString(
					R.string.safety_salt_info));
			icObdSeatBelt.setColorFilter(null);
		}

		if (caninfo.HANDBRAKE_STATUS == 0) {
			handbrake.setText(mContext.getResources().getString(
					R.string.handbrake_info)
					+ mContext.getResources().getString(R.string.normal));
			icObdHandBrake.setColorFilter(null);
		} else if (caninfo.HANDBRAKE_STATUS == 1) {
			handbrake.setText(mContext.getResources().getString(
					R.string.handbrake_info)
					+ mContext.getResources().getString(R.string.not_put));
			icObdHandBrake.setColorFilter(Color.RED);
		} else {
			handbrake.setText(mContext.getResources().getString(
					R.string.handbrake_info));
			icObdHandBrake.setColorFilter(null);
		}

		if (caninfo.DISINFECTON_STATUS == 0) {
			clean.setText(mContext.getResources().getString(
					R.string.washer_info)
					+ mContext.getResources().getString(R.string.normal));
			icObdWasherFluid.setColorFilter(null);
		} else if (caninfo.DISINFECTON_STATUS == 1) {
			clean.setText(mContext.getResources().getString(
					R.string.washer_info)
					+ mContext.getResources().getString(R.string.too_low));
			icObdWasherFluid.setColorFilter(Color.RED);
		} else {
			clean.setText(mContext.getResources().getString(
					R.string.washer_info));
			icObdWasherFluid.setColorFilter(null);
		}

		if (caninfo.ENGINE_SPEED != -1)
			enginee.setText((int) caninfo.ENGINE_SPEED + " RPM");
		driving_speed.setText((int) caninfo.DRIVING_SPEED + "KM/H");
		distance.setText(mContext.getResources().getString(
				R.string.driving_distance)
				+ " " + caninfo.DRIVING_DISTANCE + "km");
		speedHand.setRotation(1.125f * caninfo.DRIVING_SPEED);
	}

}
