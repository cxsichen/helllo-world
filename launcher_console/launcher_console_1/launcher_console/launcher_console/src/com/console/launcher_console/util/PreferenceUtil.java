package com.console.launcher_console.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

public class PreferenceUtil {
	
	
	/*
	 * MODE:
	 * 
	 * WORKMODE_MISC = 0x01, WORKMODE_VIDEO = 0x02, WORKMODE_RADIO = 0x03,
	 * WORKMODE_GPS = 0x04, WORKMODE_AUX = 0x05,
	 */
	public static final String KEY_MODE = "key_mode";

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0);
	}

	private static SharedPreferences.Editor getPreferencesEditor(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0).edit();
	}

	public static int getMode(Context context) {
		int mode=4;
		try {
			mode = Settings.System.getInt(context.getContentResolver(),
					Constact.MODE,0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return mode;
	}

	public static void setMode(Context context, int value) {	
		try {
			Settings.System.putInt(context.getContentResolver(),
					Constact.MODE,value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static int getCheckMode(Context context) {
		int mode=4;
		try {
			mode = Settings.System.getInt(context.getContentResolver(),
					Constact.CHECKMODE,1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return mode;
	}

	public static void setCheckMode(Context context, int value) {	
		try {
			Settings.System.putInt(context.getContentResolver(),
					Constact.CHECKMODE,value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

}
