package com.console.launcher_console.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.CoderUtils;
import com.console.launcher_console.util.Constact;
import com.console.launcher_console.util.Contacts;
import com.console.launcher_console.util.PreferenceUtil;
import com.console.launcher_console.util.Trace;

import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;
import cn.kuwo.autosdk.api.KWAPI;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SerialPortControlService extends Service {

	private ISerialPortService mISpService;
	private KWAPI mKwapi;
	public static final String ACTION_STOP_MUSIC = "com.console.STOP_MUSIC";
	int volumeValue;
	/**
	 * 音效设置默认值
	 */
	private static int treValue = 7;
	private static int midValue = 7;
	private static int basValue = 7;
	private static int rowValue = 7;
	private static int colValue = 7;

	private final String[] Applist = { "com.console.radio",
			"cn.kuwo.kwmusiccar", "com.mxtech.videoplayer.pro",
			"com.mtk.bluetooth", "com.console.equalizer", "com.baidu.navi",
			"com.autonavi.amapauto", "com.srtc.pingwang", "com.console.auxapp" }; // 与系统发过来的值对应
	private final String[] modeApplist = { "com.console.radio",
			"cn.kuwo.kwmusiccar", "com.mxtech.videoplayer.pro",
			"com.mtk.bluetooth", "com.console.auxapp", "com.console.equalizer",
			"com.autonavi.amapauto", "com.srtc.pingwang" }; // Acc on后打开的应用的列表

	Boolean Change2FM = false;

	private final int[] Modes = { 0x06, 0x04, 0x05, 0x0B, 0X09, 0x11, 0x0A,
			0x0E };
	private static final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";
	private static final String RADIO_FREQ_ACTION = "action.colink.startFM";
	private static final String SEND_APP_CHANGE = "com.console.sendAppChange";
	private static final String SEND_BACK_CAR_OFF = "com.console.SEND_BACK_CAR_OFF";
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				byte[] packet = (byte[]) msg.obj;
				if (mDataCallback != null) {
					mDataCallback.OnChange(packet);
				}
				// 处理串口发送过来的一部分命令
				dealWithPacket(packet);
				break;
			case Contacts.MSG_RADIO_VALUME_CHANGE:
			/*
			 * if ((PreferenceUtil.getMode(SerialPortControlService.this) == 0)
			 * || (PreferenceUtil .getMode(SerialPortControlService.this) == 4))
			 * //aux和收音模式下使用mcu的音量调节
			 */
			{
				int value = android.provider.Settings.System.getInt(
						getContentResolver(), Constact.KEY_VOLUME_VALUE, 27);
				sendMsg("FD020000" + BytesUtil.intToHexString(value));
			}
				break;
			case Contacts.MSG_RADIO_FREQ_MEG:
				if (PreferenceUtil.getMode(SerialPortControlService.this) == 0
						&& (float) msg.obj != 0)
					sendMsg(getMsgString((int) ((float) msg.obj * 100), 1));
				break;
			case Contacts.MSG_ACCON_MSG:
				/*
				 * acc on 后自动返回之前开启的模式对应的app
				 */
				int parkingState = android.provider.Settings.System.getInt(
						getContentResolver(), Constact.BACK_CAR, 0);
				Log.i("cxs", "======MSG_ACCON_MSG=========parkingState======="
						+ parkingState);
				if (parkingState != 1) {
					int mode = PreferenceUtil
							.getMode(SerialPortControlService.this);
					sendMsg("F5020000" + BytesUtil.intToHexString(mode));
					Log.i("cxs", "======MSG_ACCON_MSG=========mode======="
							+ mode);
					startModeActivty(mode);
				}
				/*
				 * 启动can协议服务
				 */
				Intent canIntent = new Intent(
						"com.console.canreader.service.CanService");
				canIntent.setPackage("com.console.canreader");
				startService(canIntent);
				/*
				 * 发送音效的高低音值
				 */
				sendEquValue();
				break;
			case Contacts.MSG_SEND_FIRST_MSG:
				int launchMode = PreferenceUtil
						.getMode(SerialPortControlService.this);
				sendMsg("F00000" + BytesUtil.intToHexString(launchMode) + "01");
				break;
			case Contacts.MSG_CHECK_MODE:
				if (PreferenceUtil.getMode(SerialPortControlService.this) != PreferenceUtil
						.getCheckMode(SerialPortControlService.this)) {
					/*
					 * Toast.makeText(SerialPortControlService.this,
					 * "     模式错误  现在自动修正。\n 如果还不对应，请重新打开应用",
					 * Toast.LENGTH_LONG).show();
					 */
					sendMsg("F5020000"
							+ BytesUtil.intToHexString(PreferenceUtil
									.getMode(SerialPortControlService.this)));
					mHandler.sendEmptyMessageDelayed(Contacts.MSG_CHECK_MODE,
							2 * 1000);
				}

				break;
			case Contacts.MSG_BACK_CAR: // 处理倒车事件
				Intent intent = new Intent();
				intent.setClassName("com.console.parking",
						"com.console.parking.MainActivity");
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;
			case Contacts.MSG_APP_CHANGE:
				/*
				 * 处理app切换模式命令 0 收音机 1 音乐 2 视频 3 蓝牙 4 aux 5 音效 6 导航 7 行车记录仪
				 */
				mHandler.removeMessages(Contacts.MSG_CHECK_MODE);
				switch (Applist[msg.arg1]) {
				case "com.console.radio":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 0) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 0);
						sendMsg("F5020000" + BytesUtil.intToHexString(0));
						// 暂停音乐
						stopKWMusic();
						// 切换到收音状态
						Settings.System.putInt(getContentResolver(),
								Constact.FMSTATUS, 0);
						Change2FM = true;
						// 切到收音模式的时候 发收音音量值
						int value = android.provider.Settings.System.getInt(
								getContentResolver(),
								Constact.KEY_VOLUME_VALUE, 27);
						sendMsg("FD020000" + BytesUtil.intToHexString(value));

					}
					break;
				case "cn.kuwo.kwmusiccar":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 1) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 1);
						sendMsg("F5020000" + BytesUtil.intToHexString(1));
					}
					break;
				case "com.mxtech.videoplayer.pro":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 2) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 2);
						sendMsg("F5020000" + BytesUtil.intToHexString(2));
						// 暂停音乐
						stopKWMusic();
					}
					break;
				case "com.mtk.bluetooth":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 3) {
						// 暂停音乐
						stopKWMusic();
						PreferenceUtil
								.setMode(SerialPortControlService.this, 3);
						sendMsg("F5020000" + BytesUtil.intToHexString(3));
					}
					break;
				case "com.console.auxapp":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 4) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 4);
						sendMsg("F5020000" + BytesUtil.intToHexString(4));
						// 暂停音乐
						stopKWMusic();
						// 切到AUX模式的时候 发收音音量值 （AUX和收音一个处理模式）
						int value = android.provider.Settings.System.getInt(
								getContentResolver(),
								Constact.KEY_VOLUME_VALUE, 27);
						sendMsg("FD020000" + BytesUtil.intToHexString(value));

					}
					break;
				case "com.console.equalizer":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 5) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 5);
						sendMsg("F5020000" + BytesUtil.intToHexString(5));
					}
					break;
				case "com.baidu.navi":
				case "com.autonavi.amapauto":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 6) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 6);
						sendMsg("F5020000" + BytesUtil.intToHexString(6));
					}
					break;
				case "com.srtc.pingwang":
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 7) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 7);
						sendMsg("F5020000" + BytesUtil.intToHexString(7));
					}
					break;
				default:
					if (PreferenceUtil.getMode(SerialPortControlService.this) != 1) {
						PreferenceUtil
								.setMode(SerialPortControlService.this, 1);
						sendMsg("F5020000" + BytesUtil.intToHexString(1));
					}
					break;
				}
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_CHECK_MODE,
						2 * 1000);
				if (!Change2FM) {
					Settings.System.putInt(getContentResolver(),
							Constact.FMSTATUS, 1);
				}
				Change2FM = false;
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 发送音效的高低音值
	 */
	private void sendEquValue() {
		int[] values = PreferenceUtil.getEquValue(this, basValue, midValue,
				treValue, rowValue, colValue);
		basValue = values[0];
		midValue = values[1];
		treValue = values[2];
		rowValue = values[3];
		colValue = values[4];
		sendMsg(BytesUtil.makeEfMsg(basValue, midValue, treValue, rowValue,
				colValue));
	}

	public String getMsgString(final int freq, int type) {
		byte[] packet = new byte[5];
		packet[0] = Integer.valueOf("F1", 16).byteValue();
		packet[1] = Integer.valueOf("01", 16).byteValue();
		int data3 = freq / 256;
		int data4 = freq - data3 * 256;
		packet[2] = Integer.valueOf("00", 16).byteValue();
		packet[3] = Integer.valueOf(Integer.toHexString(data3), 16).byteValue();
		packet[4] = Integer.valueOf(Integer.toHexString(data4), 16).byteValue();
		return BytesUtil.bytesToHexString(packet);
	}

	private void startModeActivty(int mode) {
		// TODO Auto-generated method stub
		switch (mode) {
		case 1: // 酷我
			mKwapi.startAPP(SerialPortControlService.this, true);
			break;
		case 6:
			startNavi();
			break;
		default:
			openApplication(SerialPortControlService.this, modeApplist[mode]);
			break;
		}

	}

	private void startNavi() {
		// TODO Auto-generated method stub
		int mapType = Settings.System.getInt(getContentResolver(),
				Constact.MAP_INDEX, 0);
		if (mapType == 0) {
			Intent naviIntent = getPackageManager().getLaunchIntentForPackage(
					"com.baidu.navi");
			naviIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(naviIntent);
		} else {
			Intent naviIntent = getPackageManager().getLaunchIntentForPackage(
					"com.autonavi.amapauto");
			naviIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(naviIntent);
		}
	}

	public static boolean openApplication(Context context, String pkgName) {
		if (TextUtils.isEmpty(pkgName)) {
			return false;
		}

		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
				pkgName);
		if (intent == null) {
			return false;
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		return true;
	}

	int[] version = new int[20];

	private void dealWithPacket(byte[] packet) {
		// TODO Auto-generated method stub
		switch (packet[0]) {
		case Contacts.SWITCH_MODE:
			if (packet[1] == Contacts.ZERO && packet[2] == Contacts.ZERO
					&& packet[3] == Contacts.ZERO) {
				if (packet[4] == Contacts.BACK_CAR) {
					// MCU发过来的倒车信号
					mHandler.sendEmptyMessage(Contacts.MSG_BACK_CAR);
				} else if (packet[4] == Contacts.BACK_CAR_OFF) {
					// MCU发过来的倒车结束信号
					Intent intent = new Intent();
					intent.setAction(SEND_BACK_CAR_OFF);
					sendBroadcast(intent);
				} else {
					// MCU发过来的模式校验
					for (int i = 0; i < Modes.length; i++) {
						if (packet[4] == Modes[i]) {
							// PreferenceUtil.setMode(context, i);
							PreferenceUtil.setCheckMode(this, i);
						}
					}
				}
			}
			break;
		case Contacts.BACKLIGHT:// 背光信息
			if (packet[1] == Contacts.ONE && packet[2] == Contacts.ZERO // 大灯开启降低背光
					&& packet[3] == Contacts.ZERO && packet[4] == Contacts.ZERO) {
				changeBackLight(true);

			} else if (packet[1] == Contacts.ZERO && packet[2] == Contacts.ZERO // 大灯关闭恢复背光
					&& packet[3] == Contacts.ZERO && packet[4] == Contacts.ZERO) {
				changeBackLight(false);

			}
			break;
		case Contacts.VERSION_0: // 获取mcu版本号
			version[0] = packet[1] & 0xff;
			version[1] = packet[2] & 0xff;
			version[2] = packet[3] & 0xff;
			version[3] = packet[4] & 0xff;
			Settings.System.putString(getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_1:
			version[4] = packet[1] & 0xff;
			version[5] = packet[2] & 0xff;
			version[6] = packet[3] & 0xff;
			version[7] = packet[4] & 0xff;
			Settings.System.putString(getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_2:
			version[8] = packet[1] & 0xff;
			version[9] = packet[2] & 0xff;
			version[10] = packet[3] & 0xff;
			version[11] = packet[4] & 0xff;
			Settings.System.putString(getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_3:
			version[12] = packet[1] & 0xff;
			version[13] = packet[2] & 0xff;
			version[14] = packet[3] & 0xff;
			version[15] = packet[4] & 0xff;
			Settings.System.putString(getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_4:
			version[16] = packet[1] & 0xff;
			version[17] = packet[2] & 0xff;
			version[18] = packet[3] & 0xff;
			version[19] = packet[4] & 0xff;
			Settings.System.putString(getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		default:
			break;
		}

	}

	/**
	 * @param down
	 *            down 为true 大灯开启降低背光 down 为false 大灯关闭恢复背光
	 */
	private void changeBackLight(Boolean down) {
		// TODO Auto-generated method stub
		if (down) {
			try {
				int value = Settings.System.getInt(getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS, 0);
				Log.i("cxs", "=========SCREEN_BRIGHTNESS=====value=========="
						+ value);
				if (value > Constact.DEFAULT_BRIGHTNESS) {
					Settings.System.putInt(getContentResolver(),
							Constact.USER_SAVE_BRIGHTNESS, value);
					Settings.System.putInt(getContentResolver(),
							Settings.System.SCREEN_BRIGHTNESS,
							Constact.DEFAULT_BRIGHTNESS);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				int value = Settings.System.getInt(getContentResolver(),
						Constact.USER_SAVE_BRIGHTNESS, 0);
				Log.i("cxs",
						"=========USER_SAVE_BRIGHTNESS=====value=========="
								+ value);
				if (value > Constact.DEFAULT_BRIGHTNESS) {
					Settings.System.putInt(getContentResolver(),
							Settings.System.SCREEN_BRIGHTNESS, value);
					Settings.System.putInt(getContentResolver(),
							Constact.USER_SAVE_BRIGHTNESS, 0);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public class MyBinder extends Binder {
		public SerialPortControlService getService() {
			return SerialPortControlService.this;
		}
	}

	private MyBinder myBinder = new MyBinder();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		bindSpService();
		doRegisterReceiver();
		doRegisterContentObserver();
		mKwapi = KWAPI.createKWAPI(this, "auto");

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			unbindService(mServiceConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}

		getContentResolver().unregisterContentObserver(mAccStateObserver);
		getContentResolver().unregisterContentObserver(mTTSShowObserver);
		getContentResolver().unregisterContentObserver(mBackCarObserver);
		getContentResolver().unregisterContentObserver(mAPPObserver);
		getContentResolver().unregisterContentObserver(mValumeObserver);

		super.onDestroy();

		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Intent localIntent = new Intent();
					localIntent.setClass(SerialPortControlService.this,
							SerialPortControlService.class); // 销毁时重新启动Service
					startService(localIntent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		}, 1000 * 10);
	}

	private void doRegisterContentObserver() {
		// TODO Auto-generated method stub
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.ACC_STATE),
				true, mAccStateObserver);
		// 监控语音的状态
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.TTS_SHOW),
				true, mTTSShowObserver);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.BACK_CAR),
				true, mBackCarObserver);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.APPLIST),
				true, mAPPObserver);

		volumeValue = android.provider.Settings.System.getInt(
				getContentResolver(), Constact.KEY_VOLUME_VALUE, 27);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Constact.KEY_VOLUME_VALUE),
						true, mValumeObserver);
	}

	private ValumeObserver mValumeObserver = new ValumeObserver();

	public class ValumeObserver extends ContentObserver {
		public ValumeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int value = android.provider.Settings.System.getInt(
					getContentResolver(), Constact.KEY_VOLUME_VALUE, 27);
			if (value != volumeValue) {
				volumeValue = value;
				Message msg = new Message();
				msg.what = Contacts.MSG_RADIO_VALUME_CHANGE; // 更改收音机音量
				msg.arg1 = volumeValue;
				mHandler.removeMessages(Contacts.MSG_RADIO_VALUME_CHANGE);
				mHandler.sendMessageDelayed(msg, 100);
			}
		}
	}

	private APPObserver mAPPObserver = new APPObserver();

	public class APPObserver extends ContentObserver {
		public APPObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Constact.APPLIST, 0);
			handleAPPChange(state);
		}
	}

	private void handleAPPChange(int state) {
		// TODO Auto-generated method stub
		RADIOWAKE = false;
		AUXWAKE = false;
		EQUWAKE = false;
		Message msg = new Message();
		msg.what = Contacts.MSG_APP_CHANGE;
		msg.arg1 = state;
		mHandler.sendMessage(msg);
	}

	private BackCarObserver mBackCarObserver = new BackCarObserver();

	public class BackCarObserver extends ContentObserver {
		public BackCarObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Constact.BACK_CAR, 0);
			handleBackCar(state);
		}
	}

	private void handleBackCar(int state) {
		// TODO Auto-generated method stub
		if (state == 1) {
			mHandler.sendEmptyMessage(Contacts.MSG_BACK_CAR);
		} else {
			mHandler.removeMessages(Contacts.MSG_BACK_CAR);
		}
	}

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

	private TTSShowObserver mTTSShowObserver = new TTSShowObserver();

	public class TTSShowObserver extends ContentObserver {
		public TTSShowObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Constact.TTS_SHOW, 0);
			handleTTS(state);
		}
	}

	Boolean RADIOWAKE = false;
	Boolean AUXWAKE = false;
	Boolean EQUWAKE = false;

	private void handleTTS(int state) {
		// TODO Auto-generated method stub
		int mode = android.provider.Settings.System.getInt(
				getContentResolver(), Constact.MODE, 0);
		if (state == 1 && mode == 0) { // state=1 语言被唤醒 mode=0 收音界面
			RADIOWAKE = true;
			PreferenceUtil.setMode(this, 1);
			sendMsg(Contacts.MUSIC_MODE);
		}
		if (state == 0 && RADIOWAKE) {
			RADIOWAKE = false;
			PreferenceUtil.setMode(this, 0);
			sendMsg(Contacts.RADIO_MODE);
		}

		if (state == 1 && mode == 4) { // state=1 语言被唤醒 mode=4 AUX界面
			AUXWAKE = true;
			PreferenceUtil.setMode(this, 1);
			sendMsg(Contacts.MUSIC_MODE);
		}
		if (state == 0 && AUXWAKE) {
			AUXWAKE = false;
			PreferenceUtil.setMode(this, 4);
			sendMsg(Contacts.AUX_MODE);
		}

		if (state == 1 && mode == 5) { // state=1 语言被唤醒 mode=5 音效界面
			EQUWAKE = true;
			PreferenceUtil.setMode(this, 1);
			sendMsg(Contacts.MUSIC_MODE);
		}
		if (state == 0 && EQUWAKE) {
			EQUWAKE = false;
			PreferenceUtil.setMode(this, 5);
			sendMsg(Contacts.EQUALIZER_MODE);
		}
	}

	private AccStateObserver mAccStateObserver = new AccStateObserver();

	public class AccStateObserver extends ContentObserver {
		public AccStateObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Constact.ACC_STATE, 0);
			handleAccState(state);
		}
	}

	private void handleAccState(int state) {
		// TODO Auto-generated method stub
		if (state == 1) {
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_ACCON_MSG, 1000);
		}
	}

	private void stopKWMusic() {
		Intent stopMusicIntent = new Intent(ACTION_STOP_MUSIC);
		sendBroadcast(stopMusicIntent);
	}

	private void doRegisterReceiver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(BOOT_COMPLETED_ACTION);
		filter.addAction(RADIO_FREQ_ACTION);
		registerReceiver(myReceiver, filter);
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()) {
			case BOOT_COMPLETED_ACTION:
				Message msg = new Message();
				msg.what = Contacts.MSG_SEND_FIRST_MSG;
				mHandler.sendMessage(msg);
				break;
			case RADIO_FREQ_ACTION:
				float freq = intent.getFloatExtra("fm_fq", 0);
				Message freqmsg = new Message();
				freqmsg.what = Contacts.MSG_RADIO_FREQ_MEG;
				freqmsg.obj = freq;
				mHandler.sendMessageDelayed(freqmsg, 0);
				break;
			default:
				break;
			}
		}
	};

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				Trace.i("Sound MainActivity sendMsg");
				mISpService.sendDataToSp(BytesUtil.addCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private ISerialPortCallback mICallback = new ISerialPortCallback.Stub() {

		@Override
		public void readDataFromServer(byte[] bytes) throws RemoteException {
			Trace.i("readDataFromServer" + bytes);
			Message msg = new Message();
			msg.what = Contacts.MSG_UPDATA_UI;
			msg.obj = bytes;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Trace.i("onServiceConnected");
			mISpService = ISerialPortService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	public void bindSpService() {
		if (mISpService == null) {
			Intent intent = new Intent();
			intent.setClassName("cn.colink.serialport",
					"cn.colink.serialport.service.SerialPortService");
			bindService(intent, mServiceConnection,
					android.content.Context.BIND_AUTO_CREATE);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	public interface DataCallback {
		public void OnChange(byte[] value);
	}

	DataCallback mDataCallback;

	public void setDataCallback(DataCallback dataCallback) {
		this.mDataCallback = dataCallback;
	}

}
