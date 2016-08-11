package com.console.launcher_console.control;

import java.text.DecimalFormat;

import com.console.launcher_console.R;
import com.console.launcher_console.control.SerialPortControl.DataCallback;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.Constact;
import com.console.launcher_console.util.Contacts;
import com.console.launcher_console.util.PreferenceUtil;
import com.console.launcher_console.view.FMView;
import com.console.launcher_console.view.FMView.OnValueChangedListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent; 
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FmCardControl implements OnClickListener {

	private LinearLayout fmCardLayout;
	private Context context;

	float fmValue = 90;
	float fmMaxValue = 109;
	float fmMinValue = 85;
	float fmInterval = 0.1f;
	FMView mFMView;
	TextView channelTv;
	SerialPortControl mSerialPortControl;
	public static final String ACTION_MENU_UP = "com.console.MENU_UP";
	public static final String ACTION_MENU_DOWN = "com.console.MENU_DOWN";
	public static final String ACTION_PLAY_PAUSE = "com.console.PLAY_PAUSE";
	public static final String ACTION_MENU_LONG_UP = "com.console.MENU_LONG_UP";
	public static final String ACTION_MENU_LONG_DOWN = "com.console.MENU_LONG_DOWN";

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				break;
			default:
				break;
			}
		};
	};

	public FmCardControl(Context context, LinearLayout layout,
			SerialPortControl mSerialPortControl) {
		fmCardLayout = layout;
		this.context = context;
		this.mSerialPortControl = mSerialPortControl;
		mSerialPortControl.setDataCallback(new DataCallback() {

			@Override
			public void OnChange(byte[] value) {
				// TODO Auto-generated method stub
				setFmValue(value);
			}
		});
		init();
		initView();
		registerBroastReceiver();
		
		context.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.FMSTATUS),
				true, mFmStatusObserver);
	}
	
	
	private FmStatusObserver mFmStatusObserver = new FmStatusObserver();

	public class FmStatusObserver extends ContentObserver {
		public FmStatusObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			changePlayButton();
		}
	}
	
	private void  changePlayButton(){
		Log.i("cxs","---------changePlayButton---------------");
		int mode = Settings.System.getInt(context.getContentResolver(),
				Constact.FMSTATUS,0);
		if(mode==1){
			((ImageView) fmCardLayout.findViewById(R.id.fm_play))
			.setImageResource(R.drawable.ic_music_play);
		}else{
			((ImageView) fmCardLayout.findViewById(R.id.fm_play))
			.setImageResource(R.drawable.ic_music_pause);
		}
	}
	
	private void registerBroastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_MENU_UP);
		intentFilter.addAction(ACTION_MENU_DOWN);
		intentFilter.addAction(ACTION_PLAY_PAUSE);
		intentFilter.addAction(ACTION_MENU_LONG_DOWN);
		intentFilter.addAction(ACTION_MENU_LONG_UP);
		context.registerReceiver(mBroadcastReceiver, intentFilter);
	}
	
	
	

	BroadcastReceiver mBroadcastReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (PreferenceUtil.getMode(context) == 0) {
				switch (intent.getAction()) {
				case ACTION_MENU_UP:
					mSerialPortControl.sendMsg(Contacts.HEX_PRE_SHORT_MOVE);
					break;
				case ACTION_MENU_DOWN:
					mSerialPortControl.sendMsg(Contacts.HEX_NEXT_SHORT_MOVE);
					break;
				case ACTION_PLAY_PAUSE:
					mSerialPortControl.sendMsg(Contacts.FM_PLAY);
					break;
				case ACTION_MENU_LONG_UP:
					mSerialPortControl.sendMsg(Contacts.FM_PRE);
					break;
				case ACTION_MENU_LONG_DOWN:
					mSerialPortControl.sendMsg(Contacts.FM_NEXT);
					break;
				default:
					break;
				}
			}

		}
	};

	private void init() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		// TODO Auto-generated method stub
		fmCardLayout.findViewById(R.id.fm_next).setOnClickListener(this);
		fmCardLayout.findViewById(R.id.fm_play).setOnClickListener(this);
		fmCardLayout.findViewById(R.id.fm_pre).setOnClickListener(this);
		fmCardLayout.findViewById(R.id.radio_layout).setOnClickListener(this);
		mFMView = (FMView) fmCardLayout.findViewById(R.id.fMView);
		channelTv = (TextView) fmCardLayout.findViewById(R.id.channel_tx);

		mFMView.setOnValueChangedListener(onValueChangedListener);
		mFMView.setValue(fmValue);
	}

	OnValueChangedListener onValueChangedListener = new OnValueChangedListener() {

		@Override
		public void OnChange(float value) {
			// TODO Auto-generated method stub
			channelTv.setText(value + "Mhz");
		}
	};

	private DecimalFormat df2 = new DecimalFormat("###.00");
	int curFreq = 0;

	public void setFmValue(byte[] value) {
		if (value[0] == Contacts.RADIO_MSG) {
			if (value[2] == (int) 0x01) {				
				((ImageView) fmCardLayout.findViewById(R.id.fm_play))
						.setImageResource(R.drawable.ic_music_play);
				Settings.System.putInt(context.getContentResolver(),
						Constact.FMSTATUS,1);
			} else {
				((ImageView) fmCardLayout.findViewById(R.id.fm_play))
						.setImageResource(R.drawable.ic_music_pause);
				Settings.System.putInt(context.getContentResolver(),
						Constact.FMSTATUS,0);
			}
		}
		if (value[0] != Contacts.MODE_RADIO)
			return;
		switch (value[2]) {
		case Contacts.FM1_FREQ:
		case Contacts.FM2_FREQ:
		case Contacts.FM3_FREQ:
			// 棰戠巼
			curFreq = ((int) value[3] & 0xFF) << 8 | ((int) value[4] & 0xFF);
			break;
		default:
			break;
		}
		float fmvalue = checkFmValue(Float.parseFloat(df2
				.format((float) curFreq / 100.0f)));
		mFMView.setValue(fmvalue);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fm_next:
			mSerialPortControl.sendMsg(Contacts.FM_NEXT);
			break;
		case R.id.fm_pre:
			mSerialPortControl.sendMsg(Contacts.FM_PRE);
			break;
		case R.id.fm_play:
			mSerialPortControl.sendMsg(Contacts.FM_PLAY);
			break;
		case R.id.radio_layout:
			Intent intent = new Intent();
			intent.setClassName("com.console.radio",
					"com.console.radio.MainActivity");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			break;
		default:
			break;
		}
	}
	private float checkFmValue(float value) {
		if (value > fmMaxValue)
			value = fmMaxValue;
		if (value < fmMinValue)
			value = fmMinValue;
		return value;
	}

}
