package com.softwinner.un.tool.utilex;

import android.util.Log;

/**
 * 文件名:UNLog.java V2.0 Feb 2,2015<br>
 * 描　述:UNTool Log<br>
 * 版　权:AW-BU3-PD2<br>
 *
 * @author PD2-Roy
 */
public class UNLog {

    /**
     * while debug, print the value or something important
     */
    public static final int LV_DEBUG = 0;
    /**
     * print the function name
     */
    public static final int LV_INFO = 1;
    /**
     * print the warning message
     */
    public static final int LV_WARN = 2;
    /**
     * print the error message
     */
    public static final int LV_ERROR = 3;
    private static final String TAG = "UNLogUtils";
    /**
     * to open the debug print
     */
    public static boolean isDebug = true;

    public static void debug_print(int LEVEL, String tag, String content) {

        if (isDebug) {
            switch (LEVEL) {
                case LV_DEBUG:
                    Log.d(TAG, tag + " " + content);
                    break;
                case LV_INFO:
                    Log.i(TAG, tag + " " + content);
                    break;
                case LV_WARN:
                    Log.w(TAG, tag + " " + content);
                    break;
                case LV_ERROR:
                    Log.e(TAG, tag + " " + content);
                    break;
                default:
                    break;
            }
        }
    }

}
