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
	private VerticalSeekBar seekBarTRE1;
	private VerticalSeekBar seekBarTRE2;
	private VerticalSeekBar seekBarMID;
	private VerticalSeekBar seekBarMID1;
	private VerticalSeekBar seekBarMID2;
	private VerticalSeekBar seekBarBAS;
	private VerticalSeekBar seekBarBAS1;
	private VerticalSeekBar seekBarBAS2;
	private TextView treTv;
	private TextView treTv1;
	private TextView treTv2;
	private TextView midTv;
	private TextView midTv1;
	private TextView midTv2;
	private TextView basTv;
	private TextView basTv1;
	private TextView basTv2;

	private TextView lfTv;
	private TextView rfTv;
	private TextView lrTv;
	private TextView rrTv;

	private CarSelectedView mCarSelectedView;
	private int SEEKBARDURATION = 100 / 14;

	private static int[] treGroup = { 7, 7, 7 };
	private static int[] midGroup = { 7, 7, 7 };
	private static int[] basGroup = { 7, 7, 7 };
	private static int userBasValue = 7;
	private static int userMidValue = 7;
	private static int userTreValue = 7;
	private static int mode = R.id.defaultEf_button;

	private static int[] valueGroup = { 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
			R.id.defaultEf_button };

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				byte[] packet = (byte[]) msg.obj;
				receiveMsg(packet);
				break;
			case Contacts.MSG_SEND_MSG:
				sendMsg(BytesUtil.makeEfMsg(valueGroup[0] * 2 / 14
						+ valueGroup[1] * 4 / 14 + valueGroup[2] * 8 / 14,
						valueGroup[3] * 2 / 14 + valueGroup[4] * 4 / 14
								+ valueGroup[5] * 8 / 14, valueGroup[6] * 2
								/ 14 + valueGroup[7] * 4 / 14 + valueGroup[8]
								* 8 / 14, (int) (valueGroup[9] * 14 / 100), (int) (valueGroup[10] * 14 / 100)));
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

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	}

	private void initData() {
		// TODO Auto-generated method stub
		int[] values = PreferenceUtil.getMode(this);
		for (int i = 0; i < values.length; i++) {
			valueGroup[i] = values[i];
		}
		seekBarTRE.setNumProgress(valueGroup[0]);
		seekBarTRE1.setNumProgress(valueGroup[1]);
		seekBarTRE2.setNumProgress(valueGroup[2]);
		seekBarMID.setNumProgress(valueGroup[3]);
		seekBarMID1.setNumProgress(valueGroup[4]);
		seekBarMID2.setNumProgress(valueGroup[5]);
		seekBarBAS.setNumProgress(valueGroup[6]);
		seekBarBAS1.setNumProgress(valueGroup[7]);
		seekBarBAS2.setNumProgress(valueGroup[8]);
		mCarSelectedView.setPosition(valueGroup[10],
				valueGroup[9]);
		mode=valueGroup[11];
		if (mode == R.id.reset_button) {
			mode = R.id.defaultEf_button;
		}
		//setChangeable();
		if (findViewById(mode) != null) {
			clearButtonBg();
			try {
				((TextView) findViewById(mode))
						.setBackgroundResource(R.drawable.btn_bg_selected);
			} catch (Exception e) {
				// TODO: handle exception
			}
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
		seekBarTRE1 = (VerticalSeekBar) findViewById(R.id.SeekBarTRE1);
		seekBarTRE2 = (VerticalSeekBar) findViewById(R.id.SeekBarTRE2);
		seekBarMID = (VerticalSeekBar) findViewById(R.id.SeekBarMID);
		seekBarMID1 = (VerticalSeekBar) findViewById(R.id.SeekBarMID1);
		seekBarMID2 = (VerticalSeekBar) findViewById(R.id.SeekBarMID2);
		seekBarBAS = (VerticalSeekBar) findViewById(R.id.SeekBarBas);
		seekBarBAS1 = (VerticalSeekBar) findViewById(R.id.SeekBarBas1);
		seekBarBAS2 = (VerticalSeekBar) findViewById(R.id.SeekBarBas2);
		treTv = (TextView) findViewById(R.id.tre_tv);
		treTv1 = (TextView) findViewById(R.id.tre_tv1);
		treTv2 = (TextView) findViewById(R.id.tre_tv2);
		midTv = (TextView) findViewById(R.id.mid_tv);
		midTv1 = (TextView) findViewById(R.id.mid_tv1);
		midTv2 = (TextView) findViewById(R.id.mid_tv2);
		basTv = (TextView) findViewById(R.id.bas_tv);
		basTv1 = (TextView) findViewById(R.id.bas_tv1);
		basTv2 = (TextView) findViewById(R.id.bas_tv2);

		lfTv = (TextView) findViewById(R.id.tx1);
		rfTv = (TextView) findViewById(R.id.tx2);
		lrTv = (TextView) findViewById(R.id.tx3);
		rrTv = (TextView) findViewById(R.id.tx4);
		lfTv.setOnClickListener(mOnClickListener);
		rfTv.setOnClickListener(mOnClickListener);
		lrTv.setOnClickListener(mOnClickListener);
		rrTv.setOnClickListener(mOnClickListener);

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
						setDefaultButton();
						valueGroup[0] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});
		seekBarTRE1
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							treTv1.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							treTv1.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						setDefaultButton();
						valueGroup[1] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});
		seekBarTRE2
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							treTv2.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							treTv2.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						setDefaultButton();
						valueGroup[2] = progress / SEEKBARDURATION;
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
						setDefaultButton();
						valueGroup[3] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});

		seekBarMID1
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							midTv1.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							midTv1.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						setDefaultButton();
						valueGroup[4] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});

		seekBarMID2
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							midTv2.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							midTv2.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						setDefaultButton();
						valueGroup[5] = progress / SEEKBARDURATION;
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
						setDefaultButton();
						valueGroup[6] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});
		seekBarBAS1
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							basTv1.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							basTv1.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						setDefaultButton();
						valueGroup[7] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});

		seekBarBAS2
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						if (progress / SEEKBARDURATION - 7 > 0) {
							basTv2.setText("+"
									+ (progress / SEEKBARDURATION - 7));
						} else {
							basTv2.setText(String.valueOf(progress
									/ SEEKBARDURATION - 7));
						}
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						setDefaultButton();
						valueGroup[8] = progress / SEEKBARDURATION;
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
					}
				});

		mCarSelectedView
				.setOnValueChangedListener(new OnPositionChangedListener() {

					@Override
					public void OnChange(float mColumnIndex, float mRowIndex) {
						// TODO Auto-generated method stub
						valueGroup[9] = (int) mRowIndex;
						valueGroup[10] = (int) mColumnIndex;
						syncText(mColumnIndex, mRowIndex);
						mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
						mHandler.sendEmptyMessageDelayed(Contacts.MSG_SEND_MSG,
								1000);
					}
				});

	}
	
	void setDefaultButton(){
		valueGroup[11]=R.id.defaultEf_button;
		clearButtonBg();
		defaultEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
		mode = R.id.defaultEf_button;
		PreferenceUtil.setUserMode(MainActivity.this, valueGroup);
		
	}
	
	OnClickListener mOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tx1:
			    mCarSelectedView.setPosition(valueGroup[10]-5, valueGroup[9]-5);
				break;
			case R.id.tx2:
				mCarSelectedView.setPosition(valueGroup[10]+5, valueGroup[9]-5);
				break;
			case R.id.tx3:
				mCarSelectedView.setPosition(valueGroup[10]-5, valueGroup[9]+5);
				break;
			case R.id.tx4:
				mCarSelectedView.setPosition(valueGroup[10]+5, valueGroup[9]+5);
				break;
			default:
				break;
			}
		}
	};

	private void syncText(float mColumnIndex, float mRowIndex) {
		// TODO Auto-generated method stub	
		lfTv.setText(String.format(getResources().getString(R.string.lf_tx), checkValue(mColumnIndex - 50, mRowIndex - 50)));
		rfTv.setText(String.format(getResources().getString(R.string.rf_tx), checkValue(50 - mColumnIndex, mRowIndex - 50)));
		lrTv.setText(String.format(getResources().getString(R.string.lr_tx), checkValue(mColumnIndex - 50, 50 - mRowIndex)));
		rrTv.setText(String.format(getResources().getString(R.string.rr_tx), checkValue(50 - mColumnIndex, 50 - mRowIndex)));
	}

	int checkValue(float mColumnIndex, float mRowIndex) {
		if (mColumnIndex <= 0 & mRowIndex <=0) {
			return 100;
		}
		if (mColumnIndex < 0) {
			return (int) (100 - mRowIndex * 2);
		}
		if (mRowIndex < 0) {
			return (int) (100 - mColumnIndex * 2);
		}
		return mColumnIndex>mRowIndex?(int) (100 - mColumnIndex * 2):(int) (100 - mRowIndex * 2);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mode == R.id.defaultEf_button) {
			PreferenceUtil.setUserMode(MainActivity.this, valueGroup);
		}
		mode = v.getId();
		valueGroup[11]=mode;
	//	setChangeable();
		switch (v.getId()) {
		case R.id.defaultEf_button:
			clearButtonBg();
			int[] values = PreferenceUtil.getUserMode(MainActivity.this);
			setVolumeData(values);
			defaultEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
			break;
		case R.id.rockEf_button:
			clearButtonBg();
			setVolumeData(new int[] { 4, 5, 6, 6, 7, 8, 9, 10, 9 });
			rockEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
			break;
		case R.id.popularEf_button:
			clearButtonBg();
			setVolumeData(new int[] { 9, 10, 9, 6, 7, 8, 9, 10, 9 });
			popularEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
			break;
		case R.id.classicEf_button:
			clearButtonBg();
			setVolumeData(new int[] { 8, 9, 9, 6, 7, 7, 8, 9, 8 });
			classicEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
			break;
		case R.id.jazzEf_button:
			clearButtonBg();
			setVolumeData(new int[] { 4, 5, 4, 6, 7, 6, 5, 4, 4 });
			mHandler.sendEmptyMessage(Contacts.MSG_SEND_MSG);
			jazzEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
			break;
		case R.id.reset_button:
			resetAll();
			break;
		default:
			break;
		}
	}

	private void resetAll() {
		valueGroup = new int[] { 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
				R.id.defaultEf_button };
		sendMsg(BytesUtil.makeEfMsg(7, 7, 7, 7, 7));
		clearButtonBg();
		defaultEfButton.setBackgroundResource(R.drawable.btn_bg_selected);
		seekBarTRE.setMProgress(50);
		seekBarTRE1.setMProgress(50);
		seekBarTRE2.setMProgress(50);
		seekBarBAS.setMProgress(50);
		seekBarBAS1.setMProgress(50);
		seekBarBAS2.setMProgress(50);
		seekBarMID.setMProgress(50);
		seekBarMID1.setMProgress(50);
		seekBarMID2.setMProgress(50);
		mCarSelectedView.setPosition(50, 50);
		PreferenceUtil.setUserMode(MainActivity.this, valueGroup);
	}

	private void setChangeable() {
		if (mode == R.id.defaultEf_button || mode == R.id.reset_button) {
			seekBarTRE.setEnabled(true);
			seekBarTRE1.setEnabled(true);
			seekBarTRE2.setEnabled(true);
			seekBarMID.setEnabled(true);
			seekBarMID1.setEnabled(true);
			seekBarMID2.setEnabled(true);
			seekBarBAS.setEnabled(true);
			seekBarBAS1.setEnabled(true);
			seekBarBAS2.setEnabled(true);
		} else {
			seekBarTRE.setEnabled(false);
			seekBarTRE1.setEnabled(false);
			seekBarTRE2.setEnabled(false);
			seekBarMID.setEnabled(false);
			seekBarMID1.setEnabled(false);
			seekBarMID2.setEnabled(false);
			seekBarBAS.setEnabled(false);
			seekBarBAS1.setEnabled(false);
			seekBarBAS2.setEnabled(false);
		}
	}

	// 根据低，中，高的数值改变界面显示并发送命令
	private void setVolumeData(int[] values) {
		if (values.length < 9) {
			return;
		}
		for (int i = 0; i < values.length; i++) {
			valueGroup[i] = values[i];
		}
		seekBarTRE.setNumProgress(values[0]);
		seekBarTRE1.setNumProgress(values[1]);
		seekBarTRE2.setNumProgress(values[2]);
		seekBarMID.setNumProgress(values[3]);
		seekBarMID1.setNumProgress(values[4]);
		seekBarMID2.setNumProgress(values[5]);
		seekBarBAS.setNumProgress(values[6]);
		seekBarBAS1.setNumProgress(values[7]);
		seekBarBAS2.setNumProgress(values[8]);
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
		PreferenceUtil.setMode(this, valueGroup);
		unbindSpService();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sendMsg(Contacts.HEX_SOUND_TO_HOME);
		PreferenceUtil.setMode(this, valueGroup);
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
