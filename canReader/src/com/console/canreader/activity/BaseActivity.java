package com.console.canreader.activity;

import com.console.canreader.GuideActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.service.CanService;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
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
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

public class BaseActivity extends FragmentActivity {
	public CanInfo mCaninfo;

	 Boolean IsResume=false;
	 public static String canName="";
	 public static String canFirtName="";

	/**
	 * 数据变化 需要改变界面的时候调用 界面获取焦点和有数据反馈的时候会调用
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
	
	public CanInfo getCanInfo() throws RemoteException{
		CanInfo tempInfo=null;
		if(mISpService!=null)
			tempInfo = mISpService.getCanInfo();
		return tempInfo;
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
					if (mCaninfo.CHANGE_STATUS == 10&&IsResume)
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
		bindService();

		// 监控车型和协议选择
		syncCanName();
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CAN_CLASS_NAME),
				true, mCanNameObserver);

	}

	private CanNameObserver mCanNameObserver = new CanNameObserver();

	public class CanNameObserver extends ContentObserver {
		public CanNameObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (!canName.equals( PreferenceUtil.getCANName(BaseActivity.this)))
				finish();

		}
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IsResume=true;
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
		IsResume=false;
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
		getContentResolver().unregisterContentObserver(mCanNameObserver);
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
				switch (canFirtName) {
				case Contacts.CANFISRTNAMEGROUP.RAISE:
					mISpService.sendDataToSp(BytesUtil.addRZCCheckBit(msg));
					break;
				case Contacts.CANFISRTNAMEGROUP.HIWORLD:					
					mISpService.sendDataToSp(BytesUtil.addSSCheckBit(msg));
					break;
				default:
					break;
				}

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void syncCanName(){
		canName = PreferenceUtil.getCANName(this);
		canFirtName=PreferenceUtil.getFirstTwoString(this, canName);
	}

}
