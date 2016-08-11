package com.console.nodisturb;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.sql.Date;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	public static final String LEAVE_NODISTURB = "com.intent.action.LEAVE_NODISTURB";
	TextView timeTv;
	TextView dateTv;
	static long saveTime = 0;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Date curDate = new Date(System.currentTimeMillis());
				timeTv.setText(timeSdf.format(curDate));
				dateTv.setText(dateSdf.format(curDate));
				mHandler.sendEmptyMessageDelayed(1, 1000);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		setContentView(R.layout.activity_main);
		sendBroadcast(new Intent("com.inet.broadcast.no_disturb"));
		initView();
		initReceiver();
		timeSdf = new SimpleDateFormat("HH:mm");
		dateSdf = new SimpleDateFormat("yyyy.M.d EEEE");
		Date curDate = new Date(System.currentTimeMillis());
		timeTv.setText(timeSdf.format(curDate));
		dateTv.setText(dateSdf.format(curDate));
		mHandler.sendEmptyMessageDelayed(1, 1000);
	}

	private void initView() {
		// TODO Auto-generated method stub
		timeTv = (TextView) findViewById(R.id.time_tv);
		dateTv = (TextView) findViewById(R.id.date_tv);
	}

	private void initReceiver() {
		// TODO Auto-generated method stub
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(LEAVE_NODISTURB);
		registerReceiver(mBroadcastReceiver, intentFilter);
	}

	BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (TextUtils.equals(action, LEAVE_NODISTURB)) {
				MainActivity.this.finish();
			}
		}

	};
	private SimpleDateFormat dateSdf;
	private SimpleDateFormat timeSdf;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mHandler.removeMessages(1);
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - saveTime) < 500) {
				MainActivity.this.finish();				
			}
			saveTime = System.currentTimeMillis();
		}
		return super.onTouchEvent(event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
