package cn.colink.serialport.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import cn.colink.serialport.buffer.CircleBuffer;
import cn.colink.serialport.control.SerialPortControl;
import cn.colink.serialport.utils.BytesUtil;
import cn.colink.serialport.utils.Contacts;
import cn.colink.serialport.utils.KeyDealer;
import cn.colink.serialport.utils.Trace;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android_serialport_api.SerialPort;

@SuppressLint("HandlerLeak")
public class SerialPortService extends Service {

	public final static String TAG = "SerialPortService";
	public final static int BUFFER_SIZE = 1024;

	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private Thread mReadDataFromSpThread;
	private Thread mSendDataToSpThread;

	private KeyDealer mKeyDealer;
	
	SerialPortControl mSerialPortControl;

	interface SerialPortWriteTask {
		public void excute();
	}

	private BlockingQueue<SerialPortWriteTask> mBlockingQueue = new LinkedBlockingQueue<SerialPortWriteTask>();

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Contacts.MSG_DATA:
				byte[] mPacket = (byte[]) msg.obj;
				// Broadcast to all clients the new value.
				final int N = mCallbacks.beginBroadcast();
				try {
					for (int i = 0; i < N; i++)
						mCallbacks.getBroadcastItem(i).readDataFromServer(
								mPacket);
				} catch (RemoteException e) {
					// The RemoteCallbackList will take care of removing
					// the dead object for us.
				}
				mCallbacks.finishBroadcast();
				
				if(mSerialPortControl!=null)
				   mSerialPortControl.deal(mPacket);
				
				if (mPacket[0] == Contacts.KEY_HEAD) {
					mKeyDealer = KeyDealer.getInstance(SerialPortService.this);
					mKeyDealer.dealCanKeyEvent(SerialPortService.this, mPacket);
				}
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
			mOutputStream = SerialPort.getInstance().getOutputStream();
			mReadDataFromSpThread = new ReadDataFromSpThread();
			mReadDataFromSpThread.start();
			mSendDataToSpThread = new SendDataToSpThread();
			mSendDataToSpThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initReceiver();
		doStartService();
		mSerialPortControl=new SerialPortControl(this);
	}
	
	
	private void doStartService() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SerialPortService.this, BNRService.class);
		startService(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Trace.i("SerialPortService onStartCommand");		
		if (intent != null) {
			String command = intent.getStringExtra("keyEvent");
			if(mSerialPortControl==null)
				mSerialPortControl=new SerialPortControl(this);
			Trace.m("SerialPortService onStartCommand=========="+command);
			if (command != null) {
				mSerialPortControl.dealCommand(command);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Trace.i("SerialPortService onDestroy");
		//注销
		if(mSerialPortControl!=null)
		mSerialPortControl.onDestroy();
		mInputStream = null;
		mOutputStream = null;
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
		removeReceiver();
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
					buffer.put(new byte[] { data }, 1);
					while (buffer.getDateSize() >= CircleBuffer.PACKET_LENGHT) {
						byte[] packet = new byte[CircleBuffer.PACKET_LENGHT];
						if (buffer.getPacket(packet)) {
							// Log.i("packet", "packet : " +
							// BytesUtil.bytesToHexString(packet));
							Trace.i("packet : "
									+ BytesUtil.bytesToHexString(packet));
							dealWithPacketData(packet);
						} else {
							break;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void dealWithPacketData(byte[] packet) {
			Message dataMsg = new Message();
			dataMsg.what = Contacts.MSG_DATA;
			dataMsg.obj = packet;
			mHandler.sendMessage(dataMsg);
		}

		public void clearBuffer() {
			buffer.clearBuffer();
		}
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
				try {
					mOutputStream.write(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		return task;
	}

	private void startMcu() {
		try {
			mBlockingQueue.put(getTask(Contacts.HEX_START));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(final String hexString) throws RemoteException {
		try {
			mBlockingQueue.put(getTask(hexString));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

	private final RemoteCallbackList<ISerialPortCallback> mCallbacks = new RemoteCallbackList<ISerialPortCallback>();

	private final ISerialPortService.Stub mBinder = new ISerialPortService.Stub() {

		@Override
		public void sendDataToSp(final String hexString) throws RemoteException {
			try {
				mBlockingQueue.put(getTask(hexString));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void addClient(ISerialPortCallback client)
				throws RemoteException {
			if (mReadDataFromSpThread != null) {
				((ReadDataFromSpThread) mReadDataFromSpThread).clearBuffer();
			}
			if (client != null) {
				mCallbacks.register(client);
			}
			
		}

		@Override
		public void removeCliend(ISerialPortCallback client)
				throws RemoteException {
			if (client != null) {
				mCallbacks.unregister(client);
			}
		}

	};

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// ---------------------receive msg start--------------------------------
	public static final String ACTION_APP_CANCEL = "action_fm_app_cancel";
	private static final String ACTION_CLOSE_FMAUDIO = "android.intent.action.CLOSE_FMAUDIO";
	private static final String ACTION_OPEN_FMAUDIO = "android.intent.action.OPEN_FMAUDIO";

	public void initReceiver() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_APP_CANCEL);
		filter.addAction(ACTION_CLOSE_FMAUDIO);
		filter.addAction(ACTION_OPEN_FMAUDIO);
		registerReceiver(mBroadcastReceiver, filter);
	}

	public void removeReceiver() {
		unregisterReceiver(mBroadcastReceiver);
	}

	private MyBroadcastReceiver mBroadcastReceiver;

	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Trace.i("SerialPortService action : " + action);
			if (TextUtils.equals(action, ACTION_APP_CANCEL)) {
				try {
					mBlockingQueue.put(getTask(Contacts.HEX_FM_TO_HOME));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (TextUtils.equals(action, ACTION_CLOSE_FMAUDIO)) {
				try {
					mBlockingQueue.put(getTask(Contacts.HEX_HAND_UP));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (TextUtils.equals(action, ACTION_OPEN_FMAUDIO)) {
				try {
					mBlockingQueue.put(getTask(Contacts.HEX_HAND_DOWN));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// ---------------------receive msg end--------------------------------
}
