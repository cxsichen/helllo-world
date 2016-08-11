package com.console.launcher_console.control;

import com.console.launcher_console.AngleUtil.MyLatLng;
import com.console.launcher_console.R;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.Manifest;

import com.console.launcher_console.AngleUtil;

public class CompassControl {

	private ImageView navCompass;
	private Context context;

	LocationManager mLocationManager;
	String TAG = "cxs";
	Location latestLocation;

	MyLatLng A = new MyLatLng(113.249648, 23.401553);
	MyLatLng B = new MyLatLng(113.246033, 23.403362);

	public CompassControl(Context context, FrameLayout layout) {
		this.context = context;
		navCompass = (ImageView) layout.findViewById(R.id.ev_nav_compss);
		init(layout);
	}

	private void init(FrameLayout layout) {
		// TODO Auto-generated method stub

		mLocationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		String provider = mLocationManager.getBestProvider(criteria, true);

		if (provider != null) {

			Location location = mLocationManager.getLastKnownLocation(provider);
			// 设置间隔两秒获得一次GPS定位信息
			mLocationManager.requestLocationUpdates(provider, 2000, 8,
					new LocationListener() {
						@Override
						public void onLocationChanged(Location location) {
							// 当GPS定位信息发生改变时，更新定位
							Log.i("cxs", "-----onLocationChanged---------");
							updateShow(location);
						}

						@Override
						public void onStatusChanged(String provider,
								int status, Bundle extras) {
							Log.i("cxs", "-----onStatusChanged---------");

						}

						@Override
						public void onProviderEnabled(String provider) {
							// 当GPS LocationProvider可用时，更新定位
							Log.i("cxs", "-----onProviderEnabled---------");
							updateShow(mLocationManager
									.getLastKnownLocation(provider));
						}

						@Override
						public void onProviderDisabled(String provider) {
							updateShow(null);
						}
					});
		}
		navCompass.setRotation((float) AngleUtil.getAngle(A, B));
	}

	private void updateShow(Location location) {
		if (location != null) {
			MyLatLng B = new MyLatLng(location.getLongitude(),
					location.getLatitude());
			MyLatLng A = B;
			navCompass.setRotation((float) AngleUtil.getAngle(A, B));
		}
	}

}
