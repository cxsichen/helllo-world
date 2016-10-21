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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.console.canreader.R;
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

	// �����Ӽ���mute������ͳһ���������������洦��
	public static final String KEYCODE_VOLUME_UP = "com.console.KEYCODE_VOLUME_UP";
	public static final String KEYCODE_VOLUME_DOWN = "com.console.KEYCODE_VOLUME_DOWN";
	public static final String KEYCODE_VOLUME_MUTE = "com.console.KEYCODE_VOLUME_MUTE";
	// �����Ӽ���mute������ͳһ���������������洦��
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
			case Contacts.KEYEVENT.KNOBVOLUME:
				Log.i("cxs", "-------Contacts.KEYEVENT.KNOBVOLUME-------");
				handleKnobVolume(msg.arg1);
				break;
			case Contacts.KEYEVENT.KNOBSELECTOR:
				Log.i("cxs", "-------Contacts.KEYEVENT.KNOBSELECTOR-------");
				handleKnobSelector(msg.arg1);
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

	// ��������Ӽ�������
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
				// mute���
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
	 * acc on�������ť����ֵ
	 */
	public void clearKnobValue(){
		PreferenceUtil.setKnobVolValue(context,0);
		PreferenceUtil.setKnobSelValue(context,0);
	}


	public void handleKnobSelector(int knobValue) {
		int  temp=knobValue-PreferenceUtil.getKnobSelValue(context);
		if(temp>125){
			temp=temp-256;
		}
		if(temp<-125){
			temp=256+temp;
		}
		Log.i("cxs","==handleKnobSelector=temp=="+temp);
		if(temp>0){
			handleMenuDown();
		}else{
			handleMenuUp();
		}
		PreferenceUtil.setKnobSelValue(context,knobValue);
	}
	

	public void handleKnobVolume(int knobValue) {
		if (mAudioManager == null)
			mAudioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		cur_music = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		int  temp=knobValue-PreferenceUtil.getKnobVolValue(context);
		//����ѭ��
		if(temp>125){
			temp=temp-256;
		}
		if(temp<-125){
			temp=256+temp;
		}
		//����ֵ�������		
		if(temp>0){
			temp=(temp/2)<1?1:(temp/2);
		}else if(temp<0){
			temp=(temp/2)>-1?-1:(temp/2);
		}
		handleVolume(context, cur_music + temp);
		PreferenceUtil.setKnobVolValue(context,knobValue);
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
     
	static public int CAR_VOLUME_KNOB=0;	
	// ����
	protected void dealWith(Context context, CanInfo canInfo) {
		// TODO Auto-generated method stub

		// ������ť ѡ����ť
		if (canInfo.STEERING_BUTTON_MODE == Contacts.KEYEVENT.KNOBVOLUME) {
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.KNOBVOLUME;
				msg.arg1 = canInfo.CAR_VOLUME_KNOB;
				mHandler.sendMessage(msg);
			}
		}
		if (canInfo.STEERING_BUTTON_MODE == Contacts.KEYEVENT.KNOBSELECTOR) {
			if (System.currentTimeMillis() - lastSendTime > 500) {
				lastSendTime = System.currentTimeMillis();
				Message msg = new Message();
				msg.what = Contacts.KEYEVENT.KNOBSELECTOR;
				msg.arg1 = canInfo.CAR_VOLUME_KNOB;
				mHandler.sendMessage(msg);
			}
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
			case Contacts.KEYEVENT.MAP:
				mHandler.removeMessages(Contacts.KEYEVENT.MAP);
				mHandler.sendEmptyMessageDelayed(Contacts.KEYEVENT.MAP, 200);
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

}
