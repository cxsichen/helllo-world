package com.console.launcher_console.util;

import java.io.UnsupportedEncodingException;
import java.lang.Thread.UncaughtExceptionHandler;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandler INSTANCE;
	private Context mContext;
	private CrashHandler() {

	}

	public static CrashHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CrashHandler();
		return INSTANCE;
	}

	public void init(Context context) {
		mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override   
	public void uncaughtException(Thread thread, Throwable ex) {
//		if (!handleException(ex) && mDefaultHandler != null) {
//			mDefaultHandler.uncaughtException(thread, ex);
//		} else {
//		}
		Log.i("cxs","------uncaughtException------------");
		try {
			CrashLogUtils.saveLogToFile(mContext, ex);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mDefaultHandler.uncaughtException(thread, ex);
	}

	public boolean handleException(final Throwable ex) {
		Log.i("cxs","------handleException------------");
		if (ex == null || mContext == null)
			return false;
		new Thread() {
			public void run() {
				ex.printStackTrace();
				try {
					CrashLogUtils.saveLogToFile(mContext, ex);
				} catch (Exception e) {
					e.printStackTrace();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
		}.start();
		return true;
	}

}