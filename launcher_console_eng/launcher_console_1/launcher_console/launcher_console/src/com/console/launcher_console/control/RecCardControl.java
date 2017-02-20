package com.console.launcher_console.control;

import com.console.launcher_console.R;
import com.console.launcher_console.util.Constact;
import com.softwinner.un.tool.util.UNJni;
import com.softwinner.un.tool.util.UtilsStatus;
import com.softwinner.un.tool.video.UNVideoViewHelper;
import com.srtc.pingwang.ISrtcService;
import com.srtc.pingwang.IVideoListener;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class RecCardControl implements UNVideoViewHelper.UNVideoViewListener,
		View.OnClickListener {

	private static final String TAG = "RecCardControl";

	private UNVideoViewHelper mUNVideoViewHelper;

	private ISrtcService mService = null;
	private RelativeLayout mRelativeLayout = null;

	// public static boolean isStartVideoStream = false;// 表示是否视频完成
	// private boolean isStartingVideoStream = false;// 表示是否正在显示视频
	// private boolean isStopingVideoStream = false;// 表示是否正在停止视频

	private boolean mResume = false;

	private static final int MSG_START_STREAM = 1;
	private static final int MSG_STOP_STREAM = 2;
	private static final int MSG_UPDATE_BUTTON = 3;

	private LinearLayout recCardLayout;
	private Context context;
	private ImageView devicePre;
	private ImageView mRecordButton = null;
	private ImageView mLockButton = null;
	private ImageView mCaptureButton = null;
	Object obj = new Object();
	private Handler mWorkHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// UNLog.debug_print(UNLog.LV_DEBUG, TAG,
			// "cxs =================handleMessage msg what = "
			// + msg.what);
			Log.i(TAG, "========handleMessage===============" + msg.what);
			switch (msg.what) {
			case MSG_UPDATE_BUTTON:
				syncButtonStatus();
				break;
			case MSG_START_STREAM:
				new Thread() {
					public void run() {
						startVideoStream();
					};
				}.start();

				break;
			case MSG_STOP_STREAM:
				new Thread() {
					public void run() {
						stopVideoStream();
					};
				}.start();
				break;
			}
		}
	};

	public RecCardControl(Context context, LinearLayout layout) {
		recCardLayout = layout;
		this.context = context;

		mRelativeLayout = (RelativeLayout) recCardLayout
				.findViewById(R.id.video_parentview);
		recCardLayout.findViewById(R.id.video_layout).setOnClickListener(this);
		devicePre = (ImageView) recCardLayout.findViewById(R.id.video_offline);
		mRecordButton = (ImageView) recCardLayout
				.findViewById(R.id.RecordButton);
		mRecordButton.setOnClickListener(this);
		mLockButton = (ImageView) recCardLayout.findViewById(R.id.LockButton);
		mLockButton.setOnClickListener(this);
		mCaptureButton = (ImageView) recCardLayout
				.findViewById(R.id.CaptureButton);
		mCaptureButton.setOnClickListener(this);

		mUNVideoViewHelper = new UNVideoViewHelper(context, mRelativeLayout);
		mUNVideoViewHelper.setVideoViewListener(this);
		Intent intent = new Intent("com.softwinner.un.tool.service.SrtcService");
		intent.setPackage("com.srtc.pingwang");
		boolean ret = context.bindService(intent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		Log.e(TAG, "---------------------------bindservice ret = " + ret);

		// 监控adas状态
		context.getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Constact.ADAS_STATE),
						true, mAdasStateObserver);
		setAdasSetting(getAdasStatus());
	}

	public void resumeVideoPreview() {
		if (mService == null) {
			Log.i(TAG,
					"========resumeVideoPreview===bind agin===mService========");
			Intent intent = new Intent(
					"com.softwinner.un.tool.service.SrtcService");
			intent.setPackage("com.srtc.pingwang");
			boolean ret = context.bindService(intent, mServiceConnection,
					Context.BIND_AUTO_CREATE);
		}
		Log.i(TAG, "========resumeVideoPreview===============");
		mResume = true;
		// 如果正在打开设备 则返回
		if (mWorkHandler.hasMessages(MSG_START_STREAM)) {
			return;
		}
		mWorkHandler.removeMessages(MSG_START_STREAM);
		mWorkHandler.removeMessages(MSG_STOP_STREAM);
		mWorkHandler.sendEmptyMessageDelayed(MSG_START_STREAM, 500);
	}

	public void pauseVideoPreview() {
		mResume = false;
		// 如果正在关闭设备 则返回
		if (mWorkHandler.hasMessages(MSG_STOP_STREAM)) {
			return;
		}
		mWorkHandler.removeMessages(MSG_START_STREAM);
		mWorkHandler.removeMessages(MSG_STOP_STREAM);
		mWorkHandler.sendEmptyMessageDelayed(MSG_STOP_STREAM, 500);
	}

	public void stopVideoPreview() {
		Log.i("cxs", "======stopVideoPreviews=========");
		mWorkHandler.removeMessages(MSG_START_STREAM);
		mWorkHandler.removeMessages(MSG_STOP_STREAM);
		mWorkHandler.sendEmptyMessageDelayed(MSG_STOP_STREAM, 500);
		context.unbindService(mServiceConnection);
		context.getContentResolver().unregisterContentObserver(
				mAdasStateObserver);
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
			resetFlags();
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
		if (ret < 0) {
			Log.e(TAG,
					"startVideoStream 5 jni_initNetServer erro ~~~~~~~~~~~~~~~ ret = "
							+ ret);
			return;
		}
		ret = 0;
		try {
			ret = mService.startVideo();
		} catch (RemoteException e) {
			Log.e(TAG, "startVideoStream 4 service connection error!!!");
		}
		Log.e(TAG, "startVideoStream 5 startVideo ret = " + ret);

		if (1 == ret) {
			UNJni.jni_startDisplay(mUNVideoViewHelper);
		}
		mWorkHandler.sendEmptyMessageDelayed(MSG_UPDATE_BUTTON, 500);
	}

	public void stopVideoStream() {

		UNJni.jni_deInitNetServer();

		Log.e(TAG, "stopVideoStream in");

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
	}

	private void resetFlags() {
		mWorkHandler.sendEmptyMessageDelayed(MSG_STOP_STREAM, 500);
		Log.e(TAG, "resetFlags send stop stream 3");
	}

	private IVideoListener.Stub mVideoListener = new IVideoListener.Stub() {

		@Override
		public void onConnectStatusChange(int connected) throws RemoteException {
			Log.e(TAG, "+++++++++++++++++++onConnectStatusChange connected = "
					+ connected);
			if (1 == connected && mResume) {
				resumeVideoPreview();
			} else {
				resetFlags();
			}
		}

		@Override
		public void onStatusChange(int index, int state) throws RemoteException {
			// TODO Auto-generated method stub
			// TODO：状态改变时，会回调这个接口，index对应UtilsStatus里面的index

			switch (index) {
			case UtilsStatus.INDEX_RECORDING:
				if (state > 0) {
					Log.i(TAG, "-----record success---------------------");
				} else {
					Log.i(TAG, "-----record fail------------------------");
				}
				break;
			case UtilsStatus.INDEX_CAPTURE:
				if (state > 0) {
					Log.i(TAG, "-----capture success---------------------");
				} else {
					Log.i(TAG, "-----capture fail---------------------");
				}
				break;
			case UtilsStatus.INDEX_FILE_LOCK:
				if (state > 0) {
					Log.i(TAG, "-----lock success---------------------");
				} else {
					Log.i(TAG, "-----lock fail---------------------");
				}
			case UtilsStatus.INDEX_ADAS:
				if (state >= 0) {
					Log.i(TAG, "-----ADAS state---------------------" + state);
				} else {
					Log.i(TAG, "-----ADAS fail---------------------");
				}
				setAdasSetting(state);			
				break;
			default:
				break;
			}
			mWorkHandler.sendEmptyMessageDelayed(MSG_UPDATE_BUTTON, 500);

		}
	};

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
/*		case R.id.video_layout:
			try {
				Intent recIntent = context.getPackageManager()
						.getLaunchIntentForPackage("com.srtc.pingwang");
				recIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(recIntent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;*/
		case R.id.RecordButton:
			if (null != mService) {
				try {
					int record_status = mService
							.getStatus(UtilsStatus.INDEX_RECORDING);
					if (record_status != -1) { // ret == -1 意味者设备未连接
						int ret = mService.setStatus(
								UtilsStatus.INDEX_RECORDING,
								record_status == 0 ? 1 : 0);
						// TODO: 判断返回ret，给出对应的回应
						Log.i(TAG, "record msg send ret = " + ret);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.LockButton:
			if (null != mService) {
				try {
					int lock_status = mService
							.getStatus(UtilsStatus.INDEX_FILE_LOCK);
					if (lock_status != -1) { // ret == -1 意味者设备未连接
						int ret = mService.setStatus(
								UtilsStatus.INDEX_FILE_LOCK,
								lock_status == 0 ? 1 : 0);
						// TODO: 判断返回ret，给出对应的回应
						Log.i(TAG, "lock msg send ret = " + ret);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.CaptureButton:
			if (null != mService) {
				try {
					int capture_status = mService
							.getStatus(UtilsStatus.INDEX_CAPTURE);
					if (capture_status != -1) { // ret == -1 意味者设备未连接
						int ret = mService.setStatus(UtilsStatus.INDEX_CAPTURE,
								capture_status == 0 ? 1 : 0);
						// TODO: 判断返回ret，给出对应的回应
						Log.i(TAG, "capture msg send ret = " + ret);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			break;

		default:
			break;
		}
	}

	private void syncButtonStatus() {
		try {
			if (mService != null) {
				// lock button status
				int lock_status = mService
						.getStatus(UtilsStatus.INDEX_FILE_LOCK);
				if (lock_status == 0) {
					mLockButton.setImageResource(R.drawable.ic_rec_lock);
				} else {
					mLockButton.setImageResource(R.drawable.ic_rec_unlock);
				}
				Log.i(TAG, "=============lock_status=========" + lock_status);
				// record button status
				int record_status = mService
						.getStatus(UtilsStatus.INDEX_RECORDING);
				if (record_status == 0) {
					mRecordButton.setImageResource(R.drawable.ic_rec_start);
				} else {
					mRecordButton.setImageResource(R.drawable.ic_rec_stop);
				}
				Log.i(TAG, "=============record_status========="
						+ record_status);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 监控adas状态
	private AdasStateObserver mAdasStateObserver = new AdasStateObserver();

	public class AdasStateObserver extends ContentObserver {
		public AdasStateObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			syncAdasStatus();
		}
	}
	/**
	 * 设置adas状态的系统数据库
	 * @param status
	 */
	private void setAdasSetting(int status){
		if(status>0){
			android.provider.Settings.System.putInt(
					context.getContentResolver(), Constact.ADAS_STATE, 1);
		}else{
			android.provider.Settings.System.putInt(
					context.getContentResolver(), Constact.ADAS_STATE, 0);
		}
	}
	/**
	 * 根据adas状态的系统数据库设置adas的状态
	 * @param status
	 */	
	private void syncAdasStatus(){
		int state = android.provider.Settings.System.getInt(
				context.getContentResolver(), Constact.ADAS_STATE, 0);
		if(getAdasStatus()>0&&state==0){
			setAdasStatus(state);
		}
		if(getAdasStatus()<=0&&state==1){
			setAdasStatus(state);
		}
	}
	//静音状态保存
    static int adasStausSave=1;
    //设备无连接或者设置出错的时候 adas默认关闭
	private int getAdasStatus() {
		try {
			if (mService != null) {
				int status = mService.getStatus(UtilsStatus.INDEX_ADAS);
				if(status>0){
					adasStausSave=status;
				}
				if(status==-1){
					setAdasSetting(status);
				}
				return status;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setAdasSetting(-1);
			return -1;
		}
		setAdasSetting(-1);
		return -1;
	}

	private void setAdasStatus(int status) {
		try {
			if (mService != null) {
				if (status < 0 || status >= 2) {
					status = 0; 
				}
				if(status>0){
					status=adasStausSave;
				}
				int ret = mService.setStatus(UtilsStatus.INDEX_ADAS, status);
				// TODO: 判断返回ret，给出对应的回应
				Log.i(TAG, "=======ADAS设置命令 ret ========" + ret);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
