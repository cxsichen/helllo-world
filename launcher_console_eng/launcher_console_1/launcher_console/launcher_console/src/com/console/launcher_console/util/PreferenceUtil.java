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
	
	public static final String KEY_BAS_VALUE = "basValue";
	public static final String KEY_MID_VALUE = "midValue";
	public static final String KEY_TRE_VALUE = "treValue";
	public static final String KEY_ROW_VALUE = "rowValue";
	public static final String KEY_COL_VALUE = "colValue";

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
	public static int[] getEquValue(Context context, int basValue, int midValue,
			int treValue, int rowValue, int colValue) {
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
		int values[]=new int[6];
		values[0]=basValue;
		values[1]=midValue;
		values[2]=treValue;
		values[3]=rowValue;
		values[4]=colValue;
		return values;

	}


}
