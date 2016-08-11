package com.console.launcher_console.control;

import java.util.List;
import java.util.Set;

import com.console.launcher_console.R;
import com.softwinner.un.tool.util.UNJni;
import com.softwinner.un.tool.utilex.UNLog;
import com.softwinner.un.tool.video.UNVideoViewHelper;
import com.srtc.pingwang.ISrtcService;
import com.srtc.pingwang.IVideoListener;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecCardControl implements UNVideoViewHelper.UNVideoViewListener,
		View.OnClickListener {

	private static final String TAG = "cxs_TestClient";

	private UNVideoViewHelper mUNVideoViewHelper;

	private ISrtcService mService = null;
	private RelativeLayout mRelativeLayout = null;

	// public static boolean isStartVideoStream = false;// 表示是否视频完成
	// private boolean isStartingVideoStream = false;// 表示是否正在显示视频
	// private boolean isStopingVideoStream = false;// 表示是否正在停止视频

	private boolean mResume = false;
	

	private WorkHandler mWorkHandler = null;
	private Looper mWorkLooper = null;

	private static final int MSG_START_STREAM = 1;
	private static final int MSG_STOP_STREAM = 2;

	private LinearLayout recCardLayout;
	private Context context;
	private ImageView devicePre;

	private final class WorkHandler extends Handler {
		public WorkHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// UNLog.debug_print(UNLog.LV_DEBUG, TAG,
			// "cxs =================handleMessage msg what = "
			// + msg.what);
			Log.i("cxs", "===WorkHandler===handleMessage=========" + msg.what);
			switch (msg.what) {
			case MSG_START_STREAM:
				startVideoStream();
				break;
			case MSG_STOP_STREAM:
				stopVideoStream();
				break;
			}
		}
	}

	public RecCardControl(Context context, LinearLayout layout) {
		recCardLayout = layout;
		this.context = context;

		mRelativeLayout = (RelativeLayout) recCardLayout
				.findViewById(R.id.video_parentview);
		recCardLayout.findViewById(R.id.video_layout).setOnClickListener(this);
		devicePre = (ImageView) recCardLayout.findViewById(R.id.video_offline);

		mUNVideoViewHelper = new UNVideoViewHelper(context, mRelativeLayout);
		mUNVideoViewHelper.setVideoViewListener(this);
		Intent intent = new Intent("com.softwinner.un.tool.service.SrtcService");
		intent.setPackage("com.srtc.pingwang");
		boolean ret = context.bindService(intent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		Log.e(TAG, "---------------------------bindservice ret = " + ret);

		thread = new HandlerThread("WorkHandler[" + TAG + "]");
		thread.start();
		mWorkLooper = thread.getLooper();
		mWorkHandler = new WorkHandler(mWorkLooper);

	}

	public void resumeVideoPreview() {
		Log.i("cxs", "======resumeVideoPreview=========");
		if (!thread.isAlive())
			thread.start();
		mResume = true;
		mWorkHandler.removeMessages(MSG_START_STREAM);
		mWorkHandler.removeMessages(MSG_STOP_STREAM);
		mWorkHandler.sendEmptyMessageDelayed(MSG_START_STREAM, 500);
	}

	public void pauseVideoPreview() {
		Log.i("cxs", "======pauseVideoPreview=========");
		mResume = false;
		mWorkHandler.removeMessages(MSG_START_STREAM);
		mWorkHandler.removeMessages(MSG_STOP_STREAM);
		mWorkHandler.sendEmptyMessageDelayed(MSG_STOP_STREAM, 500);
	}

	public void stopVideoPreview() {
		Log.i("cxs", "======stopVideoPreviews=========");
		mWorkHandler.removeMessages(MSG_START_STREAM);
		mWorkHandler.removeMessages(MSG_STOP_STREAM);
		mWorkHandler.sendEmptyMessageDelayed(MSG_STOP_STREAM, 500);
		mWorkLooper.quit();
		context.unbindService(mServiceConnection);
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = ISrtcService.Stub.asInterface(service);
			Log.e(TAG, "+++++++++++++++++++service connected");
			try {
				mService.setVideoListener(mVideoListener);
				if (mResume) {
					mWorkHandler.sendEmptyMessage(MSG_START_STREAM);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			Log.e(TAG, "service disconnected");
		}
	};

	private void startVideoStream() {
		Log.e(TAG, "startVideoStream 1");
		if (null == mService) {
			Log.e(TAG, "startVideoStream 2 client not connect!!!");
			return;
		}
		// if (!isStartVideoStream && !isStartingVideoStream &&
		// !isStopingVideoStream) {
		try {
			if (mService.getConnectStatus() == 0) {
				Log.e(TAG,
						"startVideoStream 2 getConnectStatus device not connected");
				mRelativeLayout.post(new Runnable() {
					@Override
					public void run() {
						mRelativeLayout.setVisibility(View.INVISIBLE);
					}
				});
				devicePre.post(new Runnable() {
					@Override
					public void run() {
						devicePre.setVisibility(View.VISIBLE);
					}
				});
				return;
			} else {
				Log.e(TAG,
						"aaaaaaaaaaa startVideoStream 2 getConnectStatus device  connected");
				mRelativeLayout.post(new Runnable() {
					@Override
					public void run() {
						mRelativeLayout.setVisibility(View.VISIBLE);
					}
				});
				devicePre.post(new Runnable() {
					@Override
					public void run() {
						devicePre.setVisibility(View.INVISIBLE);
					}
				});
			}
		} catch (RemoteException e) {
			Log.e(TAG, "startVideoStream 3 service connection error!!!");
		}

		int ret = UNJni.jni_initNetServer();
		Log.e(TAG, "startVideoStream 5 jni_initNetServer ret = " + ret);

		ret = 0;
		try {
			ret = mService.startVideo();
		} catch (RemoteException e) {
			Log.e(TAG, "startVideoStream 4 service connection error!!!");
		}
		Log.e(TAG, "startVideoStream 5 startVideo ret = " + ret);

		if (1 == ret) {
			// isStartingVideoStream = true;
			UNJni.jni_startDisplay(mUNVideoViewHelper);
		}
		// }
	}

	public void stopVideoStream() {

		UNJni.jni_deInitNetServer();

		Log.e(TAG, "stopVideoStream in");// isStartVideoStream =
											// "+isStartVideoStream );
		// if (isStartVideoStream) {
		// try {
		// if (mService.getConnectStatus() == 0) {
		// return;
		// }
		// } catch (RemoteException e) {
		// e.printStackTrace();
		// }
		// isStartVideoStream = false;
		// isStopingVideoStream = true;

		mRelativeLayout.post(new Runnable() {
			@Override
			public void run() {
				mRelativeLayout.setVisibility(View.INVISIBLE);
			}
		});
		devicePre.post(new Runnable() {
			@Override
			public void run() {
				devicePre.setVisibility(View.VISIBLE);
			}
		});
		try {
			mService.stopVideo();
		} catch (Exception e) {
			Log.e(TAG, "stopVideoStream client not connect!!!");
		}
		UNJni.jni_stopDisplay();
		// } else if (isStartingVideoStream) {
		//
		// mRelativeLayout.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// stopVideoStream();
		// }
		// }, 1000);
		// }
	}

	private void resetFlags() {
		mWorkHandler.sendEmptyMessage(MSG_STOP_STREAM);

		// isStartVideoStream = false;
		// isStartingVideoStream = false;
		// isStopingVideoStream = false;
	}

	private IVideoListener.Stub mVideoListener = new IVideoListener.Stub() {

		@Override
		public void onConnectStatusChange(int connected) throws RemoteException {
			Log.e(TAG, "+++++++++++++++++++onConnectStatusChange connected = "
					+ connected);
			if (1 == connected && mResume) {
				mWorkHandler.sendEmptyMessage(MSG_START_STREAM);
			} else {
				resetFlags();
			}
		}
	};

	private HandlerThread thread;

	@Override
	public void videoViewShow() {
		// isStartVideoStream = true;
		// isStartingVideoStream = false;
	}

	@Override
	public void videoViewShowing() {

	}

	@Override
	public void videoViewEnd() {
		// isStartVideoStream = false;
		// isStopingVideoStream = false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.video_layout:
			try {
				Intent recIntent = context.getPackageManager()
						.getLaunchIntentForPackage("com.srtc.pingwang");
				recIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(recIntent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		default:
			break;
		}
	}

}
