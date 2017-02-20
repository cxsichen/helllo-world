package com.console.launcher_console.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceCallback implements SurfaceHolder.Callback {
	private final String TAG = "PreviewView";
	private final static String RECORD_SERVICE_STATE = "h264_media_code";

	private SurfaceView mSurfaceView;
	private SurfaceHolder mHolder;
	private Context mContext;
	private boolean isCameraStart = false;

	public SurfaceCallback(Context context, SurfaceView sv) {
		mContext = context;
		mSurfaceView = sv;
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("cds", "LauncherFullmirror, surfaceCreated...");
		if (isCameraStart) {
			if (android.provider.Settings.System.getInt(
					mContext.getContentResolver(), RECORD_SERVICE_STATE, 1) == 0) {
				startPreview(holder);
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.i("cds", "LauncherFullmirror, surfaceChanged...");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("cds", "LauncherFullmirror, surfaceDestroyed...");
		if (isCameraStart) {
			if (android.provider.Settings.System.getInt(
					mContext.getContentResolver(), RECORD_SERVICE_STATE, 1) == 0) {
				stopPreview(holder);
			}
		}
	}

	public void surfaceStart(boolean state) {
		isCameraStart = state;
	}

	public static final String BACK_RECORD_SERVICE_PACKAGE_NAME = "com.xair.h264demo";
	public static final String BACK_RECORD_SERVICE_CLASS_NAME = "com.xair.h264demo.MainActivity";

	public void startPreview(SurfaceHolder holder) {
		Intent i = new Intent();
		ComponentName cn = new ComponentName(BACK_RECORD_SERVICE_PACKAGE_NAME,
				BACK_RECORD_SERVICE_CLASS_NAME);
		i.setComponent(cn);
		i.putExtra("action", "startPreview");
		i.putExtra("surface", holder.getSurface());
		mContext.startService(i);
	}

	public void stopPreview(SurfaceHolder holder) {
		Intent i = new Intent();
		ComponentName cn = new ComponentName(BACK_RECORD_SERVICE_PACKAGE_NAME,
				BACK_RECORD_SERVICE_CLASS_NAME);
		i.setComponent(cn);
		i.putExtra("action", "stopPreview");
		i.putExtra("surface", holder.getSurface());
		mContext.startService(i);
	}
}
