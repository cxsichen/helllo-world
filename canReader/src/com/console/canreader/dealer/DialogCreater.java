package com.console.canreader.dealer;

import java.lang.reflect.Field;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.view.AirConDialog;
import com.console.canreader.view.UnlockWaringDialog;

public class DialogCreater {

	private static AirConDialog airCondialog;
	private static UnlockWaringDialog unlockWaringDialog;
	public static final String CAN_WARNING = "com.console.canWaring";
	public static final String BATTRYT_WARNING = "com.console.battery.canWaring";
	public static final String FUEL_CAN_WARNING = "com.console.fuel.canWaring";
	public static final String HANDBRAKE_WARNING = "com.console.handbrake.canWaring";
	public static final String SEATBELT_WARNING = "com.console.seatbelt.canWaring";
	public static final String WASHER_WARNING = "com.console.wahser.canWaring";

	/*-------------------显示车门报警  start--------------------*/
	public static void showUnlockWaringDialog(Context context, CanInfo canInfo) {
		// TODO Auto-generated method stub
		if (canInfo.RIGHT_BACKDOOR_STATUS == 1
				|| canInfo.LEFT_BACKDOOR_STATUS == 1
				|| canInfo.RIGHT_FORONTDOOR_STATUS == 1
				|| canInfo.LEFT_FORONTDOOR_STATUS == 1
				|| canInfo.TRUNK_STATUS == 1 || canInfo.HOOD_STATUS == 1) {

			if (unlockWaringDialog == null) {
				unlockWaringDialog = new UnlockWaringDialog(context,
						R.style.MyDialog);
				unlockWaringDialog.getWindow().setType(
						WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

				Window dialogWindow = unlockWaringDialog.getWindow();
				dialogWindow.setGravity(Gravity.CENTER);
				// unlockWaringDialog.setCancelable(false);
			}

			if (!unlockWaringDialog.isShowing()) {
				unlockWaringDialog.show();
			}

			unlockWaringDialog.setCanInfo(canInfo);
		}
		if (unlockWaringDialog != null) {
			if (unlockWaringDialog.isShowing()) {
				unlockWaringDialog.setCanInfo(canInfo);
			}
		}
	}

	/*-------------------显示车门报警  end--------------------*/

	/*-------------------处理360全景 start--------------------*/
	public static void dealPanoramaEvent(Context context, CanInfo canInfo) {
		if (canInfo.PANORAMA_STATUS == 1) {
			if (android.provider.Settings.System.getInt(
					context.getContentResolver(), Contacts.BACK_CAR, 1) != 1) {
				android.provider.Settings.System.putInt(
						context.getContentResolver(), Contacts.BACK_CAR, 1);
			}
		} else if (canInfo.PANORAMA_STATUS == 0) {
			if (android.provider.Settings.System.getInt(
					context.getContentResolver(), Contacts.BACK_CAR, 0) != 0) {
				android.provider.Settings.System.putInt(
						context.getContentResolver(), Contacts.BACK_CAR, 0);
			}
		}
	}

	/*-------------------处理360全景  end--------------------*/

	/*-------------------显示车身信息报警  start--------------------*/
	static int[] waringStatus = { 0, 0, 0, 0, 0 };
	private static final String WARNSTART = "warn_start";

	public static void showUnlockWaringInfo(Context context, CanInfo canInfo) {
		if (canInfo.FUEL_WARING_SIGN != waringStatus[0]
				|| canInfo.BATTERY_WARING_SIGN != waringStatus[1]
				|| canInfo.SAFETY_BELT_STATUS != waringStatus[2]
				|| canInfo.DISINFECTON_STATUS != waringStatus[3]
				|| canInfo.HANDBRAKE_STATUS != waringStatus[4]) {

			waringStatus[0] = canInfo.FUEL_WARING_SIGN;
			waringStatus[1] = canInfo.BATTERY_WARING_SIGN;
			waringStatus[2] = canInfo.SAFETY_BELT_STATUS;
			waringStatus[3] = canInfo.DISINFECTON_STATUS;
			waringStatus[4] = canInfo.HANDBRAKE_STATUS;

			android.provider.Settings.System.putInt(
					context.getContentResolver(), BATTRYT_WARNING,
					canInfo.BATTERY_WARING_SIGN);
			android.provider.Settings.System.putInt(
					context.getContentResolver(), FUEL_CAN_WARNING,
					canInfo.FUEL_WARING_SIGN);
			android.provider.Settings.System.putInt(
					context.getContentResolver(), HANDBRAKE_WARNING,
					canInfo.HANDBRAKE_STATUS);
			android.provider.Settings.System.putInt(
					context.getContentResolver(), SEATBELT_WARNING,
					canInfo.SAFETY_BELT_STATUS);
			android.provider.Settings.System.putInt(
					context.getContentResolver(), WASHER_WARNING,
					canInfo.DISINFECTON_STATUS);
			/*
			 * int temp=android.provider.Settings.System.getInt(context.
			 * getContentResolver(), CAN_WARNING, 0);
			 * android.provider.Settings.System
			 * .putInt(context.getContentResolver(), CAN_WARNING, temp==0?1:0);
			 */
			Intent intent = new Intent(CAN_WARNING);
			context.sendBroadcast(intent);
			/*
			 * for (int i = 0; i < waringStatus.length; i++) { if
			 * (waringStatus[i] == 1) {
			 * 
			 * String topActivty = getTopActivity(context); if (!topActivty
			 * .equals("com.console.canreader")) {
			 * Settings.System.putInt(context.getContentResolver(), WARNSTART,
			 * 1); Intent intent = new Intent(); intent.setClassName(context,
			 * "com.console.canreader.activity.CarInfoWaringActivity");
			 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 * context.startActivity(intent); } } }
			 */
		}
	}

	public static void clearUnlockWaringData(Context context) {
		android.provider.Settings.System.putInt(context.getContentResolver(),
				BATTRYT_WARNING, 0);
		android.provider.Settings.System.putInt(context.getContentResolver(),
				FUEL_CAN_WARNING, 0);
		android.provider.Settings.System.putInt(context.getContentResolver(),
				HANDBRAKE_WARNING, 0);
		android.provider.Settings.System.putInt(context.getContentResolver(),
				SEATBELT_WARNING, 0);
		android.provider.Settings.System.putInt(context.getContentResolver(),
				WASHER_WARNING, 0);
		Intent intent = new Intent(CAN_WARNING);
		context.sendBroadcast(intent);
		for (int i = 0; i < waringStatus.length; i++) {
			waringStatus[i] = 0;
		}
		
		//浮动窗
		if(mFloatLayout!=null){
			mWindowManager.removeView(mFloatLayout);
			mFloatLayout=null;
		}
	}

	/*-------------------显示车身信息报警  end--------------------*/
	public static String getTopActivity(Context context) {
		if (context == null) {
			return null;
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		if (am == null) {
			return null;
		}
		if (Build.VERSION.SDK_INT <= 20) {
			List<RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (tasks != null && !tasks.isEmpty()) {
				ComponentName componentName = tasks.get(0).topActivity;
				if (componentName != null) {
					return componentName.getClassName();
				}
			}
		} else {
			RunningAppProcessInfo currentInfo = null;
			Field field = null;
			int START_TASK_TO_FRONT = 2;
			int PROCESS_STATE_PERSISTENT_UI = 1;
			String pkgName = null;
			try {
				field = RunningAppProcessInfo.class
						.getDeclaredField("processState");
			} catch (Exception e) {
				return null;
			}
			List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();
			if (appList == null || appList.isEmpty()) {
				return null;
			}
			for (RunningAppProcessInfo app : appList) {
				if (app != null
						&& app.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Integer state = null;
					try {
						state = field.getInt(app);
					} catch (Exception e) {
						return null;
					}
					if (state != null
							&& (state == START_TASK_TO_FRONT || state == PROCESS_STATE_PERSISTENT_UI)) {
						currentInfo = app;
						break;
					}
				}
			}
			if (currentInfo != null) {
				pkgName = currentInfo.processName;
			}
			return pkgName;
		}
		return null;
	}

	/*-------------------显示空调界面  start--------------------*/
	static int[] airConStatus = { -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1,
			-1, 0, 0, 0, 0, 0 };
	static float[] airConStatusFloat = { 0f, 0f, 0f };

	public static void showAirConDialog(Context context, CanInfo canInfo,
			CallBack mCallBack) {
		// TODO Auto-generated method stub
		if (canInfo.AIR_CONDITIONER_STATUS != airConStatus[0]
				|| canInfo.AC_INDICATOR_STATUS != airConStatus[1]
				|| canInfo.CYCLE_INDICATOR != airConStatus[2]
				|| canInfo.LARGE_LANTERN_INDICATOR != airConStatus[3]
				|| canInfo.SMALL_LANTERN_INDICATOR != airConStatus[4]
				|| canInfo.MAX_FRONT_LAMP_INDICATOR != airConStatus[5]
				|| canInfo.REAR_LAMP_INDICATOR != airConStatus[6]
				|| canInfo.UPWARD_AIR_INDICATOR != airConStatus[7]
				|| canInfo.PARALLEL_AIR_INDICATOR != airConStatus[8]
				|| canInfo.DOWNWARD_AIR_INDICATOR != airConStatus[9]
				|| canInfo.AIR_RATE != airConStatus[10]
				|| (float) canInfo.DRIVING_POSITON_TEMP != airConStatusFloat[0]
				|| (float) canInfo.DEPUTY_DRIVING_POSITON_TEMP != airConStatusFloat[1]
				/*
				 * || (float) canInfo.OUTSIDE_TEMPERATURE !=
				 * airConStatusFloat[2]
				 */
				|| canInfo.LEFT_SEAT_TEMP != airConStatus[13]
				|| canInfo.RIGTHT_SEAT_TEMP != airConStatus[14]) {

			airConStatus[0] = canInfo.AIR_CONDITIONER_STATUS;
			airConStatus[1] = canInfo.AC_INDICATOR_STATUS;
			airConStatus[2] = canInfo.CYCLE_INDICATOR;
			airConStatus[3] = canInfo.LARGE_LANTERN_INDICATOR;
			airConStatus[4] = canInfo.SMALL_LANTERN_INDICATOR;

			airConStatus[5] = canInfo.MAX_FRONT_LAMP_INDICATOR;
			airConStatus[6] = canInfo.REAR_LAMP_INDICATOR;
			airConStatus[7] = canInfo.UPWARD_AIR_INDICATOR;
			airConStatus[8] = canInfo.PARALLEL_AIR_INDICATOR;
			airConStatus[9] = canInfo.DOWNWARD_AIR_INDICATOR;

			airConStatus[10] = canInfo.AIR_RATE;
			airConStatusFloat[0] = (float) canInfo.DRIVING_POSITON_TEMP;
			airConStatusFloat[1] = (float) canInfo.DEPUTY_DRIVING_POSITON_TEMP;
			/* airConStatusFloat[2] = (float) canInfo.OUTSIDE_TEMPERATURE; */
			airConStatus[13] = canInfo.LEFT_SEAT_TEMP;
			airConStatus[14] = canInfo.RIGTHT_SEAT_TEMP;

			if (airCondialog != null) {
				if (!airCondialog.isShowing()) {
					mCallBack.sendShowMsg();
					airCondialog.show();
				}
			}

			if (airCondialog != null)
				airCondialog.setCanInfo(canInfo);

			if (airCondialog == null) {
				airCondialog = new AirConDialog(context, R.style.MyDialog);
				airCondialog.getWindow().setType(
						WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

				Window dialogWindow = airCondialog.getWindow();
				dialogWindow.setGravity(Gravity.BOTTOM);// 显示在底部
			}
		}

	}

	/*-------------------显示空调界面  end--------------------*/
	public static void closeAllWaringDialog() {
		if (airCondialog != null) {
			if (airCondialog.isShowing()) {
				airCondialog.dismiss();
			}
		}
		if (unlockWaringDialog != null) {
			if (unlockWaringDialog.isShowing()) {
				unlockWaringDialog.dismiss();
			}
		}

	}

	public interface CallBack {
		void sendShowMsg();
	}

	private static Dialog CarInfoWaringAlertDialog;

	public static void showCarInfoWaring(Context mContext, CanInfo canInfo) {

		try {
			if (CarInfoWaringAlertDialog != null) {
				CarInfoWaringAlertDialog.cancel();
			}
			String warning_id = BytesUtil.intToHexString_4(canInfo.WARNING_ID);
			Log.i("xxx", "warning_id==" + warning_id);
			int i = mContext.getResources().getIdentifier(
					"warning_" + warning_id, "string",
					mContext.getPackageName());

			CarInfoWaringAlertDialog = new AlertDialog.Builder(mContext)
					.setTitle("警告")
					.setMessage(mContext.getResources().getString(i)).create();
			CarInfoWaringAlertDialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

			CarInfoWaringAlertDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*-------------------显示浮窗报警  start--------------------*/
	// 定义浮动窗口布局
	private static RelativeLayout mFloatLayout;
	private static WindowManager.LayoutParams wmParams;
	// 创建浮动窗口设置布局参数的对象
	private static WindowManager mWindowManager;
	private static TextView mWmTextView;
	private static ImageView mWmImageView;
	private static String floatWaringStr="";

	@SuppressWarnings("static-access")
	public static void showFloatCarInfoWaring(Context mContext, CanInfo canInfo) {
		if(!floatWaringStr.equals(canInfo.WARING_MSG)){
			floatWaringStr=canInfo.WARING_MSG;
		}else{
			return;
		}
		
		if (mFloatLayout == null||mWindowManager==null||mWmTextView==null||mWmImageView==null) {
			wmParams = new WindowManager.LayoutParams();
			// 通过getApplication获取的是WindowManagerImpl.CompatModeWrapper
			mWindowManager = (WindowManager) mContext
					.getSystemService(mContext.WINDOW_SERVICE);
			// 设置window type
			wmParams.type = LayoutParams.TYPE_PHONE;
			// 设置图片格式，效果为背景透明
			wmParams.format = PixelFormat.RGBA_8888;
			// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
			wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
			// 调整悬浮窗显示的停靠位置为左侧置顶
			wmParams.gravity = Gravity.LEFT | Gravity.TOP;

			// 设置悬浮窗口长宽数据
			wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
			wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

			LayoutInflater inflater = LayoutInflater.from(mContext);
			mFloatLayout = (RelativeLayout) inflater.inflate(
					R.layout.alert_window_menu, null);
			// 浮动窗口按钮
			mWmTextView = (TextView) mFloatLayout
					.findViewById(R.id.alert_window_tv);
			mWmImageView = (ImageView) mFloatLayout
					.findViewById(R.id.alert_window_iv);

			mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
					.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			// 以屏幕左上角为原点，设置x、y初始值，相对于gravity
			wmParams.x = (int) 1000 - mFloatLayout.getMeasuredWidth() / 2;
			wmParams.y = (int) 550 - mFloatLayout.getMeasuredHeight() / 2;
			// 添加mFloatLayout
			mWindowManager.addView(mFloatLayout, wmParams);
			
			mWmImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mFloatLayout!=null){
						mWindowManager.removeView(mFloatLayout);
						mFloatLayout=null;
					}
				}
			});
		}
		// 获取浮动窗口视图所在布局
		mWmTextView.setText(canInfo.WARING_MSG);
		mWmTextView.setTextColor(canInfo.WARING_MSG_STATUS == 1 ? Color.RED
				: Color.YELLOW);


	}

	/*-------------------显示浮窗报警  end--------------------*/

}
