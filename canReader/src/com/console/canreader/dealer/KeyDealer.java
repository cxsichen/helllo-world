package com.console.canreader.dealer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

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
	public static final String ACTION_MENU_LONG_UP = "com.console.MENU_LONG_UP";
	public static final String ACTION_MENU_LONG_DOWN = "com.console.MENU_LONG_DOWN";
	
	//音量加减和mute由这里处理，其他发到外面处理
	public static final String KEYCODE_VOLUME_UP = "com.console.KEYCODE_VOLUME_UP";
	public static final String KEYCODE_VOLUME_DOWN = "com.console.KEYCODE_VOLUME_DOWN";
	public static final String KEYCODE_VOLUME_MUTE = "com.console.KEYCODE_VOLUME_MUTE";
	//音量加减和mute由这里处理，其他发到外面处理
	public static final String ACTION_RAIDO_VOL_DOWN = "com.console.RAIDO_VOL_DOWN";
	public static final String ACTION_RAIDO_VOL_UP = "com.console.RAIDO_VOL_UP";

	private final static int SETP_VOLUME = 1;
	private static final int MAX_ALARM_VOICE = 15;
	private final int MAX_CALL_VOICE = 15;
	private static AudioManager mAudioManager;
	int cur_music;
	static int save_music = 0;
	static Context context;
	static KeyDealer mKeyDealer;
	ValumeObserver mValumeObserver = new ValumeObserver();

	public Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.VOL_UP:
				Log.i("cxs", "-----1111--msg.VOL_UP-------");
				if (mAudioManager == null)
		    	mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
				cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				handleVolume(context, cur_music + SETP_VOLUME);
				break;
			case Contacts.VOL_DOWN:
				Log.i("cxs", "-------msg.VOL_DOWN-------");
				if (mAudioManager == null)
	    		mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
				cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				handleVolume(context, cur_music - SETP_VOLUME);
				break;
			case Contacts.MENU_UP:
				Log.i("cxs", "-------msg.MENU_UP-------");
				handleMenuUp();
				break;
			case Contacts.MENU_DOWN:
				Log.i("cxs", "-------msg.MENU_DOWN-------");
				handleMenuDown();
				break;
			case Contacts.TEL:
				Log.i("cxs", "-------msg.TEL-------");
				handleTel();
				break;
			case Contacts.MUTE:
				Log.i("cxs", "-------msg.MUTE-------");
				handleMute();
				break;
			case Contacts.SRC:
			case Contacts.MIC:
				Log.i("cxs", "-------msg.MIC-------");
				ComponentName txz_name = new ComponentName(TXZ_PKG,
						TXZ_SERVICE_CLASS);
				Intent txz = new Intent();
				txz.setComponent(txz_name);
				txz.setAction(ACTION_START_TALK);
				context.startService(txz);
				break;
			case Contacts.TEL_ANSWER:
				Log.i("cxs", "-------msg.TEL_ANSWER-------");
				handleTelAnswer();
				break;
			case Contacts.TEL_HANDUP:
				Log.i("cxs", "-------msg.TEL_HANDUP-------");
				handleTelHandUp();
				break;
			case Contacts.MENU_LONG_UP:
				Log.i("cxs", "-------msg.MENU_LONG_UP-------");
				handleMenuLongUp();
				break;
			case Contacts.MENU_LONG_DOWN:
				Log.i("cxs", "-------msg.MENU_LONG_DOWN-------");
				handleMenuLongDown();
				break;
			default:
				break;
			}
		};
	};

	public KeyDealer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		context.registerReceiver(mValumeObserver, new IntentFilter(
				ACTION_VOLUMN_CHANGE));
		doRegisterReceiver();
	}

	// 监听物理加减音量键
	private void doRegisterReceiver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(KEYCODE_VOLUME_UP);
		filter.addAction(KEYCODE_VOLUME_DOWN);
		filter.addAction(KEYCODE_VOLUME_MUTE);
		context.registerReceiver(myReceiver, filter);
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("cxs1","----------myReceiver----------"+intent.getAction());
			switch (intent.getAction()) {
			case KEYCODE_VOLUME_UP:
				mHandler.sendEmptyMessageDelayed(Contacts.VOL_UP, 0);
				break;
			case KEYCODE_VOLUME_DOWN:
				mHandler.sendEmptyMessageDelayed(Contacts.VOL_DOWN, 0);
				break;
			case KEYCODE_VOLUME_MUTE:
				mHandler.sendEmptyMessageDelayed(Contacts.MUTE, 0);
				break;
			default:
				break;
			}
		}
	};

	private class ValumeObserver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (action.equals(ACTION_VOLUMN_CHANGE)) {

				if (mAudioManager == null)
					mAudioManager = (AudioManager) context
							.getSystemService(Context.AUDIO_SERVICE);
				cur_music = mAudioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				
				//mute相关
				if (cur_music > 0) {
					save_music = 0;
				}
			}
		}
	}

	protected void handleRadioVolUP() {
		int valume = Settings.System.getInt(context.getContentResolver(),
				Contacts.KEY_VOLUME_VALUE, 0);
		valume = (valume + 1) > 48 ? 48 : (valume + 1);
		Settings.System.putInt(context.getContentResolver(),
				Contacts.KEY_VOLUME_VALUE, valume);
	}

	protected void handleRadioVolDown() {
		int valume = Settings.System.getInt(context.getContentResolver(),
				Contacts.KEY_VOLUME_VALUE, 0);
		valume = (valume - 1) < 0 ? 0 : (valume - 1);
		Settings.System.putInt(context.getContentResolver(),
				Contacts.KEY_VOLUME_VALUE, valume);
	}

	protected void handleMenuLongDown() {
		Intent intent = new Intent();
		intent.setAction(ACTION_MENU_LONG_DOWN);
		context.sendBroadcast(intent);
	}

	protected void handleMenuLongUp() {
		Intent intent = new Intent();
		intent.setAction(ACTION_MENU_LONG_UP);
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
		cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		if (cur_music > 0) {
			save_music = cur_music;
			handleVolume(context, 0);
		} else if (cur_music == 0 && save_music != 0) {
			handleVolume(context, save_music);
		}
	}

	public static KeyDealer getInstance(Context context) {
		if (mKeyDealer == null) {
			mKeyDealer = new KeyDealer(context);
		}
		return mKeyDealer;
	}

	public void dealCanKeyEvent(Context context, CanInfo mCanInfo) {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		dealWith(context, mCanInfo);
	}

	long lastSendTime = 0;
	// PRESSFREE为false表示未收到按键释放的事件 这时候只有menu长按可以响应
	Boolean PRESSFREE = true;

	protected void dealWith(Context context, CanInfo canInfo) {
		// TODO Auto-generated method stub

		if (canInfo.STEERING_BUTTON_STATUS == 0) {			
			mHandler.removeMessages(Contacts.MENU_LONG_UP);
			mHandler.removeMessages(Contacts.MENU_LONG_DOWN);
			PRESSFREE = true;
		}
		if (canInfo.STEERING_BUTTON_STATUS > 0 && PRESSFREE) {
			PRESSFREE = false;
			switch (canInfo.STEERING_BUTTON_MODE) {
			case Contacts.VOL_UP:
				if (System.currentTimeMillis() - lastSendTime > 200) {
					lastSendTime = System.currentTimeMillis();
					mHandler.sendEmptyMessage(Contacts.VOL_UP);
				}
				break;
			case Contacts.VOL_DOWN:
				if (System.currentTimeMillis() - lastSendTime > 200) {
					lastSendTime = System.currentTimeMillis();
					mHandler.sendEmptyMessage(Contacts.VOL_DOWN);
				}
				break;
			case Contacts.MUTE:
				if (System.currentTimeMillis() - lastSendTime > 200) {
					lastSendTime = System.currentTimeMillis();
					mHandler.sendEmptyMessage(Contacts.MUTE);
				}
				break;
			case Contacts.MENU_UP:
				mHandler.removeMessages(Contacts.MENU_UP);
				mHandler.sendEmptyMessageDelayed(Contacts.MENU_UP, 100);
				mHandler.sendEmptyMessageDelayed(Contacts.MENU_LONG_UP, 600);

				break;
			case Contacts.MENU_DOWN:
				mHandler.removeMessages(Contacts.MENU_DOWN);
				mHandler.sendEmptyMessageDelayed(Contacts.MENU_DOWN, 100);
				mHandler.sendEmptyMessageDelayed(Contacts.MENU_LONG_DOWN, 600);
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
			case Contacts.TEL_ANSWER:
				Log.i("cxs","======dealWith==TEL_ANSWER=====");
				mHandler.removeMessages(Contacts.TEL_ANSWER);
				mHandler.sendEmptyMessageDelayed(Contacts.TEL_ANSWER, 200);
				break;
			case Contacts.TEL_HANDUP:
				mHandler.removeMessages(Contacts.TEL_HANDUP);
				mHandler.sendEmptyMessageDelayed(Contacts.TEL_HANDUP, 200);
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
		mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,
				currVolume, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_RING,
				currVolume*2> 15?15:currVolume*2, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				currVolume > 15 ? 15 : currVolume, 1);
		mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				currVolume, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, currVolume, 0);
		mAudioManager.setStreamVolume(6,currVolume,0);         //蓝牙声音

		Settings.System.putInt(context.getContentResolver(),
				Contacts.KEY_VOLUME_VALUE, currVolume * 3);
	}

}
