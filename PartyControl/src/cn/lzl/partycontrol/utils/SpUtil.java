package cn.lzl.partycontrol.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    public static final String KEY_PWD = "pwd";//0:ST 1:MD
    public static final String DEFAULE_PWD = "888888";
    
    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0);
    }

    private static SharedPreferences.Editor getSpEd(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0).edit();
    }

    public static String getPwd(Context context) {
        SharedPreferences pref = getSp(context);
        return pref.getString(KEY_PWD,DEFAULE_PWD);
    }
 
}
