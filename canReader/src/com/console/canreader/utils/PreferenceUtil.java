package com.console.canreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

public class PreferenceUtil {
	


	public static int getCARTYPE(Context context) {
		int mode=4;
		try {
			mode = Settings.System.getInt(context.getContentResolver(),
					Contacts.CARTYPE,0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return mode;
	}

	public static void setCARTYPE(Context context, int value) {	
		try {
			Settings.System.putInt(context.getContentResolver(),
					Contacts.CARTYPE,value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static int getCANTYPE(Context context) {
		int mode=4;
		try {
			mode = Settings.System.getInt(context.getContentResolver(),
					Contacts.CANTYPE,0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return mode;
	}

	public static void setCANTYPE(Context context, int value) {	
		try {
			Settings.System.putInt(context.getContentResolver(),
					Contacts.CANTYPE,value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

}
