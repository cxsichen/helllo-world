package com.console.equalizer.util;

import com.console.equalizer.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

public class PreferenceUtil {

	public static final String KEY_ST_OR_MD = "st_or_md";// 0:ST 1:MD
	public static final String KEY_BAS_VALUE = "basValue";
	public static final String KEY_MID_VALUE = "midValue";
	public static final String KEY_TRE_VALUE = "treValue";
	public static final String KEY_ROW_VALUE = "rowValue";
	public static final String KEY_COL_VALUE = "colValue";
	public static final String KEY_MODE = "mode";
	
	public static final String KEY_USER_BAS_VALUE = "userBasValue";
	public static final String KEY_USER_MID_VALUE = "userMidValue";
	public static final String KEY_USER_TRE_VALUE = "userTreValue";

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
	
	 /**
     * 获取音效值
     * @param context
     * @param basValue     低音值
     * @param midValue     中音值
     * @param treValue     高音值
     * @param rowValue     声音偏移排值
     * @param colValue     声音偏移列值
     * @param mode
     * @return
     */

	public static int[] getMode(Context context, int basValue, int midValue,
			int treValue, int rowValue, int colValue, int mode) {
		basValue = Settings.System.getInt(context.getContentResolver(),
				KEY_BAS_VALUE, 7);
		midValue = Settings.System.getInt(context.getContentResolver(),
				KEY_MID_VALUE, 7);
		treValue = Settings.System.getInt(context.getContentResolver(),
				KEY_TRE_VALUE, 7);
		rowValue = Settings.System.getInt(context.getContentResolver(),
				KEY_ROW_VALUE, 7);
		colValue = Settings.System.getInt(context.getContentResolver(),
				KEY_COL_VALUE, 7);
		mode = Settings.System
				.getInt(context.getContentResolver(), KEY_MODE, R.id.defaultEf_button);
		int values[]=new int[6];
		values[0]=basValue;
		values[1]=midValue;
		values[2]=treValue;
		values[3]=rowValue;
		values[4]=colValue;
		values[5]=mode;
		return values;

	}

	public static void setMode(Context context, int basValue, int midValue,
			int treValue, int rowValue, int colValue, int mode) {

		Settings.System.putInt(context.getContentResolver(), KEY_BAS_VALUE,
				basValue);
		Settings.System.putInt(context.getContentResolver(), KEY_MID_VALUE,
				midValue);
		Settings.System.putInt(context.getContentResolver(), KEY_TRE_VALUE,
				treValue);
		Settings.System.putInt(context.getContentResolver(), KEY_ROW_VALUE,
				rowValue);
		Settings.System.putInt(context.getContentResolver(), KEY_COL_VALUE,
				colValue);
		Settings.System.putInt(context.getContentResolver(), KEY_MODE, mode);
	}
	
	
	public static int[] getUserMode(Context context, int userBasValue, int userMidValue,
			int userTreValue) {
		userBasValue = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE, 7);
		userMidValue = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_MID_VALUE, 7);
		userTreValue = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE, 7);
		int values[]=new int[3];
		values[0]=userBasValue;
		values[1]=userMidValue;
		values[2]=userTreValue;
		return values;
	}
	
	public static void setUserMode(Context context, int basValue, int midValue,
			int treValue) {

		Settings.System.putInt(context.getContentResolver(), KEY_USER_BAS_VALUE,
				basValue);
		Settings.System.putInt(context.getContentResolver(), KEY_USER_MID_VALUE,
				midValue);
		Settings.System.putInt(context.getContentResolver(), KEY_USER_TRE_VALUE,
				treValue);

	}

}
