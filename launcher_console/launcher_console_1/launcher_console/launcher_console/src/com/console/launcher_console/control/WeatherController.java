/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.console.launcher_console.control;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.console.launcher_console.R;
import com.console.launcher_console.util.Trace;
import com.zzj.softwareservice.database.DBConstant;

public class WeatherController {

	private final Context mContext;


	public static Double Lat = 0.0;
	public static Double Lng = 0.0;
	public static String LocationTime;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;

	private String weather;
	private String currentTemperature;
	private String wind;
	private String localStr;
	private String modfifyStr;
	
	private String mImei;
	
	private TextView temperatureTv;
	private ImageView weatherIv;
	private TextView weatherTv;
	private ImageView locationIv;
	private TextView weatherPlaceTv;
	private LinearLayout weatherLayout;
	private TextView dateTv;
//	private long time;
	
	private HashMap<String, String> mBigImageNameIdMap = null;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 0:
				getWeather();
				break;
			case 1:
				showWeatherDate();
				break;
			case 3:
				Date curDate = new Date(System.currentTimeMillis());
				dateTv.setText((new SimpleDateFormat("yyyy年MM月dd日  HH:mm")).format(curDate));
				mHandler.sendEmptyMessageDelayed(3, 1000);
				break;
			default:
				break;
			}
		}

	};

	private void initBigImageNameIdMap(Context context) {

		// String TODAY_STRING = context.getResources().getString(R.string.today);
		// String TOMMOROW_STRING = context.getResources().getString(R.string.tomorrow);
		// String DAY_AFTER_TOMMOROW_STRING = getResources().getString(R.string.day_after_tomorrow);

		String sunny = context.getResources().getString(R.string.weather_sunny_big);
		String cloudy = context.getResources().getString(R.string.weather_cloudy_big);
		String overcast = context.getResources().getString(R.string.weather_overcast_big);
		String foggy = context.getResources().getString(R.string.weather_foggy_big);
		String dustblow = context.getResources().getString(R.string.weather_dustblow_big);
		String dust = context.getResources().getString(R.string.weather_dust_big);
		String sandstorm = context.getResources().getString(R.string.weather_sandstorm_big);
		String strong_sandstorm = context.getResources().getString(R.string.weather_strong_sandstorm_big);
		String icerain = context.getResources().getString(R.string.weather_icerain_big);
		String shower = context.getResources().getString(R.string.weather_shower_big);
		String thunder_rain = context.getResources().getString(R.string.weather_thunder_rain_big);
		String hail = context.getResources().getString(R.string.weather_hail_big);
		String sleety = context.getResources().getString(R.string.weather_sleety_big);
		String light_rain = context.getResources().getString(R.string.weather_light_rain_big);
		String moderate_rain = context.getResources().getString(R.string.weather_moderate_rain_big);
		String heavy_rain = context.getResources().getString(R.string.weather_heavy_rain_big);
		String rainstorm = context.getResources().getString(R.string.weather_rainstorm_big);
		String big_rainstorm = context.getResources().getString(R.string.weather_big_rainstorm_big);
		String super_rainstorm = context.getResources().getString(R.string.weather_super_rainstorm_big);
		String snow_shower = context.getResources().getString(R.string.weather_snow_shower_big);
		String light_snow = context.getResources().getString(R.string.weather_light_snow_big);
		String moderate_snow = context.getResources().getString(R.string.weather_moderate_snow_big);
		String heavy_snow_big = context.getResources().getString(R.string.weather_heavy_snow_big);
		String blizzard = context.getResources().getString(R.string.weather_blizzard_big);
		String haze = context.getResources().getString(R.string.weather_haze);

	    if (mBigImageNameIdMap == null) {
	        mBigImageNameIdMap = new HashMap<String, String>();
	    }
	    mBigImageNameIdMap.clear();
	    mBigImageNameIdMap.put(sunny, "ic_weather_sunny_big");
		mBigImageNameIdMap.put(cloudy, "ic_weather_cloudy_big");
		mBigImageNameIdMap.put(overcast, "ic_weather_overcast_big");
		mBigImageNameIdMap.put(foggy, "ic_weather_foggy_big");
		mBigImageNameIdMap.put(dustblow, "ic_weather_dustblow_big");
		mBigImageNameIdMap.put(dust, "ic_weather_dust_big");
		mBigImageNameIdMap.put(sandstorm, "ic_weather_sandstorm_big");
		mBigImageNameIdMap.put(strong_sandstorm, "ic_weather_strong_sandstorm_big");
		mBigImageNameIdMap.put(icerain, "ic_weather_icerain_big");
		mBigImageNameIdMap.put(shower, "ic_weather_shower_big");
		mBigImageNameIdMap.put(thunder_rain, "ic_weather_thunder_rain_big");
		mBigImageNameIdMap.put(hail, "ic_weather_hail_big");
		mBigImageNameIdMap.put(sleety, "ic_weather_sleety_big");
		mBigImageNameIdMap.put(light_rain, "ic_weather_light_rain_big");
		mBigImageNameIdMap.put(moderate_rain, "ic_weather_moderate_rain_big");
		mBigImageNameIdMap.put(heavy_rain, "ic_weather_heavy_rain_big");
		mBigImageNameIdMap.put(rainstorm, "ic_weather_rainstorm_big");
		mBigImageNameIdMap.put(big_rainstorm, "ic_weather_big_rainstorm_big");
		mBigImageNameIdMap.put(super_rainstorm, "ic_weather_super_rainstorm_big");
		mBigImageNameIdMap.put(snow_shower,"ic_weather_snow_shower_big");
		mBigImageNameIdMap.put(light_snow, "ic_weather_light_snow_big");
		mBigImageNameIdMap.put(moderate_snow, "ic_weather_moderate_snow_big");
		mBigImageNameIdMap.put(heavy_snow_big, "ic_weather_heavy_snow_big");
		mBigImageNameIdMap.put(blizzard, "ic_weather_blizzard_big");
		mBigImageNameIdMap.put(haze, "ic_weather_haze");
	}

	public WeatherController(Context context,LinearLayout layout) {

		Trace.i("WeatherController");
		mContext = context;
		initBigImageNameIdMap(context);
		weatherLayout=layout;
		mImei = ((TelephonyManager)mContext.getSystemService("phone")).getDeviceId();

		initLocationClient();
		initView();
		startLoc();

	}
	

	
	private void initView() {
		// TODO Auto-generated method stub
		temperatureTv=(TextView) weatherLayout.findViewById(R.id.temperature_tv);
		weatherIv= (ImageView) weatherLayout.findViewById(R.id.weather_iv);
		weatherTv=(TextView) weatherLayout.findViewById(R.id.weather_tv);
		locationIv=(ImageView) weatherLayout.findViewById(R.id.weather_location);
		weatherPlaceTv=(TextView) weatherLayout.findViewById(R.id.weather_place);
		dateTv=(TextView) weatherLayout.findViewById(R.id.date_tv);
		mHandler.sendEmptyMessageDelayed(3, 1000);
	}
	

	private void showWeatherDate() {
		// TODO Auto-generated method stub
		if(!TextUtils.isEmpty(currentTemperature)){
			temperatureTv.setText(currentTemperature+mContext.getString(R.string.temperature_degree));
		}
		if(!TextUtils.isEmpty(weather)&&!TextUtils.isEmpty(wind)){
			weatherTv.setText(weather+"  "+wind);
		}
		if(!TextUtils.isEmpty(localStr)){
			weatherPlaceTv.setText(localStr);
		}
		if(!TextUtils.isEmpty(modfifyStr)){
		    String image= mBigImageNameIdMap.get(modfifyStr);
		    weatherIv.setImageResource( getResourceWerterName(image));
		}
	}

	public void startLoc(){
		if(mLocationClient == null){
			initLocationClient();
		}
		mLocationClient.start();
		Trace.i("mLocationClient start ");
	}
	
	public void stopLoc(){
		if(mLocationClient == null){
			initLocationClient();
		}
		mLocationClient.stop();
		Trace.i("mLocationClient stopLoc ");
	}
	
	private void initLocationClient(){
		mLocationClient = new LocationClient(mContext);
		initLocation(5000);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
	}

	private void initLocation(int span){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//可选
//        option.setCoorType("bd09ll");//
        option.setScanSpan(span);//可选
        option.setIsNeedAddress(true);//可选
        option.setOpenGps(true);//可选
        option.setLocationNotify(false);//可选
   //     option.setIsNeedLocationDescribe(true);//可选
   //     option.setIsNeedLocationPoiList(true);//可选
        option.setIgnoreKillProcess(false);//可选 
        option.SetIgnoreCacheException(true);//可选
 //       option.setEnableSimulateGps(false);//可选
        mLocationClient.setLocOption(option);
    }

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			
			int errorCode = location.getLocType();
			if (errorCode == BDLocation.TypeGpsLocation || errorCode == BDLocation.TypeNetWorkLocation || errorCode == 65) {
				String temp = location.getCity();
				locationIv.setVisibility(View.VISIBLE);
				if(TextUtils.isEmpty(mImei)){
					mImei = ((TelephonyManager)mContext.getSystemService("phone")).getDeviceId();
				}
				if((!TextUtils.isEmpty(temp) && !temp.equals(localStr))){
					localStr = temp;
					initLocation(600000);
					mHandler.sendEmptyMessage(0);
				}

			} else {
				
			}
		}
	}
	
	private void getWeather() {
		final String url = "http://scv2.hivoice.cn/service/iss?history=&text=" + localStr + "的天气" + "&scenario=incar&appkey=sknvnxhkddkf4vz2l2nl464imv5ymzjhs5x55oag&method=iss.getTalk&udid="+mImei+"&ver=2.0&appsig=CC8FA3BB8C56277DA8D47CC3D5BF250E3589AF60&appver=2.1.0.27";
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setSoTimeout(httpParameters, 3000);
				HttpGet httpGet = new HttpGet(url);
				try {
					httpClient.execute(httpGet);
					HttpResponse response = httpClient.execute(httpGet);
					Trace.i("getStatusCode = " + response.getStatusLine().getStatusCode());
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						String msg = EntityUtils.toString(entity);
						weatherAnalyze(msg);
					} else {
						mHandler.sendEmptyMessageDelayed(0, 60000);
					}
				} catch (ClientProtocolException e) {
					mHandler.sendEmptyMessageDelayed(0, 60000);
					e.printStackTrace();
				} catch (IOException e) {
					mHandler.sendEmptyMessageDelayed(0, 60000);
					e.printStackTrace();
				}
			}
		}).start();
	}

	int i;
	private void weatherAnalyze(String weatherUrl){
		try {
			Trace.i("i = " +i++);
			JSONObject weatherJson = new JSONObject(weatherUrl);
			JSONArray weatherArray = weatherJson.getJSONObject("data").getJSONObject("result").getJSONArray("weatherDays");
			JSONObject todayJson = (JSONObject) weatherArray.get(0);
			weather = todayJson.getString("weather");
			currentTemperature = todayJson.getString("currentTemperature");
			wind = todayJson.getString("wind");
			Trace.i("weather = " + weather);
			Trace.i("currentTemperature = " + currentTemperature);
			Trace.i("wind = " + wind);
			modfifyStr = modfifyWeatherImage(weather);
			Trace.i("modfifyStr = " + modfifyStr);
	//		weatherStr = weatherArray.get(0).toString();
	//		L.v("weatherStr = " + weatherStr);
			String imageres = mBigImageNameIdMap.get(modfifyStr);
			Trace.i("imageres = " + imageres);
			ContentValues con = new ContentValues();
			if(!TextUtils.isEmpty(localStr)){
				con.put(DBConstant.WeatherTable.CITY, localStr);
			}
			if(!TextUtils.isEmpty(weather)){
				con.put(DBConstant.WeatherTable.WEATHER, weather);
			}
			if(!TextUtils.isEmpty(wind)){
				con.put(DBConstant.WeatherTable.WIND, wind);
			}
			if(!TextUtils.isEmpty(currentTemperature)){
				con.put(DBConstant.WeatherTable.TEMPERATURE, currentTemperature);
			}
			if(!TextUtils.isEmpty(imageres)){
				con.put(DBConstant.WeatherTable.IMAGERES, imageres);
			}
			int update = mContext.getContentResolver().update(DBConstant.WeatherTable.CONTENT_URI, con,null, null);
			if(update == 0){
				mContext.getContentResolver().update(DBConstant.WeatherTable.CONTENT_URI, con,null, null);
			}
	    	mHandler.removeMessages(0);
			mHandler.sendEmptyMessageDelayed(0, 5400000);
			mHandler.sendEmptyMessage(1);
		//	initLocation(3600000);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String modfifyWeatherImage(String weather) {
		String weatherDao = mContext.getString(R.string.to);
		String weatherZhuan = mContext.getString(R.string.turn);

		if (weather != null && !(weather = weather.trim()).equals("")) {
			int zhuanIndex = -1;
			int daoIndex = -1;

			if ((zhuanIndex = weather.indexOf(weatherZhuan)) > 0) {
				weather = weather.substring(0, zhuanIndex);
			}

			if ((daoIndex = weather.indexOf(weatherDao)) > 0) {
				weather = weather.substring(daoIndex + weatherDao.length(), weather.length());
			}
		}
		return weather;
	}
	
	public int getResourceWerterName(String imageName) {
		int resId = mContext.getResources().getIdentifier(imageName, "drawable",
				mContext.getPackageName());
		return resId;
	}

}
