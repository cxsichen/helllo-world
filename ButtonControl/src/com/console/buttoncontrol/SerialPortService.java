package com.console.buttoncontrol;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.console.buttoncontrol.utils.BytesUtil;
import com.console.buttoncontrol.utils.CircleBuffer;
import com.console.buttoncontrol.utils.Contacts;
import com.console.buttoncontrol.utils.KeyDealer;
import com.console.buttoncontrol.utils.Trace;
import android_serialport_api.SerialPort;

@SuppressLint("HandlerLeak")
public class SerialPortService extends Service {

	public final static String TAG = "SerialPortService";
	public final static int BUFFER_SIZE = 1024;

	private InputStream mInputStream;
	private Thread mReadDataFromSpThread;

	private KeyDealer mKeyDealer;
	static int[] keyGroup;
	KeyInfo mKeyInfo;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Contacts.MSG_DATA:
				int i = msg.arg1;
				if (mReaderCallback != null) {
					mReaderCallback.Callback(i);
				}
				if(mKeyInfo==null){
					mKeyInfo=new KeyInfo(SerialPortService.this);
				}
				if (keyGroup == null) {				
					keyGroup = mKeyInfo.getKeyGroup();
				}

				if(mKeyDealer==null){
					mKeyDealer=new KeyDealer(SerialPortService.this);
				}
				mKeyDealer.dealCanKeyEvent(SerialPortService.this,
						mKeyInfo.getKeyCommand(i));
				break;
			case 2:
				if (mKeyInfo == null) {
					mKeyInfo=new KeyInfo(SerialPortService.this);
				}
				KeyInfo.syncKeyCode(SerialPortService.this);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate() {
		Trace.i("SerialPortService onCreate");
		super.onCreate();
		try {
			mInputStream = SerialPort.getInstance().getInputStream();
			mReadDataFromSpThread = new ReadDataFromSpThread();
			mReadDataFromSpThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(mKeyDealer==null){
			mKeyDealer=new KeyDealer(SerialPortService.this);
		}
		if(mKeyInfo==null){
			mKeyInfo=new KeyInfo(SerialPortService.this);
		}
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE0),
						true, mkeyStateObserver);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE1),
						true, mkeyStateObserver);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE2),
						true, mkeyStateObserver);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE3),
						true, mkeyStateObserver);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE4),
						true, mkeyStateObserver);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE5),
						true, mkeyStateObserver);
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.DZ_KEYCODE6),
						true, mkeyStateObserver);
	}

	private keyStateObserver mkeyStateObserver = new keyStateObserver();

	public class keyStateObserver extends ContentObserver {
		public keyStateObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			mHandler.sendEmptyMessage(2);
		}
	}

	@Override
	public void onDestroy() {
		Trace.i("SerialPortService onDestroy");
		// 注销
		mInputStream = null;
		if (mReadDataFromSpThread != null) {
			Trace.i("SerialPortService mReadDataFromSpThread interrupt");
			if (!mReadDataFromSpThread.isInterrupted())
				mReadDataFromSpThread.interrupt();
		}
		super.onDestroy();
	}

	class ReadDataFromSpThread extends Thread {

		CircleBuffer buffer = new CircleBuffer(BUFFER_SIZE);

		@Override
		public void run() {
			Trace.d("ReadDataFromSpThread start...");
			while (mInputStream != null && !isInterrupted()) {
				try {
					byte data = (byte) mInputStream.read();
					Trace.d("data..." + data);
					readData(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void readData(byte data) throws IOException {
			for (int i = 0; i < KeyInfo.keyCode.length; i++) {
				if (data == KeyInfo.keyCode[i][0]) {
					byte data2 = (byte) mInputStream.read();
					Trace.d("data2..." + data2);
					if (data2 == KeyInfo.keyCode[i][1]) {
						dealWithPacketData(i);
					} else {
						readData(data2);
					}
				}
			}
		}

		private void dealWithPacketData(int i) {
			Message dataMsg = new Message();
			dataMsg.what = Contacts.MSG_DATA;
			dataMsg.arg1 = i;
			mHandler.sendMessage(dataMsg);
		}

		public void clearBuffer() {
			buffer.clearBuffer();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	// normal server binder

	public interface ReaderCallback {
		public void Callback(int KeyIndex);
	}

	ReaderCallback mReaderCallback;

	public void setCallback(ReaderCallback mReaderCallback) {
		this.mReaderCallback = mReaderCallback;
	}

	private CanServiceBinder myBinder = new CanServiceBinder();

	public class CanServiceBinder extends Binder {

		public SerialPortService getService() {
			return SerialPortService.this;
		}
	}

}
