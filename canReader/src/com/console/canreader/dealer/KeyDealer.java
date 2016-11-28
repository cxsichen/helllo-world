package com.console.canreader.dealer;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.console.canreader.R;
import com.console.canreader.activity.CarInfoActivity;
import com.console.canreader.activity.MenuAcAcitivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

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
	public static final String ACTION_MUSIC_START = "com.console.MUSIC_START";
	public static final String ACTION_CLOSE_AUX= "com.console.CLOSE_AUX";
	public static final String ACTION_PLAY_PAUSE = "com.console.PLAY_PAUSE";
	public static final String RADIO_FREQ_ACTION = "action.colink.startFM";

	// 音量加减和mute由这里统一处理，其他发到外面处理
	public static final String KEYCODE_VOLUME_UP = "com.console.KEYCODE_VOLUME_UP";
	public static final String KEYCODE_VOLUME_DOWN = "com.console.KEYCODE_VOLUME_DOWN";
	public static final String KEYCODE_VOLUME_MUTE = "com.console.KEYCODE_VOLUME_MUTE";
	// 音量加减和mute由这里统一处理，其他发到外面处理
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
				handleVolUp();
				break;
			case Contacts.VOL_DOWN:
				Log.i("cxs", "-------msg.VOL_DOWN-------");
				handleVolDown();
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
			case Contacts.KEYEVENT.POWER:
				Log.i("cxs", "-------msg.POWER-------");
				handlePower();
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
			case Contacts.KEYEVENT.CANINFOPAGE:
				Log.i("cxs", "-------msg.CANINFOPAGE-------");
				handleCanInfoPage();
				break;
			case Contacts.KEYEVENT.FM_AM:
				Log.i("cxs", "-------Contacts.KEYEVENT.FM_AM-------");
				handleFmAm();
				break;
			case Contacts.KEYEVENT.MUSIC:
				Log.i("cxs", "-------Contacts.KEYEVENT.MUSIC-------");
				handleMUSIC();
				break;
			case Contacts.KEYEVENT.PHONE_APP:
				Log.i("cxs", "-------Contacts.KEYEVENT.PHONE_APP-------");
				handlePHONE_APP();
				break;
			case Contacts.KEYEVENT.HOME:
				Log.i("cxs", "-------Contacts.KEYEVENT.HOME-------");
				handleHOME();
				break;
			case Contacts.KEYEVENT.MAP:
				Log.i("cxs", "-------Contacts.KEYEVENT.MAP-------");
				handleMAP();
				break;
			case Contacts.KEYEVENT.AUX:
				Log.i("cxs", "-------Contacts.KEYEVENT.AUX-------");
				handleAUX();
				break;
			case Contacts.KEYEVENT.CLOSEAUX:
				Log.i("cxs", "-------Contacts.KEYEVENT.CLOSEAUX-------");
				handleCLOSEAUX();
				break;
			case Contacts.KEYEVENT.MUSIC_PLAY_PAUSE:
				Log.i("cxs", "-------Contacts.KEYEVENT.MUSIC_PLAY_PAUSE-------");
				handleMUSIC_PLAY_PAUSE();
				break;
			case Contacts.KEYEVENT.KNOBVOLUME:
				Log.i("cxs", "-------Contacts.KEYEVENT.KNOBVOLUME-------");
				handleKnobVolume(msg.arg1);
				break;
			case Contacts.KEYEVENT.KNOBSELECTOR:
				Log.i("cxs", "-------Contacts.KEYEVENT.KNOBSELECTOR-------");
				handleKnobSelector(msg.arg1);
				break;
			case Contacts.KEYEVENT.ANSWER_WITH_MENUUP:
				Log.i("cxs",
						"-------Contacts.KEYEVENT.ANSWER_WITH_MENUUP-------");
				handleANSWER_WITH_MENUUP();
				break;
			case Contacts.KEYEVENT.HANGUP_WITH_MENUDOWN:
				Log.i("cxs",
						"-------Contacts.KEYEVENT.HANGUP_WITH_MENUDOWN-------");
				handleHANGUP_WITH_MENUDOWN();
				break;
			case Contacts.KEYEVENT.FM_CHANGE_FREQUENCY:
				Log.i("cxs",
						"-------Contacts.KEYEVENT.FM_CHANGE_FREQUENCY-------");
				handleFM_CHANGE_FREQUENCY((float) msg.obj);
				break;
			case Contacts.KEYEVENT.NUM1:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM1-------");
				handleNUM1();
				break;
			case Contacts.KEYEVENT.NUM2:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM2-------");
				handleNUM2();
				break;
			case Contacts.KEYEVENT.NUM3:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM3-------");
				handleNUM3();
				break;
			case Contacts.KEYEVENT.NUM4:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM4-------");
				handleNUM4();
				break;
			case Contacts.KEYEVENT.NUM5:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM5-------");
				handleNUM5();
				break;
			case Contacts.KEYEVENT.NUM6:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM6-------");
				handleNUM6();
				break;
			case Contacts.KEYEVENT.NUM7:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM7-------");
				handleNUM7();
				break;
			case Contacts.KEYEVENT.NUM8:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM8-------");
				handleNUM8();
				break;
			case Contacts.KEYEVENT.NUM9:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM9-------");
				handleNUM9();
				break;
			case Contacts.KEYEVENT.NUM0:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUM0-------");
				handleNUM0();
				break;
			case Contacts.KEYEVENT.NUMSTAR:
				Log.i("cxs", "-------Contacts.KEYEVENT.NUMSTAR-------");
				handleNUMSTAR();
				break;
			case Contacts.KEYEVENT.POUND:
				Log.i("cxs", "-------Contacts.KEYEVENT.POUND-------");
				handlePOUND();
				break;
			case Contacts.KEYEVENT.DPAD_UP:
				Log.i("cxs", "-------Contacts.KEYEVENT.DPAD_UP-------");
				handleDPAD_UP();
				break;
			case Contacts.KEYEVENT.DPAD_DOWN:
				Log.i("cxs", "-------Contacts.KEYEVENT.DPAD_DOWN-------");
				handleDPAD_DOWN();
				break;
			case Contacts.KEYEVENT.DPAD_LEFT:
				Log.i("cxs", "-------Contacts.KEYEVENT.DPAD_LEFT-------");
				handleDPAD_LEFT();
				break;
			case Contacts.KEYEVENT.DPAD_RIHGT:
				Log.i("cxs", "-------Contacts.KEYEVENT.DPAD_RIHGT-------");
				handleDPAD_RIHGT();
				break;
			case Contacts.KEYEVENT.ENTER:
				Log.i("cxs", "-------Contacts.KEYEVENT.ENTER-------");
				handleENTER();
				break;
			case Contacts.KEYEVENT.DEL:
				Log.i("cxs", "-------Contacts.KEYEVENT.DEL-------");
				handleDEL();
				break;
			case Contacts.KEYEVENT.BACK:
				Log.i("cxs", "-------Contacts.KEYEVENT.BACK-------");
				handleBACK();
				break;
			case Contacts.KEYEVENT.AIRCONTROLER:
				Log.i("cxs", "-------Contacts.KEYEVENT.AIRCONTROLER-------");
				startAcForAir();
				break;
			case Contacts.KEYEVENT.CARINFO:
				Log.i("cxs", "-------Contacts.KEYEVENT.CARINFO-------");
				startAcForCarInfo();
				break;
			default:
				break;
			}
		}
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
				// mute相关
				if (cur_music > 0) {
					save_music = 0;
				}
			}
		}
	}

	/*
	 * protected void handleRadioVolUP() { int valume =
	 * Settings.System.getInt(context.getContentResolver(),
	 * Contacts.KEY_VOLUME_VALUE, 0); valume = (valume + 1) > 48 ? 48 : (valume
	 * + 1); Settings.System.putInt(context.getContentResolver(),
	 * Contacts.KEY_VOLUME_VALUE, valume); }
	 * 
	 * protected void handleRadioVolDown() { int valume =
	 * Settings.System.getInt(context.getContentResolver(),
	 * Contacts.KEY_VOLUME_VALUE, 0); valume = (valume - 1) < 0 ? 0 : (valume -
	 * 1); Settings.System.putInt(context.getContentResolver(),
	 * Contacts.KEY_VOLUME_VALUE, valume); }
	 */
	/**
	 * acc on后清除旋钮保存值
	 */
	public void clearKnobValue() {
		PreferenceUtil.setKnobVolValue(context, 0);
		PreferenceUtil.setKnobSelValue(context, 0);
	}

	public void handleKnobSelector(int knobValue) {
		int temp = knobValue - PreferenceUtil.getKnobSelValue(context);
		if (temp > 125) {
			temp = temp - 256;
		}
		if (temp < -125) {
			temp = 256 + temp;
		}
		Log.i("cxs", "==handleKnobSelector=temp==" + temp);
		if (temp > 0) {
			handleMenuDown();
		} else {
			handleMenuUp();
		}
		PreferenceUtil.setKnobSelValue(context, knobValue);
	}
	
	
	private void startAcForCarInfo() {
		try {
			Intent i=new Intent(context, CarInfoActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} catch (Exception e) {
			Log.i("xxx", "start startAcForCarInfo error");
		}
	}

	private void startAcForAir() {
		try {
			Intent i=new Intent(context, MenuAcAcitivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		} catch (Exception e) {
			Log.i("xxx", "start startAcForAir error");
		}
	}

	public void handleKnobVolume(int knobValue) {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		int temp = knobValue - PreferenceUtil.getKnobVolValue(context);
		// 处理循环
		if (temp > 125) {
			temp = temp - 256;
		}
		if (temp < -125) {
			temp = 256 + temp;
		}
		// 处理值操作间隔
		if (temp > 0) {
			temp = (temp / 2) < 1 ? 1 : (temp / 2);
		} else if (temp < 0) {
			temp = (temp / 2) > -1 ? -1 : (temp / 2);
		}
		handleVolume(context, cur_music + temp);
		PreferenceUtil.setKnobVolValue(context, knobValue);
	}

	protected void handleNUM1() {
		actionKey(KeyEvent.KEYCODE_1);
	}

	protected void handleNUM2() {
		actionKey(KeyEvent.KEYCODE_2);
	}

	protected void handleNUM3() {
		actionKey(KeyEvent.KEYCODE_3);
	}

	protected void handleNUM4() {
		actionKey(KeyEvent.KEYCODE_4);
	}

	protected void handleNUM5() {
		actionKey(KeyEvent.KEYCODE_5);
	}

	protected void handleNUM6() {
		actionKey(KeyEvent.KEYCODE_6);
	}

	protected void handleNUM7() {
		actionKey(KeyEvent.KEYCODE_7);
	}

	protected void handleNUM8() {
		actionKey(KeyEvent.KEYCODE_8);
	}

	protected void handleNUM9() {
		actionKey(KeyEvent.KEYCODE_9);
	}

	protected void handleNUM0() {
		actionKey(KeyEvent.KEYCODE_0);
	}

	protected void handleNUMSTAR() {
		actionKey(KeyEvent.KEYCODE_STAR);
	}

	protected void handlePOUND() {
		actionKey(KeyEvent.KEYCODE_POUND);
	}

	protected void handleDPAD_UP() {
		actionKey(KeyEvent.KEYCODE_DPAD_UP);
	}

	protected void handleDPAD_DOWN() {
		actionKey(KeyEvent.KEYCODE_DPAD_DOWN);
	}

	protected void handleDPAD_LEFT() {
		actionKey(KeyEvent.KEYCODE_DPAD_LEFT);
	}

	protected void handleDPAD_RIHGT() {
		actionKey(KeyEvent.KEYCODE_DPAD_RIGHT);
	}

	protected void handleENTER() {
		actionKey(KeyEvent.KEYCODE_DPAD_RIGHT);
	}

	protected void handleDEL() {
		actionKey(KeyEvent.KEYCODE_DEL);
	}

	protected void handleBACK() {
		actionKey(KeyEvent.KEYCODE_BACK);
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
		}
	}

	private void handleANSWER_WITH_MENUUP() {
		// TODO Auto-generated method stub
		if (PreferenceUtil.getMode(context) == 3) {
			handleTelAnswer();
		} else {
			handleMenuUp();
		}
	}

	private void handleFM_CHANGE_FREQUENCY(float value) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(RADIO_FREQ_ACTION);
		intent.putExtra("fm_fq", value);
		context.sendBroadcast(intent);
	}

	private void handleHANGUP_WITH_MENUDOWN() {
		// TODO Auto-generated method stub
		if (PreferenceUtil.getMode(context) == 3) {
			handleTelHandUp();
		} else {
			handleMenuDown();
		}
	}

	private void handleMUSIC_PLAY_PAUSE() {
		// TODO Auto-generated method stub
		 Intent intent= new Intent();
         intent.setClassName("cn.colink.serialport", "cn.colink.serialport.service.SerialPortService");
         intent.putExtra("keyEvent", ACTION_PLAY_PAUSE);
         context.startService(intent);
	}

	private void handleAUX() {
		// TODO Auto-generated method stub
		openApplication(context, "com.console.auxapp");
	}
	
	private void handleCLOSEAUX() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(ACTION_CLOSE_AUX);
		context.sendBroadcast(intent);
	}


	private void handleMAP() {
		// TODO Auto-generated method stub
		startNavi();
	}

	private void startNavi() {
		// TODO Auto-generated method stub
		int mapType = Settings.System.getInt(context.getContentResolver(),
				"MAP_INDEX", 0);
		if (mapType == 0) {
			openApplication(context, "com.baidu.navi");
		} else {
			openApplication(context, "com.autonavi.amapauto");
		}
	}

	private void handleHOME() {
		// TODO Auto-generated method stub
		try {
			Intent home = new Intent(Intent.ACTION_MAIN);
			home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			home.addCategory(Intent.CATEGORY_HOME);
			home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(home);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void handlePHONE_APP() {
		// TODO Auto-generated method stub
		openApplication(context, "com.mtk.bluetooth");
	}

	private void handleMUSIC() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction(ACTION_MUSIC_START);
		context.sendBroadcast(intent);
	};

	protected void handleFmAm() {
		openApplication(context, "com.console.radio");
	}

	protected void handleCanInfoPage() {
		openApplication(context, "com.console.canreader");
	}

	protected void handleMenuLongDown() {
		Intent intent= new Intent();
        intent.setClassName("cn.colink.serialport", "cn.colink.serialport.service.SerialPortService");
        intent.putExtra("keyEvent", ACTION_MENU_LONG_DOWN);
        context.startService(intent);
	}

	protected void handleMenuLongUp() {
		 Intent intent= new Intent();
         intent.setClassName("cn.colink.serialport", "cn.colink.serialport.service.SerialPortService");
         intent.putExtra("keyEvent", ACTION_MENU_LONG_UP);
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

	protected void handleMenuUp() {
		 Intent intent= new Intent();
         intent.setClassName("cn.colink.serialport", "cn.colink.serialport.service.SerialPortService");
         intent.putExtra("keyEvent", ACTION_MENU_UP);
         context.startService(intent);
	}

	protected void handleMenuDown() {
		 Intent intent= new Intent();
         intent.setClassName("cn.colink.serialport", "cn.colink.serialport.service.SerialPortService");
         intent.putExtra("keyEvent", ACTION_MENU_DOWN);
         context.startService(intent);
	}

	protected void handleTel() {
		Intent intent = new Intent();
		intent.setAction(ACTION_TEL);
		context.sendBroadcast(intent);
	}

	public void handleMute() {
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

	public void handleVolUp() {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		handleVolume(context, cur_music + SETP_VOLUME);
	}

	public void handleVolDown() {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		handleVolume(context, cur_music - SETP_VOLUME);
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

	Boolean PRESSFREE = true;

	static public int CAR_VOLUME_KNOB = 0;

	// 处理
	protected void dealWith(Context context, CanInfo canInfo) {
		// TODO Auto-generated method stub

		// 音量旋钮 选择旋钮 语音命令
		switch (canInfo.STEERING_BUTTON_MODE) {
		case Contacts.KEYEVENT.KNOBVOLUME:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.KNOBVOLUME;
				msg.arg1 = canInfo.CAR_VOLUME_KNOB;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.KNOBSELECTOR:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.KNOBSELECTOR;
				msg.arg1 = canInfo.CAR_VOLUME_KNOB;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.VOICE_FM:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.FM_AM;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.VOICE_MENUUP:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.MENUUP;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.VOICE_MENUDOWN:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.MENUDOWN;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.VOICE_MUSIC_PLAY_PAUSE:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.MUSIC_PLAY_PAUSE;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.FM_CHANGE_FREQUENCY:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.FM_CHANGE_FREQUENCY;
				msg.obj = canInfo.FREQUENCY_VALUE;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.CLOSEAUX:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.CLOSEAUX;
				mHandler.sendMessage(msg);
			}
			break;
		case Contacts.KEYEVENT.OPENAUX:
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.AUX;
				mHandler.sendMessage(msg);
			}
			break;
		default:
			break;
		}

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
				mHandler.removeMessages(Contacts.TEL_ANSWER);
				mHandler.sendEmptyMessageDelayed(Contacts.TEL_ANSWER, 200);
				break;
			case Contacts.TEL_HANDUP:
				mHandler.removeMessages(Contacts.TEL_HANDUP);
				mHandler.sendEmptyMessageDelayed(Contacts.TEL_HANDUP, 200);
				break;
			case Contacts.KEYEVENT.CANINFOPAGE:
				mHandler.removeMessages(Contacts.KEYEVENT.CANINFOPAGE);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.CANINFOPAGE,
						200);
				break;
			case Contacts.KEYEVENT.FM_AM:
				mHandler.removeMessages(Contacts.KEYEVENT.FM_AM);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.FM_AM, 200);
				break;
			case Contacts.KEYEVENT.MUSIC:
				mHandler.removeMessages(Contacts.KEYEVENT.MUSIC);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.MUSIC, 200);
				break;
			case Contacts.KEYEVENT.PHONE_APP:
				mHandler.removeMessages(Contacts.KEYEVENT.PHONE_APP);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.PHONE_APP,
						200);
				break;
			case Contacts.KEYEVENT.HOME:
				mHandler.removeMessages(Contacts.KEYEVENT.HOME);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.HOME, 200);
				break;
			case Contacts.KEYEVENT.POWER:
				mHandler.removeMessages(Contacts.KEYEVENT.POWER);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.POWER, 200);
				break;
			case Contacts.KEYEVENT.MAP:
				mHandler.removeMessages(Contacts.KEYEVENT.MAP);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.MAP, 200);
				break;
			case Contacts.KEYEVENT.AUX:
				mHandler.removeMessages(Contacts.KEYEVENT.AUX);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.AUX, 200);
				break;
			case Contacts.KEYEVENT.MUSIC_PLAY_PAUSE:
				mHandler.removeMessages(Contacts.KEYEVENT.MUSIC_PLAY_PAUSE);
				mHandler.sendEmptyMessageDelayed(
						Contacts.KEYEVENT.MUSIC_PLAY_PAUSE, 200);
				break;
			case Contacts.KEYEVENT.ANSWER_WITH_MENUUP:
				mHandler.removeMessages(Contacts.KEYEVENT.ANSWER_WITH_MENUUP);
				mHandler.sendEmptyMessageDelayed(
						Contacts.KEYEVENT.ANSWER_WITH_MENUUP, 200);
				break;
			case Contacts.KEYEVENT.HANGUP_WITH_MENUDOWN:
				mHandler.removeMessages(Contacts.KEYEVENT.HANGUP_WITH_MENUDOWN);
				mHandler.sendEmptyMessageDelayed(
						Contacts.KEYEVENT.HANGUP_WITH_MENUDOWN, 200);
				break;
			case Contacts.KEYEVENT.NUM1:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM1);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM1, 200);
				break;
			case Contacts.KEYEVENT.NUM2:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM2);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM2, 200);
				break;
			case Contacts.KEYEVENT.NUM3:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM3);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM3, 200);
				break;
			case Contacts.KEYEVENT.NUM4:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM4);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM4, 200);
				break;
			case Contacts.KEYEVENT.NUM5:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM5);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM5, 200);
				break;
			case Contacts.KEYEVENT.NUM6:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM6);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM6, 200);
				break;
			case Contacts.KEYEVENT.NUM7:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM7);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM7, 200);
				break;
			case Contacts.KEYEVENT.NUM8:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM8);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM8, 200);
				break;
			case Contacts.KEYEVENT.NUM9:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM9);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM9, 200);
				break;
			case Contacts.KEYEVENT.NUM0:
				mHandler.removeMessages(Contacts.KEYEVENT.NUM0);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUM0, 200);
				break;
			case Contacts.KEYEVENT.NUMSTAR:
				mHandler.removeMessages(Contacts.KEYEVENT.NUMSTAR);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.NUMSTAR, 200);
				break;
			case Contacts.KEYEVENT.POUND:
				mHandler.removeMessages(Contacts.KEYEVENT.POUND);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.POUND, 200);
				break;
			case Contacts.KEYEVENT.DPAD_UP:
				mHandler.removeMessages(Contacts.KEYEVENT.DPAD_UP);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.DPAD_UP, 200);
				break;
			case Contacts.KEYEVENT.DPAD_DOWN:
				mHandler.removeMessages(Contacts.KEYEVENT.DPAD_DOWN);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.DPAD_DOWN,
						200);
				break;
			case Contacts.KEYEVENT.DPAD_LEFT:
				mHandler.removeMessages(Contacts.KEYEVENT.DPAD_LEFT);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.DPAD_LEFT,
						200);
				break;
			case Contacts.KEYEVENT.DPAD_RIHGT:
				mHandler.removeMessages(Contacts.KEYEVENT.DPAD_RIHGT);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.DPAD_RIHGT,
						200);
				break;
			case Contacts.KEYEVENT.ENTER:
				mHandler.removeMessages(Contacts.KEYEVENT.ENTER);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.ENTER, 200);
				break;
			case Contacts.KEYEVENT.DEL:
				mHandler.removeMessages(Contacts.KEYEVENT.DEL);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.DEL, 200);
				break;
			case Contacts.KEYEVENT.BACK:
				mHandler.removeMessages(Contacts.KEYEVENT.BACK);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.BACK, 200);
				break;
			case Contacts.KEYEVENT.AIRCONTROLER:
				mHandler.removeMessages(Contacts.KEYEVENT.AIRCONTROLER);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.AIRCONTROLER, 200);
				break;
			case Contacts.KEYEVENT.CARINFO:
				mHandler.removeMessages(Contacts.KEYEVENT.CARINFO);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.CARINFO, 200);
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
				currVolume > 15 ? 15 : currVolume, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				currVolume > 15 ? 15 : currVolume, 1);
		mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				currVolume, 0);
		mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, currVolume, 0);

		Settings.System.putInt(context.getContentResolver(),
				Contacts.KEY_VOLUME_VALUE, currVolume * 3);
	}

	public static boolean openApplication(Context context, String pkgName) {
		if (TextUtils.isEmpty(pkgName)) {
			Toast.makeText(context, R.string.activity_not_found,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		try {
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(pkgName);
			if (intent == null) {
				Toast.makeText(context, R.string.activity_not_found,
						Toast.LENGTH_SHORT).show();
				return false;
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context, R.string.activity_not_found,
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	public void actionKey(final int keyCode) {
		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("input keyevent " + keyCode);
		} catch (IOException e) {
			Log.e("wrc", "------actionKey--" + e.getMessage());
			e.printStackTrace();
		}

	}

}
