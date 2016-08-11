package com.console.radio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import java.util.List;

public class PreferenceUtil {

	public static final String KEY_ST_OR_MD = "st_or_md";// 0:ST 1:MD
	/** 0:FM1 1:FM2 2:FM3 3:AM1 4:AM2 */
	public static final String KEY_CURRENT_BAND = "current_band";
	public static final String KEY_FAVORITE_FQ = "favorite_fq";
	public static final String KEY_VOLUME_VALUE = "volume_value";
	public static final String KEY_FQ_0 = "fq_0";
	public static final String KEY_FQ_1 = "fq_1";
	public static final String KEY_FQ_2 = "fq_2";
	public static final String KEY_FQ_3 = "fq_3";
	public static final String KEY_FQ_4 = "fq_4";
	public static final String KEY_FQ_5 = "fq_5";

	public static final String KEY_FQ_6 = "fq_6";
	public static final String KEY_FQ_7 = "fq_7";
	public static final String KEY_FQ_8 = "fq_8";
	public static final String KEY_FQ_9 = "fq_9";
	public static final String KEY_FQ_10 = "fq_10";
	public static final String KEY_FQ_11 = "fq_11";

	public static final String KEY_FQ_12 = "fq_12";
	public static final String KEY_FQ_13 = "fq_13";
	public static final String KEY_FQ_14 = "fq_14";
	public static final String KEY_FQ_15 = "fq_15";
	public static final String KEY_FQ_16 = "fq_16";
	public static final String KEY_FQ_17 = "fq_17";

	public static final String KEY_FQ_18 = "fq_18";
	public static final String KEY_FQ_19 = "fq_19";
	public static final String KEY_FQ_20 = "fq_20";
	public static final String KEY_FQ_21 = "fq_21";
	public static final String KEY_FQ_22 = "fq_22";
	public static final String KEY_FQ_23 = "fq_23";

	public static final String KEY_FQ_24 = "fq_24";
	public static final String KEY_FQ_25 = "fq_25";
	public static final String KEY_FQ_26 = "fq_26";
	public static final String KEY_FQ_27 = "fq_27";
	public static final String KEY_FQ_28 = "fq_28";
	public static final String KEY_FQ_29 = "fq_29";

	public static final String[] ALL_FQ_KEY = { KEY_FQ_0, KEY_FQ_1, KEY_FQ_2,
			KEY_FQ_3, KEY_FQ_4, KEY_FQ_5, KEY_FQ_6, KEY_FQ_7, KEY_FQ_8,
			KEY_FQ_9, KEY_FQ_10, KEY_FQ_11, KEY_FQ_12, KEY_FQ_13, KEY_FQ_14,
			KEY_FQ_15, KEY_FQ_16, KEY_FQ_17, KEY_FQ_18, KEY_FQ_19, KEY_FQ_20,
			KEY_FQ_21, KEY_FQ_22, KEY_FQ_23, KEY_FQ_24, KEY_FQ_25, KEY_FQ_26,
			KEY_FQ_27, KEY_FQ_28, KEY_FQ_29 };

	public static final int DEFAULT_FM = 9500;
	public static final int DEFAULT_AM = 522;

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0);
	}

	private static SharedPreferences.Editor getPreferencesEditor(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0).edit();
	}

	public static int getSTOrMD(Context context) {
		SharedPreferences pref = getPreferences(context);
		return pref.getInt(KEY_ST_OR_MD, 0);
	}

	public static void setSTOrMD(Context context, int value) {
		SharedPreferences.Editor editor = getPreferencesEditor(context);
		editor.putInt(KEY_ST_OR_MD, value).apply();
	}

	public static int getBand(Context context) {
		return getPreferences(context).getInt(KEY_CURRENT_BAND, 0);
	}

	public static void setBand(Context context, int band) {
		getPreferencesEditor(context).putInt(KEY_CURRENT_BAND, band).apply();
	}

	public static int getFavoriteFq(Context context) {
		return getPreferences(context).getInt(KEY_FAVORITE_FQ, 0);
	}

	public static void setFavoriteFq(Context context, int item) {
		getPreferencesEditor(context).putInt(KEY_FAVORITE_FQ, item).apply();
	}

	public static void setVolume(Context context, int value) {
		try {
			Settings.System.putInt(context.getContentResolver(),
					KEY_VOLUME_VALUE,value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static int getVolume(Context context) {
		try {
			int mode = Settings.System.getInt(context.getContentResolver(),
					KEY_VOLUME_VALUE, 27);
			return mode;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}

	public static void getAllFq(Context context, List<Integer> allFqs) {
		if (allFqs == null) {
			return;
		}
		SharedPreferences pref = getPreferences(context);
		int defaultValue = 0;

		allFqs.clear();
		for (int i = 0; i < ALL_FQ_KEY.length; i++) {
			if (i < 18) {
				defaultValue = DEFAULT_FM;
			} else {
				defaultValue = DEFAULT_AM;
			}
			int value = pref.getInt(ALL_FQ_KEY[i], defaultValue);
			Trace.i(ALL_FQ_KEY[i] + " : " + value);
			allFqs.add(value);
		}
	}

	@SuppressWarnings("null")
	public static void setAllFq(Context context, List<Integer> allFqs) {
		if (allFqs == null && allFqs.size() < ALL_FQ_KEY.length) {
			return;
		}
		SharedPreferences.Editor editor = getPreferencesEditor(context);
		for (int i = 0; i < ALL_FQ_KEY.length; i++) {
			editor.putInt(ALL_FQ_KEY[i], allFqs.get(i)).apply();
		}
	}

}
