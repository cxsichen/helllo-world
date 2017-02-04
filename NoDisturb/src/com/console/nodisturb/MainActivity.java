package com.console.nodisturb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String LEAVE_NODISTURB = "com.intent.action.LEAVE_NODISTURB";
	private static final String CMDOPEN_DESK_LYRIC = "cn.kuwo.kwmusicauto.action.OPEN_DESKLYRIC";// 打开桌面歌词
	private static final String CMDCLOSE_DESK_LYRIC = "cn.kuwo.kwmusicauto.action.CLOSE_DESKLYRIC";// 关闭桌面歌词
	private final Uri uri_navi = Uri
			.parse("content://com.zzj.softwareservice.NaviProvider/navi");
	TextView timeTv;
	TextView dateTv;
	private TextView nextRoad, distanceText, remainDistanceText;
	private ImageView maneuverImage;
	static long saveTime = 0;
	static int i = 0;
	private View navi_layout;
	private View time_layout;
	private Cursor query;
	private boolean isNaviing = false;
	private final String ISNAVING = "is_naving";
	private final String MANEUVER_IMAGE = "maneuver_Image";
	private final String NEXT_ROAD = "next_roadName";
	private final String NEXT_ROAD_DISTANCE = "next_road_distance";
	private final String TOTAL_REMAIN_TIME = "total_remain_time";
	private final String TOTAL_REMAIN_DISTANCE = "total_remain_distance";
	private static final String GMT_8 = "GMT+8";
	public static final String DATE_FORMAT = "%02d:%02d";
	private final static long MILLIS_IN_DAY = 86400000;
	public static final int SHOWNAVI = 3;
	public static final int HIDENAVI = 4;
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Date curDate = new Date(System.currentTimeMillis());
				timeTv.setText(timeSdf.format(curDate));
				dateTv.setText(dateSdf.format(curDate));
				mHandler.sendEmptyMessageDelayed(1, 1000);
				break;
			case 2:
				finish();
				break;
			case SHOWNAVI:
				//time_layout.setVisibility(View.GONE);
				navi_layout.setVisibility(View.VISIBLE);
				mHandler.removeMessages(1);
				break;
			case HIDENAVI:
			//	time_layout.setVisibility(View.VISIBLE);
				navi_layout.setVisibility(View.GONE);
				mHandler.sendEmptyMessage(1);
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

		initView();
		initReceiver();
		timeSdf = new SimpleDateFormat("HH:mm");
		dateSdf = new SimpleDateFormat("yyyy.M.d EEEE");
		Date curDate = new Date(System.currentTimeMillis());
		timeTv.setText(timeSdf.format(curDate));
		dateTv.setText(dateSdf.format(curDate));
		mHandler.sendEmptyMessage(1);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		i++;
		if (i > 2) {
			mHandler.removeMessages(2);
			mHandler.sendEmptyMessageDelayed(2, 500);
		}
		sendBroadcast(new Intent("com.inet.broadcast.no_disturb"));
		int lrc_show = Settings.System.getInt(getApplicationContext()
				.getContentResolver(), "screen_off_lrc_switch", 0);
		Log.d("screen", "lrc = " + lrc_show);
		if (lrc_show == 0) {
			sendBroadcast(new Intent(CMDOPEN_DESK_LYRIC));
		}
		getNaviInfo(uri_navi);
		getContentResolver().registerContentObserver(uri_navi, false,
				observernew);
	}

	private void initView() {
		// TODO Auto-generated method stub
		navi_layout = findViewById(R.id.toast_layout_root);
		nextRoad = (TextView) findViewById(R.id.nextRoad);
		distanceText = (TextView) findViewById(R.id.distance);
		remainDistanceText = (TextView) findViewById(R.id.remainDistance);
		maneuverImage = (ImageView) findViewById(R.id.imageView);
		time_layout = findViewById(R.id.time_layout);
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
		i = 0;
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

	private ContentObserver observernew = new ContentObserver(new Handler()) {

		@Override
		public void onChange(boolean selfChange, Uri uri) {
			// TODO Auto-generated method stub
			super.onChange(selfChange, uri);
			getNaviInfo(uri);
		}

	};

	private void getNaviInfo(Uri uri) {
		try {
			query = getContentResolver().query(uri, null, null, null, null);
			if (query.moveToNext()) {
				isNaviing = query.getInt(query.getColumnIndex(ISNAVING)) == 1;
				if (isNaviing) {
					mHandler.removeMessages(HIDENAVI);
					mHandler.sendEmptyMessageDelayed(SHOWNAVI, 0);
					String maneuver_Image = query.getString(query
							.getColumnIndex(MANEUVER_IMAGE));
					String next_road = query.getString(query
							.getColumnIndex(NEXT_ROAD));
					int next_road_distance = query.getInt(query
							.getColumnIndex(NEXT_ROAD_DISTANCE));
					int total_distance = query.getInt(query
							.getColumnIndex(TOTAL_REMAIN_DISTANCE));
					int remainTime = query.getInt(query
							.getColumnIndex(TOTAL_REMAIN_TIME));

					nextRoad.setText(next_road);

					remainDistanceText.setText(getRemainDidistance(
							total_distance, System.currentTimeMillis()
									+ remainTime * 1000));
					int resID = getResources().getIdentifier(maneuver_Image,
							"drawable", getApplicationInfo().packageName);
					maneuverImage.setBackgroundResource(resID);
					distanceText.setText(getDidistance(next_road_distance));
				} else {
					mHandler.sendEmptyMessageDelayed(HIDENAVI, 3000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (query != null)
				query.close();
		}
	}

	private String getDidistance(int s) {
		String dis;
		if (s < 1000) {
			dis = String.format(getString(R.string.mi), s);
		} else if (s < 100000) {
			dis = String.format(getString(R.string.gl), (float) s * 1.0 / 1000);
		} else {
			dis = String.format(getString(R.string.gl_far), s / 1000);
		}

		return dis;
	}

	private String getRemainDidistance(int s, long time) {
		long nowTime = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(GMT_8));
		calendar.setTimeInMillis(time);
		int l = (int) (toDay(time) - toDay(nowTime));
		String d;
		switch (l) {
		case 0:
			d = String.format(DATE_FORMAT, calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE));
			break;
		case 1:
			d = "明天";
			break;
		case 2:
			d = "后天";
			break;
		default:
			d = l + "天后";
			break;
		}
		String dis;
		if (s < 1000) {
			dis = String.format(getString(R.string.arrive_mi), s, d);
		} else if (s < 1000000) {

			dis = String.format(getString(R.string.arrive_gl),
					(float) s * 1.0 / 1000, d);
		} else {
			dis = String.format(getString(R.string.arrive_gl_far), s / 1000, d);
		}

		return dis;
	}

	private long toDay(long millis) {
		return (millis + TimeZone.getDefault().getOffset(millis))
				/ MILLIS_IN_DAY;
	}

}
