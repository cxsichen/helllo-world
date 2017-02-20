package com.console.canreader.activity.VolkswagenGolf;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.view.ViewPageFactory;

public class GolfView3 extends ViewPageFactory {

	TextView consumption;
	TextView travelling_time;
	TextView range;
	TextView speed;
	TextView distance;
	Context mContext;

	DecimalFormat fnum = new DecimalFormat("##0.00");

	public GolfView3(Context context, int layout) {
		super(context, layout);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		consumption = (TextView) pageView.findViewById(R.id.consumption);
		travelling_time = (TextView) pageView
				.findViewById(R.id.travelling_time);
		range = (TextView) pageView.findViewById(R.id.range);
		speed = (TextView) pageView.findViewById(R.id.speed);
		distance = (TextView) pageView.findViewById(R.id.distance);
	}

	@Override
	public void showView(CanInfo caninfo) {
		// TODO Auto-generated method stub
		switch (caninfo.CONSUMPTION_UNIT) {
		case 0:
			consumption.setText(caninfo.CONSUMPTION_LONG_TERM + "L/100km");
			break;
		case 1:
			consumption.setText(caninfo.CONSUMPTION_LONG_TERM + "km/L");
			break;
		case 2:
			consumption.setText(caninfo.CONSUMPTION_LONG_TERM + "mpg(UK)");
			break;
		case 3:
			consumption.setText(caninfo.CONSUMPTION_LONG_TERM + "mpg(US)");
			break;
		default:
			break;
		}
		if (caninfo.TRAVELLINGTIME_LONG_TERM / 60 > 0) {
			travelling_time.setText(caninfo.TRAVELLINGTIME_LONG_TERM / 60
					+ mContext.getResources().getString(R.string.hour)
					+ caninfo.TRAVELLINGTIME_LONG_TERM % 60
					+ mContext.getResources().getString(R.string.minute));
		} else {
			travelling_time.setText(caninfo.TRAVELLINGTIME_LONG_TERM % 60
					+ mContext.getResources().getString(R.string.minute));
		}

		if (caninfo.RANGE_UNIT == 0) {
			range.setText(caninfo.RANGE + "km");
		} else {
			range.setText(caninfo.RANGE + "mi");
		}

		if (caninfo.SPEED_UNIT == 0) {
			speed.setText(caninfo.SPEED_LONG_TERM + "km/h");
		} else {
			speed.setText(caninfo.SPEED_LONG_TERM + "mph");
		}

		if (caninfo.DISTANCE_UNIT == 0) {
			distance.setText(caninfo.DISTANCE_LONG_TERM + "km");
		} else {
			distance.setText(caninfo.DISTANCE_LONG_TERM + "mi");
		}

	}

}
