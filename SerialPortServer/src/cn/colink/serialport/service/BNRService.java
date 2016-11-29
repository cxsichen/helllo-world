package cn.colink.serialport.service;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import com.baidu.navisdk.remote.BNRemoteVistor;
import com.baidu.navisdk.remote.aidl.BNEventListener;


import cn.colink.serialport.utils.Contacts;
import com.zzj.softwareservice.database.DBConstant;

public class BNRService extends Service implements Contacts {
	private final static String PATH = "ar_";
	private static final String ACC_STATE = "acc_state";
	// private WeatherController mController;
	BNEventListener.Stub mBNEventListener = new BNEventListener.Stub() {

		/**
		 * �����յ�ͼ����»ص�
		 * 
		 * @param assitantType
		 *            �����յ�����
		 *            {@link com.baidu.navisdk.remote.BNRemoteConstants.AssitantType
		 *            <code>AssitantType</code>}
		 * @param limitedSpeed
		 *            ��������SpeedCamera��IntervalCamera��ʱ�򣬻�������ٵ�ֵ
		 * @param distance
		 *            �յ�����(����λ��λ),������Ϊ0ʱ����������յ���ʧ��ʧ
		 */
		@Override
		public void onAssistantChanged(int assistantType, int limitedSpeed,
				int distance) throws RemoteException {
		}

		/**
		 * ���������»ص�
		 * 
		 * @param serviceArea
		 *            ������������
		 * @param distance
		 *            �������ľ��룬��distanceΪ0����serviceAreaΪ��ʱ��������������ʧ
		 */
		@Override
		public void onServiceAreaChanged(String serviceArea, int distance) {
		}

		/**
		 * GPS�ٶȱ仯����ʵ��Ӧ���У���ĳЩ����£��ֻ�GPS�ٶ���ʵ�ʳ��������4km/h����
		 * 
		 * @param speed
		 *            gps�ٶȣ���λkm/h
		 * @param latitude
		 *            γ�ȣ�GCJ-02����
		 * @param longitude
		 *            ���ȣ�GCJ-02����
		 */
		@Override
		public void onGpsChanged(int speed, double latitude, double longitude) {
		}

		/**
		 * �������������
		 * 
		 * @param maneuverName
		 *            ��һ�����������ƣ�������Բο������ϣ�ÿһ�����������ƶ�Ӧ��ͼ��
		 * @param distance
		 *            ������һ����������루����Ϊ��λ��
		 */
		@Override
		public void onManeuverChanged(final String maneuverName,
				final int distance) {
			Log.d("test", maneuverName);
			// MainActivity.nav_point(maneuverName);
			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.MANEUVER_IMAGE, maneuverName);
			con.put(DBConstant.NaviTable.NEXT_ROAD_DISTANCE, distance);
			con.put(DBConstant.NaviTable.ISNAVING, 1);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);
		}

		/**
		 * ����Ŀ�ĵصľ����ʱ�����
		 * 
		 * @param remainDistance
		 *            ����Ŀ�ĵص�ʣ����루����Ϊ��λ��
		 * @param remainTime
		 *            ����Ŀ�ĵص�ʣ��ʱ��(����Ϊ��λ)
		 */
		@Override
		public void onRemainInfoChanged(final int remainDistance,
				final int remainTime) {
			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.TOTAL_REMAIN_DISTANCE, remainDistance);
			con.put(DBConstant.NaviTable.TOTAL_REMAIN_TIME, remainTime);
			con.put(DBConstant.NaviTable.ISNAVING, 1);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);

		}

		/**
		 * ��ǰ��·������
		 * 
		 * @param currentRoadName
		 *            ��ǰ·��
		 */
		@Override
		public void onCurrentRoadNameChanged(final String currentRoadName) {
			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.CURRENT_ROADNAME, currentRoadName);
			con.put(DBConstant.NaviTable.ISNAVING, 1);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);

		}

		/**
		 * ��һ��·������
		 * 
		 * @param nextRoadName
		 *            ��һ����·��
		 */
		@Override
		public void onNextRoadNameChanged(final String nextRoadName) {
			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.NEXT_ROAD, nextRoadName);
			con.put(DBConstant.NaviTable.ISNAVING, 1);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);

		}

		@Override
		public void onNaviEnd() throws RemoteException {

			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.ISNAVING, 0);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);

			BNRemoteVistor.getInstance().setOnConnectListener(
					mOncConnectListener);
			BNRemoteVistor.getInstance().connectToBNService(
					getApplicationContext());
			sendBroadcast(new Intent(STOPNAVI));
		}

		@Override
		public void onNaviStart() throws RemoteException {
			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.ISNAVING, 1);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);
			sendBroadcast(new Intent(STARTNAVI));
		}

		@Override
		public void onReRoutePlanComplete() throws RemoteException {
		}

		@Override
		public void onRoutePlanYawing() throws RemoteException {

		}

		@Override
		public void onCruiseEnd() throws RemoteException {

		}

		@Override
		public void onCruiseStart() throws RemoteException {
		}

		@Override
		public void onExtendEvent(int arg0, Bundle arg1) throws RemoteException {
		}

		@Override
		public void onGPSLost() throws RemoteException {
		}

		@Override
		public void onGPSNormal() throws RemoteException {
		}

	};

	private BNRemoteVistor.OnConnectListener mOncConnectListener = new BNRemoteVistor.OnConnectListener() {

		@Override
		public void onDisconnect() {

			ContentValues con = new ContentValues();
			con.put(DBConstant.NaviTable.ISNAVING, 0);
			getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
					null, null);

			sendBroadcast(new Intent("com.inet.broadcast.stoptnavi"));

			BNRemoteVistor.getInstance().setOnConnectListener(
					mOncConnectListener);
			BNRemoteVistor.getInstance().connectToBNService(
					getApplicationContext());

		}

		@Override
		public void onConnectSuccess() {
			try {

				BNRemoteVistor.getInstance().setBNEventListener(
						mBNEventListener);

			} catch (RemoteException e) {

				e.printStackTrace();

			}

		}

		@Override
		public void onConnectFail(final String reason) {
			try {

				BNRemoteVistor.getInstance().setBNEventListener(
						mBNEventListener);

			} catch (RemoteException e) {

				e.printStackTrace();

			}

		}

	};

	@Override
	public IBinder onBind(Intent intent) {

		return mBNEventListener;

	}

	ContentObserver contentObserver = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			boolean acc_state = Settings.System.getInt(getContentResolver(),
					ACC_STATE, 1) == 1;
			if (acc_state) {
				// mController.startLoc();
			} else {
				// mController.stopLoc();
			}
		}
	};
	private Thread mThread;

	@Override
	public void onCreate() {
		super.onCreate();
		// mController = new WeatherController(this);
		// mController.startLoc();
        Log.i("cxs","========BNRSERVICE====");
		getContentResolver().registerContentObserver(
				Settings.System.getUriFor(ACC_STATE), true, contentObserver);
		ContentValues con = new ContentValues();
		con.put(DBConstant.NaviTable.ISNAVING, 0);
		getContentResolver().update(DBConstant.NaviTable.CONTENT_URI, con,
				null, null);

		BNRemoteVistor.getInstance().setOnConnectListener(mOncConnectListener);

		BNRemoteVistor.getInstance()
				.connectToBNService(getApplicationContext());


	}


	@Override
	public void onDestroy() {
		startService(new Intent(this, BNRService.class));
		getContentResolver().unregisterContentObserver(contentObserver);
		super.onDestroy();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
}
