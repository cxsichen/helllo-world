package com.console.auxapp;

import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SurfaceTextureListener {

	private Camera camera;// ÉùÃ÷Ïà»ú
	private TextureView surface;
	private TextView auxHint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
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
		if (com.example.cjc7150.MainActivity.getmode() != 0)
			com.example.cjc7150.MainActivity.setmode((byte) 0);
		super.onResume();
		openCamera();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		closeCamera();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeCamera();
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

	private void initView() {
		// TODO Auto-generated method stub
		auxHint = (TextView) findViewById(R.id.aux_hint);
		surface = (TextureView) findViewById(R.id.camera_surface);
		surface.setSurfaceTextureListener(this);
		Matrix transform = new Matrix();
		transform.setScale(1, 1, 0, 0);
		surface.setTransform(transform);
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
		auxHint.setVisibility(View.GONE);
	}

}
