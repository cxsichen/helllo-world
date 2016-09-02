package com.console.canreader.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.console.canreader.R;
import com.console.canreader.bean.AnalyzeUtils;
import com.console.canreader.bean.BeanFactory;
import com.console.canreader.dealer.DialogCreater;
import com.console.canreader.dealer.KeyDealer;
import com.console.canreader.dealer.DialogCreater.CallBack;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.Trace;
import com.console.canreader.view.AirConDialog;
import com.console.canreader.view.UnlockWaringDialog;

import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android_serialport_api.SerialPort;

public class CanService extends Service {

	private static final String TAG = "BindService";
	public final static int BUFFER_SIZE = 1024;

	private InputStream mInputStream;
	private OutputStream mOutputStream;

	private Thread mReadDataFromSpThread;
	private Thread mSendDataToSpThread;
	private KeyDealer mKeyDealer;
	AnalyzeUtils info = null;
	public static final Object lock1 = new Object();

	private int canType = -1; // 盒子厂家 0：睿志诚 1：尚摄
	private int carType = -1; // 车型 0:大众

	interface SerialPortWriteTask {
		public void excute();
	}

	public interface ReaderCallback {
		public void Callback(CanInfo mCanInfo);
	}

	private BlockingQueue<SerialPortWriteTask> mBlockingQueue = new LinkedBlockingQueue<SerialPortWriteTask>();

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Contacts.MSG_DATA:
				byte[] mPacket = (byte[]) msg.obj;
				// Broadcast to all clients the new value.
				Trace.i("packet : " + BytesUtil.bytesToHexString(mPacket));
				Log.i("cxs", "==========handleMessage===========");
				info = BeanFactory.getCanInfo(CanService.this, mPacket,
						canType, carType);
				if (info != null)
					if (info.getCanInfo() != null)
						dealCanInfo();
				break;
			default:
				break;
			}
		}

	};

	// 处理接收到的Can信息
	private void dealCanInfo() {
		// TODO Auto-generated method stub
		// 发送can信息到客户端
		final int N = mCallbacks.beginBroadcast();
		try {
			for (int i = 0; i < N; i++)
				mCallbacks.getBroadcastItem(i).readDataFromServer(
						info.getCanInfo());
		} catch (RemoteException e) {
			// The RemoteCallbackList will take care of removing
			// the dead object for us.
		}
		mCallbacks.finishBroadcast();

		// 发送can信息到客户端2
		if (mReaderCallback != null) {
			mReaderCallback.Callback(info.getCanInfo());
		}

		// 根据can信息处理事件
		int parkingState = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.BACK_CAR, 0); // 倒车的时候不处理任何弹框事件
		if (parkingState != 1) {
			DialogCreater.showUnlockWaringDialog(CanService.this, // 车门报警事件处理
																	// 比较重要
																	// 故都处理一下
					info.getCanInfo());
			switch (info.getCanInfo().CHANGE_STATUS) {
			case 2:
				mKeyDealer = KeyDealer.getInstance(CanService.this); // 按键事件处理
				mKeyDealer.dealCanKeyEvent(CanService.this, info.getCanInfo());
				break;
			case 3:
				DialogCreater.showAirConDialog(CanService.this,
						info.getCanInfo(), // 空调事件处理
						new CallBack() {

							@Override
							public void sendShowMsg() {
								// TODO Auto-generated method stub
								if (canType == 0 && carType == 0)
									writeCanPort(BytesUtil
											.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO));
								if (canType == 0 && carType == 1)
									writeCanPort(BytesUtil
											.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO_0_1));

							}
						});
				break;
			case 10:
				DialogCreater.showUnlockWaringInfo(CanService.this, // 车身信息报警处理
						info.getCanInfo());
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			mInputStream = SerialPort.getInstance().getInputStream();
			mOutputStream = SerialPort.getInstance().getOutputStream();
			mReadDataFromSpThread = new ReadDataFromSpThread();
			mReadDataFromSpThread.start();
			mSendDataToSpThread = new SendDataToSpThread();
			mSendDataToSpThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	private AccStateObserver mAccStateObserver = new AccStateObserver();

	public class AccStateObserver extends ContentObserver {
		public AccStateObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Contacts.ACC_STATE, 0);
			handleAccState(state);
		}
	}

	private void handleAccState(int state) {
		// TODO Auto-generated method stub
		if (state == 1) {
			// acc on清空原先的数据
			BeanFactory.setInfoEmpty();
		}
	}

	private BackCarObserver mBackCarObserver = new BackCarObserver();

	public class BackCarObserver extends ContentObserver {
		public BackCarObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Contacts.BACK_CAR, 0);
			if (state == 1)
				DialogCreater.closeAllWaringDialog();
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCARTYPE(this);
		// 监控车型和协议选择
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CANTYPE),
				true, mCanTypeObserver);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CARTYPE),
				true, mCarTypeObserver);

		connectCanDevice();
		mKeyDealer = KeyDealer.getInstance(CanService.this); // 初始化按键事件处理
																// 里面有音量加减的监听
		getContentResolver().registerContentObserver(
				// 监听倒车状态，取消报警对话框
				android.provider.Settings.System.getUriFor(Contacts.BACK_CAR),
				true, mBackCarObserver);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.ACC_STATE),
				true, mAccStateObserver);
	}

	private CanTypeObserver mCanTypeObserver = new CanTypeObserver();

	public class CanTypeObserver extends ContentObserver {
		public CanTypeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			canType = PreferenceUtil.getCANTYPE(CanService.this);
			// 清空原先的数据
			BeanFactory.setInfoEmpty();
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
			carType = PreferenceUtil.getCARTYPE(CanService.this);
			// 清空原先的数据
			BeanFactory.setInfoEmpty();
		}
	}

	private void connectCanDevice() {
		// TODO Auto-generated method stub
		switch (canType) {
		case 0: // 睿志诚
			writeCanPort(BytesUtil.addRZCCheckBit(Contacts.CONNECTMSG));
			break;
		case 1: // 尚摄
			break;
		default:
			break;
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (mReadDataFromSpThread != null) {
			Trace.i("SerialPortService mReadDataFromSpThread interrupt");
			if (!mReadDataFromSpThread.isInterrupted())
				mReadDataFromSpThread.interrupt();
		}
		if (mSendDataToSpThread != null) {
			Trace.i("SerialPortService mSendDataToSpThread interrupt");
			if (!mSendDataToSpThread.isInterrupted())
				mSendDataToSpThread.interrupt();
		}
		getContentResolver().unregisterContentObserver(mBackCarObserver);
		mInputStream = null;
		mOutputStream = null;
		super.onDestroy();
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Intent localIntent = new Intent();
					localIntent.setClass(CanService.this, CanService.class); // 销毁时重新启动Service
					startService(localIntent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		}, 1000 * 10);
	}

	private class SendDataToSpThread extends Thread {
		@Override
		public void run() {
			Trace.d("SendDataToSpThread start...");
			while (mOutputStream != null && !isInterrupted()) {
				try {
					SerialPortWriteTask task = mBlockingQueue.take();
					task.excute();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public SerialPortWriteTask getTask(final String hexString) {

		SerialPortWriteTask task = new SerialPortWriteTask() {
			@Override
			public void excute() {
				byte[] packet = BytesUtil.hexStringToBytes(hexString);
				Trace.d("send byte to serial prot : "
						+ BytesUtil.bytesToHexString(packet));
				synchronized (lock1) {
					try {
						mOutputStream.write(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};

		return task;
	}

	public void writeCanPort(String hexString) {
		try {
			mBlockingQueue.put(getTask(hexString));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ReadDataFromSpThread extends Thread {

		@Override
		public void run() {
			Trace.d("ReadDataFromSpThread start...");
			while (mInputStream != null && !isInterrupted()) {
				try {
					switch (canType) {
					case 0:
						readRZCCanPort(); // 睿志诚数据帧
						break;
					case 1:
						readSSCanPort(); // 尚摄数据帧
						break;
					default:
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		long lastSendTime = 0;

		private byte mReadByte() {
			if (mInputStream != null) {
				byte data = 0;
				try {
					data = (byte) mInputStream.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return data;
			}
			return 0;
		}

		/*-------------------尚摄数据帧----------start-------------*/
		private boolean isSSAValidPacket(final byte[] packet) {
			byte sum = 0;

			for (int i = 2; i < packet.length - 1; i++) {
				sum += packet[i];
			}
			// sum = (byte) ((byte) 0xff - sum);
			sum = (byte) (((byte) sum & (byte) 0xFF) - 1);
			if (packet[packet.length - 1] == sum) {
				return true;
			}
			return false;
		}

		// 数据位接收成功响应
		private void mSSACK(byte mode) {
			byte[] packet = new byte[6];
			packet[0] = (byte) Contacts.SS_HEAD_CODE_1;
			packet[1] = (byte) Contacts.SS_HEAD_CODE_2;
			packet[2] = 0x01;
			packet[3] = (byte) 0xFF;
			packet[4] = mode; // 对应的模式位
			packet[5] = (byte) (((packet[2] + packet[3] + packet[4]) & (byte) 0xff) - 1);
			if (mOutputStream != null) {
				try {
					mOutputStream.write(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void readSSCanPort() throws IOException {
			// TODO Auto-generated method stub
			byte data1 = mReadByte();
			byte data2 = mReadByte();
			if (data1 == (byte) Contacts.SS_HEAD_CODE_1
					&& data2 == (byte) Contacts.SS_HEAD_CODE_2) {
				byte len = mReadByte();
				byte mode = mReadByte();
				byte[] packet = new byte[len + 5];
				packet[0] = (byte) Contacts.SS_HEAD_CODE_1;
				packet[1] = (byte) Contacts.SS_HEAD_CODE_2;
				packet[2] = len;
				packet[3] = mode;
				for (int i = 0; i < len + 1; i++) {
					packet[4 + i] = mReadByte();
				}
				synchronized (lock1) {
					if (isSSAValidPacket(packet)) {
						if (mOutputStream != null) {
							mSSACK(mode);
						}

						// if (System.currentTimeMillis() - lastSendTime > 100)
						// {
						// lastSendTime = System.currentTimeMillis();
						dealWithPacketData(packet);
						// }
					} else {
						Trace.i("packet read failed");
						if (mOutputStream != null)
							mOutputStream.write((byte) 0x0f);
					}
				}
				packet = null;
			}

		}

		/*-------------------尚摄数据帧-----end------------------*/

		/*-------------------睿志诚据帧-----start-----------------*/
		private boolean isRZCAValidPacket(final byte[] packet) {
			byte sum = 0;

			for (int i = 1; i < packet.length - 1; i++) {
				sum += packet[i];
			}
			sum = (byte) ((byte) 0xff - sum);
			if (packet[packet.length - 1] == sum) {
				return true;
			}
			return false;
		}

		private void readRZCCanPort() throws IOException {
			// TODO Auto-generated method stub
			byte data = (byte) mInputStream.read();
			if (data == Contacts.VOLK_HEAD_CODE) {
				Trace.i("packet : " + data);
				byte mode = mReadByte();
				byte len = mReadByte();
				byte[] packet = new byte[len + 4];
				packet[0] = Contacts.VOLK_HEAD_CODE;
				packet[1] = mode;
				packet[2] = len;
				for (int i = 0; i < len + 1; i++) {
					packet[3 + i] = mReadByte();
				}
				synchronized (lock1) {
					if (isRZCAValidPacket(packet)) {
						if (mOutputStream != null)
							mOutputStream.write((byte) 0xff); // ACK应答
						// if (System.currentTimeMillis() - lastSendTime > 100)
						// {
						// lastSendTime = System.currentTimeMillis();
						dealWithPacketData(packet);
						// }
					} else {
						Trace.i("packet read failed");
						if (mOutputStream != null)
							mOutputStream.write((byte) 0x0f); // NACK应答
					}
				}
				packet = null;
			}

		}

		/*-------------------睿志诚据帧----------end-------------*/

		private void dealWithPacketData(byte[] packet) {
			Message dataMsg = new Message();
			dataMsg.what = Contacts.MSG_DATA;
			dataMsg.obj = packet;
			mHandler.sendMessage(dataMsg);
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	// normal server binder

	ReaderCallback mReaderCallback;

	public void setCallback(ReaderCallback mReaderCallback) {
		this.mReaderCallback = mReaderCallback;
	}

	private CanServiceBinder myBinder = new CanServiceBinder();

	public class CanServiceBinder extends Binder {

		public CanService getService() {
			return CanService.this;
		}
	}

	public void sendDataToSp(final String hexString) throws RemoteException {
		try {
			mBlockingQueue.put(getTask(hexString));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	// aidl server binder
	private final RemoteCallbackList<ICanCallback> mCallbacks = new RemoteCallbackList<ICanCallback>();

	private final ICanService.Stub mBinder = new ICanService.Stub() {

		@Override
		public void sendDataToSp(final String hexString) throws RemoteException {
			try {
				mBlockingQueue.put(getTask(hexString));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void addClient(ICanCallback client) throws RemoteException {
			if (client != null) {
				mCallbacks.register(client);
			}
		}

		@Override
		public void removeCliend(ICanCallback client) throws RemoteException {
			if (client != null) {
				mCallbacks.unregister(client);
			}
		}

		@Override
		public CanInfo getCanInfo() throws RemoteException {
			// TODO Auto-generated method stub
			if (info != null)
				return info.getCanInfo();
			else
				return null;
		}

	};

}
