package com.console.auxapp;

import com.console.auxapp.util.BytesUtil;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.preference.Preference;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;
import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;


public class SerialPortControl {

	Context context;
	private ISerialPortService mISpService;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(mDataCallback!=null)
					mDataCallback.OnChange((byte[]) msg.obj);
				break;
			default:
				break;
			}
		}
	};

	private ISerialPortCallback mICallback = new ISerialPortCallback.Stub() {

		@Override
		public void readDataFromServer(byte[] bytes) throws RemoteException {

			Message msg = new Message();
			msg.what = 1;
			msg.obj = bytes;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			mISpService = ISerialPortService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	public void bindSpService() {
		try {
			Intent intent = new Intent();
			intent.setClassName("cn.colink.serialport",
					"cn.colink.serialport.service.SerialPortService");
			context.bindService(intent, mServiceConnection, context.BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unbindSpService() {
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			context.unbindService(mServiceConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SerialPortControl(Context context) {
		this.context = context;
		bindSpService();
	}

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				mISpService.sendDataToSp(BytesUtil.addCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	



	public interface DataCallback {
		public void OnChange(byte[] value);
	}

	DataCallback mDataCallback;

	public void setDataCallback(DataCallback dataCallback) {
		this.mDataCallback = dataCallback;
	}

}
