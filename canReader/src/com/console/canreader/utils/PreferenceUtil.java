package com.console.canreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

public class PreferenceUtil {

	public static final String MODE = "Console_mode";
	public static final String KEY_SSHyundai_CAR_TYPE = "KEY_SSHyundai_CAR_TYPE";
	private final static String CARTYPE = "carType";
	private final static String CANTYPE = "canType";
	static String[] CanTypeGroup = { "RZC", "SS" };
	static String[] CarTypeGroup = { "Volkswagen", "VolkswagenGolf", "Honda",
			"Toyota", "ToyotaRZ", "GE", "DFFG", "Peugeot", "NISSAN",
			"Trumpchi", "Ford", "HondaCRV", "HondaDA", "BuickGM", "GE",
			"Roewe360", "MGGS", "BESTURNx80", "FHCm3" };

	public static String getCANName(Context context) {
		String mode = "";
		try {
			mode = Settings.System.getString(context.getContentResolver(),
					Contacts.CAN_CLASS_NAME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (mode == "" || mode == null) {
			mode = initCanName(context);
		}
		Log.i("cxs", "========mode=======" + mode);
		return mode;
	}

	private static String initCanName(Context context) {
		String temp = "RZCVolkswagen";
		try {
			int carTypeValue = Settings.System.getInt(
					context.getContentResolver(), CARTYPE, 0);
			int canTypeValue = Settings.System.getInt(
					context.getContentResolver(), CANTYPE, 0);
			temp = CanTypeGroup[canTypeValue] + CarTypeGroup[carTypeValue];
			Settings.System.putString(context.getContentResolver(),
					Contacts.CAN_CLASS_NAME, temp);
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

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0);
	}

	private static SharedPreferences.Editor getPreferencesEditor(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0).edit();
	}

	public static int getHyundaiCarType(Context context) {
		SharedPreferences pref = getPreferences(context);
		return pref.getInt(KEY_SSHyundai_CAR_TYPE, 0);
	}

	public static void setHyundaiCarType(Context context, int value) {
		SharedPreferences.Editor editor = getPreferencesEditor(context);
		editor.putInt(KEY_SSHyundai_CAR_TYPE, value).apply();
	}

	public static int getKnobVolValue(Context context) {
		SharedPreferences pref = getPreferences(context);
		return pref.getInt("KnobVolValue", 0);
	}

	public static void setKnobVolValue(Context context, int value) {
		SharedPreferences.Editor editor = getPreferencesEditor(context);
		editor.putInt("KnobVolValue", value).apply();
	}

	public static int getKnobSelValue(Context context) {
		SharedPreferences pref = getPreferences(context);
		return pref.getInt("KnobSelValue", 0);
	}

	public static void setKnobSelValue(Context context, int value) {
		SharedPreferences.Editor editor = getPreferencesEditor(context);
		editor.putInt("KnobSelValue", value).apply();
	}

	public static int getMode(Context context) {
		int mode = 4;
		try {
			mode = Settings.System
					.getInt(context.getContentResolver(), MODE, 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mode;
	}

	public static void setMode(Context context, int value) {
		try {
			Settings.System.putInt(context.getContentResolver(), MODE, value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
