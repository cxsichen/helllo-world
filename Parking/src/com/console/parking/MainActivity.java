package com.console.parking;

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

	private Camera camera;// ÉùÃ÷Ïà»ú
	private TextureView surface;
	private int screenHeight = 0;
	private LinearLayout parkingLayout;
	private dataControl mRadarControl;
	private static final String BACKCARSTATE = "back_car_state";
	private static final int BACKCARSTATEMSG = 1;
	private static final int FINISHMSG = 2;
	private static final int FINISH = 3;
	private final static String BACKTRACK = "backingTrack"; 
	private final static String VIDEOMIRROR = "videoMirroring"; 
	private static final String SEND_BACK_CAR_OFF = "com.console.SEND_BACK_CAR_OFF";
	private Button button;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BACKCARSTATEMSG:
				mHandler.sendEmptyMessageDelayed(BACKCARSTATEMSG, 2000);
				if(android.provider.Settings.System.getInt(
						getContentResolver(), BACKCARSTATE, 1)==0){
					mHandler.sendEmptyMessageDelayed(FINISHMSG, 0);
				}				
				break;
			case FINISHMSG:
				if(android.provider.Settings.System.getInt(
						getContentResolver(), BACKCARSTATE, 1)==0){			        
					finish();
				}
				break;
			case FINISH:
				finish();
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
		initView();
		initControl();
		
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(BACKCARSTATE), true,
				mBackCarObserver);
		mHandler.sendEmptyMessageDelayed(BACKCARSTATEMSG, 2000);
		doRegisterReceiver();
	}
	
	private void doRegisterReceiver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(SEND_BACK_CAR_OFF);
		registerReceiver(myReceiver, filter);
	}
	
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()) {
			case SEND_BACK_CAR_OFF:
				Message msg = new Message();
				msg.what = FINISH;
				mHandler.sendMessage(msg);
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
		//	mHandler.sendEmptyMessageDelayed(BACKCARSTATEMSG, 0);
		} 
	}

	private void initControl() {
		// TODO Auto-generated method stub
		parkingLayout = (LinearLayout) findViewById(R.id.parking_layout);
		mRadarControl = new dataControl(this, parkingLayout);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		// changeSurfaceView();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		getSetting();
		com.example.cjc7150.MainActivity.setmode((byte) 1);
		super.onResume();
		
	}
	
	private void getSetting() {
		// TODO Auto-generated method stub
		int backTrackState = android.provider.Settings.System.getInt(
				getContentResolver(), BACKTRACK, 1);
		Log.i("cxs","=======backTrackState========="+backTrackState);
		if(backTrackState==1){
			findViewById(R.id.rail_line).setVisibility(View.VISIBLE);
		}else{
			findViewById(R.id.rail_line).setVisibility(View.GONE);
		}
		
		int videoMirrorState = android.provider.Settings.System.getInt(
				getContentResolver(), VIDEOMIRROR, 1);
		Log.i("cxs","=======videoMirrorState========="+videoMirrorState);
		if(videoMirrorState==1){
			Matrix transform = new Matrix();
			transform.setScale(1, 1, 0, 0);
			surface.setTransform(transform);
		}else{
			Matrix transform = new Matrix();
			transform.setScale(-1, 1, 400, 0);
			surface.setTransform(transform);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		com.example.cjc7150.MainActivity.setmode((byte) 0);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeCamera();
		if (mRadarControl != null) {
			mRadarControl.unBindService();
		}
		getContentResolver().unregisterContentObserver(mBackCarObserver);
		unregisterReceiver(myReceiver);
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
			surface = null;
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		surface = (TextureView) findViewById(R.id.camera_surface);
		surface.setSurfaceTextureListener(this);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		openCamera();
		if (camera != null) {
			Camera.Parameters mParams = camera.getParameters();

			mParams.setPreviewSize(720, 240);
			camera.setParameters(mParams);
			try {
				camera.setPreviewTexture(surface);
			} catch (IOException t) {
			}
			camera.startPreview();
			this.surface.setAlpha(1.0f);
			this.surface.setRotation(0f);
		}
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

}
