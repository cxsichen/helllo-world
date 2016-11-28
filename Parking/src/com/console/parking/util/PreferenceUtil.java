package com.console.parking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

public class PreferenceUtil {
	
	public static final String KEY_SSHyundai_CAR_TYPE = "KEY_SSHyundai_CAR_TYPE";
	private final static String CARTYPE = "carType";
	private final static String CANTYPE = "canType";
	public static final String CAN_INFORMATON = "CAN_Informaion";
	public static final String CAN_CLASS_NAME = "CAN_Class_Name";
	public static final String  SSNissan = "SSNissan"; 
	public static final String  SSToyotaRAV4 = "SSToyotaRAV4";         //RAV4
	public static final String SSHonda15CRV = "SSHonda15CRV"; // 15CRV
	public static final String  SSTrumpchiGS5 = "SSTrumpchiGS5";     //¹ãÆû´«ì÷GS5
	
	static String[] CanTypeGroup={"RZC","SS"};
	static String[] CarTypeGroup={"Volkswagen","VolkswagenGolf","Honda","Toyota","ToyotaRZ",
			"GE","DFFG","Peugeot","NISSAN","Trumpchi",
			"FOCUS","HondaCRV","HondaDA","BuickGM","EDGE","Roewe360","MGGS",
			"BESTURNx80","CFHCm3"};
	
	public final static class CANFISRTNAMEGROUP {
		public static final String  RAISE = "RZC";
		public static final String HIWORLD = "SS";
	}
	
	
	public static String getCANName(Context context) {
		String mode = "";
		try {
			mode = Settings.System.getString(context.getContentResolver(),
					CAN_CLASS_NAME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(mode==""||mode==null){
			mode =initCanName(context);
		}
		Log.i("cxs","========mode======="+mode);
		return mode;
	}
	
	private static String  initCanName(Context context){
		String temp="RZCVolkswagen";	
		try {
			int carTypeValue = Settings.System.getInt(context.getContentResolver(), CARTYPE, 0);
			int canTypeValue = Settings.System.getInt(context.getContentResolver(), CANTYPE, 0);
			temp=CanTypeGroup[canTypeValue]+CarTypeGroup[carTypeValue];
			Settings.System.putString(context.getContentResolver(),
					CAN_CLASS_NAME,temp);
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
			if (str.substring(0, 3).equals(CANFISRTNAMEGROUP.RAISE)) {
				return str.substring(0, 3);
			} else if (str.substring(0, 2).equals(
					CANFISRTNAMEGROUP.HIWORLD)) {
				return str.substring(0, 2);
			}
		}
		return "";
	}
	
	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0);
	}

	private static SharedPreferences.Editor getPreferencesEditor(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0).edit();
	}



}
