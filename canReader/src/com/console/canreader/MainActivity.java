package com.console.canreader;

import java.text.DecimalFormat;
import java.util.List;

import com.console.canreader.service.CanInfo;
import com.console.canreader.service.CanService;
import com.console.canreader.service.CanService.ReaderCallback;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ICanService mISpService;
	public static final String TEST = "2e810101";
	CanInfo mCanInfo;
	TextView oil;
	TextView battery;
	TextView safety_salt;
	TextView handbrake;
	TextView clean;
	TextView enginee;
	TextView driving_speed;
	TextView distance;
	ImageView icObdWasherFluid;
	ImageView icObdSeatBelt;
	ImageView icObdBattery;
	ImageView icObdHandBrake;
	ImageView icFuel;
	Boolean FirstShow = true; // 第一次显示的时候所有数据都处理
	private int canType = -1; // 盒子厂家 0：睿志诚 1：尚摄
	private int carType = -1; // 车型 0:大众
	/*
	 * TextView fuel_warn; TextView battery_warn;
	 */
	private static final String WARNSTART = "warn_start";
	private static final int KEYCODE_HOME = 271;
	CanInfo mCaninfo;
	ImageView speedHand;
	int cout = 0;
	DecimalFormat fnum = new DecimalFormat("##0.00");

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				// dealwithPacket(Volkswagen)msg.obj);
				mCaninfo = (CanInfo) msg.obj;
				checkStartMode(mCaninfo);
				show(mCaninfo);
				// sendMsg(Contacts.HEX_GET_CAR_INFO);
				break;
			case Contacts.MSG_GET_MSG:
				// 大众主动获取数据
				if (canType == 0 && carType == 0) {
					sendMsg(BytesUtil.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO));
				}
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
				break;
			case Contacts.MSG_ONCE_GET_MSG:
				if (canType == 0 && carType == 0) {
					sendMsg(BytesUtil
							.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO_1));
					sendMsg(BytesUtil
							.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO_3));
				}
				break;
			default:
				break;
			}
		}
	};

	private void checkStartMode(CanInfo mCanInfo) {
		// TODO Auto-generated method stub
		int mode = Settings.System.getInt(getContentResolver(), WARNSTART, 0);
		if (mode == 1 && mCanInfo.FUEL_WARING_SIGN != 1
				&& mCanInfo.BATTERY_WARING_SIGN != 1
				&& mCanInfo.SAFETY_BELT_STATUS != 1
				&& mCanInfo.DISINFECTON_STATUS != 1
				&& mCanInfo.HANDBRAKE_STATUS != 1) {
			moveTaskToBack(true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dashboard_main);
		bindService();
		initView();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Settings.System.putInt(getContentResolver(), WARNSTART, 0);
		super.onPause();
		mHandler.removeMessages(Contacts.MSG_GET_MSG);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FirstShow = true;
		if (mISpService != null) {
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_ONCE_GET_MSG, 5000);
		}
		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCARTYPE(this);
	}

	protected void show(CanInfo caninfo) {
		// TODO Auto-generated method stub
		if (mCaninfo.CHANGE_STATUS == 10 || FirstShow) {
			FirstShow = false;
			if (caninfo.REMAIN_FUEL != -1)
				oil.setText(caninfo.REMAIN_FUEL + " L");
			if (caninfo.BATTERY_VOLTAGE != -1)
				battery.setText("剩余电量\n" + fnum.format(caninfo.BATTERY_VOLTAGE)
						+ " V");

			if (caninfo.FUEL_WARING_SIGN == 1) {
				icFuel.setColorFilter(Color.RED);
			} else {
				icFuel.setColorFilter(null);
			}

			if (caninfo.BATTERY_WARING_SIGN == 1) {
				icObdBattery.setColorFilter(Color.RED);
			} else {
				icObdBattery.setColorFilter(null);
			}

			if (caninfo.SAFETY_BELT_STATUS == 0) {
				safety_salt.setText("安全带\n正 常");
				icObdSeatBelt.setColorFilter(null);
			} else if (caninfo.SAFETY_BELT_STATUS == 1) {
				safety_salt.setText("安全带\n未 系");
				icObdSeatBelt.setColorFilter(Color.RED);
			} else {
				safety_salt.setText("安全带\n");
				icObdSeatBelt.setColorFilter(null);
			}

			if (caninfo.HANDBRAKE_STATUS == 0) {
				handbrake.setText("驻车制动\n正 常");
				icObdHandBrake.setColorFilter(null);
			} else {
				handbrake.setText("驻车制动\n未 放");
				icObdHandBrake.setColorFilter(Color.RED);
			}

			if (caninfo.DISINFECTON_STATUS == 0) {
				clean.setText("清洁液\n正 常");
				icObdWasherFluid.setColorFilter(null);
			} else if (caninfo.DISINFECTON_STATUS == 1) {
				clean.setText("清洁液\n过 低");
				icObdWasherFluid.setColorFilter(Color.RED);
			} else {
				clean.setText("清洁液\n");
				icObdWasherFluid.setColorFilter(null);
			}
			if (caninfo.ENGINE_SPEED != -1)
				enginee.setText((int) caninfo.ENGINE_SPEED + " RPM");
			driving_speed.setText((int) caninfo.DRIVING_SPEED + "KM/H");
			distance.setText("行驶里程\n " + caninfo.DRIVING_DISTANCE + "km");
			speedHand.setRotation(1.125f * caninfo.DRIVING_SPEED);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			moveTaskToBack(true);
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initView() {
		// TODO Auto-generated method stub
		oil = (TextView) findViewById(R.id.oil);
		battery = (TextView) findViewById(R.id.battery);
		safety_salt = (TextView) findViewById(R.id.safety_salt);
		handbrake = (TextView) findViewById(R.id.handbrake);
		clean = (TextView) findViewById(R.id.clean);
		enginee = (TextView) findViewById(R.id.enginee);
		driving_speed = (TextView) findViewById(R.id.driving_speed);
		distance = (TextView) findViewById(R.id.distance);
		speedHand = (ImageView) findViewById(R.id.speed_hand);
		icObdWasherFluid = (ImageView) findViewById(R.id.icObdWasherFluid);
		icObdSeatBelt = (ImageView) findViewById(R.id.icObdSeatBelt);
		icObdBattery = (ImageView) findViewById(R.id.icObdBattery);
		icObdHandBrake = (ImageView) findViewById(R.id.icObdHandBrake);
		icFuel = (ImageView) findViewById(R.id.icFuel);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unBindService();
		super.onDestroy();
	}

	private void bindService() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	private void unBindService() {
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		unbindService(mServiceConnection);
	}

	private ICanCallback mICallback = new ICanCallback.Stub() {

		@Override
		public void readDataFromServer(CanInfo canInfo) throws RemoteException {
			Message msg = new Message();
			msg.what = Contacts.MSG_UPDATA_UI;
			msg.obj = canInfo;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mISpService = ICanService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_ONCE_GET_MSG, 5000);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				mISpService.sendDataToSp(BytesUtil.addRZCCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
