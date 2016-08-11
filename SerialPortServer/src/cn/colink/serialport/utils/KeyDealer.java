package cn.colink.serialport.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
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


	private final static int SETP_VOLUME = 1;
	private static final int MAX_ALARM_VOICE = 15;
	private final int MAX_CALL_VOICE = 15;
	private static AudioManager mAudioManager;
	int cur_alarm;
	static int save_alarm = 0;
	static Context context;
	static KeyDealer mKeyDealer;
	ValumeObserver mValumeObserver = new ValumeObserver();

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
			case Contacts.K_PHONE_DN:
				Log.i("cxs", "-------msg.K_PHONE_DN-------");
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
			default:
				break;
			}
		};
	};

	public KeyDealer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		context.registerReceiver(mValumeObserver, new IntentFilter(
				ACTION_VOLUMN_CHANGE));
	}

	private class ValumeObserver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (action.equals(ACTION_VOLUMN_CHANGE)) {
				cur_alarm = mAudioManager
						.getStreamVolume(AudioManager.STREAM_ALARM);
				if (cur_alarm > 0) {
					save_alarm = 0;
				}
			}
		}
	}
	
	protected void handlePlayPause() {
		Intent intent = new Intent();
		intent.setAction(ACTION_PLAY_PAUSE);
		context.sendBroadcast(intent);
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
			intent.setClassName("com.console.nodisturb", "com.console.nodisturb.MainActivity");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	protected void handleVolumeUp() {
		Intent intent = new Intent();
		intent.setAction(KEYCODE_VOLUME_UP);
		context.sendBroadcast(intent);
	}
	
	
	protected void handleVolumeDown() {
		Intent intent = new Intent();
		intent.setAction(KEYCODE_VOLUME_DOWN);
		context.sendBroadcast(intent);
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

	protected void handleMenuUp() {
		Intent intent = new Intent();
		intent.setAction(ACTION_MENU_UP);
		context.sendBroadcast(intent);
	}

	protected void handleMenuDown() {
		Intent intent = new Intent();
		intent.setAction(ACTION_MENU_DOWN);
		context.sendBroadcast(intent);
	}

	protected void handleTel() {
		Intent intent = new Intent();
		intent.setAction(ACTION_TEL);
		context.sendBroadcast(intent);
	}

	protected void handleMute() {
		// TODO Auto-generated method stub
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_alarm = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
		if (cur_alarm > 0) {
			save_alarm = cur_alarm;
			handleVolume(context, 0);
		} else if (cur_alarm == 0 && save_alarm != 0) {
			handleVolume(context, save_alarm);
		}
	}

	public static KeyDealer getInstance(Context context) {
		if (mKeyDealer == null) {
			mKeyDealer = new KeyDealer(context);
		}
		return mKeyDealer;
	}

	public void dealCanKeyEvent(Context context, byte[] mPacket) {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_alarm = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
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
			case (byte) Contacts.K_PHONE_DN:
				mHandler.removeMessages(Contacts.K_PHONE_DN);
				mHandler.sendEmptyMessageDelayed(Contacts.K_PHONE_DN, 200);
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
			default:
				break;
			}
		}
	}

	private void handleVolume(Context context, int value) {
		int currVolume = value;
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		if (currVolume <= 0) {
			currVolume = 0;
		}

		if (currVolume > MAX_ALARM_VOICE) {
			currVolume = MAX_ALARM_VOICE;
		}
		cur_alarm = currVolume;
		mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,
				currVolume, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				currVolume > 15 ? 15 : currVolume, 1);
		mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				currVolume, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, currVolume, 0);
	}

}
