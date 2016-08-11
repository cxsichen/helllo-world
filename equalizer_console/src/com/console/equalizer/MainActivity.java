package com.console.equalizer;

import com.console.equalizer.util.BytesUtil;
import com.console.equalizer.util.Contacts;
import com.console.equalizer.util.PreferenceUtil;
import com.console.equalizer.util.Trace;
import com.console.equalizer.view.CarSelectedView;
import com.console.equalizer.view.CarSelectedView.OnPositionChangedListener;
import com.console.equalizer.view.VerticalSeekBar;
import com.console.equalizer.view.VerticalSeekBar.OnProgressChangedListener;

import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;
import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private ISerialPortService mISpService;
	private boolean isHavedReponse = false;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000001;

	private TextView defaultEfButton;
	private TextView rockEfButton;
	private TextView popularEfButton;
	private TextView classicEfButton;
	private TextView jazzEfButton;
	private TextView resetButton;

	private VerticalSeekBar seekBarTRE;
	private VerticalSeekBar seekBarMID;
	private VerticalSeekBar seekBarBAS;
	private TextView treTv;
	private TextView midTv;
	private TextView basTv;
	private CarSelectedView mCarSelectedView;
	private int SEEKBARDURATION = 100 / 14;

	private static int treValue = 7;
	private static int midValue = 7;
	private static int basValue = 7;
	private static int rowValue = 7;
	private static int colValue = 7;
	private static int userBasValue=7;
	private static int userMidValue=7;
	private static int userTreValue=7;
	private static int mode = R.id.defaultEf_button;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				byte[] packet = (byte[]) msg.obj;
				receiveMsg(packet);
				break;
			case Contacts.MSG_SEND_MSG:
				sendMsg(BytesUtil.makeEfMsg(basValue, midValue,treValue, rowValue,
						colValue));
				break;
			default:
				break;
			}
		}
	};

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
			Message msg = new Message();
			msg.what = Contacts.MSG_SEND_MSG;
			mHandler.sendMessage(msg);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	private void bindSpService() {
		try {
			Intent intent = new Intent();
			intent.setClassName("cn.colink.serialport",
					"cn.colink.serialport.service.SerialPortService");
			bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unbindSpService() {
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
	}

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

	protected void receiveMsg(byte[] packet) {
/*
		if (packet == null)
			return;
		if (packet[0] != Contacts.SYSTEM_INFO)
			return;
		switch (packet[1]) {
		case 0x01:
			Trace.i("bas value : " + ((int) packet[4] & 0xFF));
			basValue = (int) packet[4] & 0xFF;
			seekBarBAS.setNumProgress((int) packet[4] & 0xFF);
			break;
		case 0x02:
			Trace.i("tre value : " + ((int) packet[4] & 0xFF));
			treValue = (int) packet[4] & 0xFF;
			seekBarTRE.setNumProgress((int) packet[4] & 0xFF);
			break;
		case 0x03:
			Trace.i("column value : " + ((int) packet[4] & 0xFF));
			colValue = (int) packet[4] & 0xFF;
			mCarSelectedView.setPosition(colValue, rowValue);
			break;
		case 0x04:
			Trace.i("row value : " + ((int) packet[4] & 0xFF));
			rowValue = (int) packet[4] & 0xFF;
			mCarSelectedView.setPosition(colValue, rowValue);
			break;
		default:
			break;
		}
*/
		// isHavedReponse = false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("cxs","========onCreate()===========");
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED,
				FLAG_HOMEKEY_DISPATCHED);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();	
		bindSpService();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
		Log.i("cxs","========onResume()===========");
	}

	private void initData() {
		// TODO Auto-generated method stub
		int[] values=PreferenceUtil.getMode(this, basValue, midValue, treValue, rowValue,
				colValue, mode);
		basValue=values[0];
		midValue=values[1];
		treValue=values[2];
		rowValue=values[3];
		colValue=values[4];
		mode=values[5];
		
		seekBarTRE.setNumProgress(treValue);
		seekBarMID.setNumProgress(midValue);
		seekBarBAS.setNumProgress(basValue);
		mCarSelectedView.setPosition(colValue, rowValue);
		if (mode == R.id.reset_button) {
			mode = R.id.defaultEf_button;
		}
		setChangeable();
		if (findViewById(mode) != null) {
			clearButtonBg();
			((TextView) findViewById(mode))
					.setBackgroundResource(R.drawable.bg_btn_selected);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		defaultEfButton = (TextView) findViewById(R.id.defaultEf_button);
		rockEfButton = (TextView) findViewById(R.id.rockEf_button);
		popularEfButton = (TextView) findViewById(R.id.popularEf_button);
		classicEfButton = (TextView) findViewById(R.id.classicEf_button);
		jazzEfButton = (TextView) findViewById(R.id.jazzEf_button);
		resetButton = (TextView) findViewById(R.id.reset_button);

		seekBarTRE = (VerticalSeekBar) findViewById(R.id.SeekBarTRE);
		seekBarMID = (VerticalSeekBar) findViewById(R.id.SeekBarMID);
		seekBarBAS = (VerticalSeekBar) findViewById(R.id.SeekBarBas);
		treTv = (TextView) findViewById(R.id.tre_tv);
		midTv = (TextView) findViewById(R.id.mid_tv);
		basTv = (TextView) findViewById(R.id.bas_tv);

		mCarSelectedView = (CarSelectedView) findViewById(R.id.mMainCarImg);

		defaultEfButton.setOnClickListener(this);
		rockEfButton.setOnClickListener(this);
		popularEfButton.setOnClickListener(this);
		classicEfButton.setOnClickListener(this);
		jazzEfButton.setOnClickListener(this);
		resetButton.setOnClickListener(this);

		seekBarTRE
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							treTv.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							treTv.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						treValue = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});
		seekBarMID
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							midTv.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							midTv.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						midValue = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});

		seekBarBAS
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							basTv.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							basTv.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						basValue = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});

		mCarSelectedView
				.setOnValueChangedListener(new OnPositionChangedListener() {

					@Override
					public void OnChange(int mColumnIndex, int mRowIndex) {
						// TODO Auto-generated method stub
						rowValue = mRowIndex;
						colValue = mColumnIndex;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
						mHandler.sendEmptyMessageDelayed(Contacts.MSG_SEND_MSG,
								1000);
					}
				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mode==R.id.defaultEf_button){
			PreferenceUtil.setUserMode(MainActivity.this, basValue, midValue, treValue);
		}
		mode = v.getId();
		setChangeable(); 
		switch (v.getId()) {
		case R.id.defaultEf_button:
			clearButtonBg();
			int[] values=PreferenceUtil.getUserMode(MainActivity.this, userBasValue, userMidValue, userTreValue);
			setVolumeData(values[0],values[1],values[2]);
			defaultEfButton.setBackgroundResource(R.drawable.bg_btn_selected);
			break;
		case R.id.rockEf_button:
			clearButtonBg();
			setVolumeData(5,7,10);
			rockEfButton.setBackgroundResource(R.drawable.bg_btn_selected);
			break;
		case R.id.popularEf_button:
			clearButtonBg();
			setVolumeData(10,7,10);
			popularEfButton.setBackgroundResource(R.drawable.bg_btn_selected);
			break;
		case R.id.classicEf_button:
			clearButtonBg();
			setVolumeData(9,7,9);
			classicEfButton.setBackgroundResource(R.drawable.bg_btn_selected);
			break;
		case R.id.jazzEf_button:
			clearButtonBg();
			setVolumeData(5,7,5);
			mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
			jazzEfButton.setBackgroundResource(R.drawable.bg_btn_selected);
			break;
		case R.id.reset_button:
			resetAll();
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		basValue = 7;
		midValue = 7;
		treValue = 7;		
		rowValue = 7;
		colValue = 7;
		sendMsg(BytesUtil.makeEfMsg(7, 7, 7,7, 7));
		clearButtonBg();
		defaultEfButton.setBackgroundResource(R.drawable.bg_btn_selected);
		seekBarTRE.setMProgress(50);
		seekBarBAS.setMProgress(50);
		seekBarMID.setMProgress(50);
		mCarSelectedView.setPosition(7, 7);
		PreferenceUtil.setUserMode(MainActivity.this, basValue, midValue, treValue);
	}
	private void setChangeable(){
		if(mode==R.id.defaultEf_button||mode==R.id.reset_button){
			seekBarTRE.setEnabled(true);
			seekBarMID.setEnabled(true);
			seekBarBAS.setEnabled(true);
		}else{
			seekBarTRE.setEnabled(false);
			seekBarMID.setEnabled(false);
			seekBarBAS.setEnabled(false);
		}
	}
	
	//根据低，中，高的数值改变界面显示并发送命令
	private void setVolumeData(int bas,int mid,int tre ){
		basValue = bas;
		midValue = mid;
		treValue = tre;
		seekBarTRE.setNumProgress(treValue);
		seekBarMID.setNumProgress(midValue);
		seekBarBAS.setNumProgress(basValue);
		mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
	}

	private void clearButtonBg() {
		// TODO Auto-generated method stub
		defaultEfButton.setBackgroundResource(R.drawable.bg_btn_null);
		rockEfButton.setBackgroundResource(R.drawable.bg_btn_null);
		popularEfButton.setBackgroundResource(R.drawable.bg_btn_null);
		classicEfButton.setBackgroundResource(R.drawable.bg_btn_null);
		jazzEfButton.setBackgroundResource(R.drawable.bg_btn_null);

	}

	@Override
	protected void onDestroy() {
		Trace.i("onDestroy");
		PreferenceUtil.setMode(this, basValue, midValue, treValue, rowValue,
				colValue, mode);
		unbindSpService();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sendMsg(Contacts.HEX_SOUND_TO_HOME);
		PreferenceUtil.setMode(this, basValue, midValue, treValue, rowValue,
				colValue, mode);
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Trace.i("keyCode :" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			Trace.i("home press");
			sendMsg(Contacts.HEX_SOUND_TO_HOME);
			finish();
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		sendMsg(Contacts.HEX_SOUND_TO_HOME);
		finish();
	}

}
