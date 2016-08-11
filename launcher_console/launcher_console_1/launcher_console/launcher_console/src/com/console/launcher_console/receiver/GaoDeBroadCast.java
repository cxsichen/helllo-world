package com.console.launcher_console.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.autonavi.external.model.EDataFactory;
import com.autonavi.external.model.ENaviInfo;
import com.zzj.softwareservice.database.DBConstant;

public class GaoDeBroadCast extends BroadcastReceiver {

	private static final String EXTRA_OPERA = "EXTRA_OPERA";
	private static final String EXTRA_TYPE = "EXTRA_TYPE";
	private static final String AUTONAVI_BROADCAST = "AUTONAVI_STANDARD_BROADCAST_RECV";
	private static final String EXTRA_STATE = "EXTRA_STATE";
	private static final String TOTAL_REMAIN_DISTANCE2 = "total_remain_distance";
	private static final String TOTAL_REMAIN_TIME = "total_remain_time";
	private static final String ROAD_ICON2 = "road_icon";
	private static final String CURRENT_ROAD_REMAIN_DISTANCE = "current_road_remain_distance";
	private static final String NEXT_ROAD_NAME = "next_road_name";
	private static final String CURRENT_ROAD_NAME = "current_road_name";
	private static final String AR = "ar_";
	private static final String TYPE = "type";
	private final static String NAVI_GAODE = "com.amap.navi";
	private final static String NAVI_GAODE_PW = "com.autonavi.minimap.carmode.send";
	private final static String SEND_NAVI_INFO = "NAVI_INFO";
	private final static String SEND_BUSINESS_ACTION = "send_business_action";
	private final static String SEND_BUSINESS_DATA = "send_business_data";
	private final static String AUTONAVI_ACTION = "AUTONAVI_STANDARD_BROADCAST_SEND";
	private final static String KEY_TYPE = "KEY_TYPE";
	private static Context mContext;

	String imageGroup[] = { "", "", "turn_left", "turn_right",
			"turn_left_front", "turn_right_front", "turn_left_back",
			"turn_right_back", "turn_back", "turn_front", "turn_via_1",
			"turn_ring", "turn_ring_out", "hud_turn_service",
			"hud_turn_tollgate", "turn_dest", "tunnel" };
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ContentValues con = new ContentValues();
				con.put(DBConstant.NaviTable.ISNAVING, 0);
				mContext.getContentResolver().update(
						DBConstant.NaviTable.CONTENT_URI, con, null, null);
				break;

			case 1:
				ContentValues values = new ContentValues();
				values.put(DBConstant.NaviTable.ISNAVING, 1);
				mContext.getContentResolver().update(
						DBConstant.NaviTable.CONTENT_URI, values, null, null);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		String action = intent.getAction();
		Log.e("info", action);
		if (NAVI_GAODE.equals(action)) {
			Bundle extras = intent.getExtras();
			int type = extras.getInt(TYPE);
			switch (type) {
			case 101:
				mHandler.removeMessages(0);
				mHandler.sendEmptyMessageDelayed(0, 20000);
				String current_road = extras.getString(CURRENT_ROAD_NAME);
				String next_road = extras.getString(NEXT_ROAD_NAME);
				int next_road_distance = extras
						.getInt(CURRENT_ROAD_REMAIN_DISTANCE);
				int road_icon = extras.getInt(ROAD_ICON2);
				int total_remain_time = extras.getInt(TOTAL_REMAIN_TIME);
				int total_remain_distance = extras
						.getInt(TOTAL_REMAIN_DISTANCE2);

				ContentValues con = new ContentValues();
				con.put(DBConstant.NaviTable.MANEUVER_IMAGE,
						imageGroup[road_icon]);
				con.put(DBConstant.NaviTable.NEXT_ROAD_DISTANCE,
						next_road_distance);
				con.put(DBConstant.NaviTable.CURRENT_ROADNAME, current_road);
				con.put(DBConstant.NaviTable.NEXT_ROAD, next_road);
				con.put(DBConstant.NaviTable.TOTAL_REMAIN_DISTANCE,
						total_remain_distance);
				con.put(DBConstant.NaviTable.TOTAL_REMAIN_TIME,
						total_remain_time);
				con.put(DBConstant.NaviTable.ISNAVING, 1);
				context.getContentResolver().update(
						DBConstant.NaviTable.CONTENT_URI, con, null, null);
				break;
			default:
				break;
			}
		} else if (NAVI_GAODE_PW.equals(action)) {
			String businessAct;
			try {
				businessAct = intent.getStringExtra(SEND_BUSINESS_ACTION);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			if (SEND_NAVI_INFO.equals(businessAct)) {
				String sData = intent.getStringExtra(SEND_BUSINESS_DATA);
				if (!TextUtils.isEmpty(sData)) {
					ENaviInfo data;
					try {
						data = (ENaviInfo) EDataFactory.create(sData);
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
					mHandler.removeMessages(0);
					mHandler.sendEmptyMessageDelayed(0, 20000);
					if (data != null) {
						String current_road = data.getCurRoadName();
						String next_road = data.getNextRoadName();
						int total_remain_distance = data.getRouteRemainDis();
						int total_remain_time = data.getRouteRemainTime();
						int road_icon = data.getIcon();
						int next_road_distance = data.getSegRemainDis();

						ContentValues con = new ContentValues();
						con.put(DBConstant.NaviTable.MANEUVER_IMAGE,
								imageGroup[road_icon]);
						con.put(DBConstant.NaviTable.NEXT_ROAD_DISTANCE,
								next_road_distance);
						con.put(DBConstant.NaviTable.CURRENT_ROADNAME,
								current_road);
						con.put(DBConstant.NaviTable.NEXT_ROAD, next_road);
						con.put(DBConstant.NaviTable.TOTAL_REMAIN_DISTANCE,
								total_remain_distance);
						con.put(DBConstant.NaviTable.TOTAL_REMAIN_TIME,
								total_remain_time);
						con.put(DBConstant.NaviTable.ISNAVING, 1);
						context.getContentResolver().update(
								DBConstant.NaviTable.CONTENT_URI, con, null,
								null);

					}
				}
			}

		} else if (AUTONAVI_ACTION.equals(action)) {
			int type = intent.getIntExtra(KEY_TYPE, 0);
			if (type == 10001) {

				mHandler.removeMessages(0);
				mHandler.sendEmptyMessageDelayed(0, 20000);

				String current_road = intent
						.getStringExtra(GuideInfoExtraKey.CUR_ROAD_NAME);
				String next_road = intent
						.getStringExtra(GuideInfoExtraKey.NEXT_ROAD_NAME);
				int total_remain_distance = intent.getIntExtra(
						GuideInfoExtraKey.ROUTE_REMAIN_DIS, 0);
				int total_remain_time = intent.getIntExtra(
						GuideInfoExtraKey.ROUTE_REMAIN_TIME, 0);
				int road_icon = intent.getIntExtra(GuideInfoExtraKey.ICON, 0);
				int next_road_distance = intent.getIntExtra(
						GuideInfoExtraKey.SEG_REMAIN_DIS, 0);

				ContentValues con = new ContentValues();
				con.put(DBConstant.NaviTable.MANEUVER_IMAGE,
						imageGroup[road_icon]);
				con.put(DBConstant.NaviTable.NEXT_ROAD_DISTANCE,
						next_road_distance);
				con.put(DBConstant.NaviTable.CURRENT_ROADNAME, current_road);
				con.put(DBConstant.NaviTable.NEXT_ROAD, next_road);
				con.put(DBConstant.NaviTable.TOTAL_REMAIN_DISTANCE,
						total_remain_distance);
				con.put(DBConstant.NaviTable.TOTAL_REMAIN_TIME,
						total_remain_time);
				con.put(DBConstant.NaviTable.ISNAVING, 1);
				context.getContentResolver().update(
						DBConstant.NaviTable.CONTENT_URI, con, null, null);
			} else if (type == 10019) {
				int state = intent.getIntExtra(EXTRA_STATE, 0);
				switch (state) {
				case 2:
				case 9:
				case 12:
					ContentValues con = new ContentValues();
					con.put(DBConstant.NaviTable.ISNAVING, 0);
					context.getContentResolver().update(
							DBConstant.NaviTable.CONTENT_URI, con, null, null);
					break;
				/*
				 * case 8: case 10: Intent gdOp = new
				 * Intent(AUTONAVI_BROADCAST); gdOp.putExtra(KEY_TYPE, 10027);
				 * gdOp.putExtra(EXTRA_TYPE, 2); gdOp.putExtra(EXTRA_OPERA, 0);
				 * gdOp.getExtras().putInt(EXTRA_TYPE, 2);
				 * gdOp.getExtras().putInt(EXTRA_OPERA, 0);
				 * context.sendBroadcast(gdOp); break;
				 */
				default:
					break;
				}
			}
		}
	}
}
