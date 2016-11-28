package com.console.parking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.console.parking.control.SSHonda15CRVControl;
import com.console.parking.control.SSToyotaRAV4Control;
import com.console.parking.control.SSTrumpchiGS5Control;
import com.console.parking.control.SSnissandataControl;
import com.console.parking.control.dataControl;
import com.console.parking.util.DisplayUtil;
import com.console.parking.util.PreferenceUtil;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.ContentObserver;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements SurfaceTextureListener {

	private Camera camera;// 声明相机
	private TextureView surface;
	private SurfaceTexture mSurfaceTexture;
	private int screenHeight = 0;
	private RelativeLayout parkingLayout;
	private dataControl mRadarControl;
	private SSnissandataControl mSSnissandataControl;
	private SSHonda15CRVControl mSSHonda15CRVControl;
	private SSToyotaRAV4Control mSSToyotaRAV4Control;
	private SSTrumpchiGS5Control mSSTrumpchiGS5Control;	
	private static final String BACKCARSTATE = "back_car_state";
	private static final int BACKCARSTATEMSG = 1;
	private static final int FINISHMSG = 2;
	private static final int OPENCAMERA = 5;
	private static final int CLOSECAMERA = 6;
	private final static String BACKTRACK = "backingTrack";
	private final static String VIDEOMIRROR = "videoMirroring";
	private static final String SEND_BACK_CAR_OFF = "com.console.SEND_BACK_CAR_OFF";
	private Button button;
	IntentFilter filter;
	private static final String DIS_DIALOG = "com.inet.broadcast.no_disturb";

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BACKCARSTATEMSG:
				mHandler.sendEmptyMessageDelayed(BACKCARSTATEMSG, 1000);
				if (android.provider.Settings.System.getInt(
						getContentResolver(), BACKCARSTATE, 1) == 0) {
					mHandler.sendEmptyMessageDelayed(FINISHMSG, 500);
				}
				break;
			case FINISHMSG:
				if (android.provider.Settings.System.getInt(
						getContentResolver(), BACKCARSTATE, 1) == 0) {
						finish();
					// moveTaskToBack(true);
				}
				break;
			case OPENCAMERA:
				openCamera();
				if (camera != null && mSurfaceTexture != null) {
					Camera.Parameters mParams = camera.getParameters();

					mParams.setPreviewSize(720, 240);
					camera.setParameters(mParams);
					try {
						camera.setPreviewTexture(mSurfaceTexture);
					} catch (IOException t) {
					}
					// camera.startPreview();
					camera.startPreview();
					surface.setAlpha(1.0f);
					surface.setRotation(0f);
				} else {
					mHandler.sendEmptyMessageDelayed(OPENCAMERA, 500);
				}
				break;
			case CLOSECAMERA:
				closeCamera();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		setContentView(R.layout.activity_main);

		init();
		requestAudioFocus();
		//registerHomeKeyReceiver(this);

	}

	private void init() {
		// TODO Auto-generated method stub
		// 初始化can数据读取控制
		parkingLayout = (RelativeLayout) findViewById(R.id.parking_layout);

		// 初始化camera显示界面
		surface = (TextureView) findViewById(R.id.camera_surface);
		surface.setSurfaceTextureListener(this);
		
		chooseControlLayout();	
		//
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(BACKCARSTATE), true,
				mBackCarObserver);
		mHandler.sendEmptyMessageDelayed(BACKCARSTATEMSG, 1000);
		doRegisterReceiver();
	}



	private void doRegisterReceiver() {
		// TODO Auto-generated method stub
		filter = new IntentFilter();
		filter.addAction(SEND_BACK_CAR_OFF);
		registerReceiver(myReceiver, filter);
	}

	/*----------------------------监控结束状态------------------------*/
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()) {
			case SEND_BACK_CAR_OFF:
				mHandler.sendEmptyMessage(FINISHMSG);
				break;
			default:
				break;
			}
		}
	};

	private BackCarObserver mBackCarObserver = new BackCarObserver();

	public class BackCarObserver extends ContentObserver {
		public BackCarObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), BACKCARSTATE, 1);
			handleBackCar(state);
		}
	}

	private void handleBackCar(int state) {
		// TODO Auto-generated method stub
		if (state == 0) {
			mHandler.sendEmptyMessageDelayed(FINISHMSG, 0);
		}
	}

	/*----------------------------Resume------------------------*/
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (readChannelFile() != 1)
			com.example.cjc7150.MainActivity.setmode((byte) 1);
		// 设置界面
		dismissSysDialog();
	}

	private void dismissSysDialog() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(DIS_DIALOG);
		sendBroadcast(intent);
	}

	/*----------------------------Resume------------------------*/

	/*----------------------------Pause-------------------------*/
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	/*----------------------------Pause-------------------------*/

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mHandler.removeMessages(OPENCAMERA);
		mHandler.sendEmptyMessageDelayed(CLOSECAMERA, 0);
		getContentResolver().unregisterContentObserver(mBackCarObserver);
		mHandler.removeMessages(BACKCARSTATEMSG);
		unregisterReceiver(myReceiver);
		unBindService();
		surface = null;
//		unregisterHomeKeyReceiver(this);
		abandonAudioFocus();

	}



	private void openCamera() {
		// TODO Auto-generated method stub
		if (camera == null) {
			try {
				camera = Camera.open();
				camera.setDisplayOrientation(0);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	private void closeCamera() {
		// TODO Auto-generated method stub
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		// 打开摄像头
		this.mSurfaceTexture = surface;
		mHandler.sendEmptyMessageDelayed(OPENCAMERA, 0);

	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		// closeCamera();
		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
			return;
	}

	public final static String CHANNEL_FILE = "/sys/class/gpio/cjc5150/value";

	public static int readChannelFile() {

		FileInputStream fis = null;
		byte[] rBuf = new byte[10];
		int channel = -1;
		try {
			fis = new FileInputStream(CHANNEL_FILE);
			fis.read(rBuf);
			fis.close();
			if (rBuf[0] == 48) {
				channel = 0;
			} else if (rBuf[0] == 49) {
				channel = 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return channel;
		}
	}

	private HomeWatcherReceiver mHomeKeyReceiver = null;

	private void registerHomeKeyReceiver(Context context) {
		mHomeKeyReceiver = new HomeWatcherReceiver();
		final IntentFilter homeFilter = new IntentFilter(
				Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		context.registerReceiver(mHomeKeyReceiver, homeFilter);
	}

	private void unregisterHomeKeyReceiver(Context context) {
		if (null != mHomeKeyReceiver) {
			context.unregisterReceiver(mHomeKeyReceiver);
		}
	}

	class HomeWatcherReceiver extends BroadcastReceiver {
		private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
		private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
		private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
		private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
		private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				// android.intent.action.CLOSE_SYSTEM_DIALOGS
				String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
				if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
					// content.snapToPage(default_page);
				} else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
				} else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
				} else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
				}
			}
		}
	}

	private AudioManager mAudioManager;

	private int requestAudioFocus() {
		return getAudioManager().requestAudioFocus(OnAudioFocusChangeListener,
				AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
	}

	OnAudioFocusChangeListener OnAudioFocusChangeListener = new OnAudioFocusChangeListener() {

		@Override
		public void onAudioFocusChange(int focusChange) {

		}
	};

	private void abandonAudioFocus() {
		getAudioManager().abandonAudioFocus(OnAudioFocusChangeListener);
	}

	private AudioManager getAudioManager() {
		if (mAudioManager == null) {
			mAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		}
		return mAudioManager;
	}
	
	/*-----360界面选择-------*/
	
	private void chooseControlLayout() {
		// TODO Auto-generated method stub
		switch (PreferenceUtil.getCANName(this)) {
		case PreferenceUtil.SSHonda15CRV:
			getSetting();
			mSSHonda15CRVControl = new SSHonda15CRVControl(this, parkingLayout);
			break;
		case PreferenceUtil.SSNissan:
			mSSnissandataControl = new SSnissandataControl(this, parkingLayout);
			break;
		case PreferenceUtil.SSToyotaRAV4:
			mSSToyotaRAV4Control = new SSToyotaRAV4Control(this, parkingLayout);
			break;
		case PreferenceUtil.SSTrumpchiGS5:
			mSSTrumpchiGS5Control = new SSTrumpchiGS5Control(this, parkingLayout);
			break;
		default:
			getSetting();
			mRadarControl = new dataControl(this, parkingLayout);
			break;
		}
	}
	
	
	
	private void unBindService() {
		// TODO Auto-generated method stub
		if (mRadarControl != null) {
			mRadarControl.unBindService();
		}
		if(mSSnissandataControl!=null)
			mSSnissandataControl.unBindService();
		
		if(mSSToyotaRAV4Control!=null)
			mSSToyotaRAV4Control.unBindService();
		
		if(mSSTrumpchiGS5Control!=null)
			mSSTrumpchiGS5Control.unBindService();
	}
	
	private void getSetting() {
		// TODO Auto-generated method stub	
		switch (PreferenceUtil.getCANName(this)) {
		case PreferenceUtil.SSNissan:
		case PreferenceUtil.SSToyotaRAV4:
		case PreferenceUtil.SSTrumpchiGS5:
             //do nothing		
			break;
		default:
			int backTrackState = android.provider.Settings.System.getInt(
					getContentResolver(), BACKTRACK, 1);
			if (backTrackState == 1) {
				findViewById(R.id.rail_line).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.rail_line).setVisibility(View.GONE);
			}
			break;
		}


		int videoMirrorState = android.provider.Settings.System.getInt(
				getContentResolver(), VIDEOMIRROR, 1);
		if (videoMirrorState == 1) {
			Matrix transform = new Matrix();
			transform.setScale(1, 1, 0, 0);
			surface.setTransform(transform);
		} else {
			Matrix transform = new Matrix();
			transform.setScale(-1, 1, 400, 0);
			surface.setTransform(transform);
		}
	}
	
	

	
	

}
