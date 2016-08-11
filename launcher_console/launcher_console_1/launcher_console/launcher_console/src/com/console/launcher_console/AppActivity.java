package com.console.launcher_console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;

import com.console.launcher_console.base.BaseActivity;
import com.console.launcher_console.util.AppInfo;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.Contacts;
import com.console.launcher_console.util.DensityUtils;
import com.console.launcher_console.util.PreferenceUtil;
import com.console.launcher_console.util.Trace;
import com.console.launcher_console.util.ViewPagerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification.Action;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AppActivity extends BaseActivity implements OnTouchListener,
		OnGestureListener {

	private int verticalMinDistance = 180;
	private int horizontalMaxDistance = 150;
	private int minVelocity = 0;
	private GestureDetector mGestureDetector;
	private int screenHeight = 0;
	private int screenWidth = 0;
	private FrameLayout appLayout;

	private List<AppInfo> mlistAppInfo = null;

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private LinearLayout indicatorLayout;

	private ISerialPortService mISpService;

	private String[] applist = { "com.mediatek.datatransfer",
			"com.android.email", "com.inet.mtk.cardplay",
			"com.android.soundrecorder", "com.android.calendar",
			"com.mediatek.fmradio", "com.android.quicksearchbox",
			"com.android.contacts", "com.android.providers.downloads.ui",
			"com.android.mms", "com.iflytek.speechcloud", "com.android.music",
			"com.mediatek.blemanager", "com.mediatek.carcorderdemo",
			"com.mediatek.engineermode", "com.mediatek.fmtx",
			"com.console.nodisturb", "com.colink.zzj.txzassistant","com.autonavi.amapauto"
			,"com.baidu.navi","cn.lzl.partycontrol","com.mtk.bluetooth","com.srtc.pingwang",
			"com.android.settings","com.android.deskclock","com.console.radio",
			"com.share.android","com.android.gallery3d","com.console.equalizer","com.xbkpnotification",
			"com.console.auxapp","com.inetwp.cardservice","com.mediatek.mco",
			"com.mxtech.videoplayer.pro","com.console.parking","com.android.stk","com.example.mtk10263.whatsTemp"};    //屏蔽显示列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_layout);
		bindSpService();
		mGestureDetector = new GestureDetector(this, (OnGestureListener) this);
		mlistAppInfo = new ArrayList<AppInfo>();
		views = new ArrayList<View>();
		queryAppInfo();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindSpService();
		super.onDestroy();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (screenHeight == 0) {
			Rect outRect = new Rect();
			getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(
					outRect);
			screenHeight = outRect.height();
			screenWidth = outRect.width();
			initLayout();
			initIndicator();
		}
	}

	private void sendModeCommad(AppInfo appInfo) {
		// TODO Auto-generated method stub
		switch (appInfo.getPkgName()) {
		case "com.console.radio":
			PreferenceUtil.setMode(this, 0);
			sendMsg("F5020000" + BytesUtil.intToHexString(0));
			break;
		case "cn.kuwo.kwmusiccar":
			PreferenceUtil.setMode(this, 1);
			sendMsg("F5020000" + BytesUtil.intToHexString(1));
			break;
		case "com.mxtech.videoplayer.pro":
			PreferenceUtil.setMode(this, 2);
			sendMsg("F5020000" + BytesUtil.intToHexString(2));
			break;
		case "com.mtk.bluetooth":
			PreferenceUtil.setMode(this, 3);
			sendMsg("F5020000" + BytesUtil.intToHexString(3));
			break;
		case "com.console.equalizer":
			PreferenceUtil.setMode(this, 5);
			sendMsg("F5020000" + BytesUtil.intToHexString(5));
			break;
		case "com.baidu.navi":
		case "com.autonavi.amapauto":
			PreferenceUtil.setMode(this, 6);
			sendMsg("F5020000" + BytesUtil.intToHexString(6));
			break;
		case "com.srtc.pingwang":
			PreferenceUtil.setMode(this, 7);
			sendMsg("F5020000" + BytesUtil.intToHexString(7));
			break;
		default:
			PreferenceUtil.setMode(this, 1);
			sendMsg("F5020000" + BytesUtil.intToHexString(1));
			break;
		}
	}

	private void initLayout() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = LayoutInflater.from(this);
		int firtMagrin = (screenWidth - (screenWidth / 5) * 5) / 2;
		for (int i = 0; i < (((mlistAppInfo.size() - 1) / 15) + 1); i = i + 1) {
			View pageView = inflater.inflate(R.layout.activity_page, null);
			appLayout = (FrameLayout) pageView.findViewById(R.id.app_layout);
			appLayout.removeAllViews();

			for (int j = 0; j < 15; j++) {
				if ((15 * i + j) >= mlistAppInfo.size()) {
					View view = inflater.inflate(R.layout.item, null);
					LayoutParams lp = new LayoutParams(screenWidth / 5,
							screenHeight / 3);
					lp.setMargins((screenWidth / 5) * (j % 5) + firtMagrin,
							(screenHeight / 3) * (j / 5), 0, 0);
					view.setLayoutParams(lp);
					appLayout.addView(view);
				} else {

					View view = inflater.inflate(R.layout.item, null);
					LayoutParams lp = new LayoutParams(screenWidth / 5,
							screenHeight / 3);
					lp.setMargins((screenWidth / 5) * (j % 5) + firtMagrin,
							(screenHeight / 3) * (j / 5), 0, 0);
					view.setLayoutParams(lp);
					((ImageView) view.findViewById(R.id.item_iv))
							.setBackground(mlistAppInfo.get(15 * i + j)
									.getAppIcon());
					((TextView) view.findViewById(R.id.item_tv))
							.setText(mlistAppInfo.get(15 * i + j).getAppLabel());
					view.setTag((15 * i + j));
					view.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = mlistAppInfo.get((int) v.getTag())
									.getIntent();
							intent.addCategory(Intent.CATEGORY_DEFAULT);
							startActivitySafely(v, intent, null);
							// sendModeCommad(mlistAppInfo.get((int)
							// v.getTag()));
						}

					});
					appLayout.addView(view);
				}

			}

			views.add(pageView);
		}

		vp = (ViewPager) findViewById(R.id.vp);
		vp.setOffscreenPageLimit(2);
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);

	}

	protected void initIndicator() {
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		for (int i = 0; i < views.size(); i++) {
			ImageView imageView = new ImageView(AppActivity.this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dip2px(this, 4), 0,
					DensityUtils.dip2px(this, 4), 0);
			imageView.setLayoutParams(lp);
			if (i == 0) {
				imageView.setImageResource(R.drawable.white_oval);
			} else {
				imageView.setImageResource(R.drawable.gray_oval);
			}
			indicatorLayout.addView(imageView);
		}
		vp.setOnPageChangeListener(mOnPageChangeListener);
	}

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < views.size(); i++) {
				((ImageView) indicatorLayout.getChildAt(i))
						.setImageResource(R.drawable.gray_oval);
			}
			((ImageView) indicatorLayout.getChildAt(arg0))
					.setImageResource(R.drawable.white_oval);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			indicatorLayout.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			indicatorLayout.setVisibility(View.INVISIBLE);
		}
	};

	private void queryAppInfo() {
		// TODO Auto-generated method stub
		PackageManager pm = this.getPackageManager(); // 获得PackageManager对象
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = pm
				.queryIntentActivities(mainIntent, 0);
		Collections.sort(resolveInfos,
				new ResolveInfo.DisplayNameComparator(pm));
		if (mlistAppInfo != null) {
			mlistAppInfo.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				int temp = 0;
				String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
				if (activityName.equals("com.android.camera.CameraLauncher")) {
					temp = 1;
					continue;
				}
				String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包�?

				for (int i = 0; i < applist.length; i++) {
					if (pkgName.equals(applist[i])) {
						temp = 1;
						continue;
					}
				}
				if (temp == 1) {
					continue;
				}
				String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
				Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
				// 为应用程序的启动Activity 准备Intent
				Intent launchIntent = new Intent();
				launchIntent.setComponent(new ComponentName(pkgName,
						activityName));
				// 创建�?��AppInfo对象，并赋�?
				AppInfo appInfo = new AppInfo();
				appInfo.setAppLabel(appLabel);
				appInfo.setPkgName(pkgName);
				appInfo.setAppIcon(icon);
				appInfo.setIntent(launchIntent);
				mlistAppInfo.add(appInfo); // 添加至列表中
			}
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e1.getY() - e2.getY() > verticalMinDistance
				&& Math.abs(velocityY) > minVelocity
				&& Math.abs(e1.getX() - e2.getX()) < horizontalMaxDistance) {
			Intent intent = new Intent(AppActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			finish();
		} else if (e2.getY() - e1.getY() > verticalMinDistance
				&& Math.abs(velocityY) > minVelocity) {

		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(AppActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		finish();
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Trace.i("onServiceConnected");
			mISpService = ISerialPortService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	private void bindSpService() {
		try {
			Intent intent = new Intent();
			intent.setClassName("cn.colink.serialport",
					"cn.colink.serialport.service.SerialPortService");
			bindService(intent, mServiceConnection,
					android.content.Context.BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unbindSpService() {
		try {
			unbindService(mServiceConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				Trace.i("Sound MainActivity sendMsg");
				mISpService.sendDataToSp(BytesUtil.addCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
