package com.console.canreader.view;

import java.text.DecimalFormat;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AirConDialog extends Dialog {

	Context context;
	private ImageView leftFan;
	private ImageView rightFan;
	private static CanInfo canInfo;
	private Animation amt;
	private TextView leftTemperature;
	private TextView rightTemperature;
	private RelativeLayout rightFanLayout;
	private RelativeLayout leftFanLayout;

	private TextView rightAirVolume;
	private TextView leftAirVolume;

	private ImageView maxFrontDefrost;
	private ImageView recirculation;
	private ImageView rearWindowDefrost;

	private TextView lanterIndicator;
	private TextView outsideTemp;
	private TextView acIndicator;

	private ImageView leftSeatHeat;
	private ImageView rightSeatHeat;

	private ImageView airOutlet;

	private static int airConStatus = 3;
	private static int largeStatus = 3;
	private static int smallStatus = 3;
	
	DecimalFormat fnum = new DecimalFormat("##0.0");

	private int[] leftSeatDraws = { R.drawable.stat_seat_heating_left_1,
			R.drawable.stat_seat_heating_left_2,
			R.drawable.stat_seat_heating_left_3 };
	private int[] rightSeatDraws = { R.drawable.stat_seat_heating_right_1,
			R.drawable.stat_seat_heating_right_2,
			R.drawable.stat_seat_heating_right_3 };
	private int[] airOutletDraws = { R.drawable.stat_air_outlet_lower,
			R.drawable.stat_air_outlet_upper,
			R.drawable.stat_air_outlet_upper_lower,
			R.drawable.stat_air_outlet_defrost,
			R.drawable.stat_air_outlet_defrost_lower,
			R.drawable.stat_air_outlet_defrost_upper,
			R.drawable.stat_air_outlet_defrost_upper_lower, };

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_DIGLOG_HIDE:
				dismiss();
				break;
			default:
				break;
			}
		};
	};

	public AirConDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public AirConDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		amt = AnimationUtils.loadAnimation(context, R.anim.tip);
		amt.setInterpolator(new LinearInterpolator());
		getWindow().setWindowAnimations(R.style.DialogAnimation);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		leftFan = (ImageView) findViewById(R.id.ic_fan_left);
		rightFan = (ImageView) findViewById(R.id.ic_fan_right);
		leftTemperature = (TextView) findViewById(R.id.temperature_left);
		rightTemperature = (TextView) findViewById(R.id.temperature_right);
		rightFanLayout = (RelativeLayout) findViewById(R.id.right_fan);
		leftFanLayout = (RelativeLayout) findViewById(R.id.left_fan);
		rightAirVolume = (TextView) findViewById(R.id.air_volume_right);
		leftAirVolume = (TextView) findViewById(R.id.air_volume_left);
		maxFrontDefrost = (ImageView) findViewById(R.id.max_front);
		recirculation = (ImageView) findViewById(R.id.recirculation);
		rearWindowDefrost = (ImageView) findViewById(R.id.rear_window_defrost);
		lanterIndicator = (TextView) findViewById(R.id.lanterIndicator);
		outsideTemp = (TextView) findViewById(R.id.outside_temp);
		acIndicator = (TextView) findViewById(R.id.acIndicator);
		leftSeatHeat = (ImageView) findViewById(R.id.left_seat_heating);
		rightSeatHeat = (ImageView) findViewById(R.id.right_seat_heating);
		airOutlet = (ImageView) findViewById(R.id.air_outlet);
	}

	private void showStatus(CanInfo canInfo) {
		if (canInfo == null)
			return;
		if (airConStatus != canInfo.AIR_CONDITIONER_STATUS) {
			airConStatus = canInfo.AIR_CONDITIONER_STATUS;
			if (airConStatus == 1) {
				rightFan.startAnimation(amt);
				leftFan.startAnimation(amt);
			} else {
				rightFan.clearAnimation();
				leftFan.clearAnimation();
			}
		}

		leftFanLayout.setBackgroundResource(R.drawable.outline_cooling);
		rightFanLayout.setBackgroundResource(R.drawable.outline_cooling);

		leftTemperature.setText(String.valueOf(canInfo.DRIVING_POSITON_TEMP)
				+ "°");
		rightTemperature.setText(String
				.valueOf(canInfo.DEPUTY_DRIVING_POSITON_TEMP) + "°");

		if (canInfo.DRIVING_POSITON_TEMP == 0)
			leftTemperature.setText("L0");
		if (canInfo.DEPUTY_DRIVING_POSITON_TEMP == 0)
			rightTemperature.setText("L0");
		if (canInfo.DRIVING_POSITON_TEMP == 255)
			leftTemperature.setText("HI");
		if (canInfo.DEPUTY_DRIVING_POSITON_TEMP == 255)
			rightTemperature.setText("HI");
		if (canInfo.DRIVING_POSITON_TEMP == -1) {
			leftTemperature.setAlpha(0.12f);
		} else {
			leftTemperature.setAlpha(1f);
		}
		if (canInfo.DEPUTY_DRIVING_POSITON_TEMP == -1) {
			rightTemperature.setAlpha(0.12f);
		} else {
			rightTemperature.setAlpha(1f);
		}

		// -1表示自动
		if (canInfo.AIR_RATE == -1) {
			rightAirVolume.setTextSize(22);
			leftAirVolume.setTextSize(22);
			rightAirVolume.setText("Auto");
			leftAirVolume.setText("Auto");
		} else {
			rightAirVolume.setTextSize(42);
			leftAirVolume.setTextSize(42);
			rightAirVolume.setText(String.valueOf(canInfo.AIR_RATE));
			leftAirVolume.setText(String.valueOf(canInfo.AIR_RATE));
		}

		// 前窗除雾
		if (canInfo.MAX_FRONT_LAMP_INDICATOR == 1) {
			maxFrontDefrost.setAlpha(1f);
		} else {
			maxFrontDefrost.setAlpha(0.12f);
		}
		// 内外循环
		if (canInfo.CYCLE_INDICATOR == 0) {
			recirculation.setAlpha(1f);
			recirculation
					.setImageResource(R.drawable.stat_recirculation_outside);
		} else if (canInfo.CYCLE_INDICATOR == 1) {
			recirculation.setAlpha(1f);
			recirculation.setImageResource(R.drawable.stat_recirculation);
		} else if (canInfo.CYCLE_INDICATOR == 2) {
			recirculation.setAlpha(0.12f);
		}
		// 后窗除雾
		if (canInfo.REAR_LAMP_INDICATOR == 1) {
			rearWindowDefrost.setAlpha(1f);
		} else {
			rearWindowDefrost.setAlpha(0.12f);
		}

		if (canInfo.LARGE_LANTERN_INDICATOR == 0
				&& canInfo.SMALL_LANTERN_INDICATOR == 0) {
			lanterIndicator.setAlpha(0.12f);
		} else {
			lanterIndicator.setAlpha(1f);
			if (largeStatus != canInfo.LARGE_LANTERN_INDICATOR) {
				largeStatus = canInfo.LARGE_LANTERN_INDICATOR;
				if (canInfo.LARGE_LANTERN_INDICATOR == 1) {
					lanterIndicator.setText("MAX\nAUTO");
				} else if (canInfo.SMALL_LANTERN_INDICATOR == 1) {
					lanterIndicator.setText("AUTO");
				}
			}
			if (smallStatus != canInfo.SMALL_LANTERN_INDICATOR) {
				smallStatus = canInfo.SMALL_LANTERN_INDICATOR;
				if (canInfo.SMALL_LANTERN_INDICATOR == 1) {
					lanterIndicator.setText("AUTO");
				} else if (canInfo.LARGE_LANTERN_INDICATOR == 1) {
					lanterIndicator.setText("MAX\nAUTO");
				}
			}
		}
		if (canInfo.OUTSIDE_TEMPERATURE == -10000f) {
			outsideTemp.setText("--");
		} else {
			outsideTemp.setText(fnum.format(canInfo.OUTSIDE_TEMPERATURE)
					+ context.getString(R.string.temp_unit) + "\n" + "OUT");
		}

		if (canInfo.AC_INDICATOR_STATUS == 1) {
			acIndicator.setAlpha(1f);
		} else {
			acIndicator.setAlpha(0.12f);
		}
		if (canInfo.LEFT_SEAT_TEMP == 0) {
			leftSeatHeat.setAlpha(0.12f);
		} else {
			leftSeatHeat.setAlpha(1f);
			if (canInfo.LEFT_SEAT_TEMP > 0 && canInfo.LEFT_SEAT_TEMP < 4)
				leftSeatHeat
						.setImageResource(leftSeatDraws[canInfo.LEFT_SEAT_TEMP - 1]);
		}

		if (canInfo.RIGTHT_SEAT_TEMP == 0) {
			rightSeatHeat.setAlpha(0.12f);
		} else {
			rightSeatHeat.setAlpha(1f);
			if (canInfo.RIGTHT_SEAT_TEMP > 0 && canInfo.RIGTHT_SEAT_TEMP < 4)
				rightSeatHeat
						.setImageResource(rightSeatDraws[canInfo.RIGTHT_SEAT_TEMP - 1]);
		}

		int sum = canInfo.UPWARD_AIR_INDICATOR * 4
				+ canInfo.PARALLEL_AIR_INDICATOR * 2
				+ canInfo.DOWNWARD_AIR_INDICATOR;

		if (sum == 0) {
			airOutlet.setAlpha(0.12f);
		} else {
			airOutlet.setAlpha(1f);
			airOutlet.setImageResource(airOutletDraws[sum - 1]);
		}
	}

	public void setCanInfo(CanInfo canInfo) {
		if (leftFan == null) {
			return;
		}
		this.canInfo = canInfo;
		mHandler.removeMessages(Contacts.MSG_DIGLOG_HIDE);
		showStatus(canInfo);
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_DIGLOG_HIDE, 1000 * 7);
	}
}
