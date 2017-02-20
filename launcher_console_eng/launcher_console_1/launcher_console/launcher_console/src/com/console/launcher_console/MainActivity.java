package com.console.launcher_console;

import java.util.ArrayList;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.console.launcher_console.AngleUtil.MyLatLng;
import com.console.launcher_console.PagedView.OnScreenChangeListener;
import com.console.launcher_console.PagedView.OnScreenChangeListenerDataLoad;
import com.console.launcher_console.base.BaseActivity;
import com.console.launcher_console.control.BTCardControl;
import com.console.launcher_console.control.CompassControl;
import com.console.launcher_console.control.FmCardControl;
import com.console.launcher_console.control.MusicCardControl;
import com.console.launcher_console.control.NaviCardControl;
import com.console.launcher_console.control.OtherControl;
import com.console.launcher_console.control.RecCardControl;
import com.console.launcher_console.control.RecCardControl2;
import com.console.launcher_console.control.SerialPortControl;
import com.console.launcher_console.control.SettingCardControl;
import com.console.launcher_console.control.TpmsContol;
import com.console.launcher_console.control.WeatherController;
import com.console.launcher_console.control.XmlyCardControl;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.Constact;
import com.console.launcher_console.util.Contacts;
import com.console.launcher_console.util.DensityUtils;
import com.console.launcher_console.util.PreferenceUtil;
import com.console.launcher_console.util.longClickUtil;
import com.ximalaya.speechcontrol.IMainDataCallback;
import com.ximalaya.speechcontrol.IServiceBindSuccessCallBack;
import com.ximalaya.speechcontrol.IServiceDeathListener;
import com.ximalaya.speechcontrol.SpeechControler;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class MainActivity extends BaseActivity implements OnClickListener,
		OnTouchListener, OnGestureListener {

	PagedView content;
	private ImageView ev_music_play;
	private ImageView ev_nav_app;
	private ImageView ev_radio_app;
	private ImageView ev_bt_app;
	private ImageView ev_music_app;
	private ImageView ev_nav_compss;
	private ImageView ev_rec_app;
	private FrameLayout naviCardLayout;
	private LinearLayout indicatorLayout;
	private static final int default_page = 1;
	private static final int fm_page = 0;

	public static ImageView ev_point;
	public static Drawable drawable;
	public static Drawable drawable1;
	public static Drawable drawable2;

	private NaviCardControl mNaviCarControl;
	private MusicCardControl mMusicCardControl;
	private CompassControl mCompassControl;
	private SettingCardControl mSettingCardControl;
	private BTCardControl mBTCardControl;
	private FmCardControl mFmCardControl;
	private WeatherController mWeatherController;
	private OtherControl mOtherControl;
	private TpmsContol mTpmsContol;
	private SerialPortControl mSerialPortControl;
	private XmlyCardControl mXmlyCardControl;
	private SpeechControler controler;
	// 记录仪卡片
	private RecCardControl mRecCardControl;
	private RecCardControl2 mRecCardControl2;

	private int verticalMinDistance = 100;
	private int horizontalMaxDistance = 250;
	private int minVelocity = 0;
	private GestureDetector mGestureDetector;
	int currentIndex = 1;
	Boolean isResume = false;

	public static final String RECAPP_1 = "com.cam.dod";
	public static final String RECAPP_0 = "com.xair.h264demo";
	public static final int NAV_HOME=201;
	public static final int NAV_WORK=202;
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NAV_HOME:
				//startNaviHome();
				break;
			case NAV_WORK:
			//	startNaviWork();
				break;
			default:
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View group = inflater.inflate(R.layout.activity_main, null);
		content = (PagedView) group.findViewById(R.id.pane_content);
		indicatorLayout = (LinearLayout) group.findViewById(R.id.indicator);
		mGestureDetector = new GestureDetector(this, (OnGestureListener) this);
		init(content);
		setContentView(group);
		init_event();
		initControl();
		registerHomeKeyReceiver(this);

	}


	private void initControl() {
		// TODO Auto-generated method stub

		mSerialPortControl = new SerialPortControl(this);

		mNaviCarControl = new NaviCardControl(getApplicationContext(),
				(FrameLayout) findViewById(R.id.navi_car_layout));
		//mMusicCardControl = new MusicCardControl(getApplicationContext(),
		//		(LinearLayout) findViewById(R.id.music_card_layout));
		// mCompassControl = new CompassControl(getApplicationContext(),
		// (FrameLayout) findViewById(R.id.navi_car_layout));
		mSettingCardControl = new SettingCardControl(this,
				(LinearLayout) findViewById(R.id.setting_card_layout),
				mSerialPortControl);
		// mBTCardControl = new BTCardControl(getApplicationContext(),
		// (LinearLayout) findViewById(R.id.bt_card_layout));
		mTpmsContol = new TpmsContol(getApplicationContext(),
				(RelativeLayout) findViewById(R.id.tpms_layout));
		mFmCardControl = new FmCardControl(getApplicationContext(),
				(LinearLayout) findViewById(R.id.fm_layout), mSerialPortControl);

		//mWeatherController = new WeatherController(getApplicationContext(),
		//		(LinearLayout) findViewById(R.id.weather_card_layout));
		mOtherControl = new OtherControl(getApplicationContext(),
				(LinearLayout) findViewById(R.id.other_card_layout),
				mSerialPortControl);
		if (Settings.System.getInt(getContentResolver(),
				Constact.CAMAPPCHOOSE, 0)==0) {
			mRecCardControl2 = new RecCardControl2(getApplicationContext(),
					(LinearLayout) findViewById(R.id.rec_layout));
		}

		//initXmlyControl();

	}

	private void initXmlyControl() {
		try {

			controler = SpeechControler.getInstance(this);
			controler.init("bfdb1f2fe2466b91b0f7edae5cc59741",
					"8a76c8e1a44991298acb4fe69fb26b3c",
					"com.console.launcher_console");

		} catch (Exception e) {
			// TODO: handle exception
		}
		mXmlyCardControl = new XmlyCardControl(getApplicationContext(),
				(LinearLayout) findViewById(R.id.bg_card_xmly), controler);
	}

	private void init(PagedView view) {

		view.addView(getNewPage(R.layout.activity_page_4));
		view.addView(getNewPage(R.layout.activity_page_5));
		view.addView(getNewPage(R.layout.activity_page_6));

		view.invalidate();
		initIndicator(view);

	}


	private View getNewPage(int resid) {
		View view = LayoutInflater.from(this).inflate(resid, null);
		view.setVisibility(View.VISIBLE);
		return view;
	}

	protected void initIndicator(PagedView view) {

		for (int i = 0; i < 3; i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dip2px(this, 4), 0,
					DensityUtils.dip2px(this, 4), 0);
			imageView.setLayoutParams(lp);
			if (i == default_page) {
				imageView.setImageResource(R.drawable.white_oval);
			} else {
				imageView.setImageResource(R.drawable.gray_oval);
			}
			indicatorLayout.addView(imageView);
		}

		view.setOnScreenChangeListener(new OnScreenChangeListener() {

			@Override
			public void onScreenChange(int currentIndex) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 3; i++) {
					((ImageView) indicatorLayout.getChildAt(i))
							.setImageResource(R.drawable.gray_oval);
				}
				((ImageView) indicatorLayout.getChildAt(currentIndex))
						.setImageResource(R.drawable.white_oval);
			}
		});

		view.setOnScreenChangeListenerDataLoad(new OnScreenChangeListenerDataLoad() {

			@Override
			public void onScreenChange(Boolean pageMoving) {
				// TODO Auto-generated method stub
				if (pageMoving) {
					indicatorLayout.setVisibility(View.VISIBLE);
				} else {
					indicatorLayout.setVisibility(View.GONE);
				}
			}
		});

	}

	private void init_event() {
		// rec_card
		naviCardLayout = (FrameLayout) findViewById(R.id.navi_car_layout);
		naviCardLayout.setOnClickListener(this);
		findViewById(R.id.nav_home).setOnClickListener(this);
		findViewById(R.id.nav_work).setOnClickListener(this);

		ev_nav_compss = (ImageView) findViewById(R.id.ev_nav_compss);

		// ev_bt_app = (ImageView) findViewById(R.id.ev_bt_app);
		// ev_bt_app.setOnClickListener(this);

		ev_radio_app = (ImageView) findViewById(R.id.ev_radio_app);
		ev_radio_app.setOnClickListener(this);

		findViewById(R.id.rec_layout_btn).setOnClickListener(this);
		findViewById(R.id.LockButton).setOnClickListener(this);
		findViewById(R.id.RecordButton).setOnClickListener(this);
		findViewById(R.id.CaptureButton).setOnClickListener(this);

		findViewById(R.id.navigation_one_button).setOnClickListener(this);
		findViewById(R.id.ev_music_app).setOnClickListener(this);
		findViewById(R.id.bt_layout).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ev_music_app:
			Intent musicIntent = new Intent();
			musicIntent.setClassName("com.google.android.music",
					"com.android.music.activitymanagement.TopLevelActivity");
			startActivitySafely(v, musicIntent, null);

			break;
		case R.id.rec_layout_btn:
			if (longClickUtil.isDoubleClick()) {
				Toast.makeText(this,
						getResources().getString(R.string.double_click),
						Toast.LENGTH_SHORT).show();
				return;
			}
			startCameraApp(v);
			break;
		case R.id.navi_car_layout:
			startNavi(v);
			break;
		case R.id.ev_bt_app:
			Intent btIntent = getPackageManager().getLaunchIntentForPackage(
					"com.mtk.bluetooth");
			startActivitySafely(v, btIntent, null);
			break;
		case R.id.ev_radio_app:
			Intent radioTntent = getPackageManager().getLaunchIntentForPackage(
					"cn.colink.fm");
			startActivitySafely(v, radioTntent, null);
			break;
		case R.id.bt_layout:
			Intent bt_Intent = getPackageManager().getLaunchIntentForPackage(
					"com.mtk.bluetooth");
			startActivitySafely(v, bt_Intent, null);
			break;
		default:
			break;
		}
	}

	private void startNaviWork() {
		try {
			int mapType = Settings.System.getInt(getContentResolver(),
					Constact.MAP_INDEX, 0);
			if (mapType == 0) {
				// 百度地图回公司
				Intent intent = new Intent();
				intent.setData(Uri.parse("bdnavi://gohome"));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			} else {
				Intent intent = new Intent();
				intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
				intent.putExtra("KEY_TYPE", 10040);
				intent.putExtra("DEST", 1); // 0回家 1回公司
				intent.putExtra("IS_START_NAVI", 0);// 是否直接开始导航 0是 1否
				sendBroadcast(intent);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void startNaviHome() {
		try {
			int mapType = Settings.System.getInt(getContentResolver(),
					Constact.MAP_INDEX, 0);
			if (mapType == 0) {
				// 百度地图回公司
				Intent intent = new Intent();
				intent.setData(Uri.parse("bdnavi://gocompany"));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
			} else {
				Intent intent = new Intent();
				intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
				intent.putExtra("KEY_TYPE", 10040);
				intent.putExtra("DEST", 0); // 0回家 1回公司
				intent.putExtra("IS_START_NAVI", 0);// 是否直接开始导航 0是 1否
				sendBroadcast(intent);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void startCameraApp(View v) {
		int camType = Settings.System.getInt(getContentResolver(),
				Constact.CAMAPPCHOOSE, 0);

		if (camType == 0) {
			if (mRecCardControl2 == null) {
				mRecCardControl2 = new RecCardControl2(getApplicationContext(),
						(LinearLayout) findViewById(R.id.rec_layout));
			}
			Intent recIntent3 = getPackageManager().getLaunchIntentForPackage(
					RECAPP_0);
			recIntent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivitySafely(v, recIntent3, null);
		} else {
			if (mRecCardControl2 != null) {
				mRecCardControl2.unRegisterObserver();
				mRecCardControl2=null;
			}
			Intent recIntent1 = getPackageManager().getLaunchIntentForPackage(
					RECAPP_1);
			recIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivitySafely(v, recIntent1, null);
		}
	}

	private void startNavi(View v) {
		// TODO Auto-generated method stub
		int mapType = Settings.System.getInt(getContentResolver(),
				Constact.MAP_INDEX, 0);
		if (mapType == 0) {
			Intent naviIntent = getPackageManager()
					.getLaunchIntentForPackage("com.here.app.maps");
			startActivitySafely(v, naviIntent, null);
		} else if (mapType == 1) {
			Intent naviIntent = getPackageManager()
					.getLaunchIntentForPackage(
							"com.google.android.apps.maps");
			startActivitySafely(v, naviIntent, null);
		} else if (mapType == 2) {
			Intent naviIntent = getPackageManager()
					.getLaunchIntentForPackage("com.waze");
			startActivitySafely(v, naviIntent, null);
		}
	}



	@Override
	protected void onResume() {
		// TODO Auto-g';';enerated method stub
		if (mFmCardControl != null)
			mFmCardControl.onResume();
		if (mSerialPortControl != null)
			mSerialPortControl.bindSpService();
		if (mRecCardControl != null)
			mRecCardControl.resumeVideoPreview(); // rec开始预览
		// setWallPaper()
		super.onResume();
		isResume = true;

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isResume = false;
		if (mRecCardControl != null)
			mRecCardControl.pauseVideoPreview(); // rec暂停预览
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// Debug.stopMethodTracing();
		if (mNaviCarControl != null) {
			mNaviCarControl.unregisterReceiver();
		}
		if (mMusicCardControl != null) {
			mMusicCardControl.unregister();
		}
		if (mBTCardControl != null) {
			mBTCardControl.unregisterReceiver();
		}

		if (mRecCardControl2 != null) {
			mRecCardControl2.unRegisterObserver();
		}
		if (mSerialPortControl != null) {
			mSerialPortControl.unbindSpService();
		}

		if (mTpmsContol != null) {
			mTpmsContol.unregisterContentObserver();
		}
		if (mRecCardControl != null)
			mRecCardControl.stopVideoPreview(); // 停止预览
		unregisterHomeKeyReceiver(this);
		super.onDestroy();

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
		if (e2.getY() - e1.getY() > verticalMinDistance
				&& Math.abs(velocityY) > minVelocity
				&& Math.abs(e1.getX() - e2.getX()) < horizontalMaxDistance) {
			Intent intent = new Intent(MainActivity.this, AppActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);

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
					if (PreferenceUtil.getMode(MainActivity.this) == 0) {
						content.snapToPage(fm_page, 100);
					} else {
						if (isResume) {
							content.snapToPage(default_page, 1000);
						}
					}
				} else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
				} else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
				} else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}

}
