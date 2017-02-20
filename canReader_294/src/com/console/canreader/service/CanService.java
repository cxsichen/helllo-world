package com.console.canreader.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import android.provider.Settings;
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

	private String canName = "";
	private String canFirtName = "";

	public static final String KEYCODE_VOLUME_UP = "com.console.KEYCODE_VOLUME_UP";
	public static final String KEYCODE_VOLUME_DOWN = "com.console.KEYCODE_VOLUME_DOWN";
	public static final String KEYCODE_VOLUME_MUTE = "com.console.KEYCODE_VOLUME_MUTE";
	
	public static final String KEYCODE_BACK = "com.console.KEYCODE_BACK";
	public static final String KEYCODE_FM = "com.console.KEYCODE_FM";
	public static final String KEYCODE_NAV = "com.console.KEYCODE_NAV";
	
	public static final String ACTION_MENU_UP = "com.console.MENU_UP";
	public static final String ACTION_MENU_DOWN = "com.console.MENU_DOWN";
	public static final String ACTION_PLAY_PAUSE = "com.console.PLAY_PAUSE";
	public static final String ACTION_CONCOSOLE_FORCESTOP_PACKAGE = "com.console.ACTION_CONCOSOLE_FORCESTOP_PACKAGE";

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
				//打印数据到txt文件保存
				//put("packet : "+ BytesUtil.bytesToHexString(mPacket));
				info = BeanFactory
						.getCanInfo(CanService.this, mPacket, canName);
				if (info != null)
					if (info.getCanInfo() != null)
						if (info.getCanInfo().CHANGE_STATUS != 8888)
							dealCanInfo(info);
				break;
			case Contacts.MSG_MSG_CYCLE:
				sendCycleMsg();
				break;
			default:
				break;
			}
		}
	};
	
	  public void put(String info){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = sdf.format(new Date());  
      String fileName = info + "-" + time;
      Log.i("cxs", "cxs fileName===="+fileName);
			BufferedWriter out = null;  
      try {  
          out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/sdcard/"+"canReader_log.txt", true)));  
          out.write(fileName);  
          out.write("\n");
      } catch (Exception e) {  
          e.printStackTrace();  
          Log.i("cxs", "cxs e===="+e);
      } finally {  
          try {  
              out.close();  
          } catch (IOException e) {  
              e.printStackTrace();  
              Log.i("cxs", "cxs e===="+e);
          }  
      } 
		}	

	// 处理接收到的Can信息
	private void dealCanInfo(AnalyzeUtils info2) {
		// TODO Auto-generated method stub
		// 发送can信息到客户端
		final int N = mCallbacks.beginBroadcast();
		try {
			for (int i = 0; i < N; i++)
				mCallbacks.getBroadcastItem(i).readDataFromServer(
						info2.getCanInfo());
		} catch (RemoteException e) {
			// The RemoteCallbackList will take care of removing
			// the dead object for us.
		}
		mCallbacks.finishBroadcast();

		// 发送can信息到客户端2
		if (mReaderCallback != null) {
			mReaderCallback.Callback(info2.getCanInfo());
		}

		if (info.getCanInfo().CHANGE_STATUS == 20) {
			DialogCreater.dealPanoramaEvent(CanService.this, // 360全景处理
					info2.getCanInfo());
		}

		// 根据can信息处理事件
		int parkingState = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.BACK_CAR, 0); // 倒车的时候不处理任何弹框事件

		if (parkingState != 1) {

			switch (info2.getCanInfo().CHANGE_STATUS) {
			case 2:
				mKeyDealer = KeyDealer.getInstance(CanService.this); // 按键事件处理
				mKeyDealer.dealCanKeyEvent(CanService.this, info2.getCanInfo());
				break;
			case 3:
				DialogCreater.showAirConDialog(CanService.this,
						info2.getCanInfo(), // 空调事件处理
						new CallBack() {

							@Override
							public void sendShowMsg() {
								// TODO Auto-generated method stub
								sendMsgGetTemp();

							}
						});

				break;
			case 10:
				DialogCreater.showUnlockWaringInfo(CanService.this, // 车身信息报警处理
						info2.getCanInfo());
				DialogCreater.showUnlockWaringDialog(CanService.this, // 车门报警事件处理
						info2.getCanInfo());
				break;
			case 12:
				DialogCreater.showCarInfoWaring(CanService.this, // 标致408显示报警信息
						info2.getCanInfo());
				break;
			case 13:
				DialogCreater.showFloatCarInfoWaring(CanService.this, // 显示浮窗报警信息
						info2.getCanInfo());
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

	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if (intent != null) {
			String command = intent.getStringExtra("keyEvent");
			String commandArg = intent.getStringExtra("keyEventArg");
			if (command != null) {
				dealCommand(command,commandArg);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * deal the user's requset that send to this service
	 * 
	 * @param command
	 */
	private void dealCommand(String command,String commandArg ) {

		if (mKeyDealer == null)
			mKeyDealer = KeyDealer.getInstance(this);
		switch (command) {
		case KEYCODE_VOLUME_UP:
			mKeyDealer.handleVolUp();
			break;
		case KEYCODE_VOLUME_DOWN:
			mKeyDealer.handleVolDown();
			break;
		case KEYCODE_VOLUME_MUTE:
			mKeyDealer.handleMute();
			break;
		case KEYCODE_BACK:
			mKeyDealer.handleBACK();
			break;
		case KEYCODE_FM:
			mKeyDealer.handleFmAm();
			break;
		case KEYCODE_NAV:
			mKeyDealer.startNavi();
			break;	
		case ACTION_PLAY_PAUSE:
			mKeyDealer.handleMUSIC_PLAY_PAUSE();
			break;
		case ACTION_MENU_UP:
			mKeyDealer.handleMenuUp();
			break;
		case ACTION_MENU_DOWN:
			mKeyDealer.handleMenuDown();
			break;
		case ACTION_CONCOSOLE_FORCESTOP_PACKAGE:
			mKeyDealer.handleForceStopPackage(commandArg);
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
			//case Contacts.CANNAMEGROUP.RZCBESTURNx80:// 奔腾X80 
			case Contacts.CANNAMEGROUP.RZCFHCm3:  //海马M3
				initSerialPort(SERIAL_PORT_BT_19200);
				break;
			default:
				initSerialPort(SERIAL_PORT_BT_38400);
				break;
			}
			break;
		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // 尚摄
			switch (canName) {
			case Contacts.CANNAMEGROUP.SSHonda12CRV:
				initSerialPort(SERIAL_PORT_BT_19200);
				break;
			default:
				initSerialPort(SERIAL_PORT_BT_38400);
				break;
			}
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
		clearData();
	}

	// 清空原先的数据
	private void clearData() {
		BeanFactory.setInfoEmpty();
		info = null;
		DialogCreater.clearUnlockWaringData(this);
	}

	private TailDoorObserver mTailDoorObserver = new TailDoorObserver();

	public class TailDoorObserver extends ContentObserver {
		public TailDoorObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Contacts.TAILDOORSTATUS, 0);

			handleTailDoorState(state);
		}
	}

	private void handleTailDoorState(int state) {
		// TODO Auto-generated method stub
		info = BeanFactory.getCanInfo(CanService.this, null, canName);

		if (info == null) {
			return;
		}
		if (state == 1) {
			info.getCanInfo().TRUNK_STATUS = 1;
		} else {
			info.getCanInfo().TRUNK_STATUS = 0;
		}
		info.getCanInfo().CHANGE_STATUS = 10;
		Message dataMsg = new Message();
		dataMsg.what = Contacts.MSG_DATA;
		dataMsg.obj = null;
		mHandler.sendMessage(dataMsg);
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
			// acc on清空原先的数据 重新建立连接
			clearData();
			// acc on清空按键旋钮保存值
			if (mKeyDealer != null)
				mKeyDealer.clearKnobValue();
			// acc on重新发送连接数据
			connectCanDevice();
		} else {
			mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
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
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.CAN_CLASS_NAME),
						true, mCanNameObserver);

		// 与盒子建立连接，盒子发送初始数据到车机
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

		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.TAILDOORSTATUS),
						true, mTailDoorObserver); // 尾门状态

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
			connectCanDevice(); // 重新初始化
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
		
		if(mKeyDealer!=null){
			mKeyDealer.unRegisterReceiver();
		}
		getContentResolver().unregisterContentObserver(mBackCarObserver);
		getContentResolver().unregisterContentObserver(mCanNameObserver);
		getContentResolver().unregisterContentObserver(mTailDoorObserver);

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
		mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
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
						switch (canName) {
						case Contacts.CANNAMEGROUP.SSNissan:
						case Contacts.CANNAMEGROUP.SSNissanWithout360:
						case Contacts.CANNAMEGROUP.SSHonda12CRV:
						case Contacts.CANNAMEGROUP.SSJeepZNZ:
						case Contacts.CANNAMEGROUP.SSVolkswagen:
							readSSCanPort_1(); // 日产
							break;
						default:
							readSSCanPort(); // 尚摄数据帧
							break;
						}
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

		/*-------------------尚摄第二种数据帧----------start-------------*/
		private boolean isSSAValidPacket_1(final byte[] packet) {
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
		private void mSSACK_1(byte mode) {
			byte[] packet = new byte[6];
			packet[0] = (byte) Contacts.SS_HEAD_CODE_3;
			packet[1] = (byte) Contacts.SS_HEAD_CODE_4;
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

		private void readSSCanPort_1() throws IOException {
			// TODO Auto-generated method stub
			byte data1 = mReadByte();
			byte data2 = mReadByte();
			if (data1 == (byte) Contacts.SS_HEAD_CODE_3
					&& data2 == (byte) Contacts.SS_HEAD_CODE_4) {
				byte len = mReadByte();
				byte mode = mReadByte();
				byte[] packet = new byte[len + 5];
				packet[0] = (byte) Contacts.SS_HEAD_CODE_3;
				packet[1] = (byte) Contacts.SS_HEAD_CODE_4;
				packet[2] = len;
				packet[3] = mode;
				for (int i = 0; i < len + 1; i++) {
					packet[4 + i] = mReadByte();
				}
				synchronized (lock1) {
					if (isSSAValidPacket_1(packet)) {
						if (mOutputStream != null) {
							mSSACK_1(mode);
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
	private void sendMsgGetTemp() {
		if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagen))
			writeCanPort(BytesUtil.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO));
		if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagenGolf))
			writeCanPort(BytesUtil
					.addRZCCheckBit(Contacts.HEX_GET_CAR_INFO_0_1));
	}

	private void syncTimeWithMsg(String str) {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		writeCanPort(BytesUtil
				.addSSCheckBit(str + BytesUtil.changIntHex(hour)
						+ BytesUtil.changIntHex(minute) 
						+ BytesUtil.changIntHex(second)));
	}

	private void syncTimeWithMsgMT() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		writeCanPort(BytesUtil.addSSCheckBit("5AA50ACB00"
				+ BytesUtil.changIntHex(hour) + BytesUtil.changIntHex(minute)
				+ "0000" + "01" + "00000000"));
	}

	private void syncTimeWithMsgRC() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		writeCanPort(BytesUtil.addSSCheckBit("AA550ACB000000"
				+ BytesUtil.changIntHex(hour) + BytesUtil.changIntHex(minute)
				+ "0100" + "000000"));
	}
	
	private void rzcSyncTimeWithMsgRZCJAC() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mouth = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String tmp=Integer.toHexString(year).toUpperCase();
		String tmp1="";
		String tmp2="";
		if (tmp.length() > 2) {
			tmp1 = tmp.substring(tmp.length() - 2, tmp.length());
			tmp2 = Integer.toHexString(mouth+1).toUpperCase()+tmp.substring(0,1);
		}
		writeCanPort(BytesUtil.addRZCCheckBit("2E8206"
				+ tmp1
				+ tmp2
				+ BytesUtil.changIntHex(date) + BytesUtil.changIntHex(hour)
				+ BytesUtil.changIntHex(minute)
				+ "00"));
	}

	private void rzcSyncTimeWithMsg(String str) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mouth = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		writeCanPort(BytesUtil.addRZCCheckBit(str
				+ BytesUtil.changIntHex(year % 2000)
				+ BytesUtil.changIntHex(mouth + 1)
				+ BytesUtil.changIntHex(date) + BytesUtil.changIntHex(hour)
				+ BytesUtil.changIntHex(minute) + BytesUtil.changIntHex(second)
				+ "01"));
	}

	private void syncTimeWithMsgFjeep() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mouth = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		writeCanPort(BytesUtil.addSSCheckBit("5AA50ACB00"
				+ BytesUtil.changIntHex(hour) + BytesUtil.changIntHex(minute)
				+ "000001" + BytesUtil.changIntHex(year % 2000)
				+ BytesUtil.changIntHex(mouth + 1)
				+ BytesUtil.changIntHex(date) + "02"));
	}

	private void rzcSyncTimeWithMsg_1(String str) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mouth = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		writeCanPort(BytesUtil.addRZCCheckBit(str
				+ BytesUtil.changIntHex(year % 2000)
				+ BytesUtil.changIntHex(mouth + 1)
				+ BytesUtil.changIntHex(date) + BytesUtil.changIntHex(hour)
				+ BytesUtil.changIntHex(minute)));
	}

	private void syncTimeWithMsgMGGS() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int mouth = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);

		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		writeCanPort(BytesUtil.addSSCheckBit("5AA50ACB"
				+ BytesUtil.changIntHex(year % 2000)
				+ BytesUtil.changIntHex(mouth + 1)
				+ BytesUtil.changIntHex(date) + BytesUtil.changIntHex(hour)
				+ BytesUtil.changIntHex(minute) + "0100000000"));
	}

	/**
	 * 每分钟循环发送
	 */

	private void sendCycleMsg() {
		// TODO Auto-generated method stub
		switch (canFirtName) {
		case Contacts.CANFISRTNAMEGROUP.RAISE: // 睿志诚
			switch (canName) {
			case Contacts.CANNAMEGROUP.RZCJAC:
				rzcSyncTimeWithMsgRZCJAC();
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.RZCVolkswagenGolf:
			case Contacts.CANNAMEGROUP.RZCRoewe360:
				rzcSyncTimeWithMsg("2EA607");
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.RZCMGGS:
				rzcSyncTimeWithMsg_1("2EA605");
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			default:
				break;
			}
			break;
		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // 尚摄
			switch (canName) {
			case Contacts.CANNAMEGROUP.SSJeepFreedomGH:
			case Contacts.CANNAMEGROUP.SSJeepFreedomGM:
			case Contacts.CANNAMEGROUP.SSJeepFreedomGL:
			case Contacts.CANNAMEGROUP.SSJeepFreedomX:
			case Contacts.CANNAMEGROUP.SSJeepZNZ17:
				syncTimeWithMsgFjeep(); // 吉普
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.SSChery:
			case Contacts.CANNAMEGROUP.SSCheryAR5:
			case Contacts.CANNAMEGROUP.SSCheryR5:
			case Contacts.CANNAMEGROUP.SSCheryR3:
			case Contacts.CANNAMEGROUP.SSCheryR3X:
			case Contacts.CANNAMEGROUP.SSCheryR7:
			case Contacts.CANNAMEGROUP.SSHondaYG9:
				syncTimeWithMsgMT(); // 奇瑞
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.SSHonda:
			case Contacts.CANNAMEGROUP.SSHonda15CRV:
			case Contacts.CANNAMEGROUP.SSHondaSY:
			case Contacts.CANNAMEGROUP.SSRoewe360: // 荣威360
				syncTimeWithMsg("5AA503B5"); // 本田同步时间
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.SSHyundai16MT:
				syncTimeWithMsgMT(); // 16款名图同步时间
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.SSNissan:
			case Contacts.CANNAMEGROUP.SSNissanWithout360:
				syncTimeWithMsgRC(); // 日产同步时间
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.SSHonda12CRV:
				syncTimeWithMsg("AA5503B5"); // 本田同步时间
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			case Contacts.CANNAMEGROUP.SSMGGS:
				syncTimeWithMsgMGGS(); // 名爵锐腾同步时间
				mHandler.removeMessages(Contacts.MSG_MSG_CYCLE);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_MSG_CYCLE,
						1000 * 60);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 连接Can设备需要发送的初始化命令
	 */

	private void connectCanDevice() {
		// TODO Auto-generated method stub
		// 初始化空调状态
		Settings.System.putInt(getContentResolver(), "no_aircon", 0);

		mHandler.sendEmptyMessage(Contacts.MSG_MSG_CYCLE);
		switch (canFirtName) {
		case Contacts.CANFISRTNAMEGROUP.RAISE: // 睿志诚
			if (!canName.equals(Contacts.CANNAMEGROUP.RZCBESTURNx80)
					&& !canName.equals(Contacts.CANNAMEGROUP.RZCFHCm3)
					&& !canName.equals(Contacts.CANNAMEGROUP.RZCPeugeot))// 奔腾X80
																			// 海马M3
																			// 标致
				writeCanPort(BytesUtil.addRZCCheckBit(Contacts.DISCONNECTMSG));
			writeCanPort(BytesUtil.addRZCCheckBit(Contacts.CONNECTMSG));
			writeCanPort(BytesUtil.addRZCCheckBit(Contacts.CONNECTMSG));
			break;

		case Contacts.CANFISRTNAMEGROUP.HIWORLD: // 尚摄
			switch (canName) {
			case Contacts.CANNAMEGROUP.SSJeepFreedomGL:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240100")); // 2016款吉普自由光低配
				break;
			case Contacts.CANNAMEGROUP.SSJeepFreedomGM:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240200")); // 2016款吉普自由光中配
				break;
			case Contacts.CANNAMEGROUP.SSJeepFreedomGH:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240300")); // 2016款吉普自由光高配
				break;
			case Contacts.CANNAMEGROUP.SSJeepFreedomX:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240400")); // 2016款吉普自由侠
				break;
			case Contacts.CANNAMEGROUP.SSCHANGANYXH:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240513")); // 悦翔V7中高配
				break;
			case Contacts.CANNAMEGROUP.SSCHANGANYXL:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240613")); // 悦翔V7低配
				break;
			case Contacts.CANNAMEGROUP.SSHavalH1:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240411")); // 长城哈弗h1
				break;
			case Contacts.CANNAMEGROUP.SSCheryAR5:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240E35")); // 奇瑞艾瑞泽5
				break;
			case Contacts.CANNAMEGROUP.SSCheryR5:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241335")); // 奇瑞瑞虎5
				break;
			case Contacts.CANNAMEGROUP.SSCheryR3:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241235")); // 奇瑞瑞虎3
				break;
			case Contacts.CANNAMEGROUP.SSCheryR3X:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240B35")); // 奇瑞瑞虎3X
				break;
			case Contacts.CANNAMEGROUP.SSCheryR7:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241435")); // 奇瑞瑞虎7
				break;
			case Contacts.CANNAMEGROUP.SSFSAX5:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240426")); // 风神AX5
				break;
			case Contacts.CANNAMEGROUP.SSFSAX7:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240326")); // 风神AX7
				break;
			case Contacts.CANNAMEGROUP.SSDFFG:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240226")); // 风光580
				break;
			case Contacts.CANNAMEGROUP.SSHonda15CRV:
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D020B")); // 本田15CRV
				break;
			case Contacts.CANNAMEGROUP.SSToyotaBD:
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0101")); // 丰田 霸道
				break;
			case Contacts.CANNAMEGROUP.SSToyotaRZ:
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0102")); // 丰田 锐志
				break;
			case Contacts.CANNAMEGROUP.SSToyotaRAV4:
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0103")); // 丰田
																		// RAV4
				break;
			case Contacts.CANNAMEGROUP.SSToyotaKMR:
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0104")); // 丰田 凯美瑞
				break;
			case Contacts.CANNAMEGROUP.SSToyotaKLL:
				writeCanPort(BytesUtil.addSSCheckBit("5AA5022D0105")); // 丰田 卡罗拉
				break;
			case Contacts.CANNAMEGROUP.SSTrumpchi:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240100")); // 广汽传祺
			case Contacts.CANNAMEGROUP.SSTrumpchiGA6:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240100")); // 广汽传祺GA6
				break;
			case Contacts.CANNAMEGROUP.SSTrumpchiGS4: // 广汽传祺GS4
			case Contacts.CANNAMEGROUP.SSTrumpchiGS4HIGH: // 广汽传祺GS4高配
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240200"));
				break;
			case Contacts.CANNAMEGROUP.SSTrumpchiGS5:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240300")); // 广汽传祺GS5
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot: // 标致 世嘉
			case Contacts.CANNAMEGROUP.SSPeugeotSEGA:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240100"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotC4:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240200"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotC4L:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240300"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotC5:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240400"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotC55:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240500"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot307:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240600"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot308:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240700"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot408:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240800"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot508LOW:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240900"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot508HIGH:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240A00"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot3008ALL:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240B00"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotDS5HIGH:
			case Contacts.CANNAMEGROUP.SSPeugeotDS5LOW:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240C00"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotDS5LSHIGH:
			case Contacts.CANNAMEGROUP.SSPeugeotDS5LSLOW:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240D00"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot2008:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240E00"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotDS4HIGH:
			case Contacts.CANNAMEGROUP.SSPeugeotDS4LOW:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502240F00"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot308S:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241000"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot3008LOW:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241100"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeot301:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241200"));
				break;
			case Contacts.CANNAMEGROUP.SSPeugeotC3XR:
				writeCanPort(BytesUtil.addSSCheckBit("5AA502241300"));
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

}
