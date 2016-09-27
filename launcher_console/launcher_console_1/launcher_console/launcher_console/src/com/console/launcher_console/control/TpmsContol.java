package com.console.launcher_console.control;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.console.launcher_console.R;
import com.console.launcher_console.util.Constact;

public class TpmsContol implements OnClickListener {
	public static final String TAG = "TpmsContol";// 胎压
	private static final Uri CARD_URL = Uri
			.parse("content://aicare.net.cn.itpms/tpms");
	// id;设备方位：1-左前，2-左后，3-右前，4-右后
	public static final long LEFT_FRONT = 1L;
	public static final long LEFT_REAR = 2L;
	public static final long RIGHT_FRONT = 3L;
	public static final long RIGHT_REAR = 4L;
	public static final String PRESSURE = "pressure";// 胎压
	public static final String TEMP = "temp";// 温度
	public static final String IS_NO_SIGNAL = "is_No_Signal";// 是否有信号：0-有信号，1-无信号
	public static final String IS_LOW_VOLTAGE = "is_Low_Voltage";// 0-电量正常，1-电量低
	public static final String IS_HIGH_TEMP = "is_High_Temp";// 0-温度正常，1-温度过高
	public static final String IS_HIGH_PRESSURE = "is_High_Pressure";// 值：0和1,1表示胎压过高
	public static final String IS_LOW_PRESSURE = "is_Low_Pressure";// 值：0和1,1表示胎压过低
	public static final String IS_LEAK = "is_Leak";// 0-正常，1-漏气
	public static final String TEMP_UNIT = "temp_Unit";// 温度单位：℃，℉
	public static final String PRESSURE_UNIT = "pressure_Unit";// 气压单位：Bar,Kpa,Psi
	private final int TEXTCOLOR = 0x57FFFFFF;

	private static final String LEAK_CAR = "漏气";
	// private static final String NO_SIGNAL = "无信号";
	public static final long NORMAL = 0L;
	public static final long NO_NORMAL = 1L;

	private Long id;// 设备方位：1-左前，2-左后，3-右前，4-右后
	private String pressure;// 胎压
	private String temp;// 温度
	private Long isNoSignal;// 是否有信号：0-有信号，1-无信号
	private Long isLowVoltage;// 0-电量正常，1-电量低
	private Long isHighTemp;// 0-温度正常，1-温度过高
	private Long isHighPressure;// 值：0和1,1表示胎压过高
	private Long isLowPressure;// 值：0和1,1表示胎压过低
	private Long isLeak;// 0-正常，1-漏气
	private String tempUnit;// 温度单位：℃，℉
	private String pressureUnit;// 气压单位：Bar,Kpa,Psi
	private RelativeLayout tpmsLayout;
	private Context mContext;

	private TextView lfPress;
	private TextView rfPress;
	private TextView lrPress;
	private TextView rrPress;

	private TextView lfTemp;
	private TextView rfTemp;
	private TextView lrTemp;
	private TextView rrTemp;

	private ImageView lfBattery;
	private ImageView rfBattery;
	private ImageView lrBattery;
	private ImageView rrBattery;
	private Cursor cursor;

	private AlphaAnimation alphaAnimation = null;

	public TpmsContol(Context context, RelativeLayout layout) {
		tpmsLayout = layout;
		this.mContext = context;
		init();
		registerContentObserver();
		queryTyreData();
	}

	/**
	 * 以string形式取出tpms对象
	 * */
	private void queryTyreData() {
		try {
			cursor = mContext.getContentResolver().query(CARD_URL, null, null,
					null, null);
			Log.e(TAG, "cursor:" + cursor);
			ConvertToStorage(cursor);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	/**
	 * 通过Cursor取出tpms对象
	 * */
	private void ConvertToStorage(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return;
		}
		for (int i = 0; i < resultCounts; i++) {
			id = cursor.getLong(0);
			pressure = cursor.getString(cursor.getColumnIndex("pressure"));
			temp = cursor.getString(cursor.getColumnIndex("temp"));
			isNoSignal = cursor.getLong(cursor.getColumnIndex("is_No_Signal"));
			isLowVoltage = cursor.getLong(cursor
					.getColumnIndex("is_Low_Voltage"));
			isHighTemp = cursor.getLong(cursor.getColumnIndex("is_High_Temp"));
			isHighPressure = cursor.getLong(cursor
					.getColumnIndex("is_High_Pressure"));
			isLowPressure = cursor.getLong(cursor
					.getColumnIndex("is_Low_Pressure"));
			isLeak = cursor.getLong(cursor.getColumnIndex("is_Leak"));
			tempUnit = cursor.getString(cursor.getColumnIndex("temp_Unit"));
			pressureUnit = cursor.getString(cursor
					.getColumnIndex("pressure_Unit"));
			if (Constact.DEBUG) {
				Log.e(TAG, "id:" + id + "  pressure:" + pressure + "  temp:"
						+ temp + "  isNoSignal:" + isNoSignal
						+ "  isLowVoltage:" + isLowVoltage + "  isHighTemp:"
						+ isHighTemp + "  isLowPressure:" + isLowPressure
						+ "  isLeak:" + isLeak + "  tempUnit:" + tempUnit
						+ "  pressureUnit:" + pressureUnit);
			}
			if (id == LEFT_FRONT) {
				uiDisplay(lfPress, lfTemp, lfBattery);
			} else if (id == LEFT_REAR) {
				uiDisplay(lrPress, lrTemp, lrBattery);
			} else if (id == RIGHT_FRONT) {
				uiDisplay(rfPress, rfTemp, rfBattery);
			} else if (id == RIGHT_REAR) {
				uiDisplay(rrPress, rrTemp, rrBattery);
			}
			cursor.moveToNext();
		}
	}

	private void uiDisplay(TextView presssureT, TextView tempT,
			ImageView batteryI) {
		presssureT.setText(pressure + pressureUnit);
		tempT.setText(temp + tempUnit);
		twinkleTy(presssureT, isHighPressure, isLowPressure);
		twinkleWd(tempT, isHighTemp);
		batteryState(batteryI, isLowVoltage);
		if (isLeak == NO_NORMAL) {
			tempT.setText(LEAK_CAR);
			tempT.setTextColor(Color.RED);
		}/*
		 * else if (isNoSignal==NO_NORMAL) { flTemp.setText(NO_SIGNAL);
		 * flTemp.setTextColor(Color.RED); }
		 */
	}

	@Override
	public void onClick(View v) {
		try {
			
			if (isAppInstalled(mContext, "com.android.usbhost")) {
				openApplication(mContext,"com.android.usbhost");
			}else if (isAppInstalled(mContext, "aicare.net.cn.itpms")) {
				openApplication(mContext,"aicare.net.cn.itpms");
			} else {
				openApplication(mContext,"com.inetwp.cardservice");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	// 注册胎压监听
	private void registerContentObserver() {
		mContext.getContentResolver().registerContentObserver(CARD_URL, false,
				cardser);
	}

	public void unregisterContentObserver() {
		mContext.getContentResolver().unregisterContentObserver(cardser);
	}

	private ContentObserver cardser = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			queryTyreData();
		}
	};

	private void init() {
		lfPress = (TextView) tpmsLayout.findViewById(R.id.pressure_fl);
		rfPress = (TextView) tpmsLayout.findViewById(R.id.pressure_fr);
		lrPress = (TextView) tpmsLayout.findViewById(R.id.pressure_bl);
		rrPress = (TextView) tpmsLayout.findViewById(R.id.pressure_br);

		lfTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_fl);
		rfTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_fr);
		lrTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_bl);
		rrTemp = (TextView) tpmsLayout.findViewById(R.id.temperature_br);

		lfBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_fl);
		rfBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_fr);
		lrBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_bl);
		rrBattery = (ImageView) tpmsLayout.findViewById(R.id.battery_br);

		tpmsLayout.findViewById(R.id.tpms_btn).setOnClickListener(this);

		alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(500);
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		alphaAnimation.start();
	}

	// ==================taiya================================
	// zl
	// 温度高于上限的时候判断是否报警
	private void twinkleWd(TextView view, long highstate) {
		if (highstate == NO_NORMAL) {
			startWarnAnimation(view);
		} else {
			stopWarnAnimation(view);
		}
	}

	// 胎压异常的时候判断是否报警
	private void twinkleTy(TextView view, long highstate, long lowstate) {
		if (highstate == NO_NORMAL || lowstate == NO_NORMAL) {
			startWarnAnimation(view);
		} else {
			stopWarnAnimation(view);
		}
	}

	// 判断电量状态，图片闪烁(比较设备状态)
	private void batteryState(ImageView view, long state) {
		if (state == NO_NORMAL) {
			setElectricImage(view, true);
		} else {
			setElectricImage(view, false);
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
		if (view.getCurrentTextColor() != Color.RED) {
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

	@SuppressWarnings("unused")
	public boolean isAppInstalled(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
		/*
		 * final PackageManager packageManager = context.getPackageManager();
		 * List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		 * List<String> pName = new ArrayList<String>(); if (pinfo != null) {
		 * for (int i = 0; i < pinfo.size(); i++) { String pn =
		 * pinfo.get(i).packageName; pName.add(pn); } } return
		 * pName.contains(packageName);
		 */
	}
	
	public static boolean openApplication(Context context, String pkgName) {
		if (TextUtils.isEmpty(pkgName)) {
			Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
			return false;
		}
        try {
    		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
    				pkgName);
    		if (intent == null) {
    			Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
    			return false;
    		}
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		context.startActivity(intent);
    		return true;
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
			return false;
		}

	}

}
