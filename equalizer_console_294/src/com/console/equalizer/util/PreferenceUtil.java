package com.console.equalizer.util;



import com.console.equalizer.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

public class PreferenceUtil {

	public static final String KEY_ST_OR_MD = "st_or_md";// 0:ST 1:MD
	
	public static final String KEY_BAS_VALUE_OLD = "basValue";
	public static final String KEY_MID_VALUE_OLD = "midValue";
	public static final String KEY_TRE_VALUE_OLD = "treValue";
	public static final String KEY_ROW_VALUE_OLD = "rowValue";
	public static final String KEY_COL_VALUE_OLD = "colValue";
	
	public static final String KEY_BAS_VALUE = "eq_basValue1111111111";
	public static final String KEY_BAS_VALUE_1 = "eq_basValue1";
	public static final String KEY_BAS_VALUE_2 = "eq_basValue2";
	public static final String KEY_MID_VALUE = "eq_midValue";
	public static final String KEY_MID_VALUE_1 = "eq_midValue1";
	public static final String KEY_MID_VALUE_2 = "eq_midValue2";
	public static final String KEY_TRE_VALUE = "eq_treValue";
	public static final String KEY_TRE_VALUE_1 = "eq_treValue1";
	public static final String KEY_TRE_VALUE_2 = "eq_treValue2";
	public static final String KEY_ROW_VALUE = "eq_rowValue";
	public static final String KEY_COL_VALUE = "eq_colValue";
	public static final String KEY_MODE = "eq_mode";

	public static final String KEY_USER_BAS_VALUE = "eq_userBasValue";
	public static final String KEY_USER_BAS_VALUE_1 = "eq_userBasValue1";
	public static final String KEY_USER_BAS_VALUE_2 = "eq_userBasValue2";
	public static final String KEY_USER_MID_VALUE = "eq_userMidValue";
	public static final String KEY_USER_MID_VALUE_1 = "eq_userMidValue1";
	public static final String KEY_USER_MID_VALUE_2 = "eq_userMidValue2";
	public static final String KEY_USER_TRE_VALUE = "eq_userTreValue";
	public static final String KEY_USER_TRE_VALUE_1 = "eq_userTreValue1";
	public static final String KEY_USER_TRE_VALUE_2 = "eq_userTreValue2";

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
	 * 
	 * @param context
	 * @param basValue
	 *            低音值 3
	 * @param midValue
	 *            中音值 3
	 * @param treValue
	 *            高音值 3
	 * @param rowValue
	 *            声音偏移排值 1
	 * @param colValue
	 *            声音偏移列值 1
	 * @param mode
	 *            1
	 * @return
	 */

	final static String[] strGroup = { KEY_BAS_VALUE, KEY_BAS_VALUE_1,
			KEY_BAS_VALUE_2, KEY_MID_VALUE, KEY_MID_VALUE_1, KEY_MID_VALUE_2,
			KEY_TRE_VALUE, KEY_TRE_VALUE_1, KEY_TRE_VALUE_2, KEY_ROW_VALUE,
			KEY_COL_VALUE, KEY_MODE };

	public static int[] getMode(Context context) {
		int values[] = new int[12];
		for (int i = 0; i < values.length; i++) {
			if (strGroup[i].equals(KEY_MODE)) {
				values[i] = Settings.System.getInt(
						context.getContentResolver(), strGroup[i], R.id.defaultEf_button);
			} if(strGroup[i].equals(KEY_ROW_VALUE)||strGroup[i].equals(KEY_COL_VALUE)){
				values[i] = Settings.System.getInt(
						context.getContentResolver(), strGroup[i], 50);
			}else {
				values[i] = Settings.System.getInt(
						context.getContentResolver(), strGroup[i],
						7);
			}
		}
		return values;
	}

	public static void setMode(Context context, int[] valueGroup) {
		for (int i = 0; i < valueGroup.length; i++) {
			Settings.System.putInt(context.getContentResolver(), strGroup[i],
					valueGroup[i]);
		}
		
		Settings.System.putInt(context.getContentResolver(),
				KEY_BAS_VALUE_OLD, valueGroup[0] * 2 / 14
				+ valueGroup[1] * 4 / 14 + valueGroup[2] * 8 / 14);
		Settings.System.putInt(context.getContentResolver(),
				KEY_MID_VALUE_OLD, valueGroup[3] * 2 / 14 + valueGroup[4] * 4 / 14
				+ valueGroup[5] * 8 / 14);
		Settings.System.putInt(context.getContentResolver(),
				KEY_TRE_VALUE_OLD,  valueGroup[6] * 2
				/ 14 + valueGroup[7] * 4 / 14 + valueGroup[8]
				* 8 / 14);
		Settings.System.putInt(context.getContentResolver(),
				KEY_ROW_VALUE_OLD, (int) (valueGroup[9] * 14 / 100));
		Settings.System.putInt(context.getContentResolver(),
				KEY_COL_VALUE_OLD, (int) (valueGroup[10] * 14 / 100));
	}
	
	public static int[] getUserMode(Context context) {
		int values[] = new int[9];
		values[0] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE, 7);
		values[1] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE_1, 7);
		values[2] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE_2, 7);
		values[3] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_MID_VALUE, 7);
		values[4] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_MID_VALUE_1, 7);
		values[5] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_MID_VALUE_2, 7);
		values[6] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE, 7);
		values[7] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE_1, 7);
		values[8] = Settings.System.getInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE_2, 7);

		return values;
	}

	public static void setUserMode(Context context, int[] valueGroup) {	
		if(valueGroup.length<9){
			return;
		}
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE, valueGroup[0]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE_1, valueGroup[1]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_BAS_VALUE_2, valueGroup[2]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_MID_VALUE, valueGroup[3]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_MID_VALUE_1, valueGroup[4]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_MID_VALUE_2, valueGroup[5]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE, valueGroup[6]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE_1, valueGroup[7]);
		Settings.System.putInt(context.getContentResolver(),
				KEY_USER_TRE_VALUE_2, valueGroup[8]);
	}
}
