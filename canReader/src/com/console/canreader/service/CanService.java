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

	// ������յ���Can��Ϣ
	private void dealCanInfo() {
		// TODO Auto-generated method stub
		// ����can��Ϣ���ͻ���
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

		// ����can��Ϣ���ͻ���2
		if (mReaderCallback != null) {
			mReaderCallback.Callback(info.getCanInfo());
		}

		// ����can��Ϣ�����¼�
		int parkingState = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.BACK_CAR, 0); // ������ʱ�򲻴����κε����¼�
		if (parkingState != 1) {
			DialogCreater.showUnlockWaringDialog(CanService.this, // ���ű����¼�����
																	// �Ƚ���Ҫ
																	// �ʶ�����һ��
					info.getCanInfo());
			switch (info.getCanInfo().CHANGE_STATUS) {
			case 2:
				mKeyDealer = KeyDealer.getInstance(CanService.this); // �����¼�����
				mKeyDealer.dealCanKeyEvent(CanService.this, info.getCanInfo());
				break;
			case 3:
				if (info.getCanInfo().AIR_CONDITIONER_CONTROL==0) {       //����µĿյ��е����Ŀ��ƽ��� ��AIR_CONDITIONER_CONTROL��1����������
					DialogCreater.showAirConDialog(CanService.this,
							info.getCanInfo(), // �յ��¼�����
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
				DialogCreater.showUnlockWaringInfo(CanService.this, // ������Ϣ��������
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
	 * ѡ�񴮿�
	 */
	private void chooseSerialPort() {
		switch (PreferenceUtil.getFirstTwoString(this, canName)) {
		case Contacts.CANFISRTNAMEGROUP.RAISE: // �־��
			switch (canName) {
			case Contacts.CANNAMEGROUP.RZCPeugeot: // ����
			case Contacts.CANNAMEGROUP.RZCBESTURNx80:// ����X80 ����M3
			case Contacts.CANNAMEGROUP.RZCFHCm3:
				initSerialPort(SERIAL_PORT_BT_19200);
				break;
			default:
				initSerialPort(SERIAL_PORT_BT_38400);
				break;
			}
			break;
		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // ����
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
	 * ��ʼ����д�߳�
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
	 * ���ô���
	 */
	private void resetSerialPort() {
		syncCanName();
		chooseSerialPort();
		// ���ԭ�ȵ�����
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
			// acc on���ԭ�ȵ�����   ���½�������
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

		// ��س��ͺ�Э��ѡ��
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CAN_CLASS_NAME),
				true, mCanNameObserver);
		
        //����ӽ������ӣ����ӷ��ͳ�ʼ���ݵ�����
		connectCanDevice();
		
		mKeyDealer = KeyDealer.getInstance(CanService.this); // ��ʼ�������¼�����
																// �����������Ӽ��ļ���
		getContentResolver().registerContentObserver(
				// ��������״̬��ȡ�������Ի���
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
					localIntent.setClass(CanService.this, CanService.class); // ����ʱ��������Service
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
						case Contacts.CANNAMEGROUP.RZCPeugeot: // ����
						case Contacts.CANNAMEGROUP.RZCBESTURNx80: // ����X80
							readRZCCanPort_1();
							break;
						case Contacts.CANNAMEGROUP.RZCFHCm3: // ����M3
							readRZCCanPort_2();
							break;
						default:
							readRZCCanPort();// �־��ͨ������֡
							break;
						}
						break;
					case Contacts.CANFISRTNAMEGROUP.HIWORLD:
						readSSCanPort(); // ��������֡
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

		/*-------------------��������֡----------start-------------*/
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

		// ����λ���ճɹ���Ӧ
		private void mSSACK(byte mode) {
			byte[] packet = new byte[6];
			packet[0] = (byte) Contacts.SS_HEAD_CODE_1;
			packet[1] = (byte) Contacts.SS_HEAD_CODE_2;
			packet[2] = 0x01;
			packet[3] = (byte) 0xFF;
			packet[4] = mode; // ��Ӧ��ģʽλ
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

		/*-------------------��������֡-----end------------------*/

		/*-------------------�־�Ͼ�����֡----start-----------------*/
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
							mOutputStream.write((byte) 0xff); // ACKӦ��
						// if (System.currentTimeMillis() - lastSendTime > 100)
						// {
						// lastSendTime = System.currentTimeMillis();
						dealWithPacketData(packet);
						// }
					} else {
						Trace.i("packet read failed");
						if (mOutputStream != null)
							mOutputStream.write((byte) 0x0f); // NACKӦ��
					}
				}
				packet = null;
			}

		}

		/*-------------------�־�Ͼ�����֡----------end-------------*/

		/*-------------------�־�ϵڶ�������֡-----start-----------------*/
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

		/*-------------------�־�ϵڶ�������֡---------end-------------*/

		/*-------------------�־�ϵ���������֡-----start-----------------*/
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

		/*-------------------�־�ϵ���������֡---------end-------------*/

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
	 * �յ������ȡ�ⲿ�¶�
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
	 * ����Can�豸��Ҫ���͵ĳ�ʼ������
	 */
	private void connectCanDevice() {
		// TODO Auto-generated method stub
		switch (canFirtName) {
		case Contacts.CANFISRTNAMEGROUP.RAISE: // �־��
			if (!canName.equals(Contacts.CANNAMEGROUP.RZCBESTURNx80)
					&& !canName.equals(Contacts.CANNAMEGROUP.RZCFHCm3)
					&&  !canName.equals(Contacts.CANNAMEGROUP.RZCPeugeot))// ����X80
																		// ����M3
																		// ����
				writeCanPort(BytesUtil.addRZCCheckBit(Contacts.CONNECTMSG));
			break;
		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // ����
			if (canName.equals(Contacts.CANNAMEGROUP.SSToyota)){
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0103"));  //����RAV4 �ٷ�
			}
			break;
		default:
			break;
		}

	}

}
