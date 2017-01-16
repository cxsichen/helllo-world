package cn.colink.serialport.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

public class KeyDealer {

	public static final String TXZ_PKG = "com.colink.zzj.txzassistant";
	public static final String TXZ_SERVICE_CLASS = "com.colink.zzj.txzassistant.AssistantService";
	public static final String ACTION_START_TALK = "cn.yunzhisheng.intent.action.START_TALK";
	public static final String ACTION_VOLUMN_CHANGE = "android.media.VOLUME_CHANGED_ACTION";
	public static final String ACTION_MENU_UP = "com.console.MENU_UP";
	public static final String ACTION_MENU_DOWN = "com.console.MENU_DOWN";
	public static final String ACTION_TEL = "com.console.TEL";
	public static final String ACTION_TEL_ANSWER = "com.console.TEL_ANSWER";
	public static final String ACTION_TEL_HANDUP = "com.console.TEL_HANDUP";
	public static final String ACTION_PLAY_PAUSE = "com.console.PLAY_PAUSE";

	public static final String KEYCODE_VOLUME_UP = "com.console.KEYCODE_VOLUME_UP";
	public static final String KEYCODE_VOLUME_DOWN = "com.console.KEYCODE_VOLUME_DOWN";
	public static final String KEYCODE_VOLUME_MUTE = "com.console.KEYCODE_VOLUME_MUTE";
	public static final String APPLISTNAME = "Console_applist_name";

	public static final String KEYCODE_BACK = "com.console.KEYCODE_BACK";
	public static final String KEYCODE_FM = "com.console.KEYCODE_FM";
	public static final String KEYCODE_NAV = "com.console.KEYCODE_NAV";

	static Context context;
	static KeyDealer mKeyDealer;

	public Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			Log.i("cxs", "-------msg.what-------" + msg.what);
			switch (msg.what) {
			case Contacts.K_VOLUP:
				Log.i("cxs", "-------msg.K_VOLUP-------");
				handleVolumeUp();
				break;
			case Contacts.K_VOLDN:
				Log.i("cxs", "-------msg.K_PREV-------");
				handleVolumeDown();
				break;
			case Contacts.K_PREV:
				Log.i("cxs", "-------msg.K_PREV-------");
				handleMenuUp();
				break;
			case Contacts.K_NEXT:
				Log.i("cxs", "-------msg.K_NEXT-------");
				handleMenuDown();
				break;
			case Contacts.TEL:
				Log.i("cxs", "-------msg.TEL-------");
				handleTel();
				break;
			case Contacts.K_AVMUTE:
				Log.i("cxs", "-------msg.K_AVMUTE-------");
				handleMute();
				break;
			case Contacts.SRC:
			case Contacts.MIC:
				ComponentName txz_name = new ComponentName(TXZ_PKG,
						TXZ_SERVICE_CLASS);
				Intent txz = new Intent();
				txz.setComponent(txz_name);
				txz.setAction(ACTION_START_TALK);
				context.startService(txz);
				break;
			case Contacts.K_PHONE_UP:
				Log.i("cxs", "-------msg.K_PHONE_UP-------");
				handleTelAnswer();
				break;
			case Contacts.K_HAND_UP:
				Log.i("cxs", "-------msg.K_HAND_UP-------");
				handleTelHandUp();
				break;
			case Contacts.K_POWER:
				Log.i("cxs", "-------msg.K_POWER-------");
				handlePower();
				break;
			case Contacts.K_MAINMENU:
				Log.i("cxs", "-------msg.K_MAINMENU-------");
				handleMainMenu();
				break;
			case Contacts.K_PALYPAUSE:
				Log.i("cxs", "-------msg.K_PALYPAUSE-------");
				handlePlayPause();
				break;
			case Contacts.K_MEUNDOWN_HANDUP:
				Log.i("cxs", "-------msg.K_MEUNDOWN_HANDUP-------");
				handleMenuDownHandUp();
				break;
			case Contacts.K_MEUNUP_ANSWER:
				Log.i("cxs", "-------msg.K_MEUNUP_ANSWER-------");
				handleMenuUpAnswer();
				break;
			case Contacts.K_BACK:
				Log.i("cxs", "-------msg.K_BACK-------");
				handleBack();
				break;
			case Contacts.K_FM:
				Log.i("cxs", "-------msg.K_FM-------");
				handleFm();
				break;
			case Contacts.K_MUSIC:
				Log.i("cxs", "-------msg.K_MUSIC-------");
				handleMusic();
				break;
			case Contacts.K_NAV:
				Log.i("cxs", "-------msg.K_NAV-------");
				handleNav();
				break;
			default:
				break;
			}
		};
	};

	public KeyDealer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	protected void handlePlayPause() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", ACTION_PLAY_PAUSE);
		context.startService(intent);
	}

	protected void handleMainMenu() {
		try {
			Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);

			mHomeIntent.addCategory(Intent.CATEGORY_HOME);
			mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			context.startActivity(mHomeIntent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void handlePower() {
		try {
			Intent intent = new Intent();
			intent.setClassName("com.console.nodisturb",
					"com.console.nodisturb.MainActivity");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	protected void handleFm() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", KEYCODE_FM);
		context.startService(intent);
	}

	protected void handleMusic() {
		Intent intent = new Intent();
		intent.setClassName("cn.colink.serialport",
				"cn.colink.serialport.service.SerialPortService");
		intent.putExtra("keyEvent", Constact.ACTION_MUSIC_START);
		context.startService(intent);
	}

	protected void handleNav() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", KEYCODE_NAV);
		context.startService(intent);
	}

	protected void handleBack() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", KEYCODE_BACK);
		context.startService(intent);
	}

	protected void handleMute() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", KEYCODE_VOLUME_MUTE);
		context.startService(intent);
	}

	protected void handleVolumeUp() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", KEYCODE_VOLUME_UP);
		context.startService(intent);
	}

	protected void handleVolumeDown() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", KEYCODE_VOLUME_DOWN);
		context.startService(intent);
	}

	protected void handleTelAnswer() {
		Intent intent = new Intent();
		intent.setAction(ACTION_TEL_ANSWER);
		context.sendBroadcast(intent);
	}

	protected void handleTelHandUp() {
		Intent intent = new Intent();
		intent.setAction(ACTION_TEL_HANDUP);
		context.sendBroadcast(intent);
	}

	public Boolean isPhoneCommig() {
		Boolean IsPhoneComming = false;
		String appName = Settings.System.getString(
				context.getContentResolver(), APPLISTNAME);
		Log.i("cxs", "===isPhoneCommig=======appName=" + appName);
		if (appName == null)
			return IsPhoneComming;
		if (appName.contains("com.mtk.bluetooth.PhoneCallActivity")) {
			IsPhoneComming = true;
		}
		return IsPhoneComming;
	}

	protected void handleMenuUp() {

		if (isPhoneCommig()) {
			handleTelAnswer();
		} else {
			if (getMode(context) != 0) {
				Intent intent = new Intent();
				intent.setClassName("com.console.canreader",
						"com.console.canreader.service.CanService");
				intent.putExtra("keyEvent", ACTION_MENU_UP);
				context.startService(intent);
			}
		}

	}

	protected void handleMenuUpAnswer() {
		if (isPhoneCommig()) {
			handleTelAnswer();
		} else {
			Intent intent = new Intent();
			intent.setClassName("com.console.canreader",
					"com.console.canreader.service.CanService");
			intent.putExtra("keyEvent", ACTION_MENU_UP);
			context.startService(intent);
		}
	}

	protected void handleMenuDown() {

		if (isPhoneCommig()) {
			handleTelHandUp();
		} else {
			if (getMode(context) != 0) {
				Intent intent = new Intent();
				intent.setClassName("com.console.canreader",
						"com.console.canreader.service.CanService");
				intent.putExtra("keyEvent", ACTION_MENU_DOWN);
				context.startService(intent);
			}
		}
	}

	protected void handleMenuDownHandUp() {
		if (isPhoneCommig()) {
			handleTelHandUp();
		} else {
			Intent intent = new Intent();
			intent.setClassName("com.console.canreader",
					"com.console.canreader.service.CanService");
			intent.putExtra("keyEvent", ACTION_MENU_DOWN);
			context.startService(intent);
		}
	}

	protected void handleTel() {
		Intent intent = new Intent();
		intent.setAction(ACTION_TEL);
		context.sendBroadcast(intent);
	}

	public static KeyDealer getInstance(Context context) {
		if (mKeyDealer == null) {
			mKeyDealer = new KeyDealer(context);
		}
		return mKeyDealer;
	}

	public void dealCanKeyEvent(Context context, byte[] mPacket) {
		dealWith(context, mPacket);
	}

	long lastSendTime = 0;

	protected void dealWith(Context context, byte[] mPacket) {
		// TODO Auto-generated method stub
		if (mPacket[0] == Contacts.KEY_HEAD) {
			switch (mPacket[4]) {
			case Contacts.K_VOLUP:
				if (System.currentTimeMillis() - lastSendTime > 300) {
					lastSendTime = System.currentTimeMillis();
					mHandler.sendEmptyMessage(Contacts.K_VOLUP);
				}
				break;
			case Contacts.K_VOLDN:
				if (System.currentTimeMillis() - lastSendTime > 300) {
					lastSendTime = System.currentTimeMillis();
					mHandler.sendEmptyMessage(Contacts.K_VOLDN);
				}
				break;
			case Contacts.K_AVMUTE:
				if (System.currentTimeMillis() - lastSendTime > 300) {
					lastSendTime = System.currentTimeMillis();
					mHandler.sendEmptyMessage(Contacts.K_AVMUTE);
				}
				break;
			case Contacts.K_PREV:
				mHandler.removeMessages(Contacts.K_PREV);
				mHandler.sendEmptyMessageDelayed(Contacts.K_PREV, 200);
				break;
			case Contacts.K_NEXT:
				mHandler.removeMessages(Contacts.K_NEXT);
				mHandler.sendEmptyMessageDelayed(Contacts.K_NEXT, 200);
				break;
			case Contacts.TEL:
				mHandler.removeMessages(Contacts.TEL);
				mHandler.sendEmptyMessageDelayed(Contacts.TEL, 200);
				break;
			case Contacts.SRC:
			case Contacts.MIC:
				mHandler.removeMessages(Contacts.MIC);
				mHandler.sendEmptyMessageDelayed(Contacts.MIC, 200);
				break;
			case (byte) Contacts.K_PHONE_UP:
				mHandler.removeMessages(Contacts.K_PHONE_UP);
				mHandler.sendEmptyMessageDelayed(Contacts.K_PHONE_UP, 200);
				break;
			case (byte) Contacts.K_HAND_UP:
				mHandler.removeMessages(Contacts.K_HAND_UP);
				mHandler.sendEmptyMessageDelayed(Contacts.K_HAND_UP, 200);
				break;
			case Contacts.K_POWER:
				mHandler.removeMessages(Contacts.K_POWER);
				mHandler.sendEmptyMessageDelayed(Contacts.K_POWER, 200);
				break;
			case Contacts.K_MAINMENU:
				mHandler.removeMessages(Contacts.K_MAINMENU);
				mHandler.sendEmptyMessageDelayed(Contacts.K_MAINMENU, 200);
				break;
			case Contacts.K_PALYPAUSE:
				mHandler.removeMessages(Contacts.K_PALYPAUSE);
				mHandler.sendEmptyMessageDelayed(Contacts.K_PALYPAUSE, 200);
				break;
			case (byte) Contacts.K_MEUNDOWN_HANDUP:
				mHandler.removeMessages(Contacts.K_MEUNDOWN_HANDUP);
				mHandler.sendEmptyMessageDelayed(Contacts.K_MEUNDOWN_HANDUP,
						200);
				break;
			case (byte) Contacts.K_MEUNUP_ANSWER:
				mHandler.removeMessages(Contacts.K_MEUNUP_ANSWER);
				mHandler.sendEmptyMessageDelayed(Contacts.K_MEUNUP_ANSWER, 200);
				break;
			case (byte) Contacts.K_BACK:
				mHandler.removeMessages(Contacts.K_BACK);
				mHandler.sendEmptyMessageDelayed(Contacts.K_BACK, 200);
				break;
			case (byte) Contacts.K_FM:
				mHandler.removeMessages(Contacts.K_FM);
				mHandler.sendEmptyMessageDelayed(Contacts.K_FM, 200);
				break;
			case (byte) Contacts.K_MUSIC:
				mHandler.removeMessages(Contacts.K_MUSIC);
				mHandler.sendEmptyMessageDelayed(Contacts.K_MUSIC, 200);
				break;
			case (byte) Contacts.K_NAV:
				mHandler.removeMessages(Contacts.K_NAV);
				mHandler.sendEmptyMessageDelayed(Contacts.K_NAV, 200);
				break;
			default:
				break;
			}
		}
	}

	public static final String MODE = "Console_mode";

	public int getMode(Context context) {
		int mode = 4;
		try {
			mode = Settings.System
					.getInt(context.getContentResolver(), MODE, 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mode;
	}

}
