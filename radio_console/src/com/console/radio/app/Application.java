package com.console.radio.app;

import com.console.radio.utils.CrashHandler;



public class Application extends android.app.Application {

	public static Application instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
/*		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());*/
		CrashHandler.getInstance().init(this);

	}

	public static Application getInstance() {
		if (null == instance) {
			instance = new Application();
		}
		return instance;
	}

}
