package com.console.parking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.console.parking.control.dataControl;
import com.console.parking.util.DisplayUtil;

import android.app.Activity;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	private LinearLayout parkingLayout;
	private dataControl mRadarControl;
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
					//moveTaskToBack(true);
				}
				break;
			case OPENCAMERA:
				openCamera();
				if (camera != null&&mSurfaceTexture!=null) {
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
	
	}

	private void init() {
		// TODO Auto-generated method stub
		// 初始化can数据读取控制
		parkingLayout = (LinearLayout) findViewById(R.id.parking_layout);
		mRadarControl = new dataControl(this, parkingLayout);
		// 初始化camera显示界面
		surface = (TextureView) findViewById(R.id.camera_surface);
		surface.setSurfaceTextureListener(this);
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
		getSetting();

	}

	private void getSetting() {
		// TODO Auto-generated method stub
		int backTrackState = android.provider.Settings.System.getInt(
				getContentResolver(), BACKTRACK, 1);
		if (backTrackState == 1) {
			findViewById(R.id.rail_line).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.rail_line).setVisibility(View.GONE);
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
		if (mRadarControl != null) {
			mRadarControl.unBindService();
		}
		surface = null;
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
		this.mSurfaceTexture=surface;
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

}
