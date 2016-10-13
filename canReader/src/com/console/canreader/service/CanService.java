package com.console.canreader.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
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
	private static int SERIAL_PORT_BT_38400 = 38400;
	private static int SERIAL_PORT_BT_19200 = 19200;

	private InputStream mInputStream;
	private OutputStream mOutputStream;

	private Thread mReadDataFromSpThread;
	private Thread mSendDataToSpThread;
	private KeyDealer mKeyDealer;
	AnalyzeUtils info = null;
	public static final Object lock1 = new Object();

    private String canName="";
    private String canFirtName="";
	
	public static final String KEYCODE_VOLUME_UP = "com.console.KEYCODE_VOLUME_UP";
	public static final String KEYCODE_VOLUME_DOWN = "com.console.KEYCODE_VOLUME_DOWN";

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
				info = BeanFactory.getCanInfo(CanService.this, mPacket,
						canName);
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
				if (info.getCanInfo().AIR_CONDITIONER_CONTROL==0) {       //像标致的空调有单独的控制界面 则AIR_CONDITIONER_CONTROL置1，不弹界面
					DialogCreater.showAirConDialog(CanService.this,
							info.getCanInfo(), // 空调事件处理
							new CallBack() {

								@Override
								public void sendShowMsg() {
									// TODO Auto-generated method stub
								   sendMsgGetTemp();

								}
							});
				}
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
		syncCanName();
		chooseSerialPort();
		initSerialPortThread();
		init();
	}
	
	private void syncCanName(){
		canName = PreferenceUtil.getCANName(this);
		canFirtName=PreferenceUtil.getFirstTwoString(this, canName);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(intent!=null){
		String command=intent.getStringExtra("keyEvent");
		if(command!=null){
			dealCommand(command);
		}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	/**
	 * deal the user's requset  that send to this service
	 * @param command
	 */	
	private void dealCommand(String command){
		if(mKeyDealer==null)
			mKeyDealer=KeyDealer.getInstance(this);
		switch (command) {
		case KEYCODE_VOLUME_UP:
			mKeyDealer.handleVolUp();
			break;
		case KEYCODE_VOLUME_DOWN:
			mKeyDealer.handleVolDown();
			break;
		default:
			break;
		}
	}

	/**
	 * 选择串口
	 */
	private void chooseSerialPort() {
		switch (PreferenceUtil.getFirstTwoString(this, canName)) {
		case Contacts.CANFISRTNAMEGROUP.RAISE: // 睿志诚
			switch (canName) {
			case Contacts.CANNAMEGROUP.RZCPeugeot: // 标致
			case Contacts.CANNAMEGROUP.RZCBESTURNx80:// 奔腾X80 海马M3
			case Contacts.CANNAMEGROUP.RZCFHCm3:
				initSerialPort(SERIAL_PORT_BT_19200);
				break;
			default:
				initSerialPort(SERIAL_PORT_BT_38400);
				break;
			}
			break;
		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // 尚摄
			initSerialPort(SERIAL_PORT_BT_38400);
			break;
		default:
			initSerialPort(SERIAL_PORT_BT_38400);
			break;
		}
	}

	private void initSerialPort(int port) {
		try {
			mInputStream = SerialPort.getInstance(port).getInputStream();
			mOutputStream = SerialPort.getInstance(port).getOutputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化读写线程
	 */
	private void initSerialPortThread() {
		try {
			mReadDataFromSpThread = new ReadDataFromSpThread();
			mReadDataFromSpThread.start();
			mSendDataToSpThread = new SendDataToSpThread();
			mSendDataToSpThread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 重置串口
	 */
	private void resetSerialPort() {
		syncCanName();
		chooseSerialPort();
		// 清空原先的数据
		BeanFactory.setInfoEmpty();
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
			// acc on清空原先的数据   重新建立连接
			BeanFactory.setInfoEmpty();
			connectCanDevice();
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

		// 监控车型和协议选择
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CAN_CLASS_NAME),
				true, mCanNameObserver);
		
        //与盒子建立连接，盒子发送初始数据到车机
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

	private CanNameObserver mCanNameObserver = new CanNameObserver();

	public class CanNameObserver extends ContentObserver {
		public CanNameObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			syncCanName();
			resetSerialPort();

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
		getContentResolver().unregisterContentObserver(mCanNameObserver);	
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
					} catch (Exception e) {
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
					switch (canFirtName) {
					case Contacts.CANFISRTNAMEGROUP.RAISE:
						switch (canName) {
						case Contacts.CANNAMEGROUP.RZCPeugeot: // 标致
						case Contacts.CANNAMEGROUP.RZCBESTURNx80: // 奔腾X80
							readRZCCanPort_1();
							break;
						case Contacts.CANNAMEGROUP.RZCFHCm3: // 海马M3
							readRZCCanPort_2();
							break;
						default:
							readRZCCanPort();// 睿志诚通用数据帧
							break;
						}
						break;
					case Contacts.CANFISRTNAMEGROUP.HIWORLD:
						readSSCanPort(); // 尚摄数据帧
						break;
					default:
						break;
					}
				} catch (Exception e) {
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

		/*-------------------睿志诚据数据帧----start-----------------*/
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

		/*-------------------睿志诚据数据帧----------end-------------*/

		/*-------------------睿志诚第二种数据帧-----start-----------------*/
		private boolean isRZCAValidPacket_1(final byte[] packet) {
			byte sum = 0;

			for (int i = 1; i < packet.length - 1; i++) {
				sum += packet[i];
			}
			sum = (byte) (sum & 0xff);
			if (packet[packet.length - 1] == sum) {
				return true;
			}
			return false;
		}

		private void readRZCCanPort_1() throws IOException {
			// TODO Auto-generated method stub
			byte data = (byte) mInputStream.read();
			if (data == (byte) Contacts.VOLK_HEAD_CODE_1) {
				Trace.i("packet : " + data);
				byte len = mReadByte();
				byte mode = mReadByte();
				byte[] packet = new byte[len + 1];
				packet[0] = (byte) Contacts.VOLK_HEAD_CODE_1;
				packet[1] = len;
				packet[2] = mode;
				for (int i = 3; i < len + 1; i++) {
					packet[i] = mReadByte();
				}
				synchronized (lock1) {
					if (isRZCAValidPacket_1(packet)) {
						dealWithPacketData(packet);
					} else {
						Trace.i("packet read failed");
					}
				}
				packet = null;
			}

		}

		/*-------------------睿志诚第二种数据帧---------end-------------*/

		/*-------------------睿志诚第三种数据帧-----start-----------------*/
		private boolean isRZCAValidPacket_2(final byte[] packet) {
			byte sum = 0;

			for (int i = 1; i < packet.length - 1; i++) {
				sum += packet[i];
			}
			sum = (byte) (sum & 0xff);
			if (packet[packet.length - 1] == sum) {
				return true;
			}
			return false;
		}

		private void readRZCCanPort_2() throws IOException {
			// TODO Auto-generated method stub
			byte data = (byte) mInputStream.read();
			byte len = 0;
			byte mode = 0;
			byte[] packet = null;
			switch (data) {
			case (byte) Contacts.VOLK_HEAD_CODE_2:
				len = mReadByte();
				mode = mReadByte();
				packet = new byte[len + 1];
				packet[0] = (byte) Contacts.VOLK_HEAD_CODE_2;
				packet[1] = len;
				packet[2] = mode;
				for (int i = 3; i < len + 1; i++) {
					packet[i] = mReadByte();
				}
				break;
			case (byte) Contacts.VOLK_HEAD_CODE_1:
				packet = new byte[6];
				packet[0] = (byte) Contacts.VOLK_HEAD_CODE_1;
				for (int i = 1; i < 6; i++) {
					packet[i] = mReadByte();
				}
				break;
			default:
				break;
			}

			if (packet != null) {
				synchronized (lock1) {
					if (isRZCAValidPacket_2(packet)) {
						dealWithPacketData(packet);
					} else {
						Trace.i("packet read failed");
					}
				}
				packet = null;
			}

		}

		/*-------------------睿志诚第三种数据帧---------end-------------*/

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
	
	/**
	 * 空调界面获取外部温度
	 */	
	private void sendMsgGetTemp(){
		if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagen))
			writeCanPort(BytesUtil
					.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO));
		if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagenGolf))
			writeCanPort(BytesUtil
					.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO_0_1));
	}
	
	/**
	 * 连接Can设备需要发送的初始化命令
	 */
	private void connectCanDevice() {
		// TODO Auto-generated method stub
		switch (canFirtName) {
		case Contacts.CANFISRTNAMEGROUP.RAISE: // 睿志诚
			if (!canName.equals(Contacts.CANNAMEGROUP.RZCBESTURNx80)
					&& !canName.equals(Contacts.CANNAMEGROUP.RZCFHCm3)
					&&  !canName.equals(Contacts.CANNAMEGROUP.RZCPeugeot))// 奔腾X80
																		// 海马M3
																		// 标致
				writeCanPort(BytesUtil.addRZCCheckBit(Contacts.CONNECTMSG));
			break;
		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // 尚摄
			if (canName.equals(Contacts.CANNAMEGROUP.SSToyota)){
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0103"));  //丰田RAV4 荣放
			}
			break;
		default:
			break;
		}

	}

}
