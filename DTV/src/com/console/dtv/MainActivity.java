package com.console.dtv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.transform.Templates;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SurfaceTextureListener {

	private Camera camera;// ÉùÃ÷Ïà»ú
	private TextureView surface;
	private TextView auxHint;
	private SurfaceTexture mSurfaceTexture;
	private static final int OPENCAMERA = 5;
	private static final int CLOSECAMERA = 6;
	
	public SerialPortControl mSerialPortControl;  
	ButtonControl mButtonControl;
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
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
		setContentView(R.layout.activity_main);
		initView();
		mSerialPortControl=new SerialPortControl(this);
		mButtonControl=new ButtonControl(this);
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
		mHandler.sendEmptyMessageDelayed(OPENCAMERA, 1000);
		super.onResume();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mHandler.removeMessages(OPENCAMERA);
		mHandler.sendEmptyMessageDelayed(CLOSECAMERA, 0);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeCamera();
		surface = null;
		if(mSerialPortControl!=null){
			mSerialPortControl.unbindSpService();
		}
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

	private void initView() {
		// TODO Auto-generated method stub
		auxHint = (TextView) findViewById(R.id.aux_hint);
		surface = (TextureView) findViewById(R.id.camera_surface);
		surface.setSurfaceTextureListener(this);
		Matrix transform = new Matrix();
		// transform.setScale(1.05f, 0.95f, 200,0 );
		transform.setScale(1, 1, 0, 0);
		surface.setTransform(transform);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		mSurfaceTexture = surface;
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
		auxHint.setVisibility(View.GONE);
	}

}
