package com.console.launcher_console.control;

import com.console.launcher_console.R;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TpmsContol extends BroadcastReceiver {
	public static final String CARD_AUTHORITY = "com.inetwp.softwareservice.CardProvider";
	public static final Uri CARD_URL = Uri.parse("content://" + CARD_AUTHORITY
			+ "/card");
	private static final String BLUETOOTH_STATE_CHANGED = "android.bluetooth.adapter.action.STATE_CHANGED";
	private static final String BLUETOOTH_ACTION = "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED";

	public static final String LEFT_FRONT_KP = "left_front_kp"; // 左前
	public static final String LEFT_FRONT_TEMP = "left_front_temp";// 左前℃
	public static final String LEFT_FRONT_ID = "left_front_id"; // 左前设备ID
	public static final String LEFT_FRONT_VOLTAGE = "left_front_voltage";// 电量
	public static final String LEFT_FRONT_STATE = "left_front_state";// 状态

	public static final String RIGHT_FRONT_KP = "right_front_kp"; // 右前
	public static final String RIGHT_FRONT_TEMP = "right_front_temp"; // 右前℃
	public static final String RIGHT_FRONT_ID = "right_front_id"; // 右前ID
	public static final String RIGHT_FRONT_VOLTAGE = "right_front_voltage";
	public static final String RIGHT_FRONT_STATE = "right_front_state";// 状态

	public static final String RIGHT_REAR_KP = "right_rear_kp"; // 右后
	public static final String RIGHT_REAR_TEMP = "right_rear_temp"; // 右后℃
	public static final String RIGHT_REAR_ID = "right_rear_id";// 右后ID
	public static final String RIGHT_REAR_VOLTAGE = "right_rear_voltage";
	public static final String RIGHT_REAR_STATE = "right_rear_state";// 状态

	public static final String LEFT_REAR_KP = "left_rear_kp"; // 左后
	public static final String LEFT_REAR_TEMP = "left_rear_temp"; // 左后℃
	public static final String LEFT_REAR_ID = "left_rear_id"; // 左后ID
	public static final String LEFT_REAR_VOLTAGE = "left_rear_voltage";
	public static final String LEFT_REAR_STATE = "left_rear_state";// 状态
	public static final String LF_SIGNAL = "lf_signal"; // 左前信号
	public static final String LR_SIGNAL = "lr_signal";
	public static final String RF_SIGNAL = "rf_signal";
	public static final String RR_SIGNAL = "rr_signal";

	public static final String TMPS_TV_MAX = "tmps_tv_max"; // 胎压上限
	public static final String TMPS_TV_MIN = "tmps_tv_min";// 胎压下限
	public static final String TMPS_TV_TEMP = "tmps_tv_temp";// 温度上限
	private final int TEXTCOLOR= 0x57FFFFFF;

	FrameLayout tpmsLayout;
	Context context;

	private TextView flPress;
	private TextView frPress;
	private TextView blPress;
	private TextView brPress;

	private TextView flTemp;
	private TextView frTemp;
	private TextView blTemp;
	private TextView brTemp;

	private ImageView flBattery;
	private ImageView frBattery;
	private ImageView blBattery;
	private ImageView brBattery;
	private Cursor cardservice;

	private LinearLayout tpmsDataLayout;

	private AlphaAnimation alphaAnimation = null;
	private BluetoothAdapter mBluetoothAdapter = null;

	private ContentObserver cardser = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			if (tpmsDataLayout.getVisibility()==View.INVISIBLE) {
                showTpms();
                return;
            }
			queryTyreData();
		}
	};

	private void queryTyreData() {		
		try {
			cardservice = context.getContentResolver().query(CARD_URL, null, null,
					null, null);
			if (cardservice != null && cardservice.moveToNext()) {
				String lf_device_id = cardservice.getString(cardservice
						.getColumnIndex(LEFT_FRONT_ID));
				String lr_device_id = cardservice.getString(cardservice
						.getColumnIndex(LEFT_REAR_ID));
				String rf_device_id = cardservice.getString(cardservice
						.getColumnIndex(RIGHT_FRONT_ID));
				String rr_device_id = cardservice.getString(cardservice
						.getColumnIndex(RIGHT_REAR_ID));

				if (isEmpty(lf_device_id) && isEmpty(lr_device_id)
						&& isEmpty(rf_device_id) && isEmpty(rr_device_id)) {
					goneTpms();
					return;
				}

				// 胎压上限
				String tmps_tv_max = cardservice.getString(cardservice
						.getColumnIndex(TMPS_TV_MAX));
				// 胎压下限
				String tmps_tv_min = cardservice.getString(cardservice
						.getColumnIndex(TMPS_TV_MIN));
				// 温度上限
				String tmps_tv_temp = cardservice.getString(cardservice
						.getColumnIndex(TMPS_TV_TEMP));
				if (cardservice.getString(cardservice
								.getColumnIndex(LF_SIGNAL)).equals("false")) {
					flPress.setText("--kPa");
					flTemp.setText("--℃");
					stopWarnAnimation(flPress);
					stopWarnAnimation(flTemp);
				}else{
				if (lf_device_id != null) {
					String left_font_KP = cardservice.getString(cardservice
							.getColumnIndex(LEFT_FRONT_KP));
					String left_font_TM = cardservice.getString(cardservice
							.getColumnIndex(LEFT_FRONT_TEMP));
					flPress.setText(left_font_KP + "kPa");
					flTemp.setText(left_font_TM + "℃");
					if (!"".equals(lf_device_id)) {
						twinkleTy(flPress, tmps_tv_max, tmps_tv_min,
								left_font_KP);
						twinkleWd(flTemp, tmps_tv_temp, left_font_TM);

						if (lf_device_id.length() == 5) {
							batteryState5(cardservice.getString(cardservice
									.getColumnIndex(LEFT_FRONT_STATE)),
									flBattery);
						} else if (lf_device_id.length() == 6) {
							batteryState6(cardservice.getString(cardservice
									.getColumnIndex(LEFT_FRONT_VOLTAGE)),
									flBattery);
						}
					} else {
						stopWarnAnimation(flPress);
						stopWarnAnimation(flTemp);
						setElectricImage(flBattery, false);
					}
				}
}

				// zl_tai
				if (cardservice.getString(cardservice
								.getColumnIndex(RF_SIGNAL)).equals("false")) {
					stopWarnAnimation(frPress);
					stopWarnAnimation(frTemp);
					frPress.setText("--kPa");
					frTemp.setText("--℃");
				}else{
				if (rf_device_id != null) {
					String right_front_KP = cardservice.getString(cardservice
							.getColumnIndex(RIGHT_FRONT_KP));
					String right_front_TM = cardservice.getString(cardservice
							.getColumnIndex(RIGHT_FRONT_TEMP));
					frPress.setText(right_front_KP + "kPa");
					frTemp.setText(right_front_TM + "℃");
					if (!"".equals(rf_device_id)) {
						twinkleTy(frPress, tmps_tv_max, tmps_tv_min,
								right_front_KP);
						twinkleWd(frTemp, tmps_tv_temp, right_front_TM);

						if (rf_device_id.length() == 5) {
							batteryState5(cardservice.getString(cardservice
									.getColumnIndex(RIGHT_FRONT_STATE)),
									frBattery);
						} else if (rf_device_id.length() == 6) {
							batteryState6(cardservice.getString(cardservice
									.getColumnIndex(RIGHT_FRONT_VOLTAGE)),
									frBattery);
						}
					} else {
						stopWarnAnimation(frPress);
						stopWarnAnimation(frTemp);
						setElectricImage(frBattery, false);
					}

					}
				}

				if (cardservice.getString(cardservice
								.getColumnIndex(LR_SIGNAL)).equals("false")) {
					stopWarnAnimation(blPress);
					stopWarnAnimation(blTemp);
					blPress.setText("--kPa");
					blTemp.setText("--℃");
				}else{
				if (lr_device_id != null) {
					String left_rear_KP = cardservice.getString(cardservice
							.getColumnIndex(LEFT_REAR_KP));
					String left_rear_TM = cardservice.getString(cardservice
							.getColumnIndex(LEFT_REAR_TEMP));
					blPress.setText(left_rear_KP + "kPa");
					blTemp.setText(left_rear_TM + "℃");
					if (!"".equals(lr_device_id)) {
						twinkleTy(blPress, tmps_tv_max, tmps_tv_min,
								left_rear_KP);
						twinkleWd(blTemp, tmps_tv_temp, left_rear_TM);

						if (lr_device_id.length() == 5) {
							batteryState5(cardservice.getString(cardservice
									.getColumnIndex(LEFT_REAR_STATE)),
									blBattery);
						} else if (lr_device_id.length() == 6) {
							batteryState6(cardservice.getString(cardservice
									.getColumnIndex(LEFT_REAR_VOLTAGE)),
									blBattery);
						}
					} else {
						stopWarnAnimation(blPress);
						stopWarnAnimation(blTemp);
						setElectricImage(blBattery, false);
					}

					}
				}
				
				if (cardservice.getString(cardservice
								.getColumnIndex(RR_SIGNAL)).equals("false")) {
					stopWarnAnimation(brPress);
					stopWarnAnimation(brTemp);
					brPress.setText("--kPa");
					brTemp.setText("--℃");
				}else{
				if (rr_device_id != null) {
					String right_rear_KP = cardservice.getString(cardservice
							.getColumnIndex(RIGHT_REAR_KP));
					String right_rear_TM = cardservice.getString(cardservice
							.getColumnIndex(RIGHT_REAR_TEMP));
					brPress.setText(right_rear_KP + "kPa");
					brTemp.setText(right_rear_TM + "℃");
					if (!"".equals(rr_device_id)) {
						twinkleTy(brPress, tmps_tv_max, tmps_tv_min,
								right_rear_KP);
						twinkleWd(brTemp, tmps_tv_temp, right_rear_TM);

						if (rr_device_id.length() == 5) {
							batteryState5(cardservice.getString(cardservice
									.getColumnIndex(RIGHT_REAR_STATE)),
									brBattery);
						} else if (rr_device_id.length() == 6) {
							batteryState6(cardservice.getString(cardservice
									.getColumnIndex(RIGHT_REAR_VOLTAGE)),
									brBattery);
						}
					} else {
						stopWarnAnimation(brPress);
						stopWarnAnimation(brTemp);
						setElectricImage(brBattery, false);
					}

					}
				}
			}
			if (cardservice != null) {
				cardservice.close();
			}
		} catch (Exception e) {
			Log.e("inet_zl", "taiya-Exception");
		}
	}

	public TpmsContol(Context context, FrameLayout layout) {
		tpmsLayout = layout;
		this.context = context;
		init();
		registerContentObserver();
		queryTyreData();

	}

	private void registerContentObserver() {
		// TODO Auto-generated method stub
		context.getContentResolver().registerContentObserver(CARD_URL, false,
				cardser);
		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(BLUETOOTH_STATE_CHANGED);
		mIntentFilter.addAction(BLUETOOTH_ACTION);
		context.registerReceiver(this, mIntentFilter);
	}

	public void unregisterContentObserver() {
		// TODO Auto-generated method stub
		context.getContentResolver().unregisterContentObserver(cardser);
		context.unregisterReceiver(this);

	}

	private void init() {
		// TODO Auto-generated method stub
		flPress = (TextView) tpmsLayout.findViewById(R.id.pressure_fl);
		frPress = (TextView) tpmsLayout.findViewById(R.id.pressure_fr);
		blPress = (TextView) tpmsLayout.findViewById(R.id.pressure_bl);
		brPress = (TextView) tpmsLayout.findViewById(R.id.pressure_br);

		flTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_fl);
		frTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_fr);
		blTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_bl);
		brTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_br);

		flBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_fl);
		frBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_fr);
		blBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_bl);
		brBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_br);
		tpmsDataLayout = (LinearLayout) tpmsLayout
				.findViewById(R.id.tpms_data_layout);
		
		tpmsLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
                    ComponentName  mComponentNames = new ComponentName("com.inetwp.cardservice", "com.inetwp.card.DrawerMainActivity");
                    Intent intents = new Intent ();
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
                    intents.setComponent(mComponentNames);
                    context.startActivity(intents);
                } catch (Exception e) {  
                    e.printStackTrace();
                }
			}
		});

		alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(500);
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		alphaAnimation.start();
		


		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	private void goneTpms() {
	//	tpmsDataLayout.setVisibility(View.INVISIBLE);
	}
	
	private void showTpms() {
		tpmsDataLayout.setVisibility(View.VISIBLE);
	}


	private boolean isEmpty(String str) {
		if (str == null)
			return true;
		if ("null".equals(str))
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}

	// ==================taiya================================
	// zl
	// 温度高于上限的时候判断是否报警
	private void twinkleWd(TextView view, String max, String value) {
		if (!"--".equals(value)) {
			if (Double.parseDouble(value) > Double.parseDouble(max)) {
				startWarnAnimation(view);
			} else {
				stopWarnAnimation(view);
			}
		}
	}

	// 胎压异常的时候判断是否报警
	private void twinkleTy(TextView view, String max, String min, String value) {
		// 达到区间值，即闪烁
		if (!"--".equals(value)) {
			if (Double.parseDouble(value) > Double.parseDouble(max)
					|| Double.parseDouble(value) < Double.parseDouble(min)) {
				startWarnAnimation(view);
			} else {
				stopWarnAnimation(view);
			}
		}
	}

	// 判断ID长度为5的电量状态，图片闪烁(比较设备状态)
	private void batteryState5(String state, ImageView view) {
		if ("BATTERY_ERROR".equals(state)) {
			setElectricImage(view, true);
		} else {
			setElectricImage(view, false);
		}
	}

	// 判断ID长度为6的电量状态，图片闪烁（比较电压值）
	private void batteryState6(String stateValue, ImageView view) {
		if (!"--".equals(stateValue)) {
			if (Double.parseDouble(stateValue) < 2.4) {
				setElectricImage(view, true);
			} else {
				setElectricImage(view, false);
			}
		}
	}

	// --------------电量图片UI--------------------////
	private void setElectricImage(ImageView view, Boolean flag) {
		if (flag) {
			view.setVisibility(View.VISIBLE);
			startAnim(view);
		} else {
			stopAnim(view);
			view.setVisibility(View.INVISIBLE);
		}
	}

	// 警告、文字闪烁
	private void startWarnAnimation(TextView view) {
		// Log.e("zline",
		// "-------startWarnAnimation----------"+view.getCurrentTextColor());
		if (view.getCurrentTextColor() != Color.RED) {
			// Log.e("zline", "-------RED----------"+Color.RED);
			view.setTextColor(Color.RED);
			startAnim(view);
		}
	}

	// 停止动画
	private void stopWarnAnimation(TextView view) {
		if (view.getCurrentTextColor() != TEXTCOLOR) {
			stopAnim(view);
			view.setTextColor(TEXTCOLOR);
		}
	}

	// 闪烁动画
	private void startAnim(View view) {
		if (alphaAnimation != null) {
			view.setAnimation(alphaAnimation);
		}
	}

	// 停止动画
	private void stopAnim(View view) {
		view.clearAnimation();
	}

	// 获取蓝牙当前状态
	private int getBluetoothStat() {
		return mBluetoothAdapter.getState();
	}

	private void refreshTpms() {
		switch (getBluetoothStat()) {
		case BluetoothAdapter.STATE_ON:
			noNullDisplay();
			break;
		case BluetoothAdapter.STATE_TURNING_ON:
			Log.e("zline", "-------STATE_TURNING_ON----------");
			break;
		case BluetoothAdapter.STATE_OFF:
			goneTpms();
			 Log.e("zline", "-------STATE_OFF----------");
			break;
		case BluetoothAdapter.STATE_TURNING_OFF:
			 Log.e("zline", "-------STATE_TURNING_OFF----------");
			break;
		}
	}

	public String getID(int posion) {
		String deveceID = "";
		try {
			cardservice = context.getContentResolver().query(CARD_URL, null, null,
					null, null);
			if (cardservice != null && cardservice.moveToNext()) {

				if (posion == 0) {

					deveceID = cardservice.getString(cardservice
							.getColumnIndex(LEFT_FRONT_ID));
				} else if (posion == 1) {

					deveceID = cardservice.getString(cardservice
							.getColumnIndex(LEFT_REAR_ID));

				} else if (posion == 2) {

					deveceID = cardservice.getString(cardservice
							.getColumnIndex(RIGHT_FRONT_ID));

				} else if (posion == 3) {

					deveceID = cardservice.getString(cardservice
							.getColumnIndex(RIGHT_REAR_ID));
				}

			}

			if (cardservice != null) {
				cardservice.close();
			}
		} catch (Exception e) {
			Log.e("inet_zl", "taiya-Exception");
		}

		return deveceID;
	}

	private void noNullDisplay() {
		if (!isEmpty(getID(0)) || !isEmpty(getID(1)) || !isEmpty(getID(2))
				|| !isEmpty(getID(3))) {
			showTpms();
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final String action = intent.getAction();
		if (action.equals(BLUETOOTH_STATE_CHANGED)
				|| action.equals(BLUETOOTH_ACTION)) {
			refreshTpms();
		}
	}

}
