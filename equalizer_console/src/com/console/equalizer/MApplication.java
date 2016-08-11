package com.console.equalizer;



import com.console.equalizer.util.CrashHandler;

import android.app.Application;

public class MApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler.getInstance().init(this);
	}
}
