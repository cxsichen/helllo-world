package com.console.launcher_console.control;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.console.launcher_console.R;
import com.console.launcher_console.R.string;
import com.console.launcher_console.util.Constact;
import com.console.launcher_console.util.Trace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;

public class NaviCardControl extends BroadcastReceiver implements Constact {

	private FrameLayout naviCardLayout;
	private Context context;
	private LinearLayout naviLayout;
	private LinearLayout compassLayout;
	private Cursor tps;
	private TextView nextDistance;
	private ImageView maneuverImg;
	private TextView nextRoadName;
	private TextView remainMessage;
	private boolean showNaviLayout = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    Handler mHandler =new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case 1:
				showNaviLayout = false;
				showLayout(compassLayout);
				hideLayout(naviLayout);
				break;
			default:
				break;
			}
    	};
    };
	public NaviCardControl(Context context, FrameLayout layout) {
		naviCardLayout = layout;
		this.context = context;
		init();
		registerReceiver();
	}

	private void init() {
		// TODO Auto-generated method stub
		compassLayout = (LinearLayout) naviCardLayout
				.findViewById(R.id.compass_layout);
		naviLayout = (LinearLayout) naviCardLayout
				.findViewById(R.id.navi_layout);
		nextDistance = (TextView) naviCardLayout
				.findViewById(R.id.next_distance);
		maneuverImg = (ImageView) naviCardLayout
				.findViewById(R.id.maneuver_name);
		nextRoadName = (TextView) naviCardLayout
				.findViewById(R.id.next_road_name);
		remainMessage = (TextView) naviCardLayout
				.findViewById(R.id.remain_message);
	}

	private void registerReceiver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(STOPNAVI);
		filter.addAction(STARTNAVI);
		filter.setPriority(Integer.MAX_VALUE);
		context.registerReceiver(this, filter);

		context.getContentResolver().registerContentObserver(NAVI_CONTENT_URI,
				false, navi);
	}

	public void unregisterReceiver() {
		// TODO Auto-generated method stub
		context.unregisterReceiver(this);
		context.getContentResolver().unregisterContentObserver(navi);
	}

	private ContentObserver navi = new ContentObserver(new Handler()) {

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			changeNaviView();
		}

	};

	private void changeNaviView() {
		// TODO Auto-generated method stub
		mHandler.removeMessages(1);
		mHandler.sendEmptyMessageDelayed(1, 10*1000);
		tps = context.getContentResolver().query(NAVI_CONTENT_URI, null, null,
				null, null);

		try {
			if (tps != null && tps.moveToNext()) {
				if (tps.getString(tps.getColumnIndex(IS_NAVING)).equals("1")) {

					if (!showNaviLayout) {
						showNaviLayout = true;
						showLayout(naviLayout);
						hideLayout(compassLayout);
					}
					//下个路口的距离
					String nextDString = tps.getString(tps
							.getColumnIndex(NEXT_ROAD_DISTANCE));
					if (Integer.parseInt(nextDString) > 1000) {
						nextDistance
								.setText(Integer.toString(Integer
										.parseInt(nextDString) / 1000)
										+ context
												.getString(R.string.next_road_km_unit));
					} else {
						Log.i("cxs", "======22======");
						nextDistance.setText(nextDString
								+ context.getString(R.string.next_road_unit));
					}

					//导航箭头
					Log.i("cxs","====tps.getString(tps.getColumnIndex(MANEUVER_IMAGE)=============="+tps
							.getString(tps.getColumnIndex(MANEUVER_IMAGE)));
					maneuverImg.setImageResource(getResourceWerterName(tps
							.getString(tps.getColumnIndex(MANEUVER_IMAGE))));
					//下个路口的名称
					nextRoadName.setText(tps.getString(tps
							.getColumnIndex(NEXT_ROADNAME)));
					
					//剩余时间
					String curDate = null;
					int temp = tps
							.getInt(tps.getColumnIndex(TOTAL_REMAIN_TIME))
							/ (60 * 60);
					if (temp >= 12) {
						curDate = temp
								+ context.getString(R.string.remind_day_unit);
					} else {
						curDate = sdf.format(new Date(System
								.currentTimeMillis()
								+ tps.getInt(tps
										.getColumnIndex(TOTAL_REMAIN_TIME))
								* 1000));
					}

					remainMessage.setText(tps.getInt(tps
							.getColumnIndex(TOTAL_REMAIN_DISTANCE))
							/ 1000
							+ context.getString(R.string.remind_distance_unit)
							+ "  "
							+ curDate
							+ context.getString(R.string.remind_time_unit));

				} else {
					mHandler.sendEmptyMessageDelayed(1, 5*1000);
				}
				if (tps != null) {
					tps.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public int getResourceWerterName(String imageName) {
		int resId = context.getResources().getIdentifier(imageName, "drawable",
				context.getPackageName());
		return resId;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Trace.i("-----onReceive------" + intent.getAction());
		switch (intent.getAction()) {
		case STOPNAVI:			
			showNaviLayout = false;
			showLayout(compassLayout);
			hideLayout(naviLayout);
			mHandler.removeMessages(1);
			break;
		case STARTNAVI:
			showNaviLayout = true;
			showLayout(naviLayout);
			hideLayout(compassLayout);
			break;
		default:
			break;
		}
	}

	private void showLayout(ViewGroup vg) {
		// TODO Auto-generated method stub
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.fade_in);
		vg.startAnimation(animation);
		vg.setVisibility(View.VISIBLE);
	}

	private void hideLayout(ViewGroup vg) {
		// TODO Auto-generated method stub
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.fade_out);
		vg.startAnimation(animation);
		vg.setVisibility(View.GONE);
	}
}
