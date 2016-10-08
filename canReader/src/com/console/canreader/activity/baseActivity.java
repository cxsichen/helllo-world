package com.console.canreader.activity;

import com.console.canreader.GuideActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.service.CanService;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
import com.console.canreader.service.CanService.CanTypeObserver;
import com.console.canreader.service.CanService.CarTypeObserver;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Window;

public class baseActivity extends Activity {
	CanInfo mCaninfo;

	private int canType = -1; // 盒子厂家 0：睿志诚 1：尚摄
	private int carType = -1; // 车型 0:大众

	/**
	 * 数据变化 需要改变界面的时候调用
	 */
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
	}

	/**
	 * 服务意外断开链接时调用
	 */
	public void serviceDisconnected() {
		// TODO Auto-generated method stub

	}

	/**
	 * 服务链接时调用
	 */
	public void serviceConnected() {
		// TODO Auto-generated method stub
		firstShow();
	}

	private void firstShow() {
		// TODO Auto-generated method stub
		try {
			if (mISpService != null) {
				Message msg = new Message();
				msg.what = Contacts.MSG_UPDATA_UI;
				CanInfo tempInfo = mISpService.getCanInfo();
				// this page only deal with the type 10
				if (tempInfo != null) {
					tempInfo.CHANGE_STATUS = 10;
					msg.obj = tempInfo;
					mHandler.sendMessage(msg);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				mCaninfo = (CanInfo) msg.obj;
				if (mCaninfo != null) {
					if(mCaninfo.CHANGE_STATUS == 10)
					   show(mCaninfo);
				}
				break;
			case Contacts.MSG_SERVICE_CONNECTED:
				serviceConnected();
				break;
			case Contacts.MSG_SERVICE_DISCONNECTED:
				serviceDisconnected();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		bindService();

		// 监控车型和协议选择
		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCANTYPE(this);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CANTYPE),
				true, mCanTypeObserver);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CARTYPE),
				true, mCarTypeObserver);

	}

	private CanTypeObserver mCanTypeObserver = new CanTypeObserver();

	public class CanTypeObserver extends ContentObserver {
		public CanTypeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (canType != PreferenceUtil.getCANTYPE(baseActivity.this))
				finish();

		}
	}

	private CarTypeObserver mCarTypeObserver = new CarTypeObserver();

	public class CarTypeObserver extends ContentObserver {
		public CarTypeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (carType != PreferenceUtil.getCARTYPE(baseActivity.this))
				finish();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		firstShow();
		try {
			if (mISpService != null)
				mISpService.addClient(mICallback);
			else {
				bindService();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unBindService();
		getContentResolver().unregisterContentObserver(mCanTypeObserver);
		getContentResolver().unregisterContentObserver(mCarTypeObserver);
	}

	/**
	 * bind service
	 */
	private ICanService mISpService;
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
			mHandler.sendEmptyMessage(Contacts.MSG_SERVICE_CONNECTED);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
			mHandler.sendEmptyMessage(Contacts.MSG_SERVICE_DISCONNECTED);
		}
	};

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
