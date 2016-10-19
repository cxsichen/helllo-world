package com.console.canreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

public class PreferenceUtil {

	private final static String CARTYPE = "carType";
	private final static String CANTYPE = "canType";
	static String[] CanTypeGroup={"RZC","SS"};
	static String[] CarTypeGroup={"Volkswagen","VolkswagenGolf","Honda","Toyota","ToyotaRZ",
			"GE","DFFG","Peugeot","NISSAN","Trumpchi",
			"FOCUS","HondaCRV","HondaDA","BuickGM","EDGE","Roewe360","MGGS",
			"BESTURNx80","CFHCm3"};
	public static String getCANName(Context context) {
		String mode = "";
		try {
			mode = Settings.System.getString(context.getContentResolver(),
					Contacts.CAN_CLASS_NAME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(mode==""||mode==null){
			mode =initCanName(context);
		}
		return mode;
	}
	
	private static String  initCanName(Context context){
		String temp="RZCVolkswagen";
		int carTypeValue = Settings.System.getInt(context.getContentResolver(), CARTYPE, 0);
		int canTypeValue = Settings.System.getInt(context.getContentResolver(), CANTYPE, 0);
		try {
			temp=CanTypeGroup[canTypeValue]+CarTypeGroup[carTypeValue];
			Settings.System.putString(context.getContentResolver(),
					Contacts.CAN_CLASS_NAME,temp);
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return temp;
	}

	public static String getFirstTwoString(Context context, String str) {
		if (str == null) {
			return "";
		}
		if (str.length() <= 2) {
			return str;
		} else {
			if (str.substring(0, 3).equals(Contacts.CANFISRTNAMEGROUP.RAISE)) {
				return str.substring(0, 3);
			} else if (str.substring(0, 2).equals(
					Contacts.CANFISRTNAMEGROUP.HIWORLD)) {
				return str.substring(0, 2);
			}
		}
		return "";
	}

}
