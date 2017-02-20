package com.console.launcher_console.control;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.kuwo.autosdk.m;

import com.console.launcher_console.R;
import com.console.launcher_console.util.LogXyw;
import com.console.launcher_console.util.longClickUtil;
import com.console.launcher_console.view.SurfaceCallback;

public class RecCardControl2 implements OnClickListener {

	// 加锁或者不加锁的图片
	private final static String RECORD_STATE = "carcorder_records_state";
	private final static String LOCK_FILE_STATE = "lock_file_state";
	private final static String KEY_ACC_STATE = "acc_state";
	public static final String BACK_RECORD_SERVICE_PACKAGE_NAME = "com.xair.h264demo";
	public static final String BACK_RECORD_SERVICE_CLASS_NAME = "com.xair.h264demo.MainActivity";
	private RecordStateObserver mRecordStateObserver = new RecordStateObserver(
			new Handler());
	private LockStateObserver mLockStateObserver = new LockStateObserver(
			new Handler());
	private AccStateObserver mAccStateObserver = new AccStateObserver(
			new Handler());

	// 发送给camera的广播
	private final static String START_RECORD = "android.intent.action.START_RECORD";
	private final static String STOP_RECORD = "android.intent.action.STOP_RECORD";
	private final static String LOCK_FILE = "android.intent.action.LOCK_RECORD_FILE";
	private final static String RECORD_START_PREVIEW = "h264_media_code_start_preview";
	private final static String RECORD_STOP_PREVIEW = "h264_media_code_stop_preview";
	private final static String SURFACE_IS_NULL = "h264_media_surface_null";
	public static final String STATE_START_RECORD = "h264_media_surface_start";
	public static final String STATE_STOP_RECORD = "h264_media_surface_stop";
	private final static String SERVICE_IS_START = "h264_media_service_start";

	// 发送给mHandler的消息值
	private final static int MSG_START_RECORD_VALUE = 1;
	private final static int MSG_STOP_RECORD_VALUE = 2;
	private final static int MSG_RECORD_TIME_VALUE = 3;
	private boolean isService = false;

	private long startRecTime;
	private SurfaceView mPreWindow;
	private SurfaceCallback mPreCallback;
	private Context mContext;
	private LinearLayout layout;
	private ImageView devicePre;
	private TextView mRecordTime;
	private ImageView mRecordButton = null;
	private ImageView mLockButton = null;
	private ImageView mCaptureButton = null;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_START_RECORD_VALUE:
				if (!isService) {
					Log.d("cds", "launcher dakai dvr service");
					startPreview(mPreWindow.getHolder());
				}
				break;
			case MSG_STOP_RECORD_VALUE:
				removeMessages(MSG_RECORD_TIME_VALUE);
				break;
			case MSG_RECORD_TIME_VALUE:
				updateRecTime();
				break;

			default:
				break;
			}
		}

	};

	public void startPreview(SurfaceHolder holder) {
		Intent i = new Intent();
		ComponentName cn = new ComponentName(BACK_RECORD_SERVICE_PACKAGE_NAME,
				BACK_RECORD_SERVICE_CLASS_NAME);
		i.setComponent(cn);
		i.putExtra("action", "startPreview");
		i.putExtra("surface", holder.getSurface());
		mContext.startService(i);
	}

	private void updateRecTime() {
		LogXyw.i("updateRecTime--");
		long now = SystemClock.uptimeMillis();
		long delta = now - startRecTime;
		long deltaAdjusted = delta;
		String text;

		long targetNextUpdateDelay;

		text = longClickUtil.millisecondToTimeString(deltaAdjusted, false);
		targetNextUpdateDelay = 1000;
		mRecordTime.setText(text);

		long actualNextUpdateDelay = targetNextUpdateDelay
				- (delta % targetNextUpdateDelay);
		mHandler.sendEmptyMessageDelayed(MSG_RECORD_TIME_VALUE,
				actualNextUpdateDelay);
	}

	public class RecordStateObserver extends ContentObserver {
		public RecordStateObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mContext.getContentResolver(), RECORD_STATE, 0);
			if (state == 0) {
				LogXyw.i(" stop record");
				mRecordButton.setImageResource(R.drawable.ic_rec_start);
				mRecordTime.setText(R.string.label_card_rec);
				mHandler.sendEmptyMessage(MSG_STOP_RECORD_VALUE);
				// SharedPrefManager.getInstance(mContext).put(
				// SharedPrefManager.RECORD_PLAY, true);
				// mRecord.setSelected(false);
				// mCtextView.setText("行车记录仪");
				// Log.d(TAG, "停止录像");
				// mHandler.removeMessages(MSG_START_TIME);
			} else if (state == 1) {
				LogXyw.i(" start record");
				mRecordButton.setImageResource(R.drawable.ic_rec_stop);
				startRecTime = SystemClock.uptimeMillis();
				updateRecTime();
				// SharedPrefManager.getInstance(mContext).put(
				// SharedPrefManager.RECORD_PLAY, false);
				// mRecord.setSelected(true);
				// Log.d(TAG, "开始录像");
				// startRec = SystemClock.uptimeMillis();
				// updateRecordingTime();
			}
		}
	}

	public class LockStateObserver extends ContentObserver {
		public LockStateObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mContext.getContentResolver(), LOCK_FILE_STATE, 0);
			if (state == 0) {
				mLockButton.setImageResource(R.drawable.ic_rec_unlock);
				// mLockImageView.setSelected(false);
				// Log.d(TAG, "解锁");
			} else if (state == 1) {
				mLockButton.setImageResource(R.drawable.ic_rec_lock);
				// mLockImageView.setSelected(true);
				// Log.d(TAG, "加锁");
			}
		}
	}

	public class AccStateObserver extends ContentObserver {
		public AccStateObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mContext.getContentResolver(), KEY_ACC_STATE, 0);
			Log.i("cds", "mAccStateObserver---state-" + state);
			if (state == 0) {
				isService = false;
				mPreCallback.surfaceStart(false);
				mPreWindow.setVisibility(View.GONE);
				mRecordButton.setImageResource(R.drawable.ic_rec_start);
				mRecordTime.setText(R.string.label_card_rec);
				mHandler.sendEmptyMessage(MSG_STOP_RECORD_VALUE);
				mHandler.removeMessages(MSG_START_RECORD_VALUE);
			} else if (state == 1) {
				mHandler.sendEmptyMessageDelayed(MSG_START_RECORD_VALUE,
						30 * 1000);
			}
		}
	}

	public RecCardControl2(Context context, LinearLayout layout) {
		this.mContext = context;
		this.layout = layout;

		initView();
		initObserver();
		mHandler.sendEmptyMessageDelayed(MSG_START_RECORD_VALUE, 30 * 1000);
	}

	private void initObserver() {
		// mContext.getContentResolver().registerContentObserver(
		// android.provider.Settings.System.getUriFor(RECORD_STATE), true,
		// mRecordStateObserver);
		mContext.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(LOCK_FILE_STATE),
				true, mLockStateObserver);
		mContext.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(KEY_ACC_STATE),
				true, mAccStateObserver);
		registerFinishSelfReceiver();
	}

	public void unRegisterObserver() {
		// mContext.getContentResolver().unregisterContentObserver(
		// mRecordStateObserver);
		mContext.getContentResolver().unregisterContentObserver(
				mLockStateObserver);
		mContext.getContentResolver().unregisterContentObserver(
				mAccStateObserver);
		mContext.unregisterReceiver(mSurfaceReceiver);
		mHandler.removeMessages(MSG_START_RECORD_VALUE);
	}

	private void initView() {
		devicePre = (ImageView) layout.findViewById(R.id.video_offline);
		devicePre.setOnClickListener(this);
		mRecordButton = (ImageView) layout.findViewById(R.id.RecordButton);
		mRecordButton.setOnClickListener(this);
		mRecordTime = (TextView) layout.findViewById(R.id.record_time);
		mRecordTime.setAlpha(1);
		mLockButton = (ImageView) layout.findViewById(R.id.LockButton);
		mLockButton.setOnClickListener(this);
		mCaptureButton = (ImageView) layout.findViewById(R.id.CaptureButton);
		mCaptureButton.setOnClickListener(this);
		mPreWindow = (SurfaceView) layout.findViewById(R.id.sur_window);
		mPreCallback = new SurfaceCallback(mContext, mPreWindow);
		mPreWindow.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		int recordState = android.provider.Settings.System.getInt(
				mContext.getContentResolver(), RECORD_STATE, 0);
		LogXyw.i("recordState==" + recordState);
		switch (v.getId()) {
		case R.id.video_layout:
			LogXyw.i("lu xiang jie mian");
			break;
		case R.id.RecordButton:
			if (recordState == 1) {
				// android.provider.Settings.System.putInt(
				// mContext.getContentResolver(), RECORD_STATE, 0);
				Intent stopRecord = new Intent(STOP_RECORD);
				mContext.sendBroadcast(stopRecord);
			} else {
				// android.provider.Settings.System.putInt(
				// mContext.getContentResolver(), RECORD_STATE, 1);
				Intent startRecord = new Intent(START_RECORD);
				mContext.sendBroadcast(startRecord);
			}
			// if (SharedPrefManager.getInstance(mContext).get(
			// SharedPrefManager.RECORD_PLAY, true)) {
			// Intent startRecord = new Intent(
			// "android.intent.action.START_RECORD");
			// sendBroadcast(startRecord);
			//
			// } else {
			//
			// Intent stopRecord = new Intent(
			// "android.intent.action.STOP_RECORD");
			// sendBroadcast(stopRecord);
			//
			// }
			break;
		case R.id.LockButton:
			if (recordState == 1) {
				Intent lockIntent = new Intent(LOCK_FILE);
				mContext.sendBroadcast(lockIntent);
			} else {
				Toast.makeText(mContext, "请打开行车记录仪", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.CaptureButton:
			Intent captureIntent = new Intent(
					"android.intent.action.TAKE_PICTURE");
			mContext.sendBroadcast(captureIntent);
			break;

		default:
			break;
		}
	}

	private void registerFinishSelfReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(RECORD_START_PREVIEW);
		filter.addAction(RECORD_STOP_PREVIEW);
		filter.addAction(STATE_START_RECORD);
		filter.addAction(STATE_STOP_RECORD);
		filter.addAction(SERVICE_IS_START);
		mContext.registerReceiver(mSurfaceReceiver, filter);
	}

	private BroadcastReceiver mSurfaceReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.v("mSurfaceReceiver", "action = " + action);
			if (TextUtils.equals(action, RECORD_START_PREVIEW)) {
				mPreCallback.surfaceStart(true);
				mPreWindow.setVisibility(View.VISIBLE);
			} else if (TextUtils.equals(action, RECORD_STOP_PREVIEW)) {
				isService = false;
				mPreCallback.surfaceStart(false);
				mPreWindow.setVisibility(View.GONE);
			} else if (TextUtils.equals(action, SURFACE_IS_NULL)) {
				startPreview(mPreWindow.getHolder());
			} else if (TextUtils.equals(action, STATE_START_RECORD)) {
				mRecordButton.setImageResource(R.drawable.ic_rec_stop);
				startRecTime = SystemClock.uptimeMillis();
				updateRecTime();
			} else if (TextUtils.equals(action, STATE_STOP_RECORD)) {
				mRecordButton.setImageResource(R.drawable.ic_rec_start);
				mRecordTime.setText(R.string.label_card_rec);
				mHandler.sendEmptyMessage(MSG_STOP_RECORD_VALUE);
			} else if (TextUtils.equals(action, SERVICE_IS_START)) {
				mHandler.removeMessages(MSG_START_RECORD_VALUE);
				isService = true;
				Log.d("cds", "launcher onReceive SERVICE_IS_START:" + isService);
			}
		}
	};
}
