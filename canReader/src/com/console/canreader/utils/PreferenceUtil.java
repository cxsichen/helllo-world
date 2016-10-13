package com.console.canreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

public class PreferenceUtil {

	public static String getCANName(Context context) {
		String mode = "";
		try {
			mode = Settings.System.getString(context.getContentResolver(),
					Contacts.CAN_CLASS_NAME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mode;
	}

	public static String getFirstTwoString(Context context, String str) {
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
		return null;
	}

}
